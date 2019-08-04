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


    // get all awards for either admin or a specified user
    public List<Award> getAllAwards(String requestedByUser, Boolean isAdmin) {
        if (isAdmin) {
            return this.repository.findAll();
        } else {
            UserAccount userAccount = userAccountRepository.findByUsername(requestedByUser);
            System.out.println("\n\n\n\n\n\n" + userAccount + "\n\n\n\n\n");
            return this.repository.findByUserAccount(userAccount);
        }
    }


    // create new award
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


    // update existing award
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


    // delete award
    public ResponseEntity<Long> deleteAward(Long userAccountId, Long awardId) {
        try {
            Award toDelete = this.repository.findById(awardId);
            logDelete(userAccountId, toDelete.getClass().getSimpleName(), awardId);
            this.repository.delete(toDelete);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
//            System.err.println("Error on Award delete");
//            e.printStackTrace();
            return ResponseEntity.notFound().build();
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
