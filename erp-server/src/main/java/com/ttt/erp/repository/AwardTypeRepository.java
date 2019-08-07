package com.ttt.erp.repository;

import com.ttt.erp.model.AwardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardTypeRepository extends JpaRepository<AwardType, Integer> {
    AwardType findById(Long id);
    AwardType deleteById(Long id);


    // List the number of times each award type was given
    @Query(value = "SELECT t.id, t.name AS name , COUNT(a.award_type_id) AS total\n" +
        "FROM award_type AS t\n" +
        "INNER JOIN award AS a\n" +
        "ON t.id = a.award_type_id\n" +
        "GROUP BY t.id, name\n" +
        "ORDER BY total DESC", nativeQuery = true)
    List<Object[]> getAwardTypeCounts();
}
