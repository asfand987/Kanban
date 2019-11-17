package KanbanBoard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;

public class Kanban extends Application {
    @FXML private AnchorPane Card;

    @FXML private AnchorPane Column;

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