package com.ttt.erp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_account")
public class UserAccount {
    // columns

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @NotNull
    private Long id;

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
    public UserAccount(Long id, String email, String username, String password, String signature, Boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.signature = signature;
        this.isAdmin = isAdmin;
    }


    // getters

    public Long getId() {
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

    public void setId(Long id) {
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