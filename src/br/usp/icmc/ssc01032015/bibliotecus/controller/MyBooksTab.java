package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.Loan;
import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MyBooksTab implements Initializable
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
        Library.getInstance().currentUserProperty()
                .addListener((observable, oldValue, newValue) -> onUserChange(newValue));

        bookCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUser().getName()));
        checkOutCol.setCellValueFactory(new PropertyValueFactory<Loan, LocalDate>("checkOut"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<Loan, LocalDate>("dueDate"));

        myLoans = FXCollections.observableArrayList();
        myBooksTable.setItems(myLoans);
    }

    private void onUserChange(User newUser)
    {
        List<Loan> loans = Library.getInstance().getLoans()
                .stream()
                .filter(loan -> loan.getUser().getName().equals(newUser.getName()))
                .collect(Collectors.toList());

        myLoans.setAll(loans);
    }
}
