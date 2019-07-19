package com.ttt.erp.controller;

import com.ttt.erp.model.UserAccount;
import com.ttt.erp.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    @Autowired
    UserAccountRepository userAccountRepository;

    @GetMapping("")
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
