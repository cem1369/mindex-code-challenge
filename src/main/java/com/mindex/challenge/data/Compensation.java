package com.mindex.challenge.data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Compensation {
    private BigDecimal salary;
    private String effectiveDate;

    public Compensation()  {
    }
    
    public BigDecimal getSalary()  {
        return salary;
    }

    public void setSalary(BigDecimal salary)  {
        this.salary = salary.setScale(2);
    }

    public String getEffectiveDate()  {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate)  {
        this.effectiveDate = effectiveDate;
    }
}
