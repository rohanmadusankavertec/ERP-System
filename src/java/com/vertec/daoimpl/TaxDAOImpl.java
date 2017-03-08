/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.hibe.model.Account;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Supplier;
import com.vertec.hibe.model.Tax;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ruchira
 */
public class TaxDAOImpl {
    public String saveTax(Tax tax){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(tax);
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
    
    public List<Tax> getallTax(Company com) {
       Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT t FROM Tax t WHERE t.companyId=:com");
//                query.setParameter("isActive", true);
                query.setParameter("com", com);
                List<Tax> taxList = query.list();
                return taxList;

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
    
    public Tax getTaxDetail(Company com,int id) {
       Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT t FROM Tax t WHERE t.companyId=:com AND t.id=:id");
                query.setParameter("id", id);
                query.setParameter("com", com);
                Tax tax = (Tax)query.uniqueResult();
                return tax;

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
    public String updateTax(Tax tax) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.createQuery("UPDATE Tax as t set t.name=:taxName,t.percentage=:perc WHERE t.id=:id");
//                Query query = session.createQuery("UPDATE Tax t set t.name=:taxName t.percentage=:perc WHERE t.id=:id");
                query.setParameter("taxName", tax.getName());
                query.setParameter("perc", tax.getPercentage());
                query.setParameter("id", tax.getId());

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
    
    
}
