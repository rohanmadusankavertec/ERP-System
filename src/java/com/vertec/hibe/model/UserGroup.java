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
@Table(name = "user_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserGroup.findAll", query = "SELECT u FROM UserGroup u"),
    @NamedQuery(name = "UserGroup.findByUserGroupId", query = "SELECT u FROM UserGroup u WHERE u.userGroupId = :userGroupId"),
    @NamedQuery(name = "UserGroup.findByUserGroupName", query = "SELECT u FROM UserGroup u WHERE u.userGroupName = :userGroupName")})
public class UserGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_group_id")
    private Integer userGroupId;
    @Column(name = "user_group_name")
    private String userGroupName;
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userGroupId")
    private Collection<UserGroupPriviledge> userGroupPriviledgeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userGroupId")
    private Collection<SysUser> sysUserCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userGroupId")
    private Collection<UserGroupPrivilegeItem> userGroupPrivilegeItemCollection;

    public UserGroup() {
    }

    public UserGroup(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public UserGroup(String userGroupName,Company company) {
        this.userGroupName = userGroupName;
    }
    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName,Company company) {
        this.userGroupName = userGroupName;
        this.companyId=company;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    @XmlTransient
    public Collection<UserGroupPriviledge> getUserGroupPriviledgeCollection() {
        return userGroupPriviledgeCollection;
    }

    public void setUserGroupPriviledgeCollection(Collection<UserGroupPriviledge> userGroupPriviledgeCollection) {
        this.userGroupPriviledgeCollection = userGroupPriviledgeCollection;
    }

    @XmlTransient
    public Collection<SysUser> getSysUserCollection() {
        return sysUserCollection;
    }

    public void setSysUserCollection(Collection<SysUser> sysUserCollection) {
        this.sysUserCollection = sysUserCollection;
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
        hash += (userGroupId != null ? userGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserGroup)) {
            return false;
        }
        UserGroup other = (UserGroup) object;
        if ((this.userGroupId == null && other.userGroupId != null) || (this.userGroupId != null && !this.userGroupId.equals(other.userGroupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.UserGroup[ userGroupId=" + userGroupId + " ]";
    }
    
}
