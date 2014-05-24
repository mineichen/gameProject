/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectFour;

import connectFour.entity.Game;
import connectFour.entity.GameInterface;
import connectFour.entity.PlayerInterface;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.security.auth.callback.Callback;

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
        executor = Executors.newCachedThreadPool();
    }

    public int suggestCol()
    {
        this.tPlayer = game.getCurrentPlayer();
        try {
            return getMax(executor.submit(new MinMax(game, level)).get());
        } catch(Exception e) {
            throw new RuntimeException("Thread Died");
        }
    }
    
    private int getMax(int[] minmax) 
    {
        int firstAllowed = 0;
        while(!game.isAllowed(firstAllowed)) {
            firstAllowed++;
        }
        
        int maxKey = firstAllowed;
        int maxValue = minmax[firstAllowed];
        for(int i = firstAllowed +1; i<minmax.length;i++) {
            if(game.isAllowed(i) && maxValue < minmax[i]) {
                maxValue = minmax[i];
                maxKey = i;
            }
        }
        return maxKey;
    }
    
    private class MinMax implements Callable<int[]>
    {
        private GameInterface game;
        private int level;
        private HashMap<Integer,Future<int[]>> tasks;
        
        private int[] results;
        public MinMax(GameInterface game, int level)
        {
            this.game = game;
            this.level = level;
            this.tasks = new HashMap<>();
            this.results = new int[game.getCols()];
        }
        @Override
        public int[] call() 
        {
            for(int col = 0; col < game.getCols(); col++) {
                if(!game.isAllowed(col)) {
                    continue;
                }

                if(game.isWinnerMove(col)) {
                    int minMax = (game.getCurrentPlayer().isSameTeam(tPlayer)) ? 1 : -1;
                    results[col] = minMax * (int)Math.pow(game.getCols(), level);
                } else if (level > 0) {
                    try {
                        GameInterface cGame = (GameInterface) game.clone();
                        cGame.addDisc(col);
                        
                        if(makeThread) {
                            tasks.put(col, executor.submit(new MinMax(cGame, level -1)));
                            makeThread = false;
                        } else {
                            results[col] = calcSum(new MinMax(cGame, level -1).call());
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
        
        private int calcSum(int[] tempResults) 
        {
            int tmp = 0;
            for(int i: tempResults) {
                tmp += i;
            }
            return tmp;
        }
    }
}
