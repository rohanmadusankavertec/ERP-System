/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.hibe.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vertec-r
 */
@Entity
@Table(name = "branch_stock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BranchStock.findAll", query = "SELECT b FROM BranchStock b"),
    @NamedQuery(name = "BranchStock.findByBranchStockId", query = "SELECT b FROM BranchStock b WHERE b.branchStockId = :branchStockId"),
    @NamedQuery(name = "BranchStock.findByQuantity", query = "SELECT b FROM BranchStock b WHERE b.quantity = :quantity"),
    @NamedQuery(name = "BranchStock.findByFirstAddedDate", query = "SELECT b FROM BranchStock b WHERE b.firstAddedDate = :firstAddedDate"),
    @NamedQuery(name = "BranchStock.findByLastUpdated", query = "SELECT b FROM BranchStock b WHERE b.lastUpdated = :lastUpdated")})
public class BranchStock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "branch_stock_id")
    private Integer branchStockId;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "first_added_date")
    @Temporal(TemporalType.DATE)
    private Date firstAddedDate;
    @Column(name = "last_updated")
    @Temporal(TemporalType.DATE)
    private Date lastUpdated;
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
    @ManyToOne(optional = false)
    private Branch branchId;
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne(optional = false)
    private Product productId;
    @JoinColumn(name = "added_by", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser addedBy;
    @JoinColumn(name = "updated_by", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser updatedBy;

    public BranchStock() {
    }

    public BranchStock(Integer branchStockId) {
        this.branchStockId = branchStockId;
    }

    public Integer getBranchStockId() {
        return branchStockId;
    }
    
    public BranchStock(Integer branchStockId, Integer quantity, Date lastUpdated, SysUser updatedBy) {
        this.branchStockId = branchStockId;
        this.quantity = quantity;
        this.lastUpdated = lastUpdated;
        this.updatedBy = updatedBy;
    }

    public BranchStock(Integer quantity, Date firstAddedDate, Date lastUpdated, SysUser updatedBy, SysUser addedBy, Product productId, Branch branchId) {
        this.quantity = quantity;
        this.firstAddedDate = firstAddedDate;
        this.lastUpdated = lastUpdated;
        this.updatedBy = updatedBy;
        this.addedBy = addedBy;
        this.productId = productId;
        this.branchId = branchId;
    }

    public void setBranchStockId(Integer branchStockId) {
        this.branchStockId = branchStockId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getFirstAddedDate() {
        return firstAddedDate;
    }

    public void setFirstAddedDate(Date firstAddedDate) {
        this.firstAddedDate = firstAddedDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Branch getBranchId() {
        return branchId;
    }

    public void setBranchId(Branch branchId) {
        this.branchId = branchId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public SysUser getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(SysUser addedBy) {
        this.addedBy = addedBy;
    }

    public SysUser getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(SysUser updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (branchStockId != null ? branchStockId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BranchStock)) {
            return false;
        }
        BranchStock other = (BranchStock) object;
        if ((this.branchStockId == null && other.branchStockId != null) || (this.branchStockId != null && !this.branchStockId.equals(other.branchStockId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.BranchStock[ branchStockId=" + branchStockId + " ]";
    }
    
}
