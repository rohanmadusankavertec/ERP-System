/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author vertec-r
 */
public class SaveDAOImpl {
    public String saveObject(Object object) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                session.save(object);
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
