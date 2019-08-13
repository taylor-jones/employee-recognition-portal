package com.ttt.erp.repository;

import com.ttt.erp.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    UserAccount findById(Long id);
    UserAccount findByUsername(String username);

    // Get the User with their time of creation
    @Query( value = "SELECT ua.*, l.modified_at\n" +
        "FROM user_account AS ua\n" + 
        "INNER JOIN log AS l ON l.subject_id = ua.id\n" +
        "WHERE ua.username = :username\n" +
        "AND l.operation  = 'insert';", nativeQuery = true)
    Object getUserWithCreatedTime(String username);

    // List all users along with the number of awards given by each user
    @Query(value = "SELECT u.id, u.username, COUNT(a.id) AS total\n" +
        "FROM award AS a\n" +
        "RIGHT JOIN user_account AS u\n" +
        "ON u.id = a.user_account_id\n" +
        "GROUP BY u.id, u.username\n" +
        "ORDER BY total DESC;", nativeQuery = true)
    List<Object[]> getUserAwardCounts();
}
