package Project;

import Employee.Employee;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.Period;
import java.time.LocalDate;

public class Cost {

    //Constructors
    public Cost(LocalDate _startDate, LocalDate _endDate, List<Employee> _employees) {
        projectStart = _startDate;
        projectEnd = _endDate;
        employees = _employees;
    }

    //Variables
    private LocalDate projectStart;
    private LocalDate projectEnd;
    private List<Employee> employees;
    private Map<Material, Integer> materialCosts = new HashMap<>();

    public void setEndDate(LocalDate _newEnd) {
        this.projectEnd = _newEnd;
    }

    //Methods
    //returns the whole staff costs based on the employees monthly salary for the duration of the project
    public double getCalculatedStaffCosts() {
        double sumDailySalary = 0;
        double sum = 0;
        long projectDuration = ChronoUnit.DAYS.between(projectStart, projectEnd);
        for (Employee e : employees) {
            sumDailySalary += e.getSalary() / 30;

        }

        sum = (projectDuration * sumDailySalary);
        sum = Math.round(sum * 10) / 10;
        return sum;
    }

    /**
     * returns the whole costs of all materials and other stuff which is needed for the project
     **/
    public double getCalculatedMaterialCosts() {
        double value = 0;
        for (Map.Entry<Material, Integer> e : materialCosts.entrySet()) {
            value += e.getKey().getMaterialPrice() * e.getValue();
            System.out.println(e.getKey().getMaterialName());
        }
        return value;
    }


    public HashMap<Material, Integer> getMaterialCosts() {
        return (HashMap) materialCosts;
    }

    public void setMaterialCosts(Map<Material, Integer> _material) {
        this.materialCosts = _material;
    }
}

