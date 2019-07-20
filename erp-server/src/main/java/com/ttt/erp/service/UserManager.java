package com.ttt.erp.service;

import com.ttt.erp.model.UserAccount;

public interface UserManager {
    void set(UserAccount userAccount);
    UserAccount get();
}
