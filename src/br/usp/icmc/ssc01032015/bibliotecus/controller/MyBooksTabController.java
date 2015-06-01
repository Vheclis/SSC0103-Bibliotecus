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
        import javafx.scene.control.Alert;
        import javafx.scene.control.Button;
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

    @FXML
    private Button checkInButton;

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
        if(Library.getInstance().getCurrentUser() == null) return;

        long today = Library.getInstance().getCurrentDate().toEpochDay();
        long newestLoanDate = Library.getInstance().getNewestLoanDate().toEpochDay();
        checkInButton.setDisable(today < newestLoanDate);

        List<Loan> loans = Library.getInstance().getLoans()
                .filtered(loan -> loan.getUser().getName().equals(Library.getInstance().getCurrentUser().getName()))
                .filtered(loan -> loan.getCheckOut().isBefore(Library.getInstance().getCurrentDate())
                        || loan.getCheckOut().isEqual(Library.getInstance().getCurrentDate()))
                .filtered(loan -> loan.getCheckIn() == null ||
                        loan.getCheckIn().toEpochDay() > Library.getInstance().getCurrentDate().toEpochDay());
        myLoans.setAll(loans);
    }

    public void onCheckIn(ActionEvent actionEvent)
    {
        Loan selectedLoan = myBooksTable.getSelectionModel().getSelectedItem();
        if(selectedLoan == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Select a loan");
            alert.setContentText("You must select a loan");
            alert.show();

            return;
        }

        selectedLoan.setCheckIn(Library.getInstance().getCurrentDate());
        Library.getInstance().getLoans().remove(selectedLoan);
        Library.getInstance().getLoans().add(selectedLoan);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Check in");
        alert.setContentText(selectedLoan.getBook().getTitle() + " has been checked back in");
        alert.show();
    }
}