package com.ttt.erp.service;

import com.ttt.erp.model.UserAccount;
import com.ttt.erp.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Service
public class UserAccountService extends LogService {

    @Autowired
    private UserAccountRepository repository;

    @Autowired
    private SignatureService signatureService;


    public UserAccount getUserByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    // get the user id from a principal
    public UserAccount userFromPrincipal(Principal principal) {
        return this.repository.findByUsername(principal.getName());
    }

    public String signatureFileNameByUsername(String username) {
        return this.repository.findByUsername(username).getSignature();
    }

    /* Create a new user. If non-admin, should have signature data. Store the signature
    * data separately so that it doesn't get
    *
    */
    public Optional<UserAccount> createUser(Long userAccountId, UserAccount user) {
        try {
            if (! user.getIsAdmin()) {
                user.setSignature(this.signatureService.newSignatureForUser(user.getSignature(), user));
            } else {
                user.setSignature(null);
            }
            UserAccount newUser = repository.save(user);
            logInsert(userAccountId, newUser.getClass().getSimpleName(), newUser.getId());
            return Optional.ofNullable(newUser);
        } catch (Exception e) {
            System.out.println("Error on UserAccount insert");
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // update existing user
    public Optional<UserAccount> updateUser(Long userAccountId, Long userId, UserAccount modified) {
        try {
            UserAccount existing = this.repository.findById(userId);
            modified.setId(userId);
            logUpdate(userAccountId, existing.getClass().getSimpleName(), existing.getId(), existing, modified);
            return Optional.ofNullable(this.repository.save(modified));
        } catch (Exception e) {
            System.out.println("Error on UserAccount update");
            e.printStackTrace();
            return Optional.empty();
        }
    }


    // delete user
    public Optional<UserAccount> deleteUser(Long userAccountId, Long userId) {
        try {
            UserAccount toDelete = this.repository.findById(userId);
            logDelete(userAccountId, toDelete.getClass().getSimpleName(), userId);
            this.repository.delete(toDelete);
            return Optional.ofNullable(toDelete);
        } catch (Exception e) {
            System.err.println("Error on UserAccount update");
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
