package com.ttt.erp.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.ttt.erp.model.PasswordResetRequest;
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

    // get users recovery questions
    @GetMapping(value = "/{username}/questions")
    public List<RecoveryQuestion> getQuestions(
            @PathVariable(value = "username") String username) {
        return this.recoveryService.getRecoveryQuestions(username);
    }

    // sets users recovery questions/answers
    @PostMapping(value = "/{username}/questions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setQuestions(
            @PathVariable(value = "username") String username,
            @RequestBody List<RecoveryQuestion> questions) {
        Boolean isValid = this.recoveryService.setRecoveryQuestions(username, questions);

        return isValid ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    // check users recovery answers
    @PostMapping(value = "/{username}/answers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkAnswers(
            @PathVariable(value = "username") String username,
            @RequestBody List<RecoveryQuestion> questions) {
        Boolean isValid = this.recoveryService.validateRecoveryQuestions(username, questions);

        return isValid ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    // set new password for user when forgot
    @PostMapping(value = "/{username}/newPassword")
    public ResponseEntity updatePassword(
            @PathVariable(value = "username") String username,
            @RequestBody PasswordResetRequest request) {
        Boolean isChanged = this.recoveryService.updateUsersPassword(request.getUsername(), request.getPassword());

        return isChanged ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
