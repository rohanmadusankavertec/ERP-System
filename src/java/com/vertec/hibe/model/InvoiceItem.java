/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.hibe.model;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vertec-r
 */
@Entity
@Table(name = "invoice_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvoiceItem.findAll", query = "SELECT i FROM InvoiceItem i"),
    @NamedQuery(name = "InvoiceItem.findByInvoiceItemId", query = "SELECT i FROM InvoiceItem i WHERE i.invoiceItemId = :invoiceItemId"),
    @NamedQuery(name = "InvoiceItem.findByUnitPrice", query = "SELECT i FROM InvoiceItem i WHERE i.unitPrice = :unitPrice"),
    @NamedQuery(name = "InvoiceItem.findByQuantity", query = "SELECT i FROM InvoiceItem i WHERE i.quantity = :quantity"),
    @NamedQuery(name = "InvoiceItem.findByTotAmount", query = "SELECT i FROM InvoiceItem i WHERE i.totAmount = :totAmount"),
    @NamedQuery(name = "InvoiceItem.findByDiscount", query = "SELECT i FROM InvoiceItem i WHERE i.discount = :discount"),
    @NamedQuery(name = "InvoiceItem.findByTotAfterDis", query = "SELECT i FROM InvoiceItem i WHERE i.totAfterDis = :totAfterDis")})
public class InvoiceItem implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceItemId")
    private Collection<ReturnByCustomer> returnByCustomerCollection;

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "invoice_item_id")
    private Integer invoiceItemId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "unit_price")
    private Double unitPrice;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "tot_amount")
    private Double totAmount;
    @Column(name = "discount")
    private Double discount;
    @Column(name = "tot_after_dis")
    private Double totAfterDis;
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")
    @ManyToOne(optional = false)
    private Invoice invoiceId;
    @JoinColumn(name = "product_master_id", referencedColumnName = "product_master_id")
    @ManyToOne(optional = false)
    private ProductMaster productMasterId;

    public InvoiceItem() {
    }

    public InvoiceItem(Integer invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public Integer getInvoiceItemId() {
        return invoiceItemId;
    }
 public InvoiceItem(double unitPrice, int quantity, double totAmount, Double discount, double totAfterDis, ProductMaster productMasterId, Invoice invoiceId) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totAmount = totAmount;
        this.discount = discount;
        this.totAfterDis = totAfterDis;
        this.productMasterId = productMasterId;
        this.invoiceId = invoiceId;
    }

    public InvoiceItem(Integer invoiceItemId, int quantity, double totAmount, Double discount, double totAfterDis) {
        this.invoiceItemId = invoiceItemId;
        this.quantity = quantity;
        this.totAmount = totAmount;
        this.discount = discount;
        this.totAfterDis = totAfterDis;
    }

    public void setInvoiceItemId(Integer invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotAmount() {
        return totAmount;
    }

    public void setTotAmount(Double totAmount) {
        this.totAmount = totAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTotAfterDis() {
        return totAfterDis;
    }

    public void setTotAfterDis(Double totAfterDis) {
        this.totAfterDis = totAfterDis;
    }

    public Invoice getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Invoice invoiceId) {
        this.invoiceId = invoiceId;
    }

    public ProductMaster getProductMasterId() {
        return productMasterId;
    }

    public void setProductMasterId(ProductMaster productMasterId) {
        this.productMasterId = productMasterId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceItemId != null ? invoiceItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvoiceItem)) {
            return false;
        }
        InvoiceItem other = (InvoiceItem) object;
        if ((this.invoiceItemId == null && other.invoiceItemId != null) || (this.invoiceItemId != null && !this.invoiceItemId.equals(other.invoiceItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.InvoiceItem[ invoiceItemId=" + invoiceItemId + " ]";
    }

    @XmlTransient
    public Collection<ReturnByCustomer> getReturnByCustomerCollection() {
        return returnByCustomerCollection;
    }

    public void setReturnByCustomerCollection(Collection<ReturnByCustomer> returnByCustomerCollection) {
        this.returnByCustomerCollection = returnByCustomerCollection;
    }


    
}
