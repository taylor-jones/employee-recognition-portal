package com.ttt.erp.service;

import com.ttt.erp.model.RecoveryQuestion;
import com.ttt.erp.model.UserAccount;
import com.ttt.erp.repository.RecoveryQuestionRepository;
import com.ttt.erp.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

            // Create the new User and user the new User's id
            // as both the subjectId and the modifiedById. 
            UserAccount newUser = repository.save(user);
            Long newUserId = newUser.getId();
            Long actingUserId = userAccountId == 0 ? newUserId : userAccountId;
            logInsert(actingUserId, newUser.getClass().getSimpleName(), newUserId);
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

    
    /**
     * Gets a list of existing usernames and emails
     * to check for validation when attemtping to create
     * a new user account.
     * @return JSON list of arrays in the format of:
     * [username, email]
     */
    public List<Object> getExistingUsernamesAndEmails() {
      List<UserAccount> users = this.repository.findAll();
      ArrayList<Object> results = new ArrayList<>();

      users.forEach(u -> {
        ArrayList<Object> curr = new ArrayList<>();
          curr.add(u.getUsername());
          curr.add(u.getEmail());
          results.add(curr);
      });

      return results;
    }
}
