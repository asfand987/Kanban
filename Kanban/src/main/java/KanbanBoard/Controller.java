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
import java.util.*;
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
    @FXML private VBox row1;

    @FXML private HBox col;
    @FXML private Button exit;
    @FXML
    ArrayList<Button> inner = new ArrayList<Button>();
    @FXML
    private ArrayList<Button> cardCount;
    private LinkedList<String> p = new LinkedList<>();



    @FXML
    private void handleLabel(MouseEvent event) {
        Object p = (Button) event.getSource();
        currentCard = (Button) p;
    }






    @FXML
    private void addColPress()
    {


        newCol.setOnAction((enterEvent)->
        {
            VBox column = new VBox();
            column.setPrefSize(160,570);
            TextField colTitle = new TextField();
            colTitle.setPrefWidth(160);
            TextField addCardTitle = new TextField();
            addCardTitle.setPrefWidth(160);
            colTitle.setText(newCol.getText());
            col.getChildren().add(column);
            column.getChildren().add(colTitle);
            column.getChildren().add(addCardTitle);
            addCardTitle.setPromptText("Add New Card");
            newCol.clear();
            newCol.setPromptText("Add new Column");

            addCardTitle.setOnAction((newCardEvent)->
            {

                Button butt = new Button();
                butt.setPrefWidth(160);
                butt.setText(addCardTitle.getText());
                column.getChildren().add(butt);
                addCardTitle.clear();
                addCardTitle.setPromptText("Add new Card");
                ///
               /* p.add("User added " + newCard.getText()+ " to "+ addCardTitle.getText());
                row1.getChildren().clear();
                for(int i=p.size()-1; i >= 0 ; i--) {

                    Button but1 = new Button();

                    but1.setText(p.get(i) );
                    row1.getChildren().add(but1);
                }
                System.out.println(column.getChildren().size()-2);*/
                ///

                butt.setOnDragDetected(new EventHandler<MouseEvent>()
                {
                    @Override public void handle(MouseEvent event)
                    {
                        Dragboard db = butt.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent content = new ClipboardContent();
                        WritableImage snapshot = butt.snapshot(new SnapshotParameters(), null);
                        db.setDragView(snapshot);
                        content.putString("rec");
                        db.setContent(content);
                        event.consume();
                    }
                });
                butt.setOnDragDone((DragEvent even) ->
                {
                    if (even.getTransferMode() == TransferMode.MOVE)
                    {
                       /* p.add("User deleted " +butt.getText()+ " from "+ addCardTitle.getText());
                        row1.getChildren().clear();
                        for(int i=p.size()-1; i >= 0 ; i--) {

                            Button but2 = new Button();

                            but2.setText( p.get(i)  );
                            row1.getChildren().add(but2);
                        }*/
                        column.getChildren().remove(butt);
                        // but.rem;
                    }
                    even.consume();
                });
            });
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



