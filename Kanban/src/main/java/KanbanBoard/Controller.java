package KanbanBoard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import java.util.ArrayList;

public class Controller {
    @FXML
    private Button Log;
    @FXML
    private Button card;
    @FXML
    private Button delete;
    @FXML
    private TextField newCol;
    @FXML
    private TextField newCard;
    @FXML
    private TextField colTitle;
    @FXML
    private TextField boardTitle;
    @FXML
    private VBox row;
    @FXML
    private HBox col;

    @FXML
    ArrayList<Button> inner = new ArrayList<Button>();
    @FXML
    private ArrayList<Button> cardCount;


    private void initialize()
    {
    }



    @FXML
    private void addNewCol(String title){
        // int count = 0;
        //outer.add(inner);
    }
    @FXML
    private void addNewCard(String name)
    {
        int count = 0;
        inner.add(count,card);
        card.setText(name);
    }

    @FXML
    private void addCardPress(){

        newCard.setOnAction((event)->{

            Button but = new Button();
            but.setPrefWidth(160);
            but.setText(newCard.getText());
            row.getChildren().add(but);
            but.setOnMouseDragged(new EventHandler<MouseEvent>()
            {
                public void handle(MouseEvent event)
                {
                    System.out.println("Event on Source: mouse dragged");
                }
            });
            delete.setOnMouseDragOver(new EventHandler <MouseDragEvent>()
            {
                public void handle(MouseDragEvent event)
                {
                    //row.getChildren().removeAll();
                    System.out.println("Button dragged to the delete button");
                }
            });
        });
    }

    @FXML
    private void addColPress(){
        Button title = new Button();
        title.setPrefWidth(160);
        newCol.setOnAction((event)->{
            title.setText(newCol.getText());
            col.getChildren().add(title);
        });
    }


    /*EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                l.setText(b.getText());
            }
        };

        // when enter is pressed
        b.setOnAction(event);

        // add textfield
        r.getChildren().add(b);
        r.getChildren().add(l);*/
}


