package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Victor on 5/26/2015.
 */
public class SignInController
{
    @FXML
    private TextField usernameField;

    public void onSignIn(ActionEvent actionEvent)
    {
        String username = usernameField.getText();

        for(User user : Library.getInstance().getUsers())
        {
            if(user.getName().equals(username))
            {
                Library.getInstance().setCurrentUser(user);
                Stage stage = ((Stage)usernameField.getScene().getWindow());
                if(stage != null)
                {
                    stage.close();
                }
                return;
            }
        }
        new Alert(Alert.AlertType.INFORMATION, "User not found!").show();
    }
}
