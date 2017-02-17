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
@Table(name = "prn_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrnInfo.findAll", query = "SELECT p FROM PrnInfo p"),
    @NamedQuery(name = "PrnInfo.findByPrnInfoId", query = "SELECT p FROM PrnInfo p WHERE p.prnInfoId = :prnInfoId"),
    @NamedQuery(name = "PrnInfo.findByDate", query = "SELECT p FROM PrnInfo p WHERE p.date = :date"),
    @NamedQuery(name = "PrnInfo.findByStatus", query = "SELECT p FROM PrnInfo p WHERE p.status = :status")})
public class PrnInfo implements Serializable {

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prnInfoId")
    private Collection<PrnItem> prnItemCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prn_info_id")
    private Integer prnInfoId;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "status")
    private Boolean status;
    @JoinColumn(name = "sys_user_sysuser_id", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser sysUserSysuserId;

    public PrnInfo() {
    }

    public PrnInfo(Integer prnInfoId) {
        this.prnInfoId = prnInfoId;
    }

    public Integer getPrnInfoId() {
        return prnInfoId;
    }

    public void setPrnInfoId(Integer prnInfoId) {
        this.prnInfoId = prnInfoId;
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

    public SysUser getSysUserSysuserId() {
        return sysUserSysuserId;
    }

    public void setSysUserSysuserId(SysUser sysUserSysuserId) {
        this.sysUserSysuserId = sysUserSysuserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prnInfoId != null ? prnInfoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrnInfo)) {
            return false;
        }
        PrnInfo other = (PrnInfo) object;
        if ((this.prnInfoId == null && other.prnInfoId != null) || (this.prnInfoId != null && !this.prnInfoId.equals(other.prnInfoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.PrnInfo[ prnInfoId=" + prnInfoId + " ]";
    }

    @XmlTransient
    public Collection<PrnItem> getPrnItemCollection() {
        return prnItemCollection;
    }

    public void setPrnItemCollection(Collection<PrnItem> prnItemCollection) {
        this.prnItemCollection = prnItemCollection;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }
    
}
