package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Book;
import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ListBooksTabController implements Initializable
{
    @FXML
    private TableView booksTable;

    @FXML
    private TableColumn titleCol;

    @FXML
    private TableColumn authorCol;

    @FXML
    private TableColumn typeCol;

    @FXML
    private TableColumn quantityCol;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        booksTable.setItems(Library.getInstance().getBooks());

        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Book, String>("typeName"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("totalQuantity"));
    }
}
