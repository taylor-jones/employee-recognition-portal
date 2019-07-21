package com.ttt.erp.repository;

import com.ttt.erp.model.AwardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardTypeRepository extends JpaRepository<AwardType, Integer> {
    AwardType findById(Long id);
    AwardType deleteById(Long id);
}
