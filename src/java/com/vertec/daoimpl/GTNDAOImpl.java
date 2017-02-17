/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.hibe.model.Branch;
import com.vertec.hibe.model.BranchProductmaster;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Gtn;
import com.vertec.hibe.model.GtnInfo;
import com.vertec.hibe.model.Product;
import com.vertec.hibe.model.ProductMaster;
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
public class GTNDAOImpl {
    
    public List<Branch> loadBranch(Company com){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT b  FROM Branch b WHERE b.companyId=:com");
                query.setParameter("com", com);
                List<Branch> bList =(List<Branch>) query.list();
                
                return bList;
                
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
    public List<BranchProductmaster> loadProductCategory(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT b  FROM BranchProductmaster b WHERE b.branchId.branchId=:bid");
                query.setParameter("bid", id);
                List<BranchProductmaster> bList =(List<BranchProductmaster>) query.list();
                
                return bList;
                
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
    public List<Product> loadProduct(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT p  FROM Product p WHERE p.productCategoryId.productCategoryId=:cid");
                query.setParameter("cid", id);
                List<Product> bList =(List<Product>) query.list();
                
                return bList;
                
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
    public List<ProductMaster> loadSellingPrice(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT p  FROM ProductMaster p WHERE p.productId.productId=:pid");
                query.setParameter("pid", id);
                List<ProductMaster> bList =(List<ProductMaster>) query.list();
                
                return bList;
                
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
    public int loadAvailableQty(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT b.quantity  FROM BranchProductmaster b WHERE b.productMasterId.productMasterId=:pmid");
                query.setParameter("pmid", id);
                int bm =(int) query.uniqueResult();
                
                return bm;
                
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        
        }
        
        
        return 0;
    }
    
    public String saveGtn(GtnInfo gf) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                session.save(gf);
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
    public String saveGtnProduct(Gtn gtn) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                session.save(gtn);
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
    public List<GtnInfo> viewGTNInfo(Branch branch){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT g  FROM GtnInfo g WHERE g.fromBranch=:fbranch");
                query.setParameter("fbranch", branch);
                List<GtnInfo> gList =(List<GtnInfo>) query.list();
                
                return gList;
                
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
    public List<GtnInfo> viewGTNInfoByToBranch(int bid){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT g  FROM GtnInfo g WHERE g.toBranch.branchId=:bid AND g.isPending=:pending ");
                query.setParameter("bid", bid);
                query.setParameter("pending", true);
                List<GtnInfo> gList =(List<GtnInfo>) query.list();
                
                return gList;
                
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
    public Gtn getBranchId(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT g  FROM Gtn g WHERE g.gtninfoId.gtninfoId=:gid ");
                query.setParameter("gid", id);
                Gtn g =(Gtn) query.uniqueResult();
                
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
    public List<Gtn> viewGTN(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT g  FROM Gtn g WHERE g.gtninfoId.gtninfoId=:gid ");
                query.setParameter("gid", id);
                List<Gtn> gList =(List<Gtn>) query.list();
                
                return gList;
                
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
    public GtnInfo viewGTNinfo(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT g  FROM GtnInfo g WHERE g.gtninfoId=:gifid");
                query.setParameter("gifid", id);
                GtnInfo gtninfo = (GtnInfo) query.uniqueResult();
//                List<Gtn> gList =(List<Gtn>) query.list();
                
                return gtninfo;
                
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
    
    
    public String updateQty(int bpmid,int qty) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE BranchProductmaster as b set b.quantity=:qty  where b.bpmId=:bpmid");

                query.setParameter("qty", qty);
                query.setParameter("bpmid", bpmid);
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
    public String updateAction(int bpmid,Boolean bool) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE GtnInfo as g set g.isPending=:pending,g.isValid=:valid  WHERE g.gtninfoId=:bpmid");

                query.setParameter("pending", false);
                query.setParameter("valid", bool);
                query.setParameter("bpmid", bpmid);
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
    public String updateCancelGTN(int pmid,int newQty) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE BranchProductmaster as b set b.quantity=:newQty WHERE b.productMasterId.productMasterId =:pmid");

//                query.setParameter("pending", false);
                query.setParameter("newQty", newQty);
                query.setParameter("pmid", pmid);
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
