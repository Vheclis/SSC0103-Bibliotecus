package br.usp.icmc.ssc01032015.bibliotecus.controller;


import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.Loan;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        checkOutCol.setCellValueFactory(new PropertyValueFactory<Loan, LocalDate>("checkOut"));
        checkInCol.setCellValueFactory(new PropertyValueFactory<Loan, LocalDate>("checkIn"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<Loan, LocalDate>("dueDate"));
        bookCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBook().getTitle()));
        userCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUser().getName()));

        loansTable.setItems(Library.getInstance().getLoans());

//        Library.getInstance().dateProperty().addListener((observable, oldValue, newValue) -> onChangeDate(newValue));
//        onChangeDate(LocalDate.now());
    }

    private void onChangeDate(LocalDate newDate)
    {
//        loansTable.setItems(loans);
    }
}
