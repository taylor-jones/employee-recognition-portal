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


    /**
     * Gets the Award with the specified Id
     * @param id - the id of the award
     * @return - the Award JSON
     */
    @GetMapping("/{id}")
    public Award getAward(@PathVariable("id") final Long id) {
        return repository.findById(id);
    }
    
    /**
     * Creates a new Award record
     * @param modifiedById - the userId cookie value
     * @param newAward - the Award data
     * @return the Award JSON if successful
     */
    @PostMapping
    public Optional<Award> addAward (
        @CookieValue(value = "userId") String modifiedById,
        @RequestBody Award newAward) {
        return this.service.createAward(Long.parseLong(modifiedById), newAward);
    }

    /**
     * Updates an award record
     * @param modifiedById - the cookie userId value
     * @param awardId - the id of the award to update
     * @param modified - the modified award data
     * @return - the updated Award JSON, if successful
     */
    @PutMapping("/{id}")
    public Optional<Award> updateAwardById (
        @CookieValue(value = "userId") String modifiedById,
        @PathVariable("id") Long awardId,
        @RequestBody Award modified) {
        return this.service.updateAward(Long.parseLong(modifiedById), awardId, modified);
    }

    /**
     * Deletes an Award record
     * @param modifiedById - the cookie userId value
     * @param awardId - the Award to delete
     * @return 200 if successful, other HTTP error code if not
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteAwardById (
        @CookieValue(value = "userId") String modifiedById,
        @PathVariable("id") Long awardId
    ) {
        return this.service.deleteAward(Long.parseLong(modifiedById), awardId);
    }

    /**
     * Gets all the awards created by a particular user, or all awards if user is admin
     * @param requestedByUser - the user cookie value (username)
     * @param isAdmin - boolean - is the user an admin user?
     * @return JSON array of awards available to the requesting user.
     */
    @GetMapping
    public List<Award> getAll(
        @CookieValue(value = "user") String requestedByUser,
        @CookieValue(value = "admin") String isAdmin
    ) {
        return this.service.getAllAwards(requestedByUser, Boolean.parseBoolean(isAdmin));
    }
}
