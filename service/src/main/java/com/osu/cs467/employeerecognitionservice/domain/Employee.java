package com.osu.cs467.employeerecognitionservice.domain;

import java.time.LocalDateTime;

public class Employee {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private LocalDateTime created;

    public Employee(Long employeeId, String firstName, String lastName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.created = LocalDateTime.now();
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}
