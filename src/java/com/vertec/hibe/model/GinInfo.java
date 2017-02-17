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
 * @author Rohan
 */
@Entity
@Table(name = "gin_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GinInfo.findAll", query = "SELECT g FROM GinInfo g"),
    @NamedQuery(name = "GinInfo.findById", query = "SELECT g FROM GinInfo g WHERE g.id = :id"),
    @NamedQuery(name = "GinInfo.findByDate", query = "SELECT g FROM GinInfo g WHERE g.date = :date"),
    @NamedQuery(name = "GinInfo.findByTotal", query = "SELECT g FROM GinInfo g WHERE g.total = :total")})
public class GinInfo implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ginInfoId")
    private Collection<Gin> ginCollection;
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @JoinColumn(name = "sysuser_id", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser sysuserId;

    public GinInfo() {
    }

    public GinInfo(Integer id) {
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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
        if (!(object instanceof GinInfo)) {
            return false;
        }
        GinInfo other = (GinInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.GinInfo[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Gin> getGinCollection() {
        return ginCollection;
    }

    public void setGinCollection(Collection<Gin> ginCollection) {
        this.ginCollection = ginCollection;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }
    
}
