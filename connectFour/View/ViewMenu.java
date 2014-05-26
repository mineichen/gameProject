/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectFour.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

    public ViewMenu(ViewInterface view, MainController mainContr) {
        this.view = view;
        this.mainContr = mainContr;
        this.addFileMenu();
    }

    private void addFileMenu() {

        JMenu menuFile = new JMenu("File");
        JMenuItem filePlayerPlayer = new JMenuItem("New Game Player vs. Player");
        JMenuItem filePlayerKIMike = new JMenuItem("New Game Player vs. KI Mike");
        JMenuItem filePlayerKIKusi = new JMenuItem("New Game Player vs. KI Kusi");
        JMenuItem fileSearchUDPGame = new JMenuItem("Search a network game");
        JMenuItem fileSave = new JMenuItem("Save");
        JMenuItem fileLoad = new JMenuItem("Load");
        JMenuItem fileClose = new JMenuItem("Close");
        menuFile.add(filePlayerPlayer);
        menuFile.add(filePlayerKIMike);
        menuFile.add(filePlayerKIKusi);
        menuFile.add(fileSearchUDPGame);
        menuFile.addSeparator();
        menuFile.add(fileSave);
        menuFile.add(fileLoad);
        menuFile.add(fileClose);

        this.add(menuFile);

        // create the File menu
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
         filePlayerKIKusi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mainContr.newGameKIKusi(view);
                } catch(IOException ioException) {
                    System.out.println("Error loading Images");
                }
            }
        });

        fileSearchUDPGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    mainContr.searchUDPGame(view);
                } catch(IOException ioException) {
                    System.out.println("Error loading Images");
                }
            }
        });

        fileSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    mainContr.saveGame(view.getGame(), view.getMainWindow());
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
                
            }
        });
        fileLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                mainContr.loadGame(view);
            }
        });

        fileClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

}
