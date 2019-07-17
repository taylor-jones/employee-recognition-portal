package com.ttt.erp.repository;

import com.ttt.erp.model.AwardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardTypeRepository extends JpaRepository<AwardType, Integer> {
    List<AwardType> findById(Long id);
}
