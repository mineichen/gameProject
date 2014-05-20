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
import java.util.Enumeration;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import connectFour.View.View;

class NetworkGameFinder
{
    private boolean gameFound = false;
    private boolean isServer = false;
    private DatagramSocket socket; 
    private byte[] randomNumber = new byte[1024];
    private final int port = 8921;
    private ArrayList<PlayerInterface> players;
    private PlayerInterface player;
    private int tcpport = 9999;
    private PlayerInterface opponent;
    private ObjectOutputStream oout;
    private ObjectInputStream oin;
    private Socket playersocket;

    public static void main(String[] args)
    {
        try {
            PlayerInterface guiplayer = new GuiPlayer("Eti", ImageIO.read(NetworkGameFinder.class.getResource("/connectFour/images/default_red_dot.png")), null);
            NetworkGameFinder g = new NetworkGameFinder(guiplayer);
            g.startSearch();
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Stop search");
    }

    public NetworkGameFinder(PlayerInterface player)
    {
        this.player = player;
        Random randomGenerator = new Random();
        randomGenerator.nextBytes(randomNumber);
    }

    public PlayerInterface[] startSearch()
    {
        try {
            socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);

            Thread serverListener = new Thread(new ServerListener());
            serverListener.start();

            DatagramPacket packet;

            while(!gameFound) {
                packet = new DatagramPacket(randomNumber,randomNumber.length, InetAddress.getByName("255.255.255.255"), port);
                socket.send(packet);
                System.out.println("Broadcast sended");
                Thread.sleep(10000);
            }
            System.out.println("Opponent name: " + opponent.getName());
        } catch(Exception e) {
        }
        return (PlayerInterface[]) players.toArray() ;

    }

    private class NetworkPlayerListener implements Runnable
    {
        public void run()
        {
            synchronized(opponent) {
                try {
                    PlayerInterface networkplayer = (PlayerInterface) oin.readObject();
                    opponent = new NetworkPlayer(networkplayer.getName(), networkplayer.getImage(), playersocket); 
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ServerListener implements Runnable
    {
        public void run()
        {
            try {
                while(!gameFound) {
                    DatagramPacket packet = new DatagramPacket(new byte[1024],1);
                    socket.receive(packet);
                    InetAddress address = packet.getAddress();
                    byte[] data = packet.getData();
                    if(data[0]!=randomNumber[0] && data[1]!=randomNumber[1]) {
                        System.out.println("fam annru");

                        System.out.println(data[3]);
                        System.out.println(randomNumber[3]);
                        
                        if(data[3] == randomNumber[3]){
                            System.out.println(address + " wants to connect with me");
                            // ich bin der Client
                            Thread.sleep(2000);
                            playersocket = new Socket(address, tcpport);
                            exchangePlayers();
                        } else {
                            isServer = true;
                            System.out.println("I want to connect with " + data + "@" + address);
                            // ich bin der Server
                            ServerSocket serverSocket = new ServerSocket(tcpport);
                            data[0] = randomNumber[0];
                            data[1] = randomNumber[1];
                            socket.send(new DatagramPacket(data, data.length, address, port));
                            playersocket = serverSocket.accept();
                            exchangePlayers();
                        }
                    }
                }
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
            Thread.sleep(1000);
            oout.writeObject(player);

            synchronized(opponent) {
                if(isServer) {
                    players.add(player);
                    players.add(opponent);
                } else {
                    players.add(opponent);
                    players.add(player);
                }
            }
            gameFound = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
