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

    @FXML
    private void addNewBoard() {
        //newBoard.setOnAction((event -> {
        Button Board = new Button();
        Board.setId("New");
        Board.setPrefSize(210, 500);
        Board.setText(newBoard.getText());
        pane.getChildren().add(Board);
        board nBoard = new board(newBoard.getText());
        GlobalData.BoardList.add(nBoard);
        int index=GlobalData.BoardList.size()-1;
        Board.setOnMouseClicked(event1 -> {
            newBoard(index);
        });
    }

    @FXML
    private void newBoard(int index){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Kanban.fxml"));
            Parent root2 = fxmlLoader.load();
            Controller controller = new Controller();
            fxmlLoader.setController(controller);
            Stage stage = new Stage();
            stage.setTitle(GlobalData.BoardList.get(index).getBoardTitle());
            stage.setScene(new Scene(root2));
            GlobalData.CurrentIndex=index;
            stage.show();
            controller.myinit(index,stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

