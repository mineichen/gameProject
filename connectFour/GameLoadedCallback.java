/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour;

import connectFour.entity.GameInterface;

/**
 *
 * @author mineichen
 */
public interface GameLoadedCallback
{
    public void onLoad(GameInterface game);
}   
