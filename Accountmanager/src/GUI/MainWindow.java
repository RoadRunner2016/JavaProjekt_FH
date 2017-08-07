package GUI;

import Project.Project;
import StorageController.ProjectController;
import StorageController.JDBCController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * mainclass for MainWindow
 **/
public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    /**
     * method to open MainWindow
     **/
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow2.fxml"));
        Pane root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        JDBCController jdbcController = new JDBCController();
        ProjectController controller = loader.getController();
        controller.insertProjects(jdbcController.loadProjects());

    }
}