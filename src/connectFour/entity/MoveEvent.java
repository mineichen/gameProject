/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

/**
 *
 * @author mineichen
 */
public class MoveEvent
{
    private int col;
    private PlayerInterface player;
    public MoveEvent(int col, PlayerInterface player)
    {
        this.col = col;
        this.player = player;
    }
    
    public int getCol()
    {
        return col;
    }
    
    public PlayerInterface getPlayer()
    {
        return player;
    }
}
