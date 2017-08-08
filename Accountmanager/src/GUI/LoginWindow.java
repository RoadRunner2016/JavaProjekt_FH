package GUI;

import com.sun.org.apache.bcel.internal.generic.Select;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import EmpDatabase.Login;
import StorageController.JDBCController;
import org.junit.experimental.theories.FromDataPoints;

import java.sql.SQLException;

import GUI.MainWindow;
import StorageController.JDBCController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import GUI.LoginWindow;

/**
 * Created by Ben on 26.04.2017.
 */

/**mainclass for LoginWindow**/
public class LoginWindow {

    /**method to open the LoginWindow**/
    public void start(Stage loginScreen)throws Exception
    {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        loginScreen.setScene(scene);

        Text scenetitle = new Text("Anmeldefenster");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Loginname:");
        grid.add(userName, 0, 1);

        //LOGIN textfield

        TextField userTextField = new TextField();
        userTextField.setText("rainer123");
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        //Password textfield

        PasswordField pwBox = new PasswordField();
        pwBox.setText("rainer123");
        grid.add(pwBox, 1, 2);



        Button btn = new Button("Anmelden");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);


        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        loginScreen.show();

        btn.setOnAction

                (new EventHandler<ActionEvent>()
                {

                    public void handle(ActionEvent e)
                    {
                        actiontarget.setText("");

                //usertextfield
                //pwbox

                String tmpPassword = null;

                JDBCController jdbc = new JDBCController();

                actiontarget.setFill(Color.FIREBRICK);

                if (jdbc.loadPassword(userTextField.getText(), pwBox.getText())) {
                    // open the mainwindow
                    actiontarget.setText("Login erfolgreich!");
                    loginScreen.close();
                    MainWindow mw = new MainWindow();

                    Stage mainwindow = new Stage();
                    try {
                        mw.start(mainwindow);
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                } else {

                    actiontarget.setText("Login fehlgeschlagen");
                }
            }
        });



    }
}