package com.ttt.erp.service;

import com.ttt.erp.model.Employee;
import com.ttt.erp.model.EmployeeRegion;
import com.ttt.erp.model.Region;
import com.ttt.erp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService extends LogService {

    @Autowired
    private EmployeeRepository repository;

    // create new employee
    public Optional<Employee> createEmployee(Long userAccountId, Employee employee) {
        try {
            Employee newEmployee = repository.save(employee);
            logInsert(userAccountId, newEmployee.getClass().getSimpleName(), newEmployee.getId());
            return Optional.ofNullable(newEmployee);
        } catch (Exception e) {
            System.out.println("Error on Employee insert");
            e.printStackTrace();
            return Optional.empty();
        }
    }


    // update existing employee
    public Optional<Employee> updateEmployee(Long userAccountId, Long employeeId, Employee modified) {
        try {
            Employee existing = this.repository.findById(employeeId);
            modified.setId(employeeId);
            logUpdate(userAccountId, existing.getClass().getSimpleName(), existing.getId(), existing, modified);
            return Optional.ofNullable(this.repository.save(modified));
        } catch (Exception e) {
            System.out.println("Error on Employee update");
            e.printStackTrace();
            return Optional.empty();
        }
    }


    // delete employee
    public ResponseEntity<String> deleteEmployee(Long userAccountId, Long employeeId) {
        try {
            Employee toDelete = this.repository.findById(employeeId);
            logDelete(userAccountId, toDelete.getClass().getSimpleName(), employeeId);
            this.repository.delete(toDelete);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error on Employee update");
            e.printStackTrace();
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
    }


    // get regions
    public Set<Region> findRegions(Employee employee) {
        Set<Region> regions = new HashSet<>();
        Set<EmployeeRegion> employeeRegions = employee.getRegions();

        employeeRegions.forEach(er -> {
            regions.add(er.getRegion());
        });

        return regions;
    };


    /**
     * Response Format:
     * - [0] employee id
     * - [1] employee first name
     * - [2] employee last name
     * - [3] count of awards
     * @return the number of awards each employee has received
     */
    public List<Object[]> getEmployeeAwardCounts() {
        return this.repository.getEmployeeAwardCounts();
    }


    /**
     * Response Format:
     * - [0] employee id
     * - [1] employee first name
     * - [2] employee last name
     * - [3] count of award types
     * @return the number of awards each employee has received
     */
    public List<Object[]> getEmployeeAwardTypeDiversity() {
        return this.repository.getEmployeeAwardTypeDiversity();
    }


    /**
     * Response Format:
     * - [0] employee id
     * - [1] employee first name
     * - [2] employee last name
     * - [3] count of award types
     * @return Each employee having received the specified award type
     *  and the number of times they've received the award type
     */
    public List<Object[]> getEmployeesWithAwardType(Long awardTypeId) {
        return this.repository.getEmployeesWithAwardType(awardTypeId);
    }
}
