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
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;
import java.util.Observable;
import javax.swing.AbstractAction;
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
import connectFour.entity.ButtonClickedListener;
import connectFour.entity.Disc;
import connectFour.entity.DiscMoveEvent;
import connectFour.entity.Game;
import connectFour.entity.GameInterface;
import connectFour.entity.MoveEvent;
/**
 *
 * @author mike
 */
public class View implements ViewInterface, EventListener<DiscMoveEvent> {

    /**
     * The cols of the gameBoard
     */
    private int cols;

    /**
     * The rows of the gameBoard
     */
    private int rows;

    /**
     * This will save the iconsize in pixel and refreshes everytime when the
     * Window is resized
     */
    private int iconsize;

    /**
     * Contains the standardIcon from the gameBoard
     */
    private ImageIcon neutralIcon;

    /**
     * Saves the JLabel to the Positions The Position is saved as String with
     * x-Coord : y-Coord e.g 5:3
     */
    private HashMap<String, JLabel> dots = new HashMap<String, JLabel>();

    /**
     * Contains the originalImages Is used when resizing for
     */
    private HashMap<String, ImageIcon> icons = new HashMap<String, ImageIcon>();

    /**
     * JFrame main Window
     */
    private JFrame mainWindow;

    /**
     * JPanel
     */
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
     * Delay when window is resized until the method repaint is called
     */
    private final int DELAY = 500;

    /**
     * Timer when window is resized
     */
    private Timer waitingTimer;

    /**
     * Constructor
     *
     * @param Game The gameboard
     */
    public View() {
        this.neutralIcon = new ImageIcon(View.class.getResource("/connectFour/images/default_white_dot.png"));
    }

    public void addEventListener(EventListener<MoveEvent> e) {
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
        this.cols = game.getCols();
        this.rows = game.getRows();
        this.game.addEventListener(this);
        initializeSurface();
    }

    /**
     * Draw the Surface of the Game
     */
    public void initializeSurface() {
        mainWindow = new JFrame("FourConnect");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        waitingTimer = new Timer(DELAY, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        waitingTimer.setRepeats(false);

        //repaint when Window is resised and wait for finished resize
        mainWindow.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                if (waitingTimer.isRunning()) {
                    waitingTimer.restart();
                } else {
                    waitingTimer.start();
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });

        mainWindow.setSize(new Dimension(800, 600));

        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("File");
        JMenuItem jmi = new JMenuItem("Close");
        jmb.add(jm);
        jm.add(jmi);

        JPanel mainpanel = new JPanel(new BorderLayout());
        JPanel statustop = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel status = new JLabel("Player 1 has to move");
        statustop.add(status);

        mainpanel.add(statustop, BorderLayout.NORTH);
        mainpanel.add(createGameBoard(), BorderLayout.CENTER);

        mainWindow.add(jmb, BorderLayout.NORTH);
        mainWindow.add(mainpanel, BorderLayout.CENTER);

        JPanel borderLeft = new JPanel();
        JPanel borderRight = new JPanel();
        JPanel borderBottom = new JPanel();
        borderLeft.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        borderRight.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        borderBottom.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        mainWindow.add(borderLeft, BorderLayout.LINE_START);
        mainWindow.add(borderRight, BorderLayout.LINE_END);
        mainWindow.add(borderBottom, BorderLayout.SOUTH);

        loadExistingMoves();

        mainWindow.setVisible(true);

        initFinished = true;

        repaint();
    }

    /**
     * Repaint the Gameboard and recalculate the Icon Size etc
     */
    public void repaint() {
        //repaint only allow if gui is initialised completly
        if (!initFinished) {
            return;
        }
        recalculateIconSizeOnWindowResize();
        for(int i = (rows-1); i>=0; i--){
            for (int j = 0; j < cols; j++) {
                //Make a copy of the Image
                //This is necesarry that the orignial Image is not touched
                ImageIcon img = icons.get(j + ":" + i);
                BufferedImage buImg = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                buImg.getGraphics().drawImage(img.getImage(), 0, 0, img.getImageObserver());
                BufferedImage copyOfImage = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
                Graphics g = copyOfImage.createGraphics();
                g.drawImage(buImg, 0, 0, null);
                //Copy of ImageIcon END

                ImageIcon icon = new ImageIcon(copyOfImage);

                try {
                    icon.setImage(icon.getImage().getScaledInstance(iconsize, iconsize, Image.SCALE_DEFAULT));
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.toString());
                }
                setIconOnLabel(j + ":" + i, icon);
            }
        }
        //Force to repaint everything on the jframe
        mainWindow.repaint();

    }

    /**
     * Will read from Game if already some Disc are in the Board and add it
     */
    private void loadExistingMoves() {
        for(Disc disc : game.getDiscs()) {
            ImageIcon icon = disc.getIcon();
            icons.put(disc.getCol() + ":" + disc.getRow(), icon);
            setIconOnLabel(disc.getCol() + ":" + disc.getRow(), icon);
        }
    }

    /**
     * Set a Icon on a label
     *
     * @param identifier
     * @param icon
     */
    private void setIconOnLabel(String identifier, ImageIcon icon) {
        JLabel label = dots.get(identifier);
        label.setIcon(icon);
    }

    /**
     * Creates the Gameboard with all the Discs
     *
     * @return JPanel with all Elements on it
     */
    private JPanel createGameBoard() {
        gameboardpanel = new JPanel(new GridLayout(rows, cols));
        gameboardpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        for(int i = (rows-1); i>=0; i--){
            for (int j = 0; j < cols; j++) {
                JLabel jlabel = new JLabel("", neutralIcon, JLabel.CENTER);
                gameboardpanel.add(jlabel);
                dots.put(j + ":" + i, jlabel);
                icons.put(j + ":" + i, neutralIcon);
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
    private void recalculateIconSizeOnWindowResize() {
        try {
            iconsize = gameboardpanel.getWidth() / (cols + 1);
            if (iconsize > gameboardpanel.getHeight() / (rows + 1)) {
                iconsize = gameboardpanel.getHeight() / (rows + 1);
            }
        } catch (Exception ex) {
            iconsize = 50;
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
        for(int i = (rows-1); i>=0; i--){
            for (int j = 0; j < cols; j++) {
                if (e.getSource().equals(dots.get(j + ":" + i))) {
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
        ImageIcon icon = disc.getIcon();
        
        icons.put(col+":"+row, icon);
        setIconOnLabel(col+":"+row, icon);
        
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
        addDisc(e.getDisc());
        repaint();
    }
}
