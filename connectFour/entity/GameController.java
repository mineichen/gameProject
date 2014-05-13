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
    private Game game ;

    public GameController(Game game) {
       this.game = game ;
       game.getCurrentPlayer().addEventListener(this);
    }

    public void on(MoveEvent event) {
       PlayerInterface player = game.getCurrentPlayer();
        try {
            System.out.println("Call me");
              game.addDisc(event.getCol());
              player.removeEventListener(this);
              game.getCurrentPlayer().addEventListener(this);     // view benachrichtigen
        } catch(InvalidInputException exception) {
        }
         
       
    }

}
