/*
 * This peace of Software has been written by Nick Flückiger
 * You are free to use and modifiy this software to your needs
 * For information and contact with the developer please write to
 * Mail: nick.flueckiger@outlook.de
 */
package com.team1.casino.Controller;

import com.team1.casino.Entity.Stat;
import com.team1.casino.Model.GameStatisticModel;
import com.team1.casino.Model.PlayerStatisticModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Nick Flückiger
 */
public class GameStatisticController implements Initializable, Observer {

    private GameStatisticModel model;
    @FXML
    private TableView<Stat> gameStatTable;
    @FXML
    private LineChart<String, Double> gameProfit;
    @FXML
    private ComboBox<String> gameNames;
    @FXML
    private TableColumn<Stat, String> userCol;
    @FXML
    private TableColumn<Stat, String> betCol;
    @FXML
    private TableColumn<Stat, String> resultCol;
    @FXML
    private TableColumn<Stat, String> changeCol;

    public void setGameStatistics(GameStatisticModel model) throws SQLException {
        this.model = model;
        this.gameNames.getItems().addAll(model.getGameNames());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stat, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stat, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getUsername());
                } else {
                    return new SimpleStringProperty("No Game");
                }
            }
        });
        resultCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stat, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stat, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getResult());
                } else {
                    return new SimpleStringProperty("No Result");
                }
            }
        });
        betCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stat, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stat, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getBet()));
                } else {
                    return new SimpleStringProperty("No Bet");
                }
            }
        });
        changeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stat, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stat, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getEndamount()));
                } else {
                    return new SimpleStringProperty("No Change");
                }
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        GameStatisticModel gameModel = (GameStatisticModel) o;
        this.gameStatTable.getItems().clear();
        for (Stat stat : gameModel.getGameStats()) {
            this.gameStatTable.getItems().add(stat);
        }
        ArrayList<Double> gameProfit = model.getGameProfits();
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        int counter = 0;
        this.gameProfit.getData().clear();
        for (double value : gameProfit) {
            series.getData().add(new XYChart.Data<>(String.valueOf(counter + 20), value));
            counter++;
        }
        this.gameProfit.getData().add(series);
    }

    @FXML
    private void selectionChanged(ActionEvent event) throws SQLException {
        this.model.setSelectedGame(this.gameNames.getSelectionModel().getSelectedItem());
        this.model.loadGameStats();
    }

}