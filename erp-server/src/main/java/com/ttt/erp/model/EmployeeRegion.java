package com.ttt.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "employee_region")
public class EmployeeRegion {

    // columns

    @EmbeddedId
    @JsonIgnore
    private EmployeeRegionKey id;

    @ManyToOne
    @MapsId("employee_id")
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @MapsId("region_id")
    @JoinColumn(name = "region_id")
    private Region region;

    @Column(name = "begin_date")
    private Date beginDate;

    @Column(name = "end_date")
    private Date endDate;


    // constructors

    public EmployeeRegion() {
    }

    public EmployeeRegion(
        EmployeeRegionKey id,
        Employee employee,
        Region region,
        Date beginDate,
        Date endDate
    ) {
        this.id = id;
        this.employee = employee;
        this.region = region;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }


    // getters and setters

    public EmployeeRegionKey getId() {
        return id;
    }

    public void setId(EmployeeRegionKey id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    // hashcode and equals


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeRegion that = (EmployeeRegion) o;
        return id.equals(that.id) &&
            employee.equals(that.employee) &&
            region.equals(that.region) &&
            Objects.equals(beginDate, that.beginDate) &&
            Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employee, region, beginDate, endDate);
    }
}
