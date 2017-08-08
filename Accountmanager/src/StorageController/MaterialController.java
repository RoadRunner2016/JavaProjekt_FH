package StorageController;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import java.text.ParseException;

public class MaterialController
{
    String tmpMaterialName;
    Double tmpMaterialPrice;


    @FXML
    private TextField addMaterialName;

    @FXML

    private TextField addMaterialPrice;

    @FXML

    private Button materialAddExitButton;

    @FXML

    private Button materialAddButton;

    @FXML

    // Add material with a check for an empty string and the doublevalues in the

    private boolean addMaterialMouseClick()throws ParseException
    {
        tmpMaterialName = addMaterialName.getText();
        CheckController checkController = new CheckController();
        JDBCController controller = new JDBCController();

        if (!((addMaterialPrice.getText().equals(""))&&(addMaterialName.getText().equals(""))))
        {
            if (checkController.isNumeric(addMaterialPrice.getText()))
            {
                tmpMaterialPrice = Double.parseDouble(addMaterialPrice.getText());

                if (controller.checkMaterialName(tmpMaterialName))
                {
                    return false;
                }
                else
                {
                    controller.saveMaterial(tmpMaterialName, tmpMaterialPrice);
                    close();

                    return true;
                }
            }
        }
        tmpMaterialPrice = null;
        tmpMaterialName = null;
        return false;



    };

    @FXML

    private void abortAddMaterialMouseClick()
    {

    };

    public void close(ActionEvent actionEvent)
    {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }



}

