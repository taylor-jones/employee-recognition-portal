package com.ttt.erp.controller;

import com.ttt.erp.model.Award;
import com.ttt.erp.model.AwardType;
import com.ttt.erp.repository.AwardRepository;
import com.ttt.erp.repository.AwardTypeRepository;
import com.ttt.erp.service.AwardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/awardTypes")
public class AwardTypeController {

    @Autowired
    AwardTypeRepository repo;

    @Autowired
    AwardRepository awardRepository;

    @Autowired
    AwardTypeService service;

    @GetMapping("")
    public Optional< List<AwardType> > getAll() {
        return Optional.ofNullable(this.repo.findAll());
    }

    @GetMapping("/{id}")
    public Optional<AwardType> getAwardType(@PathVariable("id") final Long id) {
        return Optional.ofNullable(this.repo.findById(id));
    }


    /**
     * Get all the awards for a particular award type
     * @param id - the awardType.id
     * @return JSON array of award object, empty array if none found
     */
    @GetMapping("/{id}/awards")
    public List<Award> getAwards(@PathVariable("id") final Long id) {
        AwardType awardType = this.repo.findById(id);
        return this.awardRepository.findByAwardType(awardType);
    }


    //TODO: get the userId from cookie instead of path
    @PostMapping("/{userId}")
    public Optional<AwardType> addAwardType (
        @PathVariable("userId") Long userAccountId, 
        @RequestBody AwardType newAwardType
    ) {
        return this.service.createAwardType(userAccountId, newAwardType);
    }

    //TODO: get the userId from cookie instead of path
    @PutMapping("/{userId}/{id}")
    public Optional<AwardType> updateAwardTypeById (
        @PathVariable("userId") Long userAccountId, 
        @PathVariable("id") Long subjectId, 
        @RequestBody AwardType modified
    ) {
        return this.service.updateAwardType(userAccountId, subjectId, modified);
    }

    @DeleteMapping("/{userId}/{id}")
    public ResponseEntity<String> deleteAwardTypeById (
        @PathVariable("userId") Long userAccountId, 
        @PathVariable("id") Long subjectId 
    ) {
        return this.service.deleteAwardType(userAccountId, subjectId);
    }
}
