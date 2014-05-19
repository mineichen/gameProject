/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.View;

import connectFour.EventListener;
import connectFour.entity.GameInterface;
import connectFour.entity.MoveEvent;

/**
 *
 * @author mineichen
 */
public interface ViewInterface  
{
    public void bind(GameInterface game);
    public void addEventListener(EventListener<MoveEvent> e);
    public void removeEventListener(EventListener<MoveEvent> e);
}
