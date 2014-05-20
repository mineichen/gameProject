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
 * @author mineichen
 */
public abstract class AbstractPlayer implements PlayerInterface
{
    private String name;
    private Image image;
    protected EventDispatcher<MoveEvent> dispatcher = new EventDispatcher<>();
    
    public AbstractPlayer(String name, Image image)
    {
        this.name = name;
        this.image = image;
    }
    
    @Override
    public void addEventListener(EventListener<MoveEvent> e) {
        dispatcher.addEventListener(e);
    }
    
    @Override
    public void removeEventListener(EventListener<MoveEvent> e)
    {
        dispatcher.removeEventListener(e);
    }
    
    public boolean isSameTeam(PlayerInterface player)
    {
        return this.equals(player);
    }
    /**
     * Returns the name of this Player
     * 
     * @return name
     */
    public String getName(){
        return name;
    }
    
    public Image getImage()
    {
        return image;
    }
}
