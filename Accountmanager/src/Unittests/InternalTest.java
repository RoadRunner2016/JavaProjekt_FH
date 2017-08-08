package Unittests;

import EmpDatabase.EmpDatabase;
import Employee.*;
import Project.Project;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by A on 06.02.2017.
 */
public class InternalTest {

    private InternalEmp controller;
    private EmpDatabase myDB;
    private EmpDatabase TestDatabase;
    private Project TestProject;


    @Before
    public void setUp()
    {
        TestDatabase = new EmpDatabase();
        controller = new InternalEmp();
        controller.setAccessLevel(5);
        myDB = new EmpDatabase();
        TestProject = new Project("prj1",2017, 1,1,2017,1,31);
       // myDB.tmpEdatabase.add(controller);
    }

    @After
    public void tearDown()
    {
        myDB.removeAll();
    }

    @Test
    public void test_searchEmployee_checkTheDatabase()
    {
        InternalEmp tmpMax = new InternalEmp("Max", "Mustermann", "Max@gmx.de");
        InternalEmp tmpMaxi = new InternalEmp("Maxi", "Mustermann", "Maxi@gmx.de");
        InternalEmp tmpKalle = new InternalEmp("Kalle", "Nethe","Kalle@gmx.de");

        TestDatabase.getAllEmpl().add(tmpKalle);
        TestDatabase.getAllEmpl().add(tmpMax);
        TestDatabase.getAllEmpl().add(tmpMaxi);

        Assert.assertNotNull(controller.searchEmployee(TestDatabase, "MustermannMax"));


    }


    @Test
    public void test_hireNewPersonnel_hiresPersonnelWhichIsNotPartOfTheCompany()
    {


        final int actualID = Employee.getIdCounter() - Employee.getStartId() - 1;

        controller.hireNewEmployee(myDB,"Fussvolk","Eins");
        controller.hireNewEmployee(myDB,"Fussvolk","Zwei");
        controller.hireNewEmployee(myDB,"Interner","Eins","eins@mail.de");

        String lastName = "Klausen";
        String firstName = "Klaus";

        controller.hireNewEmployee(myDB,firstName,lastName,"zwei@mail.de");

        Assert.assertEquals("Size of myDB should be 4",myDB.getAllEmpl().size(),4);

        Iterator<Employee> actualEmployee = myDB.getAllEmpl().iterator();

        Assert.assertEquals("The classes of the itarator pointing to the first " +
                        "employee and the employee with the first ID should be the same",
                actualEmployee.next(), myDB.searchEmployeeByID(actualID+1));

        Assert.assertTrue(actualEmployee.next() == myDB.searchEmployeeByID(actualID+2));

        Assert.assertTrue(actualEmployee.next() == myDB.searchEmployeeByID(actualID+3));

        InternalEmp intEmp = (InternalEmp)actualEmployee.next();

        Assert.assertEquals(intEmp.getLoginName(), lastName+firstName);

        myDB.removeAll();
    }

    @Test
    public void test_fireNewPersonnel_firedPersonnelShouldBeRemovedAfterThisMethod()
    {


        final int actualID = Employee.getIdCounter() - Employee.getStartId() - 1;

        controller.hireNewEmployee(myDB,"Fussvolk","Eins");
        controller.hireNewEmployee(myDB,"Fussvolk","Zwei");
        controller.hireNewEmployee(myDB,"Interner","Eins","eins@mail.de");

        String lastName = "Klausen";
        String firstName = "Klaus";

        controller.hireNewEmployee(myDB,firstName,lastName,"zwei@mail.de");

        Employee firedEmp = myDB.searchEmployeeByID(actualID + 3);

        Assert.assertTrue(controller.fireEmployee(myDB,myDB.searchEmployeeByID(actualID+3)));

        Assert.assertFalse(myDB.getAllEmpl().contains(firedEmp));

        Assert.assertFalse(controller.fireEmployee(myDB,myDB.searchEmployeeByID(actualID + 3)));

        Assert.assertEquals(myDB.getAllEmpl().size(),3);

        myDB.removeAll();
    }

    @Test
    public void test_createProject_And_addProjToDatabase()
    {


        Project myNewProj = controller.createProject(2016,10,17,2017,3,3,"PRG3");

        Assert.assertTrue(controller.AddProjectToDatabase(myDB,myNewProj));

        controller.AddProjectToDatabase(myDB,controller.createProject(2017,3,3,2017,4,1,"PRG3 - WDH"));

        Assert.assertEquals("Number of projects should be 2, because we added 2 projects",myDB.getNumberOfProjects(),2);

    }

    @Test
    public void test_addPersonnelToProject()
    {

        Project myNewProj = new Project("p",2016,10,17,2017,3,3);
        Project myOtherNewProj = new Project("p",2017,1,17,2017,3,3);
        Project myThirdNewProj = new Project("p1");
        Project myLastNewProj = new Project("p2");
        ExternalEmp extEmp0 = new ExternalEmp("Fussvolk","Eins");
        ExternalEmp extEmp1 = new ExternalEmp("Fussvolk","Zwei");
        ExternalEmp extEmp2 = new ExternalEmp("Fussvolk","Drei");
        InternalEmp intEmp0 = new InternalEmp("Hier","Name","vorstellen@mail.de");

        Assert.assertEquals(myNewProj.getEmployees().size(),0);

        Assert.assertTrue(controller.AddPersonelToProject(extEmp0,myNewProj));

        Assert.assertEquals(myNewProj.getEmployees().size(),1);

        Assert.assertEquals(extEmp0.getNumberOfProjectsInvolved(),1);

        Assert.assertFalse(controller.AddPersonelToProject(extEmp0,myNewProj));

        controller.AddPersonelToProject(extEmp0,myOtherNewProj);

        controller.AddPersonelToProject(extEmp1,myOtherNewProj);

        controller.AddPersonelToProject(extEmp2,myOtherNewProj);

        Assert.assertEquals(extEmp0.getNumberOfProjectsInvolved(),2);

        Assert.assertEquals(myOtherNewProj.getEmployees().size(),3);

        Assert.assertTrue(controller.AddPersonelToProject(extEmp0,myThirdNewProj));

        Assert.assertEquals(extEmp0.getNumberOfProjectsInvolved(),3);

        Assert.assertFalse(controller.AddPersonelToProject(extEmp0,myLastNewProj));

        Assert.assertEquals(extEmp0.getNumberOfProjectsInvolved(),3);

    }

    @Test
    public void test_removePersonnelFromProject()
    {

        Project myNewProj = new Project("asd",2016,10,17,2017,3,3);
        Project myOtherNewProj = new Project("asd",2017,1,17,2017,3,3);

        ExternalEmp extEmp0 = new ExternalEmp("Fussvolk","Eins");
        ExternalEmp extEmp1 = new ExternalEmp("Fussvolk","Zwei");
        ExternalEmp extEmp2 = new ExternalEmp("Fussvolk","Drei");
        InternalEmp intEmp0 = new InternalEmp("Hier","Name","vorstellen@mail.de");

        controller.AddPersonelToProject(extEmp0,myNewProj);
        controller.AddPersonelToProject(extEmp1,myNewProj);
        controller.AddPersonelToProject(extEmp2,myNewProj);

        Assert.assertTrue(controller.RemovePersonelFromProject(extEmp0,myNewProj));
        Assert.assertEquals(myNewProj.getEmployees().size(),2);
        Assert.assertTrue(controller.RemovePersonelFromProject(extEmp1,myNewProj));
        Assert.assertFalse(controller.RemovePersonelFromProject(extEmp2,myOtherNewProj));


    }

    @Test
    public void test_showEmployeesFromDatabase()
    {

        EmpDatabase TestDatabase = new EmpDatabase();

        InternalEmp tmpMax = new InternalEmp("Max", "Mustermann", "Max@gmx.de");
        InternalEmp tmpMaxi = new InternalEmp("Maxi", "Mustermann", "Maxi@gmx.de");
        InternalEmp tmpKalle = new InternalEmp("Kalle", "Nethe","Kalle@gmx.de");

        TestDatabase.getAllEmpl().add(tmpMax);
        TestDatabase.getAllEmpl().add(tmpMaxi);
        TestDatabase.getAllEmpl().add(tmpKalle);

        Assert.assertTrue(tmpMax.showEmployee(TestDatabase));
    }
    @Test

    public void test_matCosts()
    {

        controller.AddMatCosts("Bohrmaschine", 500,TestProject);
        controller.AddMatCosts("Kran", 1500,TestProject);

        Assert.assertTrue(TestProject.getProjectCosts().getCalculatedMaterialCosts() == 2000);

    }
}
