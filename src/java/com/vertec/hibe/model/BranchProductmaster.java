/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.hibe.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vertec-r
 */
@Entity
@Table(name = "branch_productmaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BranchProductmaster.findAll", query = "SELECT b FROM BranchProductmaster b"),
    @NamedQuery(name = "BranchProductmaster.findByBpmId", query = "SELECT b FROM BranchProductmaster b WHERE b.bpmId = :bpmId"),
    @NamedQuery(name = "BranchProductmaster.findByQuantity", query = "SELECT b FROM BranchProductmaster b WHERE b.quantity = :quantity"),
    @NamedQuery(name = "BranchProductmaster.findByLastUpdatedDate", query = "SELECT b FROM BranchProductmaster b WHERE b.lastUpdatedDate = :lastUpdatedDate"),
    @NamedQuery(name = "BranchProductmaster.findByType", query = "SELECT b FROM BranchProductmaster b WHERE b.type = :type")})
public class BranchProductmaster implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bpmId")
    private Collection<Gin> ginCollection;

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bpm_id")
    private Integer bpmId;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "last_updated_date")
    @Temporal(TemporalType.DATE)
    private Date lastUpdatedDate;
    @Column(name = "type")
    private Boolean type;
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
    @ManyToOne(optional = false)
    private Branch branchId;
    @JoinColumn(name = "product_master_id", referencedColumnName = "product_master_id")
    @ManyToOne(optional = false)
    private ProductMaster productMasterId;

    public BranchProductmaster() {
    }

    public BranchProductmaster(Integer bpmId) {
        this.bpmId = bpmId;
    }

    public Integer getBpmId() {
        return bpmId;
    }

    public BranchProductmaster(Integer quantity, Date lastUpdatedDate, Branch branchId, ProductMaster productMasterId) {
        this.quantity = quantity;
        this.lastUpdatedDate = lastUpdatedDate;
        this.branchId = branchId;
        this.productMasterId = productMasterId;
    }

    public BranchProductmaster(Integer bpmId, Integer quantity, Date lastUpdatedDate, Branch branchId, ProductMaster productMasterId) {
        this.bpmId = bpmId;
        this.quantity = quantity;
        this.lastUpdatedDate = lastUpdatedDate;
        this.branchId = branchId;
        this.productMasterId = productMasterId;
    }

    public BranchProductmaster(Integer bpmId, Integer quantity, Date lastUpdatedDate) {
        this.bpmId = bpmId;
        this.quantity = quantity;
        this.lastUpdatedDate = lastUpdatedDate;
    }
    public void setBpmId(Integer bpmId) {
        this.bpmId = bpmId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
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
        hash += (bpmId != null ? bpmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BranchProductmaster)) {
            return false;
        }
        BranchProductmaster other = (BranchProductmaster) object;
        if ((this.bpmId == null && other.bpmId != null) || (this.bpmId != null && !this.bpmId.equals(other.bpmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.BranchProductmaster[ bpmId=" + bpmId + " ]";
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    @XmlTransient
    public Collection<Gin> getGinCollection() {
        return ginCollection;
    }

    public void setGinCollection(Collection<Gin> ginCollection) {
        this.ginCollection = ginCollection;
    }
    
}
