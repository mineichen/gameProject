/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Description
 *
 * @author  Jon Buchli
 */
public class GuiPlayer extends PlayerInterface implements ActionListener{

    private String name;
    private Color discColor;
    private View view;
    
    public GuiPlayer(String name, Color discColor, View view){
        this.name = name;
        this.discColor = discColor;
        this.view = view;
        //this.view.addActionListener(this);
    }
    
    public String getName(){
        return name;
    }
    
    public void onMove(){
        
        
    }
    
    public Color getDiscImage(){
        
        return discColor;
    }
    
     public void actionPerformed(ActionEvent e) {
         // get col
         //fireMoveEvent(col);
     }
    
}
