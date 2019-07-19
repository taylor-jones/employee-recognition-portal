package com.ttt.erp.config;

import com.ttt.erp.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

// https://github.com/Baeldung/spring-security-registration/issues/65
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
//    private final UserAccountService userAccountService;
//
//    public SecurityConfig(DataSource dataSource, UserAccountService userAccountService) {
//        this.dataSource = dataSource;
//        this.userAccountService = userAccountService;
//        System.out.println(new BCryptPasswordEncoder().encode("admin"));
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userAccountService).passwordEncoder(passwordEncoder());
////    }
//
//    // https://stackoverflow.com/questions/49654143/spring-security-5-there-is-no-passwordencoder-mapped-for-the-id-null
//    // https://www.baeldung.com/spring-security-5-default-password-encoder
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username, password, enabled"
//                        + " from users_test where username=?")
//                .authoritiesByUsernameQuery("select username, authority "
//                        + "from authorities_test where username=?")
//                .passwordEncoder(new BCryptPasswordEncoder());
////        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
////        auth.inMemoryAuthentication()
////                .withUser("spring")
////                .password(encoder.encode("secret"))
////                .roles("USER");
//////        auth.userDetailsService(userAccountService).passwordEncoder(passwordEncoder());
//    }
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////
////        http.authorizeRequests().anyRequest().hasAnyRole("ADMIN", "USER")
////                .and()
////                .httpBasic(); // Authenticate users with HTTP basic authentication
////    }
////

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @SuppressWarnings("deprecation")
    public static NoOpPasswordEncoder passwordEncoder1() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username,password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from user_roles where username=?")
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
        http.authorizeRequests()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/**").authenticated()
                .and()
                .formLogin()
                    .loginProcessingUrl("/api/login").permitAll()
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and()
                .logout()
                    .logoutUrl("/api/logout").permitAll()
                    .deleteCookies("JSESSIONID")
                .and()
                .cors().disable();
        http.exceptionHandling().accessDeniedPage("/403");
    }
}
