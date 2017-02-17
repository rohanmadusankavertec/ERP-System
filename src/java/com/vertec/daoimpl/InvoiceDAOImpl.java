/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;


import com.vertec.hibe.model.Branch;
import com.vertec.hibe.model.BranchStock;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.DelInvoice;
import com.vertec.hibe.model.Invoice;
import com.vertec.hibe.model.InvoiceItem;
import com.vertec.hibe.model.InvoicePayment;
import com.vertec.hibe.model.OutstandigInvoice;
import com.vertec.hibe.model.Payment;
import com.vertec.hibe.model.ProductMaster;
import com.vertec.hibe.model.ReturnStock;
import com.vertec.hibe.model.SysUser;
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
 * @author User
 */
public class InvoiceDAOImpl{

    
    public String saveInvoice(Invoice invoice) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(invoice);
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

    /**
     * call from case "LoadCustomerInvoice" in InvoiceController
     *
     * @param customerId
     * @return List<Object[]> invoiceListAccordingCustomers
     */
    
    public List<Object[]> invoiceForCustomers(int customerId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT i.invoice_id,i.invoiced_date,i.tot_after_discount as payemble FROM outstandig_invoice oi inner join invoice i on oi.invoice_id=i.invoice_id  where oi.balance_amount>0 and i.customer_id=:customerId and i.is_valid=:is_valid and i.invoice_id not in (select invoice_id from invoice_payment)");
                query.setParameter("customerId", customerId);
                query.setParameter("is_valid", true);
                List<Object[]> inList = query.list();
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
public List<Object[]> invoiceForAll(String customerId,String branch,String type,String from, String to, Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                String sql="SELECT i.invoice_id,i.invoiced_date,i.tot_after_discount as payemble FROM outstandig_invoice oi inner join invoice i on oi.invoice_id=i.invoice_id  where i.is_valid='1' and i.company_id='"+com.getId()+"'";
                if(type.equals("2")){
                sql+=" and i.customer_id='"+customerId+"'";
                }else if(type.equals("3")){
                sql+=" and i.invoiced_date between '"+from+"' and '"+to+"'";
                }else if(type.equals("4")){
                 sql+=" and i.invoiced_date like '"+from+"%' ";
                }else if(type.equals("5")){
                 sql+=" and i.branch_id='"+branch+"' ";
                }
                SQLQuery query = session.createSQLQuery(sql);
                List<Object[]> inList = query.list();
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
    /**
     * call from InvoiceController--> case
     * "ViewInvoice","UpdateInvoice","DeleteInvoice","UpdateAll"
     *
     * @param invoiceId
     * @return Invoice
     */

    public Invoice getInvoice(int invoiceId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("From Invoice i Where i.invoiceId=:invoiceId");
                query.setParameter("invoiceId", invoiceId);

                Invoice invoice = (Invoice) query.uniqueResult();
                return invoice;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }
    public List<Invoice> getInvoices(Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT i FROM Invoice i WHERE i.isValid=:isValid AND i.companyId=:com");
                query.setParameter("isValid", true);
                query.setParameter("com", com);

                List<Invoice> invoice = (List<Invoice>) query.list();
                return invoice;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }
    public List<ReturnStock> getReturnStock(Branch branch) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT rs FROM ReturnStock rs WHERE rs.qty>0 AND rs.branchId=:branch ");
                query.setParameter("branch", branch);
                List<ReturnStock> invoice = (List<ReturnStock>) query.list();
                return invoice;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }
    public List<InvoiceItem> getInvoiceItems(int invoiceId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT i FROM InvoiceItem i Where i.invoiceId=:invoiceId");
                query.setParameter("invoiceId", invoiceId);

                List<InvoiceItem> invoice = (List<InvoiceItem>) query.uniqueResult();
                return invoice;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }
    public List<Invoice> getPendingInvoice() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT i FROM Invoice i WHERE i.isPending=:isPending AND i.isValid=:isValid");
                query.setParameter("isPending", true);
                query.setParameter("isValid", true);
                List<Invoice> invoice = (List<Invoice>) query.list();
                return invoice;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }
    
     public String updateInvoicePendingToSuccess(int invoiceId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("UPDATE Invoice as i set i.isPending=:isPending where i.invoiceId=:invoiceId");
                query.setParameter("isPending", false);
                query.setParameter("invoiceId", invoiceId);
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
    
    

//    public String updateWarehouseStock(WarehouseStock wsid,int qty){
//        
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//
//                Query query = session.createQuery("UPDATE WarehouseStock set qty=qty-:qty where warehouse_stock_id=:wsId");
//
//                query.setParameter("wsId", wsid.getWarehouseStockId());
//                query.setParameter("qty", qty);
//                query.executeUpdate();
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
//        
//        return null;
//    
//    
//    }
    
//     public WarehouseStock getWarehouseStock(int wsId) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//
//        if (session != null) {
//            try {
//                Query query = session.createQuery("SELECT w FROM WarehouseStock w WHERE w.warehouseStockId = :warehouseStockId");
//                query.setParameter("warehouseStockId", wsId);
//
//                WarehouseStock ws = (WarehouseStock) query.uniqueResult();
//                return ws;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            } finally {
//                if (session != null && session.isOpen()) {
//                    session.close();
//                }
//            }
//        }
//
//        return null;
//    }
    /**
     * call from InvoiceController--> case
     * "ViewInvoice","ViewInvoice","ViewInvoice","DeleteInvoice"
     *
     * @param invoiceId
     * @return List<InvoiceItem> according to invoiceId
     */
    
    public List<InvoiceItem> getInvoiceItem(int invoiceId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.createQuery("From InvoiceItem i Where i.invoiceId.invoiceId=:invoiceId");
                query.setParameter("invoiceId", invoiceId);

                List<InvoiceItem> iiList = query.list();
                return iiList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }

    
    public OutstandigInvoice getOutstanding(int invoiceId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                System.out.println("INVOICE NO IS : "+invoiceId);
                Query query = session.createQuery("From OutstandigInvoice i Where i.invoiceId.invoiceId=:invoiceId");
                query.setParameter("invoiceId", invoiceId);

                OutstandigInvoice iiList =(OutstandigInvoice) query.uniqueResult();
                return iiList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }
    
    /**
     * call from InvoiceController--> case "DeleteInvoice" Update InvoiceStatus
     *
     * @param invoiceId
     * @return
     */
    
    public String updateInvoiceStatus(int invoiceId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE Invoice as i set  i.isValid=:isValid where i.invoiceId=:invoiceId");

                query.setParameter("isValid", false);
                query.setParameter("invoiceId", invoiceId);
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

    /**
     * call from InvoiceController--> case "DeleteInvoice" Update
     * OutstandingStatus
     *
     * @param outId
     * @return
     */
    
    public String updateOutstandingStatus(int outId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE OutstandigInvoice as o set  o.outStatus=:isValid where o.invoiceId.invoiceId=:invoiceId");

                query.setParameter("isValid", false);
                query.setParameter("invoiceId", outId);
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

    /**
     * call from InvoiceController--> case "UpdateAll"
     *
     * @param invoiceItem
     * @return
     */
    
    public String updateInvoiceItem(InvoiceItem invoiceItem) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE InvoiceItem as i set  i.quantity=:quantity,i.totAmount=:totAmount,i.discount=:discount,i.totAfterDis=:totAfterDis where i.invoiceItemId=:invoiceItemId");

                query.setParameter("quantity", invoiceItem.getQuantity());
                query.setParameter("totAmount", invoiceItem.getTotAmount());
                query.setParameter("discount", invoiceItem.getDiscount());
                query.setParameter("totAfterDis", invoiceItem.getTotAfterDis());
                query.setParameter("invoiceItemId", invoiceItem.getInvoiceItemId());
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

    /**
     * call from InvoiceController--> case "UpdateAll"
     *
     * @param invoiceItemId
     * @return
     */
    
    public InvoiceItem loadInvoiceItem(int invoiceItemId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.createQuery("From InvoiceItem i Where i.invoiceItemId=:invoiceItemId");
                query.setParameter("invoiceItemId", invoiceItemId);

                InvoiceItem invoiceItem = (InvoiceItem) query.uniqueResult();
                return invoiceItem;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }

    /**
     * call from InvoiceController--> case "UpdateAll"
     *
     * @param invoice
     * @return
     */
    
    public String updateInvoice(Invoice invoice) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE Invoice as i set  i.invoiceTotal=:invoiceTotal,i.discount=:discount,i.totAfterDiscount=:totAfterDiscount where i.invoiceId=:invoiceId");

                query.setParameter("invoiceTotal", invoice.getInvoiceTotal());
                query.setParameter("discount", invoice.getDiscount());
                query.setParameter("totAfterDiscount", invoice.getTotAfterDiscount());
                query.setParameter("invoiceId", invoice.getInvoiceId());
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

    /**
     * call from InvoiceController--> case "UpdateAll" and PaymentController
     * case "SubmitPayment"
     *
     * @param outstandigInvoice
     * @return
     */
    
    public String updateOutstanding(OutstandigInvoice outstandigInvoice) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE OutstandigInvoice as o set o.balanceAmount=:balanceAmount,o.lastUpdateDate=:lastUpdateDate where o.invoiceId.invoiceId=:invoiceId");
                query.setParameter("balanceAmount", outstandigInvoice.getBalanceAmount());
                query.setParameter("lastUpdateDate", outstandigInvoice.getLastUpdateDate());
                query.setParameter("invoiceId", outstandigInvoice.getInvoiceId().getInvoiceId());
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

    /**
     * call from PaymentController--> case "DoClearCheque"
     *
     * @param invoiceId
     * @return
     */
    
    public OutstandigInvoice viewOutstanding(int invoiceId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.createQuery("From OutstandigInvoice o Where o.invoiceId.invoiceId=:invoiceId");
                query.setParameter("invoiceId", invoiceId);

                OutstandigInvoice outstandigInvoice = (OutstandigInvoice) query.uniqueResult();
                return outstandigInvoice;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }

    /**
     * call from StockController--> case
     * "SaveVehicleStock","SaveUpdatedVehicleStock" call from
     * InvoiceController--> case "DeleteInvoice","UpdateAll"
     *
     * @param arr
     * @return
     */
    
    public BranchStock viewBranchStock(int[] arr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("From BranchStock bs Where bs.branchId.branchId=:branchId and bs.productId.productId=:productId");
                query.setParameter("branchId", arr[0]);
                query.setParameter("productId", arr[1]);
                BranchStock bs = (BranchStock) query.uniqueResult();
                return bs;

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

    /**
     * call from InvoiceController--> case"SelectDates"
     *
     * @param arr
     * @return
     */
    
    public List<Object[]> invoiceAccordingToPeriod(String[] arr, Company com) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                String sql="SELECT i.invoice_id,i.invoiced_date,c.customer_name,i.tot_after_discount FROM invoice i inner join customer c on i.customer_id=c.customer_id where i.is_valid='1' and i.company_id='"+com.getId()+"' and i.invoiced_date between :fromDate and :toDate";    
                
                if(!arr[2].equals("ALL")){
                sql+=" and i.branch_id='"+arr[2]+"'";
                }
                
                SQLQuery query = session.createSQLQuery(sql);
                query.setParameter("fromDate", arr[0]);
                query.setParameter("toDate", arr[1]);
                List<Object[]> inList = query.list();
                
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
public List<Object[]> CanceledinvoiceAccordingToPeriod(String[] arr) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                String sql="SELECT i.invoice_id,i.invoiced_date,c.customer_name,i.tot_after_discount FROM invoice i inner join customer c on i.customer_id=c.customer_id where i.is_valid='0' and i.invoiced_date between :fromDate and :toDate";    
                
                if(!arr[2].equals("ALL")){
                sql+=" and i.branch_id='"+arr[2]+"'";
                }
                
                SQLQuery query = session.createSQLQuery(sql);
                query.setParameter("fromDate", arr[0]);
                query.setParameter("toDate", arr[1]);
                List<Object[]> inList = query.list();
                
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
    public List<Object[]> invoiceAccordingToBranch(String branch,Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                String hql="";
                if(!branch.equals("0")){
                hql="SELECT i.invoice_id,i.invoiced_date,c.customer_name,i.tot_after_discount FROM invoice i inner join customer c on i.customer_id=c.customer_id where i.is_valid='1' and i.company_id='"+com.getId()+"' and i.branch_id='"+branch+"'";
                }else{
                hql="SELECT i.invoice_id,i.invoiced_date,c.customer_name,i.tot_after_discount FROM invoice i inner join customer c on i.customer_id=c.customer_id where i.is_valid='1' and i.company_id='"+com.getId()+"'";
                }
                SQLQuery query = session.createSQLQuery(hql);
                List<Object[]> inList = query.list();
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
    
    
    
    /**
     * call from InvoiceController--> case "ToPrint"
     *
     * @return
     */
    
    public Object[] getLastInvoice() {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT i.invoice_id,i.invoiced_date,c.customer_name,i.tot_after_discount "
                        + "FROM invoice i inner join customer c on i.customer_id=c.customer_id\n"
                        + "ORDER BY invoice_id DESC LIMIT 1;");
                Object[] invoice = (Object[]) query.uniqueResult();
                return invoice;

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

    
    public String reqDelete(int id,SysUser user) {
         Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                DelInvoice di = new DelInvoice();
                di.setDate(new Date());
                Invoice iii=new Invoice(id);
                di.setInvoiceInvoiceId(iii);
                di.setSysUserSysuserId(user);
                di.setIsDeleted(false);
                session.save(di);
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


    
    public String updateReqDelete(int invoiceId, boolean b) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                
                Query query = session.createQuery("UPDATE DelInvoice SET isDeleted=:isDel WHERE invoice_invoice_id=:invoiceId");

                query.setParameter("isDel", b);
                query.setParameter("invoiceId", invoiceId);
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
    
    
    
    
    
    public String updateWarehouse(ProductMaster pmId,int qty) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("UPDATE WarehouseStock w SET w.qty=w.qty+:qty2 WHERE w.productMasterProductMasterId=:pmId");
                
                query.setParameter("pmId", pmId);
                query.setParameter("qty2", qty);
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
    
    
    
    public String ProductFromCategory(String id, int branchId) {
        String html = "";

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
//               SQLQuery query = session.createSQLQuery("Select p.product_id,p.product_code,p.product_name From branch_stock bs inner join product p on bs.product_id=p.product_id where bs.branch_id=:branchId and p.product_category_id='"+id+"' and bs.quantity>0 group by p.product_id order by p.product_code asc");
               SQLQuery query = session.createSQLQuery("Select p.product_id,p.product_code,p.product_name From branch_productmaster bpm inner join product_master pm on bpm.product_master_id=pm.product_master_id inner join product p on pm.product_id=p.product_id where bpm.branch_id=:branchId and p.product_category_id='"+id+"' and bpm.quantity>0 group by p.product_id order by p.product_code asc");
                query.setParameter("branchId", branchId);
                                            
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
     public String ProductFromCategoryWarehouse(String id) {
        String html = "";

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                 SQLQuery query = session.createSQLQuery("Select p.product_id,p.product_code,p.product_name From warehouse_stock bpm inner join product_master pm on bpm.product_master_product_master_id=pm.product_master_id inner join product p on pm.product_id=p.product_id where p.product_category_id='"+id+"' and bpm.qty>0 group by p.product_id order by p.product_code asc");
                                           
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
     public String savePayment(Payment payment) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(payment);
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
    public String saveInvoicePayment(InvoicePayment payment) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(payment);
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
