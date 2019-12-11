package KanbanBoard;

import javafx.fxml.FXML;

import java.io.*;

public class ObjectController {
    @FXML
    public void save_file(){
        try {
            File csv = new File("src/main/resources/KanbanBoard/saves.csv");

            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, false));

            for (int i = 0; i<10; i ++){//i< collum.array.length
                bw.write("Col Name");//save Collum
                for(int j = 0; j< 10 ; j ++){//j <card.array.length
                    bw.write("Card Name"+","+"Card description");
                }
            }


            bw.write("Log");//save Log
            bw.newLine();

            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
