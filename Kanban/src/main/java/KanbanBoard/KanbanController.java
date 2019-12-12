package KanbanBoard;

import javafx.fxml.FXML;

import java.io.*;
import java.util.ArrayList;

public class KanbanController {

    @FXML
    public void save_file(String str){
        try {

            File csv = new File("src/main/resources/KanbanBoard/saves.json");

            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, false));



            bw.write(str);//save Log
            bw.newLine();
            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
