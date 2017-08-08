package StorageController;

import Project.*;
import Employee.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import javax.sql.RowSet;
import java.awt.*;
import java.sql.Array;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by A on 05.08.2017.
 */
public class ProjectController {

    public ProjectController() {
    }

    private static final int FONTSIZE = 16;
    private static ObservableList<Project> PROJECTS = null;
    private static Project PROJECT = null;
    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,###.00");

    // Tab Pane Projects

    @FXML
    private TableView<Project> projects;
    @FXML
    private TableColumn<Project, String> projectID;
    @FXML
    private TableColumn<Project, String> projectName;
    @FXML
    private TableColumn<Project, String> projectStart;
    @FXML
    private TableColumn<Project, String> projectEnd;

    // Tab Pane Overview

    @FXML
    private Text overviewMilestones;
    @FXML
    private Label overviewProjectName;
    @FXML
    private Label overviewStartEnd;
    @FXML
    private Label overviewCosts;
    @FXML
    private Text overviewMaterial;
    @FXML
    private Text overviewEmployees;

    // Tab Pane Employees/Staff

    @FXML
    private TableView<Employee> staffInvolved;
    @FXML
    private TableView<Employee> staffAll;
    @FXML
    private TableColumn<Employee, String> staffInvolvedID;
    @FXML
    private TableColumn<Employee, String> staffInvolvedLastname;
    @FXML
    private TableColumn<Employee, String> staffInvolvedFirstname;
    @FXML
    private TableColumn<Employee, String> staffAllID;
    @FXML
    private TableColumn<Employee, String> staffAllLastname;
    @FXML
    private TableColumn<Employee, String> staffAllFirstname;
    @FXML
    private TableColumn<Employee, String> staffAllProject;
    @FXML
    private Label employeeCosts0;
    @FXML
    private Label employeeCosts1;

    // Tab Pane Material

    @FXML
    private TableView<Material> materialUsed;
    @FXML
    private TableView<Material> materialAll;
    @FXML
    private TableColumn<Material, String> usedMaterialName;
    @FXML
    private TableColumn<Material, String> usedMaterialPrice;
    @FXML
    private TableColumn<Material, String> usedMaterialAmount;
    @FXML
    private TableColumn<Material, String> materialAllName;
    @FXML
    private TableColumn<Material, String> materialAllPrice;
    @FXML
    private Label materialCosts;

    // Tab Pane Milstones

    @FXML
    private GridPane milestonesGridPane;


    @FXML
    private TableColumn<String, String> msg;
    @FXML
    private SplitPane msganch;

    @FXML
    private GridPane messageGridPane;

    @FXML
    private void openWindowNewMaterial() {
        AddToDBController.openWindowNewMaterial("Neues Material hinzufügen");
    }

    @FXML
    private void openWindowNewEmployee() {
        AddToDBController.openWindowNewEmployee("Neuen Mitarbeiter hinzufügen");
    }

    @FXML
    private void openWindowNewProject() {
        AddToDBController.openWindowNewProject("Neues Projekt hinzufügen");
    }

    @FXML
    private void employeeAddToProj() {
        System.out.println("add Employee to project");
        if (PROJECT != null) {
            ExternalEmp e = (ExternalEmp) JDBCController.getInternal().searchEmployeeByID(((ExternalEmp) staffAll.getSelectionModel().selectedItemProperty().get()).getProject(),staffAll.getSelectionModel().selectedItemProperty().get().getEmpID());
            System.out.println("project != null");
            if (e != null) {
                System.out.println("emp != null");
                JDBCController jdbc = new JDBCController();
                try {
                    System.out.println("In der Methode: " + PROJECT + "---" + e.getProject());

                    JDBCController.getInternal().RemovePersonelFromProject(e, e.getProject());
                    JDBCController.getInternal().AddPersonelToProject(e, PROJECT);
                    jdbc.updateEmployee(e.getFirstName(), e.getLastName(), e.getSalary(), PROJECT.getProjectID(), e);

                    ObservableList<Employee> oEmp = FXCollections.observableArrayList(PROJECT.getEmployees());
                    staffInvolved.setItems(oEmp);

                    oEmp = FXCollections.observableArrayList(jdbc.loadEmployeeNotProject(PROJECT.getProjectID()));
                    staffAll.setItems(oEmp);

                } catch (ParseException exc) {
                    exc.printStackTrace();
                }
            }
            else
            {
                e = (ExternalEmp) staffAll.getSelectionModel().selectedItemProperty().get();
                JDBCController jdbc = new JDBCController();
                try {
                    System.out.println("In der Methode: " + PROJECT + "---" + e.getProject());

                    JDBCController.getInternal().RemovePersonelFromProject(e, e.getProject());
                    JDBCController.getInternal().AddPersonelToProject(e, PROJECT);
                    jdbc.updateEmployee(e.getFirstName(), e.getLastName(), e.getSalary(), PROJECT.getProjectID(), e);

                    ObservableList<Employee> oEmp = FXCollections.observableArrayList(PROJECT.getEmployees());
                    staffInvolved.setItems(oEmp);

                    oEmp = FXCollections.observableArrayList(jdbc.loadEmployeeNotProject(PROJECT.getProjectID()));
                    staffAll.setItems(oEmp);

                } catch (ParseException exc) {
                    exc.printStackTrace();
                }
            }

            insertEmployeeCosts(PROJECT);
        }
    }

    @FXML
    private void employeeRemoveFromProj() {
        if (PROJECT != null) {
            ExternalEmp e = (ExternalEmp) staffInvolved.getSelectionModel().selectedItemProperty().get();
            System.out.println("erste klammer");
            if (e != null) {
                System.out.println("zweite klammer");

                JDBCController jdbc = new JDBCController();
                try {
                    System.out.println("In der try methode zeuch: " + PROJECT.getEmployees().toString());

                    JDBCController.getInternal().RemovePersonelFromProject(e, PROJECT);
                    jdbc.updateEmployee(e.getFirstName(), e.getLastName(), e.getSalary(), 0, e);

                    ObservableList<Employee> oEmp = FXCollections.observableArrayList(PROJECT.getEmployees());
                    staffInvolved.setItems(oEmp);

                    oEmp = FXCollections.observableArrayList(jdbc.loadEmployeeNotProject(PROJECT.getProjectID()));
                    staffAll.setItems(oEmp);

                } catch (ParseException exc) {
                    exc.printStackTrace();
                }
            }
            insertEmployeeCosts(PROJECT);
        }
    }


    @FXML
    private void initialize() {
        projectID.setCellValueFactory(
                new PropertyValueFactory<Project, String>("ProjectID"));
        projectName.setCellValueFactory(
                new PropertyValueFactory<Project, String>("Name"));
        projectStart.setCellValueFactory(
                new PropertyValueFactory<Project, String>("StartDateString"));
        projectEnd.setCellValueFactory(
                new PropertyValueFactory<Project, String>("EndDateString"));

        staffAllFirstname.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("FirstName"));
        staffAllID.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("EmpID"));
        staffAllLastname.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("LastName"));
        staffAllProject.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("ProjectName"));

        staffInvolvedFirstname.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("FirstName"));
        staffInvolvedID.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("EmpID"));
        staffInvolvedLastname.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("LastName"));

        usedMaterialAmount.setCellValueFactory(
                new PropertyValueFactory<Material, String>("MaterialAmount"));
        usedMaterialName.setCellValueFactory(
                new PropertyValueFactory<Material, String>("MaterialName"));
        usedMaterialPrice.setCellValueFactory(
                new PropertyValueFactory<Material, String>("MaterialPrice"));

        materialAllName.setCellValueFactory(
                new PropertyValueFactory<Material, String>("MaterialName"));
        materialAllPrice.setCellValueFactory(
                new PropertyValueFactory<Material, String>("MaterialPrice"));

        messageGridPane.setStyle("-fx-background-color: white;");
        messageGridPane.getRowConstraints().clear();
        milestonesGridPane.setStyle("-fx-background-color: white;");
        milestonesGridPane.getRowConstraints().clear();


        /** Dummy Implementation of the Messaging System **/

        int xyz = 10;
        Label[] g = new Label[xyz];
        TextArea[] t = new TextArea[xyz];
        VBox[] a = new VBox[xyz];
        for (int i = 0; i < xyz; ++i) {
            g[i] = new Label();
            t[i] = new TextArea();
            a[i] = new VBox();
            t[i].getPrefColumnCount();
            t[i].setMinHeight(FONTSIZE * t[i].getPrefRowCount());
            t[i].prefWidth(Double.MAX_VALUE);
            t[i].setWrapText(true);
            t[i].setEditable(false);
            t[i].setStyle("-fx-font-size: " + FONTSIZE + "; -fx-text-fill: black;");
            g[i].setStyle("-fx-font-size: " + FONTSIZE + "; -fx-text-fill: black; -fx-text-alignment: top;");
            g[i].setText("hi" + i);
            t[i].setText(t[i].getPrefColumnCount() + "kasd aklsjd aksd aksdj aslkc kasd aklsjd aksd aksdj aslkc kasd aklsjd aksd aksdj aslkc kasd aklsjd aksd aksdj aslkc kasd aklsjd aksd aksdj aslkc");
            messageGridPane.add(g[i], 0, i);
            messageGridPane.add(t[i], 1, i);

        }


        projects.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        staffAll.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        staffInvolved.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        materialUsed.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        materialAll.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        projects.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Project>() {

                    public void changed(ObservableValue<? extends Project> observable, Project oldValue, Project newValue) {
                        insertOverviewDetails(newValue);
                        insertMilestones(newValue);
                        PROJECT = newValue;
                    }
                });


    }

    public void insertProjects(ObservableList<Project> _projectList) {
        PROJECTS = _projectList;
        projects.setItems(_projectList);
    }

    public void insertMaterials(ObservableList<Material> _materialList) {
        materialAll.setItems(_materialList);
    }

    private void insertOverviewDetails(Project _p) {

        if (_p != null) {

            JDBCController jdbc = new JDBCController();

            /** Set The TextFields for the Overview Pane **/
            overviewProjectName.setText(_p.getName());
            String milestoneString = "";
            String employeeString = "";
            String materialString = "";


            for (Milestones m : jdbc.loadMilestoneProjects(_p.getProjectID())) {
                milestoneString += m.getMilestoneDate().toString() + "\n" + m.getMilestoneName() + "\n";
            }

            overviewMilestones.setText(milestoneString);
            Double costs = (_p.getProjectCosts().getCalculatedMaterialCosts() + _p.getProjectCosts().getCalculatedStaffCosts());


            overviewCosts.setText(MONEY_FORMAT.format(costs) + " €");

            overviewStartEnd.setText(_p.getStartDateString() + " - " + _p.getEndDateString());

            for (Employee emp : _p.getEmployees()) {
                employeeString += emp.getEmpID() + " -- " + emp.getFirstName() + " " + emp.getLastName() + "\n";
            }

            overviewEmployees.setText(employeeString);

            ObservableList<Material> materials = FXCollections.observableArrayList();

            for (Map.Entry<Material, Integer> mat : _p.getProjectCosts().getMaterialCosts().entrySet()) {
                materialString += mat.getKey().getMaterialName() + " -- " + mat.getValue().toString() + " x " + mat.getKey().getMaterialPrice() + "\n";

                /** Add the material which is needed for the project **/

                mat.getKey().setMaterialAmount(mat.getValue());
                materials.addAll(mat.getKey());
            }

            overviewMaterial.setText(materialString);

            /** Set the Text Fields of the Employee Tab Pane **/

            ObservableList<Employee> oEmp = FXCollections.observableArrayList(_p.getEmployees());
            staffInvolved.setItems(oEmp);

            oEmp = FXCollections.observableArrayList(jdbc.loadEmployeeNotProject(_p.getProjectID()));
            staffAll.setItems(oEmp);

            /** Calculating and inserting staff costs per day  **/
            insertEmployeeCosts(_p);

            /** Set the Tables of the Material Tab Pane **/

            /** Insert used material into table **/
            materialUsed.setItems(materials);

            /** Insert costs for used material **/
            costs = _p.getProjectCosts().getCalculatedMaterialCosts();
            materialCosts.setText(MONEY_FORMAT.format(costs) + " €");


        }
    }

    /**
     * Change the Milestones to the Project milestones by creating textareas which r displaying the date and description
     **/
    private void insertMilestones(Project _p) {

        milestonesGridPane.getChildren().clear();

        int i = 0;
        for (Milestones m : _p.getMilestones()) {


            /** creating description fields **/
            TextArea t = new TextArea();
            t.getPrefColumnCount();
            t.setMinHeight(FONTSIZE * t.getPrefRowCount());
            t.prefWidth(Double.MAX_VALUE);
            t.setWrapText(true);
            t.setEditable(false);
            t.setStyle("-fx-font-size: " + FONTSIZE + "; -fx-text-fill: black;");
            t.setText(m.getMilestoneName()
                    + " -- "
                    + m.getMilestoneDate()
                    + "\n"
                    + m.getMilestoneInfo()
            );

            milestonesGridPane.add(t, 0, i);


            /**  creating change buttons for any milestone, anchorpane used as background and cell borders**/

            AnchorPane a = new AnchorPane();
            a.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1;");

            Button btn = new Button();
            btn.setText("Ändern");
            btn.setTranslateY(10);

            milestonesGridPane.add(a, 1, i);
            milestonesGridPane.add(btn, 1, i);

            GridPane.setValignment(btn, VPos.TOP);
            GridPane.setHalignment(btn, HPos.CENTER);

            i++;
        }
    }

    /**
     * Change the Milestones to the Project milestones by creating textareas which r displaying the date and description
     **/
    private void insertMessages(Project _p) {
        milestonesGridPane.getRowConstraints().clear();
        int i = 0;
        for (Milestones m : _p.getMilestones()) {

            /** creating description fields **/
            TextArea t = new TextArea();
            t.getPrefColumnCount();
            t.setMinHeight(FONTSIZE * t.getPrefRowCount());
            t.prefWidth(Double.MAX_VALUE);
            t.setWrapText(true);
            t.setEditable(false);
            t.setStyle("-fx-font-size: " + FONTSIZE + "; -fx-text-fill: black;");
            t.setText(m.getMilestoneName()
                    + " -- "
                    + m.getMilestoneDate()
                    + "\n"
                    + m.getMilestoneInfo()
            );
            RowConstraints rc = new RowConstraints();

            milestonesGridPane.add(t, 0, i);

            /**  creating change buttons for any milestone  **/
            Button btn = new Button();
            btn.setText("Ändern");
            btn.setTranslateY(10);
            milestonesGridPane.add(btn, 1, i);
            GridPane.setValignment(btn, VPos.TOP);
            GridPane.setHalignment(btn, HPos.CENTER);

            i++;
        }
    }

    private void insertEmployeeCosts(Project _p){
        /** Calculating and inserting staff costs per day  **/
        Double staffCosts = _p.getProjectCosts().getCalculatedStaffCosts() / ChronoUnit.DAYS.between(_p.getStartDate(), _p.getEndDate());
        employeeCosts0.setText(MONEY_FORMAT.format(staffCosts) + " €");

        /** staff costs for the whole projekt time period **/
        staffCosts = _p.getProjectCosts().getCalculatedStaffCosts();
        employeeCosts1.setText(MONEY_FORMAT.format(staffCosts) + " €");
    }

}
