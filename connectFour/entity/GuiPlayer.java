/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import javax.swing.ImageIcon;
import connectFour.EventDispatcher;
import connectFour.EventListener;

/**
 * Description
 *
 * @author  Jon Buchli
 */
public class GuiPlayer implements PlayerInterface {

    private String name;
    private ImageIcon icon;
    private EventDispatcher<MoveEvent> dispatcher = new EventDispatcher<>();
    
    public GuiPlayer(String name, ImageIcon icon){
        this.name = name;
        this.icon = icon;
    }
    
    public void addEventListener(EventListener<MoveEvent> e) {
        dispatcher.addEventListener(e);
    }
    
    public void removeEventListener(EventListener<MoveEvent> e)
    {
        dispatcher.removeEventListener(e);
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
