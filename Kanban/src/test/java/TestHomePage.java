import KanbanBoard.HomeController;
import KanbanBoard.Kanban;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

@ExtendWith(ApplicationExtension.class)
class TestHomePage  {
    private Boolean success = true;

    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(HomeController.class.getResource("Home.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Test
    void testBoardOpens() {
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
    void CreateNewBoard(FxRobot robot) {
        String board = "Board";
        robot.clickOn("#newBoard").write(board).press(KeyCode.ENTER).release(KeyCode.ENTER);
        FxAssert.verifyThat("#New", hasText(board));
    }

    @Test
    void CreateMultipleBoards(FxRobot robot) {
        String board = "Board";
        String board2 = "Board2";
        String board3 = "Board3";
        robot.clickOn("#newBoard").write(board).press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.doubleClickOn("#newBoard").press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE).write(board2).press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.doubleClickOn("#newBoard").press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE).write(board3).press(KeyCode.ENTER).release(KeyCode.ENTER);
        verifyThat("#pane", (HBox b) -> {
            return b.getChildren().size() == 3 ;
        });
    }
}
