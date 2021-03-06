/*
 * This peace of Software has been written by Nick Flückiger
 * You are free to use and modifiy this software to your needs
 * For information and contact with the developer please write to
 * Mail: nick.flueckiger@outlook.de
 */
package Baccara.Model;

import Baccara.BaccaraHandler;
import com.team1.casino.User.Spieler;
import com.team1.casino.User.Util.PlayerCentral;

/**
 *
 * @author Nick Flückiger
 */
public class BaccaraMenuModel extends BaccaraModel {

    private final Spieler player = PlayerCentral.getInstance().getUser();

    public BaccaraMenuModel(BaccaraHandler baccaraGame) {
        super(baccaraGame);
    }

    public void backToMainMenu() throws Exception {
        super.getBaccaraGame().displayMainMenu();
    }

    public void startBaccara() {
        super.getBaccaraGame().displayGame();
    }

    public Spieler getPlayer() {
        return this.player;
    }
}
