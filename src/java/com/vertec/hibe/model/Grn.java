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
@Table(name = "grn")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grn.findAll", query = "SELECT g FROM Grn g"),
    @NamedQuery(name = "Grn.findByGrnId", query = "SELECT g FROM Grn g WHERE g.grnId = :grnId"),
    @NamedQuery(name = "Grn.findByQty", query = "SELECT g FROM Grn g WHERE g.qty = :qty"),
    @NamedQuery(name = "Grn.findByPPrice", query = "SELECT g FROM Grn g WHERE g.pPrice = :pPrice"),
    @NamedQuery(name = "Grn.findBySPrice", query = "SELECT g FROM Grn g WHERE g.sPrice = :sPrice"),
    @NamedQuery(name = "Grn.findByTotal", query = "SELECT g FROM Grn g WHERE g.total = :total")})
public class Grn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "grn_id")
    private Integer grnId;
    @Column(name = "qty")
    private Integer qty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "p_price")
    private Double pPrice;
    @Column(name = "s_price")
    private Double sPrice;
    @Column(name = "total")
    private Double total;
    @JoinColumn(name = "grn_info_grn_info_id", referencedColumnName = "grn_info_id")
    @ManyToOne(optional = false)
    private GrnInfo grnInfoGrnInfoId;
    @JoinColumn(name = "product_product_id", referencedColumnName = "product_id")
    @ManyToOne(optional = false)
    private Product productProductId;

    public Grn() {
    }

    public Grn(Integer grnId) {
        this.grnId = grnId;
    }

    public Integer getGrnId() {
        return grnId;
    }

    public void setGrnId(Integer grnId) {
        this.grnId = grnId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getPPrice() {
        return pPrice;
    }

    public void setPPrice(Double pPrice) {
        this.pPrice = pPrice;
    }

    public Double getSPrice() {
        return sPrice;
    }

    public void setSPrice(Double sPrice) {
        this.sPrice = sPrice;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public GrnInfo getGrnInfoGrnInfoId() {
        return grnInfoGrnInfoId;
    }

    public void setGrnInfoGrnInfoId(GrnInfo grnInfoGrnInfoId) {
        this.grnInfoGrnInfoId = grnInfoGrnInfoId;
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
        hash += (grnId != null ? grnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grn)) {
            return false;
        }
        Grn other = (Grn) object;
        if ((this.grnId == null && other.grnId != null) || (this.grnId != null && !this.grnId.equals(other.grnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Grn[ grnId=" + grnId + " ]";
    }
    
}
