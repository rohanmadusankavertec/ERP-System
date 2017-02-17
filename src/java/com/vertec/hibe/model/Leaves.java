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
@Table(name = "leaves")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Leaves.findAll", query = "SELECT l FROM Leaves l"),
    @NamedQuery(name = "Leaves.findById", query = "SELECT l FROM Leaves l WHERE l.id = :id"),
    @NamedQuery(name = "Leaves.findByDescription", query = "SELECT l FROM Leaves l WHERE l.description = :description"),
    @NamedQuery(name = "Leaves.findByFromdate", query = "SELECT l FROM Leaves l WHERE l.fromdate = :fromdate"),
    @NamedQuery(name = "Leaves.findByTodate", query = "SELECT l FROM Leaves l WHERE l.todate = :todate"),
    @NamedQuery(name = "Leaves.findByDays", query = "SELECT l FROM Leaves l WHERE l.days = :days"),
    @NamedQuery(name = "Leaves.findByIsApproved", query = "SELECT l FROM Leaves l WHERE l.isApproved = :isApproved"),
    @NamedQuery(name = "Leaves.findByRemark", query = "SELECT l FROM Leaves l WHERE l.remark = :remark"),
    @NamedQuery(name = "Leaves.findByIsPay", query = "SELECT l FROM Leaves l WHERE l.isPay = :isPay")})
public class Leaves implements Serializable {

    @Column(name = "type")
    private String type;
    @JoinColumn(name = "added_by", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser addedBy;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "description")
    private String description;
    @Column(name = "fromdate")
    @Temporal(TemporalType.DATE)
    private Date fromdate;
    @Column(name = "todate")
    @Temporal(TemporalType.DATE)
    private Date todate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "days")
    private Double days;
    @Column(name = "is_approved")
    private Boolean isApproved;
    @Column(name = "remark")
    private String remark;
    @Column(name = "is_pay")
    private Boolean isPay;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employee employeeId;

    public Leaves() {
    }

    public Leaves(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    public Double getDays() {
        return days;
    }

    public void setDays(Double days) {
        this.days = days;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getIsPay() {
        return isPay;
    }

    public void setIsPay(Boolean isPay) {
        this.isPay = isPay;
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
        if (!(object instanceof Leaves)) {
            return false;
        }
        Leaves other = (Leaves) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Leaves[ id=" + id + " ]";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SysUser getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(SysUser addedBy) {
        this.addedBy = addedBy;
    }
    
}
