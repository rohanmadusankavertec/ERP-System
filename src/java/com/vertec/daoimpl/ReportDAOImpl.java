/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.hibe.model.Account;
import com.vertec.hibe.model.BalanceSheetData;
import com.vertec.hibe.model.Bin;
import com.vertec.hibe.model.Branch;
import com.vertec.hibe.model.BranchProductmaster;
import com.vertec.hibe.model.BudgetPlan;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Depreciation;
import com.vertec.hibe.model.DepreciationData;
import com.vertec.hibe.model.DisposeItems;
import com.vertec.hibe.model.Gin;
import com.vertec.hibe.model.Grn;
import com.vertec.hibe.model.Gtn;
import com.vertec.hibe.model.InvoiceItem;
import com.vertec.hibe.model.Payment;
import com.vertec.hibe.model.ReturnByCustomer;
import com.vertec.hibe.model.ReturnBySupplier;
import com.vertec.hibe.model.ReturnToCustomer;
import com.vertec.hibe.model.ReturnToSupplier;
import com.vertec.hibe.model.StockReturn;
import com.vertec.hibe.model.SystemData;
import com.vertec.hibe.model.Transaction;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import com.vertec.util.test;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author vertec-r
 */
public class ReportDAOImpl {

    public List<Object[]> invoiceItemToReport(int invoiceId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT p.product_name,ii.unit_price,ii.quantity,ii.tot_amount,ii.discount,ii.tot_after_dis FROM invoice_item ii inner join product_master pm on ii.product_master_id=pm.product_master_id\n"
                        + "inner join product p on pm.product_id=p.product_id\n"
                        + "where ii.invoice_id=:invoiceId");
                query.setParameter("invoiceId", invoiceId);

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

    public Object[] invoiceHeaderToReport(int invoiceId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT i.invoice_id,i.invoiced_date,i.invoice_total,i.discount,i.tot_after_discount,c.customer_name,c.address as cadd,b.address as badd,b.contact_no,sum(i.tot_after_discount-(i.invoice_total-i.discount)) as tax\n"
                        + "FROM invoice i inner join customer c on i.customer_id=c.customer_id inner join branch b on i.branch_id=b.branch_id\n"
                        + "where i.invoice_id=:invoiceId");
                query.setParameter("invoiceId", invoiceId);
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

    public List<Object[]> dailyInvoiceReport(String selectDate) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT i.invoice_id,c.customer_name,b.branch_name,i.invoice_total,i.discount,i.tot_after_discount,i.invoiced_date\n"
                        + "FROM invoice i inner join customer c on i.customer_id=c.customer_id inner join branch b on i.branch_id=b.branch_id\n"
                        + "where i.invoiced_date=:selectDate");
                query.setParameter("selectDate", selectDate);

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

    public List<Object[]> periodicallyInvoiceReport(String[] daeArr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT i.invoice_id,c.customer_name,b.branch_name,i.invoice_total,i.discount,i.tot_after_discount,i.invoiced_date\n"
                        + "FROM invoice i inner join customer c on i.customer_id=c.customer_id inner join branch b on i.branch_id=b.branch_id\n"
                        + "where i.invoiced_date between :fromDate and :toDate");
                query.setParameter("fromDate", daeArr[0]);
                query.setParameter("toDate", daeArr[1]);

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

    public List<Object[]> periodicallyOutstanding() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT sum(balance_amount) as outstanding,c.customer_name FROM outstandig_invoice oi\n"
                        + "inner join invoice i on oi.invoice_id=i.invoice_id\n"
                        + "inner join customer c on i.customer_id=c.customer_id\n"
                        + "group by c.customer_id");

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

    public List<Object[]> branchWiseProduct(int branchId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT p.product_name,p.product_code,pm.selling_price,bp.quantity FROM branch_productmaster bp inner join product_master pm on bp.product_master_id=pm.product_master_id\n"
                        + "inner join branch b on bp.branch_id=b.branch_id\n"
                        + "inner join product p on pm.product_id=p.product_id\n"
                        + "where b.branch_id=:branchId");
                query.setParameter("branchId", branchId);
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

    public List<Object[]> branchWiseProductMaster(int branchId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT p.product_name,p.product_code,bs.quantity\n"
                        + "FROM branch_stock bs inner join branch b on bs.branch_id=b.branch_id\n"
                        + "inner join product p on bs.product_id=p.product_id\n"
                        + "where b.branch_id=:branchId");
                query.setParameter("branchId", branchId);
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

    public List<Object[]> releaseProductBranchWise(String[] date, int branchId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT p.product_name,p.product_code,pm.selling_price,sum(ii.quantity) as pQuan\n"
                        + "FROM invoice_item ii inner join invoice i on ii.invoice_id=i.invoice_id\n"
                        + "inner join product_master pm on ii.product_master_id=pm.product_master_id\n"
                        + "inner join product p on pm.product_id=p.product_id\n"
                        + "inner join branch b on i.branch_id=b.branch_id\n"
                        + "where b.branch_id=:branchId and i.invoiced_date between :fromDate and :toDate group by pm.product_master_id");
                query.setParameter("branchId", branchId);
                query.setParameter("fromDate", date[0]);
                query.setParameter("toDate", date[1]);
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

    public List<Object[]> releaseProductPeriodicalyyVehicle(String[] date) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT p.product_name,p.product_code,pm.selling_price,sum(ii.quantity) as pQuan,v.vehicle_reg_no FROM invoice i\n"
                        + "inner join invoice_item ii on i.invoice_id=ii.invoice_id\n"
                        + "left join vehicle v on i.vehicle_id=v.vehicle_id\n"
                        + "inner join product_master pm on ii.product_master_id=pm.product_master_id\n"
                        + "inner join product p on pm.product_id=p.product_id\n"
                        + "where i.invoice_type='V' and i.invoiced_date between :fromDate and :toDate group by ii.product_master_id,v.vehicle_id");
                query.setParameter("fromDate", date[0]);
                query.setParameter("toDate", date[1]);
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

    public List<Object[]> releaseProductPeriodicalyySP(String[] date, int user_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getLastInvoiceId() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("SELECT invoice_id FROM invoice\n"
                        + "ORDER BY invoice_id DESC\n"
                        + "LIMIT 1;");
                int inId = (Integer) query.uniqueResult();
                return inId;

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return 0;
    }

    public List<Transaction> loadAssetCreditSide(Account a, Date from, Date to, Company company) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT t FROM Transaction t WHERE t.credit = :account AND t.companyId=:com AND t.date BETWEEN :from AND :to");
                query.setParameter("account", a);
                query.setParameter("from", from);
                query.setParameter("to", to);
                query.setParameter("com", company);
                List<Transaction> pcList = query.list();
                return pcList;

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

    public List<Transaction> loadAllOfAccountByPayType(Date fdate, Date todate, Company com) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT t FROM Transaction t WHERE t.companyId=:com  AND t.date  BETWEEN :fdate AND :todate");
//                query.setParameter("cid",cid);

                query.setParameter("fdate", fdate);
                query.setParameter("todate", todate);
//                query.setParameter("did",did);
                query.setParameter("com", com);

                List<Transaction> aList = query.list();
                return aList;
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

    public List<Account> loadAllOfAccountByCash1(Company com) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {

//                Query query = session.createQuery("SELECT t FROM Transaction t WHERE t.companyId=:com  AND t.date  BETWEEN :fdate AND :todate");
                Query query = session.createQuery("SELECT a FROM Account a WHERE a.companyId=:com AND a.isValid=:valid AND (a.subtypeId.name='Cash' OR a.subtypeId.name='Bank')");
//                query.setParameter("cid",cid);

                query.setParameter("valid", true);
//                query.setParameter("did",did);
                query.setParameter("com", com);

                List<Account> aList = query.list();
                return aList;
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

    public List<String[]> loadAccountsForProfit(List<Account> account, Date from, Date to) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                List<String[]> starr = new ArrayList<>();
                for (Account a : account) {
                    Query query1 = session.createQuery("SELECT t FROM Transaction t WHERE t.date BETWEEN :from AND :to");
                    query1.setParameter("from", from);
                    query1.setParameter("to", to);
                    List<Transaction> tList = query1.list();

                    for (Transaction transaction : tList) {
                        System.out.println("CHECK >>>" + transaction.getDebit().getSubtypeId().getTypeId().getName() + "   " + transaction.getCredit().getSubtypeId().getTypeId().getName());

                        if (!transaction.getDebit().getSubtypeId().getTypeId().getName().equals("Capital") && !transaction.getCredit().getSubtypeId().getTypeId().getName().equals("Capital")) {
                            System.out.println("NO >>>" + transaction.getDebit().getSubtypeId().getTypeId().getName());
                            boolean bool = true;
                            for (String[] st : starr) {
                                if (st[0].equals(transaction.getId() + "")) {
                                    bool = false;
                                }
                            }
                            if (bool) {
                                String[] arr = {transaction.getId() + "", transaction.getCredit().getName(), transaction.getDebit().getName(), transaction.getPrice() + ""};
                                starr.add(arr);
                            }
                        }
                    }
                }
                List<String[]> arr = new ArrayList<>();

                for (String[] st : starr) {
                    boolean cbool = false;
                    boolean dbool = false;
                    boolean is_change = false;

                    Query query1 = session.createQuery("SELECT t FROM Transaction t WHERE t.id=:id");
                    query1.setParameter("id", Integer.parseInt(st[0]));
                    Transaction t1 = (Transaction) query1.uniqueResult();

                    System.out.println(">>>>>" + t1.getDescription() + "----" + t1.getDebit().getSubtypeId().getName() + "----" + t1.getCredit().getSubtypeId().getName());

                    if ((t1.getDebit().getSubtypeId().getName().equals("Cash") | t1.getDebit().getSubtypeId().getName().equals("Bank")) & (t1.getCredit().getSubtypeId().getName().equals("Cash") | t1.getCredit().getSubtypeId().getName().equals("Bank"))) {
                        dbool = true;
                        cbool = true;
                    } else if ((!t1.getDebit().getSubtypeId().getName().equals("Cash") | !t1.getDebit().getSubtypeId().getName().equals("Bank")) & (t1.getCredit().getSubtypeId().getName().equals("Cash") | t1.getCredit().getSubtypeId().getName().equals("Bank"))) {
                        dbool = false;
                        cbool = true;
                        if (t1.getDebit().getSubtypeId().getName().equals("Creditors") | t1.getDebit().getSubtypeId().getName().equals("Debtors")) {
                            cbool = false;
                            System.out.println("Ignored<<<<<<<<<<<<<<<<<<<<<<<");
                        }

                    } else if ((t1.getDebit().getSubtypeId().getName().equals("Cash") | t1.getDebit().getSubtypeId().getName().equals("Bank")) & (!t1.getCredit().getSubtypeId().getName().equals("Cash") | !t1.getCredit().getSubtypeId().getName().equals("Bank"))) {
                        dbool = true;
                        cbool = false;

                        if (t1.getCredit().getSubtypeId().getName().equals("Creditors") | t1.getCredit().getSubtypeId().getName().equals("Debtors")) {
                            dbool = false;
                            System.out.println("Ignored<<<<<<<<<<<<<<<<<<<<<<<");
                        }
                    } else if ((!t1.getDebit().getSubtypeId().getName().equals("Cash") | !t1.getDebit().getSubtypeId().getName().equals("Bank")) & (!t1.getCredit().getSubtypeId().getName().equals("Cash") | !t1.getCredit().getSubtypeId().getName().equals("Bank"))) {
                        dbool = false;
                        cbool = false;
                    }

                    if (cbool & dbool) {
                    } else if (!cbool & !dbool) {
                    } else {
                        double amount = 0.0;
                        String accountname = "";

                        if (dbool & dbool) {
                            amount = t1.getPrice();
                            accountname = t1.getCredit().getName() + "  (" + t1.getCredit().getSubtypeId().getName() + ")";
                        } else {
                            amount = (-1) * t1.getPrice();
                            accountname = t1.getDebit().getName() + "  (" + t1.getDebit().getSubtypeId().getName() + ")";
                        }
                        String[] sarr = {st[0], accountname, amount + ""};
                        arr.add(sarr);
                    }
                }
                List<String[]> finalarr = new ArrayList<>();
                for (String[] starray : arr) {
                    boolean finalbool = true;
                    for (String[] arr2 : finalarr) {
                        if (starray[1].equals(arr2[0])) {
                            finalbool = false;
                            arr2[1] = (Double.parseDouble(starray[2]) + Double.parseDouble(arr2[1])) + "";
                        }
                    }
                    if (finalbool) {
                        String[] newarr = {starray[1], starray[2]};
                        finalarr.add(newarr);
                    }
                }
                for (String[] starray : finalarr) {
                    System.out.println(starray[0] + "_____________________" + starray[1]);
                }
                return finalarr;
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

    public List<Transaction> loadAssetDebitSide(Account a, Date from, Date to, Company company) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT t FROM Transaction t WHERE t.debit = :account AND t.companyId=:com AND t.date BETWEEN :from AND :to");
                query.setParameter("account", a);
                query.setParameter("from", from);
                query.setParameter("to", to);
                query.setParameter("com", company);
                List<Transaction> pcList = query.list();
                return pcList;

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

    public Account getAccountById(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a FROM Account a WHERE a.id=:id");
                query.setParameter("id", id);
                Account pcList = (Account) query.uniqueResult();
                return pcList;

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

    public Double getAccountOpenBalance(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a.balance FROM Account a WHERE a.id=:id");
                query.setParameter("id", id);
                Double pcList = (Double) query.uniqueResult();
                return pcList;

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

    public Double loadBalanceBackward(Account a, Date date, Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Double debit = 0.0;
                Double credit = 0.0;
                Query query = session.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.debit = :account AND t.companyId=:com AND t.date < :date");
                query.setParameter("account", a);
                query.setParameter("date", date);
                query.setParameter("com", com);
                Object ob = query.uniqueResult();
                if (ob == null) {
                } else {
                    debit = (Double) ob;
                }
                Query query1 = session.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.credit = :account AND t.companyId=:com AND t.date < :date");
                query1.setParameter("account", a);
                query1.setParameter("date", date);
                query1.setParameter("com", com);
//                credit =(Double) query1.uniqueResult();
                Object ob2 = query1.uniqueResult();
                if (ob2 == null) {
                } else {
                    credit = (Double) ob2;
                }
                System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><>");
                System.out.println("DEBIT :" + debit);
                System.out.println("CREDIT :" + credit);
                System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><>");
                return debit - credit;
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

    public List<String[]> loadTrialbalance(Date from, Date to, Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a FROM Account a ");
                List<Account> account = (List<Account>) query.list();
                List<String[]> starr = new ArrayList<>();
                for (Account a : account) {

                    Double debit = 0.0;
                    Double credit = 0.0;

                    Query query1 = session.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.debit=:acc AND t.companyId=:com AND t.date BETWEEN :from AND :to");
                    query1.setParameter("from", from);
                    query1.setParameter("to", to);
                    query1.setParameter("acc", a);
                    query1.setParameter("com", com);
                    Object ob1 = query1.uniqueResult();
                    if (ob1 == null) {
                    } else {
                        debit = (Double) ob1;
                    }

                    Query query2 = session.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.credit=:acc AND t.companyId=:com AND t.date BETWEEN :from AND :to");
                    query2.setParameter("from", from);
                    query2.setParameter("to", to);
                    query2.setParameter("acc", a);
                    query2.setParameter("com", com);
                    Object ob2 = query2.uniqueResult();
                    if (ob2 == null) {
                    } else {
                        credit = (Double) ob2;
                    }

                    String[] arr = {a.getName(), (debit - credit) + ""};
                    starr.add(arr);

                }
                return starr;
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

    public List<Account> getAccountsByCompany(Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a FROM Account a WHERE a.companyId=:com");
                query.setParameter("com", com);
                List<Account> pcList = (List<Account>) query.list();
                return pcList;
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

    public List<Account> getAccountsByCompany(Company com, String acc) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = null;
                if (acc.equals("")) {
                    query = session.createQuery("SELECT a FROM Account a WHERE a.companyId=:com");
                } else {
                    query = session.createQuery("SELECT a FROM Account a WHERE a.companyId=:com AND a.id=:id");
                    query.setParameter("id", Integer.parseInt(acc));
                }

//                
//                Query query = session.createQuery("SELECT a FROM Account a WHERE a.companyId=:com");
                query.setParameter("com", com);
                List<Account> pcList = (List<Account>) query.list();
                return pcList;
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

    public List<BalanceSheetData> getBalanceSheetData(Date from, Date to, Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a FROM Account a");
                List<Account> account = (List<Account>) query.list();
                List<BalanceSheetData> starr = new ArrayList<>();
                for (Account a : account) {

                    Double debit = 0.0;
                    Double credit = 0.0;

                    Query query1 = session.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.debit=:acc AND t.companyId=:com AND t.date BETWEEN :from AND :to");
                    query1.setParameter("from", from);
                    query1.setParameter("to", to);
                    query1.setParameter("acc", a);
                    query1.setParameter("com", com);
                    Object ob1 = query1.uniqueResult();
                    if (ob1 == null) {
                    } else {
                        debit = (Double) ob1;
                    }

                    Query query2 = session.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.credit=:acc AND t.companyId=:com AND t.date BETWEEN :from AND :to");
                    query2.setParameter("from", from);
                    query2.setParameter("to", to);
                    query2.setParameter("acc", a);
                    query2.setParameter("com", com);
                    Object ob2 = query2.uniqueResult();
                    if (ob2 == null) {
                    } else {
                        credit = (Double) ob2;
                    }
                    BalanceSheetData bsd = new BalanceSheetData();
                    bsd.setA(a);
                    bsd.setSt(null);
                    bsd.setAmount((debit - credit));
                    starr.add(bsd);
                }
                return starr;
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

    public List<DepreciationData> getDepreciationData(Date from, Date to, Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT d FROM Depreciation d");
                List<Depreciation> depreciation = (List<Depreciation>) query.list();
                List<DepreciationData> starr = new ArrayList<>();
                for (Depreciation dep : depreciation) {
                    Query query1 = session.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.depreciationId=:dep AND t.debit.subtypeId.name='Depreciation' AND t.companyId=:com AND t.date BETWEEN :from AND :to");
                    query1.setParameter("from", from);
                    query1.setParameter("to", to);
                    query1.setParameter("dep", dep);
                    query1.setParameter("com", com);
                    Double ob1 = (Double) query1.uniqueResult();

                    Query query2 = session.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.depreciationId=:dep AND t.debit.subtypeId.name='Depreciation' AND t.companyId=:com AND t.date < :from ");
                    query2.setParameter("from", from);
                    query2.setParameter("dep", dep);
                    query2.setParameter("com", com);
                    Double ob2 = (Double) query2.uniqueResult();

                    DepreciationData depre = new DepreciationData();
                    if (ob1 == null) {
                    } else {
                        depre.setAmount(ob1);
                    }
                    if (ob2 == null) {
                    } else {
                        depre.setBeforedep(ob2);
                    }
                    depre.setAccount(dep.getDebit());
                    depre.setDepreciation(dep);
                    starr.add(depre);
                }
                return starr;
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

    public void CalculateDepreciation(Date from, Date to, Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        org.hibernate.Transaction tr = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT d FROM Depreciation d WHERE d.isClosed=:ic AND d.credit.companyId=:com");
                query.setParameter("com", com);
                query.setParameter("ic", false);
                List<Depreciation> pcList = (List<Depreciation>) query.list();

                for (Depreciation dep : pcList) {
                    Query dq = session.createQuery("DELETE FROM Transaction t WHERE t.credit=:credit AND t.debit=:debit AND t.depreciationId=:did");
                    dq.setParameter("credit", dep.getCredit());
                    dq.setParameter("debit", dep.getDebit());
                    dq.setParameter("did", dep);
                    dq.executeUpdate();
                    session.flush();
                    tr.commit();

                    int terms = dep.getTerm();
                    double amount = dep.getAmount();
                    double rate = dep.getRate();
                    Date ldate = dep.getDate();
                    Date CurrentDate = to;

                    int i = 0;
                    while (ldate.before(CurrentDate)) {
                        i++;
                        Calendar c = Calendar.getInstance();
                        c.setTime(ldate);
                        c.add(Calendar.YEAR, 1);
                        Date dd = c.getTime();
                        long diff = CurrentDate.getTime() - dd.getTime();

                        if (diff > 365) {
                            double depamount = amount * (rate / 100);
                            Transaction trans = new Transaction();
                            trans.setCompanyId(com);
                            trans.setCredit(dep.getCredit());
                            trans.setDebit(dep.getDebit());
                            trans.setDate(dd);
                            trans.setDepreciationId(dep);
                            trans.setDescription("Calculated Depreciation Automatically...");
                            trans.setDiscount(0.0);
                            trans.setPaidAmount(depamount);
                            trans.setPrice(depamount);
                            new SaveDAOImpl().saveObject(trans);
                            amount -= depamount;
                        }
                        long diff2 = CurrentDate.getTime() - ldate.getTime();

                        if (diff < 0 && diff2 > 0) {
                            int diffInDays = getDifferenceDays(ldate, CurrentDate);
                            double depamount = amount * (rate / 100) * (Double.parseDouble(diffInDays + "") / 365);
                            DecimalFormat df = new DecimalFormat("#.##");
                            depamount = Double.valueOf(df.format(depamount));
                            Transaction trans = new Transaction();
                            trans.setCompanyId(com);
                            trans.setCredit(dep.getCredit());
                            trans.setDebit(dep.getDebit());
                            trans.setDate(CurrentDate);
                            trans.setDepreciationId(dep);
                            trans.setDescription("Calculated Depreciation Automatically...");
                            trans.setDiscount(0.0);
                            trans.setPaidAmount(depamount);
                            trans.setPrice(depamount);
                            new SaveDAOImpl().saveObject(trans);
                            amount -= depamount;
                        }
                        if (i == terms) {
                            Query uq = session.createQuery("UPDATE Depreciation d SET d.isClosed=:ic WHERE d=:did");
                            uq.setParameter("did", dep);
                            uq.setParameter("ic", true);
                            uq.executeUpdate();
                            session.flush();
                            tr.commit();
                            break;
                        }
                        ldate = dd;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
    }

    public int getDifferenceDays(Date d1, Date d2) {
        int daysdiff = 0;
        long diff = d2.getTime() - d1.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
        daysdiff = (int) diffDays;
        return daysdiff;
    }

    public List<Grn> GetGRNDATA(BranchProductmaster bpm) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT grn FROM Grn grn WHERE grn.productProductId=:pid AND grn.pPrice=:pprice AND grn.sPrice=:sprice");
                query.setParameter("pid", bpm.getProductMasterId().getProductId());
                query.setParameter("pprice", bpm.getProductMasterId().getPurchasedPrice());
                query.setParameter("sprice", bpm.getProductMasterId().getSellingPrice());
                List<Grn> inList = (List<Grn>) query.list();
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

    public List<InvoiceItem> GetInvoiceDATA(BranchProductmaster bpm) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT i FROM InvoiceItem i WHERE i.productMasterId=:pmid");
                query.setParameter("pmid", bpm.getProductMasterId());
                List<InvoiceItem> inList = (List<InvoiceItem>) query.list();
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

    public List<Gtn> GetGtnDATA(BranchProductmaster bpm) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT g FROM Gtn g WHERE g.productMasterId=:pmid");
                query.setParameter("pmid", bpm.getProductMasterId());
                List<Gtn> inList = (List<Gtn>) query.list();
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

    public List<Gin> GetGinDATA(BranchProductmaster bpm) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT g FROM Gin g WHERE g.bpmId=:bpmid");
                query.setParameter("bpmid", bpm);
                List<Gin> inList = (List<Gin>) query.list();
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
    
    
    public List<ReturnByCustomer> GetCustomerReturnDATA(BranchProductmaster bpm) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT r FROM ReturnByCustomer r WHERE r.invoiceItemId.productMasterId=:pmid");
                query.setParameter("pmid", bpm.getProductMasterId());
                List<ReturnByCustomer> inList = (List<ReturnByCustomer>) query.list();
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
    public List<ReturnToCustomer> GetReturnToCustomerDATA(BranchProductmaster bpm) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT r FROM ReturnToCustomer r WHERE r.productMasterId=:pmid");
                query.setParameter("pmid", bpm.getProductMasterId());
                List<ReturnToCustomer> inList = (List<ReturnToCustomer>) query.list();
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
    public List<DisposeItems> GetDisposeDATA(BranchProductmaster bpm) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT d FROM DisposeItems d WHERE d.productMasterId=:pmid");
                query.setParameter("pmid", bpm.getProductMasterId());
                List<DisposeItems> inList = (List<DisposeItems>) query.list();
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
    public List<StockReturn> GetStockReturnDATA(BranchProductmaster bpm) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM StockReturn s WHERE s.productMasterId=:pmid");
                query.setParameter("pmid", bpm.getProductMasterId());
                List<StockReturn> inList = (List<StockReturn>) query.list();
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
    public List<ReturnToSupplier> GetReturnToSupplierDATA(BranchProductmaster bpm) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT r FROM ReturnToSupplier r WHERE r.returnStockId.productMasterId=:pmid");
                query.setParameter("pmid", bpm.getProductMasterId());
                List<ReturnToSupplier> inList = (List<ReturnToSupplier>) query.list();
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
    public List<ReturnBySupplier> GetReturnBySupplierDATA(BranchProductmaster bpm) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT r FROM ReturnBySupplier r WHERE r.productMasterId=:pmid");
                query.setParameter("pmid", bpm.getProductMasterId());
                List<ReturnBySupplier> inList = (List<ReturnBySupplier>) query.list();
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
    
    public List<Payment> getCreditCardPayment(Date fd,Date td) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT p FROM Payment p WHERE p.paymentTypeId.ptId=:type AND p.paymentDate BETWEEN :fdate AND :tdate");
//                Query query1 = session.createQuery("SELECT p FROM InvoicePayment p WHERE p.paymentId.paymentId:type AND p.invoiceId.companyId=:");
                query.setParameter("type",3);
                query.setParameter("fdate",fd);
                query.setParameter("tdate",td);
                List<Payment> inList = (List<Payment>) query.list();
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
    public List<BudgetPlan> getBudgetPlan(String acc,String year,Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT b FROM BudgetPlan b WHERE b.accountId.id=:acc AND b.year=:year AND b.companyId=:com");
                query.setParameter("acc",Integer.parseInt(acc));
                query.setParameter("year",year);
                query.setParameter("com",com);
                List<BudgetPlan> inList = (List<BudgetPlan>) query.list();
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
    
    public double getBudgetOfYear(Company com,int accid,String year){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if(session != null){
            try {
                Query query = session.createQuery("SELECT SUM(s.value)FROM BudgetPlan s WHERE s.accountId.id=:accId AND s.companyId=:com AND s.year=:year");
//                Query query = session.createQuery("SELECT SUM(s.value),s.year FROM BudgetPlan s WHERE s.accountId.id=:accId AND s.companyId=:com GROUP BY s.year");
                query.setParameter("com", com);
                query.setParameter("accId", accid);
                query.setParameter("year", year);
                double value =(double) query.uniqueResult();
                System.out.println("/////////"+value);
                return value;
                
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if(session != null && session.isOpen()){
                    session.close();
                }
            }
        }
        
        return 0.0;
    }
    
    public List<Object[]> getBudgetOfYear1(Company com,int accid){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if(session != null){
            try {
//                Query query = session.createQuery("SELECT SUM(s.value)FROM BudgetPlan s WHERE s.accountId.id=:accId AND s.companyId=:com AND s.year=:year");
                Query query = session.createQuery("SELECT SUM(s.value),s.year FROM BudgetPlan s WHERE s.accountId.id=:accId AND s.companyId=:com GROUP BY s.year");
                query.setParameter("com", com);
                query.setParameter("accId", accid);
//                query.setParameter("year", year);

//                double value =(double) query.uniqueResult();
                List<Object[]> list = (List<Object[]>)query.list();
                
                return list;
                
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if(session != null && session.isOpen()){
                    session.close();
                }
            }
        }
        
        return null;
    }
    
    public SystemData getCreditCardRate() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM SystemData s WHERE s.id =1");
//                query.setParameter("sid",id);
                
                SystemData inList = (SystemData) query.uniqueResult();
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
    public String updateBudgetPlan(BudgetPlan bp,Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE BudgetPlan as b set b.value=:val WHERE b.companyId=:com AND b.month=:month AND b.year=:year");

                query.setParameter("com", com);
                query.setParameter("month", bp.getMonth());
                query.setParameter("year", bp.getYear());
                query.setParameter("val", bp.getValue());
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
    

    public List<Bin> GetBin(BranchProductmaster bpm, Branch branch) {

        List<Grn> grnData = GetGRNDATA(bpm);
        List<InvoiceItem> invoiceData = GetInvoiceDATA(bpm);
        List<Gtn> gtnData = GetGtnDATA(bpm);
        List<Gin> ginData = GetGinDATA(bpm);
        
        
        List<ReturnByCustomer> customerReturnData = GetCustomerReturnDATA(bpm);
        List<ReturnToCustomer> ReturntocustomerData = GetReturnToCustomerDATA(bpm);
        List<DisposeItems> DisposeData = GetDisposeDATA(bpm);
        List<StockReturn> StockReturnData = GetStockReturnDATA(bpm);
        List<ReturnToSupplier> ReturnToSupplierData = GetReturnToSupplierDATA(bpm);
        List<ReturnBySupplier> ReturnbySupplierData = GetReturnBySupplierDATA(bpm);

        
        
        List<Bin> binList = new ArrayList<>();

        
        
        for (ReturnBySupplier c : ReturnbySupplierData) {
            Bin b = new Bin();
            b.setDate(c.getSupplierReturnId().getDate());
            b.setQty(c.getQty());
            b.setDes("Returned By Supplier");
            binList.add(b);

        }
        for (ReturnToSupplier c : ReturnToSupplierData) {
            Bin b = new Bin();
            b.setDate(c.getSupplierReturnId().getDate());
            b.setQty(c.getQty()*(-1));
            b.setDes("Return to supplier");
            binList.add(b);

        }
        for (DisposeItems c : DisposeData) {
            Bin b = new Bin();
            b.setDate(c.getDate());
            b.setQty(c.getQty()*(-1));
            b.setDes("Disposed");
            binList.add(b);

        }
        for (ReturnToCustomer c : ReturntocustomerData) {
            Bin b = new Bin();
            b.setDate(c.getCustomerReturnId().getDate());
            b.setQty(c.getQty()*(-1));
            b.setDes("Returned to customer");
            binList.add(b);

        }
        for (ReturnByCustomer c : customerReturnData) {
            Bin b = new Bin();
            b.setDate(c.getCustomerReturnId().getDate());
            b.setQty(c.getQty());
            b.setDes("Customer Returned");
            binList.add(b);

        }
        
        
        
        
        
        
        
        
        
        
        
        
        for (Gin gin : ginData) {

            Bin b = new Bin();

            b.setDate(gin.getGinInfoId().getDate());
            b.setQty(gin.getQty() * (-1));
            b.setDes("GIN");
            binList.add(b);

        }
        for (Grn grn : grnData) {
            Bin b = new Bin();
            b.setDate(grn.getGrnInfoGrnInfoId().getDate());
            b.setQty(grn.getQty());
            b.setDes("GRN");
            binList.add(b);
        }
        for (InvoiceItem invoice : invoiceData) {
            Bin b = new Bin();
            b.setDate(invoice.getInvoiceId().getInvoicedDate());
            b.setQty(invoice.getQuantity() * (-1));
            b.setDes("Invoice");
            binList.add(b);
        }

        for (Gtn gtn : gtnData) {
            Bin b = new Bin();
            b.setDate(gtn.getGtninfoId().getDate());
            if (gtn.getGtninfoId().getToBranch().equals(branch)) {
                b.setQty(gtn.getQty());
            } else if (gtn.getGtninfoId().getFromBranch().equals(branch)) {
                b.setQty(gtn.getQty() * (-1));
            }
            b.setDes("GTN");
            binList.add(b);
        }
        System.gc();
        //Return data should be added
        List<Bin> binList2 = new ArrayList<>();
        Collections.sort(binList, new CompDate(false));
        System.out.println("BY Date asc");
        for (Bin p : binList) {
            binList2.add(p);
        }
        return binList2;
    }
}

class CompDate implements Comparator<Bin> {

    private int mod = 1;

    public CompDate(boolean desc) {
        if (desc) {
            mod = -1;
        }
    }

    public int compare(Bin arg0, Bin arg1) {
        return mod * arg0.getDate().compareTo(arg1.getDate());
    }
    
}
