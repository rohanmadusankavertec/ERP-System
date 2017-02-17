/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.dao;

import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.PrivilegeItem;
import com.vertec.hibe.model.SysUser;
import com.vertec.hibe.model.UserGroup;
import com.vertec.hibe.model.UserGroupPrivilegeItem;
import java.util.List;

/**
 *
 * @author User
 */
public interface UserDAO {

    public abstract SysUser userlogin(SysUser sysUser);

    public abstract String registerUser(SysUser sysUser);

    public abstract String updateUser(SysUser sysUser);

    public abstract String saveUGPI(UserGroupPrivilegeItem ugpi);

    public abstract String checkemail(String email);

    public abstract String checkUsername(String username);

    public abstract List<SysUser> getListofUsers(Company company);

    public abstract List<PrivilegeItem> allDefPriv();

    public abstract String removeUser(int userId);

    public abstract String updatePassword(SysUser su);

    public abstract UserGroup createUserGroup(UserGroup ug);

    public abstract SysUser viewUser(int userId);

}
