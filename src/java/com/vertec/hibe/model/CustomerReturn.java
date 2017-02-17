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
@Table(name = "customer_return")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerReturn.findAll", query = "SELECT c FROM CustomerReturn c"),
    @NamedQuery(name = "CustomerReturn.findById", query = "SELECT c FROM CustomerReturn c WHERE c.id = :id"),
    @NamedQuery(name = "CustomerReturn.findByDate", query = "SELECT c FROM CustomerReturn c WHERE c.date = :date")})
public class CustomerReturn implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerReturnId")
    private Collection<ReturnToCustomer> returnToCustomerCollection;
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
    @ManyToOne(optional = false)
    private Branch branchId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerReturnId")
    private Collection<ReturnByCustomer> returnByCustomerCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")
    @ManyToOne(optional = false)
    private Invoice invoiceId;
    
    public CustomerReturn() {
    }

    public CustomerReturn(Integer id) {
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

    

    public Invoice getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Invoice invoiceId) {
        this.invoiceId = invoiceId;
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
        if (!(object instanceof CustomerReturn)) {
            return false;
        }
        CustomerReturn other = (CustomerReturn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.CustomerReturn[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<ReturnToCustomer> getReturnToCustomerCollection() {
        return returnToCustomerCollection;
    }

    public void setReturnToCustomerCollection(Collection<ReturnToCustomer> returnToCustomerCollection) {
        this.returnToCustomerCollection = returnToCustomerCollection;
    }

    public Branch getBranchId() {
        return branchId;
    }

    public void setBranchId(Branch branchId) {
        this.branchId = branchId;
    }

    @XmlTransient
    public Collection<ReturnByCustomer> getReturnByCustomerCollection() {
        return returnByCustomerCollection;
    }

    public void setReturnByCustomerCollection(Collection<ReturnByCustomer> returnByCustomerCollection) {
        this.returnByCustomerCollection = returnByCustomerCollection;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
}
