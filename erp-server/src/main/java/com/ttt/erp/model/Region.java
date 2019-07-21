package com.ttt.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "region")
public class Region {
    // columns

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 35)
    @NaturalId
    @Column(name = "name")
    private String name;


    // relationships

    @OneToMany(targetEntity = EmployeeRegion.class, mappedBy = "region")
    @JsonIgnore
    private Set<EmployeeRegion> employees = new HashSet<>();


    // constructors

    public Region() {}

    public Region(String name) {
        this.name = name;
    }

    public Region(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    // getters

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    Set<EmployeeRegion> getEmployees() {
        return this.employees;
    }


    // setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}