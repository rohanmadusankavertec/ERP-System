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
 * @author Rohan
 */
@Entity
@Table(name = "prn_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrnItem.findAll", query = "SELECT p FROM PrnItem p"),
    @NamedQuery(name = "PrnItem.findByPrnId", query = "SELECT p FROM PrnItem p WHERE p.prnId = :prnId"),
    @NamedQuery(name = "PrnItem.findByQty", query = "SELECT p FROM PrnItem p WHERE p.qty = :qty")})
public class PrnItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prn_id")
    private Integer prnId;
    @Column(name = "qty")
    private Integer qty;
    @JoinColumn(name = "prn_info_id", referencedColumnName = "prn_info_id")
    @ManyToOne(optional = false)
    private PrnInfo prnInfoId;
    @JoinColumn(name = "product_product_id", referencedColumnName = "product_id")
    @ManyToOne(optional = false)
    private Product productProductId;

    public PrnItem() {
    }

    public PrnItem(Integer prnId) {
        this.prnId = prnId;
    }

    public Integer getPrnId() {
        return prnId;
    }

    public void setPrnId(Integer prnId) {
        this.prnId = prnId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public PrnInfo getPrnInfoId() {
        return prnInfoId;
    }

    public void setPrnInfoId(PrnInfo prnInfoId) {
        this.prnInfoId = prnInfoId;
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
        hash += (prnId != null ? prnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrnItem)) {
            return false;
        }
        PrnItem other = (PrnItem) object;
        if ((this.prnId == null && other.prnId != null) || (this.prnId != null && !this.prnId.equals(other.prnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.PrnItem[ prnId=" + prnId + " ]";
    }
    
}
