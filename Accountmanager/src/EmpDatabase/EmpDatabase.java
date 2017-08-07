package EmpDatabase;

import Employee.*;
import Project.Project;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by A on 06.02.2017.
 */

/**
 * Database with lists of information about all employees and projects
 **/
public class EmpDatabase {
    private List<Employee> tmpEdatabase = new ArrayList<Employee>();
    private List<Project> tmpProjects = new ArrayList<Project>();

    /**
     * delete all lists
     **/
    public void removeAll() {
        tmpEdatabase.clear();
        tmpProjects.clear();
    }

    /**
     * add a project to the database
     **/
    public boolean AddProjToDatabase(Project _proj) {
        return tmpProjects.add(_proj);
    }

    /**
     * get the list of projects
     **/
    public List getAllProj() {
        return tmpProjects;
    }

    /**
     * get the list of employess
     **/
    public List getAllEmpl() {
        return tmpEdatabase;
    }

    /**
     * get amount of projects
     **/
    public int getNumberOfProjects() {
        return tmpProjects.size();
    }

    /**
     * search for an employee by ID
     **/
    public Employee searchEmployeeByID(int _givenID) {
        Iterator<Employee> actualEmployee = this.tmpEdatabase.iterator();

        int id = _givenID + Employee.getStartId();

        while (actualEmployee.hasNext()) {
            Employee e = actualEmployee.next();
            if (e.getEmpID() == id) {
                return e;
            }
        }

        return null;


    }

    /**
     * search for a project by name
     **/
    public Project searchProjectByName(String _givenName) {
        Iterator<Project> actualProject = this.tmpProjects.iterator();

        while (actualProject.hasNext()) {
            Project p = actualProject.next();
            if (p.getName() == _givenName) {
                return p;
            }
        }

        return null;


    }

}
