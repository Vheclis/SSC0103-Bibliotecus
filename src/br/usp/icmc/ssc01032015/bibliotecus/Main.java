package br.usp.icmc.ssc01032015.bibliotecus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/br/usp/icmc/ssc01032015/bibliotecus/view/Main.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Bibliotecus");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
