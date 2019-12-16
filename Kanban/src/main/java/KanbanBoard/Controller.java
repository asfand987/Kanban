package KanbanBoard;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Controller 
{
    @FXML public Pane delCard;
    @FXML private TextField newCol;
    @FXML public TextField boardTitle;
    @FXML private VBox row1;
    @FXML private HBox col;
    @FXML private Button exit;
    @FXML private ScrollPane log1;
    private Boolean closed = false;
    private LinkedList<String> p = new LinkedList<String>();
    private VBox te = new VBox();
    private Button tempp = new Button();
    private TextField tee =new TextField();
    private Button t = new Button();
    private board CurrentBoard;
    private int setup = 0;
    private int overallTime;
    private long initialTime = System.currentTimeMillis();
    @FXML private Button statsButton;
    private int wip = 0;
    private Integer storyCount = 0;
    private ArrayList<Integer> arr =new ArrayList<Integer>();
    private ArrayList<Integer> arr2 =new ArrayList<Integer>();
    private String colour;
    @FXML private Button currentWIP;
    private HashMap< String,Label> c1 = new HashMap<String,Label>();
    private HashMap< String,Label> c2 = new HashMap<String,Label>();
    private Label temp1 = new Label();
    private Label temp2 = new Label();
    private TextField tempText3 = new TextField();
    private Button tempCheck3 = new Button();
    private VBox tempBox = new VBox();
    private Label tempDes = new Label();
    private XYChart.Series<Number,Number> data = new XYChart.Series<>();
    private int ID = 0;
    private HashMap< String,TextField> c3 = new HashMap<String,TextField>();
    private HashMap< String,Button> c4 = new HashMap<String,Button>();
    private HashMap< String,VBox> c5 = new HashMap<String,VBox>();
    private HashMap< String,Label> c6 = new HashMap<String,Label>();
    private Label temp3 = new Label();
    private HashMap< String,Label> c7 = new HashMap<String,Label>();
    int count;
    int log;

    private void initialSetup() {
        while (setup < 3) {
            addColPress();
        }
    }

    private int getTimeInSeconds() {
        long currentTime = System.currentTimeMillis();
        overallTime = (int)((currentTime - this.initialTime) / 1000);
        return (int)((currentTime - this.initialTime) / 1000);
    }

    public double mean(ArrayList<Integer> arr) {
        Integer sum = 0;
        if(arr.size() == 0) {
            return 0;
        }
        for (int i = 0; i < arr.size(); i++) {
            sum += arr.get(i);
        }
        return (sum / arr.size()) / 604800;
    }

    @FXML
    private void statsPage() {
        statsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Pane statsWindow = new Pane();
                statsWindow.setId("s");
                statsWindow.setStyle("-fx-background-color: #FEEEAD");
                Label label1 = new Label("Overall velocity:\n"  + storyCount + " story points per week");
                label1.setLayoutX(60);
                label1.setLayoutY(25);
                label1.setFont(new Font("Arial", 15));
                label1.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        label1.setScaleX(1.2);
                        label1.setScaleY(1.2);
                    }
                });
                label1.setOnMouseExited(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        label1.setScaleX(1);
                        label1.setScaleY(1);
                    }
                });
                Label label2 = new Label("Average lead time:\n" + mean(arr) + " weeks");
                label2.setLayoutX(315);
                label2.setLayoutY(25);
                label2.setFont(new Font("Arial", 15));
                label2.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        label2.setScaleX(1.2);
                        label2.setScaleY(1.2);
                    }
                });
                label2.setOnMouseExited(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        label2.setScaleX(1);
                        label2.setScaleY(1);
                    }
                });
                PieChart pie = new PieChart();
                ObservableList<PieChart.Data> pData = FXCollections.observableArrayList();
                pData.add(new PieChart.Data("WIP", wip));
                pData.add(new PieChart.Data("Max WIP", 10));
                pie.setData(pData);
                pie.setLayoutX(80);
                pie.setLayoutY(60);
                pie.setPrefSize(320,200);
                NumberAxis xAxis = new NumberAxis();
                NumberAxis yAxis = new NumberAxis();
                yAxis.setLabel("Average WIP");
                xAxis.setLabel("Time");
                LineChart<Number,Number> chart = new LineChart<Number,Number>(xAxis,yAxis);
                chart.getData().add(data);
                chart.setLayoutX(-5);
                chart.setLayoutY(275);
                chart.setPrefSize(470,280);
                statsWindow.getChildren().addAll(label1,label2,pie,chart);
                Stage stage = new Stage();
                stage.setScene(new Scene(statsWindow,500,550));
                stage.show();
            }
        });
    }

    @FXML
    private void restrictWIP() {
        if(storyCount > 4){
            colour = "-fx-text-fill: EC3642;";
        } else {
            colour = "-fx-text-fill: rgb(61,156,59);";
        }
        currentWIP.setStyle(colour);
        currentWIP.setText("WIP: " + wip + " / 5");
    }

    private void log()
    {
            log++;
            Button but1 = new Button();
            but1.setId("b"+log );
            but1.setText("Card " + tempp.getText() + " added");
            row1.getChildren().add(but1);
    }

    private void logCol()
    {
        log++;
        Button but1 = new Button();
        but1.setId("b"+log);
        but1.setText("Column " + tee.getText() + " added");
        row1.getChildren().add(but1);
    }

    private void delCol()
    {
        log++;
        Button but1 = new Button();
        but1.setId("b"+log);
        but1.setText("Column " + tee.getText() + " deleted");
        row1.getChildren().add(but1);
    }

    private void logDelete()
    {
        log++;
        Button but1 = new Button();
        but1.setId("b"+log);
        but1.setText("Card " + tempp.getText() + " deleted");
        row1.getChildren().add(but1);
    }

    private void logCardDrag() 
    {
        log++;
        Button but1 = new Button();
        but1.setId("b"+log);
        but1.setText("Card " + tee.getText() + " moved ");
        row1.getChildren().add(but1);
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
                tempp = but;
                tee = ti;
                logCardDrag();
                coll.getChildren().remove(but);
            }
            e.consume();
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
    private void addCard(TextField title, VBox coll, column columnAdd, TextField colTitle)
    {
        title.setOnAction(e->
        {
            ID++;
            Button butt = new Button();
            butt.setId("button");
            butt.setPrefWidth(160);
            butt.setText(title.getText());
            coll.getChildren().add(butt);
            tempp = butt;
            log();
            title.clear();
            title.setPromptText("Add new Card");

            Label id = new Label();
            TextField text3 = new TextField();
            text3.setId("t3");
            Button check3 = new Button("✓");
            check3.setPrefWidth(80);
            check3.setId("c3");
            VBox box = new VBox(10);
            check3.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent event) {
                    if(overallTime > 604800) {
                        storyCount = 0;
                        overallTime = overallTime % 604800;
                    }
                    storyCount++;
                    wip++;
                    data.getData().add(new XYChart.Data(getTimeInSeconds(), wip));
                    restrictWIP();
                    HBox items = new HBox(30);
                    Label storyPoint = new Label();
                    storyPoint.setId("sp");
                    storyPoint.setText(text3.getText());
                    Button check4 = new Button("✓");
                    check4.setPrefWidth(50);
                    check4.setId("c4");
                    check4.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent event) {
                            storyPoint.setText(text3.getText());
                        }
                    });
                    Button delete1 = new Button("X");
                    delete1.setPrefWidth(80);
                    delete1.setId("d1");
                    delete1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent event) {
                            items.getChildren().clear();
                             wip--;
                            data.getData().add(new XYChart.Data(getTimeInSeconds(), wip));
                            restrictWIP();
                        }
                    });
                    items.getChildren().addAll(storyPoint,check4,delete1);
                    box.getChildren().add(items);
                }
            });
            Label description = new Label();
            description.setId("d1");
            Label time1 = new Label();
            time1.setText(String.valueOf(getTimeInSeconds()));
            Label time2 = new Label();
            if(colTitle.getText().equals("Done") && time2.getText().isEmpty()) {
                time2.setText( String.valueOf(getTimeInSeconds()));
                Integer value = getTimeInSeconds() - Integer.parseInt(time1.getText());
                arr.add(value);
            }
            butt.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    t = butt;
                }
            });

            butt.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent event){
                    Pane cardPage = new Pane();

                    Label id = new Label("Given ID: ");
                    id.setId("ID");

                    if(id.getText().isEmpty()) {
                        id.setText(String.valueOf(ID));
                    }

                    id.setLayoutX(40);
                    id.setLayoutY(30);
                    Label label1 = new Label("To edit the title, type out the preferred title in the textfield below \nand click the button below the textfield");
                    label1.setLayoutX(40);
                    label1.setLayoutY(70);
                    TextField text1 = new TextField();
                    text1.setId("t1");
                    text1.setLayoutX(40);
                    text1.setLayoutY(110);
                    Button check1 = new Button("✓");
                    check1.setPrefWidth(70);
                    check1.setId("c1");
                    check1.setLayoutX(40);
                    check1.setLayoutY(140);
                    check1.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override public void handle(MouseEvent event){
                            butt.setText(text1.getText());
                        }
                    });
                    Label label2 = new Label("To add/edit the description, type out the preferred description in \nthe textfield below and click the button below the textfield");
                    label2.setLayoutX(40);
                    label2.setLayoutY(190);
                    TextField text2 = new TextField();
                    text2.setId("t2");
                    text2.setLayoutX(40);
                    text2.setLayoutY(230);
                    description.setLayoutX(40);
                    description.setLayoutY(260);
                    Button check2 = new Button("✓");
                    check2.setPrefWidth(80);
                    check2.setId("c2");
                    check2.setLayoutX(40);
                    check2.setLayoutY(277);
                    check2.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override public void handle(MouseEvent event){
                            description.setText(text2.getText());
                        }
                    });
                    time1.setLayoutX(40);
                    time1.setLayoutY(330);
                    time2.setLayoutX(40);
                    time2.setLayoutY(360);
                    Label label3 = new Label("To add a story point, type out the preferred story point in the \ntextfield below and click the button below the textfield. To edit \na story point, type out the new story point and click the tick next \nto the story point. To delete a story point, click the X");
                    label3.setLayoutX(40);
                    label3.setLayoutY(400);
                    text3.setLayoutX(40);
                    text3.setLayoutY(470);
                    check3.setLayoutX(40);
                    check3.setLayoutY(500);
                    box.setLayoutX(40);
                    box.setLayoutY(530);

                    c1.put(butt.getText(),time1);
                    c2.put(butt.getText(),time2);
                    c3.put(butt.getText(),text3);
                    c4.put(butt.getText(),check3);
                    c5.put(butt.getText(),box);
                    c6.put(butt.getText(),description);
                    c7.put(butt.getText(),id);
                    temp1 = time1;
                    temp2 = time2;
                    tempText3 = text3;
                    tempCheck3 = check3;
                    tempBox = box;
                    tempDes = description;

                    cardPage.getChildren().addAll(id,label1,text1,check1,label2,text2,check2,description,time1,time2,label3,text3,check3,box);
                    ScrollPane scroll = new ScrollPane();
                    scroll.setContent(cardPage);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(scroll,500,550));
                    stage.show();
                }
            });
            card  CurrentCard = new card(butt.getText());
            columnAdd.ColumnCard.add(CurrentCard);
            System.out.println("add card title: "+CurrentCard.getCardTitle());
            collDragPush(coll, butt, title, columnAdd);
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

    private void collDragPush(VBox coll, Button button, TextField ti, column add)
    {
        coll.setOnDragDone((DragEvent even) ->
        {
            //push card
            card  CurrentCard = new card(button.getText());
            add.ColumnCard.add(CurrentCard);
            if (even.getTransferMode() == TransferMode.MOVE) {
                    col.getChildren().remove(coll);
                 //   System.out.println("Removed");
                }
                even.consume();
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


    private Button resumeCard(String cardName, TextField title, VBox coll)//, VBox coll, TextField title, column columnAdd)
    {
        Button butt = new Button();
        butt.setPrefWidth(160);
        butt.setText(cardName);
        butSetOnDragDetect(butt);
        butDragDone(butt, title, coll);
        return butt;
    }

    private void deleteCol(Button delete,TextField title)
    {
        delete.setOnAction(e->
        {
            Parent coll = delete.getParent();
            col.getChildren().remove(coll);
            delCol();
        });
    }

    private void boardDragDropped(){
        col.setOnDragDropped((DragEvent event) -> 
        {
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

    private void boardDragOver()
    {
        col.setOnDragOver((DragEvent event) -> 
        {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });
    }

    private void collDragOver(VBox coll)
    {
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
            column.getChildren().add(resumeCard(cards.getJSONObject(i).getString("title"),addCardTitle, column));
            System.out.println("card resumed : "+cards.getJSONObject(i).getString("title") );
        }

        newCol.setOnAction(event -> {
            addColPress();
        });

        addCardTitle.setOnAction(event -> {
            Button butt = new Button();
            butt.setPrefWidth(160);
            butt.setText(addCardTitle.getText());
            column.getChildren().add(butt);
            butSetOnDragDetect(butt);
            butDragDone(butt, addCardTitle, column);

        });

        deleteCol(del,colTitle);
        collDragDone(column);
        boardDragOver();
        boardDragDropped();
        collDragOver(column);
        colldragDropped(column,addCardTitle,colTitle);
    }

    @FXML
    private void addColPress()
    {
        count++;
        VBox column = new VBox();
        String c = Integer.toString(count);
        column.setId("column" + c);
        column.setPrefSize(160,570);
        Button del = new Button("X");
        del.setId("deleteCol" + c);
        TextField colTitle = new TextField();
        colTitle.setId("Col");
        //colTitle.setText(newCol.getText());
        if(setup == 0 || setup == 2) {
            del.setDisable(true);
        }
        if(setup == 0) {
            colTitle.setText("To Do");
            colTitle.setDisable(true);
        }
        else if(setup == 1) {
            colTitle.setText(newCol.getText());
           // colTitle.setText("To Do");
        }
        else if (setup == 2) {
            colTitle.setText("Done");
            colTitle.setDisable(true);
        }
        else {
            colTitle.setText(newCol.getText());
        }
        colTitle.setPrefWidth(160);
        TextField addCardTitle = new TextField();
        addCardTitle.setId("Card");
        addCardTitle.setPrefWidth(160);
        tee = colTitle;
        try {
            board cBoard = GlobalData.BoardList.get(GlobalData.BoardList.size() - 1);
            column CurrentColumn = new column(colTitle.getText());
            cBoard.addColumn(CurrentColumn);
            logCol();
            column.getChildren().add(del);
            col.getChildren().add(column);
            column.getChildren().add(colTitle);
            column.getChildren().add(addCardTitle);
            addCardTitle.setPromptText("Add New Card");
            newCol.clear();
            newCol.setPromptText("Add new Column");
            String style = "-fx-background-color: rgba(255, 255, 255, 0.5);";
            column.setStyle(style);
            addCard(addCardTitle, column, CurrentColumn, colTitle);
            deleteCol(del, colTitle);
            collDragDone(column);
            boardDragOver();
            boardDragDropped();
            collDragOver(column);
            colldragDropped(column, addCardTitle, colTitle);
        setup++;
        initialSetup();
        }
        catch (Exception e) {
            column CurrentColumn = new column(colTitle.getText());
            logCol();
            column.getChildren().add(del);
            col.getChildren().add(column);
            column.getChildren().add(colTitle);
            column.getChildren().add(addCardTitle);
            addCardTitle.setPromptText("Add New Card");
            newCol.clear();
            newCol.setPromptText("Add new Column");
            String style = "-fx-background-color: rgba(255, 255, 255, 0.5);";
            column.setStyle(style);
            addCard(addCardTitle, column, CurrentColumn, addCardTitle);
            deleteCol(del, colTitle);
            collDragDone(column);
            boardDragOver();
            boardDragDropped();
            collDragOver(column);
            colldragDropped(column, addCardTitle, colTitle);
            setup++;
            initialSetup();
        }
    }

    private void colldragDropped(VBox coll, TextField title,TextField colTitle)
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
                    logCardDrag();
                    coll.getChildren().remove(tempBoat);
                }
                even.consume();
            });

            tempBoat.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent event){
                    Pane cardPage = new Pane();
                    
                    Label label1 = new Label("To edit the title, type out the preferred title in the textfield below \nand click the button below the textfield");
                    label1.setLayoutX(40);
                    label1.setLayoutY(70);
                    TextField text1 = new TextField();
                   // text1.setId("t1");
                    text1.setLayoutX(40);
                    text1.setLayoutY(110);
                    Button check1 = new Button("✓");
                    //check1.setId("c1");
                    check1.setLayoutX(40);
                    check1.setLayoutY(140);
                    check1.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override public void handle(MouseEvent event){
                            tempBoat.setText(text1.getText());
                            event.consume();
                        }
                    });
                    Label label2 = new Label("To add/edit the description, type out the preferred description in \nthe textfield below and click the button below the textfield");
                    label2.setLayoutX(40);
                    label2.setLayoutY(190);
                    TextField text2 = new TextField();
                    text2.setLayoutX(40);
                    text2.setLayoutY(230);
                    tempDes.setLayoutX(40);
                    tempDes.setLayoutY(260);
                    Button check2 = new Button("✓");
                    check2.setLayoutX(40);
                    check2.setLayoutY(277);
                    check2.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override public void handle(MouseEvent event){
                            tempDes.setText(text2.getText());
                            event.consume();
                        }
                    });
                    temp1 = c1.get(tempBoat.getText());
                    temp2 = c2.get(tempBoat.getText());
                    if(title.getText().equals("Done") && temp2.getText().isEmpty()) {
                        temp2.setText( String.valueOf(getTimeInSeconds()));
                        Integer value = getTimeInSeconds() - Integer.parseInt(temp1.getText());
                        arr.add(value);
                    }
                    tempText3 = c3.get(tempBoat.getText());
                    tempCheck3 = c4.get(tempBoat.getText());
                    tempBox = c5.get(tempBoat.getText());
                    tempDes = c6.get(tempBoat.getText());
                    temp3 = c7.get(tempBoat.getText());
                    temp3.setLayoutX(40);
                    temp3.setLayoutY(30);
                    
                    temp1.setLayoutX(40);
                    temp1.setLayoutY(330);
                    temp2.setLayoutX(40);
                    temp2.setLayoutY(360);
                    Label label3 = new Label("To add a story point, type out the preferred story point in the \ntextfield below and click the button below the textfield. To edit \na story point, type out the new story point and click the tick next \nto the story point. To delete a story point, click the X");
                    label3.setLayoutX(40);
                    label3.setLayoutY(400);
                    tempText3.setLayoutX(40);
                    tempText3.setLayoutY(470);
                    tempCheck3.setLayoutX(40);
                    tempCheck3.setLayoutY(500);
                    tempBox.setLayoutX(40);
                    tempBox.setLayoutY(530);
                    cardPage.getChildren().addAll(temp3,label1,text1,check1,label2,text2,check2,tempDes,temp1,temp2,label3,tempText3,tempCheck3,tempBox);
                    ScrollPane scroll = new ScrollPane();
                    scroll.setContent(cardPage);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(scroll,500,550));
                    stage.show();

                    if(colTitle.getText().equals("Done") && temp2.getText().isEmpty()) {
                        temp2.setText( String.valueOf(getTimeInSeconds()));
                        Integer value = getTimeInSeconds() - Integer.parseInt(temp1.getText());
                        arr.add(value);
                    }
                    event.consume();
                }
            });

        }
        event.setDropCompleted(success);
        event.consume();
    });
}

    @FXML
    public void exitApp(ActionEvent event) 
    {
        closedApp();
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
    public void deleteCard(DragEvent event)
    {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            Button tempBoat = new Button(db.getString());
            tempBoat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            delCard.getChildren().add(tempBoat);
            delCard.getChildren().clear();
            tempp = tempBoat;
            success = true;
            logDelete();
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    public void save()
    {
        GlobalData.BoardList.get(GlobalData.CurrentIndex).attachLog(p);
        String str = GlobalData.BoardList.get(GlobalData.CurrentIndex).toJson().toJSONString();
        new KanbanController().save_file(str);
    }

    @FXML
    public void load() 
    {
        System.out.println("loading");
        try {
            String save_str = new KanbanController().read_save();
            System.out.println("file read complete");
            JSONObject kbjson = JSON.parseObject(save_str);
            System.out.println("json parse complete");
            resumeBoard(kbjson);
            System.out.println("loading complete");
        } catch (Exception e) 
        {
            System.out.println(e.toString());
        }
    }

    public Boolean closedApp()
    {
        closed = true;
        return closed;
    }
}


