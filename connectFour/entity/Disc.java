/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author mineichen
 */
public class Disc
{
    private int col;
    private int row;
    private ImageIcon icon;
    private PlayerInterface player;
    public Disc(PlayerInterface player, int col, int row) 
    {
        this.player = player;
        this.col = col;
        this.row = row;
        //this.icon = new ImageIcon(Game.class.getResource("/connectFour.images/default_red_dot.png"));
    }
    public Disc(PlayerInterface player, int col, int row, ImageIcon icon) 
    {
        this.player = player;
        this.col = col;
        this.row = row;
        this.icon = icon;
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

    public boolean isSameTeam(PlayerInterface player)
    {
        return this.player == player;
    }
}
