/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventListener;
import connectFour.InvalidInputException;
import connectFour.View.ViewInterface;
import java.awt.Image;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Description
 *
 * @author  Jon Buchli
 */
public class GuiPlayer extends AbstractPlayer implements Serializable {

    private transient ViewInterface view;
    
    public GuiPlayer()
    {
        super();
    }
    
    public GuiPlayer(String name, Image image)
    {
        super(name, image);
    }
    
    public void bind(ViewInterface view)
    {
        this.view = view;
    }
    
    @Override
    public void addEventListener(EventListener<MoveEvent> e) {
        view.addEventListener(e);
    }
    @Override
    public void handleError(InvalidInputException e)
    {
        System.out.println(e.getMessage());
    }
    
    @Override
    public void removeEventListener(EventListener<MoveEvent> e)
    {
        view.removeEventListener(e);
    }
    
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        abstractReadObject(in);    
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        abstractWriteObject(out);
    }
}
