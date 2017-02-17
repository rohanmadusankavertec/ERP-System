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
@Table(name = "product_master")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductMaster.findAll", query = "SELECT p FROM ProductMaster p"),
    @NamedQuery(name = "ProductMaster.findByProductMasterId", query = "SELECT p FROM ProductMaster p WHERE p.productMasterId = :productMasterId"),
    @NamedQuery(name = "ProductMaster.findByPurchasedPrice", query = "SELECT p FROM ProductMaster p WHERE p.purchasedPrice = :purchasedPrice"),
    @NamedQuery(name = "ProductMaster.findBySellingPrice", query = "SELECT p FROM ProductMaster p WHERE p.sellingPrice = :sellingPrice"),
    @NamedQuery(name = "ProductMaster.findByIsAvailable", query = "SELECT p FROM ProductMaster p WHERE p.isAvailable = :isAvailable")})
public class ProductMaster implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productMasterId")
    private Collection<DisposeItems> disposeItemsCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productMasterId")
    private Collection<ReturnToCustomer> returnToCustomerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productMasterId")
    private Collection<ReturnBySupplier> returnBySupplierCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productMasterId")
    private Collection<StockReturn> stockReturnCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productMasterId")
    private Collection<InvoiceItem> invoiceItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productMasterId")
    private Collection<ReturnStock> returnStockCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productMasterId")
    private Collection<Gtn> gtnCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productMasterId")
    private Collection<BranchProductmaster> branchProductmasterCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "product_master_id")
    private Integer productMasterId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "purchased_price")
    private Double purchasedPrice;
    @Column(name = "selling_price")
    private Double sellingPrice;
    @Column(name = "is_available")
    private Boolean isAvailable;
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne(optional = false)
    private Product productId;

    public ProductMaster() {
    }

    public ProductMaster(Integer productMasterId) {
        this.productMasterId = productMasterId;
    }

    public Integer getProductMasterId() {
        return productMasterId;
    }

    public void setProductMasterId(Integer productMasterId) {
        this.productMasterId = productMasterId;
    }

    public Double getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(Double purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productMasterId != null ? productMasterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductMaster)) {
            return false;
        }
        ProductMaster other = (ProductMaster) object;
        if ((this.productMasterId == null && other.productMasterId != null) || (this.productMasterId != null && !this.productMasterId.equals(other.productMasterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.ProductMaster[ productMasterId=" + productMasterId + " ]";
    }

    @XmlTransient
    public Collection<BranchProductmaster> getBranchProductmasterCollection() {
        return branchProductmasterCollection;
    }

    public void setBranchProductmasterCollection(Collection<BranchProductmaster> branchProductmasterCollection) {
        this.branchProductmasterCollection = branchProductmasterCollection;
    }

    @XmlTransient
    public Collection<Gtn> getGtnCollection() {
        return gtnCollection;
    }

    public void setGtnCollection(Collection<Gtn> gtnCollection) {
        this.gtnCollection = gtnCollection;
    }

    @XmlTransient
    public Collection<StockReturn> getStockReturnCollection() {
        return stockReturnCollection;
    }

    public void setStockReturnCollection(Collection<StockReturn> stockReturnCollection) {
        this.stockReturnCollection = stockReturnCollection;
    }

    @XmlTransient
    public Collection<InvoiceItem> getInvoiceItemCollection() {
        return invoiceItemCollection;
    }

    public void setInvoiceItemCollection(Collection<InvoiceItem> invoiceItemCollection) {
        this.invoiceItemCollection = invoiceItemCollection;
    }

    @XmlTransient
    public Collection<ReturnStock> getReturnStockCollection() {
        return returnStockCollection;
    }

    public void setReturnStockCollection(Collection<ReturnStock> returnStockCollection) {
        this.returnStockCollection = returnStockCollection;
    }

    @XmlTransient
    public Collection<ReturnToCustomer> getReturnToCustomerCollection() {
        return returnToCustomerCollection;
    }

    public void setReturnToCustomerCollection(Collection<ReturnToCustomer> returnToCustomerCollection) {
        this.returnToCustomerCollection = returnToCustomerCollection;
    }

    @XmlTransient
    public Collection<ReturnBySupplier> getReturnBySupplierCollection() {
        return returnBySupplierCollection;
    }

    public void setReturnBySupplierCollection(Collection<ReturnBySupplier> returnBySupplierCollection) {
        this.returnBySupplierCollection = returnBySupplierCollection;
    }

    @XmlTransient
    public Collection<DisposeItems> getDisposeItemsCollection() {
        return disposeItemsCollection;
    }

    public void setDisposeItemsCollection(Collection<DisposeItems> disposeItemsCollection) {
        this.disposeItemsCollection = disposeItemsCollection;
    }

}
