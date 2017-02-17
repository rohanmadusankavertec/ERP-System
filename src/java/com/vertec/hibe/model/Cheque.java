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
@Table(name = "cheque")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cheque.findAll", query = "SELECT c FROM Cheque c"),
    @NamedQuery(name = "Cheque.findById", query = "SELECT c FROM Cheque c WHERE c.id = :id"),
    @NamedQuery(name = "Cheque.findByChequeNo", query = "SELECT c FROM Cheque c WHERE c.chequeNo = :chequeNo"),
    @NamedQuery(name = "Cheque.findByChequeDate", query = "SELECT c FROM Cheque c WHERE c.chequeDate = :chequeDate"),
    @NamedQuery(name = "Cheque.findByIsValid", query = "SELECT c FROM Cheque c WHERE c.isValid = :isValid"),
    @NamedQuery(name = "Cheque.findByAmount", query = "SELECT c FROM Cheque c WHERE c.amount = :amount")})
public class Cheque implements Serializable {

    @OneToMany(mappedBy = "chequeId")
    private Collection<SalaryPayment> salaryPaymentCollection;
    @OneToMany(mappedBy = "chequeId")
    private Collection<Advance> advanceCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cheque_no")
    private String chequeNo;
    @Column(name = "cheque_date")
    @Temporal(TemporalType.DATE)
    private Date chequeDate;
    @Column(name = "is_valid")
    private Boolean isValid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @JoinColumn(name = "bank_accounts_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BankAccounts bankAccountsId;

    public Cheque() {
    }

    public Cheque(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public Date getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(Date chequeDate) {
        this.chequeDate = chequeDate;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BankAccounts getBankAccountsId() {
        return bankAccountsId;
    }

    public void setBankAccountsId(BankAccounts bankAccountsId) {
        this.bankAccountsId = bankAccountsId;
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
        if (!(object instanceof Cheque)) {
            return false;
        }
        Cheque other = (Cheque) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Cheque[ id=" + id + " ]";
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
    
}
