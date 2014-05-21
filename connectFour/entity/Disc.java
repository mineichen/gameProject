/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author mineichen
 */
public class Disc implements Serializable
{
    private int col;
    private int row;
    private PlayerInterface player;
    public Disc(PlayerInterface player, int col, int row) 
    {
        this.player = player;
        this.col = col;
        this.row = row;
    }
    
    public int getCol(){
        return col;
    }
    
    public int getRow(){
        return row;
    }
    
    public Image getImage()
    {
        return player.getImage();
    }

    public PlayerInterface getPlayer()
    {
        return player;
    }

    public boolean isSameTeam(PlayerInterface player)
    {
        return this.player.isSameTeam(player);
    }
}
