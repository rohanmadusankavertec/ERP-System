/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.hibe.model.Account;
import com.vertec.hibe.model.Company;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author vertec-r
 */
public class LiabilitiesDAOImpl {
    public List<Account> loadAccountsToCreditor(Company company) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT ac FROM Account ac WHERE ac.subtypeId.name=:stype AND ac.companyId=:company AND ac.isValid=:iv");
                query.setParameter("company", company);
                query.setParameter("stype", "Creditors");
                query.setParameter("iv", true);
                List<Account> acList = query.list();
                return acList;
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
    
    public List<Account> loadAccountsBySubtype(Company company,String stype) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT ac FROM Account ac WHERE ac.subtypeId.name=:stype AND ac.companyId=:company AND ac.isValid=:iv");
                query.setParameter("company", company);
                query.setParameter("stype", stype);
                query.setParameter("iv", true);
                List<Account> acList = query.list();
                return acList;
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
    
    public String updateAccountBalance(double amount,int id) {
        System.out.println("UPDATING >>>Amount :"+amount+">>>>> Account ID :"+id);
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("UPDATE Account as a set a.balance=a.balance+:amount WHERE a.id=:account");
                query.setParameter("amount", amount);
                query.setParameter("account", id);
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
