/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.util.EventObject;

/**
 *
 * @author mineichen
 */
public class DiscMoveEvent extends EventObject 
{
    private Disc disc;
    private boolean winnerMove;
    public DiscMoveEvent(Object dispatcher, Disc disc, boolean winnerMove)
    {
        super(dispatcher);
        this.disc = disc;
        this.winnerMove = winnerMove;
    }
    
    public Disc getDisc()
    {
        return disc;
    }
    
    public boolean isWinnerMove()
    {
        return winnerMove;
    }
}
