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
@Table(name = "gtn_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GtnInfo.findAll", query = "SELECT g FROM GtnInfo g"),
    @NamedQuery(name = "GtnInfo.findByGtninfoId", query = "SELECT g FROM GtnInfo g WHERE g.gtninfoId = :gtninfoId"),
    @NamedQuery(name = "GtnInfo.findByDate", query = "SELECT g FROM GtnInfo g WHERE g.date = :date"),
    @NamedQuery(name = "GtnInfo.findByTotal", query = "SELECT g FROM GtnInfo g WHERE g.total = :total")})
public class GtnInfo implements Serializable {

    @Column(name = "is_valid")
    private Boolean isValid;
    @Column(name = "is_pending")
    private Boolean isPending;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "gtninfo_id")
    private Integer gtninfoId;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gtninfoId")
    private Collection<Gtn> gtnCollection;
    @JoinColumn(name = "from_branch", referencedColumnName = "branch_id")
    @ManyToOne(optional = false)
    private Branch fromBranch;
    @JoinColumn(name = "to_branch", referencedColumnName = "branch_id")
    @ManyToOne(optional = false)
    private Branch toBranch;
    @JoinColumn(name = "added_by", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser addedBy;

    public GtnInfo() {
    }

    public GtnInfo(Integer gtninfoId) {
        this.gtninfoId = gtninfoId;
    }

    public Integer getGtninfoId() {
        return gtninfoId;
    }

    public void setGtninfoId(Integer gtninfoId) {
        this.gtninfoId = gtninfoId;
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
    public Collection<Gtn> getGtnCollection() {
        return gtnCollection;
    }

    public void setGtnCollection(Collection<Gtn> gtnCollection) {
        this.gtnCollection = gtnCollection;
    }

    public Branch getFromBranch() {
        return fromBranch;
    }

    public void setFromBranch(Branch fromBranch) {
        this.fromBranch = fromBranch;
    }

    public Branch getToBranch() {
        return toBranch;
    }

    public void setToBranch(Branch toBranch) {
        this.toBranch = toBranch;
    }

    public SysUser getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(SysUser addedBy) {
        this.addedBy = addedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gtninfoId != null ? gtninfoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GtnInfo)) {
            return false;
        }
        GtnInfo other = (GtnInfo) object;
        if ((this.gtninfoId == null && other.gtninfoId != null) || (this.gtninfoId != null && !this.gtninfoId.equals(other.gtninfoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.GtnInfo[ gtninfoId=" + gtninfoId + " ]";
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Boolean getIsPending() {
        return isPending;
    }

    public void setIsPending(Boolean isPending) {
        this.isPending = isPending;
    }
    
}
