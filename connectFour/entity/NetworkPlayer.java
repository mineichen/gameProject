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
 * NetworkPlayer is a player which listens to the network and sends moves to opponents
 *
 * @author  efux
 */
public class NetworkPlayer extends AbstractPlayer implements EventListener<DiscMoveEvent> {

    private Socket socket;
    protected Game game ;
    protected NetworkThread networkThread;
   
    /**
     * Default constructor
     *
     * @param name Name of the player
     * @param image Image of the player
     * @param socket Socket of the network opponent, has to be established before creating this object
     */
    public NetworkPlayer(String name, Image image, Socket socket)
    {
        super(name, image);
        this.socket = socket;
        new NetworkThread(this).start();
    }

    /**
     * Binds the game to this player so the player gets informed about all the moves
     *
     * @param game The game in which this player is
     */
    public void bind(Game game)
    {
        this.game = game ;
        game.addEventListener(this) ;
    }

    /**
     * This method gets called by the controller when a player makes a move
     *
     * @param event The DiscMoveEvent
     */
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
    
    /**
     * This Thread listens to the network traffic and creates the events from the opponents for this game
     */
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
                
                int data = in.read();
                while(data != -1) {
                    dispatcher.dispatch(new MoveEvent(game.getCurrentPlayer(), data));
                    data = in.read();
                }
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
