/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yatzy;

import com.team1.casino.MainApp;
import com.team1.casino.User.Util.PlayerCentral;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Erik Hess
 */
public class YatzyFXMLMenuController implements Initializable {

    /**
     * Initialises the controller class.
     */
    private MainApp mainApplication;
    private Yatzy yatzy;
    @FXML
    private Button btnstart;
    @FXML
    private AnchorPane menuwindow;
    @FXML
    private Label balanceLabel;
    @FXML
    private Button btnback;

    public void setYatzy(Yatzy yatzy) {
        this.yatzy = yatzy;
    }

    public void setMainApplication(MainApp mainApplication) {
        this.mainApplication = mainApplication;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("Started");
        balanceLabel.setText("Konto: " + PlayerCentral.getInstance().getUser().getCurrentChipBalance());
    }

    @FXML
    private void btnstartPress(ActionEvent event) {
        this.yatzy.displayGame();
    }

    @FXML
    private void pressbtnback(ActionEvent event) {
        this.mainApplication.displayMainMenu();
    }

    @FXML
    private void exitbtnback(MouseEvent event) {
        btnback.setStyle("-fx-background-color: rgba(1, 1, 1, 0); -fx-border-color: black; -fx-border-width: 3;");
    }

    @FXML
    private void enterbtnback(MouseEvent event) {
        btnback.setStyle("-fx-background-color: rgba(1, 1, 1, .1); -fx-border-color: black; -fx-border-width: 3;");
    }

}
