/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.InvalidInputException;

/**
 *
 * @author mineichen
 */
public interface GameInterface
{
    public void addDisc(int col) throws InvalidInputException;
    public boolean isWinnerMove(int col);
    public PlayerInterface getCurrentPlayer();
    
}