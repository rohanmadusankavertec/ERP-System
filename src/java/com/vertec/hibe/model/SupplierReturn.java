/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.hibe.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vertec-r
 */
@Entity
@Table(name = "supplier_return")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupplierReturn.findAll", query = "SELECT s FROM SupplierReturn s"),
    @NamedQuery(name = "SupplierReturn.findById", query = "SELECT s FROM SupplierReturn s WHERE s.id = :id"),
    @NamedQuery(name = "SupplierReturn.findByDate", query = "SELECT s FROM SupplierReturn s WHERE s.date = :date")})
public class SupplierReturn implements Serializable {

    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
    @ManyToOne(optional = false)
    private Branch branchId;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierReturnId")
    private Collection<ReturnToSupplier> returnToSupplierCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierReturnId")
    private Collection<ReturnBySupplier> returnBySupplierCollection;

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id")
    @ManyToOne(optional = false)
    private Supplier supplierId;

    public SupplierReturn() {
    }

    public SupplierReturn(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        this.supplierId = supplierId;
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
        if (!(object instanceof SupplierReturn)) {
            return false;
        }
        SupplierReturn other = (SupplierReturn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.SupplierReturn[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<ReturnToSupplier> getReturnToSupplierCollection() {
        return returnToSupplierCollection;
    }

    public void setReturnToSupplierCollection(Collection<ReturnToSupplier> returnToSupplierCollection) {
        this.returnToSupplierCollection = returnToSupplierCollection;
    }

    @XmlTransient
    public Collection<ReturnBySupplier> getReturnBySupplierCollection() {
        return returnBySupplierCollection;
    }

    public void setReturnBySupplierCollection(Collection<ReturnBySupplier> returnBySupplierCollection) {
        this.returnBySupplierCollection = returnBySupplierCollection;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Branch getBranchId() {
        return branchId;
    }

    public void setBranchId(Branch branchId) {
        this.branchId = branchId;
    }

   
    
}
