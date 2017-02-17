/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.hibe.model.Account;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Loan;
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
public class LoanPayDAOImpl {
    
    public List<Account> getLoanAccount(Company com,String sName){
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if(session != null ){
            Query query = session.createQuery("SELECT a FROM Account a WHERE a.companyId=:comp AND a.isValid=:valid AND a.subtypeId.name=:subName ");
            query.setParameter("comp", com);
            query.setParameter("valid", true);
            query.setParameter("subName", sName);
            
            List<Account> aList = query.list();
            
            return aList;
        }
        
        return null;
    }
    
    public List<Account> loadAccountByPayName(String sName,Company com){
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            if(session != null){
            Query query = session.createQuery("SELECT a FROM Account a WHERE a.isValid=:valid AND a.subtypeId.name=:sname AND a.companyId=:com");
            query.setParameter("valid", true);
            query.setParameter("sname", sName);
            query.setParameter("com", com);
            
            List<Account> acList = query.list();
            
            return acList;
        }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(session != null && session.isOpen() ){
                session.close();
                
            }
        }
        
        
        return null;
    }
    
    public List<Loan> loadLoanByAccount(int id){
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            if(session != null){
//            Query query = session.createQuery("SELECT a FROM Account a WHERE a.isValid=:valid AND a.subtypeId.name=:sname AND a.companyId=:com");
            Query query = session.createQuery("SELECT l FROM Loan l WHERE l.accountId=:acc");
            query.setParameter("acc", new Account(id));
            List<Loan> acList = query.list();
            
            return acList;
        }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(session != null && session.isOpen() ){
                session.close();
                
            }
        }
        
        
        return null;
    }
    
    public String saveLoanPayment(com.vertec.hibe.model.Transaction t){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(t);
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
    
    public String updateLoanPaid(int Id,double price){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                
                Query query = session.createQuery("Update Loan a set a.paid=:p where a.id=:lId");


                query.setParameter("p", price);
                query.setParameter("lId", Id);


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
    
    public double getPaidAmount(int id){
        System.out.println("mmmmmmmmmmmm");
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            if(session != null){
//            Query query = session.createQuery("SELECT a FROM Account a WHERE a.isValid=:valid AND a.subtypeId.name=:sname AND a.companyId=:com");
            Query query = session.createQuery("SELECT l.paid FROM Loan l WHERE l.id=:lId");
            query.setParameter("lId", id);
//            List<Loan> acList = query.list();
              double amt =(double) query.uniqueResult();
                System.out.println("l---- "+amt);
            return amt;
        }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(session != null && session.isOpen() ){
                session.close();
                
            }
        }
        
        
        return 0;
    }
    public Loan getloan(int id){
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            if(session != null){
//            Query query = session.createQuery("SELECT a FROM Account a WHERE a.isValid=:valid AND a.subtypeId.name=:sname AND a.companyId=:com");
            Query query = session.createQuery("SELECT l FROM Loan l WHERE l.id=:lId");
            query.setParameter("lId", id);
//            List<Loan> acList = query.list();
              
              Loan loan =(Loan) query.uniqueResult();
                
            return loan;
        }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(session != null && session.isOpen() ){
                session.close();
                
            }
        }
        
        
        return null;
    }
    
}
