package com.ttt.erp.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "award")
public class Award {

    // columns

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 1000)
    @Column(name = "description")
    private String description;

    @Column(name = "awarded_date")
    private Date awardedDate;

    @Column(name = "awarded_time")
    private Date awardedTime;


    // relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "award_type_id", referencedColumnName = "id", nullable = false)
    private AwardType awardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", referencedColumnName = "id", nullable = false)
    private UserAccount userAccount;


    // constructors

    public Award() {}

    public Award(
      AwardType awardType,
      Employee employee, 
      UserAccount userAccount,
      String description, 
      Date awardedDate, 
      Date awardedTime
    ) {
      this.awardType = awardType;
      this.employee = employee;
      this.userAccount = userAccount;
      this.description = description;
      this.awardedDate = awardedDate;
      this.awardedTime = awardedTime;
    }

    public Award(
      Long id, 
      AwardType awardType,
      Employee employee, 
      UserAccount userAccount,
      String description, 
      Date awardedDate, 
      Date awardedTime
    ) {
      this.id = id;
      this.awardType = awardType;
      this.employee = employee;
      this.userAccount = userAccount;
      this.description = description;
      this.awardedDate = awardedDate;
      this.awardedTime = awardedTime;
    }


    // getters and setters

    public Long getId() {
        return this.id;
    }

    public AwardType getAwardType() {
        return this.awardType;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public UserAccount getUserAccount() {
        return this.userAccount;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getAwardedDate() {
        return this.awardedDate;
    }

    public Date getAwardedTime() {
        return this.awardedTime;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setAwardType(AwardType awardType) {
        this.awardType = awardType;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAwardedDate(Date awardedDate) {
        this.awardedDate = awardedDate;
    }

    public void setAwardedTime(Date awardedTime) {
        this.awardedTime = awardedTime;
    }
}