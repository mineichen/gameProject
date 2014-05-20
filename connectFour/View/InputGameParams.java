/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.View;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 * Inputs for the game through dialog inputs
 *
 * @author  jonbuc
 */
public class InputGameParams {

    private String namePlayer1;
    private String namePlayer2;
    private int gameRows;
    private int gameCols;
    private Object[] params;
    
    public InputGameParams(){
        setGameParameters();
    }
    
    /**
     * Set the game parameters with dialog inputs
     */
    private void setGameParameters(){
    

        JTextField name1;
        JTextField name2;
        JSpinner rows;
        JSpinner cols;
        int defaultParams;

        defaultParams = JOptionPane.showConfirmDialog(null,
                "Default-Einstellungen?", "Auswahl der Einstellungen", JOptionPane.YES_NO_OPTION);
        if (defaultParams != 0) {
            name1 = new JTextField();
            name2 = new JTextField();
            SpinnerNumberModel model1 = new SpinnerNumberModel(10, 1, 35, 1);  //default value,lower bound,upper bound,increment by
            SpinnerNumberModel model2 = new SpinnerNumberModel(8, 1, 30, 1);
            rows = new JSpinner(model1);
            cols = new JSpinner(model2);
            Object[] message1 = {"Name Player 1", name1,
                "Name Player 2", name2,
                "Anzahl Spalten", rows,
                "Anzahl Zeilen", cols};
            try {
                JOptionPane pane1 = new JOptionPane(message1,
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.OK_CANCEL_OPTION); // OK_CANCEL
            pane1.createDialog(null, "Paramtereingabe").setVisible(true);
            } catch (HeadlessException error) {
                    JOptionPane.showMessageDialog(null,"Something went wrong");
                    namePlayer1 = "Player1";
                    namePlayer2 = "Player2";
                }
            namePlayer1 = name1.getText();
            namePlayer2 = name2.getText();
            gameRows = (Integer)rows.getValue();
            gameCols = (Integer)cols.getValue();
        }
        
        // Default Parameters
        else {
            namePlayer1 = "Player1";
            namePlayer2 = "Player2";
            gameRows = 12;
            gameCols = 8;            
        }
    }
    
    public String getNamePlayer1(){
        return namePlayer1;
    }
    
    public String getNamePlayer2(){
        return namePlayer2;
    }
    
    public int getGameRows(){
        return gameRows;
    }
    
    public int getGameCols(){
        return gameCols;
    }
}
