/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.util.HashSet;

/**
 *
 * @author mineichen
 */
public abstract class PlayerInterface
{
    public abstract void onMove();
    public abstract String getName();
            
    private HashSet<MoveListener> _listener = new HashSet<>() ;
    
    public synchronized void addMoveListener(MoveListener listener)
    {
         _listener.add(listener) ;
    }

    public synchronized void removeMoveListener(MoveListener listener)
    {
         _listener.remove(listener) ;
    }

    private synchronized void fireMoveEvent(int col) {
          MoveEvent moveEvent = new MoveEvent(col, this) ;
          for(MoveListener listener : _listener) {
               listener.movePerformed(moveEvent) ;
          }
    }
}
