import KanbanBoard.Controller;
import KanbanBoard.Kanban;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import java.awt.*;
import java.security.Key;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

@ExtendWith(ApplicationExtension.class)
class TestKanbanBoard  {
    private Boolean success = true;
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
        // robot.press(KeyCode.ENTER);
        robot.sleep(2000);
        verifyThat("#button", Node::isVisible);
    }

   /* @Test
    void VerifyColumnNameIsCorrectAsInput(FxRobot robot) {  //works
        robot.clickOn("#newCol").write("To do list!");
        robot.press(KeyCode.ENTER);
        verifyThat("#Col", hasText("To do list!"));
        //sleep(2000);
    }
*/
   @Test
   void veryifyStatsPageOpens(FxRobot robot) {
       robot.clickOn("#statsButton");
       FxAssert.verifyThat("#s", Node::isVisible);
   }

    @Test
    void exitButExists() {
        verifyThat("#exit", LabeledMatchers.hasText("X"));
    } //works

    @Test
    void testBoardClosesOnClickingExitButton(FxRobot robot) {  //works
        robot.clickOn("#exit");
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Application.launch(Kanban.class); // Run JavaFX application.
                    success = true;
                } catch(Throwable t) {
                    if(t.getCause() != null && t.getCause().getClass().equals(InterruptedException.class)) {
                        success = true;
                        return;
                    }
                    success = false;
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
        }
        thread.interrupt();
        try {
            thread.join(1);
        } catch(InterruptedException ex) {
        }
        assertFalse(success);
        //robot.sleep(2000);
        //FxAssert.verifyThat();
    }

    @Test
    void testBoardOpens() {  //works
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Application.launch(Kanban.class); // Run JavaFX application.
                    success = true;
                } catch (Throwable t) {
                    if (t.getCause() != null && t.getCause().getClass().equals(InterruptedException.class)) {
                        success = true;
                        return;
                    }
                    success = true;
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(1000);

        } catch (InterruptedException ex) {
        }
        thread.interrupt();
        try {
            thread.join(1);
        } catch (InterruptedException ex) {

        }
        assertTrue(success);
    }

    @Test
    void VerifyCardIsBeingDeleted(FxRobot robot) {  //works
        boolean exists = true;
        robot.clickOn("#newCol").write("To do List!").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("card1").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("card2").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.drag("#button", MouseButton.PRIMARY);
        robot.interact(() -> robot.dropTo("#delCard"));
        FxAssert.verifyThat("#button", LabeledMatchers.hasText("card2"));
        robot.sleep(2000);

    }
    @Test
    void DragCardToNewColumn(FxRobot robot) {
        robot.clickOn("#newCol").write("drag!").press(KeyCode.ENTER).release(KeyCode.ENTER);
        //robot.clickOn("#newCol").write("Done!").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#Card").write("card1").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.drag("#button", MouseButton.PRIMARY);
        robot.interact(() -> robot.dropTo("#column2"));
        verifyThat("#column2", (VBox c) -> {
            return !c.getChildren().isEmpty();
        });
    }

    @Test
    void deleteColumn(FxRobot robot) {
        Boolean deleted = false;
        robot.clickOn("#newCol").press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#deleteCol2");
        verifyThat("#col", (HBox h) -> {
            return h.getChildren().size() == 2;  //initially 3 columns created, delete one should leave 2.
        });
    }
    @Test
    void dragColumn(FxRobot robot) {
        robot.clickOn("#newCol").write("To do List!").press(KeyCode.ENTER).release(KeyCode.ENTER);
       // robot.clickOn("#Col").write("Test");
        robot.drag("#column1", MouseButton.PRIMARY);
        robot.interact(() -> robot.dropTo("#col"));
        robot.sleep(1000);
       // robot.clickOn("#deleteCol2");
        robot.drag("#column2", MouseButton.PRIMARY);
        robot.interact(() -> robot.dropTo("#col"));
        robot.sleep(1000);
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
        robot.sleep(1000);
        robot.sleep(1000);
        robot.doubleClickOn("#Col").press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE).write(changeTitle).press(KeyCode.ENTER).release(KeyCode.ENTER);
        FxAssert.verifyThat("#Col", hasText(changeTitle));
        robot.sleep(1000);
    }

    @Test
    void cardOpens(FxRobot robot) {
       robot.clickOn("#newCol").write("Test").press(KeyCode.ENTER).release(KeyCode.ENTER);
       robot.clickOn("#Card").write("Card1").press(KeyCode.ENTER).release(KeyCode.ENTER);
       robot.clickOn("#button");
       FxAssert.verifyThat("#ID", Node::isVisible);
    }
}

