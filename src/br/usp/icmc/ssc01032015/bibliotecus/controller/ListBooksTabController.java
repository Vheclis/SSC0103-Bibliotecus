package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Book;
import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.Loan;
import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import java.util.List;
import java.util.ResourceBundle;

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

    private ObservableList<Book> booksView;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //table column bindings
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalQuantity"));
        availableCol.setCellValueFactory(new PropertyValueFactory<>("currentQuantity"));
        typeCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().name));

        //table binding
        booksView = FXCollections.observableArrayList();
        booksTable.setItems(booksView);


        //update table when date or books are onCurrentDateChange
        Library.getInstance().currentDateProperty().addListener((observable, oldValue, newValue) -> updateTable());
        Library.getInstance().getBooks().addListener((ListChangeListener.Change<? extends Book> c) -> updateTable());

        //enable-disable borrow button if not logged in or read only state
        Library.getInstance().currentUserProperty()
                .addListener((observable, oldValue, newValue) -> updateBorrowButton());
        Library.getInstance().newestLoanDateProperty()
                .addListener((observable, oldValue, newValue) -> updateBorrowButton());
        Library.getInstance().currentDateProperty()
                .addListener((observable, oldValue, newValue) -> updateBorrowButton());

        //start logged out
        updateBorrowButton();
    }

    private void updateTable()
    {
        //books registered until library's current date
        List<Book> books =
                Library.getInstance().getBooks()
                        .filtered(book ->
                        {
                            long registration = book.getRegistration().toEpochDay();
                            long today = Library.getInstance().getCurrentDate().toEpochDay();
                            return registration <= today;
                        });

        booksView.setAll(books);
    }

    private void updateBorrowButton()
    {
        Library library = Library.getInstance();
        if(library.getCurrentUser() == null
                || library.getCurrentDate().toEpochDay() < library.getNewestLoanDate().toEpochDay())
            borrowButton.setDisable(true);
        else borrowButton.setDisable(false);
    }

    public void onBorrow(ActionEvent actionEvent)
    {
        Library library = Library.getInstance();
        User currentUser = library.getCurrentUser();

        //test for suspention
        int daysSuspended = library.calculateUserSuspension(currentUser);
        if(daysSuspended > 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("You are suspended");
            alert.setContentText("You can not borrow any book until "
                    + library.getCurrentDate().plusDays(daysSuspended));
            alert.show();
            return;
        }

        //test how much books user has
        List<Loan> loans = Library.getInstance().getLoans()
                .filtered(loan -> loan.getUser().getName().equals(Library.getInstance().getCurrentUser().getName()))
                .filtered(loan -> loan.getCheckIn() == null);
        if((currentUser.getType() == User.Type.Student) && (loans.size() >= 4))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Limits of borrow achieved");
            alert.setContentText("Students can only borrow 4 books at the same time");
            alert.show();
            return;
        }

        if((currentUser.getType() == User.Type.Professor) && (loans.size() >= 6))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Limits of borrow achieved");
            alert.setContentText("Professors can only borrow 6 books at the same time");
            alert.show();
            return;
        }

        if((currentUser.getType() == User.Type.Community) && (loans.size() >= 2))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Limits of borrow achieved");
            alert.setContentText("Community users can only borrow 2 books at the same time");
            alert.show();
            return;
        }

        //test for selected book
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if(selectedBook == null || selectedBook.getCurrentQuantity() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Book unavailable");
            alert.setContentText("There are no copies of this book in stock");
            alert.show();
            return;
        }

        //test if the user type is allowed to borrow that book
        if((selectedBook.getType() == Book.Type.Textbook) && (currentUser.getType() == User.Type.Community))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Borrow not allowed");
            alert.setContentText("Community users can't borrow textbooks");
            alert.show();
            return;
        }
        //add loan
        Loan loan = new Loan(currentUser, selectedBook, library.getCurrentDate());
        library.getLoans().add(loan);

        //warn due date
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("You have borrowed \"" + loan.getBook().getTitle()+ "\"");
        alert.setContentText("Return it until " + loan.getDueDate());
    }
}
