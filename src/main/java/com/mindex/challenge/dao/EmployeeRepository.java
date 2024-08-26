package com.mindex.challenge.dao;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.EmployeeInfo;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    EmployeeInfo findInfoByEmployeeId(String employeeId);
    Employee findByEmployeeId(String employeeId);
}
