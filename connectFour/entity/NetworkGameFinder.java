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
import java.awt.Image;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import connectFour.View.View;

class NetworkGameFinder
{
    private boolean gameFound = false;
    private DatagramSocket socket; 
    private byte[] randomNumber = new byte[1024];
    private final int port = 8921;
    private ArrayList<PlayerInterface> players;
    private PlayerInterface player;

    public static void main(String[] args)
    {
        try {
            PlayerInterface guiplayer = new GuiPlayer("Eti", ImageIO.read(NetworkGameFinder.class.getResource("/connectFour/images/default_red_dot.png")), new View());
            NetworkGameFinder g = new NetworkGameFinder(guiplayer);
            g.startSearch();
        } catch(Exception e) {
        }
        System.out.println("Stop search");
    }

    public NetworkGameFinder(PlayerInterface player)
    {
        this.player = player;
    }

    public NetworkGameFinder()
    {
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
            System.out.println(InetAddress.getByName("255.255.255.255"));
            
            DatagramPacket packet;
            randomNumber[0] = 0x7;
            
            while(!gameFound) {
                packet = new DatagramPacket(randomNumber,randomNumber.length, InetAddress.getByName("255.255.255.255"), port);
                System.out.println(randomNumber);
                socket.send(packet);
                System.out.println("Broadcast sended");
                Thread.sleep(10000);
            }
        } catch(Exception e) {
        }
        return (PlayerInterface[]) players.toArray() ;
        
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
                    //if(InetAddress.getAllByName(address.toString())!=InetAddress.getAllByName("127.0.0.1")) {
                        byte[] data = packet.getData();
                        
                        if(data[0]==randomNumber[0]){
                            System.out.println(address + " wants to connect with me");
                        } else {
                            System.out.println("I want to connect with " + data + "@" + address);
                        }
                    //}
                }

                gameFound = true;
            } catch(Exception e) {
            }
        }
    }
}
