/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.awt.Image;

/**
 *
 * @author mike
 */
public abstract class AbstractKIPlayer extends AbstractPlayer {
    
    protected transient GameInterface game;
    
    public AbstractKIPlayer(String name, Image image){
        super(name, image);
    }
    
    public void bind(GameInterface game){
        this.game = game;
    }
    
}
