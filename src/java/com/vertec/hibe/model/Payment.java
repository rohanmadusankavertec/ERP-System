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
@Table(name = "payment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p"),
    @NamedQuery(name = "Payment.findByPaymentId", query = "SELECT p FROM Payment p WHERE p.paymentId = :paymentId"),
    @NamedQuery(name = "Payment.findByPaymentDate", query = "SELECT p FROM Payment p WHERE p.paymentDate = :paymentDate"),
    @NamedQuery(name = "Payment.findByAmount", query = "SELECT p FROM Payment p WHERE p.amount = :amount"),
    @NamedQuery(name = "Payment.findByChequeNo", query = "SELECT p FROM Payment p WHERE p.chequeNo = :chequeNo"),
    @NamedQuery(name = "Payment.findByBankName", query = "SELECT p FROM Payment p WHERE p.bankName = :bankName"),
    @NamedQuery(name = "Payment.findByIsCleared", query = "SELECT p FROM Payment p WHERE p.isCleared = :isCleared"),
    @NamedQuery(name = "Payment.findByChequeDate", query = "SELECT p FROM Payment p WHERE p.chequeDate = :chequeDate")})
public class Payment implements Serializable {

    @Column(name = "cc_expire_month")
    private Integer ccExpireMonth;

    @Column(name = "cc_number")
    private String ccNumber;
    @Column(name = "cc_expire_year")
    private Integer ccExpireYear;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentId")
    private Collection<GrnPayment> grnPaymentCollection;

    @Column(name = "is_cleared")
    private Boolean isCleared;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "payment_id")
    private Integer paymentId;
    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "cheque_no")
    private String chequeNo;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "cheque_date")
    private String chequeDate;
    @JoinColumn(name = "payment_type_id", referencedColumnName = "pt_id")
    @ManyToOne(optional = false)
    private PaymentType paymentTypeId;
    @JoinColumn(name = "sys_user_sysuser_id", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser sysUserSysuserId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentId")
    private Collection<InvoicePayment> invoicePaymentCollection;

    public Payment() {
    }

    public Payment(Integer paymentId) {
        this.paymentId = paymentId;
    }
public Payment(Integer paymentId, Date paymentDate, double amount) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    public Payment(Integer paymentId, double amount, PaymentType paymentTypeId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentTypeId = paymentTypeId;
    }

    public Payment(Date paymentDate, double amount, Boolean isCleared, SysUser sysUserSysuserId, PaymentType paymentTypeId) {
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.isCleared = isCleared;
        this.sysUserSysuserId = sysUserSysuserId;
        this.paymentTypeId = paymentTypeId;
    }

    public Payment(Date paymentDate, double amount, String chequeNo, String bankName, Boolean isCleared, SysUser sysUserSysuserId, PaymentType paymentTypeId,String dateck) {
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.chequeNo = chequeNo;
        this.bankName = bankName;
        this.isCleared = isCleared;
        this.sysUserSysuserId = sysUserSysuserId;
        this.paymentTypeId = paymentTypeId;
        this.chequeDate = dateck;
    }
    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public PaymentType getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(PaymentType paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public SysUser getSysUserSysuserId() {
        return sysUserSysuserId;
    }

    public void setSysUserSysuserId(SysUser sysUserSysuserId) {
        this.sysUserSysuserId = sysUserSysuserId;
    }

    @XmlTransient
    public Collection<InvoicePayment> getInvoicePaymentCollection() {
        return invoicePaymentCollection;
    }

    public void setInvoicePaymentCollection(Collection<InvoicePayment> invoicePaymentCollection) {
        this.invoicePaymentCollection = invoicePaymentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentId != null ? paymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.paymentId == null && other.paymentId != null) || (this.paymentId != null && !this.paymentId.equals(other.paymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Payment[ paymentId=" + paymentId + " ]";
    }

    public Boolean getIsCleared() {
        return isCleared;
    }

    public void setIsCleared(Boolean isCleared) {
        this.isCleared = isCleared;
    }

    @XmlTransient
    public Collection<GrnPayment> getGrnPaymentCollection() {
        return grnPaymentCollection;
    }

    public void setGrnPaymentCollection(Collection<GrnPayment> grnPaymentCollection) {
        this.grnPaymentCollection = grnPaymentCollection;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public Integer getCcExpireYear() {
        return ccExpireYear;
    }

    public void setCcExpireYear(Integer ccExpireYear) {
        this.ccExpireYear = ccExpireYear;
    }

    public Integer getCcExpireMonth() {
        return ccExpireMonth;
    }

    public void setCcExpireMonth(Integer ccExpireMonth) {
        this.ccExpireMonth = ccExpireMonth;
    }


}
