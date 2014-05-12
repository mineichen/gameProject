/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author mineichen
 */
public abstract class AbstractPlayer implements PlayerInterface
{
    private ArrayList<MoveListener> listener = new ArrayList<>() ;
    
    public synchronized void addMoveListener(MoveListener listener)
    {
         this.listener.add(listener) ;
    }

    public synchronized void removeMoveListener(MoveListener listener)
    {
         this.listener.remove(listener) ;
    }

    private synchronized void fireMoveEvent(int col) {
          MoveEvent moveEvent = new MoveEvent(col, this) ;
          for(MoveListener listener : listener) {
               listener.on(moveEvent) ;
          }
    }
}
