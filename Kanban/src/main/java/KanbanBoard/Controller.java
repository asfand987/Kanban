package KanbanBoard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.ArrayList;

public class Controller {
    @FXML private Button Log;
    @FXML private Button card;
    @FXML private Pane delCard;
    @FXML private Button currentCard; //when a user clicks card
    @FXML private TextField newCol;
    @FXML private TextField newCard;
    @FXML private TextField colTitle;
    @FXML private TextField boardTitle;
    @FXML private VBox row;
    @FXML private HBox col;
    @FXML private Button exit;
    @FXML
    ArrayList<Button> inner = new ArrayList<Button>();
    @FXML
    private ArrayList<Button> cardCount;

    private void initialize() {
    }

    @FXML
    private void handleLabel(MouseEvent event) {
        Object p = (Button) event.getSource();
        currentCard = (Button) p;
    }

    @FXML
    private void addNewCol(String title) {
        // int count = 0;
        //outer.add(inner);
    }

    @FXML
    private void addNewCard(String name) {
        int count = 0;
        inner.add(count, card);
        card.setText(name);
    }

    @FXML
    private void addCardPress() {
        newCard.setOnAction((event) -> {
            Button but = new Button();
            but.setPrefWidth(160);
            but.setText(newCard.getText());
            row.getChildren().add(but);
            but.setOnDragDetected(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent event){
                    Dragboard db = but.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    WritableImage snapshot = but.snapshot(new SnapshotParameters(), null);
                    db.setDragView(snapshot);
                    content.putString("rec");
                    db.setContent(content);
                    event.consume();
                }
            });
            but.setOnDragDone((DragEvent e) -> {
                if (e.getTransferMode() == TransferMode.MOVE) {
                    row.getChildren().remove(but);
                   // but.rem;
                }
                e.consume();
            });
            newCard.clear();
        });

    }

    @FXML
    private void addColPress() {
        Button title = new Button();
        title.setPrefWidth(160);
        newCol.setOnAction((event) -> {
            title.setText(newCol.getText());
            col.getChildren().add(title);
        });
    }

    @FXML
    public void exitApp(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void dragOver()
    {
        delCard.setOnDragOver((DragEvent event) -> {
                event.acceptTransferModes(TransferMode.MOVE);
                event.consume();
            });
    }

    @FXML
    public void dragDropped() {
        delCard.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                Button tempBoat = new Button(db.getString());
                tempBoat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                //System.out.println("DONE");
                delCard.getChildren().add(tempBoat);
                delCard.getChildren().clear();
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
}



