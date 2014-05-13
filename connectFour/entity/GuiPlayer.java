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
public class GuiPlayer extends AbstractPlayer implements ButtonClickedListener{

    private String name;
    private ImageIcon icon;
    
    
    public GuiPlayer(String name, ImageIcon icon)
    {
        this.name = name;
        this.icon = icon;
    }
    
    public String getName()
    {
        return name;
    }
    
    public ImageIcon getIcon()
    {
        return icon;
    }
    
    public void buttonClicked(int col)
    {
        dispatcher.dispatch(new MoveEvent(this, col));
    }
}
