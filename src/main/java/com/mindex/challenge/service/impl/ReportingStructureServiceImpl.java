package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.EmployeeInfo;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import com.mindex.challenge.service.impl.EmployeeServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Creating employee with id [{}]", id);
        ReportingStructure reportingStructure = new ReportingStructure();
        
        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        reportingStructure.setEmployee(employee);

        int numberOfReports = countAllDirectReports(employee);
        reportingStructure.setNumberOfReports(numberOfReports);

        return reportingStructure;
    }

    private int countAllDirectReports(Employee employee)  {
        int thisEmployeeDirectReports = 0;
        List<Employee> directReportEmployees = employee.getDirectReports();
        if(directReportEmployees != null)  {
            thisEmployeeDirectReports = directReportEmployees.size();
            for(Employee e : directReportEmployees)  {
                String id = e.getEmployeeId();
                Employee directReport = employeeRepository.findByEmployeeId(id);
                if (employee == null) {
                    throw new RuntimeException("Invalid employeeId: " + id);
                }
                thisEmployeeDirectReports += countAllDirectReports(directReport);
            }
        }
        return thisEmployeeDirectReports;

    }
}
