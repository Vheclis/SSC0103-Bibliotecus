package br.usp.icmc.ssc01032015.bibliotecus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController
{
    public void userSignUp(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/SignUp.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Sign Up User");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void bookRegister(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/RegisterBook.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Register Book");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void userSignIn(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/SignIn.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Sign In User");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void booksImport(ActionEvent actionEvent)
    {

    }

    public void usersImport(ActionEvent actionEvent)
    {

    }

    public void loansImport(ActionEvent actionEvent)
    {

    }

    public void booksExport(ActionEvent actionEvent)
    {

    }

    public void usersExport(ActionEvent actionEvent)
    {

    }

    public void loansExport(ActionEvent actionEvent)
    {

    }
}