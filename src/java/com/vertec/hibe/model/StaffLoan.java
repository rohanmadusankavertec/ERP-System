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
@Table(name = "staff_loan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StaffLoan.findAll", query = "SELECT s FROM StaffLoan s"),
    @NamedQuery(name = "StaffLoan.findById", query = "SELECT s FROM StaffLoan s WHERE s.id = :id"),
    @NamedQuery(name = "StaffLoan.findByAmount", query = "SELECT s FROM StaffLoan s WHERE s.amount = :amount"),
    @NamedQuery(name = "StaffLoan.findByTerm", query = "SELECT s FROM StaffLoan s WHERE s.term = :term"),
    @NamedQuery(name = "StaffLoan.findByInterest", query = "SELECT s FROM StaffLoan s WHERE s.interest = :interest"),
    @NamedQuery(name = "StaffLoan.findByMonthlyPayment", query = "SELECT s FROM StaffLoan s WHERE s.monthlyPayment = :monthlyPayment"),
    @NamedQuery(name = "StaffLoan.findByDate", query = "SELECT s FROM StaffLoan s WHERE s.date = :date"),
    @NamedQuery(name = "StaffLoan.findByTotal", query = "SELECT s FROM StaffLoan s WHERE s.total = :total"),
    @NamedQuery(name = "StaffLoan.findByPaid", query = "SELECT s FROM StaffLoan s WHERE s.paid = :paid"),
    @NamedQuery(name = "StaffLoan.findByBalance", query = "SELECT s FROM StaffLoan s WHERE s.balance = :balance"),
    @NamedQuery(name = "StaffLoan.findByInterestType", query = "SELECT s FROM StaffLoan s WHERE s.interestType = :interestType"),
    @NamedQuery(name = "StaffLoan.findByInterestTerm", query = "SELECT s FROM StaffLoan s WHERE s.interestTerm = :interestTerm"),
    @NamedQuery(name = "StaffLoan.findByPaidTerm", query = "SELECT s FROM StaffLoan s WHERE s.paidTerm = :paidTerm")})
public class StaffLoan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "term")
    private Integer term;
    @Column(name = "interest")
    private Double interest;
    @Column(name = "monthly_payment")
    private Double monthlyPayment;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "total")
    private Double total;
    @Column(name = "paid")
    private Double paid;
    @Column(name = "balance")
    private Double balance;
    @Column(name = "interest_type")
    private Integer interestType;
    @Column(name = "interest_term")
    private Boolean interestTerm;
    @Column(name = "paid_term")
    private Integer paidTerm;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employee employeeId;
    @JoinColumn(name = "sys_user_sysuser_id", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser sysUserSysuserId;

    public StaffLoan() {
    }

    public StaffLoan(Integer id) {
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

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getInterestType() {
        return interestType;
    }

    public void setInterestType(Integer interestType) {
        this.interestType = interestType;
    }

    public Boolean getInterestTerm() {
        return interestTerm;
    }

    public void setInterestTerm(Boolean interestTerm) {
        this.interestTerm = interestTerm;
    }

    public Integer getPaidTerm() {
        return paidTerm;
    }

    public void setPaidTerm(Integer paidTerm) {
        this.paidTerm = paidTerm;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public SysUser getSysUserSysuserId() {
        return sysUserSysuserId;
    }

    public void setSysUserSysuserId(SysUser sysUserSysuserId) {
        this.sysUserSysuserId = sysUserSysuserId;
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
        if (!(object instanceof StaffLoan)) {
            return false;
        }
        StaffLoan other = (StaffLoan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.StaffLoan[ id=" + id + " ]";
    }
    
}
