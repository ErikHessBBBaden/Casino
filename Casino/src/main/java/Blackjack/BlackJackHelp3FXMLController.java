/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Blackjack;

import com.team1.casino.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author albio
 */
public class BlackJackHelp3FXMLController implements Initializable {
    
    private MainApp main = new MainApp();
    @FXML
    private Button buttonBeenden;

    public void setMain(MainApp main) {
        this.main = main;
    }

    @FXML
    private Button buttonZurueck;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void zurueck(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BlackJackHelp2FXML.fxml"));
            Parent root;
            root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stage = this.main.getStage();
            stage.setTitle("Hilfe");
            stage.setScene(scene);
            stage.show();
            BlackJackHelp2FXMLController controller = loader.getController();
            controller.setMain(main);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void beenden(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BlackJackFXML.fxml"));
            Parent root;
            root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stage = this.main.getStage();
            stage.setTitle("Black Jack");
            stage.setScene(scene);
            stage.show();
            BlackJackFXMLController controller = loader.getController();
            controller.setMain(main);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}