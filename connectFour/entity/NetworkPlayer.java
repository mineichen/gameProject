/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import javax.swing.ImageIcon;
import connectFour.EventDispatcher;
import connectFour.EventListener;
import connectFour.InvalidInputException;
import connectFour.entity.DiscMoveEvent;
import connectFour.entity.Disc;
import connectFour.entity.Game;
import java.awt.Image;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * Description
 *
 * @author  efux
 */
public class NetworkPlayer extends AbstractPlayer implements EventListener<DiscMoveEvent> {

    private Socket socket;
    protected Game game ;
    protected NetworkThread networkThread;
    
    public NetworkPlayer(String name, Image image, Socket socket)
    {
        super(name, image);
        this.socket = socket;
        new Thread(new NetworkThread(this)).start();
    }

    public void bind(Game game)
    {
        this.game = game ;
        game.addEventListener(this) ;
    }

    public void on(DiscMoveEvent event) 
    {
        if(event.getDisc().getPlayer() != this) {
            try {
                OutputStream out = socket.getOutputStream();
                out.write(event.getDisc().getCol());
                out.flush();
            } catch(Exception e) {
            }
        }
    }

    @Override
    public void handleError(InvalidInputException e)
    {
        
    }
    
    protected class NetworkThread implements Runnable
    {
        private PlayerInterface playerInterface;

        public NetworkThread(NetworkPlayer player)
        {
            playerInterface = player;
        }

        public void run()
        {
            try {
                InputStream in = socket.getInputStream();
                
                while(true) {
                    int data = in.read();
                    dispatcher.dispatch(new MoveEvent(game.getCurrentPlayer(), data));
                }
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
