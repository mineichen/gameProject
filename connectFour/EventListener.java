/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour;

public interface EventListener<K> extends java.util.EventListener
{
    public void on(K event);
}
