package KanbanBoard;

import javafx.fxml.FXML;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class KanbanController {

    @FXML
    public void save_file(String str){
        try {
            File json = new File("src/main/resources/KanbanBoard/saves.json");
            BufferedWriter bw = new BufferedWriter(new FileWriter(json, false));
            bw.write(str);//save Log
            bw.newLine();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public static String read_save() throws Exception{
        File json = new File("src/main/resources/KanbanBoard/saves.json");
        if(!json.exists()){
            return null;
        }
        FileInputStream inputStream = new FileInputStream(json);
        int length = inputStream.available();
        byte bytes[] = new byte[length];
        inputStream.read(bytes);
        inputStream.close();
        String str =new String(bytes, StandardCharsets.UTF_8);
        return str ;
    }

}
