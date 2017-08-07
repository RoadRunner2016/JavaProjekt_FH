package Unittests;

import EmpDatabase.EmpDatabase;
import Employee.ExternalEmp;
import Employee.InternalEmp;
import Project.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by Admin on 05.03.2017.
 */
public class IntegrityTest {
    InternalEmp Controller;


    EmpDatabase TestDatabase;
    Project TestProject1;
    Project TestProject2;
    ExternalEmp TestEmployee1;
    ExternalEmp TestEmployee2;
    Milestones TestMilestone1;
    Milestones TestMilestone2;


    @Before

    public void setUp() {
        Controller = new InternalEmp("Karl", "Heinz", "Karl.H@gmx.de");
        TestDatabase = new EmpDatabase();
        TestProject1 = new Project("asd", 2017, 1, 1, 2017, 1, 31);
        TestProject2 = new Project("asd", 2017, 1, 17, 2017, 3, 3);
        TestEmployee1 = new ExternalEmp("Worker", "John");
        TestEmployee2 = new ExternalEmp("Worker", "Doe");
        TestMilestone1 = new Milestones(1, "Programmieren 3", "Abgabe erfolgt über GitHub", LocalDate.of(2017, 03, 05));
        TestMilestone2 = new Milestones(2, "Vorlesungsfrei", "Party!", LocalDate.of(2017, 03, 03));
        Controller.setAccessLevel(5);
        TestEmployee1.setSalary(2000);
        TestEmployee2.setSalary(1500);
    }

    @Test

    public void IntegrityTest_complete() {


        // add projects to the database
        Assert.assertTrue(Controller.AddProjectToDatabase(TestDatabase, TestProject1));
        Assert.assertTrue(Controller.AddProjectToDatabase(TestDatabase, TestProject2));

        // check the size of the arraylist

        Assert.assertEquals(TestDatabase.getAllProj().size(), 2);

        // delete one project of the list

        Assert.assertTrue(Controller.DeleteProject(TestDatabase, TestProject2));

        // check the size

        Assert.assertEquals(TestDatabase.getAllProj().size(), 1);

        // add personal to the project

        Assert.assertTrue(Controller.AddPersonelToProject(TestEmployee1, TestProject1));
        Assert.assertTrue(Controller.AddPersonelToProject(TestEmployee2, TestProject1));

        // check the number of employees

        Assert.assertEquals(TestProject1.getEmployees().size(), 2);

        // remove ext. employee

        Assert.assertTrue(Controller.RemovePersonelFromProject(TestEmployee2, TestProject1));

        Assert.assertEquals(TestProject1.getEmployees().size(), 1);

        Assert.assertTrue(Controller.addNewMilestone(TestProject1, TestMilestone1));
        Assert.assertTrue(Controller.addNewMilestone(TestProject1, TestMilestone2));

        // milestone sizecheck

        Assert.assertEquals(TestProject1.getMilestones().size(), 2);

        // staffcost

        Assert.assertTrue(TestProject1.getProjectCosts().getCalculatedStaffCosts() > 2000);                     // Roundingerror >

        Assert.assertTrue(Controller.AddPersonelToProject(TestEmployee2, TestProject1));

        Assert.assertTrue(TestProject1.getProjectCosts().getCalculatedStaffCosts() == 3500);

        // materialCosts

        Controller.AddMatCosts("High-End PC", 2500, TestProject1);
        Controller.AddMatCosts("Bagger", 12500, TestProject1);

        Assert.assertTrue(TestProject1.getProjectCosts().getCalculatedMaterialCosts() == 15000);

        //
    }
}
