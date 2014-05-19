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
import connectFour.entity.KI_Player_Random;
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
 * Description
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
        //makeMenuBar(frame);
    }

    private void addFileMenu() {

        JMenu menuFile = new JMenu("File");
        JMenuItem filePlayerPlayer = new JMenuItem("New Game Player vs. Player");
        JMenuItem fileFindNetwork = new JMenuItem("Find Network-Player");
        JMenuItem fileSave = new JMenuItem("Save");
        JMenuItem fileClose = new JMenuItem("Close");
        menuFile.add(filePlayerPlayer);
        menuFile.add(fileFindNetwork);
        menuFile.addSeparator();
        menuFile.add(fileSave);
        menuFile.add(fileClose);

        this.add(menuFile);

        // create the File menu
        filePlayerPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // MainController Methode
                try {
                    mainContr.newGameTwoGuiPlayers(view);
                } catch(IOException ioException) {
                    System.out.println("Error loading Images");
                }
                
            }
        });

        fileFindNetwork.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

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

    /**
     * Create the main frame's menu bar.
     *
     * @param frame The frame that the menu bar should be added to.
     */
    private void makeMenuBar(JFrame frame) {

        
    }
}
