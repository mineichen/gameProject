/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.awt.Image;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description
 *
 * @author  efux
 */
public class ServerNetworkPlayer extends AbstractNetworkPlayer {
    
    private ServerSocket listen;
    private Socket client;

    public ServerNetworkPlayer(String name, Image image, String host, int port)
    {
        super(name,image,host,port);

        connect();
    }

    @Override
    public void connect()
    {
        try {
            listen = new ServerSocket(port);
            client = listen.accept();
            out = client.getOutputStream();
            in = client.getInputStream();

            new Thread(new NetworkThread()).start();
        } catch(Exception e) {
        }
    }
    
}
