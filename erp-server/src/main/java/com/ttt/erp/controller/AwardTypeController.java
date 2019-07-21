package com.ttt.erp.controller;

import com.ttt.erp.model.AwardType;
import com.ttt.erp.repository.AwardTypeRepository;
import com.ttt.erp.service.AwardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/awardTypes")
public class AwardTypeController {

    @Autowired
    AwardTypeRepository repo;

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
