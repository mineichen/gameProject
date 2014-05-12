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
public class Game implements GameInterface
{
    private int winNumber;
    private int playerCounter = 0;
    private PlayerInterface[] players;
    private final int cols;
    private final int rows;
    private static final Direction[] checkDirections = {
        Direction.WEST, 
        Direction.SOUTHWEST, 
        Direction.SOUTH, 
        Direction.SOUTHEAST
    };
    /**
     * Disc[col][row]
     * 3 <- rows 
     * 2
     * 1 2 3 <-cols
     */
    private Disc[][] discs;
    
    public Game(PlayerInterface... players)
    {
        this(7, 6, 4, players);
    }
    
    public Game(int cols, int rows, PlayerInterface... players) 
    {
        this(cols, rows, 4, players);
    }
    
    public Game(int cols, int rows, int winNumber, PlayerInterface... players) 
    {
        this.winNumber = winNumber;
        this.cols = cols;
        this.rows = rows;
        this.players = players;
        discs = new Disc[rows][cols];
    }
    public void addDisc(int col)
    {
        int nextRow = calcNextRow(col);
        if(isWinnerMove(col, nextRow)) {
        
        }
    
        discs[col][nextRow] = new Disc(getCurrentPlayer());
        risePlayerCounter();
        
    }
    
    public boolean isWinnerMove(int col) 
    {
        return isWinnerMove(col, calcNextRow(col));
    }
    
    private boolean isWinnerMove(int col, int row) 
    {
        for(Direction dir : checkDirections) {
            if((dir.count(col, row, cols, rows) + dir.getOpposite().count(col, row, cols, rows) + 1) >= winNumber) {
                return true;
            }
        }
        
        return false;
    }
    
    public PlayerInterface getCurrentPlayer()
    {
        return players[playerCounter];
    }
    
    private void risePlayerCounter()
    {
        if(playerCounter == players.length - 1) {
            playerCounter = 0;
        } else {
            playerCounter++;
        }
    }
    
    public int calcNextRow(int col)
    {
        for(int i = 0; i < cols; i++) {
            if(discs[col][i] == null) {
                return i;
            }
        }
        return -1;
    }
    
    protected class Disc
    {
        private PlayerInterface player;
        public Disc(PlayerInterface player) 
        {
            this.player = player;
        }
        
        public boolean isSameTeam(Disc Disc)
        {
            return this.player.equals(Disc.player);
        }
    }
}
