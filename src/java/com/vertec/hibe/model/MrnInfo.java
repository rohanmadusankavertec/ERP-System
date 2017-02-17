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
@Table(name = "mrn_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MrnInfo.findAll", query = "SELECT m FROM MrnInfo m"),
    @NamedQuery(name = "MrnInfo.findById", query = "SELECT m FROM MrnInfo m WHERE m.id = :id"),
    @NamedQuery(name = "MrnInfo.findByDate", query = "SELECT m FROM MrnInfo m WHERE m.date = :date")})
public class MrnInfo implements Serializable {

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mrnInfoId")
    private Collection<Mrn> mrnCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "sysuser_id", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser sysuserId;

    public MrnInfo() {
    }

    public MrnInfo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SysUser getSysuserId() {
        return sysuserId;
    }

    public void setSysuserId(SysUser sysuserId) {
        this.sysuserId = sysuserId;
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
        if (!(object instanceof MrnInfo)) {
            return false;
        }
        MrnInfo other = (MrnInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.MrnInfo[ id=" + id + " ]";
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    @XmlTransient
    public Collection<Mrn> getMrnCollection() {
        return mrnCollection;
    }

    public void setMrnCollection(Collection<Mrn> mrnCollection) {
        this.mrnCollection = mrnCollection;
    }
    
}
