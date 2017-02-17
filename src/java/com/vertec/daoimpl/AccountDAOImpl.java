/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.hibe.model.Account;
import com.vertec.hibe.model.Subtype;
import com.vertec.hibe.model.Type;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Java-Dev-Ruchira
 */
public class AccountDAOImpl {
    
    public List<Type> loadofType(){
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT t FROM Type t");
                
                List<Type> tList = query.list();
                
                return tList;

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
    
    public List<Subtype> loadOfSubType(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Subtype s WHERE s.typeId.id = :id");
                query.setParameter("id", id);
                List<Subtype> sList = query.list();
                
                return sList;
                

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
    
    public String saveAccount(Account account){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(account);
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
    
    public List<Account> viewAllOfAccount(){
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if(session != null){
            try {
                Query query = session.createQuery("SELECT a FROM Account a WHERE a.isValid=:isValid");
                query.setParameter("isValid", true);
                List<Account> aList = query.list();
                return aList;
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if(session != null && session.isOpen()){
                    session.close();
                }
            }
        }
        
        return null;
    }
    
    
    public Account viewToUpdate(int id){
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            if(session !=null){
                Query query = session.createQuery("SELECT a FROM Account a WHERE a.id = :id");
                query.setParameter("id", id);
                Account account = (Account)query.uniqueResult();
                return account;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(session != null && session.isOpen()){
                session.close();
            }
        }
        
            
            
        
        
        
        return null;
    }
    
    
    public String updateAccount(Account  ac){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                
                Query query = session.createQuery("Update Account a set a.name=:name,a.balance=:bal,a.subtypeId.id=:subId where a.id=:aId");

                query.setParameter("name", ac.getName());
                query.setParameter("bal", ac.getBalance());
                query.setParameter("subId", ac.getSubtypeId().getId());
                query.setParameter("aId", ac.getId());


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
    
    public String DeleteAccount(Account  ac){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                
                Query query = session.createQuery("Update Account a set a.isValid=:isvalid where a.id=:aId");

                query.setParameter("isvalid", false);
                query.setParameter("aId", ac.getId());


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
