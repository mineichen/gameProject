/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventListener;
import java.util.HashSet;
import javax.swing.ImageIcon;

/**
 *
 * @author mineichen
 */
public interface PlayerInterface
{
    public String getName();
    public ImageIcon getIcon();        
    public void addEventListener(EventListener<MoveEvent> e);
    public void removeEventListener(EventListener<MoveEvent> e);
}
