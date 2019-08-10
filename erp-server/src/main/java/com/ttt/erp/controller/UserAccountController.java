package com.ttt.erp.controller;

import com.ttt.erp.model.Award;
import com.ttt.erp.model.UserAccount;
import com.ttt.erp.repository.AwardRepository;
import com.ttt.erp.repository.RecoveryQuestionRepository;
import com.ttt.erp.repository.UserAccountRepository;
import com.ttt.erp.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ttt.erp.service.UserManager;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    @Autowired
    UserAccountRepository repository;

    @Autowired
    AwardRepository awardRepository;

    @Autowired
    UserAccountService service;

    @Autowired
    UserManager userManager;

    @Autowired
    private RecoveryQuestionRepository recoveryQuestionRepository;

    @GetMapping("/{id}")
    public UserAccount getUserAccount(@PathVariable("id") final Long id) {
        return repository.findById(id);
    }

    /**
     * Get all the awards for a particular user account
     * @param id - the userAccount.id
     * @return JSON array of award object, empty array if none found
     */
    @GetMapping("/{id}/awards")
    public List<Award> getAwards(@PathVariable("id") final Long id) {
        UserAccount userAccount = this.repository.findById(id);
        return this.awardRepository.findByUserAccount(userAccount);
    }

    /**
     * Gets the number of awards given by each user
     * @return Array of Objects
     */
    @GetMapping("/awards/totals")
    public List<Object[]> getAwardTotals() {
        return this.service.getUserAwardCounts();
    }

    @PostMapping
    public Optional<UserAccount> addUserAccount (@RequestBody UserAccount newUserAccount, Principal principal) {
        UserAccount actingUser = this.repository.findByUsername(principal.getName());
        return this.service.createUser(actingUser.getId(), newUserAccount);
        //return Optional.empty();
    }

    // callback for checking login status
    @GetMapping(value = "/whoAmI")
    public UserAccount getUser() {
        return this.userManager.get();
    }


    @PutMapping("/{id}")
    public Optional<UserAccount> updateUserAccountById (
        @CookieValue(value = "user") String actingUser,
        @PathVariable("id") Long userId,
        @RequestBody UserAccount modified) {
        return this.service.updateUser(this.repository.findByUsername(actingUser).getId(), userId, modified);
    }

    @DeleteMapping("/{id}")
    public Optional<UserAccount> deleteUserAccountById (
        @CookieValue(value = "user") String actingUser,
        @PathVariable("id") Long userId
    ) {
        return this.service.deleteUser(this.repository.findByUsername(actingUser).getId(), userId);
    }

    @GetMapping
    public List<UserAccount> getAll() {
        return repository.findAll();
    }

    @GetMapping("/username/{username}")
    public UserAccount getUserAccount(@PathVariable("username") final String username) {
        return repository.findByUsername(username);
    }

    @GetMapping("/validate/{username}")
    public ResponseEntity<Boolean> checkUserName(@PathVariable("username") final String username) {
        UserAccount userAccount = repository.findByUsername(username);
        return new ResponseEntity<>(userAccount == null, HttpStatus.OK);
    }

    @PostMapping("/newAccount")
    public Optional<UserAccount> addUserCreatedAccount (@RequestBody UserAccount newUserAccount) {
        return this.service.createUser(0L, newUserAccount);
    }
}
