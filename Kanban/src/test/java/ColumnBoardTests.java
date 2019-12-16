import KanbanBoard.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

@ExtendWith(ApplicationExtension.class)
class ColumnBoardTests {
    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Controller.class.getResource("Kanban.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }
   /* @Test
    void VerifyColumnNameIsCorrectAsInput(FxRobot robot) {  //works
        robot.clickOn("#newCol").write("To do list!");
        robot.press(KeyCode.ENTER);
        verifyThat("#Col", hasText("To do list!"));
        //sleep(2000);
    }*/

    @Test
    void deleteColumn(FxRobot robot) {
        robot.clickOn("#newCol").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#deleteCol2");
        verifyThat("#col", (HBox h) -> {
            return h.getChildren().size() == 2;  //initially 3 columns created, delete one should leave 2.
        });
    }
    @Test
    void dragColumn(FxRobot robot) {
        robot.clickOn("#newCol").write("To do List!").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.drag("#column1", MouseButton.PRIMARY);
        robot.interact(() -> robot.dropTo("#col"));
        robot.drag("#column2", MouseButton.PRIMARY);
        robot.interact(() -> robot.dropTo("#col"));
        FxAssert.verifyThat("#Col", hasText("Done"));
        //First column and second column are now the last two columns while the original last column is now the first column
        //#Col returns the first column.
        //Assertions.assertThat(robot.lookup("#button").queryButton()).hasText("card1");
    }

    @Test
    void ChangeColName(FxRobot robot) {
        String changeTitle = "Work done";
        robot.clickOn("#newCol").write("List").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.drag("#column1", MouseButton.PRIMARY);
        robot.interact(() -> robot.dropTo("#col"));
        robot.doubleClickOn("#Col").press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE).write(changeTitle).press(KeyCode.ENTER).release(KeyCode.ENTER);
        FxAssert.verifyThat("#Col", hasText(changeTitle));
    }

}
