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
@Table(name = "priviledge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Priviledge.findAll", query = "SELECT p FROM Priviledge p"),
    @NamedQuery(name = "Priviledge.findByPriviledgeId", query = "SELECT p FROM Priviledge p WHERE p.priviledgeId = :priviledgeId"),
    @NamedQuery(name = "Priviledge.findByPriviledgeName", query = "SELECT p FROM Priviledge p WHERE p.priviledgeName = :priviledgeName")})
public class Priviledge implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "priviledge_id")
    private Integer priviledgeId;
    @Column(name = "priviledge_name")
    private String priviledgeName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priviledgeId")
    private Collection<UserGroupPriviledge> userGroupPriviledgeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priviledgePriviledgeId")
    private Collection<PrivilegeItem> privilegeItemCollection;
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;

    public Priviledge() {
    }

    public Priviledge(Integer priviledgeId) {
        this.priviledgeId = priviledgeId;
    }

     public Priviledge(Integer priviledgeId, String priviledgeName) {
        this.priviledgeId = priviledgeId;
        this.priviledgeName = priviledgeName;
    }
     public Priviledge(String priviledgeName,Company company) {
        this.priviledgeName = priviledgeName;
        this.companyId=company;
    }
    public Integer getPriviledgeId() {
        return priviledgeId;
    }

    public void setPriviledgeId(Integer priviledgeId) {
        this.priviledgeId = priviledgeId;
    }

    public String getPriviledgeName() {
        return priviledgeName;
    }

    public void setPriviledgeName(String priviledgeName) {
        this.priviledgeName = priviledgeName;
    }

    @XmlTransient
    public Collection<UserGroupPriviledge> getUserGroupPriviledgeCollection() {
        return userGroupPriviledgeCollection;
    }

    public void setUserGroupPriviledgeCollection(Collection<UserGroupPriviledge> userGroupPriviledgeCollection) {
        this.userGroupPriviledgeCollection = userGroupPriviledgeCollection;
    }

    @XmlTransient
    public Collection<PrivilegeItem> getPrivilegeItemCollection() {
        return privilegeItemCollection;
    }

    public void setPrivilegeItemCollection(Collection<PrivilegeItem> privilegeItemCollection) {
        this.privilegeItemCollection = privilegeItemCollection;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (priviledgeId != null ? priviledgeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Priviledge)) {
            return false;
        }
        Priviledge other = (Priviledge) object;
        if ((this.priviledgeId == null && other.priviledgeId != null) || (this.priviledgeId != null && !this.priviledgeId.equals(other.priviledgeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Priviledge[ priviledgeId=" + priviledgeId + " ]";
    }
    
}
