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
    
    /**
     * Checks, weather field is from a specific Player
     * 
     * @param player
     * @param col
     * @param row
     * @return 
     */
    @Override
    public boolean isFromPlayer(PlayerInterface player, int col, int row)
    {
        System.out.println("New Pos: " + (cols - col) + " row: " + (rows - row) + "Existance" + ((discs[col][row] != null) ? "yes" : "no"));
        
        return (cols - col) >= 1 
            && (rows - row) >= 1
            && discs[col][row] != null
            && discs[col][row].isSameTeam(player);
    }
    
    public void addDisc(int col)
    {
        int nextRow = calcNextRow(col);
        if(isWinnerMove(col, nextRow)) {
        
        }
        System.out.println("Add Disc: " + col + ":" + nextRow);
        discs[col][nextRow] = new Disc(getCurrentPlayer(), col, nextRow);
        risePlayerCounter();
    }
    
    
    
    public boolean isWinnerMove(int col) 
    {
        return isWinnerMove(col, calcNextRow(col));
    }
    
    private boolean isWinnerMove(int col, int row) 
    {
        for(Direction dir : checkDirections) {
            if((dir.count(this, col, row) + dir.getOpposite().count(this, col, row) + 1) >= winNumber) {
                return true;
            }
        }
        
        return false;
    }
    
    private Disc getDisc(int row, int col)
    {
        return discs[col][row];
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
    
    /**
     * Calculates the next row in respect to all currently
     * set Discs
     * 
     * @param col
     * @return nextRow
     */
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
        private int col;
        private int row;
        private PlayerInterface player;
        public Disc(PlayerInterface player, int col, int row) 
        {
            this.player = player;
            this.col = col;
            this.row = row;
        }
        
        
        public boolean isSameTeam(PlayerInterface player)
        {
            System.out.println("Is Same Player Check");
            return this.player.equals(player);
        }
    }
}
