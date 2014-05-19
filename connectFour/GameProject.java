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
import connectFour.entity.KI_Player_Random;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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
            View view = new View();
            PlayerInterface player = new GuiPlayer("Markus", ImageIO.read(GameProject.class.getResource("/connectFour/images/default_red_dot.png")), view);
            PlayerInterface player2 = new GuiPlayer("Mike", ImageIO.read(GameProject.class.getResource("/connectFour/images/default_yellow_dot.png")),view);
            
            PlayerInterface playerki = new KI_Player_Random("KI_Random", ImageIO.read(GameProject.class.getResource("/connectFour/images/default_yellow_dot.png")));
            
            Game game = new Game(7,6, 5,player2, player);
            GameController ctrl = new GameController(game);
            
            view.bind(game);
        } catch(IOException e) {
              System.out.println("Image not found: " + e.getMessage());
        }    
        
    }
    
}
