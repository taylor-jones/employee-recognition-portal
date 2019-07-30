package com.ttt.erp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttt.erp.model.UserAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserManager userManager;
    private final UserAccountService userAccountService;
    private String globalCookieAccess = "/";

    public AuthenticationSuccessHandler(UserManager userManager, UserAccountService userAccountService) {
        this.userManager = userManager;
        this.userAccountService = userAccountService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserAccount userAccount = this.userAccountService.getUserByUsername(authentication.getName());
        if (userAccount != null) {
            this.userManager.set(userAccount);
            Cookie userCookie = new Cookie("user", userAccount.getUsername().toString());
            Cookie roleCookie = new Cookie("admin", userAccount.getIsAdmin().toString());
            userCookie.setPath(globalCookieAccess);
            roleCookie.setPath(globalCookieAccess);
            response.addCookie(userCookie);
            response.addCookie(roleCookie);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
