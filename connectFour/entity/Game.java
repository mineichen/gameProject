/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventDispatcher;
import connectFour.EventListener;
import connectFour.InvalidInputException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author mineichen
 */
public class Game implements GameInterface, Serializable, Cloneable
{
    private int playerCounter = 0;
    private final int winNumber;
    private final int cols;
    private final int rows;
    private final int[] colCounter;
    private final PlayerInterface[] players;
    private transient EventDispatcher<DiscMoveEvent> dispatcher;
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

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        dispatcher = new EventDispatcher<>();
    }

    
    public Game()
    {
        this(7, 6, 4, new PlayerInterface[0]); 
    }
    
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
        dispatcher = new EventDispatcher<>();
        this.winNumber = winNumber;
        this.cols = cols;
        this.rows = rows;
        this.players = players;
        discs = new Disc[cols][rows];
        colCounter = new int[cols];
    }
    
    public List<PlayerInterface> getPlayers()
    {
        return Arrays.asList(players);
    }
    
    public void addEventListener(EventListener<DiscMoveEvent> e)
    {
        dispatcher.addEventListener(e);
    }
    
    public boolean isAllowed(int col)
    {
        return colCounter[col] < rows;
    }
    
    @Override
    public void removeEventListener(EventListener<DiscMoveEvent> e)
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
        int nextRow = getNextRow(col);
        boolean winnerMove = isWinnerMove(col, nextRow);
        discs[col][nextRow] = new Disc(getCurrentPlayer(), col, nextRow);
        
        incrementPlayerCounter();
        colCounter[col]++;
        if(dispatcher.hasEventListeners()) {
            dispatcher.dispatch(new DiscMoveEvent(this, discs[col][nextRow], winnerMove));
        }
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
            return isWinnerMove(col, getNextRow(col));
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
    
    public Disc getDisc(int row, int col)
    {
        return discs[col][row];
    }
    
    public PlayerInterface getCurrentPlayer()
    {
        return players[playerCounter];
    }
    
    private void incrementPlayerCounter()
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
    public int getNextRow(int col) throws InvalidInputException
    {
        if(!isAllowed(col)) {
            throw new InvalidInputException();
        }
        
        return colCounter[col];
    }
    
    public int getRows(){
        return rows;
    }
    public int getCols(){
        return cols;
    }
    
    public Object clone()  
    {
        try {
            return super.clone();
        } catch(CloneNotSupportedException e) {
            throw new RuntimeException("Game is not Clonable");
        }
    }
    
    public int getWinNumber(){
        return winNumber;
    }
}
