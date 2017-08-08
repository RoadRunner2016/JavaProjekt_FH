package EmpDatabase;

import Employee.*;

import java.util.Iterator;

/**
 * Created by Ben on 05.01.2017.
 */
public class Login {

    public boolean checkPassword(EmpDatabase _tmpDB, String _eName, String _ePassword)                                  /** check the password for a internalemployee,  tmpStatus is a indicator for the online/offline status of the employee**/
    {
        Iterator<Employee> eItrtmp = _tmpDB.getAllEmpl().iterator();
        InternalEmp tmpIntEmpl;

        while (eItrtmp.hasNext()) {
            tmpIntEmpl = (InternalEmp) eItrtmp.next();
            if (tmpIntEmpl.getPassword().equals(_ePassword) && (tmpIntEmpl.getLoginName().equals(_eName))) {
                tmpIntEmpl.setOnStatus(true);
                return true;
            }
        }

        System.out.println(" Anmeldung fehlgeschlagen. ");
        return false;
    }


    public boolean loginPersonal(EmpDatabase _tmpDatabase, String _tmpName, String _tmpPassword)                        /** loginmethod to check the name and password **/
    {
        boolean _checkTMP;
        int _trails = 3;

        InternalEmp tmpInternalEmpTmp = new InternalEmp();

        while (_trails != 0)
        {
            System.out.println(" Anmeldebildschirm ");

            _checkTMP = checkPassword(_tmpDatabase, tmpInternalEmpTmp.searchEmployee(_tmpDatabase, _tmpName), _tmpPassword);

            if (_checkTMP == true)
            {

                System.out.println("Anmeldung war erfolgreich");
                return true;
            }
            _trails--;
        }

        System.out.println(" Der Anmeldebildschirm ist gesperrt. ");
        return false;
    }

}

