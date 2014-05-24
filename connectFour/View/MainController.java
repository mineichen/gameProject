/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectFour.View;

import connectFour.GameBackend;
import connectFour.GameLoadedCallback;
import connectFour.GameProject;
import connectFour.MinMaxKI;
import connectFour.entity.Game;
import connectFour.entity.GameController;
import connectFour.entity.GameInterface;
import connectFour.entity.GuiPlayer;
import connectFour.entity.KIPlayerMike;
import connectFour.entity.KiPlayer;
import connectFour.entity.NetworkPlayer;
import connectFour.entity.PlayerInterface;
import java.awt.Component;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Controller of the menu in main window
 *
 * @author jonbuc
 */
public class MainController {

    private GameBackend gameBackend = new GameBackend();
    private String namePlayer1;
    private String namePlayer2;
    private int gameRows;
    private int gameCols;
    
    private Object[] params;

    /**
     * Create a new game with two local players
     *
     * @param view
     * @return game
     * @throws IOException
     */
    public GameInterface newGameTwoGuiPlayers(ViewInterface view) throws IOException {

        setGameParams("Player 2");
        GuiPlayer player1 = new GuiPlayer(
            namePlayer1,
            ImageIO.read(GameProject.class.getResource("/connectFour/images/default_red_dot.png"))
        );
        
        GuiPlayer player2 = new GuiPlayer(
            namePlayer2,
            ImageIO.read(GameProject.class.getResource("/connectFour/images/default_yellow_dot.png"))
        );

        player1.bind(view);
        player2.bind(view);
        Game game = new Game(gameRows, gameCols, 4, player1, player2);
        new GameController(game);

        view.bind(game);
        return game;
    }
    
    
    
    public GameInterface newGameKIMike(ViewInterface view) throws IOException{
        setGameParams("KI Mike");
        GuiPlayer player1 = new GuiPlayer(
            namePlayer1,
            ImageIO.read(GameProject.class.getResource("/connectFour/images/default_red_dot.png"))
        );
        KIPlayerMike playerki = new KIPlayerMike(
            namePlayer2,
            ImageIO.read(GameProject.class.getResource("/connectFour/images/default_yellow_dot.png"))
        );
        player1.bind(view);
        
        Game game = new Game(gameRows, gameCols, 4, player1, playerki);
        playerki.bind(game);
        
        GameController ctrl = new GameController(game);
        view.bind(game);
        return game;
    }
    
    public GameInterface newGameKIKusi(ViewInterface view) throws IOException{
        setGameParams("KI Kusi");
        MinMaxKI ki= new MinMaxKI();
        GuiPlayer player1 = new GuiPlayer(
            namePlayer1,
            ImageIO.read(GameProject.class.getResource("/connectFour/images/default_red_dot.png"))
        );
        KiPlayer playerki = new KiPlayer(
            namePlayer2,
            ImageIO.read(GameProject.class.getResource("/connectFour/images/default_yellow_dot.png")),
            ki
        );
        player1.bind(view);
        
        Game game = new Game(gameRows, gameCols, 4, playerki, player1);
        ki.bind(game);
        
        GameController ctrl = new GameController(game);
        view.bind(game);
        return game;
    }

    /**
     * Create a game for the server to play over network
     *
     * @param view
     * @return game
     * @throws IOException
     */
    public GameInterface findNetworkPlayer(ViewInterface view) throws IOException {

        ServerSocket server = new ServerSocket(1111);

        NetworkPlayer player = new NetworkPlayer(
                "Markus",
                ImageIO.read(GameProject.class.getResource("/connectFour/images/default_red_dot.png")),
                server.accept()
        );
        GuiPlayer player2 = new GuiPlayer("Mike", ImageIO.read(GameProject.class.getResource("/connectFour/images/default_yellow_dot.png")));
        player2.bind(view);

        Game game = new Game(30, 31, 5, player, player2);
        GameController ctrl = new GameController(game);

        view.bind(game);
        player.bind(game);
        return game;
    }

    public void saveGame(GameInterface game, Component view)
    {
        try {
            gameBackend.save(game, view);
        } catch(IOException e) {
            System.out.println("Game is not Serializable");
        }
    }
    
    public void loadGame(ViewInterface view)
    {
        final ViewInterface viewForInnerClass = view;
        gameBackend.load(view.getMainWindow(), new GameLoadedCallback() {
            public void onLoad(GameInterface game) {
                for(PlayerInterface player : game.getPlayers()) {
                    if(player instanceof GuiPlayer) {
                        ((GuiPlayer)player).bind(viewForInnerClass);
                    }
                }
                new GameController(game);
                viewForInnerClass.bind(game);
            }
        });
    }
    
    /**
     * Create a game for the client to play over network
     *
     * @param view
     * @return game
     * @throws IOException
     */
    public GameInterface connectNetworkGame(ViewInterface view) throws IOException {
        GuiPlayer player = new GuiPlayer("Markus", ImageIO.read(GameProject.class.getResource("/connectFour/images/default_red_dot.png")));
        player.bind(view);
        NetworkPlayer player2 = new NetworkPlayer(
                "Mike",
                ImageIO.read(GameProject.class.getResource("/connectFour/images/default_yellow_dot.png")),
                new Socket("localhost", 1111)
        );

        Game game = new Game(30, 31, 5, player, player2);
        GameController ctrl = new GameController(game);

        view.bind(game);
        player2.bind(game);
        return game;
    }
    
    
    
    private void setGameParams(String player2) {
        
        InputGameParams params = new InputGameParams(player2);
        namePlayer1 = params.getNamePlayer1();
        namePlayer2 = params.getNamePlayer2();
        gameRows = params.getGameRows();
        gameCols = params.getGameCols();
    }
    
}
