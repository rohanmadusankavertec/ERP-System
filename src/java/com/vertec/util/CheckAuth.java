/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.util;

import com.vertec.hibe.model.SysUser;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author User
 */
public class CheckAuth {

    SysUser user = new SysUser();

    public Object[] checkUserAuth(String code, int gId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT ugpi_id, user_group_id,privilege_item_code FROM user_group_privilege_item inner join privilege_item on user_group_privilege_item.privilege_item_id=privilege_item.privilege_item_id where user_group_privilege_item.user_group_id=:groupId and privilege_item.privilege_item_code=:itemCode");
                
                query.setParameter("groupId", gId);
                query.setParameter("itemCode", code);
                Object[] ob =  (Object[])query.uniqueResult();

                return ob;

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
}
