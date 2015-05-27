package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterBookController implements Initializable
{
    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private ComboBox typeBox;

    @FXML
    private Spinner quantitySpinner;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        typeBox.getSelectionModel().selectFirst();
    }

    public void onRegister(ActionEvent actionEvent)
    {
        new Book(titleField.getText(),
                authorField.getText(),
                Book.Type.valueOf((String)typeBox.getValue()),
                (int)quantitySpinner.getValue());
    }
}