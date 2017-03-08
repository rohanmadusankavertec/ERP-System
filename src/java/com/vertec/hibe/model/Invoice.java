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
@Table(name = "invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findByInvoiceId", query = "SELECT i FROM Invoice i WHERE i.invoiceId = :invoiceId"),
    @NamedQuery(name = "Invoice.findByInvoicedDate", query = "SELECT i FROM Invoice i WHERE i.invoicedDate = :invoicedDate"),
    @NamedQuery(name = "Invoice.findByInvoiceTotal", query = "SELECT i FROM Invoice i WHERE i.invoiceTotal = :invoiceTotal"),
    @NamedQuery(name = "Invoice.findByDiscount", query = "SELECT i FROM Invoice i WHERE i.discount = :discount"),
    @NamedQuery(name = "Invoice.findByTotAfterDiscount", query = "SELECT i FROM Invoice i WHERE i.totAfterDiscount = :totAfterDiscount"),
    @NamedQuery(name = "Invoice.findByIsValid", query = "SELECT i FROM Invoice i WHERE i.isValid = :isValid")})
public class Invoice implements Serializable {

    @Column(name = "tax")
    private Double tax;

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceId")
    private Collection<CustomerReturn> customerReturnCollection;

    @Column(name = "is_pending")
    private Boolean isPending;

    @Column(name = "is_valid")
    private Boolean isValid;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "invoice_total")
    private Double invoiceTotal;
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
    @ManyToOne(optional = false)
    private Branch branchId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "invoice_id")
    private Integer invoiceId;
    @Column(name = "invoiced_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoicedDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "discount")
    private Double discount;
    @Column(name = "tot_after_discount")
    private Double totAfterDiscount;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceId")
    private Collection<InvoiceItem> invoiceItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceId")
    private Collection<OutstandigInvoice> outstandigInvoiceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceId")
    private Collection<InvoicePayment> invoicePaymentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceInvoiceId")
    private Collection<DelInvoice> delInvoiceCollection;
    
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private Customer customerId;

    public Invoice() {
    }

    public Invoice(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public Invoice(Date invoicedDate, double invoiceTotal, Double discount, double totAfterDiscount, boolean isValid, Customer customerId) {
        this.invoicedDate = invoicedDate;
        this.invoiceTotal = invoiceTotal;
        this.discount = discount;
        this.totAfterDiscount = totAfterDiscount;
        this.isValid = isValid;
        this.customerId = customerId;
    }

    public Invoice(Integer invoiceId, double invoiceTotal, Double discount, double totAfterDiscount) {
        this.invoiceId = invoiceId;
        this.invoiceTotal = invoiceTotal;
        this.discount = discount;
        this.totAfterDiscount = totAfterDiscount;
    }


    public Invoice(Integer invoiceId, Date invoicedDate, double invoiceTotal, double totAfterDiscount, boolean isValid) {
        this.invoiceId = invoiceId;
        this.invoicedDate = invoicedDate;
        this.invoiceTotal = invoiceTotal;
        this.totAfterDiscount = totAfterDiscount;
        this.isValid = isValid;
    }

    public Invoice(Branch branchId, Date invoicedDate, double invoiceTotal, Double discount, double totAfterDiscount, boolean isValid,boolean isDelete, Customer customerId,String bn) {
        
        this.branchId = branchId;
        this.invoicedDate = invoicedDate;
        this.invoiceTotal = invoiceTotal;
        this.discount = discount;
        this.totAfterDiscount = totAfterDiscount;
        this.isValid = isValid;
        this.customerId = customerId;
    }
    public Invoice(Branch branchId, Date invoicedDate, double invoiceTotal, Double discount, double totAfterDiscount, boolean isValid,boolean isDelete, Customer customerId) {
        
        this.branchId = branchId;
        this.invoicedDate = invoicedDate;
        this.invoiceTotal = invoiceTotal;
        this.discount = discount;
        this.totAfterDiscount = totAfterDiscount;
        this.isValid = isValid;
        this.customerId = customerId;
    }
    
    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getInvoicedDate() {
        return invoicedDate;
    }

    public void setInvoicedDate(Date invoicedDate) {
        this.invoicedDate = invoicedDate;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTotAfterDiscount() {
        return totAfterDiscount;
    }

    public void setTotAfterDiscount(Double totAfterDiscount) {
        this.totAfterDiscount = totAfterDiscount;
    }

    @XmlTransient
    public Collection<InvoiceItem> getInvoiceItemCollection() {
        return invoiceItemCollection;
    }

    public void setInvoiceItemCollection(Collection<InvoiceItem> invoiceItemCollection) {
        this.invoiceItemCollection = invoiceItemCollection;
    }

    @XmlTransient
    public Collection<OutstandigInvoice> getOutstandigInvoiceCollection() {
        return outstandigInvoiceCollection;
    }

    public void setOutstandigInvoiceCollection(Collection<OutstandigInvoice> outstandigInvoiceCollection) {
        this.outstandigInvoiceCollection = outstandigInvoiceCollection;
    }

    @XmlTransient
    public Collection<InvoicePayment> getInvoicePaymentCollection() {
        return invoicePaymentCollection;
    }

    public void setInvoicePaymentCollection(Collection<InvoicePayment> invoicePaymentCollection) {
        this.invoicePaymentCollection = invoicePaymentCollection;
    }

    @XmlTransient
    public Collection<DelInvoice> getDelInvoiceCollection() {
        return delInvoiceCollection;
    }

    public void setDelInvoiceCollection(Collection<DelInvoice> delInvoiceCollection) {
        this.delInvoiceCollection = delInvoiceCollection;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceId != null ? invoiceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.invoiceId == null && other.invoiceId != null) || (this.invoiceId != null && !this.invoiceId.equals(other.invoiceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Invoice[ invoiceId=" + invoiceId + " ]";
    }

    public Double getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public Branch getBranchId() {
        return branchId;
    }

    public void setBranchId(Branch branchId) {
        this.branchId = branchId;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Boolean getIsPending() {
        return isPending;
    }

    public void setIsPending(Boolean isPending) {
        this.isPending = isPending;
    }

    @XmlTransient
    public Collection<CustomerReturn> getCustomerReturnCollection() {
        return customerReturnCollection;
    }

    public void setCustomerReturnCollection(Collection<CustomerReturn> customerReturnCollection) {
        this.customerReturnCollection = customerReturnCollection;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

}
