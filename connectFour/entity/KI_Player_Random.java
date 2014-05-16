/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.awt.Image;

/**
 *
 * @author mike
 */
public class KI_Player_Random extends AbstractPlayer{

    String name;
    Image icon;
    
    public KI_Player_Random(String name, Image icon){
        this.name = name;
        this.icon = icon;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Image getImage() {
        return icon;
    }
    
}
