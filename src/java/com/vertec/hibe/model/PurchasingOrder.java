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
@Table(name = "purchasing_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PurchasingOrder.findAll", query = "SELECT p FROM PurchasingOrder p"),
    @NamedQuery(name = "PurchasingOrder.findByPoId", query = "SELECT p FROM PurchasingOrder p WHERE p.poId = :poId"),
    @NamedQuery(name = "PurchasingOrder.findByQty", query = "SELECT p FROM PurchasingOrder p WHERE p.qty = :qty"),
    @NamedQuery(name = "PurchasingOrder.findByPrice", query = "SELECT p FROM PurchasingOrder p WHERE p.price = :price"),
    @NamedQuery(name = "PurchasingOrder.findByTotal", query = "SELECT p FROM PurchasingOrder p WHERE p.total = :total"),
    @NamedQuery(name = "PurchasingOrder.findByAvailableQty", query = "SELECT p FROM PurchasingOrder p WHERE p.availableQty = :availableQty")})
public class PurchasingOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "po_id")
    private Integer poId;
    @Column(name = "qty")
    private Integer qty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @Column(name = "total")
    private Double total;
    @Column(name = "available_qty")
    private Integer availableQty;
    @JoinColumn(name = "po_info_po_info_id", referencedColumnName = "po_info_id")
    @ManyToOne(optional = false)
    private PoInfo poInfoPoInfoId;
    @JoinColumn(name = "product_product_id", referencedColumnName = "product_id")
    @ManyToOne(optional = false)
    private Product productProductId;

    public PurchasingOrder() {
    }

    public PurchasingOrder(Integer poId) {
        this.poId = poId;
    }

    public Integer getPoId() {
        return poId;
    }

    public void setPoId(Integer poId) {
        this.poId = poId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(Integer availableQty) {
        this.availableQty = availableQty;
    }

    public PoInfo getPoInfoPoInfoId() {
        return poInfoPoInfoId;
    }

    public void setPoInfoPoInfoId(PoInfo poInfoPoInfoId) {
        this.poInfoPoInfoId = poInfoPoInfoId;
    }

    public Product getProductProductId() {
        return productProductId;
    }

    public void setProductProductId(Product productProductId) {
        this.productProductId = productProductId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (poId != null ? poId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchasingOrder)) {
            return false;
        }
        PurchasingOrder other = (PurchasingOrder) object;
        if ((this.poId == null && other.poId != null) || (this.poId != null && !this.poId.equals(other.poId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.PurchasingOrder[ poId=" + poId + " ]";
    }
    
}
