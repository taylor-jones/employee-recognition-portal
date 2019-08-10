package com.ttt.erp.service;

import com.ttt.erp.model.RecoveryQuestion;
import com.ttt.erp.model.UserAccount;
import com.ttt.erp.repository.RecoveryQuestionRepository;
import com.ttt.erp.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecoveryService extends LogService{

    private final RecoveryQuestionRepository recoveryQuestionRepository;
    private final UserAccountRepository userAccountRepository;

    public RecoveryService(RecoveryQuestionRepository recoveryQuestionRepository, UserAccountRepository userAccountRepository) {
        this.recoveryQuestionRepository = recoveryQuestionRepository;
        this.userAccountRepository = userAccountRepository;
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
            UserAccount modified = existing;

            modified.setPassword(password);
            // doesn't work and no error message
            logUpdate(modified.getId(), existing.getClass().getSimpleName(), existing.getId(), existing, modified);
            Optional.ofNullable(this.userAccountRepository.save(modified));

            return this.userAccountRepository.findByUsername(username).getPassword().equals(password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean setRecoveryQuestions(String username, List<RecoveryQuestion> questions) {
        try {
            UserAccount userAccount = this.userAccountRepository.findByUsername(username);

            // TODO: needs logging
            questions.forEach(question -> question.setUserAccount(userAccount));
            recoveryQuestionRepository.saveAll(questions);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
