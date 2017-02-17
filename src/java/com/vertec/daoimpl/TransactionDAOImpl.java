/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vertec.daoimpl;

import com.vertec.hibe.model.Account;
import com.vertec.hibe.model.Company;
import com.vertec.util.NewHibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author User
 */
public class TransactionDAOImpl {
    
    public List<Account> getListOfAccounts(Company cId){
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a FROM Account a WHERE a.companyId=:comId");
                query.setParameter("comId", cId);
                List<Account> accList = query.list();
                
                return accList;

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
