package br.usp.icmc.ssc01032015.bibliotecus;

import br.usp.icmc.ssc01032015.bibliotecus.controller.MainController;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/usp/icmc/ssc01032015/bibliotecus/view/Main.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        Stage stage = new Stage();
        controller.startupStage(stage);
        stage.setTitle("Bibliotecus");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
