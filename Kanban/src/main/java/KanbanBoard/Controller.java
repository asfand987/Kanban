package KanbanBoard;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.LinkedList;



public class Controller {

    @FXML public Pane delCard;
    @FXML private TextField newCol;
    @FXML public TextField boardTitle;
    @FXML private VBox row1;
    @FXML private HBox col;
    @FXML private Button exit;
    @FXML private MenuItem save_board;
    @FXML private MenuItem load_board;
    Boolean closed = false;
    private LinkedList<String> p = new LinkedList<String>();
    private VBox te = new VBox();
    private VBox tem = new VBox();
    private TextField tee =new TextField();
    private Button t = new Button();
    board CurrentBoard;


    private void log()
    {
        for(int i=p.size()-1; i >= 0 ; i--)
        {
            Button but1 = new Button();
            but1.setText(p.get(i));
            row1.getChildren().add(but1);
        }
    }

    public void myinit(int index,Stage stg)
    {
        System.out.println("myinit");
        CurrentBoard = GlobalData.BoardList.get(index);
        GlobalData.curStage=stg;
    }

    private void butDragDone(Button but, TextField ti, VBox coll)
    {
        but.setOnDragDone((DragEvent e) ->
        {
            if (e.getTransferMode() == TransferMode.MOVE)
            {
                p.add("User added card" +but.getText()+ " to column"+ ti.getText());
                row1.getChildren().clear();
                log();
                coll.getChildren().remove(but);
            }
            e.consume();
        });
    }

    private void collDragDone(VBox coll)
    {
        coll.setOnDragDetected(new EventHandler<MouseEvent>()
        {
            @Override public void handle(MouseEvent event)
            {
                Dragboard db = coll.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                WritableImage snapshot = coll.snapshot(new SnapshotParameters(), null);
                db.setDragView(snapshot);
                content.putString(coll.toString());
                db.setContent(content);
                te = coll;
                col.getChildren().remove(coll);
                event.consume();
            }
        });
    }

    private void butSetOnDragDetect(Button but)
    {
        but.setOnDragDetected(new EventHandler<MouseEvent>()
        {
            @Override public void handle(MouseEvent event)
            {
                Dragboard db = but.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                WritableImage snapshot = but.snapshot(new SnapshotParameters(), null);
                db.setDragView(snapshot);
                content.putString(but.getText());
                db.setContent(content);
                event.consume();
            }
        });
    }
    private void addCard(TextField title, VBox coll, column columnAdd,TextField collti)
    {
        title.setOnAction(e->
        {
            Button butt = new Button();
            butt.setPrefWidth(160);
            butt.setText(title.getText());
            coll.getChildren().add(butt);
            p.add("User added card " + butt.getText()+ " to "+ collti.getText());
            log();
            title.clear();
            title.setPromptText("Add new Card");
            collDragDone(coll, butt, title, columnAdd);
            butSetOnDragDetect(butt);
            butDragDone(butt, title, coll);

        });
    }

    @FXML
    private void resumeBoard(JSONObject save)
    {
        board resumedBoard=new board(save.getString("title"));
        System.out.println("resumming board name: "+resumedBoard.getBoardTitle());
        GlobalData.curStage.setTitle(resumedBoard.getBoardTitle());
        JSONArray cols=save.getJSONArray("columns");
        for(int i=0;i<cols.size();i++)
        {
            resumeCol(cols.getJSONObject(i));
            System.out.println("column resumed : " + cols.getJSONObject(i).getString("title"));
        }
            JSONArray logs=save.getJSONArray("logs");
            for(int i=0;i<logs.size();i++)
        {
            Button but1 = new Button();
            but1.setText(logs.get(i).toString());
            row1.getChildren().add(but1);
        }
    }





    private void collDragDone(VBox coll, Button button, TextField ti, column add)
    {
        coll.setOnDragDone((DragEvent even) ->
        {
            //push card
            card  CurrentCard = new card(button.getText());
            add.ColumnCard.add(CurrentCard);
            System.out.println("add card title: "+CurrentCard.getCardTitle());
            row1.getChildren().clear();
            log();
            if (even.getTransferMode() == TransferMode.MOVE) {
                    col.getChildren().remove(coll);
                    System.out.println("Removed");
                }
                even.consume();
            });
    }

    @FXML private Button resumeCard(String cardName)
    {
        Button butt = new Button();
        butt.setPrefWidth(160);
        butt.setText(cardName);
        return butt;
    }


    private void deleteCol(Button delete,TextField title)
    {
        delete.setOnAction(e->
        {
            Parent coll = delete.getParent();
            col.getChildren().remove(coll);
            p.add("User deleted column " + title.getText() );
            row1.getChildren().clear();
            log();

        });
    }



    private void boardDragDropped(){
        col.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                col.getChildren().addAll(te);
                System.out.println("Drop detected");
                event.setDropCompleted(true);
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void collDragOver(VBox coll){
        coll.setOnDragOver((DragEvent event) ->
        {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });
    }

    @FXML
    private void resumeCol(JSONObject coljson)
    {
        VBox column = new VBox();
        Button del = new Button();
        del.setText("X");
        column.setPrefSize(160, 570);
        TextField colTitle = new TextField();
        colTitle.setPrefWidth(160);
        TextField addCardTitle = new TextField();
        addCardTitle.setPrefWidth(160);
        colTitle.setText(coljson.getString("title"));
        column.getChildren().add(del);
        col.getChildren().add(column);
        column.getChildren().add(colTitle);
        column.getChildren().add(addCardTitle);
        addCardTitle.setPromptText("Add New Card");
        newCol.clear();
        newCol.setPromptText("Add new Column");
        String style = "-fx-background-color: rgba(255, 255, 255, 0.5);";
        column.setStyle(style);
        JSONArray cards= coljson.getJSONArray("cards");
        for(int i=0;i<cards.size();i++)
        {
            column.getChildren().add(resumeCard(cards.getJSONObject(i).getString("title")));
            System.out.println("card resumed : "+cards.getJSONObject(i).getString("title") );
        }

    }

    @FXML
    private void addColPress()
    {
        VBox column = new VBox();
        column.setPrefSize(160,570);
        Button del = new Button("X");
        TextField colTitle = new TextField(newCol.getText());
        colTitle.setPrefWidth(160);
        TextField addCardTitle = new TextField();
        addCardTitle.setPrefWidth(160);
        p.add("User added column " + colTitle.getText() );
        //put the current column into the list inside board
        board cBoard=GlobalData.BoardList.get(GlobalData.BoardList.size()-1);
        column CurrentColumn = new column(colTitle.getText());
        cBoard.addColumn(CurrentColumn);
        System.out.println("created column : "+CurrentColumn.getColumnTitle());
        row1.getChildren().clear();
        log();
        column.getChildren().add(del);
        col.getChildren().add(column);
        column.getChildren().add(colTitle);
        column.getChildren().add(addCardTitle);
        addCardTitle.setPromptText("Add New Card");
        newCol.clear();
        newCol.setPromptText("Add new Column");
        String style = "-fx-background-color: rgba(255, 255, 255, 0.5);";
        column.setStyle(style);
        addCard(addCardTitle, column, CurrentColumn,colTitle);
        deleteCol(del,colTitle);
        collDragDone(column);
        boardDragDropped();
        collDragOver(column);
        colldragDropped(column,addCardTitle);
    }

private void colldragDropped(VBox coll, TextField title)
{
    coll.setOnDragDropped((DragEvent event) ->
    {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString())
        {
            Button tempBoat = new Button(db.getString());
            tempBoat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            coll.getChildren().add(tempBoat);
            success = true;
            tempBoat.setOnDragDetected(new EventHandler<MouseEvent>()
            {
                @Override public void handle(MouseEvent event)
                {
                    Dragboard db = tempBoat.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    WritableImage snapshot = tempBoat.snapshot(new SnapshotParameters(), null);
                    db.setDragView(snapshot);
                    content.putString(tempBoat.getText());
                    db.setContent(content);
                    event.consume();
                }
            });
            tempBoat.setOnDragDone((DragEvent even) ->
            {
                if (even.getTransferMode() == TransferMode.MOVE)
                {
                    p.add("Added card " +tempBoat.getText()+ " to column "+ title.getText());
                    row1.getChildren().clear();
                    log();
                    coll.getChildren().remove(tempBoat);
                }
                even.consume();
            });

        }
        event.setDropCompleted(success);
        event.consume();
    });
}

    @FXML
    public void exitApp(ActionEvent event) {
        closedApp();
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void dragOver(DragEvent event)
    {

        //d.drag(event);
        event.acceptTransferModes(TransferMode.MOVE);
        event.consume();
    }
    @FXML
    public void dragDropped(DragEvent event) {
        //  d.drop(event);
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            Button tempBoat = new Button(db.getString());
            tempBoat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            delCard.getChildren().add(tempBoat);
            delCard.getChildren().clear();
            success = true;
            p.add("Deleted card " +tempBoat.getText());
            row1.getChildren().clear();
            for(int i=p.size()-1; i >= 0 ; i--) {
                Button but2 = new Button();
                but2.setText( p.get(i)  );
                row1.getChildren().add(but2);
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }
    public Pane getPane() {
        return delCard;
    }

    public void cardmove() {
        Button butt = new Button();
        butt.setPrefWidth(160);
        butt.setText(tee.getText());
        tem.getChildren().add(butt);
        tee.clear();
        tee.setPromptText("Add new Card");
        butt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                t = butt;
            }
        });
        p.add("User added card " + butt.getText()+ " to "+ tee.getText());
        row1.getChildren().clear();
        for(int i=p.size()-1; i >= 0 ; i--) {

            Button but1 = new Button();

            but1.setText(p.get(i) );
            row1.getChildren().add(but1);
        }
        System.out.println(tem.getChildren().size()-2);

        butt.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                Dragboard db = butt.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                WritableImage snapshot = butt.snapshot(new SnapshotParameters(), null);
                db.setDragView(snapshot);
                content.putString(butt.getText());
                db.setContent(content);
                event.consume();
            }
        });
        butt.setOnDragDone((DragEvent even) -> {
            if (even.getTransferMode() == TransferMode.MOVE) {
                p.add("User added card" +butt.getText()+ " to column"+ tee.getText());
                row1.getChildren().clear();
                for(int i=p.size()-1; i >= 0 ; i--) {
                    Button but2 = new Button();
                    but2.setText( p.get(i)  );
                    row1.getChildren().add(but2);
                }
                tem.getChildren().remove(butt);
            }
            even.consume();
        });
    }



    @FXML
    public void save(){
        GlobalData.BoardList.get(GlobalData.CurrentIndex).attachLog(p);
        String str = GlobalData.BoardList.get(GlobalData.CurrentIndex).toJson().toJSONString();
        new KanbanController().save_file(str);
    }
    @FXML
    public void load() {
        System.out.println("loading");
        try {
            String save_str = new KanbanController().read_save();
            System.out.println("file read complete");
            JSONObject kbjson = JSON.parseObject(save_str);
            System.out.println("json parse complete");
            resumeBoard(kbjson);
            System.out.println("loading complete");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public Boolean closedApp() {
        closed = true;
        return closed;

    }


    public void setBoardTitle(String boardT)
    {
        boardTitle.setText(boardT);
    }






}


