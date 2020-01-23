package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController
{

    @FXML
    void startSingleplayerGame(ActionEvent event) throws IOException
    {

        GameController.isMultiplayer = false;
        Parent newScene = FXMLLoader.load(getClass().getResource("battleship.fxml"));
        Scene scene = new Scene(newScene);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
        appStage.getIcons().add(new Image("file:src/main/resources/bow_hit.png"));
    }

    public void leaveGame()
    {
        System.exit(0);
    }

}
