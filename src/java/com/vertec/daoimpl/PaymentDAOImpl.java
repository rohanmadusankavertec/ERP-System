/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;


import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.GrnInfo;
import com.vertec.hibe.model.Invoice;
import com.vertec.hibe.model.InvoicePayment;
import com.vertec.hibe.model.OutstandigInvoice;
import com.vertec.hibe.model.Payment;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author User
 */
public class PaymentDAOImpl{

    /**
     * call from PaymentController--> case "LoadCusBal"
     *
     * @param customerId
     * @return
     */
    
    public List<Object[]> loadAccordigCus(int customerId) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT oi.oi_id,oi.balance_amount,oi.invoice_id,i.tot_after_discount,i.invoiced_date\n"
                        + "FROM outstandig_invoice oi inner join invoice i on oi.invoice_id=i.invoice_id\n"
                        + "where oi.balance_amount>0 and i.customer_id=:customerId and i.is_valid=true");
                query.setParameter("customerId", customerId);
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
     * call from PaymentController--> case "SubmitPayment"
     *
     * @param payment
     * @return
     */
    
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

    /**
     * call from PaymentController--> case "ChequeStatus"
     *
     * @param customerId
     * @return
     */
    
    public List<Object[]> loadChequeDetails(int customerId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT p.payment_id,p.cheque_no,p.bank_name,p.amount,i.invoice_id,p.cheque_date\n"
                        + "FROM payment p\n"
                        + "inner join invoice_payment ip on p.payment_id=ip.payment_id\n"
                        + "inner join invoice i on ip.invoice_id=i.invoice_id\n"
                        + "where p.payment_type_id=:payment_type_id and p.is_cleared=:is_cleared and i.customer_id=:customerId group by ip.payment_id");
                query.setParameter("payment_type_id", 2);
                query.setParameter("customerId", customerId);
                query.setParameter("is_cleared", false);
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
     * call from PaymentController--> case "DoClearCheque"
     *
     * @param payment
     * @return
     */
    
    public String updateChequeStatus(Payment payment) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("update payment p\n"
                        + "inner join invoice_payment ip on p.payment_id=ip.payment_id\n"
                        + "inner join outstandig_invoice oi on ip.invoice_id=oi.invoice_id\n"
                        + "set p.is_cleared=:is_cleared,ip.balance_last=:balance_last,oi.balance_amount=:balance_amount\n"
                        + "where p.payment_id=:payment_id and ip.invoice_id=:invoice_id and p.payment_type_id=:payment_type_id");

//                Query query = session.createQuery("UPDATE SysUser as s set s.firstName=:firstName, s.lastName=:lastName, s.contactNo=:contactNo where s.username=:username");
                query.setParameter("is_cleared", true);
                query.setParameter("balance_amount", payment.getAmount());
                query.setParameter("balance_last", payment.getAmount());
                query.setParameter("payment_id", payment.getPaymentId());
                query.setParameter("invoice_id", payment.getPaymentTypeId().getPtId());
                query.setParameter("payment_type_id", 2);

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
     * @param arr
     * @return
     */
    
    public List<Object[]> paymentsAccordingToPeriod(String[] arr,Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("SELECT p.payment_id,if(payment_type_id=2,'Cheque','Cash') as ptype,if(is_cleared=true,'Cleared','Not Cleared') as pstatus,\n"
                        + "p.payment_date,p.amount,ip.invoice_id,i.tot_after_discount,ip.balance_last,c.customer_name\n"
                        + "FROM payment p inner join invoice_payment ip on p.payment_id=ip.payment_id\n"
                        + "inner join invoice i on ip.invoice_id=i.invoice_id inner join customer c on i.customer_id=c.customer_id\n"
                        + "where i.company_id='"+com.getId()+"' and p.payment_date between :fromDate and :toDate");
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
    
    public double getOutstangingBalance(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {

                Query query = session.createQuery("SELECT s.balanceAmount FROM OutstandigInvoice s WHERE s.invoiceId=:id");
                        
                query.setParameter("fromDate", id);
//                query.setParameter("toDate", arr[1]);
                double oti =(double) query.uniqueResult();
                return oti;

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return  0.0;
    }
    public String updateBalance(int invId,double bal) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE OutstandigInvoice as i set i.balanceAmount=:bal  where i.invoiceId.invoiceId=:invId");

                query.setParameter("invId", invId);
                query.setParameter("bal", bal);
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
    
    public String updateInvoiceBal(int invId,double bal,int payId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE InvoicePayment as i set i.balanceLast=:bal  where i.paymentId.paymentId=:payId AND i.invoiceId.invoiceId=:invId");

                query.setParameter("invId", invId);
                query.setParameter("bal", bal);
                query.setParameter("payId", payId);
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
    
    public String updateClearCheque(int payId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE Payment as p set p.isCleared=:clear  where p.paymentId=:payId");

                query.setParameter("payId", payId);
                query.setParameter("clear", true);
//                query.setParameter("payId", payId);
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
    
    
    public String saveInvoicePayment(InvoicePayment inv) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                session.save(inv);
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
//    public String savePayment(Payment pay) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//                session.save(pay);
//                session.flush();
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
    
    public List<InvoicePayment> loadInvoiceByPayid(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT i  FROM InvoicePayment i WHERE i.paymentId.paymentId=:pid");
                query.setParameter("pid", id);
                List<InvoicePayment> bList =(List<InvoicePayment>) query.list();
                
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
    public List<GrnInfo> loadGRNBySuppid(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT g  FROM GrnInfo g WHERE g.supplierSupplierId.supplierId=:sid");
                query.setParameter("sid", id);
                List<GrnInfo> bList =(List<GrnInfo>) query.list();
                
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
    public List<GrnInfo> loadGRNBySuppidWithBalance(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT g  FROM GrnInfo g WHERE g.supplierSupplierId.supplierId=:sid AND g.outstanding > 0");
                query.setParameter("sid", id);
                List<GrnInfo> bList =(List<GrnInfo>) query.list();
                
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
    
    public String updateGRNinfo(int ginfoId,double payBal) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE GrnInfo as g set g.outstanding=:payBal  where g.grnInfoId=:ginfoId");

                query.setParameter("payBal", payBal);
                query.setParameter("ginfoId", ginfoId);
//                query.setParameter("payId", payId);
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
    
    public Invoice viewInvoice(int inid) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {

                Query query = session.createQuery("SELECT i from Invoice i where i.invoiceId=:inId");
                query.setParameter("inId", inid);
                Invoice invoice = (Invoice) query.uniqueResult();

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
    public double getPaidTooal(int inid){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                Query query = session.createQuery("SELECT SUM(i.paymentId.amount) FROM InvoicePayment i WHERE i.invoiceId.invoiceId=:inid AND i.paymentId.isCleared=:clr");
                        
                query.setParameter("inid", inid);
                query.setParameter("clr", true);
//                List<Object> list = query.list();
//                
//                for(Object b: list){
//                    
//                    
//                }

                double paid =(double) query.uniqueResult();
                System.out.println("////////"+paid);
                return paid;

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return  0.0;
    }
    
    public Payment getFirstPayment(SysUser usr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                
                Query query = session.createQuery("SELECT p FROM Payment p WHERE p.sysUserSysuserId=:usr ORDER BY p.paymentId DESC ");
                query.setParameter("usr", usr);
                query.setMaxResults(1);

                Payment iiList =(Payment) query.uniqueResult();
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
    public List<InvoicePayment> getInvoiceByPaymnet(Payment p){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(session !=null){
            try {
                Query query = session.createQuery("SELECT i  FROM InvoicePayment i WHERE i.paymentId.paymentId=:pay");
                query.setParameter("pay", p.getPaymentId());
                List<InvoicePayment> bList =(List<InvoicePayment>) query.list();
                
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
}
