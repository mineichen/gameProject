/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventListener;
import connectFour.InvalidInputException ;

/**
 *
 * @author efux
 */
public class GameController implements EventListener<MoveEvent>
{
    private GameInterface game;
    private MoveEvent lastChange;

    public GameController(GameInterface game) {
       this.game = game ;
       game.getCurrentPlayer().addEventListener(this);
    }

    public void on(MoveEvent event) {
       
        PlayerInterface player = game.getCurrentPlayer();
        if (event.getPlayer() == player) {
            try {
                game.addDisc(event.getCol());
            } catch(InvalidInputException exception) {
                System.out.println("Move is not allowed");
            }finally{                
                player.removeEventListener(this);
                game.getCurrentPlayer().addEventListener(this);     // view benachrichtigen
            }
        }
    }
}
