package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListUsersTabController implements Initializable
{
    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> nameCol;

    @FXML
    private TableColumn<User, String> typeCol;

    private ObservableList<User> usersView;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //table column bindings
        nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<User, String>("typeName"));

        //table bindings
        usersView = FXCollections.observableArrayList();
        usersTable.setItems(usersView);

        //update table when date or users are onCurrentDateChange
        Library.getInstance().currentDateProperty().addListener((observable, oldValue, newValue) -> updateUsers());
        Library.getInstance().getUsers().addListener((ListChangeListener.Change<? extends User> c) -> updateUsers());
    }

    private void updateUsers()
    {
        //users registered until library's current day
        List<User> users =
                Library.getInstance().getUsers()
                        .filtered(user ->
                        {
                            long registration = user.getRegistration().toEpochDay();
                            long today = Library.getInstance().getCurrentDate().toEpochDay();

                            return registration <= today;
                        });

        usersView.setAll(users);
    }
}
