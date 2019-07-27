package com.ttt.erp.repository;

import com.ttt.erp.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    UserAccount findById(Long id);
    UserAccount findByUsername(String username);
}
