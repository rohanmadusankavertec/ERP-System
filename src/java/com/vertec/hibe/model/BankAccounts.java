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
@Table(name = "bank_accounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BankAccounts.findAll", query = "SELECT b FROM BankAccounts b"),
    @NamedQuery(name = "BankAccounts.findById", query = "SELECT b FROM BankAccounts b WHERE b.id = :id"),
    @NamedQuery(name = "BankAccounts.findByAccountNo", query = "SELECT b FROM BankAccounts b WHERE b.accountNo = :accountNo"),
    @NamedQuery(name = "BankAccounts.findByAccountType", query = "SELECT b FROM BankAccounts b WHERE b.accountType = :accountType"),
    @NamedQuery(name = "BankAccounts.findByAddedDate", query = "SELECT b FROM BankAccounts b WHERE b.addedDate = :addedDate"),
    @NamedQuery(name = "BankAccounts.findByLastUpdatedDate", query = "SELECT b FROM BankAccounts b WHERE b.lastUpdatedDate = :lastUpdatedDate"),
    @NamedQuery(name = "BankAccounts.findByAccountStatus", query = "SELECT b FROM BankAccounts b WHERE b.accountStatus = :accountStatus")})
public class BankAccounts implements Serializable {

    @OneToMany(mappedBy = "bankAccountsId")
    private Collection<SalaryPayment> salaryPaymentCollection;
    @OneToMany(mappedBy = "bankAccountsId")
    private Collection<Advance> advanceCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "account_no")
    private String accountNo;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "added_date")
    @Temporal(TemporalType.DATE)
    private Date addedDate;
    @Column(name = "last_updated_date")
    @Temporal(TemporalType.DATE)
    private Date lastUpdatedDate;
    @Column(name = "account_status")
    private Boolean accountStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankAccountsId")
    private Collection<Cheque> chequeCollection;
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Bank bankId;
    @JoinColumn(name = "added_by", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser addedBy;
    @JoinColumn(name = "last_updated_by", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser lastUpdatedBy;

    public BankAccounts() {
    }

    public BankAccounts(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Boolean getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    @XmlTransient
    public Collection<Cheque> getChequeCollection() {
        return chequeCollection;
    }

    public void setChequeCollection(Collection<Cheque> chequeCollection) {
        this.chequeCollection = chequeCollection;
    }

    public Bank getBankId() {
        return bankId;
    }

    public void setBankId(Bank bankId) {
        this.bankId = bankId;
    }

    public SysUser getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(SysUser addedBy) {
        this.addedBy = addedBy;
    }

    public SysUser getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(SysUser lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
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
        if (!(object instanceof BankAccounts)) {
            return false;
        }
        BankAccounts other = (BankAccounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.BankAccounts[ id=" + id + " ]";
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
