/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vertec.hibe.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vertec-r
 */
@Entity
@Table(name = "user_group_privilege_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserGroupPrivilegeItem.findAll", query = "SELECT u FROM UserGroupPrivilegeItem u"),
    @NamedQuery(name = "UserGroupPrivilegeItem.findByUgpiId", query = "SELECT u FROM UserGroupPrivilegeItem u WHERE u.ugpiId = :ugpiId")})
public class UserGroupPrivilegeItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ugpi_id")
    private Integer ugpiId;
    @JoinColumn(name = "privilege_item_id", referencedColumnName = "privilege_item_id")
    @ManyToOne(optional = false)
    private PrivilegeItem privilegeItemId;
    @JoinColumn(name = "user_group_id", referencedColumnName = "user_group_id")
    @ManyToOne(optional = false)
    private UserGroup userGroupId;

    public UserGroupPrivilegeItem() {
    }

    public UserGroupPrivilegeItem(Integer ugpiId) {
        this.ugpiId = ugpiId;
    }
    
    public UserGroupPrivilegeItem(PrivilegeItem privilegeItemId, UserGroup userGroupId) {
        this.privilegeItemId = privilegeItemId;
        this.userGroupId = userGroupId;
    }

    public Integer getUgpiId() {
        return ugpiId;
    }

    public void setUgpiId(Integer ugpiId) {
        this.ugpiId = ugpiId;
    }

    public PrivilegeItem getPrivilegeItemId() {
        return privilegeItemId;
    }

    public void setPrivilegeItemId(PrivilegeItem privilegeItemId) {
        this.privilegeItemId = privilegeItemId;
    }

    public UserGroup getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(UserGroup userGroupId) {
        this.userGroupId = userGroupId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ugpiId != null ? ugpiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserGroupPrivilegeItem)) {
            return false;
        }
        UserGroupPrivilegeItem other = (UserGroupPrivilegeItem) object;
        if ((this.ugpiId == null && other.ugpiId != null) || (this.ugpiId != null && !this.ugpiId.equals(other.ugpiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.UserGroupPrivilegeItem[ ugpiId=" + ugpiId + " ]";
    }
    
}
