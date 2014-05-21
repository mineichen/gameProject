/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour;

import connectFour.entity.GameInterface;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.swing.JFileChooser;

/**
 *
 * @author mineichen
 */
public class GameBackend
{
    public void save(GameInterface game, Component component)
    {
        new SaveThread(game, component).run();
    }
    
    public void load(Component component, GameLoadedCallback callback)
    {
        new LoadThread(component, callback).run();
    }
    
    private class LoadThread extends Thread
    {
        private JFileChooser chooser = new JFileChooser();
        private GameLoadedCallback callback;
        private Component component;
        public LoadThread(Component component, GameLoadedCallback callback)
        {
            this.component = component;
            this.callback = callback;
        }
        
        @Override
        public void run() {
            int retval = chooser.showOpenDialog(component);
            if (retval == JFileChooser.APPROVE_OPTION) {
                ObjectOutputStream writer;
                
                try {
                    ObjectInputStream oi = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()));
                    GameInterface game = (GameInterface)oi.readObject();
                    callback.onLoad(game);
                } catch(IOException e) {
                    throw new RuntimeException(e);
                } catch(ClassNotFoundException ex) {
                
                }finally {
                    
                }
            }
        }
    }
    
    private class SaveThread extends Thread {
        private GameInterface game;
        private JFileChooser chooser = new JFileChooser();
        private Component component;
        public SaveThread(GameInterface game, Component component)
        {
            this.game = game;
            this.component = component;
        }
        @Override
        public void run() {
            int retval = chooser.showSaveDialog(component);
            if (retval == JFileChooser.APPROVE_OPTION) {
                ObjectOutputStream writer;
                
                try {
                    writer = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()));
                    writer.writeObject(game);
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    
                }
            }
        }
    }
}
