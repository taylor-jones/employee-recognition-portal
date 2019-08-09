package com.ttt.erp.repository;

import com.ttt.erp.model.RecoveryQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecoveryQuestionRepository extends JpaRepository<RecoveryQuestion, Integer> {
//    List<RecoveryQuestion> getUserAccountById();
    List<RecoveryQuestion> findAllByUserAccountId(Long id);

    List<RecoveryQuestion> findAllByUserAccountUsername(String username);
}
