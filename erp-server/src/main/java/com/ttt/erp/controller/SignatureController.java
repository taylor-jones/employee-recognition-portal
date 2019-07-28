package com.ttt.erp.controller;

import com.ttt.erp.service.SignatureService;

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

    @GetMapping("")
    public Optional<String> findSignature(Principal principal) {
        return service.getSignatureForUsername(principal.getName());
    }   

    @PostMapping("")
    public ResponseEntity<String> createSignature(@RequestBody Map<String, String> body) {
        return this.service.newSignature(body);
    }

}