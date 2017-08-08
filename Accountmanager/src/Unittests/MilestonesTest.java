package Unittests;

import Employee.InternalEmp;
import Project.Milestones;
import Project.Project;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import java.time.LocalDate;


/**
 * Created by Ben on 04.03.2017.
 */

public class MilestonesTest
{
    private Project testProject;
    private Milestones testMilestone1;
    private Milestones testMilestone2;
    private InternalEmp testEmployee;

    @Before

    public void setUp()
    {
        testProject = new Project("asd");
        testMilestone1 = new Milestones();
        testMilestone2 = new Milestones();
        testEmployee = new InternalEmp();

        testMilestone1.setDateOfMilestone(LocalDate.of(2017,3,1));
        testMilestone1.setDateOfMilestone(LocalDate.of(2017,3,5));
        testMilestone1.setMilestoneID(01);
        testMilestone2.setMilestoneID(02);
    }

    @Test

    public void setMilestone_setNameandDate()                                                                           /** Test of the setmethods and the actualDatemethod **/
    {
        Milestones testMilestone = new Milestones();

        Assert.assertTrue(testMilestone1.setMilestoneName("Abgabetermin"));
        Assert.assertTrue(testMilestone1.setDateOfMilestone(LocalDate.now()));
    }

    @Test

    public void addMilestone_check()                                                                                    /** test methods of Milestones and the permission **/
    {
        testEmployee.setAccessLevel(5);

        Assert.assertTrue(testEmployee.addNewMilestone(testProject,testMilestone1));
        Assert.assertTrue(testEmployee.addNewMilestone(testProject,testMilestone2));
        Assert.assertEquals(testProject.getMilestones().size(),2);
        Assert.assertTrue(testEmployee.deleteMilestone(testProject,01));
        Assert.assertEquals(testProject.getMilestones().size(),1);
        Assert.assertTrue(testEmployee.deleteMilestone(testProject, 02));
        Assert.assertEquals(testProject.getMilestones().size(),0);

        Assert.assertTrue(testProject.getMilestones().isEmpty());

        testEmployee.setAccessLevel(0);

        Assert.assertFalse(testEmployee.addNewMilestone(testProject,testMilestone1));
        Assert.assertEquals(testProject.getMilestones().size(),0);
        Assert.assertFalse(testEmployee.deleteMilestone(testProject,02));
    }

    @Test

    public void checkAllMilestones_atStartup()
    {

    }

    @Test

    public void deleteMilestones()
    {

        Assert.assertTrue(testEmployee.deleteMilestone(testProject,01));

    }

}