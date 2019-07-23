package com.ttt.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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


    // relationships

    @OneToMany(targetEntity = Award.class, mappedBy = "awardType", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Award> awards = new HashSet<>();


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

    public Set<Award> getAwards() {
        return this.awards;
    }


    // setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    // equals, hashcode, and toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AwardType awardType = (AwardType) o;
        return id.equals(awardType.id) &&
            name.equals(awardType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "AwardType{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}