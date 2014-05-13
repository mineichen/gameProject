/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour;

import connectFour.entity.Game;
import connectFour.entity.GuiPlayer;
import connectFour.entity.PlayerInterface;
import connectFour.View.View;
import connectFour.entity.GameController;
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
        
            
            View view = new View();
            
            PlayerInterface player = new GuiPlayer("Markus", new ImageIcon(GameProject.class.getResource("/connectFour/images/default_red_dot.png")), view);
            PlayerInterface player2 = new GuiPlayer("Mike", new ImageIcon(GameProject.class.getResource("/connectFour/images/default_yellow_dot.png")),view);
            Game game = new Game(player,player2);
            GameController ctrl = new GameController(game);
            
            view.bind(game);
            
        
    }
    
}
