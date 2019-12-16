import KanbanBoard.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
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
class CardTests  {

    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Controller.class.getResource("Kanban.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }
    @Test
    void createNewCard(FxRobot robot) {
        robot.clickOn("#newCol").write("Col");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("testing").press(KeyCode.ENTER);
        verifyThat("#button", Node::isVisible);
    }

    @Test
    void VerifyCardIsBeingDeleted(FxRobot robot) {  //works
        robot.clickOn("#newCol").write("To do List!").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("card1").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("card2").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.drag("#button", MouseButton.PRIMARY);
        robot.interact(() -> robot.dropTo("#delCard"));
        FxAssert.verifyThat("#button", LabeledMatchers.hasText("card2"));

    }
    @Test
    void DragCardToNewColumn(FxRobot robot) {
        robot.clickOn("#newCol").write("drag!").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("card1").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.drag("#button", MouseButton.PRIMARY);
        robot.interact(() -> robot.dropTo("#column2"));
        verifyThat("#column2", (VBox c) -> {
            return !c.getChildren().isEmpty();
        });
    }

    @Test
    void cardOpens(FxRobot robot) {
        robot.clickOn("#newCol").write("Test").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("Card1").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#button");
        FxAssert.verifyThat("#ID", Node::isVisible);
    }

    @Test
    void ChangeCardName(FxRobot robot) {
        robot.clickOn("#newCol").write("col").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("Card1").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#button");
        robot.clickOn("#t1").write("test");
        robot.clickOn("#c1");
        FxAssert.verifyThat("#button", LabeledMatchers.hasText("test"));

    }


    @Test
    void addStoryPoints(FxRobot robot) {
        robot.clickOn("#newCol").write("col").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("Card1").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#button");
        robot.clickOn("#t3").write("New story point");
        robot.clickOn("#c3");
        robot.sleep(200);
        FxAssert.verifyThat("#sp", LabeledMatchers.hasText("New story point"));
    }

}
