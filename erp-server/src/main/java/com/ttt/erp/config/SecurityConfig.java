package com.ttt.erp.config;

import com.ttt.erp.service.AuthenticationFailureHandler;
import com.ttt.erp.service.AuthenticationSuccessHandler;
import com.ttt.erp.service.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

import static java.util.Objects.requireNonNull;

// https://github.com/Baeldung/spring-security-registration/issues/65
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final UnauthorizedEntryPoint unauthorizedEntryPoint;
    private final DataSource dataSource;

    public SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler, UnauthorizedEntryPoint unauthorizedEntryPoint, DataSource dataSource) {
        this.authenticationSuccessHandler = requireNonNull(authenticationSuccessHandler);
        this.authenticationFailureHandler = requireNonNull(authenticationFailureHandler);
        this.unauthorizedEntryPoint = requireNonNull(unauthorizedEntryPoint);
        this.dataSource = dataSource;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @SuppressWarnings("deprecation")
    public static NoOpPasswordEncoder passwordEncoder1() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    // TODO: fix bcrypt passwordencoder, currently using passwordencoder1
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from user_account where username=?")
                .authoritiesByUsernameQuery(
                    "select username, case when is_admin = 't' then 'ROLE_ADMIN' else 'ROLE_USER' end as role from user_account where username=?"
                )
                .passwordEncoder(passwordEncoder1());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/")
                .antMatchers("/index.html")
                .antMatchers("/assets/**")
                .antMatchers("/**/*.js")
                .antMatchers("/**/*.js.gz");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                    .cacheControl().disable()
                .and()
//            	.cors()
//              .and()
                .authorizeRequests()
                    .antMatchers("/api/admin/**").hasRole("ADMIN")
                    .antMatchers("/api/users/whoAmI").authenticated()
                    .antMatchers("/api/users/me").authenticated()
                    .antMatchers("/api/users/validate/**").permitAll()
                    .antMatchers("/api/users/availability/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/users/newAccount").permitAll()
                    .antMatchers("/api/recover/**").permitAll()
                    .antMatchers("/api/**").hasAnyRole("USER","ADMIN")
                .and()
                .formLogin()
                    .loginProcessingUrl("/api/login").permitAll()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedEntryPoint)
                .and()
                .logout()
                    .logoutUrl("/api/logout").permitAll()
//                .logoutSuccessHandler(LogoutSuccessHandler)
                .deleteCookies("JSESSIONID", "user", "admin")
                .and()
                .csrf().disable();
    }
}
