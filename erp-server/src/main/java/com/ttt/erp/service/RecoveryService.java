package com.ttt.erp.service;

import com.ttt.erp.model.RecoveryQuestion;
import com.ttt.erp.model.UserAccount;
import com.ttt.erp.repository.RecoveryQuestionRepository;
import com.ttt.erp.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecoveryService extends LogService{

    private final RecoveryQuestionRepository recoveryQuestionRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserAccountService userAccountService;

    public RecoveryService(RecoveryQuestionRepository recoveryQuestionRepository, UserAccountRepository userAccountRepository, UserAccountService userAccountService) {
        this.recoveryQuestionRepository = recoveryQuestionRepository;
        this.userAccountRepository = userAccountRepository;
        this.userAccountService = userAccountService;
    }

    public List<RecoveryQuestion> getRecoveryQuestions(String username) {
        return this.recoveryQuestionRepository.findAllByUserAccountUsername(username);
    }

    public Boolean validateRecoveryQuestions(String username, final List<RecoveryQuestion> questions) {
        final List<RecoveryQuestion> recoveryQuestions = this.getRecoveryQuestions(username);
        List<RecoveryQuestion> result = new ArrayList<>();

        // protects from users having none set
        if (recoveryQuestions.isEmpty()) {
            return false;
        }

        for(RecoveryQuestion recoveryQuestion : recoveryQuestions) {
            for (RecoveryQuestion question: questions) {
                if (question.getId().equals(recoveryQuestion.getId())) {
                    // ignore case checks using the same case on both sides
                    if (question.getAnswer().equalsIgnoreCase(recoveryQuestion.getAnswer())) {
                        result.add(recoveryQuestion);
                    }
                }
            }
        }

        return result.size() == recoveryQuestions.size();
    }

    public Boolean updateUsersPassword(String username, String password) {
        try {
            UserAccount existing = this.userAccountRepository.findByUsername(username);
            UserAccount modified = new UserAccount(
                    existing.getId(),
                    existing.getEmail(),
                    existing.getUsername(),
                    password,
                    existing.getSignature(),
                    existing.getIsAdmin(),
                    existing.getIsEnabled()
            );

            System.out.println(modified.toString());
            System.out.println(existing.toString());
            System.out.println(this.userAccountRepository.findById(existing.getId()).toString());
//            temp.setPassword(password);
//            System.out.println(modified.toString());
//            System.out.println(existing.toString());

//            modified.setPassword(password);
            this.userAccountService.updateUser(modified.getId(), modified.getId(), modified);
//            logUpdate(modified.getId(), existing.getClass().getSimpleName(), existing.getId(), existing, modified);
//            Optional.ofNullable(this.userAccountRepository.save(modified));

            return this.userAccountRepository.findByUsername(username).getPassword().equals(password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Boolean setRecoveryQuestions(String username, List<RecoveryQuestion> questions) {
        try {
            UserAccount userAccount = this.userAccountRepository.findByUsername(username);

            // TODO: maybe add logging, but it is kind of covered since this goes with creating account
            questions.forEach(question -> question.setUserAccount(userAccount));
            recoveryQuestionRepository.saveAll(questions);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
