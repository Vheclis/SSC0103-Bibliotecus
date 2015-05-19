package br.usp.icmc.ssc01032015.bibliotecus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Stage stage = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
        stage.show();
    }

    public static void main(String[] args) throws IOException
    {
        launch(args);
    }
}
