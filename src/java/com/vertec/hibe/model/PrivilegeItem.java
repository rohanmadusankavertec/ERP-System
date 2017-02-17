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
@Table(name = "privilege_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrivilegeItem.findAll", query = "SELECT p FROM PrivilegeItem p"),
    @NamedQuery(name = "PrivilegeItem.findByPrivilegeItemId", query = "SELECT p FROM PrivilegeItem p WHERE p.privilegeItemId = :privilegeItemId"),
    @NamedQuery(name = "PrivilegeItem.findByPrivilegeItemName", query = "SELECT p FROM PrivilegeItem p WHERE p.privilegeItemName = :privilegeItemName"),
    @NamedQuery(name = "PrivilegeItem.findByPrivilegeItemCode", query = "SELECT p FROM PrivilegeItem p WHERE p.privilegeItemCode = :privilegeItemCode"),
    @NamedQuery(name = "PrivilegeItem.findByPrivilegeItemDefaultStatus", query = "SELECT p FROM PrivilegeItem p WHERE p.privilegeItemDefaultStatus = :privilegeItemDefaultStatus"),
    @NamedQuery(name = "PrivilegeItem.findByPrivilegeItemAddedDateTime", query = "SELECT p FROM PrivilegeItem p WHERE p.privilegeItemAddedDateTime = :privilegeItemAddedDateTime")})
public class PrivilegeItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "privilege_item_id")
    private Integer privilegeItemId;
    @Column(name = "privilege_item_name")
    private String privilegeItemName;
    @Column(name = "privilege_item_code")
    private String privilegeItemCode;
    @Column(name = "privilege_item_default_status")
    private String privilegeItemDefaultStatus;
    @Column(name = "privilege_item_added_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date privilegeItemAddedDateTime;
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;
    @JoinColumn(name = "priviledge_priviledge_id", referencedColumnName = "priviledge_id")
    @ManyToOne(optional = false)
    private Priviledge priviledgePriviledgeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "privilegeItemId")
    private Collection<UserGroupPrivilegeItem> userGroupPrivilegeItemCollection;

    public PrivilegeItem() {
    }

    public PrivilegeItem(Integer privilegeItemId) {
        this.privilegeItemId = privilegeItemId;
    }

    public Integer getPrivilegeItemId() {
        return privilegeItemId;
    }

    public PrivilegeItem(Integer privilegeItemId, String privilegeItemName, String privilegeItemCode, String privilegeItemDefaultStatus, Priviledge priviledgePriviledgeId) {
        this.privilegeItemId = privilegeItemId;
        this.privilegeItemName = privilegeItemName;
        this.privilegeItemCode = privilegeItemCode;
        this.privilegeItemDefaultStatus = privilegeItemDefaultStatus;
        this.priviledgePriviledgeId = priviledgePriviledgeId;
    }

    public PrivilegeItem(String privilegeItemName, String privilegeItemCode, String privilegeItemDefaultStatus, Date privilegeItemAddedDateTime, Priviledge priviledgePriviledgeId,Company company) {
        this.privilegeItemName = privilegeItemName;
        this.privilegeItemCode = privilegeItemCode;
        this.privilegeItemDefaultStatus = privilegeItemDefaultStatus;
        this.privilegeItemAddedDateTime = privilegeItemAddedDateTime;
        this.priviledgePriviledgeId = priviledgePriviledgeId;
        this.companyId=company;
    }

    public void setPrivilegeItemId(Integer privilegeItemId) {
        this.privilegeItemId = privilegeItemId;
    }

    public String getPrivilegeItemName() {
        return privilegeItemName;
    }

    public void setPrivilegeItemName(String privilegeItemName) {
        this.privilegeItemName = privilegeItemName;
    }

    public String getPrivilegeItemCode() {
        return privilegeItemCode;
    }

    public void setPrivilegeItemCode(String privilegeItemCode) {
        this.privilegeItemCode = privilegeItemCode;
    }

    public String getPrivilegeItemDefaultStatus() {
        return privilegeItemDefaultStatus;
    }

    public void setPrivilegeItemDefaultStatus(String privilegeItemDefaultStatus) {
        this.privilegeItemDefaultStatus = privilegeItemDefaultStatus;
    }

    public Date getPrivilegeItemAddedDateTime() {
        return privilegeItemAddedDateTime;
    }

    public void setPrivilegeItemAddedDateTime(Date privilegeItemAddedDateTime) {
        this.privilegeItemAddedDateTime = privilegeItemAddedDateTime;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public Priviledge getPriviledgePriviledgeId() {
        return priviledgePriviledgeId;
    }

    public void setPriviledgePriviledgeId(Priviledge priviledgePriviledgeId) {
        this.priviledgePriviledgeId = priviledgePriviledgeId;
    }

    @XmlTransient
    public Collection<UserGroupPrivilegeItem> getUserGroupPrivilegeItemCollection() {
        return userGroupPrivilegeItemCollection;
    }

    public void setUserGroupPrivilegeItemCollection(Collection<UserGroupPrivilegeItem> userGroupPrivilegeItemCollection) {
        this.userGroupPrivilegeItemCollection = userGroupPrivilegeItemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (privilegeItemId != null ? privilegeItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrivilegeItem)) {
            return false;
        }
        PrivilegeItem other = (PrivilegeItem) object;
        if ((this.privilegeItemId == null && other.privilegeItemId != null) || (this.privilegeItemId != null && !this.privilegeItemId.equals(other.privilegeItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.PrivilegeItem[ privilegeItemId=" + privilegeItemId + " ]";
    }

}
