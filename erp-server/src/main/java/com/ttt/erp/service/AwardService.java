package com.ttt.erp.service;

import com.ttt.erp.model.Award;
import com.ttt.erp.model.UserAccount;
import com.ttt.erp.repository.AwardRepository;
import com.ttt.erp.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AwardService extends LogService {

    @Autowired
    private AwardRepository repository;

    @Autowired
    private UserAccountRepository userAccountRepository;


    /**
     * get all awards for either admin or a specified user
     * @param requestedByUser - unsername of user that make request
     * @param isAdmin - is the user an admin user?
     * @return - list of Award records
     */
    public List<Award> getAllAwards(String requestedByUser, Boolean isAdmin) {
        if (isAdmin) {
            return this.repository.findAll();
        } else {
            UserAccount userAccount = userAccountRepository.findByUsername(requestedByUser);
            return this.repository.findByUserAccount(userAccount);
        }
    }


    /**
     * Creates and returns a new Award record
     * @param userAccountId - the id of the user that created the award
     * @param award  - the award data
     * @return - the created Award
     */
    public Optional<Award> createAward(Long userAccountId, Award award) {
        try {
            Award newAward = repository.save(award);
            logInsert(userAccountId, newAward.getClass().getSimpleName(), newAward.getId());
            return Optional.ofNullable(newAward);
        } catch (Exception e) {
            System.out.println("Error on Award insert");
            e.printStackTrace();
            return Optional.empty();
        }
    }


    /**
     * Updates and returns an existing Award record
     * @param userAccountId - the id of the user that updated the award
     * @param modified  - the modified award data
     * @return - the updated Award
     */
    public Optional<Award> updateAward(Long userAccountId, Long awardId, Award modified) {
        try {
            Award existing = this.repository.findById(awardId);
            modified.setId(awardId);
            logUpdate(userAccountId, existing.getClass().getSimpleName(), existing.getId(), existing, modified);
            return Optional.ofNullable(this.repository.save(modified));
        } catch (Exception e) {
            System.out.println("Error on Award update");
            e.printStackTrace();
            return Optional.empty();
        }
    }


    /**
     * Deletes an Award record
     * @param userAccountId - the id of the user that deleted the award
     * @param awardId  - the id of the award to delete
     * @return - 200 if successful, 404 if not
     */
    public ResponseEntity<Long> deleteAward(Long userAccountId, Long awardId) {
        try {
            Award toDelete = this.repository.findById(awardId);
            logDelete(userAccountId, toDelete.getClass().getSimpleName(), awardId);
            this.repository.delete(toDelete);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
