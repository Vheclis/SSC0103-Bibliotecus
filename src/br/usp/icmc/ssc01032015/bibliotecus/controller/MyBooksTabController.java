package br.usp.icmc.ssc01032015.bibliotecus.controller;

        import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
        import br.usp.icmc.ssc01032015.bibliotecus.model.Loan;
        import javafx.beans.property.SimpleStringProperty;
        import javafx.collections.FXCollections;
        import javafx.collections.ListChangeListener;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.cell.PropertyValueFactory;

        import java.net.URL;
        import java.time.LocalDate;
        import java.util.List;
        import java.util.ResourceBundle;

public class MyBooksTabController implements Initializable
{
    @FXML
    private TableView<Loan> myBooksTable;

    @FXML
    private TableColumn<Loan, String> bookCol;

    @FXML
    private TableColumn<Loan, LocalDate> checkOutCol;

    @FXML
    private TableColumn<Loan, LocalDate> dueDateCol;

    private ObservableList<Loan> myLoans;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //table column bindings
        bookCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBook().getTitle()));
        checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        //table bindings
        myLoans = FXCollections.observableArrayList();
        myBooksTable.setItems(myLoans);

        //update my books when user
        Library.getInstance().currentUserProperty().addListener((observable, oldValue, newValue) -> updateMyBooks());
        Library.getInstance().currentDateProperty().addListener((observable, oldValue, newValue) -> updateMyBooks());
        Library.getInstance().getLoans().addListener((ListChangeListener.Change<? extends Loan> c) -> updateMyBooks());

    }

    private void updateMyBooks()
    {
        List<Loan> loans = Library.getInstance().getLoans()
                .filtered(loan -> loan.getUser().getName().equals(Library.getInstance().getCurrentUser().getName()))
                .filtered(loan -> loan.getCheckOut().isBefore(Library.getInstance().getCurrentDate())
                        || loan.getCheckOut().isEqual(Library.getInstance().getCurrentDate()))
                .filtered(loan -> loan.getCheckIn() == null);
        myLoans.setAll(loans);

    }

    public void onCheckIn(ActionEvent actionEvent) {

        Loan selectedLoan = myBooksTable.getSelectionModel().getSelectedItem();
        selectedLoan.setCheckIn(Library.getInstance().getCurrentDate());
        Library.getInstance().getLoans().remove(selectedLoan);
        Library.getInstance().getLoans().add(selectedLoan);
        //updateMyBooks();
    }
}