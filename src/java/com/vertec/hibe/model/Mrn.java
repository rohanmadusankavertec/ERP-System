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
@Table(name = "mrn")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mrn.findAll", query = "SELECT m FROM Mrn m"),
    @NamedQuery(name = "Mrn.findById", query = "SELECT m FROM Mrn m WHERE m.id = :id"),
    @NamedQuery(name = "Mrn.findByQty", query = "SELECT m FROM Mrn m WHERE m.qty = :qty"),
    @NamedQuery(name = "Mrn.findByAvailableQty", query = "SELECT m FROM Mrn m WHERE m.availableQty = :availableQty")})
public class Mrn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "qty")
    private Integer qty;
    @Column(name = "available_qty")
    private Integer availableQty;
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne(optional = false)
    private Product productId;
    @JoinColumn(name = "mrn_info_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MrnInfo mrnInfoId;

    public Mrn() {
    }

    public Mrn(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(Integer availableQty) {
        this.availableQty = availableQty;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public MrnInfo getMrnInfoId() {
        return mrnInfoId;
    }

    public void setMrnInfoId(MrnInfo mrnInfoId) {
        this.mrnInfoId = mrnInfoId;
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
        if (!(object instanceof Mrn)) {
            return false;
        }
        Mrn other = (Mrn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Mrn[ id=" + id + " ]";
    }
    
}
