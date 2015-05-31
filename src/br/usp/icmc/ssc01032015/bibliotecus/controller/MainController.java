package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Book;
import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.Loan;
import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import br.usp.icmc.ssc01032015.bibliotecus.serialization.CSVSerializable;
import br.usp.icmc.ssc01032015.bibliotecus.serialization.CSVSerializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    public void booksImport(ActionEvent actionEvent) throws InstantiationException, IOException
    {
        File file = openFileDialog("books");
        if (file == null) return;

        if (file.exists())
        {
            List<Book> books = parseCSV(file, Book.class);
            Library.getInstance().getBooks().addAll(books);

            new Alert(AlertType.INFORMATION, books.size() + " book(s) added!").show();
        } else
        {
            new Alert(AlertType.ERROR, "Error opening file").show();
        }
    }

    public void usersImport(ActionEvent actionEvent) throws InstantiationException, IOException, IllegalAccessException
    {
        File file = openFileDialog("users");
        if (file == null) return;

        if (file.exists())
        {
            List<User> users = parseCSV(file, User.class);
            Library.getInstance().getUsers().addAll(users);

            new Alert(AlertType.INFORMATION, users.size() + " user(s) added!").show();
        } else
        {
            new Alert(AlertType.ERROR, "Error opening file").show();
        }
    }

    public void loansImport(ActionEvent actionEvent) throws InstantiationException, IOException
    {
        File file = openFileDialog("loans");
        if (file == null) return;

        if (file.exists())
        {
            List<Loan> loans = parseCSV(file, Loan.class);
            Library.getInstance().getLoans().addAll(loans);

            new Alert(AlertType.INFORMATION, loans.size() + " loan(s) added!").show();
        } else
        {
            new Alert(AlertType.ERROR, "Error opening file").show();
        }
    }

    private File openFileDialog(String subject)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import " + subject);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files (*csv)", "*.csv"));
        return fileChooser.showOpenDialog(currentUserLabel.getScene().getWindow());
    }

    private <T extends CSVSerializable> List<T> parseCSV(File file, Class<T> c) throws IOException
    {
        List<T> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file))))
        {
            reader.lines().forEach(line ->
            {
                try
                {
                    items.add(CSVSerializer.read(line, c));
                } catch (IllegalAccessException | InstantiationException e)
                {
                    e.printStackTrace();
                }
            });
        }
        return items;
    }

    public void booksExport(ActionEvent actionEvent) throws FileNotFoundException
    {
        File file = openSaveDialog("books");
        if (file == null) return;
        FileOutputStream fileOS = new FileOutputStream(file);
        for (Book book : Library.getInstance().getBooks())
            CSVSerializer.write(book, fileOS);
    }


    public void usersExport(ActionEvent actionEvent) throws FileNotFoundException
    {
        File file = openSaveDialog("users");
        if (file == null) return;
        FileOutputStream fileOS = new FileOutputStream(file);
        for (User user: Library.getInstance().getUsers())
            CSVSerializer.write(user, fileOS);
    }

    public void loansExport(ActionEvent actionEvent) throws FileNotFoundException
    {
        File file = openSaveDialog("loans");
        if (file == null) return;
        FileOutputStream fileOS = new FileOutputStream(file);
        for (Loan loan : Library.getInstance().getLoans())
            CSVSerializer.write(loan, fileOS);
    }

    private File openSaveDialog(String subject)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export " + subject);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files (*csv)", "*.csv"));
        return fileChooser.showSaveDialog(currentUserLabel.getScene().getWindow());
    }

    private void onCurrentUserChanged(User newUser)
    {
        if (newUser != null)
        {
            currentUserLabel.setText(newUser.getName());
            myBooksTab.setDisable(false);
        } else
        {
            currentUserLabel.setText("None");
        }
    }


    public void onDateChange(ActionEvent actionEvent)
    {
        Library.getInstance().setCurrentDate(datePicker.getValue());
    }
}