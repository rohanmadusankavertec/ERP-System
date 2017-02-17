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
@Table(name = "branch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Branch.findAll", query = "SELECT b FROM Branch b"),
    @NamedQuery(name = "Branch.findByBranchId", query = "SELECT b FROM Branch b WHERE b.branchId = :branchId"),
    @NamedQuery(name = "Branch.findByBranchName", query = "SELECT b FROM Branch b WHERE b.branchName = :branchName"),
    @NamedQuery(name = "Branch.findByContactNo", query = "SELECT b FROM Branch b WHERE b.contactNo = :contactNo"),
    @NamedQuery(name = "Branch.findByAddress", query = "SELECT b FROM Branch b WHERE b.address = :address")})
public class Branch implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId")
    private Collection<SupplierReturn> supplierReturnCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId")
    private Collection<DisposeItems> disposeItemsCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId")
    private Collection<CustomerReturn> customerReturnCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId")
    private Collection<StockReturn> stockReturnCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId")
    private Collection<ReturnStock> returnStockCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId")
    private Collection<Invoice> invoiceCollection;

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fromBranch")
    private Collection<GtnInfo> gtnInfoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toBranch")
    private Collection<GtnInfo> gtnInfoCollection1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "branch_id")
    private Integer branchId;
    @Column(name = "branch_name")
    private String branchName;
    @Column(name = "contact_no")
    private String contactNo;
    @Column(name = "address")
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId")
    private Collection<BranchProductmaster> branchProductmasterCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId")
    private Collection<BranchStock> branchStockCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchBranchId")
    private Collection<SysUser> sysUserCollection;

    public Branch() {
    }

    public Branch(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlTransient
    public Collection<BranchProductmaster> getBranchProductmasterCollection() {
        return branchProductmasterCollection;
    }

    public void setBranchProductmasterCollection(Collection<BranchProductmaster> branchProductmasterCollection) {
        this.branchProductmasterCollection = branchProductmasterCollection;
    }

    @XmlTransient
    public Collection<BranchStock> getBranchStockCollection() {
        return branchStockCollection;
    }

    public void setBranchStockCollection(Collection<BranchStock> branchStockCollection) {
        this.branchStockCollection = branchStockCollection;
    }

    @XmlTransient
    public Collection<SysUser> getSysUserCollection() {
        return sysUserCollection;
    }

    public void setSysUserCollection(Collection<SysUser> sysUserCollection) {
        this.sysUserCollection = sysUserCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (branchId != null ? branchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Branch)) {
            return false;
        }
        Branch other = (Branch) object;
        if ((this.branchId == null && other.branchId != null) || (this.branchId != null && !this.branchId.equals(other.branchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Branch[ branchId=" + branchId + " ]";
    }

    @XmlTransient
    public Collection<GtnInfo> getGtnInfoCollection() {
        return gtnInfoCollection;
    }

    public void setGtnInfoCollection(Collection<GtnInfo> gtnInfoCollection) {
        this.gtnInfoCollection = gtnInfoCollection;
    }

    @XmlTransient
    public Collection<GtnInfo> getGtnInfoCollection1() {
        return gtnInfoCollection1;
    }

    public void setGtnInfoCollection1(Collection<GtnInfo> gtnInfoCollection1) {
        this.gtnInfoCollection1 = gtnInfoCollection1;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    @XmlTransient
    public Collection<Invoice> getInvoiceCollection() {
        return invoiceCollection;
    }

    public void setInvoiceCollection(Collection<Invoice> invoiceCollection) {
        this.invoiceCollection = invoiceCollection;
    }

    @XmlTransient
    public Collection<ReturnStock> getReturnStockCollection() {
        return returnStockCollection;
    }

    public void setReturnStockCollection(Collection<ReturnStock> returnStockCollection) {
        this.returnStockCollection = returnStockCollection;
    }

    @XmlTransient
    public Collection<StockReturn> getStockReturnCollection() {
        return stockReturnCollection;
    }

    public void setStockReturnCollection(Collection<StockReturn> stockReturnCollection) {
        this.stockReturnCollection = stockReturnCollection;
    }

    @XmlTransient
    public Collection<CustomerReturn> getCustomerReturnCollection() {
        return customerReturnCollection;
    }

    public void setCustomerReturnCollection(Collection<CustomerReturn> customerReturnCollection) {
        this.customerReturnCollection = customerReturnCollection;
    }

    @XmlTransient
    public Collection<DisposeItems> getDisposeItemsCollection() {
        return disposeItemsCollection;
    }

    public void setDisposeItemsCollection(Collection<DisposeItems> disposeItemsCollection) {
        this.disposeItemsCollection = disposeItemsCollection;
    }

    @XmlTransient
    public Collection<SupplierReturn> getSupplierReturnCollection() {
        return supplierReturnCollection;
    }

    public void setSupplierReturnCollection(Collection<SupplierReturn> supplierReturnCollection) {
        this.supplierReturnCollection = supplierReturnCollection;
    }

    
}
