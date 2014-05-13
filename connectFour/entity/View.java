/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectFour.entity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer ;
import java.util.Observable ;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author mike
 */
public class View implements Observer {

    private int cols;
    private int rows;
    private int iconsize;
    private ImageIcon neutralIcon;
    private HashMap<String, JLabel> dots = new HashMap<String, JLabel>();
    private JFrame mainWindow;
    private JPanel gameboardpanel;
    private ArrayList<ButtonClickedListener> listeners = new ArrayList<>();
    private Game game;

    /**
     * Constructor
     *
     * @param Game The gameboard
     */
    public View() {
        this.cols = 7;
        this.rows = 6;
        this.neutralIcon = new ImageIcon(View.class.getResource("/connectFour/images/default_white_dot.png"));
        this.game = null;
    }

    /**
     * When a new Disc is added to the gameboard
     *
     * @param disc The Disc
     */
    public void addDisc(Disc disc) {

    }

    public void addListener(ButtonClickedListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ButtonClickedListener listener) {
        listeners.remove(listener);
    }

    public void bind(Game game) {
        this.game = game;
        this.cols = game.getCols();
        this.rows = game.getRows();
        this.game.getGameObservable().addObserver(this) ;
        initializeSurface();
    }

    /**
     * Draw the Surface of the Game
     */
    public void initializeSurface() {
        mainWindow = new JFrame("FourConnect");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //repaint when Window is resised
        mainWindow.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
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
        repaint();
    }

    public void repaint() {
        recalculateIconSizeOnWindowResize();
        try {
            neutralIcon.setImage(neutralIcon.getImage().getScaledInstance(iconsize, iconsize, Image.SCALE_DEFAULT));
        } catch (Exception ex) {
            System.out.println("Error");
        }
        for (JLabel label : dots.values()) {
            label.setIcon(neutralIcon);
        }

    }

    /**
     * Will read from Game if already some Disc are in the Board and add it
     */
    private void loadExistingMoves() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Disc disc = null;
                try {
                    disc = game.getDisc(j, i);
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.toString());
                }
                if (disc != null) {
                    ImageIcon icon = disc.getIcon();
                    setIconOnLabel(j + ":" + i, icon);
                }
            }
        }
    }

    private void setIconOnLabel(String identifier, ImageIcon icon) {
        JLabel label = dots.get(identifier);
        label.setIcon(icon);
    }

    private JPanel createGameBoard() {
        gameboardpanel = new JPanel(new GridLayout(rows, cols));
        gameboardpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JLabel jlabel = new JLabel("", neutralIcon, JLabel.CENTER);
                gameboardpanel.add(jlabel);
                dots.put(j + ":" + i, jlabel);
                jlabel.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        rowClicked(e);
                    }
                });
            }
        }
        return gameboardpanel;
    }

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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (e.getSource().equals(dots.get(j + ":" + i))) {
                    buttonClicked = j;
                }
            }
        }
        //Inform all Listeners for the change
        for (ButtonClickedListener listener : listeners) {
            listener.buttonClicked(buttonClicked);
        }
    }
   
    /**
     * Is called when a new disc get added to the game board
     *
     * @param Observable usually the an object of GameObservable from a game
     * @param Object the disc added to the game board
     */
    @Override
    public void update(Observable gameObservable, Object d)
    {
        Disc newDisc = (Disc) d ;
    }

}
