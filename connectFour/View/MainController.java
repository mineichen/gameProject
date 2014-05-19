/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectFour.View;

import connectFour.GameProject;
import connectFour.entity.Game;
import connectFour.entity.GameController;
import connectFour.entity.GameInterface;
import connectFour.entity.GuiPlayer;
import connectFour.entity.KIPlayerMike;
import connectFour.entity.NetworkPlayer;
import connectFour.entity.PlayerInterface;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;

/**
 * Description
 *
 * @author jonbuc
 */
public class MainController {

    public GameInterface newGameTwoGuiPlayers(ViewInterface view) throws IOException {
        //View view = new View();
        PlayerInterface player1 = new GuiPlayer("Markus",
                ImageIO.read(GameProject.class.getResource(
                                "/connectFour/images/default_red_dot.png")), view);
        PlayerInterface player2 = new GuiPlayer("Mike",
                ImageIO.read(GameProject.class.getResource(
                                "/connectFour/images/default_yellow_dot.png")), view);

        Game game = new Game(8, 6, 4, player1, player2);
        new GameController(game);

        view.bind(game);
        return game;
    }
    
    public GameInterface newGameKIMike(ViewInterface view) throws IOException{
        PlayerInterface player1 = new GuiPlayer("Markus",
                ImageIO.read(GameProject.class.getResource(
                                "/connectFour/images/default_red_dot.png")), view);
        KIPlayerMike playerki = new KIPlayerMike("KI Mike",
                ImageIO.read(GameProject.class.getResource(
                                "/connectFour/images/default_yellow_dot.png")));
        
        Game game = new Game(8, 6, 4, player1, playerki);
        GameController ctrl = new GameController(game);
        
        view.bind(game);
        playerki.bind(game);
        return game;
    }

    public GameInterface findNetworkPlayer(ViewInterface view) throws IOException {

        ServerSocket server = new ServerSocket(1111);

        NetworkPlayer player = new NetworkPlayer(
                "Markus",
                ImageIO.read(GameProject.class.getResource("/connectFour/images/default_red_dot.png")),
                server.accept()
        );
        PlayerInterface player2 = new GuiPlayer("Mike", ImageIO.read(GameProject.class.getResource("/connectFour/images/default_yellow_dot.png")), view);

        Game game = new Game(30, 31, 5, player, player2);
        GameController ctrl = new GameController(game);

        view.bind(game);
        player.bind(game);
        return game;
    }

    public GameInterface connectNetworkGame(ViewInterface view) throws IOException {
        PlayerInterface player = new GuiPlayer("Markus", ImageIO.read(GameProject.class.getResource("/connectFour/images/default_red_dot.png")), view);
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

}
