package com.ttt.erp.service;

import com.ttt.erp.model.UserAccount;
import com.ttt.erp.repository.UserAccountRepository;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountService extends LogService {

    @Autowired
    private UserAccountRepository repository;

    // create new user
    public Optional<UserAccount> createUser (Long userAccountId, UserAccount user) {
        try {
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
    public ResponseEntity<String> deleteUser(Long userAccountId, Long userId) {
        try {
            UserAccount toDelete = this.repository.findById(userId);
            logDelete(userAccountId, toDelete.getClass().getSimpleName(), userId);
            this.repository.delete(toDelete);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error on UserAccount update");
            e.printStackTrace();
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
    }
=======
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = requireNonNull(userAccountRepository);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserAccount user = this.userAccountRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return user;
//    }
>>>>>>> Uses Deprecated resoruces.
}
