package KanbanBoard;

import KanbanBoard.card;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class column {
    private String ColumnTitle;
    ArrayList<card> ColumnCard =new ArrayList<card>() ;

    public String getColumnTitle()
    {
        return  ColumnTitle;
    }

    column(String ColumnTitle){
        //ColumnCard = new ArrayList<card>();
        this.ColumnTitle = ColumnTitle;
    }

    public JSONObject toJson(){
        JSONObject column = new JSONObject();
        column.put("title",ColumnTitle);
        List<JSONObject> cards = new ArrayList<JSONObject>();
        for(int i = 0; i < ColumnCard.size(); i++){
            cards.add(ColumnCard.get(i).toJson());
        }
        column.put("cards",cards);
        return column ;
    }
}
