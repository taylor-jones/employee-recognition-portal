package com.ttt.erp.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "user_account")
public class UserAccount {
    // columns

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @NotNull
    private Integer id;

    @Column(name = "email", unique = true)
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;

    @NotNull
    @Size(min = 8, max = 16)
    @Column(name = "password")
    private String password;

    @Lob
    @Column(name = "signature")
    private String signature;

    @NotNull
    @Column(name = "is_admin")
    private Boolean isAdmin;


    // constructors

    public UserAccount() {}
    public UserAccount(Integer id, String email, String username, String password, String signature, Boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.signature = signature;
        this.isAdmin = isAdmin;
    }

//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }

    // getters

    public Integer getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getSignature() {
        return this.signature;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin;
    }
    

    // setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
