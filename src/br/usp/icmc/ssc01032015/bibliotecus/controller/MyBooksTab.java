package br.usp.icmc.ssc01032015.bibliotecus.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MyBooksTab implements Initializable
{
    @FXML
    private TableView myBooksTable;

    @FXML
    private TableColumn bookCol;

    @FXML
    private TableColumn checkOutCol;

    @FXML
    private TableColumn dueDateCol;

    @FXML
    private TableColumn statusCol;

    @FXML
    private TableColumn suspensionCol;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
