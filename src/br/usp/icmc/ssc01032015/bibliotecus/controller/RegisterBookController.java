package br.usp.icmc.ssc01032015.bibliotecus.controller;

import br.usp.icmc.ssc01032015.bibliotecus.model.Book;
import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
    }

    public void onRegister(ActionEvent actionEvent)
    {
        ObservableList<Book> books = Library.getInstance().getBooks();

        Optional<Book> existingBook = books.stream()
                .filter(book -> book.getTitle().equals(titleField.getText()))
                .findFirst();

        if(existingBook.isPresent())
        {
            //trying to add an existing book
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("There is already a book named \"" + existingBook.get().getTitle() + "\"");
            alert.setContentText("We don't accept late duplicates");
            alert.show();
        }
        else
        {
            //add new book
            Book newBook = new Book(titleField.getText(),
                                    authorField.getText(),
                                    Book.Type.valueOf((String) typeBox.getValue()),
                                    quantitySpinner.getValue());

            Library.getInstance().getBooks().add(newBook);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("New book");
            alert.setContentText("Book \"" + newBook.getTitle() + "\" added");
            alert.show();
        }

        Stage stage = ((Stage) titleField.getScene().getWindow());
        stage.close();
    }
}