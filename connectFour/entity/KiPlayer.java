/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventListener;
import connectFour.InvalidInputException;
import connectFour.MinMaxKI;
import java.awt.Image;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author mineichen
 */
public class KiPlayer extends AbstractPlayer implements Serializable
{
    private MinMaxKI ki;
    
    public KiPlayer(String name, Image image, MinMaxKI ki) 
    {
        super(name, image);
        this.ki = ki;
    }
    
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        abstractReadObject(in);    
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        abstractWriteObject(out);
    }
    
    public void addEventListener(EventListener<MoveEvent> e) 
    {
        System.out.println("Suggest");
        e.on(new MoveEvent(this, ki.suggestCol(e)));
        System.out.println("EndSuggest");
    }
    
    public void handleError(InvalidInputException e) 
    {
        throw new RuntimeException("Invalid move from KI: " + e.getMessage());
    }
}
