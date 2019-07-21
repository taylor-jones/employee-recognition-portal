package com.ttt.erp.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "log")
public class Log {
    // columns

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LastModifiedBy
    private UserAccount userAccount;

    @Column(name = "controller_class")
    private String controllerClass;

    // TR: Can't use @JoinColumn here, since it will reference ids from lots of tables
    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "operation")
    private String operation;

    @Column(name = "property")
    private String property;

    @Column(name = "changed_from")
    private String changedFrom;

    @Column(name = "changed_to")
    private String changedTo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at", insertable = false, nullable = false)
    @LastModifiedDate
    private Date modifiedAt;


    // constructors

    public Log() {}
    
    public Log (
        Long id, 
        UserAccount userAccount, 
        String controllerClass, 
        Long subjectId,
        String operation, 
        String property, 
        String changedFrom, 
        String changedTo, 
        Date modifiedAt
    ) {
        this.id = id;
        this.userAccount = userAccount;
        this.controllerClass = controllerClass;
        this.subjectId = subjectId;
        this.operation = operation;
        this.property = property;
        this.changedFrom = changedFrom;
        this.changedTo = changedTo;
        this.modifiedAt = modifiedAt;
    }

    public Log (
        UserAccount userAccount, 
        String controllerClass, 
        Long subjectId, 
        String operation, 
        String property, 
        String changedFrom, 
        String changedTo
    ) {
        this.userAccount = userAccount;
        this.controllerClass = controllerClass;
        this.subjectId = subjectId;
        this.operation = operation;
        this.property = property;
        this.changedFrom = changedFrom;
        this.changedTo = changedTo;
    }


    // getters

    public Long getId() {
        return this.id;
    }

    public UserAccount userAccount() {
        return this.userAccount;
    }

    public String controllerClass() {
        return this.controllerClass;
    }

    public Long subjectId() {
        return this.subjectId;
    }

    public String operation() {
        return this.operation;
    }

    public String property() {
        return this.property;
    }

    public String changedFrom() {
        return this.changedFrom;
    }

    public String changedTo() {
        return this.changedTo;
    }

    public Date modifiedAt() {
        return this.modifiedAt;
    }


    // setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserAccountId(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void setControllerClass(String controllerClass) {
        this.controllerClass = controllerClass;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setChangedFrom(String changedFrom) {
        this.changedFrom = changedFrom;
    }

    public void setChangedTo(String changedTo) {
        this.changedTo = changedTo;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}