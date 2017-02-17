/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.hibe.model.BranchProductmaster;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Gin;
import com.vertec.hibe.model.GinInfo;
import com.vertec.hibe.model.Grn;
import com.vertec.hibe.model.GrnInfo;
import com.vertec.hibe.model.GrnPayment;
import com.vertec.hibe.model.Product;
import com.vertec.hibe.model.ProductMaster;
import com.vertec.hibe.model.Supplier;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author vertec-r
 */
public class GINDAOImpl {

    public int getSupplier(String po) {
        int sid = 0;
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session ses = sf.openSession();
        SQLQuery query = ses.createSQLQuery("SELECT s.supplier_id,s.supplier_name FROM po_info pi inner join supplier s ON s.supplier_id=pi.supplier_supplier_id WHERE pi.po_info_id='" + po + "'");
        List<Object[]> inList = query.list();
        for (Object[] list : inList) {
            sid = Integer.parseInt(list[0].toString());
        }
        ses.close();
        return sid;
    }

    public JSONObject LoadMRN(String mrn) {

        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session ses = sf.openSession();
        SQLQuery query = ses.createSQLQuery("SELECT mrn.product_id,p.product_name,p.product_code,mrn.available_qty FROM mrn mrn INNER JOIN product p on p.product_id=mrn.product_id WHERE mrn.mrn_info_id='" + mrn + "'");

        List<Object[]> inList = query.list();
        JSONObject mainObject = new JSONObject();
        JSONArray jarr = new JSONArray();
        JSONObject product = null;
        for (Object[] list : inList) {
            product = new JSONObject();
            product.put("pid", list[0].toString());
            product.put("pname", list[1].toString());
            product.put("pcode", list[2].toString());
            product.put("qty", list[3].toString());

            SQLQuery query2 = ses.createSQLQuery("SELECT pm.product_master_id,pm.selling_price FROM product_master pm  WHERE pm.product_id='" + list[0].toString() + "'");
            List<Object[]> inList2 = query2.list();
            JSONArray pmarr = new JSONArray();
            JSONObject pmob = null;
            for (Object[] list2 : inList2) {
                pmob = new JSONObject();
                pmob.put("pmid", list2[0].toString());
                pmob.put("sp", list2[1].toString());
                pmarr.add(pmob);
            }
            product.put("pmarr", pmarr);
            jarr.add(product);
        }
        mainObject.put("data", jarr);

        ses.close();

        return mainObject;
    }

    public String SaveGRNPayment(GrnPayment grnPay) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(grnPay);
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

    public String SaveGIN(GinInfo gininfo) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(gininfo);
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

    public String SaveGINItem(Gin gin_items) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(gin_items);
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

    public String UpdateBranchStock(Gin ginItems) {
        try {
            Session ses = NewHibernateUtil.getSessionFactory().openSession();
            Transaction tr = ses.beginTransaction();
            System.out.println("PRODUCT IS NOT AVAILABLE IN BRANCH PRODUCT MASTER....");
            
            SQLQuery query2 = ses.createSQLQuery("Update branch_productmaster set quantity=quantity-:qty where bpm_id=:id ");
            query2.setParameter("id", ginItems.getBpmId().getBpmId());
            query2.setParameter("qty", ginItems.getQty());
            query2.executeUpdate();
            tr.commit();

            ses.close();
            return VertecConstants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return VertecConstants.ERROR;
        }
    }

    public String UpdateMRN(String poId, String productId, String qty) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("Update mrn set available_qty=available_qty-:qty where product_id=:productId and mrn_info_id=:mrnId");

                query.setParameter("mrnId", poId);
                query.setParameter("productId", productId);
                query.setParameter("qty", qty);

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

    public BranchProductmaster GetBranchProductMaster(String ProductMaster, Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {

                Query query = session.createQuery("SELECT bpm FROM BranchProductmaster bpm WHERE bpm.companyId=:com AND bpm.productMasterId.productMasterId=:productmaster");

                query.setParameter("com", com);
                query.setParameter("productmaster", Integer.parseInt(ProductMaster));
                BranchProductmaster inList = (BranchProductmaster) query.uniqueResult();
                return inList;

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

//    public String UpdateWarehouseStock(Grn grnItems, Supplier supplier, SysUser user) {
//        try {
//            System.out.println("IN UPDATE WAREHOUSE STOCK METHOD.....");
//            SessionFactory sf = NewHibernateUtil.getSessionFactory();
//            Session ses = sf.openSession();
//            Transaction tr = ses.beginTransaction();
//            System.out.println("SESSION CREATED");
//            String pmid = "0";
//            SQLQuery query = ses.createSQLQuery("SELECT pm.product_master_id,whs.warehouse_stock_id FROM warehouse_stock whs INNER JOIN product_master pm on whs.product_master_product_master_id=pm.product_master_id WHERE pm.purchased_price=:pprice AND pm.selling_price=:sprice AND pm.product_id=:pId AND whs.type=:type");
//            query.setParameter("pprice", grnItems.getPPrice());
//            query.setParameter("sprice", grnItems.getSPrice());
//            query.setParameter("pId", grnItems.getProductProductId().getProductId());
//            System.out.println("BOOLEAN TYPE " + supplier.getType());
//            query.setParameter("type", supplier.getType());
//            System.out.println("QUERY OK");
//            List<Object[]> inList = query.list();
//            boolean bool = true;
//            System.out.println("LINE 179");
//            for (Object[] list : inList) {
//                bool = false;
//                System.out.println("PRODUCT IS NOT AVAILABLE IN WAREHOUSE....");
//                pmid = list[0].toString();
//
//                SQLQuery query2 = ses.createSQLQuery("Update warehouse_stock set qty=qty+:qty where warehouse_stock_id=:id ");
//
//                query2.setParameter("id", list[1].toString());
//                query2.setParameter("qty", grnItems.getQty());
//
//                query2.executeUpdate();
//
//                tr.commit();
//                return VertecConstants.SUCCESS;
//
//            }
//
//            if (bool) {
//                System.out.println("PRODUCT IS AVAILABLE IN WAREHOUSE....");
//
//                Date d = new Date();
//                WarehouseStock wh = new WarehouseStock();
//                wh.setAddedBy(user);
//                wh.setUpdatedBy(user);
//                wh.setQty(grnItems.getQty());
//                wh.setType(supplier.getType().booleanValue());
//                wh.setAddedDate(d);
//                String promid = getProductMaster(grnItems.getProductProductId().getProductId().toString(), grnItems.getPPrice().toString(), grnItems.getSPrice().toString());
//                System.out.println("PRODUCT MASTER ID : " + promid);
//                ProductMaster pm = new ProductMaster(Integer.parseInt(promid));
//                wh.setProductMasterProductMasterId(pm);
//                wh.setWarehouseWarehouseId(getWarehouse("1"));
//                wh.setUpdatedDate(d);
//
//                ses.save(wh);
//                ses.flush();
//
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
//        return VertecConstants.FAILED;
//    }
    public String getProductMaster(String pid, String pprice, String sprice) {
        String pmid = "0";
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session ses = sf.openSession();
        Transaction tr = ses.beginTransaction();

        SQLQuery query = ses.createSQLQuery("SELECT product_id,product_master_id FROM product_master WHERE product_id=:productId AND purchased_price=:pprice AND selling_price=:sprice");
        query.setParameter("productId", pid);
        query.setParameter("pprice", pprice);
        query.setParameter("sprice", sprice);
        List<Object[]> inList = query.list();
        boolean bool = true;
        for (Object[] list : inList) {
            System.out.println("PRODUCT MASTER IS EXIST...............ID : " + list[1].toString());
            bool = false;
            pmid = list[1].toString();
        }

        if (bool) {
            try {
                System.out.println("PRODUCT MASTER IS NOT EXIST.............. ");

                ProductMaster pm = new ProductMaster();
                pm.setPurchasedPrice(Double.valueOf(pprice));
                pm.setSellingPrice(Double.valueOf(sprice));
                Product pro = new PoDAOImpl().getProduct(Integer.valueOf(pid));
                pm.setProductId(pro);
                pm.setIsAvailable(true);
                ses.save(pm);
                ses.flush();
                tr.commit();

                SQLQuery query1 = ses.createSQLQuery("SELECT product_id,product_master_id FROM product_master WHERE product_id=:productId AND purchased_price=:pprice AND selling_price=:sprice");
                query1.setParameter("productId", Integer.valueOf(pid));
                query1.setParameter("pprice", Double.valueOf(pprice));
                query1.setParameter("sprice", Double.valueOf(sprice));
                List<Object[]> inList1 = query1.list();

                for (Object[] list : inList1) {
                    System.out.println("PRODUCT MASTER IS CREATED...............ID : " + list[1].toString());
                    bool = false;
                    pmid = list[1].toString();
                    return list[1].toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        ses.close();
        return pmid;
    }

    public ProductMaster getpMasterObject(String id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("ProductMaster.findByProductMasterId");
                query.setParameter("productMasterId", Integer.parseInt(id));

                ProductMaster user = (ProductMaster) query.uniqueResult();
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

//    public Warehouse getWarehouse(String id) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//
//        if (session != null) {
//            try {
//                Query query = session.getNamedQuery("Warehouse.findByWarehouseId");
//                query.setParameter("warehouseId", Integer.parseInt(id));
//
//                Warehouse user = (Warehouse) query.uniqueResult();
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
    public Supplier getSupplierObj(String sid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("Supplier.findBySupplierId");
                query.setParameter("supplierId", Integer.parseInt(sid));

                Supplier user = (Supplier) query.uniqueResult();
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

    public String getItems(String id) {
        String html = "";
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT gin FROM Gin gin WHERE gin.ginInfoId.id=:gininfo");
                query.setParameter("gininfo", Integer.parseInt(id));
                List<Gin> inList = query.list();
                for (Gin g : inList) {
                    html += "<tr><td>" + g.getBpmId().getProductMasterId().getProductId().getProductName() + "</td><td>" + g.getQty() + "</td><td>" + g.getBpmId().getProductMasterId().getSellingPrice() + "</td><td>" + g.getBpmId().getProductMasterId().getSellingPrice()*g.getQty()  + "</td><tr>";
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
