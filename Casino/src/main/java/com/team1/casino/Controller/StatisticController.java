/*
 * This peace of Software has been written by Nick Flückiger
 * You are free to use and modifiy this software to your needs
 * For information and contact with the developer please write to
 * Mail: nick.flueckiger@outlook.de
 */
package com.team1.casino.Controller;

import com.team1.casino.Model.StatisticModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Nick Flückiger
 */
public class StatisticController implements Initializable, Observer {

    @FXML
    private Button playerStat;
    @FXML
    private Button gameStat;

    private StatisticModel model;

    public void setStatisticModel(StatisticModel model) {
        this.model = model;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void displayPlayerStat(ActionEvent event) throws SQLException {
        this.model.displayPlayerStatistic();
    }

    @FXML
    private void displayGameStat(ActionEvent event) throws SQLException {
        this.model.displayGameStatistic();
    }

}
