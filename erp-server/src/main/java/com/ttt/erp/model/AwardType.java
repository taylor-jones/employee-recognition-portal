package com.ttt.erp.model;

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
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", unique = true)
    private String name;

    // constructors

    public AwardType() {}

    public AwardType(String name) {
        this.name = name;
    }

    public AwardType(Long id, String name) {
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


    // setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}