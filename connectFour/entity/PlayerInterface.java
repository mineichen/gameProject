/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.util.HashSet;
import javax.swing.ImageIcon;

/**
 *
 * @author mineichen
 */
public interface PlayerInterface
{
    public void onMove();
    public String getName();
    public ImageIcon getIcon();        
    public void addMoveListener(MoveEventListener listener);
    public void removeMoveListener(MoveEventListener listener);
}
