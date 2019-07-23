package com.ttt.erp.repository;

import com.ttt.erp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findById(Long id);
}
