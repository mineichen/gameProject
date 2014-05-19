/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectFour.entity;

import connectFour.EventListener;
import connectFour.InvalidInputException;
import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author mike
 */
public class KIPlayerMike extends AbstractKIPlayer {

    //Intern Data structur of the KI
    private PlayerInterface grid[][];
    //Saves a list of cols where the KI is forbidden to add a Stone
    private ArrayList<Integer> forbiddenMovesCol = new ArrayList<>();

    /**
     * Constructor
     * @param name
     * @param image 
     */
    public KIPlayerMike(String name, Image image) {
        super(name, image);
    }

    /**
     * When the KI has to move this Method is called
     * @param e 
     */
    @Override
    public void addEventListener(EventListener<MoveEvent> e) {
        super.addEventListener(e);
        makeMove();
    }

    /**
     * Make the Next move
     */
    private void makeMove() {
        grid = new PlayerInterface[game.getCols()][game.getRows()];
        reloadGameBoard();

        //Check for Winnermove
        for (int i = 0; i < game.getCols(); i++) {
            if (game.isWinnerMove(i)) {
                dispatcher.dispatch(new MoveEvent(game.getCurrentPlayer(), i));
            }
        }

        forbiddenMovesCol.clear();

        for(int x=0;x<3;x++){
            for (int i = 0; i < game.getCols(); i++) {
                for (int j = 0; j < game.getRows(); j++) {
                    if (grid[i][j] != null && grid[i][j] != game.getCurrentPlayer()) {  
                        int check = -1;
                        switch(x){
                            case 0:
                                check = isPossibleWinnerMoveImportant(i, j);
                                break;
                            case 1:
                                check = isPossibleWinnerMoveNormal(i, j);
                                break;
                            case 2:
                                check = isPossibleWinnerMoveLowPrio(i, j);
                                break;
                        }
                        if (check != -1) {
                            if (!isForbiddenMove(check)) {
                                dispatcher.dispatch(new MoveEvent(game.getCurrentPlayer(), check));
                            }
                        }
                    }
                }
            }
        }
        //Random
        int counter = 0; //Make sure if every Column is forbidden that after 999 tries a random will be called
        int randomCol = getRandomCol(game.getCols());
        while (isForbiddenMove(randomCol) && counter < 999) {
            randomCol = getRandomCol(game.getCols());
        }
        dispatcher.dispatch(new MoveEvent(game.getCurrentPlayer(), randomCol));
    }

    /**
     * Checks before the KI adds the Disc if this Col is forbidden
     * @param col The Column
     * @return true when move is Forbidden, false if not
     */
    private boolean isForbiddenMove(int col) {
        for (int forbiddenCol : forbiddenMovesCol) {
            if (col == forbiddenCol) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks possible Moves to avoid very early strategys
     * @param col
     * @param row
     * @return int when its -1 no special move is recommended, else the row is given where the next move should be done
     */
    private int isPossibleWinnerMoveLowPrio(int col, int row){
        //if 1 stone 1 empty 1 stone
        if((col+2)<=(game.getCols()-1)){
            if(grid[col+2][row]!=null && grid[col+1][row]==null){
                return col+1;
            }
        }
        if((col-2)>=0){
            if(grid[col-2][row]!=null && grid[col-1][row]==null){
                return col-1;
            }
        }
        return -1;
    }
    
    /**
     * Checks possible Moves to avoid to going in to a bad situation like the other player can win with 2 different moves
     * @param col
     * @param row
     * @return int when its -1 no special move is recommended, else the row is given where the next move should be done
     */
    private int isPossibleWinnerMoveNormal(int col, int row) {
        int winNumber = game.getWinNumber();
        //KI works only if there are at minimun 3 stones are needed to win
        if (winNumber < 3) {
            return -1;
        }
        //Check left but with 2 stones less then winNumber
        if (checkLeftSamePlayer(col, row, winNumber - 2)) {
            if (grid[col - (winNumber - 2)][row] == null
                    && (col - (winNumber - 2)) >= 0
                    && (col + 1) <= (game.getCols() - 1)
                    && grid[col + 1][row] == null) {
                //Check first row
                if (row == 0) {
                    return col - (winNumber - 2);
                } else if (row >= 1) {
                    if (grid[col - (winNumber - 2)][row - 1] != null) {
                        return col - (winNumber - 2);
                    }
                }
            }
        }
        
        //Check topleft but with 2 stones less then winNumber
        if(checkTopLeftSamePlayer(col, row, winNumber-2)){
            if((col-(winNumber-2))>=0 &&
                    (row+(winNumber-2))<=(game.getRows()-1) &&
                    (grid[col-(winNumber-2)][row+(winNumber-2)]) == null){
                if((grid[col-(winNumber-2)][row+(winNumber-1-2)]) != null){
                    return col - (winNumber-2);
                }
                
            }
        }
        
        return -1;
    }
    
    /**
     *
     * @param col The Col
     * @param row The Row
     * @return int when its -1 no special move is recommended, else the row is given where the next move should be done
     */
    private int isPossibleWinnerMoveImportant(int col, int row) {
        //For better understanding of the Comments the Comments are based of that you need 4 stones in a row to win
        int winNumber = game.getWinNumber();
        //KI works only if there are at minimun 3 stones are needed to win
        if (winNumber < 3) {
            return -1;
        }
        
        //Check if 2 stones 1 not and then another
        if(checkLeftSamePlayer(col, row, winNumber-2)){
            if((col-(winNumber-2)-2)>=0){
                if(grid[(col-(winNumber-2)-1)][row]!=null && grid[(col-(winNumber-2))][row]==null){
                    return col-(winNumber-2);
                }
            }
        }
         if(checkRightSamePlayer(col, row, winNumber-2)){
            if((col+(winNumber-2)+2)<=(game.getCols()-1)){
                if(grid[(col+(winNumber-2)+1)][row]!=null && grid[(col+(winNumber-2))][row]==null){
                    return col+(winNumber-2);
                }
            }
        }
        
        //Check left
        if (checkLeftSamePlayer(col, row, winNumber - 1)) {
            if (grid[col - (winNumber - 1)][row] == null
                    && (col - (winNumber - 1)) >= 0) {
                //Check first row
                if (row == 0) {
                    return col - (winNumber - 1);
                } else if (row >= 1) {
                    //When stone is left underneath set one on top
                    if (grid[col - (winNumber - 1)][row - 1] != null) {
                        return col - (winNumber - 1);
                    } else if (row >= 2 && grid[col - (winNumber - 1)][row - 2] != null) {
                        //Forbid move when there are 2 stone needed to win because if you set one here
                        //the opposite player can win if he set in the same col
                        forbiddenMovesCol.add(col - (winNumber - 1));

                    }
                }
            }
        }
        //Check TopLeft
        if (checkTopLeftSamePlayer(col, row, winNumber - 1)) {
            if (grid[col - (winNumber - 1)][row + (winNumber - 1)] == null
                    && (col - (winNumber - 1)) >= 0
                    && (row + (winNumber - 1)) < game.getRows()) {
                if (grid[col - (winNumber - 1)][row + (winNumber - 1 - 1)] != null) {
                    return col - (winNumber - 1);
                } else if (grid[col - (winNumber - 1)][row + (winNumber - 1 - 2)] != null) {
                    //Forbid move when there are 2 stone needed to win because if you set one here
                    //the opposite player can win if he set in the same col
                    forbiddenMovesCol.add(col - (winNumber - 1));
                }
            }
        }
        //Check Top
        if (checkTopSamePlayer(col, row, winNumber - 1)) {
            if (grid[col][row + (winNumber - 1)] == null
                    && (row + (winNumber - 1)) < (game.getRows() - 1)) {
                return col;
            }
        }
        //Check TopRight
        if (checkTopRightSamePlayer(col, row, winNumber - 1)) {
            if (grid[col + (winNumber - 1)][row + (winNumber - 1)] == null
                    && (col + (winNumber - 1)) >= 0
                    && (row + (winNumber - 1)) < (game.getRows() - 1)) {
                if (grid[col + (winNumber - 1)][row + (winNumber - 1 - 1)] != null) {
                    return col + (winNumber - 1);
                } else if (grid[col + (winNumber - 1)][row + (winNumber - 1 - 2)] != null) {
                    //Forbid move when there are 2 stone needed to win because if you set one here
                    //the opposite player can win if he set in the same col
                    forbiddenMovesCol.add(col + (winNumber - 1));
                }
            }
        }
        //CheckRight
        if (checkRightSamePlayer(col, row, winNumber - 1)) {
            if (grid[col + (winNumber - 1)][row] == null
                    && (col + (winNumber - 1)) <= (game.getCols() - 1)) {
                //Check first row
                if (row == 0) {
                    return col + (winNumber - 1);
                } else if (row >= 1) {
                    //When stone is left underneath set one on top
                    if (grid[col + (winNumber - 1)][row - 1] != null) {
                        return col + (winNumber - 1);
                    } else if (row >= 2 && grid[col + (winNumber - 1)][row - 2] != null) {
                        //Forbid move when there are 2 stone needed to win because if you set one here
                        //the opposite player can win if he set in the same col
                        forbiddenMovesCol.add(col + (winNumber - 1));

                    }
                }
            }
        }

        return -1;
    }

    /**
     * Checks from the given Position to the direction if the amount of numberoflines are nearby
     * @param startCol The Col
     * @param startRow The Row
     * @param numberoflines moves to check
     * @return true, if they are nearby, false if not
     */
    private boolean checkLeftSamePlayer(int startCol, int startRow, int numberoflines) {
        if ((startCol - numberoflines) < 0) {
            return false;
        }
        for (int i = 0; i < numberoflines; i++) {
            if (grid[startCol][startRow] != grid[startCol - i][startRow]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks from the given Position to the direction if the amount of numberoflines are nearby
     * @param startCol The Col
     * @param startRow The Row
     * @param numberoflines moves to check
     * @return true, if they are nearby, false if not
     */
    private boolean checkTopLeftSamePlayer(int startCol, int startRow, int numberoflines) {
        if ((startCol - numberoflines) < 0 || (startRow + numberoflines) > (game.getRows() - 1)) {
            return false;
        }
        for (int i = 0; i < numberoflines; i++) {
            if (grid[startCol][startRow] != grid[startCol - i][startRow + i]) {
                return false;
            }
        }
        return true;
    }

    
    /**
     * Checks from the given Position to the direction if the amount of numberoflines are nearby
     * @param startCol The Col
     * @param startRow The Row
     * @param numberoflines moves to check
     * @return true, if they are nearby, false if not
     */
    private boolean checkTopSamePlayer(int startCol, int startRow, int numberoflines) {
        if ((startRow + numberoflines) > (game.getRows() - 1)) {
            return false;
        }
        for (int i = 0; i < numberoflines; i++) {
            if (grid[startCol][startRow] != grid[startCol][startRow + i]) {
                return false;
            }
        }
        return true;
    }

    
    /**
     * Checks from the given Position to the direction if the amount of numberoflines are nearby
     * @param startCol The Col
     * @param startRow The Row
     * @param numberoflines moves to check
     * @return true, if they are nearby, false if not
     */
    private boolean checkTopRightSamePlayer(int startCol, int startRow, int numberoflines) {
        if ((startCol + numberoflines) > (game.getCols() - 1) || (startRow + numberoflines) > (game.getRows() - 1)) {
            return false;
        }
        for (int i = 0; i < numberoflines; i++) {
            if (grid[startCol][startRow] != grid[startCol + i][startRow + i]) {
                return false;
            }
        }
        return true;
    }

    
    /**
     * Checks from the given Position to the direction if the amount of numberoflines are nearby
     * @param startCol The Col
     * @param startRow The Row
     * @param numberoflines moves to check
     * @return true, if they are nearby, false if not
     */
    private boolean checkRightSamePlayer(int startCol, int startRow, int numberoflines) {
        if ((startCol + numberoflines) > (game.getCols() - 1)) {
            return false;
        }
        for (int i = 0; i < numberoflines; i++) {
            if (grid[startCol][startRow] != grid[startCol + i][startRow]) {
                return false;
            }
        }
        return true;
    }

    
    /**
     * Return a Random number between 0 and maxCol
     * @param maxCol
     * @return Random Number between 0 and maxCol
     */
    private int getRandomCol(int maxCol) {
        return (int) (Math.random() * (maxCol - 1));
    }

    /**
     * Loads all the disc to the intern datastructure
     */
    private void reloadGameBoard() {
        for (Disc disc : game.getDiscs()) {
            grid[disc.getCol()][disc.getRow()] = disc.getPlayer();
        }
    }

    /**
     * Is called when a Error occured by adding a disc
     * This will just recall the makeMove method
     * @param e 
     */
    @Override
    public void handleError(InvalidInputException e) {
        makeMove();
        //throw new RuntimeException("KI Player performed forbidden move");
        
    }

}
