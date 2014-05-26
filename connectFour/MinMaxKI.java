/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectFour;

import connectFour.entity.GameInterface;
import connectFour.entity.MoveEvent;
import connectFour.entity.PlayerInterface;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author mineichen
 */
public class MinMaxKI
{

    private GameInterface game;
    private int level = 8;  
    private PlayerInterface tPlayer;
    private ExecutorService executor;
    private boolean makeThread= true;
    
    public void bind(GameInterface game)
    {
        this.game = game;
        executor = Executors.newFixedThreadPool(game.getCols() + 1);
    }

    public int suggestCol(EventListener<MoveEvent> e)
    {
        this.tPlayer = game.getCurrentPlayer();
        try {
            return getMax(executor.submit(new MinMax(game, level)).get());
        } catch(Exception ex) {
            throw new RuntimeException("Thread Died");
        }
    }
    
    private int getMax(float[] minmax) 
    {
        int firstAllowed = 0;
        while(!game.isAllowed(firstAllowed)) {
            firstAllowed++;
        }
        
        int maxKey = firstAllowed;
        float maxValue = minmax[firstAllowed];
        for(int i = firstAllowed +1; i<minmax.length;i++) {
            if(game.isAllowed(i) && maxValue < minmax[i]) {
                maxValue = minmax[i];
                maxKey = i;
            }
        }
        return maxKey;
    }
    
    private class MinMax implements Callable<float[]>
    {
        private GameInterface game;
        private int level;
        private HashMap<Integer,Future<float[]>> tasks;
        
        private float[] results;
        public MinMax(GameInterface game, int level)
        {
            this.game = game;
            this.level = level;
            this.tasks = new HashMap<>();
            this.results = new float[game.getCols()];
        }
        @Override
        public float[] call() 
        {
            for(int col = 0; col < game.getCols(); col++) {
                if(!game.isAllowed(col)) {
                    continue;
                }

                if(game.isWinnerMove(col)) {
                    float minMax = (game.getCurrentPlayer().isSameTeam(tPlayer)) ? 1 : -2;
                    results[col] = (float)(minMax * Math.pow(game.getCols(), level));
                    for(int j = 0; j < col; j++) {
                        if(results[col] == results[j]) {
                            for(int k = 0;k < col; k++) {
                                results[k] = 0;
                            }
                            results[col] = (float)(minMax * Math.pow(game.getCols(), level + 1));
                            results[j] = 0;
                            return results;
                        }
                    }
                } else if (level > 0) {
                    try {
                        GameInterface cGame = (GameInterface) game.clone();
                        cGame.addDisc(col);
                        MinMax minmax = new MinMax(cGame, level -1);
                        
                        if(makeThread) {
                            tasks.put(col, executor.submit(minmax));
                            makeThread = false;
                        } else {
                            results[col] = calcSum(minmax.call());
                        }
                    } catch(InvalidInputException e) {
                        System.out.println("Not reached: " + col);
                    }
                }   
            }
            try {
                for(int col: tasks.keySet()) {
                    results[col] = calcSum(tasks.get(col).get());
                }
            } catch(Exception e) {
                throw new RuntimeException("Thread Died");
            }
            
            return results;
        }
        
        private float calcSum(float[] tempResults) 
        {
            float tmp = 0;
            for(float i: tempResults) {
                tmp += i;
            }
            return tmp;
        }
    }
}
