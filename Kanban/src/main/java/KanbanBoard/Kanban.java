package KanbanBoard;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.Label;
import javafx.event.ActionEvent;
//import java.awt.*;
//import java.awt.event.ActionEvent;
import java.io.IOException;

public class Kanban extends Application {
    @FXML private Button closeButton;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Kanban.fxml"));
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}