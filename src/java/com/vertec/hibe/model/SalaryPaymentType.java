/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.hibe.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vertec-r
 */
@Entity
@Table(name = "salary_payment_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SalaryPaymentType.findAll", query = "SELECT s FROM SalaryPaymentType s"),
    @NamedQuery(name = "SalaryPaymentType.findById", query = "SELECT s FROM SalaryPaymentType s WHERE s.id = :id"),
    @NamedQuery(name = "SalaryPaymentType.findByType", query = "SELECT s FROM SalaryPaymentType s WHERE s.type = :type")})
public class SalaryPaymentType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salaryPaymentTypeId")
    private Collection<SalaryPayment> salaryPaymentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salaryPaymentTypeId")
    private Collection<Advance> advanceCollection;

    public SalaryPaymentType() {
    }

    public SalaryPaymentType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<SalaryPayment> getSalaryPaymentCollection() {
        return salaryPaymentCollection;
    }

    public void setSalaryPaymentCollection(Collection<SalaryPayment> salaryPaymentCollection) {
        this.salaryPaymentCollection = salaryPaymentCollection;
    }

    @XmlTransient
    public Collection<Advance> getAdvanceCollection() {
        return advanceCollection;
    }

    public void setAdvanceCollection(Collection<Advance> advanceCollection) {
        this.advanceCollection = advanceCollection;
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
        if (!(object instanceof SalaryPaymentType)) {
            return false;
        }
        SalaryPaymentType other = (SalaryPaymentType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.SalaryPaymentType[ id=" + id + " ]";
    }
    
}
