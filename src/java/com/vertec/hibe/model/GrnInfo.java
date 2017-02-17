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
@Table(name = "grn_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrnInfo.findAll", query = "SELECT g FROM GrnInfo g"),
    @NamedQuery(name = "GrnInfo.findByGrnInfoId", query = "SELECT g FROM GrnInfo g WHERE g.grnInfoId = :grnInfoId"),
    @NamedQuery(name = "GrnInfo.findByDate", query = "SELECT g FROM GrnInfo g WHERE g.date = :date"),
    @NamedQuery(name = "GrnInfo.findByTotal", query = "SELECT g FROM GrnInfo g WHERE g.total = :total")})
public class GrnInfo implements Serializable {

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grnInfoId")
    private Collection<GrnPayment> grnPaymentCollection;

    @Column(name = "outstanding")
    private Double outstanding;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "grn_info_id")
    private Integer grnInfoId;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grnInfoGrnInfoId")
    private Collection<Grn> grnCollection;
    @JoinColumn(name = "supplier_supplier_id", referencedColumnName = "supplier_id")
    @ManyToOne(optional = false)
    private Supplier supplierSupplierId;
    @JoinColumn(name = "sys_user_sysuser_id", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser sysUserSysuserId;

    public GrnInfo() {
    }

    public GrnInfo(Integer grnInfoId) {
        this.grnInfoId = grnInfoId;
    }

    public Integer getGrnInfoId() {
        return grnInfoId;
    }

    public void setGrnInfoId(Integer grnInfoId) {
        this.grnInfoId = grnInfoId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @XmlTransient
    public Collection<Grn> getGrnCollection() {
        return grnCollection;
    }

    public void setGrnCollection(Collection<Grn> grnCollection) {
        this.grnCollection = grnCollection;
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
        hash += (grnInfoId != null ? grnInfoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrnInfo)) {
            return false;
        }
        GrnInfo other = (GrnInfo) object;
        if ((this.grnInfoId == null && other.grnInfoId != null) || (this.grnInfoId != null && !this.grnInfoId.equals(other.grnInfoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.GrnInfo[ grnInfoId=" + grnInfoId + " ]";
    }

    public Double getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(Double outstanding) {
        this.outstanding = outstanding;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    @XmlTransient
    public Collection<GrnPayment> getGrnPaymentCollection() {
        return grnPaymentCollection;
    }

    public void setGrnPaymentCollection(Collection<GrnPayment> grnPaymentCollection) {
        this.grnPaymentCollection = grnPaymentCollection;
    }

   
    
}
