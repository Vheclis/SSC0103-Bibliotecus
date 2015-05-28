package br.usp.icmc.ssc01032015.bibliotecus.controller;


import br.usp.icmc.ssc01032015.bibliotecus.model.Book;
import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.Loan;
import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ListLoansTabController implements Initializable
{
    @FXML
    private TableView loansTable;

    @FXML
    private TableColumn userCol;

    @FXML
    private TableColumn bookCol;

    @FXML
    private TableColumn checkOutCol;

    @FXML
    private TableColumn checkInCol;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        loansTable.setItems(Library.getInstance().getLoans());
        bookCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>()
        {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures param)
            {
                return new SimpleStringProperty(((Loan) param.getValue()).getBook().getTitle());
            }
        });

        userCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>()
        {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures param)
            {
                return new SimpleStringProperty(((Loan) param.getValue()).getUser().getName());
            }
        });

        checkOutCol.setCellValueFactory(new PropertyValueFactory<Loan, String>("checkOut"));
        checkInCol.setCellValueFactory(new PropertyValueFactory<Loan, String>("checkIn"));
    }
}
