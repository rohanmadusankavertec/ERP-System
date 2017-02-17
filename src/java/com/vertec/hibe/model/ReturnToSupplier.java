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
@Table(name = "return_to_supplier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReturnToSupplier.findAll", query = "SELECT r FROM ReturnToSupplier r"),
    @NamedQuery(name = "ReturnToSupplier.findById", query = "SELECT r FROM ReturnToSupplier r WHERE r.id = :id"),
    @NamedQuery(name = "ReturnToSupplier.findByQty", query = "SELECT r FROM ReturnToSupplier r WHERE r.qty = :qty")})
public class ReturnToSupplier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "qty")
    private Integer qty;
    @JoinColumn(name = "return_stock_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ReturnStock returnStockId;
    @JoinColumn(name = "supplier_return_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SupplierReturn supplierReturnId;

    public ReturnToSupplier() {
    }

    public ReturnToSupplier(Integer id) {
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

    public ReturnStock getReturnStockId() {
        return returnStockId;
    }

    public void setReturnStockId(ReturnStock returnStockId) {
        this.returnStockId = returnStockId;
    }

    public SupplierReturn getSupplierReturnId() {
        return supplierReturnId;
    }

    public void setSupplierReturnId(SupplierReturn supplierReturnId) {
        this.supplierReturnId = supplierReturnId;
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
        if (!(object instanceof ReturnToSupplier)) {
            return false;
        }
        ReturnToSupplier other = (ReturnToSupplier) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.ReturnToSupplier[ id=" + id + " ]";
    }
    
}
