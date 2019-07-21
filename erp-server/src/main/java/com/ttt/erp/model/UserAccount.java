package com.ttt.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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


    @Column(name = "signature")
    private String signature;

    @NotNull
    @Column(name = "is_admin")
    private Boolean isAdmin;


    // relationships
    @OneToMany(targetEntity = Award.class, mappedBy = "userAccount")
    @JsonIgnore
    private Set<Award> awards = new HashSet<>();



    // constructors

    public UserAccount() {}
    public UserAccount(Long id, String email, String username, String password, String signature, Boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
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

    public Set<Award> getAwards() {
        return this.awards;
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


    // equals, hashcode, and toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return id.equals(that.id) &&
            Objects.equals(email, that.email) &&
            username.equals(that.username) &&
            password.equals(that.password) &&
            Objects.equals(signature, that.signature) &&
            isAdmin.equals(that.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, password, signature, isAdmin);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", signature='" + signature + '\'' +
            ", isAdmin=" + isAdmin +
            '}';
    }
}