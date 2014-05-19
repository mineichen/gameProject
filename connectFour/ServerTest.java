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
import connectFour.entity.NetworkPlayer;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author efux
 */
public class ServerTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            ServerSocket server = new ServerSocket(1111);
            
            View view = new View();
            NetworkPlayer player = new NetworkPlayer(
                "Markus", 
                ImageIO.read(GameProject.class.getResource("/connectFour/images/default_red_dot.png")), 
                server.accept()
            );
            PlayerInterface player2 = new GuiPlayer("Mike", ImageIO.read(GameProject.class.getResource("/connectFour/images/default_yellow_dot.png")),view);
            
            Game game = new Game(30,31, 5,player, player2);
            GameController ctrl = new GameController(game);
            
            view.bind(game);
            player.bind(game);
        } catch(IOException e) {
              System.out.println("Image not found: " + e.getMessage());
        }    
        
    }
    
}
