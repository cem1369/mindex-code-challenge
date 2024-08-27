# Developer Notes
My solution was based on not introducing relational tables in the NoSQL database.  I was probably reading into this choice of Mongo too much (versus using H2), it was probably just for ease of use.

A different, and perhaps easier approach, would be to just create a new document - Compensation.  Linked to the Employee with the employeeId as a field.
## Changes to existing files, independent from challenge tasks
Employee update creates duplicate records.
### src/main/java/com/mindex/challenge/data/Employee.java
Id annotation added to the employeeId field.
### src/test/java/com/mindex/challenge/service/impl/EmployeeServiceImplTest.java
Post update read, testing for duplicates

## Task 1 (ReportingStructure) Notes:
Straight forward in that the data that is needed is already being provided by the Employee endpoint. 
ReportingStructure data object, controller, service interface, and service impl were created.  Reused the EmployeeRepository existing findByEmployeeId method.

New reporting-structure Endpoint:
```
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/reporting-structure/{id}
    * RESPONSE: ReportingStructure
```

The ReportingStructure has a JSON schema of:
```json
{
  "title": "ReportingStructure",
  "type": "object",
  "properties": {
    "employee": {
      "type": "object",
      "properties": {
        "employeeId": {
          "type": "string"
        },
        "firstName": {
          "type": "string"
        },
        "lastName": {
          "type": "string"
        },
        "position": {
          "type": "string"
        },
        "department": {
          "type": "string"
        },
        "directReports": {
          "type": "array",
          "items": {
            "anyOf": [
              {
                "type": "string"
              },
              {
                "type": "object"
              }
            ]
          }
        }
      }
    },
    "numberOfReports": {
      "type": "integer"
    }
  }
}
```
ReportingStructureServiceImplTest was created for the testing suite.

## Task 2 (Compensation) Notes:
Compensation data (salary and effectiveDate) are new fields to be attached to the existing Employee data object.  The Employee object now contains a Compensation object.
Compensation controller, service interface, and service impl were created. Reused the EmployeeRepository existing findByEmployeeId method.

New compensation endpoint:
```
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/compensation/{id}
    * RESPONSE: Compensation
* UPDATE
    * HTTP Method: PUT 
    * URL: localhost:8080/compensation/{id}
    * PAYLOAD: Compensation
    * RESPONSE: Compensation
```
I went with an Update vs. a Create (as specified in the instructions), because Compensation is attached to an Employee, the use case will probably be updating a current Employee with the Compensation. 

The Compensation has a JSON schema of:
```json
{
  "title": "Compensation",
  "type": "object",
  "properties": {
    "salary": {
      "type": "number"
    },
    "effectiveDate": {
      "type": "string"
    }
  }
} 
```
CompensationServiceImplTest was created for the testing suite.

I decided to leave out the Compensation data on the json returned from the GET employee/{id} endpoint.  I made up a scenario that compensation data was broken out to its own endpoint to obscure that type of data.
To that end, I created a new data object EmployeeInfo, that contained all the Employee data, minus Compensation.  The employee/{id} endpoint was changed to return an EmployeeInfo json (identical to the original Employee json).  A new findInfoByEmployeeId method was created in the EmployeeRepository to return an EmployeeInfo object.

Originally, I put the @JsonIgnore annotation on the Employee.Compensation field.  It worked for the employee/{id} endpoint, but then the compensation endpoint was returning nulls.  It looks like the JsonIgnore has a global effect when I really wanted it isolated to the Employee json.
My next approach was to create a projection EmployeeInfo interface, but then the json fields were being reordered.
