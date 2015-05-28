package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController
{
    @FXML
    private TextField usernameField;

    public void onSignIn(ActionEvent actionEvent)
    {
        String username = usernameField.getText();
        try
        {
            User newUser = Library.getInstance().getUsers().filtered(user -> user.getName().equals(username)).get(0);
            Library.getInstance().setCurrentUser(newUser);

            //welcome user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Welcome");
            alert.setContentText(newUser.getName() + " logged in");
            alert.show();

            ((Stage)usernameField.getScene().getWindow()).close();
        } catch(IndexOutOfBoundsException ex)
        {
            //User doesn't exist
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Couldn't sign in");
            alert.setContentText("User with name "+ username + " was not found");
            alert.show();
        }
    }
}
