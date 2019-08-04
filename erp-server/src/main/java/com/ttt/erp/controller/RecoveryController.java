package com.ttt.erp.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.ttt.erp.model.RecoveryQuestion;
import com.ttt.erp.service.RecoveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/recover")
public class RecoveryController {

    private final RecoveryService recoveryService;

    public RecoveryController(RecoveryService recoveryService) {
        this.recoveryService = recoveryService;
    }

    @GetMapping(value = "/{username}/questions")
    public List<RecoveryQuestion> getQuestions(
            @PathVariable(value = "username") String username) {
        return this.recoveryService.getRecoveryQuestions(username);
    }

    @PostMapping(value = "/{username}/questions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkAnswers(
            @PathVariable(value = "username") String username,
            @RequestBody List<RecoveryQuestion> questions) {
        Boolean isValid = this.recoveryService.validateRecoveryQuestions(username, questions);

        return isValid ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/{username}/newPassword")
    public ResponseEntity updatePassword(
            @PathVariable(value = "username") String username,
            @RequestBody String newPassword) {
        Boolean isChanged = this.recoveryService.updateUsersPassword(username, newPassword);

        return isChanged ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
