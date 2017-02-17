/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.dao;

import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Priviledge;
import com.vertec.hibe.model.PrivilegeItem;
import com.vertec.hibe.model.UserGroup;
import com.vertec.hibe.model.UserGroupPriviledge;
//import com.vertec.hibe.model.UserGroupPriviledge;
import com.vertec.hibe.model.UserGroupPrivilegeItem;
import java.util.List;

/**
 *
 * @author User
 */
public interface PrivilegeDAO {

    public abstract List<Priviledge> loadAllPrivileges(Company company);

    public abstract List<PrivilegeItem> loadAllPrivilegeItems(Company company);

    public abstract List<UserGroup> loadAllUserGroup(Company company);

    public abstract List<UserGroupPriviledge> loadAllUserGroupPrivilege(int groupId);

    public abstract List<Object[]> loadAllUserGroupPrivilegeItem(int groupId);

    public abstract String savePrivilege(Priviledge priviledge);

    public abstract String updatePrivilege(Priviledge priviledge);

    public abstract List<Object[]> loadNotInAllPrivileges(int ugId);

    public abstract List<Object[]> loadNotInAllPrivilegeItems(int ugId);

    public abstract String saveUGPrivilege(UserGroupPriviledge ugp);

    public abstract String saveUGPrivilegeItem(UserGroupPrivilegeItem ugp);

    public abstract String savePrivilegeItem(PrivilegeItem pi);

    public abstract String removeUGPrivilege(int ugp);

    public abstract String removeUGPrivilegeItem(int ugp);

    public abstract String updatePrivilegeItem(PrivilegeItem privilegeItem);

    public abstract PrivilegeItem ViewPrivilegeItem(PrivilegeItem privilegeItem);
}
