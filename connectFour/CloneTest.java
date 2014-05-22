/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour;

import connectFour.entity.DiscMoveEvent;
import connectFour.entity.Game;
import connectFour.entity.GameInterface;
import connectFour.entity.GuiPlayer;

/**
 *
 * @author mineichen
 */
public class CloneTest
{
    public static void main(String[] args)
    {
        GameInterface game = new Game(new GuiPlayer("Maik", null), new GuiPlayer("Markus", null));
        game.addEventListener(new EventListener<DiscMoveEvent>() {
            @Override
            public void on(DiscMoveEvent e) {
                System.out.println("Should not be called");
            }
        });
        
        
        GameInterface game2 = (GameInterface)game.clone();
        try {
            game2.addDisc(1);    
        } catch(InvalidInputException e) {
            
        }
        System.out.println("Täscht");
    }
}
