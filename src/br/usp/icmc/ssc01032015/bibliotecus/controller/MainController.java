package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Book;
import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.Loan;
import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import br.usp.icmc.ssc01032015.bibliotecus.serialization.CSVSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;


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
            root = FXMLLoader.load(getClass().getResource("/br/usp/icmc/ssc01032015/bibliotecus/view/ListBooksTab.fxml"));
            listBooksTab.setContent(root);

            root = FXMLLoader.load(getClass().getResource("/br/usp/icmc/ssc01032015/bibliotecus/view/ListLoansTab.fxml"));
            listLoansTab.setContent(root);

            root = FXMLLoader.load(getClass().getResource("/br/usp/icmc/ssc01032015/bibliotecus/view/ListUsersTab.fxml"));
            listUsersTab.setContent(root);

            root = FXMLLoader.load(getClass().getResource("/br/usp/icmc/ssc01032015/bibliotecus/view/MyBooksTab.fxml"));
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
        Parent root = FXMLLoader.load(getClass().getResource("/br/usp/icmc/ssc01032015/bibliotecus/view/SignUp.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Sign Up User");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void bookRegister(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/br/usp/icmc/ssc01032015/bibliotecus/view/RegisterBook.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Register Book");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void userSignIn(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/br/usp/icmc/ssc01032015/bibliotecus/view/SignIn.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Sign In User");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void booksImport(ActionEvent actionEvent) throws FileNotFoundException, InstantiationException, IOException, Exception
    {
        Book book;
        int amount = 0;
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Books File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files (*csv)", "*.csv"));
        File file = fileChooser.showOpenDialog(currentUserLabel.getScene().getWindow());
        
        if(file != null && file.exists())
        {
            FileInputStream fileIO = new FileInputStream(file);
            while((book = CSVSerializer.read(fileIO, Book.class)) != null)
            {
                Library.getInstance().getBooks().add(book);
                amount++;
            }
            new Alert(AlertType.INFORMATION, amount + " Book(s) added!").show();
        }
        else
        {
            new Alert(AlertType.ERROR, "Error opening file!").show();
        }
    }

    public void usersImport(ActionEvent actionEvent) throws FileNotFoundException, InstantiationException, IOException, Exception
    {
        User user;
        int amount = 0;
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Users File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files (*csv)", "*.csv"));
        File file = fileChooser.showOpenDialog(currentUserLabel.getScene().getWindow());
        
        if(file != null && file.exists())
        {
            FileInputStream fileIO = new FileInputStream(file);
            while((user = CSVSerializer.read(fileIO, User.class)) != null)
            {
                Library.getInstance().getUsers().add(user);
                amount++;
            }
            new Alert(AlertType.INFORMATION, amount + " User(s) added!").show();
        }
        else
        {
            new Alert(AlertType.ERROR, "Error opening file!").show();
        }
        
    }

    public void loansImport(ActionEvent actionEvent) throws FileNotFoundException, InstantiationException, IOException
    {
        Loan loan = null;
        int lineCount = 0;
        int addedAmount = 0;
        String errorMessage = "";
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Loans File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files (*csv)", "*.csv"));
        File file = fileChooser.showOpenDialog(currentUserLabel.getScene().getWindow());
        
        if(file != null && file.exists())
        {
            FileInputStream fileIO = new FileInputStream(file);
            while(true)
            {
                
                try {
                    loan = CSVSerializer.read(fileIO, Loan.class);
                    if(loan == null) break;
                    Library.getInstance().getLoans().add(loan);
                    addedAmount++;
                } catch (Exception ex) {
                    errorMessage = errorMessage.concat("Line:" + lineCount + ex.getMessage() + '\n');
                }
            }
            new Alert(AlertType.INFORMATION, "Loans added: " + addedAmount + '\n' + errorMessage).show();
        }
        else
        {
            new Alert(AlertType.ERROR, "Error opening file!").show();
        }
    }

    public void booksExport(ActionEvent actionEvent) throws IllegalAccessException, IOException, InstantiationException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Books Path");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files (*csv)", "*.csv"));
        File file = fileChooser.showSaveDialog(currentUserLabel.getScene().getWindow());
        
        if(file != null && !file.exists())
        {
            FileOutputStream fileOS = new FileOutputStream(file);
            for(Book book : Library.getInstance().getBooks())
                CSVSerializer.write(book, fileOS);
        }
        else
        {
            new Alert(AlertType.ERROR, "Error creating file!").show();
        }
    }

    public void usersExport(ActionEvent actionEvent) throws IllegalAccessException, IOException, InstantiationException 
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Users Path");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files (*csv)", "*.csv"));
        File file = fileChooser.showSaveDialog(currentUserLabel.getScene().getWindow());
        
        if(file != null && !file.exists())
        {
            FileOutputStream fileOS = new FileOutputStream(file);
            
            for(User user : Library.getInstance().getUsers())
                CSVSerializer.write(user, fileOS);
        }
        else
        {
            new Alert(AlertType.ERROR, "Error creating file!").show();
        }
    }

    public void loansExport(ActionEvent actionEvent) throws IllegalAccessException, IOException, InstantiationException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Loans Export");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files (*csv)", "*.csv"));
        File file = fileChooser.showSaveDialog(currentUserLabel.getScene().getWindow());
        
        if(file != null && !file.exists())
        {
            FileOutputStream fileOS = new FileOutputStream(file);
            
            for(Loan loan : Library.getInstance().getLoans())
                CSVSerializer.write(loan, fileOS);
        }
        else
        {
            new Alert(AlertType.ERROR, "Error creating file!").show();
        }
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