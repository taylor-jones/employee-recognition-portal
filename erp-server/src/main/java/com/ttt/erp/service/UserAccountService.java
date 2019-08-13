package com.ttt.erp.service;

import com.ttt.erp.model.RecoveryQuestion;
import com.ttt.erp.model.UserAccount;
import com.ttt.erp.repository.RecoveryQuestionRepository;
import com.ttt.erp.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserAccountService extends LogService {

    @Autowired
    private UserAccountRepository repository;

    @Autowired
    RecoveryQuestionRepository recoveryAnswersRepository;

    @Autowired
    private SignatureService signatureService;


    public UserAccount getUserByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    // get the user id from a principal
    public UserAccount userFromPrincipal(Principal principal) {
        return this.repository.findByUsername(principal.getName());
    }

    public Optional<String> signatureFileNameByUsername(String username) {
        return Optional.ofNullable(this.repository.findByUsername(username).getSignature());
    }

    public Optional<UserAccount> createUser(Long userAccountId, UserAccount user) {
        try {
            if (user.getIsAdmin() == null || !user.getIsAdmin()) {
                user.setSignature(this.signatureService.newSignatureForUser(user.getSignature(), user));
            } else {
                user.setSignature(null);
            }
            user.setIsEnabled(true);

            // was having cases where the logInsert was looking up the users, before the
            // create transaction was completed
            UserAccount newUser = repository.save(user);
            if (!newUser.getIsAdmin()) {
                // works for creating a new user as an unlogged in user
                logInsert(userAccountId, newUser.getClass().getSimpleName(), newUser.getId());
            } else {
                logInsert(userAccountId, newUser.getClass().getSimpleName(), newUser.getId());
            }
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
            Optional<String> fName = signatureFileNameByUsername(existing.getUsername());
            if (fName.isPresent()) {
                modified.setSignature(fName.get());
            } else {
                modified.setSignature(null);
            }
            logUpdate(userAccountId, existing.getClass().getSimpleName(), userId, existing, modified);
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
            this.signatureService.deleteSignatureByFileName(toDelete.getSignature());
            this.repository.delete(toDelete);
            return Optional.ofNullable(toDelete);
        } catch (Exception e) {
            System.err.println("Error on UserAccount update");
            e.printStackTrace();
            return Optional.empty();
        }
    }


    /**
     * Response Format:
     * - [0] user id
     * - [1] username
     * - [2] total awards given
     * @return List all users along with the number of awards given by each user
     */
    public List<Object[]> getUserAwardCounts() {
        return this.repository.getUserAwardCounts();
    }
}
