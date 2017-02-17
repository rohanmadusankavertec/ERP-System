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
@Table(name = "loan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loan.findAll", query = "SELECT l FROM Loan l"),
    @NamedQuery(name = "Loan.findById", query = "SELECT l FROM Loan l WHERE l.id = :id"),
    @NamedQuery(name = "Loan.findByInterest", query = "SELECT l FROM Loan l WHERE l.interest = :interest"),
    @NamedQuery(name = "Loan.findByAmount", query = "SELECT l FROM Loan l WHERE l.amount = :amount"),
    @NamedQuery(name = "Loan.findByTerms", query = "SELECT l FROM Loan l WHERE l.terms = :terms"),
    @NamedQuery(name = "Loan.findByInstallment", query = "SELECT l FROM Loan l WHERE l.installment = :installment"),
    @NamedQuery(name = "Loan.findByDescription", query = "SELECT l FROM Loan l WHERE l.description = :description"),
    @NamedQuery(name = "Loan.findByTotalPayable", query = "SELECT l FROM Loan l WHERE l.totalPayable = :totalPayable"),
    @NamedQuery(name = "Loan.findByPaid", query = "SELECT l FROM Loan l WHERE l.paid = :paid"),
    @NamedQuery(name = "Loan.findByIsLiability", query = "SELECT l FROM Loan l WHERE l.isLiability = :isLiability"),
    @NamedQuery(name = "Loan.findByType", query = "SELECT l FROM Loan l WHERE l.type = :type"),
    @NamedQuery(name = "Loan.findByDate", query = "SELECT l FROM Loan l WHERE l.date = :date")})
public class Loan implements Serializable {

    @OneToMany(mappedBy = "loanId")
    private Collection<Transaction> transactionCollection;

    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account accountId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "interest")
    private Double interest;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "terms")
    private Integer terms;
    @Column(name = "installment")
    private Double installment;
    @Column(name = "description")
    private String description;
    @Column(name = "total_payable")
    private Double totalPayable;
    @Column(name = "paid")
    private Double paid;
    @Column(name = "is_liability")
    private Boolean isLiability;
    @Column(name = "type")
    private Integer type;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    public Loan() {
    }

    public Loan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTerms() {
        return terms;
    }

    public void setTerms(Integer terms) {
        this.terms = terms;
    }

    public Double getInstallment() {
        return installment;
    }

    public void setInstallment(Double installment) {
        this.installment = installment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(Double totalPayable) {
        this.totalPayable = totalPayable;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Boolean getIsLiability() {
        return isLiability;
    }

    public void setIsLiability(Boolean isLiability) {
        this.isLiability = isLiability;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        if (!(object instanceof Loan)) {
            return false;
        }
        Loan other = (Loan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Loan[ id=" + id + " ]";
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    @XmlTransient
    public Collection<Transaction> getTransactionCollection() {
        return transactionCollection;
    }

    public void setTransactionCollection(Collection<Transaction> transactionCollection) {
        this.transactionCollection = transactionCollection;
    }
    
}
