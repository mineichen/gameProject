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
    private ArrayList<MoveEventListener> listener = new ArrayList<>() ;
    
    public synchronized void addMoveListener(MoveEventListener listener)
    {
         this.listener.add(listener) ;
    }

    public synchronized void removeMoveListener(MoveEventListener listener)
    {
         this.listener.remove(listener) ;
    }

    private synchronized void fireMoveEvent(int col) {
          MoveEvent moveEvent = new MoveEvent(this, col) ;
          for(MoveEventListener listener : listener) {
               listener.on(moveEvent) ;
          }
    }
}
