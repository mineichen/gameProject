/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectFour.View;

import connectFour.GameProject;
import connectFour.entity.Game;
import connectFour.entity.GameController;
import connectFour.entity.GuiPlayer;
import connectFour.entity.PlayerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Menu bar in main window
 *
 * @author jonbuc
 */
public class ViewMenu extends JMenuBar {

    private ViewInterface view;
    private final MainController mainContr;

    public ViewMenu(ViewInterface view,MainController mainContr) {
        this.view = view;
        this.mainContr = mainContr;
        this.addFileMenu();
    }

    private void addFileMenu() {

        JMenu menuFile = new JMenu("File");
        JMenuItem filePlayerPlayer = new JMenuItem("New Game Player vs. Player");
        JMenuItem filePlayerKIMike = new JMenuItem("New Game Player vs. KI Mike");
        JMenuItem fileFindNetwork = new JMenuItem("Find Network-Player");
        JMenuItem fileConnectNetworkGame = new JMenuItem("Connect Network Game");
        JMenuItem fileSave = new JMenuItem("Save");
        JMenuItem fileClose = new JMenuItem("Close");
        menuFile.add(filePlayerPlayer);
        menuFile.add(filePlayerKIMike);
        menuFile.add(fileFindNetwork);
        menuFile.add(fileConnectNetworkGame);
        menuFile.addSeparator();
        menuFile.add(fileSave);
        menuFile.add(fileClose);

        this.add(menuFile);

        // Create a game for the server to play over network
        filePlayerPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mainContr.newGameTwoGuiPlayers(view);
                } catch(IOException ioException) {
                    System.out.println("Error loading Images");
                }
            }
        });
        
         filePlayerKIMike.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mainContr.newGameKIMike(view);
                } catch(IOException ioException) {
                    System.out.println("Error loading Images");
                }
            }
        });

        fileFindNetwork.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    mainContr.findNetworkPlayer(view);                    
                } catch(IOException ioException) {
                    System.out.println("Error loading Images");
                }
            }
        });
        
        // Create a game for the client to play over network
        fileConnectNetworkGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    mainContr.connectNetworkGame(view);
                } catch(IOException ioException) {
                    System.out.println("Error loading Images");
                }
            }
        });
        
        
        fileSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        fileClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
