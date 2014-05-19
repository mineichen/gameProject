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
public class KIPlayer extends AbstractKIPlayer{
    
    private EventDispatcher<MoveEvent> dispatcher = new EventDispatcher<>();
    
    public KIPlayer(String name, Image image){
        super(name,image);
    }

    @Override
    public void on(DiscMoveEvent event) {
        int randomCol = getRandomCol(game.getCols());
        
        if(event.getDisc().getPlayer()!=this){
            dispatcher.dispatch(new MoveEvent(game.getCurrentPlayer(), randomCol));
        }
    }
    
    public int getRandomCol(int maxCol){
        return (int)(Math.random()*(maxCol-1));
    }
    
    
    
}
