/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.hibe.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vertec-r
 */
@Entity
@Table(name = "payroll_default")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PayrollDefault.findAll", query = "SELECT p FROM PayrollDefault p"),
    @NamedQuery(name = "PayrollDefault.findById", query = "SELECT p FROM PayrollDefault p WHERE p.id = :id"),
    @NamedQuery(name = "PayrollDefault.findByOtHr", query = "SELECT p FROM PayrollDefault p WHERE p.otHr = :otHr"),
    @NamedQuery(name = "PayrollDefault.findByLateHr", query = "SELECT p FROM PayrollDefault p WHERE p.lateHr = :lateHr"),
    @NamedQuery(name = "PayrollDefault.findByNoPayHr", query = "SELECT p FROM PayrollDefault p WHERE p.noPayHr = :noPayHr"),
    @NamedQuery(name = "PayrollDefault.findByLateLimit", query = "SELECT p FROM PayrollDefault p WHERE p.lateLimit = :lateLimit")})
public class PayrollDefault implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ot_hr")
    private Double otHr;
    @Column(name = "late_hr")
    private Double lateHr;
    @Column(name = "no_pay_hr")
    private Double noPayHr;
    @Column(name = "late_limit")
    @Temporal(TemporalType.TIME)
    private Date lateLimit;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employee employeeId;

    public PayrollDefault() {
    }

    public PayrollDefault(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getOtHr() {
        return otHr;
    }

    public void setOtHr(Double otHr) {
        this.otHr = otHr;
    }

    public Double getLateHr() {
        return lateHr;
    }

    public void setLateHr(Double lateHr) {
        this.lateHr = lateHr;
    }

    public Double getNoPayHr() {
        return noPayHr;
    }

    public void setNoPayHr(Double noPayHr) {
        this.noPayHr = noPayHr;
    }

    public Date getLateLimit() {
        return lateLimit;
    }

    public void setLateLimit(Date lateLimit) {
        this.lateLimit = lateLimit;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayrollDefault)) {
            return false;
        }
        PayrollDefault other = (PayrollDefault) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.PayrollDefault[ id=" + id + " ]";
    }
    
}
