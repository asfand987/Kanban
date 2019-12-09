package KanbanBoard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Kanban extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        primaryStage.setTitle("Task Management System v1.0");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        HomeController controller = new HomeController();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
