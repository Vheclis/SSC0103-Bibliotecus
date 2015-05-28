package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Book;
import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.Loan;
import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ListBooksTabController implements Initializable
{
    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> titleCol;

    @FXML
    private TableColumn<Book, String> authorCol;

    @FXML
    private TableColumn<Book, String> typeCol;

    @FXML
    private TableColumn<Book, Integer> availableCol;

    @FXML
    private TableColumn<Book, Integer> totalCol;

    @FXML
    private Button borrowButton;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        booksTable.setItems(Library.getInstance().getBooks());

        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        totalCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("totalQuantity"));
        availableCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("currentQuantity"));

        typeCol.setCellValueFactory(param ->
                new SimpleStringProperty(((Book)((TableColumn.CellDataFeatures)param).getValue()).getType().name));

        Library.getInstance().currentUserProperty()
                .addListener((observable, oldValue, newValue) -> onChangeUser(newValue));
        onChangeUser(null);

        Library.getInstance().dateProperty().addListener((observable, oldValue, newValue) -> onChangeDate(newValue));
    }

    private void onChangeUser(User newUser)
    {
        borrowButton.setDisable(newUser == null);
    }

    private void onChangeDate(LocalDate newDate)
    {
    }

    public void onBorrow(ActionEvent actionEvent)
    {
        Library library = Library.getInstance();
        User currentUser = library.getCurrentUser();

        //test for suspention
        int daysSuspended = library.calculateUserSuspension(currentUser);
        if(daysSuspended > 0)
        {
            new Alert(Alert.AlertType.ERROR,
                    "You can not borrow any book until " + library.getDate().plusDays(daysSuspended)).show();
            return;
        }

        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if(selectedBook == null || selectedBook.getCurrentQuantity() == 0)
        {
            new Alert(Alert.AlertType.ERROR,
                    "There are no copies of this book available").show();
            return;
        }

        library.getLoans().add(new Loan(currentUser, selectedBook, library.getDate()));
    }
}
