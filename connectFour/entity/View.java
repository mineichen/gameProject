/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.entity.Game.Disc;
import javax.swing.JFrame;

/**
 *
 * @author mike
 */
public class View {
    
    private Game gameboard;
    
    /**
     * Constructor
     * @param player The Players
     */
    public View(){
        drawSurface();
    }
    
    public static void main(String args[]){
        
    }
    
    public void addDisc(Disc disc){
        
    }
    
    /**
     * Draw the Surface of the Game
     */
    public void drawSurface(){
        JFrame mainWindow = new JFrame("FourConnect");
        
        
    }
    
    /**
     * Repaint the GUi
     */
    public void refresh(){
        
    }
    
    
}
