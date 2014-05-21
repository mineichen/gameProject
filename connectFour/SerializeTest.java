/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour;

import connectFour.entity.GuiPlayer;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 *
 * @author mineichen
 */
public class SerializeTest
{
    public static void main(String[] args)
    {
        try {
            GuiPlayer player1 = new GuiPlayer("Markus", ImageIO.read(GameProject.class.getResource("/connectFour/images/default_yellow_dot.png")));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(out);
            
            oout.writeObject(player1);
            oout.flush();
            byte[] data = out.toByteArray();
            
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream oin = new ObjectInputStream(in);
            GuiPlayer player2 = (GuiPlayer) oin.readObject();
            System.out.println(player2.getClass());
            
            JFrame win = new JFrame();
            win.add(new JLabel(new ImageIcon(player2.getImage())));
            win.setVisible(true);
            win.repaint();
        } catch(Exception e) {
            
        }
    }
}
