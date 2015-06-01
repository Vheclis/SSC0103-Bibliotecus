package br.usp.icmc.ssc01032015.bibliotecus;

import br.usp.icmc.ssc01032015.bibliotecus.controller.MainController;
import br.usp.icmc.ssc01032015.bibliotecus.model.Book;
import br.usp.icmc.ssc01032015.bibliotecus.model.Library;
import br.usp.icmc.ssc01032015.bibliotecus.model.Loan;
import br.usp.icmc.ssc01032015.bibliotecus.model.User;
import br.usp.icmc.ssc01032015.bibliotecus.serialization.CSVSerializable;
import br.usp.icmc.ssc01032015.bibliotecus.serialization.CSVSerializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/usp/icmc/ssc01032015/bibliotecus/view/Main.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        Stage stage = new Stage();
        controller.startupStage(stage);
        stage.setTitle("Bibliotecus");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private static <T extends CSVSerializable> void initialImport(String filename, Class<T> c, Consumer<List<T>> consumer)
    {
        File file = new File(System.getProperty("user.dir"), filename);
        if (file.exists())
        {
            try
            {
                List<T> list = CSVSerializer.importItems(file, c);
                consumer.accept(list);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        initialImport("books.csv", Book.class, books -> Library.getInstance().getBooks().addAll(books));
        initialImport("users.csv", User.class, users -> Library.getInstance().getUsers().addAll(users) );
        initialImport("loans.csv", Loan.class, loans -> Library.getInstance().getLoans().addAll(loans));

        launch(args);
    }
}
