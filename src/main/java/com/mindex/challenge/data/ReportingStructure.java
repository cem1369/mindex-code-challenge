package com.mindex.challenge.data;

import com.mindex.challenge.data.Employee;

public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;

    public ReportingStructure()  {
    }

    public Employee getEmployee()  {
        return employee;
    }

    // Reset the Compensation to null, to protect the information.
    // Tried to JsonIgnore on the Employee.Compensation, but then wasn't displaying Compensation for the compensation endpoint
    // i would probably have to go into the json parsers to figure that one out

    // Could have gone the route of EmployeeInfo employee vs Employee.
    // Could have put restrictions on the queries themselves (@Query) to restrict the data fields returned.
    public void setEmployee(Employee employee)  {
        employee.setCompensation(null);
        this.employee = employee;
    }

    public int getNumberOfReports()  {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports)  {
        this.numberOfReports = numberOfReports;
    }
}
