package com.ttt.erp.controller;

import com.ttt.erp.model.Award;
import com.ttt.erp.model.Employee;
import com.ttt.erp.model.Region;
import com.ttt.erp.repository.AwardRepository;
import com.ttt.erp.repository.EmployeeRepository;
import com.ttt.erp.repository.RegionRepository;
import com.ttt.erp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    AwardRepository awardRepository;
    RegionRepository regionRepository;

    @Autowired
    EmployeeService service;

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") final Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * Get all the awards for a particular employee
     * @param id - the employee.id
     * @return JSON array of award object, empty array if none found
     */
    @GetMapping("/{id}/awards")
    public List<Award> getAwards(@PathVariable("id") final Long id) {
        Employee employee = this.employeeRepository.findById(id);
        return this.awardRepository.findByEmployee(employee);
    }


    /**
     * Get all the region for a particular employee
     * @param id - the employee.id
     * @return JSON array of region objects, empty array if none found
     */
    @GetMapping("/{id}/regions")
    public Set<Region> getRegions(@PathVariable("id") final Long id) {
        Employee employee = this.employeeRepository.findById(id);
        return this.service.findRegions(employee);
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
        return this.employeeRepository.findAll();
    }
}
