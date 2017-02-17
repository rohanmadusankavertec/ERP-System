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
@Table(name = "return_stock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReturnStock.findAll", query = "SELECT r FROM ReturnStock r"),
    @NamedQuery(name = "ReturnStock.findById", query = "SELECT r FROM ReturnStock r WHERE r.id = :id"),
    @NamedQuery(name = "ReturnStock.findByQty", query = "SELECT r FROM ReturnStock r WHERE r.qty = :qty")})
public class ReturnStock implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "returnStockId")
    private Collection<ReturnToSupplier> returnToSupplierCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "qty")
    private Integer qty;
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
    @ManyToOne(optional = false)
    private Branch branchId;
    @JoinColumn(name = "product_master_id", referencedColumnName = "product_master_id")
    @ManyToOne(optional = false)
    private ProductMaster productMasterId;

    public ReturnStock() {
    }

    public ReturnStock(Integer id) {
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

    public Branch getBranchId() {
        return branchId;
    }

    public void setBranchId(Branch branchId) {
        this.branchId = branchId;
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
        if (!(object instanceof ReturnStock)) {
            return false;
        }
        ReturnStock other = (ReturnStock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.ReturnStock[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<ReturnToSupplier> getReturnToSupplierCollection() {
        return returnToSupplierCollection;
    }

    public void setReturnToSupplierCollection(Collection<ReturnToSupplier> returnToSupplierCollection) {
        this.returnToSupplierCollection = returnToSupplierCollection;
    }

}
