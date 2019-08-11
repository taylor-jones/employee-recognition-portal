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

    @GetMapping("/file")
    public void findSignatureFileData(@CookieValue(value = "user") String actingUser) {
        String fName = this.userRepo.findByUsername(actingUser).getSignature();
        System.out.println(this.service.getSignatureBase64(fName));
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