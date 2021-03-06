/*
 * This peace of Software has been written by Nick Flückiger
 * You are free to use and modifiy this software to your needs
 * For information and contact with the developer please write to
 * Mail: nick.flueckiger@outlook.de
 */
package Baccara.Model;

import Baccara.BaccaraHandler;
import java.util.Observable;

/**
 *
 * @author Nick Flückiger
 */
public class BaccaraModel extends Observable {

    private final BaccaraHandler baccaraGame;

    public BaccaraModel(BaccaraHandler baccaraGame) {
        this.baccaraGame = baccaraGame;
    }

    public BaccaraHandler getBaccaraGame() {
        return this.baccaraGame;
    }
}
