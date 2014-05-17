/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import javax.swing.ImageIcon;
import connectFour.EventDispatcher;
import connectFour.EventListener;
import connectFour.entity.DiscMoveEvent;
import connectFour.entity.Disc;
import connectFour.entity.Game;
import java.awt.Image;
import java.io.OutputStream;
import java.io.InputStream;

/**
 * Description
 *
 * @author  efux
 */
public abstract class AbstractNetworkPlayer extends AbstractPlayer implements EventListener<MoveEvent> {

    private String name;
    private Image image;
    protected String host;
    protected int port;
    protected Game game ;
    protected OutputStream out;
    protected InputStream in;
    protected NetworkThread networkThread;
    
    public AbstractNetworkPlayer(String name, Image image, String host, int port)
    {
        this.name = name;
        this.image = image;
        this.host = host;
        this.port = port;

        dispatcher.addEventListener(this) ;
    }

    public void bindGame(Game game)
    {
        this.game = game ;
    }

    protected abstract void connect();
    
    public void on(MoveEvent event) 
    {
        System.out.println("Move registered: "+event.getCol()) ;
        if(event.getPlayer() != this) {
            try {
                out.write(event.getCol());
                out.flush();
            } catch(Exception e) {
            }
        }
    }

    protected class NetworkThread implements Runnable
    {
        private PlayerInterface playerInterface;

        public NetworkThread(AbstractNetworkPlayer player)
        {
            playerInterface = player;
        }

        public void run()
        {
            try {
                int data = in.read();
                while(data != -1) {
                    dispatcher.dispatch(new MoveEvent(playerInterface,data));
                    System.out.println("hallo: " + data) ;
                    data = in.read();
                }
            } catch(Exception e) {
            }
        }
    }

    @Override
    public String getName(){
        return name;
    }
   
    @Override
    public Image getImage()
    {
        return image;
    }
}
