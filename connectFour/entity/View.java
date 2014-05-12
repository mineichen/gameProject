/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.entity.Game.Disc;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author mike
 */
public class View {
    
    private Game gameboard;
    private int cols;
    private int rows;
    private ImageIcon neutralIcon;
    
    /**
     * Constructor
     * @param player The Players
     */
    public View(Game gameboard){
        this.gameboard = gameboard;
        this.cols = gameboard.getCols();
        this.rows = gameboard.getRows();
        this.neutralIcon = new ImageIcon(View.class.getResource("/connectFour.images/default_white_dot.png"));
        initSurface();
    }
    
    
    public void addDisc(Disc disc){
        
    }
    
    /**
     * Draw the Surface of the Game
     */
    public void initSurface(){
        JFrame mainWindow = new JFrame("FourConnect");
        
        
    }
    
    /**
     * Repaint the GUi
     */
    public void refresh(){
        
    }
    
    
}
