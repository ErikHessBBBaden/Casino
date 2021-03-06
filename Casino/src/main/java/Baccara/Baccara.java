/*
 * This peace of Software has been written by Nick Flückiger
 * You are free to use and modifiy this software to your needs
 * For information and contact with the developer please write to
 * Mail: nick.flueckiger@outlook.de
 */
package Baccara;

import com.team1.casino.MainApp;
import com.team1.casino.Spiel;

/**
 *
 * @author Nick Flückiger
 */
public class Baccara extends Spiel {

    public Baccara(MainApp mainApplication) {
        super(mainApplication);
    }

    @Override
    public void startGame() {
        BaccaraHandler game = new BaccaraHandler(super.getMainApp());
        game.displayMenu();
    }
}
