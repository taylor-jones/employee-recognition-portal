package com.ttt.erp.model;

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
    private Integer id;

    @NotNull
    @Size(max = 35)
    @NaturalId
    @Column(name = "name")
    private String name;


    // relationships

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = { CascadeType.PERSIST, CascadeType.MERGE },
        mappedBy = "regions")
    private Set<Employee> employees = new HashSet<>();



    // constructors

    public Region() {}

    public Region(String name) {
        this.name = name;
    }

    public Region(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    // getters

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }


    // setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}