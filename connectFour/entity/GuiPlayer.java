/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import javax.swing.ImageIcon;

/**
 * Description
 *
 * @author  Jon Buchli
 */
public class GuiPlayer extends AbstractPlayer {

    private String name;
    private ImageIcon icon;
    
    public GuiPlayer(String name, ImageIcon icon){
        this.name = name;
        this.icon = icon;
    }
    
    public String getName(){
        return name;
    }
    
    @Override   // evtl Name aendern
    public void onMove(){
        
    }
    
    public ImageIcon getIcon()
    {
        return icon;
    }
    
}
