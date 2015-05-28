package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        typeBox.getSelectionModel().selectFirst();
    }

    /**
     * Called when "Sign up" button was pressed
     *
     * @param actionEvent
     */
    public void onSignUp(ActionEvent actionEvent)
    {
        for (User user : Library.getInstance().getUsers())
        {
            if (user.getName().equals(usernameField.getText()))
            {
                new Alert(Alert.AlertType.ERROR, "User name already exists!").show();
                return;
            }
        }
        Library.getInstance().getUsers().add(new User(usernameField.getText(), User.Type.valueOf((String) typeBox.getValue())));
        ((Stage)usernameField.getScene().getWindow()).close();

    }
}