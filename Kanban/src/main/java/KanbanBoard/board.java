package KanbanBoard;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class board {
    private String BoardTitle;
    List<column> Column = new ArrayList<column>();
    LinkedList<String> kblog= new LinkedList<String>();

    public String getBoardTitle()
    {
        return BoardTitle;
    }

    public void addColumn(column c)
    {
        Column.add(c);
    }

    board(String BoardTitle){
        this.BoardTitle = BoardTitle;
    }

    public void attachLog(LinkedList<String> kblg)
    {
        kblog=kblg;
    }

    public JSONObject toJson(){
        JSONObject board = new JSONObject();
        board.put("title",BoardTitle);
        List<JSONObject> columns = new ArrayList<JSONObject>();
        for(int i = 0; i < Column.size(); i++){
            columns.add(Column.get(i).toJson());
        }
        board.put("columns", columns);
        board.put("logs",kblog);

        return board;
    }
}

