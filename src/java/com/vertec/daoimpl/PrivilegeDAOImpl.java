/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.dao.PrivilegeDAO;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Priviledge;
import com.vertec.hibe.model.PrivilegeItem;
import com.vertec.hibe.model.UserGroup;
import com.vertec.hibe.model.UserGroupPriviledge;
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
public class PrivilegeDAOImpl implements PrivilegeDAO {

    /**
     * call from PrivilegeController--> case
     * "ViewPrivilege","ForPrivilegeItem","ViewPI"
     *
     * @return
     */
    @Override
    public List<Priviledge> loadAllPrivileges(Company company) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT p FROM Priviledge p WHERE p.companyId=:company");
                query.setParameter("company", company);
                List<Priviledge> prList = query.list();
                return prList;

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

    /**
     * call from PrivilegeController--> case "SavePrivilege"
     *
     * @param priviledge
     * @return
     */
    @Override
    public String savePrivilege(Priviledge priviledge) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(priviledge);
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

    /**
     * call from PrivilegeController--> case "UpdatePrivilege"
     *
     * @param priviledge
     * @return
     */
    @Override
    public String updatePrivilege(Priviledge priviledge) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE Priviledge as p set  p.priviledgeName=:priviledgeName Where p.priviledgeId=:priviledgeId");

                query.setParameter("priviledgeName", priviledge.getPriviledgeName());
                query.setParameter("priviledgeId", priviledge.getPriviledgeId());

                query.executeUpdate();

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

    /**
     * call from PrivilegeController--> case
     * "LoadUserGroups","LoadUserGroupsForPI"
     *
     * @return
     */
    @Override
    public List<UserGroup> loadAllUserGroup(Company company) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                
                
                Query query = session.createQuery("SELECT ug FROM UserGroup ug WHERE ug.companyId=:company");
                query.setParameter("company", company);
                List<UserGroup> ugList = query.list();
                return ugList;

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

    /**
     * call from PrivilegeController--> case "SetPrivilege"
     *
     * @param groupId
     * @return
     */
    @Override
    public List<UserGroupPriviledge> loadAllUserGroupPrivilege(int groupId) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT ug FROM UserGroupPriviledge ug Where ug.userGroupId.userGroupId=:ugpId");
                query.setParameter("ugpId", groupId);

                List<UserGroupPriviledge> ugpList = query.list();
                return ugpList;

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

    /**
     * call from PrivilegeController--> case "SetPrivilege"
     *
     * @param ugId
     * @return
     */
    public List<Object[]> loadNotInAllPrivileges(int ugId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("SELECT * FROM priviledge where priviledge_id not in (SELECT priviledge_id FROM user_group_priviledge where user_group_id=:user_group_id) ");
                query.setParameter("user_group_id", ugId);
                List<Object[]> prList = query.list();
                return prList;

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

    /**
     * call from PrivilegeController--> case "UpdatePriviledge"
     *
     * @param ugp
     * @return
     */
    @Override
    public String saveUGPrivilege(UserGroupPriviledge ugp) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(ugp);
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

    /**
     * call from PrivilegeController--> case "UpdatePriviledge"
     *
     * @param ugp
     * @return
     */
    @Override
    public String removeUGPrivilege(int ugp) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("DELETE FROM UserGroupPriviledge u WHERE u.userGroupId.userGroupId=:userGroupId");

                query.setParameter("userGroupId", ugp);

                query.executeUpdate();

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

    /**
     * call from PrivilegeController--> case "ForPrivilegeItem"
     *
     * @return
     */
    @Override
    public List<PrivilegeItem> loadAllPrivilegeItems(Company company) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT pi FROM PrivilegeItem pi WHERE pi.companyId=:company");
                query.setParameter("company", company);
                List<PrivilegeItem> prList = query.list();
                return prList;

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

    /**
     * call from PrivilegeController--> case "SavePI"
     *
     * @param pi
     * @return
     */
    @Override
    public String savePrivilegeItem(PrivilegeItem pi) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(pi);
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

    /**
     * call from PrivilegeController--> case "SetPrivilegeItem"
     *
     * @param groupId
     * @return
     */
    @Override
    public List<Object[]> loadAllUserGroupPrivilegeItem(int groupId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("SELECT privilege_item.privilege_item_id,priviledge_priviledge_id,privilege_item_name,priviledge_name FROM user_group_privilege_item\n"
                        + "inner join privilege_item on (user_group_privilege_item.privilege_item_id=privilege_item.privilege_item_id)\n"
                        + "inner join priviledge on (privilege_item.priviledge_priviledge_id=priviledge.priviledge_id) where user_group_id=:ugpId");
                query.setParameter("ugpId", groupId);

                List<Object[]> ugpList = query.list();
                return ugpList;

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

    /**
     * call from PrivilegeController--> case "SetPrivilegeItem"
     *
     * @param ugId
     * @return
     */
    @Override
    public List<Object[]> loadNotInAllPrivilegeItems(int ugId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("SELECT privilege_item_id,priviledge_priviledge_id,privilege_item_name,priviledge_name\n"
                        + "FROM privilege_item inner join priviledge on (privilege_item.priviledge_priviledge_id=priviledge.priviledge_id)\n"
                        + "where privilege_item_id not in (SELECT privilege_item_id FROM user_group_privilege_item where user_group_id=:user_group_id) ");
                query.setParameter("user_group_id", ugId);
                List<Object[]> prList = query.list();
                return prList;

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

    /**
     * call from PrivilegeController--> case "UpdatePriviledgeItem"
     *
     * @param ugp
     * @return
     */
    @Override
    public String saveUGPrivilegeItem(UserGroupPrivilegeItem ugp) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(ugp);
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

    /**
     * call from PrivilegeController--> case "UpdatePriviledgeItem"
     *
     * @param ugp
     * @return
     */
    @Override
    public String removeUGPrivilegeItem(int ugp) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("DELETE FROM UserGroupPrivilegeItem u WHERE u.userGroupId.userGroupId=:userGroupId");

                query.setParameter("userGroupId", ugp);

                query.executeUpdate();

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

    /**
     * call from PrivilegeController--> case "ViewPI"
     *
     * @param privilegeItem
     * @return
     */
    @Override
    public PrivilegeItem ViewPrivilegeItem(PrivilegeItem privilegeItem) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.createQuery("From PrivilegeItem ug Where ug.privilegeItemId=:ugpId");
                query.setParameter("ugpId", privilegeItem.getPrivilegeItemId());

                PrivilegeItem pI = (PrivilegeItem) query.uniqueResult();
                return pI;

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

    /**
     * call from PrivilegeController--> case "SaveUpdatedPI"
     *
     * @param privilegeItem
     * @return
     */
    @Override
    public String updatePrivilegeItem(PrivilegeItem privilegeItem) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE PrivilegeItem as p set  p.privilegeItemName=:privilegeItemName,p.privilegeItemCode=:privilegeItemCode,p.privilegeItemDefaultStatus=:privilegeItemDefaultStatus,p.priviledgePriviledgeId.priviledgeId=:priviledgeId Where p.privilegeItemId=:privilegeItemId");

                query.setParameter("privilegeItemName", privilegeItem.getPrivilegeItemName());
                query.setParameter("privilegeItemCode", privilegeItem.getPrivilegeItemCode());
                query.setParameter("privilegeItemDefaultStatus", privilegeItem.getPrivilegeItemDefaultStatus());
                query.setParameter("priviledgeId", privilegeItem.getPriviledgePriviledgeId().getPriviledgeId());
                query.setParameter("privilegeItemId", privilegeItem.getPrivilegeItemId());

                query.executeUpdate();

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
