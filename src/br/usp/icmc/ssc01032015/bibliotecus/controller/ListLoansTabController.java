package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.Loan;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ListLoansTabController implements Initializable
{
    @FXML
    private TableView<Loan> loansTable;

    @FXML
    private TableColumn<Loan, String> userCol;

    @FXML
    private TableColumn<Loan, String> bookCol;

    @FXML
    private TableColumn<Loan, LocalDate> checkOutCol;

    @FXML
    private TableColumn<Loan, LocalDate> checkInCol;

    @FXML
    private TableColumn<Loan, LocalDate> dueDateCol;

    private ObservableList<Loan> loansView;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //table column bindings
        checkOutCol.setCellValueFactory(new PropertyValueFactory<Loan, LocalDate>("checkOut"));
        checkInCol.setCellValueFactory(new PropertyValueFactory<Loan, LocalDate>("checkIn"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<Loan, LocalDate>("dueDate"));
        bookCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBook().getTitle()));
        userCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUser().getName()));

        //table binding
        loansView = FXCollections.observableArrayList();
        loansTable.setItems(loansView);

        //update table when loans, current date or user change
        Library.getInstance().getLoans().addListener((ListChangeListener.Change<? extends Loan> c) -> updateLoans());
        Library.getInstance().currentDateProperty().addListener((observable, oldValue, newValue) -> updateLoans());
        Library.getInstance().currentUserProperty().addListener((observable, oldValue, newValue) -> updateLoans());
    }

    private void updateLoans()
    {
        //loans checked out unitl library's current date
        List<Loan> loans =
                Library.getInstance().getLoans()
                        .filtered(loan ->
                        {
                            long checkout = loan.getCheckOut().toEpochDay();
                            long today = Library.getInstance().getCurrentDate().toEpochDay();
                            return checkout <= today;
                        });

        loansView.setAll(loans);
    }
}
