import KanbanBoard.Controller;
import KanbanBoard.Kanban;
import KanbanBoard.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotException;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.robot.ClickRobot;

import java.awt.*;

import static org.junit.Assert.assertTrue;


public class test extends ApplicationTest {
    private volatile boolean success = false;
    private Button exit;
    Stage s;
    //Controller c = new Controller();
    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Kanban.class.getResource("Kanban.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
        s = stage;
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void testrun() {
        assertTrue(true);
    }

    @Test(expected = FxRobotException.class)
    public void bogus() {
        clickOn("#card");
    }

    @Test
    public void t() {
       // TextField label = (TextField) Controller.find("#newCol");
       // sleep(2000);
        clickOn("#newCol").write("To do list!");
        sleep(2000);
        //write("testing");
       // sleep(3000);
    }

    /*@Test
    public void exitButExists() {
        FxAssert.verifyThat("#exit", LabeledMatchers.hasText("X"));
    }

    @Test
    public void exitApp() {
        clickOn("#exit");
        //sleep(2000);
        //FxAssert.verifyThat();
    }

    @Test
    public void testMain() {
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
                    success = true;
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
        assertTrue(success);
    }*/

}