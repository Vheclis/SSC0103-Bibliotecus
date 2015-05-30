package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Book;
import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import br.usp.icmc.ssc01032015.bibliotecus.serialization.CSVSerializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    @FXML
    private Label currentUserLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //load tabs
        try
        {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("../view/ListBooksTab.fxml"));
            listBooksTab.setContent(root);

            root = FXMLLoader.load(getClass().getResource("../view/ListLoansTab.fxml"));
            listLoansTab.setContent(root);

            root = FXMLLoader.load(getClass().getResource("../view/ListUsersTab.fxml"));
            listUsersTab.setContent(root);

            root = FXMLLoader.load(getClass().getResource("../view/MyBooksTab.fxml"));
            myBooksTab.setContent(root);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        //set date
        datePicker.setValue(Library.getInstance().getCurrentDate());

        //enable-disable my books tab
        Library.getInstance().currentUserProperty().addListener(
                (observable, oldValue, newValue) -> onCurrentUserChanged(newValue));
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

    public void booksExport(ActionEvent actionEvent) throws IllegalAccessException, IOException, InstantiationException
    {
        Book book = new Book("titulo", "autor", Book.Type.General, 3);
        CSVSerializer.write(book, System.out);
        Book b2 = CSVSerializer.read(System.in, Book.class);
        CSVSerializer.write(b2, System.out);
    }

    public void usersExport(ActionEvent actionEvent)
    {

    }

    public void loansExport(ActionEvent actionEvent)
    {

    }

    private void onCurrentUserChanged(User newUser)
    {
        if(newUser != null)
        {
            currentUserLabel.setText(newUser.getName());
            myBooksTab.setDisable(false);
        }
        else
        {
            currentUserLabel.setText("None");
        }
    }


    public void onDateChange(ActionEvent actionEvent)
    {
        Library.getInstance().setCurrentDate(datePicker.getValue());
    }
}