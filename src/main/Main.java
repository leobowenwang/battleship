package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("startmenu.fxml"));
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(new Scene(root, 1700, 900));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:src/main/resources/bow_hit.png"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
