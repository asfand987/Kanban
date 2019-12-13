package KanbanBoard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class HomeController {
    @FXML
    private HBox pane = new HBox();
    @FXML
    public TextField newBoard = new TextField();

    private void initialize() {
    }
/*             Button butt = new Button();
               butt.setPrefWidth(160);
               butt.setText(addCardTitle.getText());
               column.getChildren().add(butt);
               addCardTitle.clear();
               addCardTitle.setPromptText("Add new Card");*/

    @FXML
    private void addNewBoard() {
        //newBoard.setOnAction((event -> {
        Button Board = new Button();
        Board.setPrefSize(210, 500);
        Board.setText(newBoard.getText());
        pane.getChildren().add(Board);


        Board.setOnMouseClicked(event1 -> {
            newBoard();
        });
        // }));
    }

    @FXML
    private void newBoard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Kanban.fxml"));
            Parent root2 = fxmlLoader.load();
            Controller controller = new Controller();
            fxmlLoader.setController(controller);
            Stage stage = new Stage();
            stage.setTitle(newBoard.getText());
            stage.setScene(new Scene(root2));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

       /* @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        primaryStage.setTitle("Task Management System v1.0");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        HomeController controller = new HomeController();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);

    }
*/


}

/*@FXML
    public void setNewBoard()  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Kanban.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(newBoard.getText());
            stage.setScene(new Scene(root1));
            stage.show();
            title.setText(newButton.getText());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }*/
