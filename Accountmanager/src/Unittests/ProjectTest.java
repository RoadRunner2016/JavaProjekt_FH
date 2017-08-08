
package Unittests;

import Project.*;
import Employee.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * Created by Ben on 02.01.2017.
 */

public class  ProjectTest {


    private Project testProject;
    private ExternalEmp testEEmp;
    private ExternalEmp testEEmp2;

    @Before
    public void setUp()
    {
        testProject = new Project("asd",2017,1,1,2017,1,31);
        testEEmp = new ExternalEmp("Max", "Mustermann");
        testEEmp2 = new ExternalEmp("Max", "Mustermann");
        testEEmp.setSalary(1500);
        testEEmp2.setSalary(500);
    }

    @Test

    public void checkCosts_saleryAndMaterialCosts()                                                                     /** Test noch nicht fertig, und Materialkostentest hinzufügen (Rundungsfehler beheben) **/
    {

        testProject.getEmployees().add(testEEmp);
        testProject.getEmployees().add(testEEmp2);

        Assert.assertTrue(testProject.getProjectCosts().getCalculatedStaffCosts() >= 2000);

    }

}
