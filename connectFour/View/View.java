/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectFour.View;

import connectFour.EventDispatcher;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import connectFour.EventListener;
import connectFour.GameProject;
import connectFour.entity.Disc;
import connectFour.entity.DiscMoveEvent;
import connectFour.entity.GameInterface;
import connectFour.entity.MoveEvent;
import java.awt.event.ComponentAdapter;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author mike
 */
public class View implements ViewInterface, EventListener<DiscMoveEvent> {

    /**
     * Contains the standardIcon from the gameBoard
     */
    private ImageIcon neutralIcon = new ImageIcon();
    private Image neutralImage;
    private JLabel[][] dots;
    private JFrame mainWindow = new JFrame("FourConnect");
    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JPanel gameboardpanel;
    private EventDispatcher<MoveEvent> dispatcher = new EventDispatcher<>();

    /**
     * Game
     */
    private GameInterface game;

    /**
     * Will be set to true as soon as everything is initialised Is needed for
     * make sure that repaint is not called before everything is initialised
     */
    private boolean initFinished = false;

    /**
     * Status which player has to move
     */
    private JLabel status = new JLabel();
    
    
    public View() 
    {
        try {
            neutralImage = ImageIO.read(GameProject.class.getResource("/connectFour/images/default_white_dot.png"));
        } catch(IOException e) {
            System.out.println(e.getMessage()); 
            System.exit(0);
        }
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(new Dimension(800, 600));
        mainWindow.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });

        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("File");
        JMenuItem jmi = new JMenuItem("Close");
        jmb.add(jm);
        jm.add(jmi);

        JPanel statustop = new JPanel(new FlowLayout(FlowLayout.LEFT));

        statustop.add(status);

        mainPanel.add(statustop, BorderLayout.NORTH);
        
        mainWindow.add(jmb, BorderLayout.NORTH);
        mainWindow.add(mainPanel, BorderLayout.CENTER);

        JPanel borderLeft = new JPanel();
        JPanel borderRight = new JPanel();
        JPanel borderBottom = new JPanel();
        borderLeft.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        borderRight.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        borderBottom.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        mainWindow.add(borderLeft, BorderLayout.LINE_START);
        mainWindow.add(borderRight, BorderLayout.LINE_END);
        mainWindow.add(borderBottom, BorderLayout.SOUTH);
    }
    
    public void addEventListener(EventListener<MoveEvent> e) 
    {
        dispatcher.addEventListener(e);
    }
    
    public void removeEventListener(EventListener<MoveEvent> e)
    {
        dispatcher.removeEventListener(e);
    }

    /**
     * Bind the game to the View Without this call the View cant be initialised
     *
     * @param game
     */
    public void bind(GameInterface game) {
        this.game = game;
        this.game.addEventListener(this);
        dots = new JLabel[game.getCols()][game.getRows()];
        mainPanel.add(createGameBoard(), BorderLayout.CENTER);
        
        for(Disc disc : game.getDiscs()) {
            this.addDisc(disc);
        }

        mainWindow.setVisible(true);

        initFinished = true;

        repaint();
    }

    /**
     * Repaint the Gameboard and recalculate the Icon Size etc
     */
    private void repaint() 
    {
        int iconsize = getIconSize(); 
        
        neutralIcon.setImage(neutralImage.getScaledInstance(iconsize, iconsize, Image.SCALE_SMOOTH));
        for(Disc disc : game.getDiscs()) {
            dots[disc.getCol()][disc.getRow()].setIcon(new ImageIcon(
                disc.getImage().getScaledInstance(iconsize, iconsize, Image.SCALE_SMOOTH)
            ));
        }
        
        status.setText("Player " + game.getCurrentPlayer().getName() + " has to move");
        //Force to repaint everything on the jframe
        mainWindow.repaint();
    }

    
    

    /**
     * Creates the Gameboard with all the Discs
     *
     * @return JPanel with all Elements on it
     */
    private JPanel createGameBoard() {
        gameboardpanel = new JPanel(new GridLayout(game.getRows(), game.getCols()));
        gameboardpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        for(int i = (game.getRows()-1); i>=0; i--){
            for (int j = 0; j < game.getCols(); j++) {
                JLabel jlabel = new JLabel("", neutralIcon, JLabel.CENTER);
                gameboardpanel.add(jlabel);
                dots[j][i] = jlabel;
                jlabel.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        rowClicked(e);
                    }
                });
            }
        }
        return gameboardpanel;
    }

    /**
     * Is called when the Window is resized. This will recalculate the size of
     * the Icons
     */
    private int getIconSize() {
        if(gameboardpanel.getWidth() >= gameboardpanel.getHeight()) {
            return gameboardpanel.getHeight() / (game.getRows() + 1);
        } else {
            return gameboardpanel.getWidth() / (game.getCols() + 1);
        }
    }

    /**
     * Is called when a Disc in a Row is clicked. It will inform all registered
     * Listeners which row was clicked. First row is 0, second 1 etc.
     *
     * @param e MouseEvent
     */
    private void rowClicked(MouseEvent e) {
        int buttonClicked = -1;
        for(int i = (game.getRows()-1); i>=0; i--){
            for (int j = 0; j < game.getCols(); j++) {
                if (e.getSource().equals(dots[j][i])) {
                    buttonClicked = j;
                }
            }
        }
        //Inform all Listeners for the change
        
        dispatcher.dispatch(new MoveEvent(game.getCurrentPlayer(), buttonClicked));
    }
    
    /**
     * Add the Disc to the gameboard
     * @param disc 
     */
    private void addDisc(Disc disc){
        int row = disc.getRow();
        int col = disc.getCol();
        ImageIcon icon = new ImageIcon(disc.getImage());
        dots[col][row].setIcon(icon);
    }
    
    
    /**
     * Is called when a new disc get added to the game board
     *
     * @param Observable usually the an object of GameObservable from a game
     * @param Object the disc added to the game board
     */
    @Override
    public void on(DiscMoveEvent e)
    {
        System.out.println("Is Winner Move: " + ((e.isWinnerMove()) ? "yes" : "no"));
        addDisc(e.getDisc());
        repaint();
    }
}
