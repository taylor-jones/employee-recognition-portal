package com.ttt.erp.controller;

import com.ttt.erp.model.Employee;
import com.ttt.erp.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = requireNonNull(employeeService);
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
         Employee emp = this.employeeService.addEmployee(employee);
         if (emp != null) {
             return new ResponseEntity<>(emp, HttpStatus.CREATED);
         }
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> empList = this.employeeService.getAllEmployees();
        if (empList != null) {
            return new ResponseEntity<>(empList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long id) {
        Employee emp = this.employeeService.getEmployeeById(id);
        if (emp != null) {
            return new ResponseEntity<>(emp, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
