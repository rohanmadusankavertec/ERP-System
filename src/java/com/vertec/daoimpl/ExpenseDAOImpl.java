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
import org.hibernate.Transaction;

/**
 *
 * @author Java-Dev-Ruchira
 */
public class ExpenseDAOImpl {
    
    public List<Account> viewAllOfAccount(Company com){
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if(session != null){
            try {
                Query query = session.createQuery("SELECT a FROM Account a WHERE a.subtypeId.name=:sId AND a.companyId=:cID AND a.isValid=:valid");
                query.setParameter("sId","Expense");
                query.setParameter("cID",com);
                query.setParameter("valid",true);
                
                
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
    
    public List<Account> viewAccountsByPayId(String id,int com){
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        
        if(session != null){
            try {
                Query query = session.createQuery("SELECT a FROM Account a WHERE a.subtypeId.name=:sId AND a.isValid=:valid AND a.companyId.id=:cId");
                query.setParameter("sId", id);
                query.setParameter("valid", true);
                query.setParameter("cId", com);

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
        
        return  null;
    }
    
    public String saveExpense(com.vertec.hibe.model.Transaction t){
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
    
}
