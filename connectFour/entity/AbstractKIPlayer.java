/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventListener;
import java.awt.Image;

/**
 *
 * @author mike
 */
public abstract class AbstractKIPlayer extends AbstractPlayer implements EventListener<DiscMoveEvent>{
    
    Game game;
    
    public AbstractKIPlayer(String name, Image image){
        this.name = name;
        this.image = image;
    }
    
    public void bind(Game game){
        this.game = game;
        game.addEventListener(this);
    }

    
}
