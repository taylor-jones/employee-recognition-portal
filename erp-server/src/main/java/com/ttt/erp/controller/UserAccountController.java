package com.ttt.erp.controller;

import com.ttt.erp.model.UserAccount;
import com.ttt.erp.repository.UserAccountRepository;
import com.ttt.erp.service.UserManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    private final UserManager userManager;
    private final UserAccountRepository userAccountRepository;

    public UserAccountController(UserManager userManager, UserAccountRepository userAccountRepository) {
        this.userManager = userManager;
        this.userAccountRepository = userAccountRepository;
    }

    // callback for checking login status
    @GetMapping(value = "/whoAmI")
    public UserAccount getUser() {
        return this.userManager.get();
    }

    // TODO: potentially this to get users profile?
//    @GetMapping(value = "/me")

    @GetMapping
    public List<UserAccount> getAll() {
        return userAccountRepository.findAll();
    }

    @GetMapping("/{id}")
    public List<UserAccount> getUserAccount(@PathVariable("id") final Long id) {
        return userAccountRepository.findById(id);
    }

    @GetMapping("/{username}")
    public UserAccount getUserAccount(@PathVariable("username") final String username) {
        return userAccountRepository.findByUsername(username);
    }
}
