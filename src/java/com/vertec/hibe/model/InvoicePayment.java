/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.hibe.model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vertec-r
 */
@Entity
@Table(name = "invoice_payment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvoicePayment.findAll", query = "SELECT i FROM InvoicePayment i"),
    @NamedQuery(name = "InvoicePayment.findByInvoicePaymentId", query = "SELECT i FROM InvoicePayment i WHERE i.invoicePaymentId = :invoicePaymentId"),
    @NamedQuery(name = "InvoicePayment.findByBalanceLast", query = "SELECT i FROM InvoicePayment i WHERE i.balanceLast = :balanceLast")})
public class InvoicePayment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "invoice_payment_id")
    private Integer invoicePaymentId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "balance_last")
    private Double balanceLast;
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")
    @ManyToOne(optional = false)
    private Invoice invoiceId;
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    @ManyToOne(optional = false)
    private Payment paymentId;

    public InvoicePayment() {
    }

    public InvoicePayment(Integer invoicePaymentId) {
        this.invoicePaymentId = invoicePaymentId;
    }

    public Integer getInvoicePaymentId() {
        return invoicePaymentId;
    }

    public InvoicePayment(double balanceLast, Invoice invoiceId, Payment paymentId) {
        this.balanceLast = balanceLast;
        this.invoiceId = invoiceId;
        this.paymentId = paymentId;
    }


    public InvoicePayment(Integer invoicePaymentId, double balanceLast) {
        this.invoicePaymentId = invoicePaymentId;
        this.balanceLast = balanceLast;
    }

    public void setInvoicePaymentId(Integer invoicePaymentId) {
        this.invoicePaymentId = invoicePaymentId;
    }

    public Double getBalanceLast() {
        return balanceLast;
    }

    public void setBalanceLast(Double balanceLast) {
        this.balanceLast = balanceLast;
    }

    public Invoice getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Invoice invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Payment getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Payment paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoicePaymentId != null ? invoicePaymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvoicePayment)) {
            return false;
        }
        InvoicePayment other = (InvoicePayment) object;
        if ((this.invoicePaymentId == null && other.invoicePaymentId != null) || (this.invoicePaymentId != null && !this.invoicePaymentId.equals(other.invoicePaymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.InvoicePayment[ invoicePaymentId=" + invoicePaymentId + " ]";
    }

}
