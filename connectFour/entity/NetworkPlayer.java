/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import connectFour.EventListener;
import connectFour.InvalidInputException;
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
        new NetworkThread(this).start();
    }

    public void bind(Game game)
    {
        this.game = game ;
        game.addEventListener(this) ;
    }

    @Override
    public void on(DiscMoveEvent event) 
    {
        if(event.getDisc().getPlayer() != this) {
            try {
                OutputStream out = socket.getOutputStream();
                out.write(event.getDisc().getCol());
                out.flush();
            } catch(IOException e) {
            }
        }
    }
 
    @Override
    public void handleError(InvalidInputException e)
    {
        throw new RuntimeException("KI Player performed forbidden move");
    }
    
    private class NetworkThread extends Thread
    {
        private final PlayerInterface playerInterface;

        public NetworkThread(NetworkPlayer player)
        {
            playerInterface = player;
        }

        @Override
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
