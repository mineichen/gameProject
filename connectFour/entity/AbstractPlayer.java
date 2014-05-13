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
    private ArrayList<MoveEventListener> listeners = new ArrayList<>() ;
    
    public synchronized void addMoveListener(MoveEventListener listener)
    {
         listeners.add(listener) ;
    }

    public synchronized void removeMoveListener(MoveEventListener listener)
    {
         listeners.remove(listener) ;
    }

    private synchronized void fireMoveEvent(int col) {
          MoveEvent moveEvent = new MoveEvent(this, col) ;
          for(MoveEventListener listener : listeners) {
               listener.on(moveEvent) ;
          }
    }
}
