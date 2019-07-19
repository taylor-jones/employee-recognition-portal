package com.ttt.erp.service;

import org.springframework.stereotype.Service;
import com.ttt.erp.model.AwardType;
import com.ttt.erp.repository.AwardTypeRepository;
import com.ttt.erp.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Service
public class AwardTypeService extends LogService {
  
  @Autowired
  AwardTypeRepository awardTypeRepository;

  public Optional<AwardType> createAwardType(Long userAccountId, AwardType awardType) {
    try {
      AwardType newAwardType = awardTypeRepository.save( awardType );
      logInsert(userAccountId, newAwardType.getClass().getSimpleName(), newAwardType.getId());
      return Optional.ofNullable(newAwardType);
    } catch(Exception e) {
      System.err.println("Error on AwardType insert");
      e.printStackTrace();
      return Optional.empty();
    }
  }

  public Optional<AwardType> updateAwardType(Long userAccountId, Long subjectId, AwardType modified) {
    try {
      AwardType start = this.awardTypeRepository.findById(subjectId);
      modified.setId(subjectId);
      logUpdate(userAccountId, start.getClass().getSimpleName(), start.getId(), start, modified);
      return Optional.ofNullable( this.awardTypeRepository.save(modified) );
    } catch (Exception e) {
      System.err.println("Error on AwardType update");
      e.printStackTrace();
      return Optional.empty();
    }
  }

  public ResponseEntity<String> deleteAwardType(Long userAccountId, Long subjectId) {
    try {
      AwardType toDelete = this.awardTypeRepository.findById(subjectId);
      logDelete(userAccountId, toDelete.getClass().getSimpleName(), subjectId);
      this.awardTypeRepository.delete(toDelete);
      return new ResponseEntity<>("Success", HttpStatus.OK);
    } catch (Exception e) {
      System.err.println("Error on AwardType update");
      e.printStackTrace();
      return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }
  }

}