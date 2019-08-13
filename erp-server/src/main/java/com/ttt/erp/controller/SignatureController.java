package com.ttt.erp.controller;

import com.ttt.erp.repository.UserAccountRepository;
import com.ttt.erp.service.AwsS3ClientService;
import com.ttt.erp.service.SignatureService;
import com.ttt.erp.service.UserAccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/signatures")
public class SignatureController {

    @Autowired
    private SignatureService service;

    @Autowired
    private UserAccountService userService;

    @Autowired 
    private UserAccountRepository userRepo;

    @Autowired 
    private AwsS3ClientService s3Client;

    @GetMapping
    public Optional<String> findSignatureName(Principal principal) {
        return service.getSignatureForUsername(principal.getName());
    }

    @GetMapping("/mine")
    public Optional<String> findSignatureFileData(@CookieValue(value = "user") String actingUser) {
        if (this.userRepo.findByUsername(actingUser).getIsAdmin() == false) {
            return Optional.ofNullable( this.service.getSignatureBase64(this.userRepo.findByUsername(actingUser).getSignature()) );
        } else {
            return Optional.empty();
        }
    }

    @PostMapping
    public ResponseEntity<String> createCurrentUserSignature(@RequestBody Map<String, String> body, Principal principal) {
        String fName = this.service.newSignatureForUser (
            body.get("signature"),
            userService.getUserByUsername(principal.getName())
        );
        return new ResponseEntity<String> ("Success", HttpStatus.OK); 
    }

}