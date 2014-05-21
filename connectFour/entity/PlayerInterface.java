/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventListener;
import connectFour.InvalidInputException;
import java.awt.Image;

/**
 *
 * @author mineichen
 */
public interface PlayerInterface
{
    public String getName();
    public Image getImage();
    public void addEventListener(EventListener<MoveEvent> e);
    public void removeEventListener(EventListener<MoveEvent> e);
    public void handleError(InvalidInputException e);
    public boolean isSameTeam(PlayerInterface player);
}
