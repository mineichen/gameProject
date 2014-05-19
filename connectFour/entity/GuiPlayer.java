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
import connectFour.View.ViewInterface;
import java.awt.Image;

/**
 * Description
 *
 * @author  Jon Buchli
 */
public class GuiPlayer extends AbstractPlayer {

    private ViewInterface view;
    
    public GuiPlayer(String name, Image image, ViewInterface view)
    {
        super(name, image);
        this.view = view;
    }
    
    @Override
    public void addEventListener(EventListener<MoveEvent> e) {
        view.addEventListener(e);
    }
    
    @Override
    public void removeEventListener(EventListener<MoveEvent> e)
    {
        view.removeEventListener(e);
    }
}
