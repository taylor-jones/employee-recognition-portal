package com.ttt.erp.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "award_type")
public class AwardType {
    // columns

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Size(max = 100)
    @NaturalId
    @Column(name = "name")
    private String name;

    // constructors

    public AwardType() {}

    public AwardType(String name) {
        this.name = name;
    }

    public AwardType(Integer id, String name) {
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