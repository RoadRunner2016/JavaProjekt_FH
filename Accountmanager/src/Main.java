import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import Employee.InternalEmp;

import GUI.MainWindow;
import StorageController.JDBCController;
import StorageController.ProjectController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import GUI.LoginWindow;
import Project.Project;
import java.util.ArrayList;

public class Main extends Application
{
        private ObservableList<Project> prjs = FXCollections.observableArrayList();

        public static void main(String[] args)
        {
            Application.launch(args);
        }

        public void start(Stage primaryStage) throws Exception
        {


            primaryStage.setTitle("Login");
            LoginWindow mainWindow = new LoginWindow();
            mainWindow.start(primaryStage);

            ObservableList<Project> listProjecttmp = FXCollections.observableArrayList();

            JDBCController controller = new JDBCController();





        }
}







