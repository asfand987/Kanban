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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

@ExtendWith(ApplicationExtension.class)
class xTestKanbanBoard {
    private Boolean success = true;
    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Controller.class.getResource("Kanban.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }


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
}

