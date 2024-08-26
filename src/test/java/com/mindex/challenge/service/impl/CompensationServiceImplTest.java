package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationIdUrl;

    @Autowired
    CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testReadUpdate() {
        Employee goodEmployeeId = new Employee();
        goodEmployeeId.setEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb3");
        Employee badEmployeeId = new Employee();
        badEmployeeId.setEmployeeId("Bad-employee-id");
        Compensation compensationGood = new Compensation();
        compensationGood.setSalary(BigDecimal.valueOf(1234.51));
        compensationGood.setEffectiveDate("2024-08-01");
        Compensation compensationBadDate = new Compensation();
        compensationBadDate.setSalary(BigDecimal.valueOf(2222));
        compensationBadDate.setEffectiveDate("abcd-gg-zz");

        Compensation blankCompensation = new Compensation();
    // Update checks
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Compensation updatedCompensation;

        // Bad compensation date
        updatedCompensation = restTemplate.exchange(compensationIdUrl,
                    HttpMethod.PUT,
                    new HttpEntity<Compensation>(compensationBadDate, headers),
                    Compensation.class,
                    goodEmployeeId.getEmployeeId()).getBody();

        assertCompensationEquivalence(blankCompensation, updatedCompensation);

        // Bad employeeId
        updatedCompensation = restTemplate.exchange(compensationIdUrl,
                HttpMethod.PUT,
                new HttpEntity<Compensation>(compensationGood, headers),
                Compensation.class,
                badEmployeeId.getEmployeeId()).getBody();

        assertCompensationEquivalence(blankCompensation, updatedCompensation);

        // Good update
        updatedCompensation = restTemplate.exchange(compensationIdUrl,
                HttpMethod.PUT,
                new HttpEntity<Compensation>(compensationGood, headers),
                Compensation.class,
                goodEmployeeId.getEmployeeId()).getBody();

        assertCompensationEquivalence(compensationGood, updatedCompensation);

    // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, goodEmployeeId.getEmployeeId()).getBody();
        assertCompensationEquivalence(updatedCompensation, readCompensation);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
