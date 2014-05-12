/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour;


import java.util.EventObject;
import java.util.LinkedList;

/**
 *
 * @author mineichen
 */
public class EventDispatcher<K extends EventObject> 
{
    private LinkedList<EventListener<K>> listeners = new LinkedList();
    
    public void addEventListener(EventListener<K> event) 
    {
        listeners.add(event);
    }
    
    public void removeEventListener(EventListener<K> event)
    {
        listeners.remove(event);
    }
    
    public void removeAllEventListeners()
    {
        listeners.removeAll(listeners);
    }
    
    public void dispatch(K event) 
    {
        for(EventListener<K> listener : listeners) {
            listener.on(event);
        }
    }
}
