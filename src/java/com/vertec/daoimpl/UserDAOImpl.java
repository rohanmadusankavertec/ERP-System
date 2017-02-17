/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.dao.UserDAO;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.CompanyGroup;
import com.vertec.hibe.model.PrivilegeItem;
import com.vertec.hibe.model.SysUser;
import com.vertec.hibe.model.UserGroup;
import com.vertec.hibe.model.UserGroupPrivilegeItem;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author User
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public SysUser userlogin(SysUser sysUser) {
        SysUser userTmp = new SysUser();
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                // Check User is already exist or not
                String loginHQLQuery = "FROM SysUser u WHERE u.username=:username AND u.password=:password";

                Query query = session.createQuery(loginHQLQuery);
                query.setParameter("username", sysUser.getUsername());
                query.setParameter("password", sysUser.getPassword());

                userTmp = (SysUser) query.uniqueResult();

                if (userTmp != null) {
                    boolean userStatus = userTmp.getIsActive();
                    if (userStatus == true) {

                        userTmp.setLoginStatus(VertecConstants.SUCCESS);

                    } else {

                        userTmp.setLoginStatus(VertecConstants.ERROR);
                    }
                    return userTmp;
                } else {
                    sysUser.setLoginStatus(VertecConstants.INVALID_LOGIN);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sysUser.setLoginStatus(VertecConstants.ERROR_MESSAGE);

            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        } else {
            sysUser.setLoginStatus(VertecConstants.ERROR_MESSAGE);
        }

        return sysUser;
    }

    @Override
    public String registerUser(SysUser sysUser) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(sysUser);
                session.flush();

                transaction.commit();
                return VertecConstants.SUCCESS;

            } catch (Exception e) {
                e.printStackTrace();
                return VertecConstants.ERROR;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }

    @Override
    public String checkemail(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String checkUsername(String username) {
        String status = "";

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        // String status = "";
        if (session != null) {
            try {
                String HQL = "From SysUser s where s.username=:username";
                Query query = session.createQuery(HQL);
                query.setParameter("username", username);
                SysUser c = (SysUser) query.uniqueResult();
                if (c != null) {
                    status = VertecConstants.ALREADYEXIST;
                    return status;
                } else {
                    status = VertecConstants.NOT_EXIST;
                    return status;
                }
            } catch (Exception e) {
                e.printStackTrace();
                status = VertecConstants.ERROR;
                return status;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return status;
    }

    @Override
    public List<SysUser> getListofUsers(Company company) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM SysUser s WHERE s.isActive=:isActive AND s.companyId=:company");
                query.setParameter("isActive", true);
                query.setParameter("company", company);

                List<SysUser> userList = query.list();
                return userList;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }

    @Override
    public String removeUser(int userId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("Update sys_user set is_active=:is_active where sysuser_id=:sysuser_id");

                query.setParameter("sysuser_id", userId);
                query.setParameter("is_active", false);

                query.executeUpdate();

                transaction.commit();
                return VertecConstants.UPDATED;

            } catch (Exception e) {
                e.printStackTrace();
                return VertecConstants.ERROR;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }

    @Override
    public SysUser viewUser(int userId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("SysUser.findBySysuserId");
                query.setParameter("sysuserId", userId);

                SysUser user = (SysUser) query.uniqueResult();
                return user;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }
public List<Company> getCompanies(CompanyGroup com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT c FROM Company c WHERE c.companyGroupId=:cg");
                query.setParameter("cg", com);

                List<Company> user = (List<Company>) query.list();
                return user;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }
    @Override
    public String updateUser(SysUser sysUser) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("Update sys_user set first_name=:first_name,last_name=:last_name,email_add=:email_add,contact_no=:contact_no,nic_no=:nic_no,dob=:dob,last_updated_date=:last_updated_date,last_updated_by=:last_updated_by,user_group_id=:user_group_id,company_id=:com where sysuser_id=:sysuser_id");

                query.setParameter("user_group_id", sysUser.getUserGroupId());
                query.setParameter("sysuser_id", sysUser.getSysuserId());
                query.setParameter("first_name", sysUser.getFirstName());
                query.setParameter("last_name", sysUser.getLastName());
                query.setParameter("email_add", sysUser.getEmailAdd());
                query.setParameter("contact_no", sysUser.getContactNo());
                query.setParameter("nic_no", sysUser.getNicNo());
                query.setParameter("dob", sysUser.getDob());
                query.setParameter("last_updated_date", sysUser.getLastUpdatedDate());
                query.setParameter("last_updated_by", sysUser.getLastUpdatedBy());
                query.setParameter("com", sysUser.getCompanyId());

                query.executeUpdate();

                transaction.commit();
                return VertecConstants.UPDATED;

            } catch (Exception e) {
                e.printStackTrace();
                return VertecConstants.ERROR;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;

    }

    @Override
    public String updatePassword(SysUser su) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE SysUser as s set  s.password=:password where s.sysuserId=:sysuserId");

                query.setParameter("password", su.getPassword());
                query.setParameter("sysuserId", su.getSysuserId());

                query.executeUpdate();

                transaction.commit();
                return VertecConstants.UPDATEDPASSWORD;

            } catch (Exception e) {
                e.printStackTrace();
                return VertecConstants.ERROR;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }

    @Override
    public UserGroup createUserGroup(UserGroup ug) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(ug);
                session.flush();
                transaction.commit();
                return ug;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }

    @Override
    public List<PrivilegeItem> allDefPriv() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.createQuery("FROM PrivilegeItem p Where p.privilegeItemDefaultStatus=:isActive ");
                query.setParameter("isActive", "Y");

                List<PrivilegeItem> groupList = query.list();
                return groupList;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }

    @Override
    public String saveUGPI(UserGroupPrivilegeItem ugpi) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(ugpi);
                session.flush();
                transaction.commit();
                return VertecConstants.SUCCESS;

            } catch (Exception e) {
                e.printStackTrace();
                return VertecConstants.ERROR;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }

}
