package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.EmployeeInfo;

public interface EmployeeService {
    Employee create(Employee employee);
    EmployeeInfo read(String id);
    Employee update(Employee employee);
}
