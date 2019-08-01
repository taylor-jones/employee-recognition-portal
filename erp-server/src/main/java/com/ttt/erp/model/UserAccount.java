package com.ttt.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import java.util.Collection;

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
    @Email(message = "email should be a valid email address")
    @Size(max = 100)
    private String email;

    @NotEmpty(message = "username must not be empty")
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "password must not be empty")
    @Size(max = 16)
    @Column(name = "password")
    private String password;

    @Column(name = "signature", nullable = true)
    private String signature;

    @NotNull
    @Column(name = "is_admin")
    private Boolean isAdmin;

    @NotNull
    @Column(name = "enabled")
    private Boolean isEnabled;


    // relationships
    @OneToMany(targetEntity = Award.class, mappedBy = "userAccount", cascade = CascadeType.ALL)
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

    public UserAccount(String email, String username, String password, Boolean isAdmin) {
        this.email = email;
        this.username = username;
        this.password = password;
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

    public Boolean getIsEnabled() {
        return this.isEnabled;
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

    public void setSignature(@Nullable String signature) {
        this.signature = signature;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
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
            isAdmin.equals(that.isAdmin) &&
            isEnabled.equals(that.isEnabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
          id, 
          email, 
          username, 
          password, 
          signature, 
          isAdmin, 
          isEnabled
        );
    }

    @Override
    public String toString() {
        return "UserAccount{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", signature='" + signature + '\'' +
            ", isAdmin=" + isAdmin + '\'' +
            ", isEnabled=" + isEnabled +
            '}';
    }
}


