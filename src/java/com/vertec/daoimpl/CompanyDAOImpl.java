/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;


import com.vertec.hibe.model.BudgetPlan;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.CompanyGroup;
import com.vertec.hibe.model.GtnInfo;
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
public class CompanyDAOImpl {
    
    public String updateCompany(int cgid,String name) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE  CompanyGroup as g set g.name=:name WHERE g.id =:cgid");

                query.setParameter("cgid", cgid);
                query.setParameter("name", name);
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
    
    
    
    
    
    
    
    
    public CompanyGroup viewCompanyGroup(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT g  FROM CompanyGroup g WHERE g.id=:id");
                query.setParameter("id", id);
                CompanyGroup g = (CompanyGroup) query.uniqueResult();
//                List<Gtn> gList =(List<Gtn>) query.list();
                
                return g;
                
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        
        }
        
        
        return null;
    }
    
    public String saveCompany(Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                session.save(com);
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
    public List<Company> viewAllCompany(){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT c  FROM Company c ");
//                query.setParameter("pid", id);
                List<Company> cList =(List<Company>) query.list();
                
                return cList;
                
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        
        }
        
        
        return null;
    }
    public Company viewCompanyByid(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT c  FROM Company c WHERE c.id=:cid ");
                query.setParameter("cid", id);
                Company cList =(Company) query.uniqueResult();
                
                return cList;
                
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        
        }
        
        
        return null;
    }
    
    public String updateCompanyDetails(Company c) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE  Company as c set c.companyName=:name,c.address=:address,c.contactNo=:contact,c.email=:email WHERE c.id =:cid");

                query.setParameter("name", c.getCompanyName());
                query.setParameter("address", c.getAddress());
                query.setParameter("contact", c.getContactNo());
                query.setParameter("email", c.getEmail());
                query.setParameter("cid", c.getId());
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
