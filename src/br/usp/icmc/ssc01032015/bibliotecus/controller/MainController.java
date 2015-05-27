package br.usp.icmc.ssc01032015.bibliotecus.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class MainController implements Initializable
{

    @FXML
    private Tab listBooksTab;

    @FXML
    private Tab listUsersTab;

    @FXML
    private Tab listLoansTab;

    @FXML
    private Tab myBooksTab;

    @FXML
    private DatePicker datePicker;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        try
        {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("../view/ListBooksTab.fxml"));
            listBooksTab.setContent(root);

            root = FXMLLoader.load(getClass().getResource("../view/ListLoansTab.fxml"));
            listLoansTab.setContent(root);

            root = FXMLLoader.load(getClass().getResource("../view/ListUsersTab.fxml"));
            listUsersTab.setContent(root);

            root = FXMLLoader.load(getClass().getResource("../view/myBooksTab.fxml"));
            myBooksTab.setContent(root);

            datePicker.setValue(LocalDate.now());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

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