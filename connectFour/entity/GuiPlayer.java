/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import javax.swing.ImageIcon;
import connectFour.EventDispatcher;
import connectFour.EventListener;
import connectFour.InvalidInputException;
import connectFour.View.View;
import java.awt.Image;

/**
 * Description
 *
 * @author  Jon Buchli
 */
public class GuiPlayer extends AbstractPlayer {

    private View view;
    
    public GuiPlayer(String name, Image image, View view)
    {
        super(name, image);
        this.view = view;
    }
    
    @Override
    public void addEventListener(EventListener<MoveEvent> e) {
        view.addEventListener(e);
    }
    @Override
    public void handleError(InvalidInputException e)
    {
        System.out.println(e.getMessage());
    }
    
    @Override
    public void removeEventListener(EventListener<MoveEvent> e)
    {
        view.removeEventListener(e);
    }
}
