package com.ttt.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "recovery_question")
public class RecoveryQuestion {

    // columns

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "question")
    private String question;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "answer")
    @JsonProperty(access = WRITE_ONLY)
    private String answer;


    // relationships
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", referencedColumnName = "id", nullable = false)
    private UserAccount userAccount;

    // getters

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    // setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    // equals, hashcode, and toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecoveryQuestion)) return false;
        RecoveryQuestion that = (RecoveryQuestion) o;
        return getId().equals(that.getId()) &&
                getQuestion().equals(that.getQuestion()) &&
                getAnswer().equals(that.getAnswer()) &&
                getUserAccount().equals(that.getUserAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQuestion(), getAnswer(), getUserAccount());
    }

    @Override
    public String toString() {
        return "RecoveryQuestion{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", userAccount=" + userAccount +
                '}';
    }
}
