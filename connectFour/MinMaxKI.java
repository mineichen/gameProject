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

/**
 *
 * @author mineichen
 */
public class MinMaxKI
{

    private GameInterface game;
    private int level;
    private PlayerInterface tPlayer;

    public MinMaxKI(GameInterface game, int level)
    {
        this.level = level;
    }

    public int suggestCol()
    {
        this.tPlayer = game.getCurrentPlayer();
        return calcMinMax(
            game, 
            this.level * game.getPlayers().size()
        );
    }

    private int calcMinMax(GameInterface game, int level)
    {
        int result = 0;
        for(int col = 0; col < game.getCols(); col++) {
            if(game.isWinnerMove(col)) {
                int minMax = (game.getCurrentPlayer().isSameTeam(tPlayer)) ? 1 : -1;
                result += minMax * Math.pow(game.getCols(), level);
            } else if (level > 0) {
                try {
                    GameInterface cGame = (GameInterface) game.clone();
                    cGame.addDisc(col);
                    result += calcMinMax(cGame, level -1);
                } catch(InvalidInputException e) {
                    
                }
            }   
        }
        
        return result;
    }
}
