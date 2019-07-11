package com.ttt.erp.model;

import javax.persistence.*;

@Entity
@Table(name = "rietz_testing")
public class RietzTesting {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "text_field", nullable = false)
    private String text_field;

    @Column(name = "other_text_field", nullable = false)
    private String other_text_field;

    public RietzTesting() {}

    public RietzTesting(Long id, String textField, String otherTextField) {
        this.id = id;
        this.text_field = textField;
        this.other_text_field = otherTextField;
    }

    public Long getId() {
        return this.id;
    }

    public String getTextField() {
        return this.text_field;
    }

    public String getOtherTextField() {
        return this.other_text_field;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTextField(String text) {
        this.text_field = text;
    }

    public void setOtherTextField(String text) {
        this.other_text_field = text;
    }

}