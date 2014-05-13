/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventDispatcher;
import connectFour.EventListener;
import connectFour.InvalidInputException;
import java.util.LinkedList;
import java.lang.Iterable;

/**
 *
 * @author mineichen
 */
public class Game implements GameInterface
{
    private int playerCounter = 0;
    private final int winNumber;
    private final PlayerInterface[] players;
    private final int cols;
    private final int rows;
    private final EventDispatcher<MoveEvent> dispatcher = new EventDispatcher<>();

    /**
     * Disc[col][row]
     * 3 <- rows 
     * 2
     * 1 2 3 <-cols
     */
    private final Disc[][] discs;
    private static final Direction[] checkDirections = {
        Direction.WEST, 
        Direction.SOUTHWEST, 
        Direction.SOUTH, 
        Direction.SOUTHEAST
    };
    
    
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
    
    public void addEventListener(EventListener<MoveEvent> e)
    {
        dispatcher.addEventListener(e);
    }
    
    @Override
    public void removeEventListener(EventListener<MoveEvent> e)
    {
        dispatcher.removeEventListener(e);
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
    public boolean isFromCurrentPlayer(int col, int row)
    {
        return (cols - col) >= 1 && col >= 0
            && (rows - row) >= 1 && row >= 0
            && discs[col][row] != null
            && discs[col][row].isSameTeam(getCurrentPlayer());
    }
    
    public void addDisc(int col) throws InvalidInputException
    {
        int nextRow = calcNextRow(col);
        
        discs[col][nextRow] = new Disc(getCurrentPlayer(), col, nextRow);
        risePlayerCounter();
    }
    
    
    @Override
    public Iterable<Disc> getDiscs()
    {
        LinkedList<Disc> tmp = new LinkedList<>();
        
        for(Disc[] discCol: discs) {
            for(Disc disc: discCol) {
                if(disc != null) {
                    tmp.add(disc);
                }    
            }
        }
        
        return (Iterable)tmp;
    }
    
    @Override
    public boolean isWinnerMove(int col)
    {
        try {
            return isWinnerMove(col, calcNextRow(col));
        } catch(InvalidInputException e) {
            return false;
        }
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
    private int calcNextRow(int col) throws InvalidInputException
    {
        for(int i = 0; i < cols; i++) {
            if(discs[col][i] == null) {
                return i;
            }
        }
        throw new InvalidInputException();
    }
    
    public int getRows(){
        return rows;
    }
    public int getCols(){
        return cols;
    }
}
