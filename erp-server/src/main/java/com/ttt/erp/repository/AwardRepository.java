package com.ttt.erp.repository;

import com.ttt.erp.model.Award;
import com.ttt.erp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardRepository extends JpaRepository<Award, Integer> {
    Award findById(Long id);

    /**
     * Gets a list of all the awards for a particular employee.
     * @param employee - the Employee to find all awards for
     * @return List of employee's awards , empty array if none are found.
     */
    List<Award> findByEmployee(Employee employee);

//    List<Award> findByRegion(Region region);
}
