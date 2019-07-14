package com.ttt.erp.repository;

import com.ttt.erp.model.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardRepository extends JpaRepository<Award, Integer> {
    List<Award> findById(Long id);
}
