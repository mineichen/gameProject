/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventDispatcher;
import connectFour.EventListener;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author mineichen
 */
public abstract class AbstractPlayer implements PlayerInterface
{
    protected String name;
    private Image image;
    protected EventDispatcher<MoveEvent> dispatcher = new EventDispatcher<>();
    
    public AbstractPlayer()
    {
        
    }
    
    public AbstractPlayer(String name, Image image)
    {
        this.name = name;
        this.image = image;
    }
    
    
    /**
     * Helper Method for all SerializablePlayers
     * 
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    protected void abstractReadObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        name = (String)in.readObject();
        image = ImageIO.read(in);
    }
    
    protected void abstractWriteObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(name);
        ImageIO.write((RenderedImage)image, "PNG", out);    
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
     * set the image of this Player
     *
     * @param img The image of this player
     */
    public void setImage(Image img)
    {
        image = img;
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
    
    public int hashCode()
    {
        return name.hashCode();
    }
    
    public boolean equals(Object o)
    {
        return o != null
            && o.getClass() == this.getClass()
            && ((AbstractPlayer) o).getName().equals(this.name);
    }
}
