/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour;

import connectFour.entity.Game;
import connectFour.entity.GuiPlayer;
import connectFour.entity.PlayerInterface;
import connectFour.entity.View;
import java.awt.Color;
import javax.swing.ImageIcon;

/**
 *
 * @author mike
 */
public class GameProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Color c = Color.red;
            
            PlayerInterface player = new GuiPlayer("Markus", new ImageIcon(GameProject.class.getResource("/connectFour/images/default_white_dot.png")));
            Game game = new Game(player);
            game.addDisc(4);
            game.addDisc(3);
            game.addDisc(2);
            System.out.println("Win: " + (game.isWinnerMove(1) ? "true" : "false"));
            game.addDisc(4);
            //game.addDisc(4);
            System.out.println("Win: " + (game.isWinnerMove(4) ? "true" : "false"));
        } catch(InvalidInputException e) {
            
        }
        
    }
    
}
