/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventDispatcher;
import connectFour.EventListener;
import java.awt.Image;

/**
 *
 * @author mike
 */
public class KIPlayerMike extends AbstractKIPlayer{
    
    PlayerInterface grid[][];
    
    public KIPlayerMike(String name, Image image){
        super(name,image);
    }

    @Override
    public void addEventListener(EventListener<MoveEvent> e) {
        super.addEventListener(e);
        
        grid = new PlayerInterface[game.getCols()][game.getRows()];
        reloadGameBoard();
        
        //Check for Winnermove
        for(int i=0; i<game.getCols(); i++){
            if(game.isWinnerMove(i)){
                dispatcher.dispatch(new MoveEvent(game.getCurrentPlayer(), i));
            }
        }
        
        for(int i = 0;i<game.getCols();i++){
            for (int j = 0; j < game.getRows(); j++) {
                if(grid[i][j]!=null && grid[i][j]!=game.getCurrentPlayer()){
                    System.out.println("Check " + i +" : "+j);
                    int[] check = isPossibleWinnerMove(i, j);
                    if(check[0]!=-1){
                        System.out.println("zug verhindern");
                    }
                }
            }
        }        
        //Random
        int randomCol = getRandomCol(game.getCols());
        dispatcher.dispatch(new MoveEvent(game.getCurrentPlayer(), randomCol));
    }
    
    /**
     * 
     * @param col
     * @param row
     * @return int Array with 2 Values, if value is [-1][-1] there is no possible winnermove, else there are the coordinates to set next disc
     */
    private int[] isPossibleWinnerMove(int col, int row){
        int winNumber = game.getWinNumber();
        for(int i = 0;i<winNumber-1;i++){
            /////tbd
            if(grid[col][row]!=null  ){
                
            }
        }
        
        return new int[]{-1,-1};
    }
    
    private boolean checkLeftSamePlayer(int startCol, int startRow, int numberoflines){
        if((startCol-numberoflines)<0){
             return false;
        }
        for(int i = 0; i<numberoflines; i++){
            if(grid[startCol][startRow] != grid[startCol][startRow-i]){
                return false;
            }
        }
        return true;
    }
    private boolean checkTopLeftSamePlayer(int startCol, int startRow, int numberoflines){
        if((startCol-numberoflines)<0 || (startRow-numberoflines)<(game.getRows()-1)){
             return false;
        }
        for(int i = 0; i<numberoflines; i++){
            if(grid[startCol][startRow] != grid[startCol-i][startRow-i]){
                return false;
            }
        }
        return true;
    }
    private boolean checkTopSamePlayer(int startCol, int startRow, int numberoflines){
        if((startRow+numberoflines)<(game.getRows()-1)){
             return false;
        }
        for(int i = 0; i<numberoflines; i++){
            if(grid[startCol][startRow] != grid[startCol-i][startRow-i]){
                return false;
            }
        }
        return true;
    }
    private boolean checkTopRightSamePlayer(int startCol, int startRow, int numberoflines){
        if((startCol+numberoflines)<(game.getCols()-1) || (startRow+numberoflines)<(game.getRows()-1)){
             return false;
        }
        for(int i = 0; i<numberoflines; i++){
            if(grid[startCol][startRow] != grid[startCol+i][startRow+i]){
                return false;
            }
        }
        return true;
    }
    private boolean checkRightSamePlayer(int startCol, int startRow, int numberoflines){
        if((startCol+numberoflines)<(game.getRows()-1)){
             return false;
        }
        for(int i = 0; i<numberoflines; i++){
            if(grid[startCol][startRow] != grid[startCol+i][startRow]){
                return false;
            }
        }
        return true;
    }
    
    private int getRandomCol(int maxCol){
        return (int)(Math.random()*(maxCol-1));
    }
    
    private void reloadGameBoard(){
        for(Disc disc : game.getDiscs()){
            grid[disc.getCol()][disc.getRow()] = disc.getPlayer();
        }
    }
        
    
    
}
