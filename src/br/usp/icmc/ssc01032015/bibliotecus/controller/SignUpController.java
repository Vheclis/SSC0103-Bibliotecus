package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable
{
    /**
     * user name input text field
     */
    @FXML
    private TextField usernameField;

    /**
     * user type input combo box
     */
    @FXML
    private ComboBox typeBox;

    /**
     * Runs when loaded
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        typeBox.setValue(typeBox.getItems().get(0));
    }

    /**
     * Called when "Sign up" button was pressed
     * @param actionEvent
     */
    public void onSignUp(ActionEvent actionEvent)
    {
        new User(usernameField.getText(), User.Type.valueOf((String) typeBox.getValue()));
    }
}