package Employee;

import Project.Project;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by A on 02.01.2017.
 */
/**subclass of employee, gets added to projects and works on projects**/
public class ExternalEmp extends Employee
{
    private int numberOfProjectsInvolved;

    public ExternalEmp(String _firstName, String _lastName)
    {
        firstName = _firstName;
        lastName = _lastName;
        projectsInvolved = new ArrayList<Project>();
    }

    public ExternalEmp(String _firstName, String _lastName, Double _salary,Integer _employeeID)
    {
        firstName = _firstName;
        lastName = _lastName;
        monthlySalary =_salary;
        empID = _employeeID;
    }

    private Project Projekt;

    public void setProjekt(Project s){Projekt = s;}

    public Project getProject(){return Projekt;}

    public String getProjectName(){return Projekt.getName();}

    /**get number of projects the external employee is involved**/
    public int getNumberOfProjectsInvolved()
    {
        return projectsInvolved.size();
    }

    /**get list of projects the external employee is involved**/
    public List<Project> getProjectList()
    {return this.projectsInvolved;}
}
