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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "allowance_deduction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AllowanceDeduction.findAll", query = "SELECT a FROM AllowanceDeduction a"),
    @NamedQuery(name = "AllowanceDeduction.findById", query = "SELECT a FROM AllowanceDeduction a WHERE a.id = :id"),
    @NamedQuery(name = "AllowanceDeduction.findByName", query = "SELECT a FROM AllowanceDeduction a WHERE a.name = :name"),
    @NamedQuery(name = "AllowanceDeduction.findByIsAmount", query = "SELECT a FROM AllowanceDeduction a WHERE a.isAmount = :isAmount"),
    @NamedQuery(name = "AllowanceDeduction.findByValue", query = "SELECT a FROM AllowanceDeduction a WHERE a.value = :value"),
    @NamedQuery(name = "AllowanceDeduction.findByIsAllowance", query = "SELECT a FROM AllowanceDeduction a WHERE a.isAllowance = :isAllowance"),
    @NamedQuery(name = "AllowanceDeduction.findByDate", query = "SELECT a FROM AllowanceDeduction a WHERE a.date = :date"),
    @NamedQuery(name = "AllowanceDeduction.findByIsFixed", query = "SELECT a FROM AllowanceDeduction a WHERE a.isFixed = :isFixed"),
    @NamedQuery(name = "AllowanceDeduction.findByIsValid", query = "SELECT a FROM AllowanceDeduction a WHERE a.isValid = :isValid")})
public class AllowanceDeduction implements Serializable {

    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employee employeeId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "is_amount")
    private Boolean isAmount;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private Double value;
    @Column(name = "is_allowance")
    private Boolean isAllowance;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "is_fixed")
    private Boolean isFixed;
    @Column(name = "is_valid")
    private Boolean isValid;
    @JoinColumn(name = "adtype_id", referencedColumnName = "id")
    @ManyToOne
    private Adtype adtypeId;

    public AllowanceDeduction() {
    }

    public AllowanceDeduction(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsAmount() {
        return isAmount;
    }

    public void setIsAmount(Boolean isAmount) {
        this.isAmount = isAmount;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getIsAllowance() {
        return isAllowance;
    }

    public void setIsAllowance(Boolean isAllowance) {
        this.isAllowance = isAllowance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Boolean isFixed) {
        this.isFixed = isFixed;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Adtype getAdtypeId() {
        return adtypeId;
    }

    public void setAdtypeId(Adtype adtypeId) {
        this.adtypeId = adtypeId;
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
        if (!(object instanceof AllowanceDeduction)) {
            return false;
        }
        AllowanceDeduction other = (AllowanceDeduction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.AllowanceDeduction[ id=" + id + " ]";
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }
    
}
