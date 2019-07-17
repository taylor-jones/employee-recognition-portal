package com.ttt.erp.model;

import org.hibernate.annotations.Type;

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
    private Integer id;

    @Column(name = "email", unique = true)
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;

    @NotNull
    @Size(min = 4, max = 16)
    @Column(name = "password")
    private String password;

    // https://gist.github.com/vegaasen/7ffb86fe380f33655ba3c59fc28459e5
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "signature")
    private byte[] signature;

    @NotNull
    @Column(name = "is_admin")
    private Boolean isAdmin;


    // constructors

    public UserAccount() {}

    public UserAccount(String email, String username, String password, byte[] signature, Boolean isAdmin) {
        this.email = email;
        this.username = username;
        this.signature = signature;
        this.isAdmin = isAdmin;
    }

    public UserAccount(Integer id, String email, String username, String password, byte[] signature, Boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.signature = signature;
        this.isAdmin = isAdmin;
    }


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

    public byte[] getSignature() {
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

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}