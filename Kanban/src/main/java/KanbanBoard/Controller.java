package KanbanBoard;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Controller
{
    //@FXML
    // The reference of inputText will be injected by the FXML loader
    @FXML public AnchorPane card1;
    @FXML public GridPane gridPane;
    @FXML public GridPane column1;
    @FXML
    private void initialize()
    {
    }

    @FXML
    public void detect() {
        card1.setOnDragDetected((MouseEvent event) -> {
            Dragboard db = card1.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(card1.getId());
            db.setContent(content);
            event.consume();
        });
    }

    @FXML
    public void accept() {
        gridPane.addEventHandler(DragEvent.DRAG_OVER, (DragEvent event) -> {
            if (event.getGestureSource() != gridPane
                    && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
    }

    @FXML
    public void drop() {
        gridPane.addEventHandler(DragEvent.DRAG_DROPPED, (DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                gridPane.getChildren().add(card1);

                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
}
