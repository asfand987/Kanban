import KanbanBoard.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import static org.testfx.api.FxAssert.verifyThat;
@ExtendWith(ApplicationExtension.class)
//
class ReadMe {
   /* @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Controller.class.getResource("Kanban.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }*/

   /* @Test
    void editStoryPoints(FxRobot robot) {
        robot.clickOn("#newCol").write("col").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("Card1").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#button");
        robot.clickOn("#t3").write("s");
        robot.clickOn("#c3");
        robot.doubleClickOn("#t3").press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE).write("New");
        robot.clickOn("#c4");
        FxAssert.verifyThat("#sp", LabeledMatchers.hasText("New"));
    }

    @Test
    void addDescriptionToCard(FxRobot robot) {
        robot.clickOn("#newCol").write("col").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("Card1").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#button");
        robot.clickOn("#t2").write("A");
        robot.clickOn("#c2");
        //robot.sleep(500);
        FxAssert.verifyThat("#d1", LabeledMatchers.hasText("A"));
    }*/


}