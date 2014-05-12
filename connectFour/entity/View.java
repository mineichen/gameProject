/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.entity.Game.Disc;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author mike
 */
public class View {
    
    private int cols;
    private int rows;
    private ImageIcon neutralIcon;
    private HashMap<Integer, JButton> buttons = new HashMap<Integer, JButton>();
    private JFrame mainWindow;
    
    /**
     * Constructor
     * @param Game The gameboard
     */
    public View(){
        this.neutralIcon = new ImageIcon(View.class.getResource("/connectFour/images/default_white_dot.png"));
        initSurface();
    }
    
    //JUST FOR TEST ////////////////////////////////////
    public static void main(String[] arg){
        View view = new View();
    }
    //JUST FOR TESTING GUI//////////////////////////////
    public View(){
        this.cols = 7;
        this.rows=6;
        this.neutralIcon = new ImageIcon(View.class.getResource("/connectFour/images/default_white_dot.png"));
        initSurface();
    }
    
    /**
     * When a new Disc is added to the gameboard
     * @param disc The Disc
     */
    public void addDisc(Disc disc){
        
    }
    
    /**
     * Draw the Surface of the Game
     */
    public void initSurface(){
        mainWindow = new JFrame("FourConnect");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(new Dimension(600, 400));
        //Create Buttons
        createButtons();
        
        mainWindow.setVisible(true);
        
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
        System.out.println("Button clicked: " + buttonClicked);
    }
    
    /**
     * Repaint the GUi
     */
    public void refresh(){
        
    }
    
    
}
