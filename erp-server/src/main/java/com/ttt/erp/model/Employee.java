package com.ttt.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "employee")
public class Employee {
    // columns

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "last_name")
    private String lastName;


    // relationships

    @OneToMany(targetEntity = EmployeeRegion.class, mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<EmployeeRegion> regions = new HashSet<>();

    @OneToMany(targetEntity = Award.class, mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Award> awards = new ArrayList<>();


    // constructors

    public Employee() {}

    public Employee(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(Long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    // getters

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFullName() {
        return this.firstName + ' ' + this.lastName;
    }

    public Set<EmployeeRegion> getRegions() {
        return this.regions;
    }

    public List<Award> getAwards() {
        return this.awards;
    }


    // setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    // equals, hashcode, and toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id) &&
            email.equals(employee.email) &&
            firstName.equals(employee.firstName) &&
            lastName.equals(employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
    }
}