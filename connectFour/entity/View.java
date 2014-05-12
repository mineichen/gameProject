/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author mike
 */
public class View{
    
    private int cols;
    private int rows;
    private int iconsize;
    private ImageIcon neutralIcon;
    private HashMap<Integer, JButton> buttons = new HashMap<Integer, JButton>();
    private HashMap<Integer[][], JLabel> dots = new HashMap<Integer[][], JLabel>();
    private JFrame mainWindow;
    private JPanel mainpanel;
    private ArrayList<ButtonClickedListener> listeners = new ArrayList<>();
    
    /**
     * Constructor
     * @param Game The gameboard
     */
    public View(){
        this.cols = 7;
        this.rows=6;
        this.neutralIcon = new ImageIcon(View.class.getResource("/connectFour/images/default_white_dot.png"));
        initializeSurface();
    }
    
    //JUST FOR TEST ////////////////////////////////////
    public static void main(String[] arg){
        View view = new View();
    }
    
    /**
     * When a new Disc is added to the gameboard
     * @param disc The Disc
     */
    public void addDisc(Disc disc)
    {
        
    }
    
    public void addListener(ButtonClickedListener listener){
        listeners.add(listener);
    }
    public void removeListener(ButtonClickedListener listener){
        listeners.remove(listener);
    }
    
    public void bind(Game game){
        this.cols = game.getCols();
        this.rows = game.getRows();
        initializeSurface();
    }
    
    /**
     * Draw the Surface of the Game
     */
    public void initializeSurface(){
        mainWindow = new JFrame("FourConnect");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
        
        mainWindow.setSize(new Dimension(600, 400));
        createButtons();
        createGameBoard();
        mainWindow.setVisible(true);
        repaint();
    }
      
    public void repaint(){
        recalculateResize();
        for(JLabel label : dots.values()){    
            neutralIcon.setImage(neutralIcon.getImage().getScaledInstance(iconsize, iconsize, Image.SCALE_DEFAULT));
            //System.out.println("a");
        }
        
    }
    
    private void createGameBoard(){
        mainpanel = new JPanel(new GridLayout(rows, cols));
        for(int i=0; i<rows;i++){
            for(int j=0; j<cols;j++){
                JLabel jlabel = new JLabel("", neutralIcon, JLabel.CENTER);
                mainpanel.add(jlabel);
                dots.put(new Integer[i][j], jlabel);
            }
        }
        mainWindow.add(mainpanel);
    }
    
    public void recalculateResize(){
        iconsize = mainpanel.getWidth()/(cols+1);
        if(iconsize>mainpanel.getHeight()/(rows+1)){
            iconsize=mainpanel.getHeight()/(rows+1);
        }
        System.out.println(iconsize);
    }
    
    private void createButtons(){
        for(int i=0;i<cols; i++){
            JButton button = new JButton();
            buttons.put(i,button);
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(e);
                }
            });
        }
    }
    
    private void buttonClicked(ActionEvent e){
        int buttonClicked = -1;
        for(int i=0;i<cols; i++){
            if(e.getSource().equals(buttons.get(i))){
                buttonClicked = i;
            }
        }
        //Inform all Listeners for the change
        for(ButtonClickedListener listener : listeners){
            listener.buttonClicked(buttonClicked);
        }
    }
    
    /**
     * Repaint the GUi
     */
    public void refresh(){
        
    }
    
    
}
