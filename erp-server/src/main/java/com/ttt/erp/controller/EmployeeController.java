package com.ttt.erp.controller;

import com.ttt.erp.model.Award;
import com.ttt.erp.model.Employee;
import com.ttt.erp.model.Region;
import com.ttt.erp.repository.AwardRepository;
import com.ttt.erp.repository.EmployeeRepository;
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

    @Autowired
    EmployeeService employeeService;


    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") final Long id) {
        return employeeRepository.findById(id);
    }


    /**
     * Get the counts of awards for each employee
     * @return JSON array of objects
     */
    @GetMapping("/awards/totals")
    public List<Object[]> getEmployeeAwardCounts() {
        return this.employeeService.getEmployeeAwardCounts();
    }


    /**
     * Get the counts of awards for each employee
     * @return JSON array of objects
     */
    @GetMapping("/awards/diversity")
    public List<Object[]> getEmployeeAwardTypeDiversity() {
        return this.employeeService.getEmployeeAwardTypeDiversity();
    }


    /**
     * Get the employees that have received a specified award type
     * @return JSON array of objects
     */
    @GetMapping("/byAwardType/{id}")
    public List<Object[]> getEmployeeAwardTypeDiversity(@PathVariable("id") final Long id) {
        return this.employeeService.getEmployeesWithAwardType(id);
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
        return this.employeeService.findRegions(employee);
    }


    @PostMapping
    public Optional<Employee> addEmployee (
        @CookieValue(value = "userId") String modifiedById,
        @RequestBody Employee newEmployee) {
        return this.employeeService.createEmployee(Long.parseLong(modifiedById), newEmployee);
    }


    @PutMapping("/{id}")
    public Optional<Employee> updateEmployeeById (
        @CookieValue(value = "userId") String modifiedById,
        @PathVariable("id") Long employeeId,
        @RequestBody Employee modified) {
        return this.employeeService.updateEmployee(Long.parseLong(modifiedById), employeeId, modified);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById (
        @CookieValue(value = "userId") String modifiedById,
        @PathVariable("id") Long employeeId
    ) {
        return this.employeeService.deleteEmployee(Long.parseLong(modifiedById), employeeId);
    }


    @GetMapping
    public List<Employee> getAll() {
        return this.employeeRepository.findAll();
    }
}
