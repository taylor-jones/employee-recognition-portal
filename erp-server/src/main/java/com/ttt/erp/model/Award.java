package com.ttt.erp.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;


@Entity
@Table(name = "award")
public class Award {

    // columns

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 1000, message = "description may only be up to 1000 characters long.")
    @Column(name = "description")
    private String description;

    @Column(name = "awarded_date")
    private LocalDate awardedDate;

    @Column(name = "awarded_time")
    private LocalTime awardedTime;


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
      LocalDate awardedDate,
      LocalTime awardedTime
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
      LocalDate awardedDate,
      LocalTime awardedTime
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

    public LocalDate getAwardedDate() {
        return this.awardedDate;
    }

    public LocalTime getAwardedTime() {
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

    public void setAwardedDate(LocalDate awardedDate) {
        this.awardedDate = awardedDate;
    }

    public void setAwardedTime(LocalTime awardedTime) {
        this.awardedTime = awardedTime;
    }


    // equals, hashcode, and toString


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Award award = (Award) o;
        return id.equals(award.id) &&
            Objects.equals(description, award.description) &&
            Objects.equals(awardedDate, award.awardedDate) &&
            Objects.equals(awardedTime, award.awardedTime) &&
            awardType.equals(award.awardType) &&
            employee.equals(award.employee) &&
            userAccount.equals(award.userAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            description,
            awardedDate,
            awardedTime,
            awardType,
            employee,
            userAccount
        );
    }

    @Override
    public String toString() {
        return "Award{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", awardedDate=" + awardedDate +
            ", awardedTime=" + awardedTime +
            ", awardType=" + awardType +
            ", employee=" + employee +
            ", userAccount=" + userAccount +
            '}';
    }

}