/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.awt.Image;
import java.net.Socket;

/**
 * Description
 *
 * @author  efux
 */
public class ClientNetworkPlayer extends AbstractNetworkPlayer {
    
    private Socket server;

    public ClientNetworkPlayer(String name, Image image, String host, int port)
    {
        super(name,image,host,port);

        connect();
    }

    @Override
    public void connect()
    {
        try {
            server = new Socket(host,port);
            out = server.getOutputStream();
            in = server.getInputStream();

            new Thread(new NetworkThread()).start();
        } catch(Exception e) {
        }
    }
    
}
