package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.impl.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Compensation read(String id) {
        LOG.debug("Creating compensation from id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        Compensation compensation = employee.getCompensation();
        if (compensation == null)  {
            compensation = new Compensation();
        }

        return compensation;
    }

    @Override
    public Compensation update(Employee employee) {
        LOG.debug("Updating employee with compensation data [{}]", employee);

        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(employee.getCompensation().getEffectiveDate());
        } catch (ParseException e) {
            throw new RuntimeException("Invalid effective date: "+employee.getCompensation().getEffectiveDate());
        }
        Compensation toUpdate = employee.getCompensation();
        Employee foundEmployee = employeeRepository.findByEmployeeId(employee.getEmployeeId());

        if (foundEmployee == null) {
            throw new RuntimeException("Invalid employeeId: " + employee.getEmployeeId());
        }

        foundEmployee.setCompensation(toUpdate);
        employee = employeeRepository.save(foundEmployee);
        return employee.getCompensation();
    }
}
