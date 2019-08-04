package com.ttt.erp.controller;

import com.ttt.erp.model.Award;
import com.ttt.erp.repository.AwardRepository;
import com.ttt.erp.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/awards")
public class AwardController {

    @Autowired
    AwardRepository repository;

    @Autowired
    AwardService service;

    @GetMapping("/{id}")
    public Award getAward(@PathVariable("id") final Long id) {
        return repository.findById(id);
    }

    // TODO: get actual userId from cookie
    @PostMapping
    public Optional<Award> addAward (
        @CookieValue(value = "userId", defaultValue = "1") String modifiedById,
        @RequestBody Award newAward) {
        return this.service.createAward(Long.parseLong(modifiedById), newAward);
    }

    // TODO: get actual userId from cookie
    @PutMapping("/{id}")
    public Optional<Award> updateAwardById (
        @CookieValue(value = "userId", defaultValue = "1") String modifiedById,
        @PathVariable("id") Long awardId,
        @RequestBody Award modified) {
        return this.service.updateAward(Long.parseLong(modifiedById), awardId, modified);
    }

    // TODO: get actual userId from cookie
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteAwardById (
        @CookieValue(value = "userId", defaultValue = "1") String modifiedById,
        @PathVariable("id") Long awardId
    ) {
        return this.service.deleteAward(Long.parseLong(modifiedById), awardId);
    }

    @GetMapping
    public List<Award> getAll(
        @CookieValue(value = "user") String requestedByUser,
        @CookieValue(value = "admin") String isAdmin
    ) {
        return this.service.getAllAwards(requestedByUser, Boolean.parseBoolean(isAdmin));
//        return repository.findAll();
    }
}
