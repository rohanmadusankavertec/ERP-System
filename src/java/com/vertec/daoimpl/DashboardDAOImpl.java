/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vertec.daoimpl;

import com.vertec.hibe.model.Company;
import com.vertec.util.NewHibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author vertec-r
 */
public class DashboardDAOImpl {
    public String getLiabilities(Company company) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
//                Query query = session.createQuery("SELECT COUNT(e.id) FROM Employee e WHERE e.isValid=:isValid");
                Query query = session.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.companyId=:com AND t.credit.subtypeId.name='Creditors'");
                query.setParameter("com", company);
                double emcount =(double) query.uniqueResult();
                
                System.out.println("emcount");
                return emcount+"";

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return "0";
    }
     public String getDebtorsAmount(Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.companyId=:com AND t.debit.subtypeId.name='Debtors'");
                query.setParameter("com", com);
                if(query.uniqueResult() != null){
                    double emcount =(double) query.uniqueResult();
                    return emcount+"";
                    
                }
                
                

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return "0";
    }
     public String getIncomeAmount(Company com) {
         System.out.println("mmmmmmmm");
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
//                Query query = session.createQuery("SELECT COUNT(c.id) FROM Package c WHERE c.isValid=:isValid");
                Query query = session.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.companyId=:com AND t.credit.subtypeId.typeId.name='Income'");
                query.setParameter("com", com);
                if(query.uniqueResult() != null){
                double emcount =(double) query.uniqueResult();
                System.out.println("..........."+emcount);
                return emcount+"";
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return "0";
    }
     public String getExpences(Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
//                Query query = session.createQuery("SELECT COUNT(c.id) FROM Service c WHERE c.isValid=:isValid");
                Query query = session.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.companyId=:com AND t.debit.subtypeId.typeId.name='Expense'");
                query.setParameter("com", com);
                if(query.uniqueResult() != null){
                double emcount =(double) query.uniqueResult();
                System.out.println("..........."+emcount);
                return emcount+"";
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return "0";
    }
     
     
     
     
     public String getAccount(Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT COUNT(a.id) FROM Account a WHERE a.companyId=:com AND a.isValid=:valid");
                query.setParameter("com", com);
                query.setParameter("valid", true);
                long cucount =(long) query.uniqueResult();
                
                return cucount+"";

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return "0";
    }
     public String getincompletedProject() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT COUNT(c.id) FROM ProjectDetails c WHERE c.statusId.status!='complete'");
                 long cucount =(long) query.uniqueResult();
                
                return cucount+"";

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return "0";
    }
     public String getHoldProject() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT COUNT(c.id) FROM ProjectDetails c WHERE c.statusId.status='hold'");
               long cucount =(long) query.uniqueResult();
                
                return cucount+"";

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return "0";
    }
     public String getOngoingProject() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT COUNT(c.id) FROM ProjectDetails c WHERE c.statusId.status='continue'");
               long cucount =(long) query.uniqueResult();
                
                return cucount+"";

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return "0";
    }
}
