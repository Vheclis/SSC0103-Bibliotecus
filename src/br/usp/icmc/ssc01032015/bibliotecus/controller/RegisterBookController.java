package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Book;
import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
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
    private Spinner<Integer> quantitySpinner;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        typeBox.getSelectionModel().selectFirst();
    }

    public void onRegister(ActionEvent actionEvent)
    {
        ObservableList<Book> books = Library.getInstance().getBooks();

        Optional<Book> existingBook = books.stream()
                .filter(book -> book.getTitle().equals(titleField.getText()))
                .findFirst();

        if(existingBook.isPresent())
        {
            new Alert(Alert.AlertType.INFORMATION,
                    "There is already a book named \"" +existingBook.get().getTitle() + "\"").show();
        }
        else
        {
            Book newBook = new Book(titleField.getText(),
                                    authorField.getText(),
                                    Book.Type.valueOf((String) typeBox.getValue()),
                                    quantitySpinner.getValue());

            Library.getInstance().getBooks().add(newBook);

            new Alert(Alert.AlertType.INFORMATION, "Book \"" + newBook.getTitle() + "\" added").show();
        }

        Stage stage = ((Stage) titleField.getScene().getWindow());
        stage.close();

    }
}