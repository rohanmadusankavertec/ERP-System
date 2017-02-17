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
@Table(name = "po_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PoInfo.findAll", query = "SELECT p FROM PoInfo p"),
    @NamedQuery(name = "PoInfo.findByPoInfoId", query = "SELECT p FROM PoInfo p WHERE p.poInfoId = :poInfoId"),
    @NamedQuery(name = "PoInfo.findByDate", query = "SELECT p FROM PoInfo p WHERE p.date = :date"),
    @NamedQuery(name = "PoInfo.findByStatus", query = "SELECT p FROM PoInfo p WHERE p.status = :status"),
    @NamedQuery(name = "PoInfo.findByTotal", query = "SELECT p FROM PoInfo p WHERE p.total = :total"),
    @NamedQuery(name = "PoInfo.findByReceivedDate", query = "SELECT p FROM PoInfo p WHERE p.receivedDate = :receivedDate")})
public class PoInfo implements Serializable {

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poInfoPoInfoId")
    private Collection<PurchasingOrder> purchasingOrderCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "po_info_id")
    private Integer poInfoId;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "status")
    private Boolean status;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @Column(name = "received_date")
    @Temporal(TemporalType.DATE)
    private Date receivedDate;
    @JoinColumn(name = "supplier_supplier_id", referencedColumnName = "supplier_id")
    @ManyToOne(optional = false)
    private Supplier supplierSupplierId;
    @JoinColumn(name = "sys_user_sysuser_id", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser sysUserSysuserId;

    public PoInfo() {
    }

    public PoInfo(Integer poInfoId) {
        this.poInfoId = poInfoId;
    }

    public Integer getPoInfoId() {
        return poInfoId;
    }

    public void setPoInfoId(Integer poInfoId) {
        this.poInfoId = poInfoId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Supplier getSupplierSupplierId() {
        return supplierSupplierId;
    }

    public void setSupplierSupplierId(Supplier supplierSupplierId) {
        this.supplierSupplierId = supplierSupplierId;
    }

    public SysUser getSysUserSysuserId() {
        return sysUserSysuserId;
    }

    public void setSysUserSysuserId(SysUser sysUserSysuserId) {
        this.sysUserSysuserId = sysUserSysuserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (poInfoId != null ? poInfoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PoInfo)) {
            return false;
        }
        PoInfo other = (PoInfo) object;
        if ((this.poInfoId == null && other.poInfoId != null) || (this.poInfoId != null && !this.poInfoId.equals(other.poInfoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.PoInfo[ poInfoId=" + poInfoId + " ]";
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    @XmlTransient
    public Collection<PurchasingOrder> getPurchasingOrderCollection() {
        return purchasingOrderCollection;
    }

    public void setPurchasingOrderCollection(Collection<PurchasingOrder> purchasingOrderCollection) {
        this.purchasingOrderCollection = purchasingOrderCollection;
    }
    
}
