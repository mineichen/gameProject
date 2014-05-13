/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import javax.swing.ImageIcon;
import connectFour.EventDispatcher;
import connectFour.EventListener;
import connectFour.View.View;

/**
 * Description
 *
 * @author  Jon Buchli
 */
public class GuiPlayer extends AbstractPlayer implements ButtonClickedListener{

    private String name;
    private ImageIcon icon;
    
    
    public GuiPlayer(String name, ImageIcon icon, View view)
    {
        this.name = name;
        this.icon = icon;
        view.addListener(this);
        
    }
    
    /**
     * Returns the name of this Player
     * 
     * @return name
     */
    @Override
    public String getName(){
        return name;
    }
    
    /**
     * Returns the set icon of this Player
     * 
     * @return icon
     */
    @Override
    public ImageIcon getIcon()
    {
        return icon;
    }
    
    /**
     * Passes the clicked col to fireMoveEvent
     * 
     * @param col
     */
    @Override
    public void buttonClicked(int col)
    {        
        dispatcher.dispatch(new MoveEvent(this, col));
        
    }
}
