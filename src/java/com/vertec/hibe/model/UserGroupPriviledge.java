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
@Table(name = "user_group_priviledge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserGroupPriviledge.findAll", query = "SELECT u FROM UserGroupPriviledge u"),
    @NamedQuery(name = "UserGroupPriviledge.findByUgpId", query = "SELECT u FROM UserGroupPriviledge u WHERE u.ugpId = :ugpId")})
public class UserGroupPriviledge implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ugp_id")
    private Integer ugpId;
    @JoinColumn(name = "user_group_id", referencedColumnName = "user_group_id")
    @ManyToOne(optional = false)
    private UserGroup userGroupId;
    @JoinColumn(name = "priviledge_id", referencedColumnName = "priviledge_id")
    @ManyToOne(optional = false)
    private Priviledge priviledgeId;

    public UserGroupPriviledge() {
    }
    
    public UserGroupPriviledge(Priviledge priviledgeId, UserGroup userGroupId) {
        this.priviledgeId = priviledgeId;
        this.userGroupId = userGroupId;
    }

    public UserGroupPriviledge(Integer ugpId) {
        this.ugpId = ugpId;
    }

    public Integer getUgpId() {
        return ugpId;
    }

    public void setUgpId(Integer ugpId) {
        this.ugpId = ugpId;
    }

    public UserGroup getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(UserGroup userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Priviledge getPriviledgeId() {
        return priviledgeId;
    }

    public void setPriviledgeId(Priviledge priviledgeId) {
        this.priviledgeId = priviledgeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ugpId != null ? ugpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserGroupPriviledge)) {
            return false;
        }
        UserGroupPriviledge other = (UserGroupPriviledge) object;
        if ((this.ugpId == null && other.ugpId != null) || (this.ugpId != null && !this.ugpId.equals(other.ugpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.UserGroupPriviledge[ ugpId=" + ugpId + " ]";
    }
    
}
