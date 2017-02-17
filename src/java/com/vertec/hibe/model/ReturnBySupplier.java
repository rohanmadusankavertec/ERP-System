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
@Table(name = "return_by_supplier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReturnBySupplier.findAll", query = "SELECT r FROM ReturnBySupplier r"),
    @NamedQuery(name = "ReturnBySupplier.findById", query = "SELECT r FROM ReturnBySupplier r WHERE r.id = :id"),
    @NamedQuery(name = "ReturnBySupplier.findByQty", query = "SELECT r FROM ReturnBySupplier r WHERE r.qty = :qty")})
public class ReturnBySupplier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "qty")
    private Integer qty;
    @JoinColumn(name = "supplier_return_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SupplierReturn supplierReturnId;
    @JoinColumn(name = "product_master_id", referencedColumnName = "product_master_id")
    @ManyToOne(optional = false)
    private ProductMaster productMasterId;

    public ReturnBySupplier() {
    }

    public ReturnBySupplier(Integer id) {
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

    public SupplierReturn getSupplierReturnId() {
        return supplierReturnId;
    }

    public void setSupplierReturnId(SupplierReturn supplierReturnId) {
        this.supplierReturnId = supplierReturnId;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReturnBySupplier)) {
            return false;
        }
        ReturnBySupplier other = (ReturnBySupplier) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.ReturnBySupplier[ id=" + id + " ]";
    }
    
}
