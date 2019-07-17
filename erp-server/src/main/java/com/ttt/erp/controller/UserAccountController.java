package com.ttt.erp.controller;

import com.ttt.erp.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    @Autowired
    UserAccountRepository userAccountRepository;
    
}
