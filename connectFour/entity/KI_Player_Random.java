/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventDispatcher;
import connectFour.EventListener;
import java.awt.Image;

/**
 *
 * @author mike
 */
public class KI_Player_Random implements PlayerInterface{

    String name;
    Image icon;
    protected EventDispatcher<MoveEvent> dispatcher = new EventDispatcher<>();
    
    public KI_Player_Random(String name, Image icon){
        this.name = name;
        this.icon = icon;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Image getImage() {
        return icon;
    }

    @Override
    public void addEventListener(EventListener<MoveEvent> e) {
        dispatcher.addEventListener(e);
    }

    @Override
    public void removeEventListener(EventListener<MoveEvent> e) {
        dispatcher.removeEventListener(e);
    }
    
}
