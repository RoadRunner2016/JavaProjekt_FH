import StorageController.JDBCController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import GUI.LoginWindow;
import GUI.MainWindow;
import Project.Project;


public class Main extends Application {
    private ObservableList<Project> prjs = FXCollections.observableArrayList();

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage) throws Exception {


        primaryStage.setTitle("Hauptbildschirm");
        LoginWindow mainWindow = new LoginWindow();
        mainWindow.start(primaryStage);

        ObservableList<Project> listProjecttmp = FXCollections.observableArrayList();

        JDBCController controller = new JDBCController();


    }
}







