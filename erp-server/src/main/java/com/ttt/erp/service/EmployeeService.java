package com.ttt.erp.service;

import com.ttt.erp.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private List<Employee> employees = new ArrayList<>();

    public Employee addEmployee(Employee employee) {
        this.employees.add(employee);
        return employee;
    }

    public List<Employee> getAllEmployees() {
        return this.employees;
    }

    public Employee getEmployeeById(Long id) {
        return this.employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
