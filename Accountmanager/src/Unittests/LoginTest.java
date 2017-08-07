package Unittests;

import EmpDatabase.EmpDatabase;
import EmpDatabase.Login;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Ben on 07.02.2017.
 */
public class LoginTest {

    Login TestLogin;
    EmpDatabase TestDatabase;
    Employee.InternalEmp tmpMax;
    Employee.InternalEmp tmpMaxi;
    Employee.InternalEmp tmpKalle;


    @Before
    public void setUp() {
        TestLogin = new Login();
        TestDatabase = new EmpDatabase();
        tmpMax = new Employee.InternalEmp("Max", "Mustermann", "Max@gmx.de");
        tmpMaxi = new Employee.InternalEmp("Maxi", "Mustermann", "Maxi@gmx.de");
        tmpKalle = new Employee.InternalEmp("Kalle", "Nethe", "Kalle@gmx.de");
    }

    @Test
    public void personalLogin_checkNameandPassword() {
        TestDatabase.getAllEmpl().add(tmpKalle);
        TestDatabase.getAllEmpl().add(tmpMax);
        TestDatabase.getAllEmpl().add(tmpMaxi);

        Assert.assertTrue(TestLogin.loginPersonal(TestDatabase, "MustermannMaxi", "1234"));
    }

    @Test
    public void test_checkPassword_isThePasswordCorrect() {
        TestDatabase.getAllEmpl().add(tmpKalle);
        TestDatabase.getAllEmpl().add(tmpMax);
        TestDatabase.getAllEmpl().add(tmpMaxi);

        Assert.assertTrue(TestLogin.checkPassword(TestDatabase, "MustermannMaxi", "1234"));
        Assert.assertFalse(TestLogin.checkPassword(TestDatabase, "MustermannMaxi", "4321"));
    }

}