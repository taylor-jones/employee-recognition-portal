package com.ttt.erp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class EmployeeRegionKey implements Serializable {

    // columns

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "region_id")
    private Long regionId;


    // constructors

    public EmployeeRegionKey() {}

    public EmployeeRegionKey(Long employeeId, Long regionId) {
        this.employeeId = employeeId;
        this.regionId = regionId;
    }


    // getters / setters

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }


    // equals, hashcode, and toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeRegionKey that = (EmployeeRegionKey) o;
        return employeeId.equals(that.employeeId) &&
            regionId.equals(that.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, regionId);
    }

    @Override
    public String toString() {
        return "EmployeeRegionKey{" +
            "employeeId=" + employeeId +
            ", regionId=" + regionId +
            '}';
    }
}
