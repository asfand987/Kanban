
import KanbanBoard.HomeController;
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
import org.testfx.api.FxRobotException;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

public class HomePgTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(HomeController.class.getResource("Home.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
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
    public void newBoard() {
        clickOn("#newBoard").write("TESTING!");
        //write("Test");
        sleep(2000);
        //press(KeyCode.ENTER);
        //clickOn("#Board");
        //FxAssert.verifyThat(window("Kanban.fxml"), WindowMatchers.isShowing());
    }
//FxAssert.verifyThat(window("My Window"), WindowMatchers.isShowing());  //new window open on button click
}
