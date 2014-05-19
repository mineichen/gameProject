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
import connectFour.entity.PlayerInterface;
import java.io.IOException;
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
            GameController ctrl = new GameController(game);

            view.bind(game);
        return game;
    }
}
