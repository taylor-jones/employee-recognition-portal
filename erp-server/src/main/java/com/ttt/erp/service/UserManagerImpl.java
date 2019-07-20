package com.ttt.erp.service;

import com.ttt.erp.model.UserAccount;
import org.springframework.stereotype.Component;

import javax.inject.Provider;
import javax.servlet.http.HttpSession;

import static java.util.Objects.requireNonNull;

@Component
public class UserManagerImpl implements UserManager {

    private static final String USER_KEY = "user";

    private final Provider<HttpSession> sessionProvider;

    public UserManagerImpl(Provider<HttpSession> sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    @Override
    public void set(UserAccount userAccount) {
        this.sessionProvider.get().setAttribute(USER_KEY, requireNonNull(userAccount));
    }

    @Override
    public UserAccount get() {
        return (UserAccount) this.sessionProvider.get().getAttribute(USER_KEY);
    }
}
