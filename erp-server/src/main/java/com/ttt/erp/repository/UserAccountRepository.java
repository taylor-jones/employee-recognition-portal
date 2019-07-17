package com.ttt.erp.repository;

import com.ttt.erp.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
//    List<UserAccount> findById(Long id);
//    List<UserAccount> findByUsername(String username);
}
