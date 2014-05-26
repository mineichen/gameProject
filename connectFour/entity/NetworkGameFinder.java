/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.NetworkInterface;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.awt.Image;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.ObjectOutputStream;
import java.io.IOException;

import connectFour.View.ViewInterface;
import connectFour.GameProject;

/**
 * Class for finding games over the network via UDP
 *
 * @author efux
 */
public class NetworkGameFinder implements Runnable
{
    private boolean gameFound = false;
    private boolean isServer = false;

    /**
     * Socket for sending UDP packets
     */
    private DatagramSocket socket; 

    private ByteValidator checkValue;
    private final int port = 8921;
    private int tcpport = 9999;

    private ViewInterface view;
    private GuiPlayer player;
    private NetworkPlayer opponent;
    private Socket playersocket;
    private ArrayList<PlayerInterface> players;

    /**
     * Used to synchronize the threads
     */
    private Object lockedOpponent = new Object();

    /**
     * Returns the found network player
     *
     * @return opponent
     */
    public NetworkPlayer getNetworkPlayer()
    {
        return opponent;
    }

    /**
     * Constructor for the search of a network player
     *
     * @param player The Guiplayer to send to the opponent
     */
    public NetworkGameFinder(GuiPlayer player, ViewInterface view)
    {
        this.player = player;
        this.view = view;

        players = new ArrayList<PlayerInterface>();
        checkValue = new ByteValidator();
    }

    public void run()
    {
        player.bind(view);
        Game game = new Game(10,10,4, startSearch());

        // change the image, so the players don't have both the red dot
        try {
            player.setImage(ImageIO.read(GameProject.class.getResource("/connectFour/images/default_yellow_dot.png")));
        } catch(IOException e) {
            System.out.println("Failed to load image");
        }

        GameController ctrl = new GameController(game);
        getNetworkPlayer().bind(game);
        view.bind(game);
    }

    /**
     * Starts the search for players via UDP
     *
     * @return An array with the players of a game in the correct order
     */
    private PlayerInterface[] startSearch()
    {
        try {
            socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);

            Thread serverListener = new Thread(new UDPPacketReceiver());
            serverListener.start();

            while(!gameFound) {
                sendBroadcast();
                // the timeout is necessary, so the connection attempt doesn't get interrupted by other arriving packets
                Thread.sleep(10000);
            }
            socket.close();
            System.out.println("Opponent name: " + opponent.getName());
        } catch(Exception e) {
            System.out.println("Failed to create an UDP socket or to send packets");
        }
        return players.toArray(new PlayerInterface[players.size()]);
    }

    private void sendBroadcast() throws IOException
    {
        socket.send(new DatagramPacket(checkValue.getPacket(),checkValue.getPacket().length, InetAddress.getByName("255.255.255.255"), port));
        System.out.println("Broadcast send");
    }

    public void setOpponent(NetworkPlayer networkPlayer)
    {
        opponent = networkPlayer;
    }

    public Socket getPlayersocket()
    {
        return playersocket;
    }

    /**
     * @return Returns the object which is used for synchronisation
     */
    public Object getLock()
    {
        return lockedOpponent;
    }

    /**
     * Class which waits for arriving UDP packets and treats them correctly
     */
    private class UDPPacketReceiver implements Runnable
    {
        private ByteValidator foreign;
        private InetAddress address;

        private void makeConnectionAsClient()
        {
            try {
                playersocket = new Socket(address,tcpport);
            } catch(IOException e) {
                System.out.println("Failed to establish the connection as a client");
            }
        }

        private void makeConnectionAsServer()
        {
            isServer = true;
            try {
                ServerSocket serverSocket = new ServerSocket(tcpport);
                playersocket = serverSocket.accept();
            } catch(IOException e) {
                System.out.println("Failed to establish the connection as a server");
            }
        }

        private void sendClientNotification()
        {
            foreign.setHostPart(checkValue.getHostPart());
            try {
                socket.send(new DatagramPacket(foreign.getPacket(), foreign.getPacket().length, address, port));
            } catch(IOException e) {
                System.out.println("Failed to send an answer packet via UDP");
            }
        }

        public void run()
        {
            while(!gameFound) {
                DatagramPacket packet = new DatagramPacket(new byte[1024],1024);

                try {
                    socket.receive(packet);
                } catch(Exception e) {
                    System.out.println("UDP Socket can't receive packets");
                }
                address = packet.getAddress();
                byte[] data = packet.getData();
                foreign = new ByteValidator(data);

                if(!checkValue.isFromTheSameHost(data)) {
                    if(checkValue.containsTheSameSum(data)){
                        System.out.println(address + " wants to connect with me");
                        makeConnectionAsClient();
                    } else {
                        isServer = true;
                        System.out.println("I want to connect with " + data + "@" + address);
                        sendClientNotification();
                        makeConnectionAsServer();
                    }
                    gameFound = true;
                    exchangePlayers();
                }
            }
            System.out.println("Connected successfully with " + opponent.getName() + "@" + address.getHostAddress());
        }
    }

    private void exchangePlayers()
    {
        try {
            ObjectOutputStream oout = new ObjectOutputStream(playersocket.getOutputStream());

            new Thread(new NetworkPlayerObjectListener(this)).start();
            oout.writeObject(player);

            synchronized(lockedOpponent) {
                // the order of the players is important
                if(isServer) {
                    players.add(player);
                    players.add(opponent);
                } else {
                    players.add(opponent);
                    players.add(player);
                }
            }
            System.out.println("set gamefound = true") ;

            
        } catch (Exception e) {
            System.out.println("Failed to send the player object to socket");
        }
    }
}
