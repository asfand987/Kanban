package KanbanBoard;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class card {
    private String CardTitle;
    ArrayList<String> Desc= new ArrayList<String>();;


    public String getCardTitle()
    {
        return CardTitle;
    }

    card(String CardTitle){
        this.CardTitle = CardTitle;
    }

    public JSONObject toJson(){
        JSONObject card = new JSONObject();
        card.put("title",CardTitle);
        card.put("desc",Desc);
        return  card;
    }
}
