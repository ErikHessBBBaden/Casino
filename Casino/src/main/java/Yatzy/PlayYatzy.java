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
public class PlayYatzy extends Spiel{

    public PlayYatzy(MainApp mainApplication) {
        super(mainApplication);
    }

    @Override
    public void startGame() {
        try{
            Stage stage = super.getMainApp().getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/YatzyFXML.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            stage.setTitle("Casino Central");
            stage.setScene(scene);
            stage.show();
            YatzyFXMLController menu = loader.getController();
            menu.setYatzy(this);
    } catch (IOException e) {
        
    }
    
    }
    
    
}
