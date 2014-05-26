package connectFour.entity;

import java.io.ObjectInputStream;

/**
 * Class which waits for a stream to receive a player
 *
 * If the player gets found, the variables will be written in the NetworkGameFinder object
 *
 * @author efux
 */
public class NetworkPlayerObjectListener implements Runnable
{
    private NetworkGameFinder networkGameFinder;

    /**
     * Constructor for this class
     *
     * @param networkGameFinder Takes a NetworkGameFinder object
     */
    public NetworkPlayerObjectListener(NetworkGameFinder networkGameFinder)
    {
        this.networkGameFinder = networkGameFinder;
    }

    /**
     * Waits for a GuiPlayer object to be received
     */
    public void run()
    {
        synchronized(networkGameFinder.getLock()) {
            try {
                ObjectInputStream oin = new ObjectInputStream(networkGameFinder.getPlayersocket().getInputStream());
                GuiPlayer networkplayer = (GuiPlayer) oin.readObject();
                networkGameFinder.setOpponent(new NetworkPlayer(networkplayer.getName(), networkplayer.getImage(), networkGameFinder.getPlayersocket()));
                System.out.println("Player fetched");
            } catch(Exception e) {
                System.out.println("Failed to read the Player object over the network");
            }
        }
    }
}
