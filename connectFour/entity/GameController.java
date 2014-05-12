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

    public void GameController(Game game) {
       this.game = game ;
    }

    public void on(MoveEvent event) {
       if(game.getCurrentPlayer() == event.getPlayer()) {
          try {
              game.addDisc(event.getCol()) ;
              // view benachrichtigen
          } catch(InvalidInputException exception) {
          }
       }
    }

}
