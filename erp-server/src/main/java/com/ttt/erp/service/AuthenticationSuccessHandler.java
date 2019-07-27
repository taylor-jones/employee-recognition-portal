package com.ttt.erp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttt.erp.model.UserAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserManager userManager;
    private final UserAccountService userAccountService;

    public AuthenticationSuccessHandler(UserManager userManager, UserAccountService userAccountService) {
        this.userManager = userManager;
        this.userAccountService = userAccountService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserAccount userAccount = this.userAccountService.getUserByUsername(authentication.getName());

        if (userAccount != null) {
            this.userManager.set(userAccount);
            response.getWriter().write(new ObjectMapper().writeValueAsString(userAccount));
            response.setStatus(HttpServletResponse.SC_OK);
//        super.onAuthenticationSuccess(request, response, authentication);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
