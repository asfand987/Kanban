package KanbanBoard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;

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
    private LinkedList<String> p = new LinkedList<String>();
    private VBox te = new VBox();

    @FXML
    private void addColPress()
    {
            VBox column = new VBox();
            Button del = new Button();
            del.setText("X");
            column.setPrefSize(160,570);
            TextField colTitle = new TextField();
            colTitle.setPrefWidth(160);
            System.out.println(column.getChildren().size()-2);
            TextField addCardTitle = new TextField();
            addCardTitle.setPrefWidth(160);
            colTitle.setText(newCol.getText());
            p.add("User added column " + colTitle.getText() );
             row1.getChildren().clear();
            for(int i=p.size()-1; i >= 0 ; i--) {
            Button but1 = new Button();
            but1.setText(p.get(i) );
            row1.getChildren().add(but1);
        }
            column.getChildren().add(del);
            col.getChildren().add(column);
            column.getChildren().add(colTitle);
            column.getChildren().add(addCardTitle);
            addCardTitle.setPromptText("Add New Card");
            newCol.clear();
            newCol.setPromptText("Add new Column");
            String style = "-fx-background-color: rgba(255, 255, 255, 0.5);";
            column.setStyle(style);
            Parent coll = del.getParent();
            del.setOnAction(e-> {
                col.getChildren().remove(coll);
                p.add("User deleted column " + colTitle.getText() );
                row1.getChildren().clear();
                for(int i=p.size()-1; i >= 0 ; i--) {

                    Button but1 = new Button();

                    but1.setText(p.get(i) );
                    row1.getChildren().add(but1);
                }
                System.out.println(column.getChildren().size()-2);
            });
            column.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent event) {
                    Dragboard db = column.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    WritableImage snapshot = column.snapshot(new SnapshotParameters(), null);
                    db.setDragView(snapshot);
                    content.putString(column.toString());
                    db.setContent(content);
                    te = column;
                    col.getChildren().remove(column);
                    event.consume();
                }
            });

            column.setOnDragDone((DragEvent even) ->
            {
                if (even.getTransferMode() == TransferMode.MOVE)
                {
                    col.getChildren().remove(column);
                    System.out.println("Removed");
                }
                even.consume();
            });

            col.setOnDragOver((DragEvent event) -> {
                event.acceptTransferModes(TransferMode.ANY);
                event.consume();
            });

            col.setOnDragDropped((DragEvent event) -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    VBox node = new VBox();
                    //node.setUserData(db.getString());
                   // column.setUserData(db.getString());
                    col.getChildren().addAll(te);
                    System.out.println("Drop detected");
                    event.setDropCompleted(true);
                    //col.getChildren();
                }
                event.setDropCompleted(success);
                event.consume();
            });

            addCardTitle.setOnAction((newCardEvent)->
            {
                Button butt = new Button();
                butt.setPrefWidth(160);
                butt.setText(addCardTitle.getText());
                column.getChildren().add(butt);
                addCardTitle.clear();
                addCardTitle.setPromptText("Add new Card");
                p.add("User added card " + butt.getText()+ " to "+ addCardTitle.getText());
                row1.getChildren().clear();
                for(int i=p.size()-1; i >= 0 ; i--) {

                    Button but1 = new Button();

                    but1.setText(p.get(i) );
                    row1.getChildren().add(but1);
                }
                System.out.println(column.getChildren().size()-2);


                butt.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent event)
                    {
                        Dragboard db = butt.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent content = new ClipboardContent();
                        WritableImage snapshot = butt.snapshot(new SnapshotParameters(), null);
                        db.setDragView(snapshot);
                        content.putString(butt.getText());
                        db.setContent(content);
                        event.consume();
                    }
                });
                butt.setOnDragDone((DragEvent even) ->
                {
                    if (even.getTransferMode() == TransferMode.MOVE)
                    {
                        p.add("User deleted card" +butt.getText()+ " from "+ addCardTitle.getText());
                        row1.getChildren().clear();
                        for(int i=p.size()-1; i >= 0 ; i--) {

                            Button but2 = new Button();

                            but2.setText( p.get(i)  );
                            row1.getChildren().add(but2);
                        }
                        column.getChildren().remove(butt);
                        // but.rem;
                    }
                    even.consume();
                });
            });

            /*column.setOnDragDone((DragEvent even) ->
            {
                if (even.getTransferMode() == TransferMode.MOVE)
                {
                       /* p.add("User deleted " +butt.getText()+ " from "+ addCardTitle.getText());
                        row1.getChildren().clear();
                        for(int i=p.size()-1; i >= 0 ; i--) {

                            Button but2 = new Button();

                            but2.setText( p.get(i)  );
                            row1.getChildren().add(but2);
                        }
                   // col.getChildren().remove(column);
                    // but.rem;
                    col.getChildren().remove(column);
                }
                even.consume();
            });*/

            column.setOnDragOver((DragEvent event) -> {
                event.acceptTransferModes(TransferMode.MOVE);
                event.consume();
            });

            column.setOnDragDropped((DragEvent event) -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    Button tempBoat = new Button(db.getString());
                    tempBoat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    column.getChildren().add(tempBoat);
                    //column.getChildren().clear();
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            });
       // });
    }

    @FXML
    public void exitApp(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void dragOver(DragEvent event)
    {
        event.acceptTransferModes(TransferMode.MOVE);
        event.consume();
    }
    @FXML
    public void dragDropped(DragEvent event) {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                Button tempBoat = new Button(db.getString());
                tempBoat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                delCard.getChildren().add(tempBoat);
                delCard.getChildren().clear();
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
    }
}



