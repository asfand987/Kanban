package KanbanBoard;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

public class deleteCard {
    //@FXML Controller c = new Controller();
    /*Pane p = new Pane();

    @FXML
    public void drag(DragEvent event)
    {
        event.acceptTransferModes(TransferMode.MOVE);
        event.consume();
    }

    @FXML
    public void drop(DragEvent event) {
        p = c.getPane();
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            Button tempBoat = new Button(db.getString());
            tempBoat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            p.getChildren().add(tempBoat);
            p.getChildren().clear();
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }
*/
}
