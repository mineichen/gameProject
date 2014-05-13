/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.View;

import connectFour.entity.Disc;
import java.awt.Canvas;
import connectFour.entity.GameInterface;
import java.awt.Graphics;

/**
 *
 * @author mineichen
 */
public class CanvasPlayGround extends Canvas implements ViewInterface
{
    private GameInterface game;
    public CanvasPlayGround(int width, int height, int margin)
    {
        this.setSize(width, height);
    }
    
    public void bind(GameInterface game)
    {
        this.game = game;
    }
    public void paint(Graphics g)
    {
        this.getWidth();
        for(Disc circle : game.getDiscs()) {
            g.drawOval(WIDTH, WIDTH, WIDTH, WIDTH);
        }
        
    }
    
}