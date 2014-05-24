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
import java.io.ObjectInputStream;
import java.io.IOException;

import connectFour.View.View;

class NetworkGameFinder
{
    private boolean gameFound = false;
    private boolean isServer = false;
    private DatagramSocket socket; 
    private ByteValidator checkValue;
    private final int port = 8921;
    private ArrayList<PlayerInterface> players;
    private GuiPlayer player;
    private int tcpport = 9999;
    private PlayerInterface opponent;
    private ObjectOutputStream oout;
    private ObjectInputStream oin;
    private Socket playersocket;
    private Object lockedOpponent = new Object();

    public static void main(String[] args)
    {
        try {
            GuiPlayer guiplayer = new GuiPlayer("Eti", ImageIO.read(NetworkGameFinder.class.getResource("/connectFour/images/default_red_dot.png")));
            NetworkGameFinder g = new NetworkGameFinder(guiplayer);
            g.startSearch();
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Stop search");
    }

    public NetworkGameFinder(GuiPlayer player)
    {
        this.player = player;

        players = new ArrayList<PlayerInterface>();
        checkValue = new ByteValidator();
    }

    public PlayerInterface[] startSearch()
    {
        try {
            socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);

            Thread serverListener = new Thread(new UDPPacketReceiver());
            serverListener.start();

            DatagramPacket packet;

            while(!gameFound) {
                packet = new DatagramPacket(checkValue.getPacket(),checkValue.getPacket().length, InetAddress.getByName("255.255.255.255"), port);
                socket.send(packet);
                System.out.println("Broadcast send");
                Thread.sleep(500);
            }
            System.out.println("Opponent name: " + opponent.getName());
        } catch(Exception e) {
        }
        PlayerInterface[] hallo = new PlayerInterface[2];
        return hallo;

    }

    private class NetworkPlayerListener implements Runnable
    {
        public void run()
        {
            synchronized(lockedOpponent) {
                try {
                    if(oin==null) {
                        System.out.println("Upps");
                    }
                    GuiPlayer networkplayer = (GuiPlayer) oin.readObject();
                    opponent = new NetworkPlayer(networkplayer.getName(), networkplayer.getImage(), playersocket); 
                    System.out.println("Player fetched");
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

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

        private void sendClientRole()
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
            try {
                while(!gameFound) {
                    DatagramPacket packet = new DatagramPacket(new byte[1024],1024);

                    socket.receive(packet);
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
                            sendClientRole();
                            makeConnectionAsServer();
                        }
                        exchangePlayers();
                    }
                }
                System.out.println("Connected successfully with " + opponent.getName() + "@" + address.getHostAddress());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void exchangePlayers()
    {
        try {
            oout = new ObjectOutputStream(playersocket.getOutputStream());
            oin = new ObjectInputStream(playersocket.getInputStream());

            new Thread(new NetworkPlayerListener()).start();
            //Thread.sleep(1000);
            oout.writeObject(player);

            synchronized(lockedOpponent) {
                if(isServer) {
                    players.add(player);
                    players.add(opponent);
                } else {
                    players.add(opponent);
                    players.add(player);
                }
            }
            System.out.println("set gamefound = true") ;

            gameFound = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
