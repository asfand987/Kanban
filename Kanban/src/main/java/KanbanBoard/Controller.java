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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;



public class Controller {
    @FXML private Button Log;
    @FXML private Button card;
    @FXML public Pane delCard;
    @FXML private Button currentCard; //when a user clicks card
    @FXML private TextField newCol;
    @FXML private TextField newCard;
    @FXML private TextField colTitle;
    @FXML public TextField boardTitle;
    @FXML private VBox row1;
    @FXML private HBox col;
    @FXML private Button exit;
    @FXML private MenuItem save_board;
    @FXML private MenuItem load_board;

    Boolean closed = false;
    deleteCard d = new deleteCard();
    @FXML
    ArrayList<Button> inner = new ArrayList<Button>();

    @FXML

    private ArrayList<Button> cardCount;
    private LinkedList<String> p = new LinkedList<String>();
    private VBox te = new VBox();
    private Button t = new Button();

    //Stage curStage;

    //String BoardTitle;
    board CurrentBoard;


    public void myinit(int index,Stage stg){
        System.out.println("myinit");
       CurrentBoard = GlobalData.BoardList.get(index);
       GlobalData.curStage=stg;
       //System.out.println("kbinit:"+CurrentBoard.getBoardTitle());
       //GlobalData.BoardList.add(CurrentBoard);
      // CurrentBoard.Column.size()
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

    @FXML
    private void resumeCol(JSONObject coljson) {
        VBox column = new VBox();
        Button del = new Button();
        del.setText("X");
        column.setPrefSize(160, 570);
        TextField colTitle = new TextField();
        colTitle.setPrefWidth(160);
        //System.out.println(column.getChildren().size()-2);
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

    @FXML private Button resumeCard(String cardName)
    {
        Button butt = new Button();
        butt.setPrefWidth(160);
        butt.setText(cardName);
        return butt;
    }

    private VBox tem = new VBox();
    private TextField tee =new TextField();
    @FXML
    private void addColPress()
    {

            VBox column = new VBox();
            tem = column;
            Button del = new Button();
            del.setText("X");
            column.setPrefSize(160,570);
            TextField colTitle = new TextField();
            colTitle.setPrefWidth(160);
            //System.out.println(column.getChildren().size()-2);
            TextField addCardTitle = new TextField();
            tee = addCardTitle;
            addCardTitle.setPrefWidth(160);
            colTitle.setText(newCol.getText());
            p.add("User added column " + colTitle.getText() );

            //put the current column into the list inside board
            board cBoard=GlobalData.BoardList.get(GlobalData.BoardList.size()-1);
            column CurrentColumn = new column(colTitle.getText());
            cBoard.addColumn(CurrentColumn);
            System.out.println("created column : "+CurrentColumn.getColumnTitle());
            //CurrentBoard.addColumn(new column("cc"));
            //CurrentBoard.addColumn(CurrentColumn);


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


            column.setOnDragDone((DragEvent even) -> {
                if (even.getTransferMode() == TransferMode.MOVE) {
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
                    col.getChildren().addAll(te);
                    System.out.println("Drop detected");
                    event.setDropCompleted(true);
                }
                event.setDropCompleted(success);
                event.consume();
            });


            column.setOnDragDone((DragEvent even) ->
            {

                Button butt = new Button();
                butt.setPrefWidth(160);
                butt.setText(addCardTitle.getText());//need to get node id first
                column.getChildren().add(butt);
                addCardTitle.clear();
                addCardTitle.setPromptText("Add new Card");

                butt.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        t = butt;
                    }
                    });
                p.add("User added card " + butt.getText()+ " to "+ addCardTitle.getText());


                //push card
                card  CurrentCard = new card(butt.getText());
                //System.out.println(
                CurrentColumn.ColumnCard.add(CurrentCard);
                System.out.println("add card title: "+CurrentCard.getCardTitle());


                row1.getChildren().clear();

                butt.setOnAction(e->{
                    getCardDescription(butt.getText());
                });


                    but1.setText(p.get(i) );
                    row1.getChildren().add(but1);
                }
                System.out.println(column.getChildren().size()-2);

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
                        p.add("User added card" +butt.getText()+ " to column"+ addCardTitle.getText());

                        row1.getChildren().clear();
                        for(int i=p.size()-1; i >= 0 ; i--) {
                            Button but2 = new Button();
                            but2.setText( p.get(i)  );
                            row1.getChildren().add(but2);
                        }
                        column.getChildren().remove(butt);
                    }
                    even.consume();
                });
            });


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

                  
                    tempBoat.setOnDragDetected(new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent event) {
                            Dragboard db = tempBoat.startDragAndDrop(TransferMode.ANY);
                            ClipboardContent content = new ClipboardContent();
                            WritableImage snapshot = tempBoat.snapshot(new SnapshotParameters(), null);
                            db.setDragView(snapshot);
                            content.putString(tempBoat.getText());
                            db.setContent(content);
                            event.consume();
                        }
                    });
                    tempBoat.setOnDragDone((DragEvent even) -> {
                        if (even.getTransferMode() == TransferMode.MOVE) {
                            p.add("Added card " +tempBoat.getText()+ " to column "+ addCardTitle.getText());
                            row1.getChildren().clear();
                            for(int i=p.size()-1; i >= 0 ; i--) {
                                Button but2 = new Button();
                                but2.setText( p.get(i)  );
                                row1.getChildren().add(but2);
                            }
                            column.getChildren().remove(tempBoat);
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


