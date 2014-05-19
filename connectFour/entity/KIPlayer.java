/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventDispatcher;
import connectFour.EventListener;
import connectFour.InvalidInputException;
import java.awt.Image;

/**
 *
 * @author mike
 */
public class KIPlayer extends AbstractKIPlayer{
    
    public KIPlayer(String name, Image image){
        super(name,image);
    }

    @Override
    public void addEventListener(EventListener<MoveEvent> e) {
        super.addEventListener(e);
        int randomCol = getRandomCol(game.getCols());
        dispatcher.dispatch(new MoveEvent(game.getCurrentPlayer(), randomCol));
    }
    
    public int getRandomCol(int maxCol){
        return (int)(Math.random()*(maxCol-1));
    }
    @Override
    public void handleError(InvalidInputException e)
    {
        
    }
    
}
