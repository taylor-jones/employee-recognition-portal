package com.ttt.erp.controller;

import com.ttt.erp.model.Employee;
import com.ttt.erp.repository.EmployeeRepository;
import com.ttt.erp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    EmployeeService service;

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") final Long id) {
        return repository.findById(id);
    }

    // TODO: get actual userId from cookie
    @PostMapping
    public Optional<Employee> addEmployee (
        @CookieValue(value = "userId", defaultValue = "1") String modifiedById,
        @RequestBody Employee newEmployee) {
        return this.service.createEmployee(Long.parseLong(modifiedById), newEmployee);
    }

    // TODO: get actual userId from cookie
    @PutMapping("/{id}")
    public Optional<Employee> updateEmployeeById (
        @CookieValue(value = "userId", defaultValue = "1") String modifiedById,
        @PathVariable("id") Long employeeId,
        @RequestBody Employee modified) {
        return this.service.updateEmployee(Long.parseLong(modifiedById), employeeId, modified);
    }

    // TODO: get actual userId from cookie
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById (
        @CookieValue(value = "userId", defaultValue = "1") String modifiedById,
        @PathVariable("id") Long employeeId
    ) {
        return this.service.deleteEmployee(Long.parseLong(modifiedById), employeeId);
    }

    @GetMapping
    public List<Employee> getAll() {
        return repository.findAll();
    }
}
