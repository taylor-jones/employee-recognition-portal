package com.ttt.erp.service;

import com.ttt.erp.model.Award;
import com.ttt.erp.repository.AwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AwardService extends LogService {

    @Autowired
    private AwardRepository repository;

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
//            existing.setUserAccount(modified.getUserAccount());
//            existing.setDescription(modified.getDescription());
//            existing.setEmployee(modified.getEmployee());
//            existing.setAwardType(modified.getAwardType());
//            existing.setAwardedDate(modified.getAwardedDate());
//            existing.setAwardedTime(modified.getAwardedTime());

            logUpdate(userAccountId, existing.getClass().getSimpleName(), existing.getId(), existing, modified);
            return Optional.ofNullable(this.repository.save(modified));
        } catch (Exception e) {
            System.out.println("Error on Award update");
            e.printStackTrace();
            return Optional.empty();
        }
    }


    // delete award
    public ResponseEntity<String> deleteAward(Long userAccountId, Long awardId) {
        try {
            Award toDelete = this.repository.findById(awardId);
            logDelete(userAccountId, toDelete.getClass().getSimpleName(), awardId);
            this.repository.delete(toDelete);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error on Award update");
            e.printStackTrace();
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
    }
}
