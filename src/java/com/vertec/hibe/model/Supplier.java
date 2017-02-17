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
@Table(name = "supplier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Supplier.findAll", query = "SELECT s FROM Supplier s"),
    @NamedQuery(name = "Supplier.findBySupplierId", query = "SELECT s FROM Supplier s WHERE s.supplierId = :supplierId"),
    @NamedQuery(name = "Supplier.findBySupplierName", query = "SELECT s FROM Supplier s WHERE s.supplierName = :supplierName"),
    @NamedQuery(name = "Supplier.findByCompanyName", query = "SELECT s FROM Supplier s WHERE s.companyName = :companyName"),
    @NamedQuery(name = "Supplier.findByAddress", query = "SELECT s FROM Supplier s WHERE s.address = :address"),
    @NamedQuery(name = "Supplier.findByFax", query = "SELECT s FROM Supplier s WHERE s.fax = :fax"),
    @NamedQuery(name = "Supplier.findByLand", query = "SELECT s FROM Supplier s WHERE s.land = :land"),
    @NamedQuery(name = "Supplier.findByMobile", query = "SELECT s FROM Supplier s WHERE s.mobile = :mobile"),
    @NamedQuery(name = "Supplier.findByEmail", query = "SELECT s FROM Supplier s WHERE s.email = :email"),
    @NamedQuery(name = "Supplier.findByRegisteredDate", query = "SELECT s FROM Supplier s WHERE s.registeredDate = :registeredDate"),
    @NamedQuery(name = "Supplier.findByIsActive", query = "SELECT s FROM Supplier s WHERE s.isActive = :isActive"),
    @NamedQuery(name = "Supplier.findByType", query = "SELECT s FROM Supplier s WHERE s.type = :type")})
public class Supplier implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierId")
    private Collection<SupplierHasGroup> supplierHasGroupCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierId")
    private Collection<SupplierReturn> supplierReturnCollection;

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "supplier_id")
    private Integer supplierId;
    @Column(name = "supplier_name")
    private String supplierName;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "address")
    private String address;
    @Column(name = "fax")
    private String fax;
    @Column(name = "land")
    private String land;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "email")
    private String email;
    @Column(name = "registered_date")
    @Temporal(TemporalType.DATE)
    private Date registeredDate;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "type")
    private Boolean type;
    @JoinColumn(name = "sys_user_sysuser_id", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser sysUserSysuserId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierSupplierId")
    private Collection<GrnInfo> grnInfoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierSupplierId")
    private Collection<PoInfo> poInfoCollection;

    public Supplier() {
    }

    public Supplier(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }
public Supplier(String suplierName, String Company, String address, String fax, String land, String mobile, String email, Date date, boolean isActive, SysUser registeredBy,boolean type,Company com) {
        this.supplierName = suplierName;
        this.companyName = Company;
        this.address = address;
        this.fax = fax;
        this.land = land;
        this.mobile = mobile;
        this.email = email;
        this.registeredDate = date;
        this.sysUserSysuserId=registeredBy;
        this.isActive = isActive;
        this.type=type;
        this.companyId=com;
    }

    public Supplier(Integer id, String suplierName, String Company, String address, String fax, String land, String mobile, String email, boolean isActive, SysUser registeredBy,boolean type) {
        this.supplierId = id;
        this.supplierName = suplierName;
        this.companyName = Company;
        this.address = address;
        this.fax = fax;
        this.land = land;
        this.mobile = mobile;
        this.email = email;
        this.sysUserSysuserId=registeredBy;
        this.isActive = isActive;
        this.type=type;
    }
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public SysUser getSysUserSysuserId() {
        return sysUserSysuserId;
    }

    public void setSysUserSysuserId(SysUser sysUserSysuserId) {
        this.sysUserSysuserId = sysUserSysuserId;
    }

    @XmlTransient
    public Collection<GrnInfo> getGrnInfoCollection() {
        return grnInfoCollection;
    }

    public void setGrnInfoCollection(Collection<GrnInfo> grnInfoCollection) {
        this.grnInfoCollection = grnInfoCollection;
    }

    @XmlTransient
    public Collection<PoInfo> getPoInfoCollection() {
        return poInfoCollection;
    }

    public void setPoInfoCollection(Collection<PoInfo> poInfoCollection) {
        this.poInfoCollection = poInfoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplierId != null ? supplierId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supplier)) {
            return false;
        }
        Supplier other = (Supplier) object;
        if ((this.supplierId == null && other.supplierId != null) || (this.supplierId != null && !this.supplierId.equals(other.supplierId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Supplier[ supplierId=" + supplierId + " ]";
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    @XmlTransient
    public Collection<SupplierReturn> getSupplierReturnCollection() {
        return supplierReturnCollection;
    }

    public void setSupplierReturnCollection(Collection<SupplierReturn> supplierReturnCollection) {
        this.supplierReturnCollection = supplierReturnCollection;
    }

    @XmlTransient
    public Collection<SupplierHasGroup> getSupplierHasGroupCollection() {
        return supplierHasGroupCollection;
    }

    public void setSupplierHasGroupCollection(Collection<SupplierHasGroup> supplierHasGroupCollection) {
        this.supplierHasGroupCollection = supplierHasGroupCollection;
    }
    
}
