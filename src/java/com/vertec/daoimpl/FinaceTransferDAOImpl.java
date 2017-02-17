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
public class FinaceTransferDAOImpl {
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
    
    public String saveDrawing(com.vertec.hibe.model.Transaction t){
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
