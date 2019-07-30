package com.ttt.erp.controller;

import com.ttt.erp.model.Award;
import com.ttt.erp.model.UserAccount;
import com.ttt.erp.repository.AwardRepository;
import com.ttt.erp.repository.UserAccountRepository;
import com.ttt.erp.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.ttt.erp.service.UserManager;
import org.springframework.web.bind.annotation.*;

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

    // TODO: get actual userId from cookie
    @PostMapping
    public Optional<UserAccount> addUserAccount (
        @CookieValue(value = "userId", defaultValue = "1") String modifiedById,
        @RequestBody UserAccount newUserAccount) {
        return this.service.createUser(Long.parseLong(modifiedById), newUserAccount);
    }

    // callback for checking login status
    @GetMapping(value = "/whoAmI")
    public UserAccount getUser(@CookieValue("user") String user) {
        System.out.println(this.repository.findByUsername(user).toString());
        return this.userManager.get();
    }

    // TODO: potentially this to get users profile?
//    @GetMapping(value = "/me")


    // TODO: get actual userId from cookie
    @PutMapping("/{id}")
    public Optional<UserAccount> updateUserAccountById (
        @CookieValue(value = "userId", defaultValue = "1") String modifiedById,
        @PathVariable("id") Long awardId,
        @RequestBody UserAccount modified) {
        return this.service.updateUser(Long.parseLong(modifiedById), awardId, modified);
    }

    // TODO: get actual userId from cookie
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserAccountById (
        @CookieValue(value = "userId", defaultValue = "1") String modifiedById,
        @PathVariable("id") Long awardId
    ) {
        return this.service.deleteUser(Long.parseLong(modifiedById), awardId);
    }

    @GetMapping
    public List<UserAccount> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{username}")
    public UserAccount getUserAccount(@PathVariable("username") final String username) {
        return repository.findByUsername(username);
    }
}
