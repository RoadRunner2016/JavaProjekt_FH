package Employee;


import java.util.List;

import Project.Project;

/**
 * Created by A on 10.12.2016.
 */

/**
 * mainclass for all employees with information about them
 **/
public abstract class Employee {
    private static final int START_ID = 2017000000;
    protected Integer projectID;
    protected static int idCounter = START_ID;
    protected String firstName;
    protected String lastName;
    protected int empID;
    protected double monthlySalary = 1876.42;
    protected List<Project> projectsInvolved;

    /**
     * get monthly salary of an employee
     **/
    public double getSalary() {
        return monthlySalary;
    }

    /**
     * get employee-id
     **/
    public int getEmpID() {
        return empID;
    }

    /**
     * get first name of an internal employee
     **/
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * get last name of an internal employee
     **/
    public String getLastName() {
        return this.lastName;
    }

    /**
     * get IDCounter
     **/
    public static final int getIdCounter() {
        return idCounter;
    }

    /**
     * get startID
     **/
    public static int getStartId() {
        return START_ID;
    }

    /**
     * change monthly salary
     **/
    public void setSalary(double _salary) {
        this.monthlySalary = _salary;
    }

    public void setFirstName(String _fName) {
        this.firstName = _fName;
    }

    public void setLastName(String _lastName) {
        this.lastName = _lastName;

    }

    public void setID(Integer _employeeID) {
        this.empID = _employeeID;
    }

    public void setProjectID(Integer _projectID) {
        this.projectID = _projectID;
    }

}