/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.hibe.model.BranchProductmaster;
import com.vertec.hibe.model.Company;
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
public class GRNDAOImpl {

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

    public JSONObject LoadPO(String poid) {

        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session ses = sf.openSession();
        SQLQuery query = ses.createSQLQuery("SELECT po.product_product_id,p.product_name,p.product_code,po.available_qty,po.price,po.total FROM purchasing_order po INNER JOIN product p on p.product_id=po.product_product_id WHERE po.po_info_po_info_id='" + poid + "'");

        List<Object[]> inList = query.list();
        JSONObject mainObject = new JSONObject();
        JSONArray jarr = new JSONArray();
        JSONObject product = null;
        for (Object[] list : inList) {
            product = new JSONObject();
            product.put("pid", list[0].toString());
            product.put("pcode", list[2].toString());
            product.put("pname", list[1].toString());
            product.put("qty", list[3].toString());
            product.put("price", list[4].toString());
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
    public String SaveGRN(GrnInfo grninfo) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(grninfo);
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

    public String SaveGRNItem(Grn grn_items) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(grn_items);
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

    public String UpdateBranchStock(Grn grnItems, Supplier supplier, SysUser user,Company company) {
        try {
            System.out.println("IN UPDATE Branch STOCK METHOD.....");
            SessionFactory sf = NewHibernateUtil.getSessionFactory();
            Session ses = sf.openSession();
            Transaction tr = ses.beginTransaction();
            System.out.println("SESSION CREATED");
            String pmid = "0";
            SQLQuery query = ses.createSQLQuery("SELECT pm.product_master_id,whs.bpm_id FROM branch_productmaster whs INNER JOIN product_master pm on whs.product_master_id=pm.product_master_id WHERE pm.purchased_price=:pprice AND pm.selling_price=:sprice AND pm.product_id=:pId AND whs.type=:type");
            query.setParameter("pprice", grnItems.getPPrice());
            query.setParameter("sprice", grnItems.getSPrice());
            query.setParameter("pId", grnItems.getProductProductId().getProductId());
            query.setParameter("type", supplier.getType());
            System.out.println("QUERY OK");
            List<Object[]> inList = query.list();
            boolean bool = true;
            System.out.println("LINE 139");
            for (Object[] list : inList) {
                bool = false;
                System.out.println("PRODUCT IS NOT AVAILABLE IN BRANCH PRODUCT MASTER....");
                pmid = list[0].toString();

                SQLQuery query2 = ses.createSQLQuery("Update branch_productmaster set quantity=quantity+:qty where bpm_id=:id ");
                query2.setParameter("id", list[1].toString());
                query2.setParameter("qty", grnItems.getQty());
                query2.executeUpdate();
                tr.commit();
                return VertecConstants.SUCCESS;
            }

            if (bool) {
                System.out.println("PRODUCT IS NOT AVAILABLE IN PRODUCT MASTER....");
                Date d = new Date();
                BranchProductmaster bpm = new BranchProductmaster();
                bpm.setBranchId(user.getBranchBranchId());
                bpm.setLastUpdatedDate(d);
                 String promid = getProductMaster(grnItems.getProductProductId().getProductId().toString(), grnItems.getPPrice().toString(), grnItems.getSPrice().toString());
                System.out.println("PRODUCT MASTER ID : " + promid);
                ProductMaster pm = new ProductMaster(Integer.parseInt(promid));
                bpm.setProductMasterId(pm);
                bpm.setQuantity(grnItems.getQty());
                bpm.setType(supplier.getType());
                bpm.setCompanyId(company);
                ses.save(bpm);
                ses.flush();
                tr.commit();
                return VertecConstants.SUCCESS;
            }
            ses.close();
        } catch (Exception e) {
            e.printStackTrace();
            return VertecConstants.ERROR;
        }
        return VertecConstants.FAILED;
    }

    public String UpdatePO(String poId, String productId, String qty) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("Update purchasing_order set available_qty=available_qty-:qty where product_product_id=:productId and po_info_po_info_id=:poId");

                query.setParameter("poId", poId);
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
    public String getItems(String id) {
        String html = "";

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("select p.product_name,grn.qty,grn.p_price,grn.s_price,grn.total from grn grn inner join product p on grn.product_product_id=p.product_id where grn.grn_info_grn_info_id='" + id + "'");

                List<Object[]> inList = query.list();
                for (Object[] list : inList) {
                    html += "<tr><td>" + list[0].toString() + "</td><td>" + list[1].toString() + "</td><td>" + list[2].toString() + "</td><td>" + list[3].toString() + "</td><td>" + list[4].toString() + "</td><tr>";
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
