/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventListener;
import connectFour.InvalidInputException ;
import connectFour.entity.Disc;
import java.util.List;

/**
 *
 * @author mineichen
 */
public interface GameInterface extends Cloneable
{
    public void addDisc(int col) throws InvalidInputException;
    public Iterable<Disc> getDiscs();
    public boolean isWinnerMove(int col);
    public PlayerInterface getCurrentPlayer();
    public boolean isFromCurrentPlayer(int col, int row);
    public void removeEventListener(EventListener<DiscMoveEvent> e);
    public void addEventListener(EventListener<DiscMoveEvent> e);
    public int getCols();
    public int getRows();
    public int getWinNumber();
    public List<PlayerInterface> getPlayers();
    public Object clone();
    public boolean isSerializable();
}
