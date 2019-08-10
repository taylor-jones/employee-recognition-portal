package com.ttt.erp.repository;

import com.ttt.erp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findById(Long id);

    // List the number of times each award type was given
    @Query(value = "SELECT e.id, e.first_name, e.last_name, COUNT(a.id) AS total\n" +
        "FROM employee AS e\n" +
        "LEFT JOIN award AS a\n" +
        "ON e.id = a.employee_id\n" +
        "GROUP BY e.id, e.first_name, e.last_name\n" +
        "ORDER BY total DESC;", nativeQuery = true)
    List<Object[]> getEmployeeAwardCounts();


    // List the number of different types of awards each employee received
    @Query(value = "SELECT e.id, e.first_name, e.last_name, COUNT(DISTINCT a.award_type_id) AS total\n" +
        "FROM employee AS e\n" +
        "LEFT JOIN award AS a\n" +
        "ON e.id = a.employee_id\n" +
        "GROUP BY e.id, e.first_name, e.last_name\n" +
        "ORDER BY total DESC;", nativeQuery = true)
    List<Object[]> getEmployeeAwardTypeDiversity();


    // List all the employees having received a specified type of award
    @Query(value = "SELECT e.id, e.first_name, e.last_name, COUNT(a.id) AS total\n" +
        "FROM employee AS e\n" +
        "LEFT JOIN award AS a\n" +
        "ON e.id = a.employee_id\n" +
        "WHERE a.award_type_id = :awardTypeId\n" +
        "GROUP BY e.id, e.first_name, e.last_name\n" +
        "ORDER BY total DESC;", nativeQuery = true)
    List<Object[]> getEmployeesWithAwardType(Long awardTypeId);
}
