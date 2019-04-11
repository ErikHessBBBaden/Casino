/*
 * This peace of Software has been written by Nick Flückiger
 * You are free to use and modifiy this software to your needs
 * For information and contact with the developer please write to
 * Mail: nick.flueckiger@outlook.de
 */
package com.team1.casino.Controller;

import com.team1.casino.Model.CasinoLoginModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Nick Flückiger
 */
public class LoginController implements Initializable {

    private CasinoLoginModel loginModel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButtion;
    @FXML
    private Button registerButton;
    @FXML
    private Button recoverPasswordButton;

    public void setModel(CasinoLoginModel model) {
        this.loginModel = model;
        bind();
    }

    private void bind() {
        this.loginModel.getUsernameProperty().bindBidirectional(this.usernameField.textProperty());
        this.loginModel.getPasswordProperty().bindBidirectional(this.passwordField.textProperty());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void loginUser(ActionEvent event) {
        String result = this.loginModel.loginUser();
        if (result.equals("Valid user")) {

            this.loginModel.displayMainMenu();
        } else {
        }
    }

    @FXML
    private void registerUser(ActionEvent event) {
        this.loginModel.displayRegistrationView();
    }

    @FXML
    private void displayPasswordRecovery(ActionEvent event) {
        this.loginModel.displayPasswordRecovery();
    }

}
