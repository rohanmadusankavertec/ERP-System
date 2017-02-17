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
@Table(name = "gtn")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gtn.findAll", query = "SELECT g FROM Gtn g"),
    @NamedQuery(name = "Gtn.findByGtnId", query = "SELECT g FROM Gtn g WHERE g.gtnId = :gtnId"),
    @NamedQuery(name = "Gtn.findByQty", query = "SELECT g FROM Gtn g WHERE g.qty = :qty"),
    @NamedQuery(name = "Gtn.findByTotal", query = "SELECT g FROM Gtn g WHERE g.total = :total")})
public class Gtn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "gtn_id")
    private Integer gtnId;
    @Column(name = "qty")
    private Integer qty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @JoinColumn(name = "product_master_id", referencedColumnName = "product_master_id")
    @ManyToOne(optional = false)
    private ProductMaster productMasterId;
    @JoinColumn(name = "gtninfo_id", referencedColumnName = "gtninfo_id")
    @ManyToOne(optional = false)
    private GtnInfo gtninfoId;

    public Gtn() {
    }

    public Gtn(Integer gtnId) {
        this.gtnId = gtnId;
    }

    public Integer getGtnId() {
        return gtnId;
    }

    public void setGtnId(Integer gtnId) {
        this.gtnId = gtnId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ProductMaster getProductMasterId() {
        return productMasterId;
    }

    public void setProductMasterId(ProductMaster productMasterId) {
        this.productMasterId = productMasterId;
    }

    public GtnInfo getGtninfoId() {
        return gtninfoId;
    }

    public void setGtninfoId(GtnInfo gtninfoId) {
        this.gtninfoId = gtninfoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gtnId != null ? gtnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gtn)) {
            return false;
        }
        Gtn other = (Gtn) object;
        if ((this.gtnId == null && other.gtnId != null) || (this.gtnId != null && !this.gtnId.equals(other.gtnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Gtn[ gtnId=" + gtnId + " ]";
    }
    
}
