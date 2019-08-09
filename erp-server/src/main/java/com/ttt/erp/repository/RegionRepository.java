package com.ttt.erp.repository;

import com.ttt.erp.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    Region findById(Long id);


    // List the number of awards given to each region
    @Query(value = "SELECT \n" +
        "  r.id, \n" +
        "  r.name AS name, \n" +
        "  COUNT(region_awards.award_id) AS total\n" +
        "FROM (\n" +
        "  SELECT \n" +
        "    er.employee_id, \n" +
        "    er.region_id, \n" +
        "    a.id AS award_id \n" +
        "  FROM employee_region AS er\n" +
        "  INNER JOIN award AS a\n" +
        "  ON er.employee_id = a.employee_id\n" +
        ") AS region_awards\n" +
        "RIGHT JOIN region AS r\n" +
        "ON region_awards.region_id = r.id\n" +
        "GROUP BY r.id, name\n" +
        "ORDER BY total DESC;", nativeQuery = true)
    List<Object[]> getAwardCountsForRegions();

}
