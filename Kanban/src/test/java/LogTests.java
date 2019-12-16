import KanbanBoard.Controller;
import KanbanBoard.HomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;


@ExtendWith(ApplicationExtension.class)
class LogTests  {
    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Controller.class.getResource("Kanban.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }
    @Test
    void logColAdded(FxRobot robot) {
        robot.clickOn("#newCol").write("col").press(KeyCode.ENTER).release(KeyCode.ENTER);
        FxAssert.verifyThat("#b1", LabeledMatchers.hasText("Column To Do added"));
    }

    @Test
    void logColDeleted(FxRobot robot) {
        robot.clickOn("#newCol").write("col").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#newCol").write("col").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#deleteCol4");
        FxAssert.verifyThat("#b5", LabeledMatchers.hasText("Column col deleted"));
    }
    @Test
    void logAddCard(FxRobot robot) {
        robot.clickOn("#newCol").write("col").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("Card1").press(KeyCode.ENTER).release(KeyCode.ENTER);
        FxAssert.verifyThat("#b4", LabeledMatchers.hasText("Card Card1 added"));
    }
    @Test
    void logCardDeleted(FxRobot robot) {
        robot.clickOn("#newCol").write("col").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("Card1").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.drag("#button", MouseButton.PRIMARY);
        robot.interact(() -> robot.dropTo("#delCard"));
        FxAssert.verifyThat("#b5", LabeledMatchers.hasText("Card Card1 deleted"));
    }

    @Test
    void logDragCard(FxRobot robot) {
        robot.clickOn("#newCol").write("col").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("Card1").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.drag("#button", MouseButton.PRIMARY);
        robot.interact(() -> robot.dropTo("#column2"));
        FxAssert.verifyThat("#b5", LabeledMatchers.hasText("Card  moved "));
    }
}

