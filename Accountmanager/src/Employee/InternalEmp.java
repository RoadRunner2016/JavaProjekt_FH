package Employee;

import EmpDatabase.EmpDatabase;
import Project.Project;
import Project.Milestones;

import java.util.Iterator;



/**
 * Created by Ben on 02.01.2017.
 */
/**subclass of employee, generates new projects or changes and deletes them, add employees to projects or removes them**/

public class InternalEmp extends Employee {

    private String eMail;
    private String loginName;
    private String password;
    private int accessLevel;
    private Boolean onStatus;

    public InternalEmp(String _firstName, String _lastName, String _eMail) {
        firstName = _firstName;
        lastName = _lastName;
        empID = idCounter;
        idCounter++;
        eMail = _eMail;
        loginName = _lastName + _firstName;
        password = "1234";
        accessLevel = 1;
        onStatus = false;
    }
    public InternalEmp() {
        this("Max", "Mustermann", "info@mail.com");
    }

    /**
     * Construtor
     **/

    /**create a new project**/
    public Project createProject(int _startYYYY, int _startMonth, int _startDay, int _endYYYY, int _endMonth, int _endDay, String _nameOfNewProj) {
        Project newP = new Project("",_startYYYY, _startMonth, _startDay, _endYYYY, _endMonth, _endDay);
        newP.setName(_nameOfNewProj);
        return newP;
    }


    // Getter + Setter

    /**get email of an internal employee**/
    public String geteMail() {
        return this.eMail;
    }
    /**get loginname of an internal employee**/
    public String getLoginName() {
        return this.loginName;
    }
    /**get password of an internal employee**/
    public String getPassword() {
        return this.password;
    }
    /**get accesslevel of an internal employee**/
    public Integer getAccessLevel() {
        return this.accessLevel;
    }
    /**get status of an internal employee**/
    public boolean getOnStatus() {
        return this.onStatus;
    }

    /**set id of an internal emplyoee**/
    public void setEmpID(int _empID) { empID = _empID; }
    /**set password of an internal emplyoee**/
    public void setPassword(String _password) { password = _password; }
    /**set accesslevel of an internal employee**/
    public void setAccessLevel(int _newLevel) {
        accessLevel = _newLevel;
    }
    /**set status of an internal employee**/
    public void setOnStatus(boolean _newStatus) {
        onStatus = _newStatus;
    }
    /**set email of an internal employee**/
    public void setEmail(String _eMail) { eMail= _eMail;}
    /** logoutmethod **/
    public boolean logoutPersonal()
    {

        this.setOnStatus(false);
        System.out.println(" Sie wurden erfolgreich abgemeldet. ");
        return false;
    }

    /*****************************EMPLOYEECONTROLLING******************************/
    /** shows all information about an employee in database **/
    public boolean showEmployee(EmpDatabase tmpDB)
    {

        Iterator<Employee> eItrtemp = tmpDB.getAllEmpl().iterator();
        Integer x = 1;
        System.out.println("Inhalt der folgenden Datenbank:\n");
        while (eItrtemp.hasNext()) {


            InternalEmp tmpEmpl = new InternalEmp();
            tmpEmpl = (InternalEmp) eItrtemp.next();
            System.out.println(" Benutzer " + x + ":");
            System.out.println(" Loginname:  \t" + tmpEmpl.getLoginName());
            System.out.println(" Password:  \t" + tmpEmpl.getPassword());
            System.out.println(" E-Mail:  \t \t" + tmpEmpl.geteMail());
            System.out.println(" Accesslevel: \t" + tmpEmpl.getAccessLevel());
            System.out.println("\n");
            x++;
        }
        System.out.println("Job Done!");
        return true;
    }

    /** search for an employee in database by name **/
    public String searchEmployee(EmpDatabase tmpDB, String _tmpeName)
    {
        Iterator<Employee> eItrtemp = tmpDB.getAllEmpl().iterator();
        InternalEmp tmpIntEmpl;

        while (eItrtemp.hasNext()) {
            tmpIntEmpl = (InternalEmp) eItrtemp.next();
            if ((tmpIntEmpl.getLoginName()).equals(_tmpeName)) {
                System.out.println(" Name wurde gefunden. \t" + _tmpeName + "gefunden: \n" + tmpIntEmpl.getLoginName());

                return tmpIntEmpl.getLoginName();
            }
        }

        System.out.println(" Name wurde nicht gefunden, bitte überprüfen Sie ihre Eingabe. ");

        return null;

    }

    public Employee searchEmployeeByID(Project _prj, int _givenID)
    {
        Iterator<Employee> actualEmployee = _prj.getEmployees().iterator();

        int id = _givenID;

        while (actualEmployee.hasNext())
        {
            Employee e = actualEmployee.next();
            if (e.getEmpID() == id) {
                return e;
            }
        }

        return null;


    }

    /*****************************PROJECTCONTROLLING******************************/
    /** add a new Project to the specific Database**/
    public boolean AddProjectToDatabase(EmpDatabase _dB, Project _proj)
    {
        return _dB.AddProjToDatabase(_proj);
    }
    /** add an employee to a specific project **/
    public boolean AddPersonelToProject(ExternalEmp _tmpEmployee, Project _proj)
    {
        if (this.accessLevel > 2 /*&& _tmpEmployee.getNumberOfProjectsInvolved() < 3 && !_proj.getEmployees().contains(_tmpEmployee)*/) {
            if (_proj.getEmployees().add(_tmpEmployee)  /*&& _tmpEmployee.projectsInvolved.add(_proj)*/) {
                _tmpEmployee.setProjekt(_proj);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }
    /** remove an employee from a project **/
    public boolean RemovePersonelFromProject(ExternalEmp _tmpEmployee, Project _proj)
    {
        System.out.println(_tmpEmployee + " in methode remove " + _proj + _proj.getEmployees());
        if (this.accessLevel > 2 && _proj.getEmployees().contains(_tmpEmployee)) {
            System.out.println("erste if in remove");
            if (_proj.getEmployees().remove(_tmpEmployee) /* && _tmpEmployee.projectsInvolved.remove(_proj) */ ) {
                System.out.println(_tmpEmployee + " removed from " +_proj);
                _tmpEmployee.setProjekt(null);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**delete a project**/
    public boolean DeleteProject(EmpDatabase _db, Project _proj)
    {
        _db.getAllProj().remove(_proj);

        return true;
    }
    /**change a project**/
    public boolean modifyProject(Project _proj, EmpDatabase _db) {

        return true;
    }


    /*****************************ACCOUNTCONTROLLING******************************/
    /** add externalemployee to the database **/
    public boolean hireNewEmployee(EmpDatabase _dB, String _firstName, String _lastName) {
        return _dB.getAllEmpl().add(new ExternalEmp(_firstName, _lastName));
    }
    /** add internalemployee to the database **/
    public boolean hireNewEmployee(EmpDatabase _dB, String _firstName, String _lastName, String _email)
    {
        return _dB.getAllEmpl().add(new InternalEmp(_firstName, _lastName, _email));
    }
    /** delete an employee from the datebase **/
    public boolean fireEmployee(EmpDatabase _dB, Employee _emp)
    {
        return _dB.getAllEmpl().remove(_emp);
    }

    /*****************************MILESTONECONTROLLING******************************/
    /** test if accesslevel of internal employee is high enough - add a milestones to the project **/
    public boolean addNewMilestone(Project _proj, Milestones _tmpMilestone)
    {

        if (this.accessLevel > 3 && _proj != null) {
            _proj.getMilestones().add(_tmpMilestone);

            return true;
        }

        return false;
    }
    /** get the number of milestones of a project - get milestone by ID - delete milestone from the project **/
    public boolean deleteMilestone(Project _proj, Integer _tmpMilestoneID)
    {
        if (this.accessLevel > 0)
        {

            for (int i = 0; i < (_proj.getMilestones().size()); i++)
            {
                System.out.println(i);

                if (_proj.getMilestones().get(i).getMilestoneID().equals(_tmpMilestoneID))
                {
                    _proj.getMilestones().remove(i);
                    return true;
                }
            }
        }

        return false;
    }


    /*****************************COSTCONTROLLING******************************/

    /**
     * Adds a new Material/Equipment/what ever and its costs to a sorted map, if the String Key is already given to another pair of String/double this one will be overwritten
     **/
    public void AddMatCosts(String _matName, double _costValue, Project _nameProject) {
        Double d = _costValue;

    }


}