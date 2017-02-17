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
@Table(name = "advance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Advance.findAll", query = "SELECT a FROM Advance a"),
    @NamedQuery(name = "Advance.findById", query = "SELECT a FROM Advance a WHERE a.id = :id"),
    @NamedQuery(name = "Advance.findByAmount", query = "SELECT a FROM Advance a WHERE a.amount = :amount"),
    @NamedQuery(name = "Advance.findByDate", query = "SELECT a FROM Advance a WHERE a.date = :date"),
    @NamedQuery(name = "Advance.findByPaid", query = "SELECT a FROM Advance a WHERE a.paid = :paid"),
    @NamedQuery(name = "Advance.findByDue", query = "SELECT a FROM Advance a WHERE a.due = :due"),
    @NamedQuery(name = "Advance.findByIsValid", query = "SELECT a FROM Advance a WHERE a.isValid = :isValid")})
public class Advance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "paid")
    private Double paid;
    @Column(name = "due")
    private Double due;
    @Column(name = "is_valid")
    private Boolean isValid;
    @JoinColumn(name = "salary_payment_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SalaryPaymentType salaryPaymentTypeId;
    @JoinColumn(name = "bank_accounts_id", referencedColumnName = "id")
    @ManyToOne
    private BankAccounts bankAccountsId;
    @JoinColumn(name = "cheque_id", referencedColumnName = "id")
    @ManyToOne
    private Cheque chequeId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employee employeeId;

    public Advance() {
    }

    public Advance(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Double getDue() {
        return due;
    }

    public void setDue(Double due) {
        this.due = due;
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

    public BankAccounts getBankAccountsId() {
        return bankAccountsId;
    }

    public void setBankAccountsId(BankAccounts bankAccountsId) {
        this.bankAccountsId = bankAccountsId;
    }

    public Cheque getChequeId() {
        return chequeId;
    }

    public void setChequeId(Cheque chequeId) {
        this.chequeId = chequeId;
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
        if (!(object instanceof Advance)) {
            return false;
        }
        Advance other = (Advance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Advance[ id=" + id + " ]";
    }
    
}
