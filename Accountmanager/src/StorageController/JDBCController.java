package StorageController;


import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.time.Instant;

import Project.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Employee.*;
import Project.*;

/**
 * mainclass to controll databasework
 **/
public class JDBCController {

    private static final String PERSONNEL = "prg4.personnel";
    private static final String EMPLOYEE = "prg4.employees";
    private static final String MILESTONE = "prg4.milestones";
    private static final String MESSAGE = "prg4.messages";
    private static final String PROJECT = "prg4.projects";


    /**
     * connect to database
     **/
    static public Connection connection;

    /**
     * Select all data of an table from database
     **/
    public String Select(String _FROM) {
        String preparedStatement = "";

        preparedStatement = "SELECT * FROM " + _FROM;

        return preparedStatement;

    }

    /**
     * Select all data of an table from database where the condition is true
     **/
    public String Select(String FROM, String WHERE) {
        String preparedStatement = "";

        preparedStatement = "SELECT * FROM " + FROM + " WHERE " + WHERE + " = ?";

        return preparedStatement;
    }

    /**
     * Select all data of an table from database where the condition is true and the returned data is equal to condition
     **/
    public String Select(String FROM, String WHERE, String _EQUALS) {
        String preparedStatement = "";

        preparedStatement = "SELECT * FROM " + FROM + " WHERE " + WHERE + " = '" + _EQUALS + "'";

        return preparedStatement;
    }

    /**
     * method to return the connectionstatement to database
     **/
    public Connection JdbcStorageController() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/prg4", "root", "Blackjack");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * get loginname and password from login to compare them
     **/
    public boolean loadPassword(String _loginName, String _pw) {
        String tmpPassword = null;
        Instant timestamp = Instant.now();


        try {
            Statement stmt = JdbcStorageController().createStatement();
            ResultSet rs = stmt.executeQuery(this.Select(PERSONNEL, "personnelLoginName", _loginName));
            if (rs.next()) {
                if (rs.getString("personnelPassword").equals(_pw)) {
                    return true;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * method to get all projects from database as an observableList
     **/
    public ObservableList<Project> loadProjects() {
        ObservableList<Project> oListProjects = FXCollections.observableArrayList();
        try {

            Project tmpProject = null;

            Statement stmt = JdbcStorageController().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM projects");
            while (rs.next()) {

                // Create the Projekt
                java.sql.Date tmpDateStart = rs.getDate("projectStart");
                java.sql.Date tmpDateEnd = rs.getDate("projectsEnd");

                LocalDate tmpDBStart = LocalDate.parse(tmpDateStart.toString());
                LocalDate tmpDBEnd = LocalDate.parse(tmpDateEnd.toString());

                tmpProject = new Project(rs.getString("projectName"), tmpDBStart, tmpDBEnd);
                tmpProject.setID(rs.getInt("projectID"));

                // Add Employee List to Projekt
                InternalEmp pers = new InternalEmp();
                pers.setAccessLevel(5);
                ArrayList<ExternalEmp> emps = this.loadEmployeeForProject(rs.getInt("projectID"));
                for (ExternalEmp emp : emps) {
                    pers.AddPersonelToProject(emp, tmpProject);
                }

                // Add Material to projekt
                tmpProject.getProjectCosts().setMaterialCosts(this.mapLoadProjectMaterial(rs.getInt("projectID")));


                oListProjects.addAll(tmpProject);
            }
            return oListProjects;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public JDBCController() {
        connection = null;
    }

    /**
     * List Employees für Projekt
     **/
    public ArrayList<ExternalEmp> loadEmployeeForProject(Integer _porjectID) {

        ArrayList<ExternalEmp> tmpListEmployee = new ArrayList<ExternalEmp>();
        Employee tmpEmployee = null;

        try {

            Statement stmt = JdbcStorageController().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + EMPLOYEE + " WHERE employeeProject = '" + _porjectID + "'");

            while (rs.next()) {
                tmpListEmployee.add(new ExternalEmp(rs.getString("employeesFirstName"), rs.getString("employeesLastName"), rs.getDouble("employeesSaleray"), rs.getInt("employeesID")));
            }

            return tmpListEmployee;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * List Employees ungleich Projekt
     **/
    public ArrayList<ExternalEmp> loadEmployeeNotProject(Integer _porjectID) {

        ArrayList<ExternalEmp> tmpListEmployee = new ArrayList<ExternalEmp>();
        Employee tmpEmployee = null;

        try {

            Statement stmt = JdbcStorageController().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees e join projects p on e.employeeProject = p.projectID WHERE employeeProject != '" + _porjectID + "'");

            while (rs.next()) {
                tmpListEmployee.add(new ExternalEmp(rs.getString("employeesFirstName"), rs.getString("employeesLastName"), rs.getDouble("employeesSaleray"), rs.getInt("employeesID")));
                tmpListEmployee.get(tmpListEmployee.size() - 1).setProjekt(rs.getString("projectName"));
            }
            if (tmpListEmployee.isEmpty()) {
                tmpListEmployee.add(new ExternalEmp("", ""));
            }
            return tmpListEmployee;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * List allEmployees
     **/
    public ArrayList<Employee> loadAllEmployee(Integer _porjectID) {

        ArrayList<Employee> tmpListEmployee = new ArrayList<Employee>();
        Employee tmpEmployee = null;

        try {

            Statement stmt = JdbcStorageController().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employee");
            while (rs.next()) {
                tmpListEmployee.add(new ExternalEmp(rs.getString("employeeFirstName"), rs.getString("employeeLastName"), rs.getDouble("employeeSalery"), rs.getInt("employeeProject")));
            }

            return tmpListEmployee;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Objekt Liste Milestones Arraylist
     **/
    public List<Milestones> loadAllMilestone() {

        List<Milestones> tmpListMilestones = null;
        Milestones tmpMilestone = null;

        try {

            Statement stmt = JdbcStorageController().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM milestones");

            java.sql.Date tmpDate = null;


            while (rs.next()) {
                tmpMilestone.setMilestoneID(rs.getInt("milestoneID"));
                tmpMilestone.setMilestoneProjectID(rs.getInt("projectID"));
                tmpDate = (rs.getDate("milestoneDate"));
                tmpMilestone.setMilestoneInfo(rs.getString("milestoneDescription"));
                tmpMilestone.setDateOfMilestone(tmpDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                tmpListMilestones.add(tmpMilestone);
            }

            return tmpListMilestones;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Object List Milestones Arraylist
     **/
    public ArrayList<Milestones> loadMilestoneProjects(Integer _projectID) {

        ArrayList<Milestones> tmpListMilestones = null;
        Milestones tmpMilestone = null;

        try {

            Statement stmt = JdbcStorageController().createStatement();
            ResultSet rs = stmt.executeQuery(this.Select(MILESTONE, "projectID", _projectID.toString()));

            java.sql.Date tmpDate = null;

            tmpMilestone = new Milestones();
            tmpListMilestones = new ArrayList<Milestones>();


            while (rs.next()) {

                tmpDate = (rs.getDate("milestoneDate"));
                tmpListMilestones.add(new Milestones(
                        rs.getInt("milestonesID"),
                        rs.getString("milestoneName"),
                        rs.getString("milestoneDescription"),
                        tmpDate.toLocalDate()
                ));
            }


            return tmpListMilestones;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * SQL-Setter
     **/

    public boolean saveProjects(String _name, Integer _dayStart, Integer _monthStart, Integer _yearStart, Integer _dayEnd, Integer _monthEnd, Integer _yearEnd, String _admin) throws ParseException {

        String startDate = _yearStart + "-" + _monthStart + "-" + _dayStart;
        String endDate = _yearEnd + "-" + _monthEnd + "-" + _dayEnd;


        DateFormat formatStart = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date dateStart = formatStart.parse(startDate);

        DateFormat formatEnd = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date dateEnd = formatEnd.parse(startDate);

        java.sql.Date sqlDateStart = new java.sql.Date(dateStart.getTime());
        java.sql.Date sqlDateEnd = new java.sql.Date(dateStart.getTime());

        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTimestamp = new Timestamp(calendar.getTime().getTime());

        try {
            Statement stmt = JdbcStorageController().createStatement();

            String SQL = "INSERT INTO projects (projectName, projectStart, projectEnd, projectTimeStamp, projectAdmin) VALUES ('" + _name + "', '" + sqlDateStart + "','" + sqlDateEnd + "','" + currentTimestamp + "', '" + _admin + "')";
            PreparedStatement psProjects = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            psProjects.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * SQL-Insert into
     **/

    public boolean saveEmployees(String _firstName, String _lastName, Integer _salery, Integer _project) throws ParseException {

        try {
            Statement stmt = JdbcStorageController().createStatement();
            String SQL = "INSERT INTO projects (employeeFirstName, employeeLastName, employeeSalery, employeeProject) VALUES ('" + _firstName + "', '" + _lastName + "','" + _salery + "','" + _project + "')";
            PreparedStatement psEmployee = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            psEmployee.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method to insert material into the database
     **/
    public boolean saveMaterial(String _firstName, String _materialName, Integer _materialPrice) throws ParseException {

        try {
            Statement stmt = JdbcStorageController().createStatement();
            String SQL = "INSERT INTO projects (materialName, materialPrice) VALUES ('" + _firstName + "', '" + _materialName + "','" + _materialPrice + "')";
            PreparedStatement psMaterial = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            psMaterial.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * method to insert the Milestones into the database
     **/
    public boolean saveMileStone(Integer _projectID, String _milestoneYear, String _milestoneMonth, String _milestoneDay, String _milestoneDescription) throws ParseException {


        String milestoneDate = _milestoneYear + "-" + _milestoneMonth + "-" + _milestoneDay;

        DateFormat formatStart = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date dateStart = formatStart.parse(milestoneDate);

        java.sql.Date sqlMileStone = new java.sql.Date(dateStart.getTime());

        try {
            Statement stmt = JdbcStorageController().createStatement();
            String SQL = "INSERT INTO projects (projectID, milestoneDate, milestoneDescription) VALUES ('" + _projectID + "', '" + sqlMileStone + "','" + _milestoneDescription + "')";
            PreparedStatement psMaterial = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            psMaterial.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * method to insert message into database
     **/
    public boolean saveMessage(Integer _projectID, String _messageText, String _messageWriter) throws ParseException {


        // EINTRAG in der Datenbank noch ändern! TYP!

        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTimestamp = new Timestamp(calendar.getTime().getTime());

        try {
            Statement stmt = JdbcStorageController().createStatement();
            String SQL = "INSERT INTO projects (projectID, milestoneDate, milestoneDescription,) VALUES ('" + _projectID + "','" + currentTimestamp + "', '" + _messageText + "','" + _messageWriter + "')";
            PreparedStatement psMessage = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            psMessage.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * method to insert the projectmaterials into the database
     **/
    public boolean saveProjectMaterial(Integer _projectID, Integer _materialID, Integer _materialAmount) throws ParseException {


        try {
            Statement stmt = JdbcStorageController().createStatement();
            String SQL = "INSERT INTO projects (projectID, marterialID, materialAmount) VALUES ('" + _projectID + "','" + _materialID + "', '" + _materialAmount + "')";
            PreparedStatement psMaterial = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            psMaterial.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** SQL-Deleter **/

    /**
     * method to delete a project from database by projectid
     **/
    public boolean deleteProject(Integer _projectID) {
        try {
            Statement stmt = JdbcStorageController().createStatement();
            String deleteImage = "DELETE FROM images " + "WHERE projectID = '" + _projectID + "'";
            String deleteMilestones = "DELETE FROM milestone " + "WHERE projectID = '" + _projectID + "'";
            String deleteProjectMaterial = "DELETE FROM project_material " + "WHERE projectID = '" + _projectID + "'";
            String deleteMessage = "DELETE FROM message " + "WHERE projectID = '" + _projectID + "'";
            String deleteEntryEmployee = "UPDATE employee" + "SET projectID = null" + " WHERE projectID = '" + _projectID + "'";
            String deleteProject = "DELETE FROM project " + "WHERE projectID = '" + _projectID + "'";


            PreparedStatement psDelete = connection.prepareStatement(deleteImage);
            psDelete.executeUpdate();
            psDelete = connection.prepareStatement(deleteMilestones);
            psDelete.executeUpdate();
            psDelete = connection.prepareStatement(deleteProjectMaterial);
            psDelete.executeUpdate();
            psDelete = connection.prepareStatement(deleteMessage);
            psDelete.executeUpdate();
            psDelete = connection.prepareStatement(deleteEntryEmployee);
            psDelete.executeUpdate();
            psDelete = connection.prepareStatement(deleteProject);
            psDelete.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;


    }

    /**
     * method to delete a employee from database by employeeid
     **/
    public boolean deleteEmployee(Integer _employeeID) {
        try {
            Statement stmt = JdbcStorageController().createStatement();
            String deleteEmployee = "DELETE FROM employee " + "WHERE employeeID = '" + _employeeID + "'";

            PreparedStatement psDeleteEmployee = connection.prepareStatement(deleteEmployee);
            psDeleteEmployee.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * method to delete a image from database by imageid
     **/
    public boolean deleteImage(Integer _imagesID) {
        try {
            Statement stmt = JdbcStorageController().createStatement();
            String deleteImgages = "DELETE FROM images " + "WHERE imagesID = '" + _imagesID + "'";
            PreparedStatement psDeleteImages = connection.prepareStatement(deleteImgages);
            psDeleteImages.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * method to delete a milestone from database by milestoneid
     **/
    public boolean deleteMileStone(Integer _milestoneID) {
        try {
            Statement stmt = JdbcStorageController().createStatement();
            String deleteMilestone = "DELETE FROM milestone " + "WHERE milestoneID = '" + _milestoneID + "'";
            PreparedStatement psDeleteMilestone = connection.prepareStatement(deleteMilestone);
            psDeleteMilestone.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<Material> loadMaterial() {
        ObservableList<Material> tmpListMaterial = null;
        Material tmpMaterial = null;

        try {

            Statement stmt = JdbcStorageController().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM milestone");


            while (rs.next()) {
                tmpMaterial.setMaterialID(rs.getInt("materialID"));
                tmpMaterial.setMaterialName(rs.getString("materialName"));
                tmpMaterial.setMaterialPrice(rs.getDouble("materialPrice"));
            }

            return tmpListMaterial;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Map<Material, Integer> mapLoadProjectMaterial(Integer _projectID) {

        Map<Material, Integer> listProjectMaterial = new HashMap<>();

        try {

            Statement stmt = JdbcStorageController().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT materialName, materialAmount, m.materialID, materialPrice FROM  prg4.project_material pm JOIN prg4.material m ON pm.materialID = m.materialID WHERE projectID = '" + _projectID + "'");
            while (rs.next()) {
                Material x = new Material();
                x.setMaterialID(rs.getInt("materialID"));
                x.setMaterialName(rs.getString("materialName"));
                x.setMaterialPrice(rs.getDouble("materialPrice"));
                listProjectMaterial.put(x, rs.getInt("materialAmount"));
            }

            stmt.close();

            rs.close();
            return listProjectMaterial;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateProjects(Integer _projectID, String _name, Integer _dayStart, Integer _monthStart, Integer _yearStart, Integer _dayEnd, Integer _monthEnd, Integer _yearEnd, String _admin) throws ParseException {

        String startDate = _yearStart + "-" + _monthStart + "-" + _dayStart;
        String endDate = _yearEnd + "-" + _monthEnd + "-" + _dayEnd;

        DateFormat formatStart = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date dateStart = formatStart.parse(startDate);

        DateFormat formatEnd = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date dateEnd = formatEnd.parse(startDate);

        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTimestamp = new Timestamp(calendar.getTime().getTime());

        try {
            Statement stmt = JdbcStorageController().createStatement();

            String SQL = "UPDATE projects SET projectName ='" + _name + "', projectStart='" + startDate + "', projectEnd='" + endDate + "',projectTimeStamp='" + currentTimestamp + "',projectAdmin= '" + _admin + "' WHERE projectID = '" + _projectID + "'";
            PreparedStatement psProjects = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            psProjects.executeUpdate();

            stmt.close();
            psProjects.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEmployee(String firstName, String lastName, Integer salery, Integer project) throws ParseException {


        try {
            Statement stmt = JdbcStorageController().createStatement();

            String SQL = "UPDATE employee SET employeeFirtName ='" + firstName + "', employeeLastName='" + lastName + "', employeeSalery='" + salery + "',employeeProject='" + project + "'";
            PreparedStatement psEmployee = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            psEmployee.executeUpdate();

            stmt.close();

            psEmployee.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateMaterial(Integer _materialID, String materialName, Integer _materialPrice) throws ParseException {
        try {
            Statement stmt = JdbcStorageController().createStatement();

            String SQL = "UPDATE projects SET materialName='" + materialName + "',materialPrice= '" + _materialPrice + "' WHERE materialID = '" + _materialID + "'";
            PreparedStatement psEmployee = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            psEmployee.executeUpdate();

            stmt.close();
            psEmployee.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateMilestones(Integer _projectID, Integer _milestoneID, String _milestoneYear, String _milestoneMonth, String _milestoneDay, String _milestoneDescription) throws ParseException {
        try {

            String milestoneDate = _milestoneYear + "-" + _milestoneMonth + "-" + _milestoneDay;

            DateFormat formatStart = new SimpleDateFormat("yyyy-mm-dd");
            java.util.Date dateStart = formatStart.parse(milestoneDate);

            java.sql.Date sqlMileStone = new java.sql.Date(dateStart.getTime());


            Statement stmt = JdbcStorageController().createStatement();

            String SQL = "UPDATE milestones SET projectID='" + _projectID + "',milestoneDate= '" + sqlMileStone + "' WHERE milestoneID = '" + _milestoneID + "'";
            PreparedStatement psMilestone = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            psMilestone.executeUpdate();

            stmt.close();
            psMilestone.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}

