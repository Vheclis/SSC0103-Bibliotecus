package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ListUsersTabController implements Initializable
{
    @FXML
    private TableView usersTable;

    @FXML
    private TableColumn nameCol;

    @FXML
    private TableColumn typeCol;



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        usersTable.setItems(Library.getInstance().getUsers());

        nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<User, String>("typeName"));

    }
}
