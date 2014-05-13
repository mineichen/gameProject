/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventDispatcher;
import connectFour.EventListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author mineichen
 */
public abstract class AbstractPlayer implements PlayerInterface
{
    private EventDispatcher<MoveEvent> dispatcher = new EventDispatcher<>();
    
    @Override
    public void addEventListener(EventListener<MoveEvent> e) {
        dispatcher.addEventListener(e);
    }
    
    @Override
    public void removeEventListener(EventListener<MoveEvent> e)
    {
        dispatcher.removeEventListener(e);
    }
}