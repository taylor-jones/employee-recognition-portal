package com.ttt.erp.controller;

import com.ttt.erp.model.UserAccount;
import com.ttt.erp.repository.UserAccountRepository;
import com.ttt.erp.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    @Autowired
    UserAccountRepository repository;

    @Autowired
    UserAccountService service;


    @GetMapping("/{id}")
    public UserAccount getUserAccount(@PathVariable("id") final Long id) {
        return repository.findById(id);
    }

    // TODO: get actual userId from cookie
    @PostMapping
    public Optional<UserAccount> addUserAccount (
        @CookieValue(value = "userId", defaultValue = "1") String modifiedById,
        @RequestBody UserAccount newUserAccount) {
        return this.service.createUser(Long.parseLong(modifiedById), newUserAccount);
    }

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
}
