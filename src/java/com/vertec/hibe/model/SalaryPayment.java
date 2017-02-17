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
@Table(name = "salary_payment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SalaryPayment.findAll", query = "SELECT s FROM SalaryPayment s"),
    @NamedQuery(name = "SalaryPayment.findById", query = "SELECT s FROM SalaryPayment s WHERE s.id = :id"),
    @NamedQuery(name = "SalaryPayment.findByPayment", query = "SELECT s FROM SalaryPayment s WHERE s.payment = :payment"),
    @NamedQuery(name = "SalaryPayment.findByDate", query = "SELECT s FROM SalaryPayment s WHERE s.date = :date"),
    @NamedQuery(name = "SalaryPayment.findByIsValid", query = "SELECT s FROM SalaryPayment s WHERE s.isValid = :isValid")})
public class SalaryPayment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "payment")
    private Double payment;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "is_valid")
    private Boolean isValid;
    @JoinColumn(name = "salary_payment_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SalaryPaymentType salaryPaymentTypeId;
    @JoinColumn(name = "salary_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Salary salaryId;
    @JoinColumn(name = "cheque_id", referencedColumnName = "id")
    @ManyToOne
    private Cheque chequeId;
    @JoinColumn(name = "bank_accounts_id", referencedColumnName = "id")
    @ManyToOne
    private BankAccounts bankAccountsId;

    public SalaryPayment() {
    }

    public SalaryPayment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public SalaryPaymentType getSalaryPaymentTypeId() {
        return salaryPaymentTypeId;
    }

    public void setSalaryPaymentTypeId(SalaryPaymentType salaryPaymentTypeId) {
        this.salaryPaymentTypeId = salaryPaymentTypeId;
    }

    public Salary getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Salary salaryId) {
        this.salaryId = salaryId;
    }

    public Cheque getChequeId() {
        return chequeId;
    }

    public void setChequeId(Cheque chequeId) {
        this.chequeId = chequeId;
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
        if (!(object instanceof SalaryPayment)) {
            return false;
        }
        SalaryPayment other = (SalaryPayment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.SalaryPayment[ id=" + id + " ]";
    }
    
}
