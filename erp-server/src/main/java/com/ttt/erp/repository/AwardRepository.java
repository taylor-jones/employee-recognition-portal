package com.ttt.erp.repository;

import com.ttt.erp.model.Award;
import com.ttt.erp.model.AwardType;
import com.ttt.erp.model.Employee;
import com.ttt.erp.model.UserAccount;
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


    /**
     * Gets a list of all the awards for a particular award type.
     * @param awardType - the Award Type to find all awards for
     * @return List of award type's awards , empty array if none are found.
     */
    List<Award> findByAwardType(AwardType awardType);


    /**
     * Gets a list of all the awards for a particular user.
     * @param userAccount - the User Account to find all awards for
     * @return List of user account's awards , empty array if none are found.
     */
    List<Award> findByUserAccount(UserAccount userAccount);
}
