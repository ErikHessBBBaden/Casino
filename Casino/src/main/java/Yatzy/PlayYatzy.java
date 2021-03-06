/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yatzy;

import com.team1.casino.MainApp;
import com.team1.casino.Spiel;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author Erik Hess
 */
public class PlayYatzy extends Spiel {
    
    public PlayYatzy(MainApp mainApplication) {
        super(mainApplication);
    }
    
//    private MainApp main;
//    
//    public void setMain(MainApp main) {
//        this.main = main;
//    }

    @Override
    public void startGame() {

        /*
            UserCentral.getInstance().getPlayer();
         */
        try {
            Stage stageGame = super.getMainApp().getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/YatzyFXML.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            stageGame.setTitle("Casino Central");
            stageGame.setScene(scene);
            stageGame.centerOnScreen();
            stageGame.show();
            YatzyFXMLController game = loader.getController();
            game.setMainApplication(super.getMainApp());
            game.setYatzy(this);
        } catch (IOException e) {

        }
    }

}
