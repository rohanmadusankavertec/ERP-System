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
@Table(name = "depreciation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Depreciation.findAll", query = "SELECT d FROM Depreciation d"),
    @NamedQuery(name = "Depreciation.findById", query = "SELECT d FROM Depreciation d WHERE d.id = :id"),
    @NamedQuery(name = "Depreciation.findByRate", query = "SELECT d FROM Depreciation d WHERE d.rate = :rate"),
    @NamedQuery(name = "Depreciation.findByTerm", query = "SELECT d FROM Depreciation d WHERE d.term = :term"),
    @NamedQuery(name = "Depreciation.findByAmount", query = "SELECT d FROM Depreciation d WHERE d.amount = :amount"),
    @NamedQuery(name = "Depreciation.findByIsClosed", query = "SELECT d FROM Depreciation d WHERE d.isClosed = :isClosed"),
    @NamedQuery(name = "Depreciation.findByDate", query = "SELECT d FROM Depreciation d WHERE d.date = :date")})
public class Depreciation implements Serializable {

    @OneToMany(mappedBy = "depreciationId")
    private Collection<Transaction> transactionCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rate")
    private Double rate;
    @Column(name = "term")
    private Integer term;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "is_closed")
    private Boolean isClosed;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "credit", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account credit;
    @JoinColumn(name = "debit", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account debit;

    public Depreciation() {
    }

    public Depreciation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Account getCredit() {
        return credit;
    }

    public void setCredit(Account credit) {
        this.credit = credit;
    }

    public Account getDebit() {
        return debit;
    }

    public void setDebit(Account debit) {
        this.debit = debit;
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
        if (!(object instanceof Depreciation)) {
            return false;
        }
        Depreciation other = (Depreciation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Depreciation[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Transaction> getTransactionCollection() {
        return transactionCollection;
    }

    public void setTransactionCollection(Collection<Transaction> transactionCollection) {
        this.transactionCollection = transactionCollection;
    }
    
}
