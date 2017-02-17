/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;


import com.vertec.hibe.model.Branch;
import com.vertec.hibe.model.BranchProductmaster;
import com.vertec.hibe.model.BranchStock;
import com.vertec.hibe.model.ProductMaster;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author vertec-r
 */
public class GINDAOImpl2{

    
    public Branch getBranch(int bid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("Branch.findByBranchId");
                query.setParameter("branchId", bid);

                Branch user = (Branch) query.uniqueResult();
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

    
//    public String SaveGINInfo(GinInfo gininfo) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//
//                session.save(gininfo);
//                session.flush();
//
//                transaction.commit();
//                return VertecConstants.SUCCESS;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return VertecConstants.ERROR;
//            } finally {
//                if (session != null && session.isOpen()) {
//                    session.close();
//                }
//            }
//        }
//        return null;
//    }

    
//    public String SaveGIN(Gin gin) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//
//                session.save(gin);
//                session.flush();
//
//                transaction.commit();
//                return VertecConstants.SUCCESS;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return VertecConstants.ERROR;
//            } finally {
//                if (session != null && session.isOpen()) {
//                    session.close();
//                }
//            }
//        }
//        return null;
//    }

    
//    public WarehouseStock getWarehouse(int id) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//
//        if (session != null) {
//            try {
//                Query query = session.getNamedQuery("WarehouseStock.findByWarehouseStockId");
//                query.setParameter("warehouseStockId", id);
//
//                WarehouseStock user = (WarehouseStock) query.uniqueResult();
//                return user;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (session != null && session.isOpen()) {
//                    session.close();
//                }
//            }
//        }
//
//        return null;
//    }

    
//    public String UpdateBranchStock(ProductMaster pmid, String branchId, String wid, String addqty) {
//
//        try {
//            System.out.println("IN UPDATE BRANCH STOCK METHOD.....");
//            SessionFactory sf = NewHibernateUtil.getSessionFactory();
//            Session ses = sf.openSession();
//            Transaction tr = ses.beginTransaction();
//            System.out.println("SESSION CREATED");
//            WarehouseStock whs = getWarehouse(Integer.parseInt(wid));
//
//            SQLQuery query = ses.createSQLQuery("SELECT bpm_id,branch_id FROM branch_productmaster WHERE product_master_id=:pmId AND branch_id=:branch_id AND type=:type");
//            query.setParameter("pmId", pmid.getProductMasterId());
//            query.setParameter("branch_id", branchId);
//            query.setParameter("type", whs.getType());
//            System.out.println("QUERY OK");
//            List<Object[]> inList = query.list();
//            boolean bool = true;
//            System.out.println("LINE 179");
//            for (Object[] list : inList) {
//                bool = false;
//                System.out.println("PRODUCT IS AVAILABLE IN BRANCH STOCK....");
//
//                SQLQuery query2 = ses.createSQLQuery("Update branch_productmaster set quantity=quantity+:qty where bpm_id=:id ");
//
//                query2.setParameter("id", list[0].toString());
//                query2.setParameter("qty", addqty);
//
//                query2.executeUpdate();
//
//                tr.commit();
//                return VertecConstants.SUCCESS;
//
//            }
//
//            if (bool) {
//                System.out.println("PRODUCT IS NOT AVAILABLE IN WAREHOUSE....");
//
//                Date d = new Date();
//
//                BranchProductmaster bpm = new BranchProductmaster();
//                bpm.setQuantity(Integer.parseInt(addqty));
//                bpm.setType(whs.getType());
//                bpm.setLastUpdatedDate(d);
//                bpm.setProductMasterId(pmid);
//                Branch b = new Branch(Integer.parseInt(branchId));
//                bpm.setBranchId(b);
//                ses.save(bpm);
//                ses.flush();
//                tr.commit();
//                return VertecConstants.SUCCESS;
//
//            }
//
//            ses.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return VertecConstants.ERROR;
//        }
//
//        return VertecConstants.ERROR;
//    }

//    public String UpdateBranchStock2(ProductMaster pmid, String branchId, String wid, String addqty, SysUser su) {
//
//        try {
//            System.out.println("IN UPDATE BRANCH STOCK2 METHOD.....");
//            SessionFactory sf = NewHibernateUtil.getSessionFactory();
//            Session ses = sf.openSession();
//            Transaction tr = ses.beginTransaction();
//            System.out.println("SESSION CREATED 2");
//            WarehouseStock whs = getWarehouse(Integer.parseInt(wid));
//
//            SQLQuery query = ses.createSQLQuery("SELECT branch_stock_id,product_id FROM branch_stock WHERE product_id=:proId AND branch_id=:branch_id");
//            query.setParameter("proId", pmid.getProductMasterId());
//            query.setParameter("branch_id", branchId);
//            System.out.println("QUERY OK");
//            List<Object[]> inList = query.list();
//            boolean bool = true;
//            System.out.println("LINE 214");
//            for (Object[] list : inList) {
//                bool = false;
//                System.out.println("PRODUCT IS AVAILABLE IN BRANCH STOCK....");
//
//                SQLQuery query2 = ses.createSQLQuery("Update branch_stock set quantity=quantity+:qty where branch_stock_id=:id ");
//
//                query2.setParameter("id", list[0].toString());
//                query2.setParameter("qty", addqty);
//
//                query2.executeUpdate();
//
//                tr.commit();
//                return VertecConstants.SUCCESS;
//
//            }
//
//            if (bool) {
//                System.out.println("PRODUCT IS NOT AVAILABLE IN BRANCH STOCK....");
//
//                Date d = new Date();
//
////                BranchProductmaster bpm = new BranchProductmaster();
////                bpm.setQuantity(Integer.parseInt(addqty));
////                bpm.setType(whs.getType());
////                bpm.setLastUpdatedDate(d);
////                bpm.setProductMasterId(pmid);
//                Branch b = new Branch(Integer.parseInt(branchId));
////                bpm.setBranchId(b);
//
//                BranchStock bs = new BranchStock();
//                bs.setBranchId(b);
//                bs.setProductId(pmid.getProductId());
//                bs.setQuantity(Integer.parseInt(addqty));
//                bs.setUpdatedBy(su);
//                bs.setAddedBy(su);
//                bs.setFirstAddedDate(d);
//                bs.setLastUpdated(d);
//
//                ses.save(bs);
//                ses.flush();
//                tr.commit();
//                return VertecConstants.SUCCESS;
//
//            }
//
//            ses.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return VertecConstants.ERROR;
//        }
//
//        return VertecConstants.ERROR;
//    }

    
    public String UpdateWarehouseStock(String whsId, String removeQty) {
        try {
            Session ses = NewHibernateUtil.getSessionFactory().openSession();
            Transaction tr = ses.beginTransaction();

            SQLQuery query2 = ses.createSQLQuery("Update warehouse_stock set qty=qty-:qty where warehouse_stock_id=:id ");

            query2.setParameter("id", whsId);
            query2.setParameter("qty", removeQty);

            query2.executeUpdate();

            tr.commit();
            return VertecConstants.SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
            return VertecConstants.ERROR;
        }

    }

    
    public String getItems(String id) {
        String html = "";

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("select p.product_code,p.product_name,gin.qty,gin.p_price,gin.s_price from gin gin inner join warehouse_stock whs on whs.warehouse_stock_id=gin.warehouse_stock_warehouse_stock_id inner join product_master pm on pm.product_master_id=whs.product_master_product_master_id inner join product p on pm.product_id=p.product_id where gin_info_gin_info_id='" + id + "'");

                List<Object[]> inList = query.list();
                for (Object[] list : inList) {
                    html += "<tr><td>" + list[0].toString() + " " + list[1].toString() + "</td><td>" + list[2].toString() + "</td><td>" + list[3].toString() + "</td><td>" + list[4].toString() + "</td><tr>";
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

    public String ProductFromCategory(String id) {
        String html = "";

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                String hql = "SELECT p.product_id,p.product_code,p.product_name,w.qty,pm.purchased_price,pm.selling_price,w.warehouse_stock_id FROM warehouse_stock w INNER JOIN product_master pm ON pm.product_master_id=w.product_master_product_master_id INNER JOIN product p ON pm.product_id=p.product_id INNER JOIN sys_user su ON su.sysuser_id=w.updated_by where w.qty>0 and product_category_id='"+id+"' ORDER BY p.product_code ASC";

                SQLQuery query = session.createSQLQuery(hql);

                List<Object[]> inList = query.list();
                for (Object[] list : inList) {
                    html += list[0]+":::::"+list[1]+":::::"+list[2]+":::::"+list[3]+":::::"+list[4]+":::::"+list[5]+":::::"+list[6]+";;;;;";
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
    public String ProductFromCategoryForPO(String id) {
        String html = "";

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                String hql = "SELECT p.product_id,p.product_code,p.product_name FROM product p where p.is_available='1' and p.product_category_id='"+id+"' ORDER BY p.product_code ASC";

                SQLQuery query = session.createSQLQuery(hql);

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
