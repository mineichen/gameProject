/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.util.Observable;

public class GameObservable extends Observable
{
    public void discAdded(Disc disc)
    {
        setChanged();
        notifyObservers(disc);
    }
}
