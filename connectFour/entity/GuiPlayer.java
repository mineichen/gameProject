/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

/**
 * Description
 *
 * @author  Jon Buchli
 */
public class GuiPlayer extends PlayerInterface{

    private String name;
    
    public GuiPlayer(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    @Override   // evtl Name aendern
    public void onMove(){
        
    }
    
}
