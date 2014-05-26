/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.awt.Image;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author mike
 */
public abstract class AbstractKIPlayer extends AbstractPlayer implements Serializable{
    
    protected GameInterface game;
    
    public AbstractKIPlayer(String name, Image image){
        super(name, image);
    }
    
    public void bind(GameInterface game){
        this.game = game;
    }
    
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        abstractReadObject(in);    
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        abstractWriteObject(out);
    }
}
