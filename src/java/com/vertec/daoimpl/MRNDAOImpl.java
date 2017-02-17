/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;


import com.vertec.hibe.model.Mrn;
import com.vertec.hibe.model.MrnInfo;
import com.vertec.hibe.model.PrnInfo;
import com.vertec.hibe.model.Product;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author vertec-r
 */
public class MRNDAOImpl{

   
 public String savePRN(PrnInfo prn) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(prn);
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
    
    public String saveMRN(MrnInfo pi) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(pi);
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

    
    public String saveMRNItems(Mrn pi) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(pi);
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

    
    public Product getProduct(int pid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("Product.findByProductId");
                query.setParameter("productId", pid);
                Product user = (Product) query.uniqueResult();
                return user;

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

    
    public List<MrnInfo> getMRNinfo(String type, String sup, String from, String to) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("MrnInfo.findAll");
                List<MrnInfo> poList = query.list();
                return poList;

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

    
    public String getItems(String id) {
        String html = "";
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("select p.product_name,po.qty from prn_item po inner join product p on po.product_product_id=p.product_id where po.prn_info_id='" + id + "'");
                List<Object[]> inList = query.list();
                for (Object[] list : inList) {
                    html += "<tr><td>" + list[0].toString() + "</td><td>" + list[1].toString() + "</td><tr>";
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return html;
    }
    public String getItems1(String id) {
        String html = "";

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("select p.product_name,mrn.qty,mrn.available_qty from mrn mrn inner join product p on mrn.product_id=p.product_id where mrn.mrn_info_id='" + id + "'");

                List<Object[]> inList = query.list();
                for (Object[] list : inList) {
                    html += "<tr><td>" + list[0].toString() + "</td><td>" + list[1].toString() + "</td><td>" + list[2].toString() + "</td><tr>";
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return html;
    }

    
    public String ChangeStatus(String id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Date d=new Date();
                SQLQuery query = session.createSQLQuery("Update po_info set status=:status,received_date=:date where po_info_id=:po_info_id");

                query.setParameter("po_info_id", id);
                query.setParameter("date", d);
                query.setParameter("status", true);

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
    
    public String ProductFromCategory(String id) {
        String html = "";

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("SELECT product_id,product_code,product_name FROM product where product_category_id='"+id+"'");
                                            
                List<Object[]> inList = query.list();
                for (Object[] list : inList) {
                    html += list[0]+":::::"+list[1]+":::::"+list[2]+";;;;;";
                }
                
                if(!html.equals("")){
                html=html.substring(0, html.length()-5);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return html;
    }
}
