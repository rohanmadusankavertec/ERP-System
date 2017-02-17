/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.hibe.model.Branch;
import com.vertec.hibe.model.BranchProductmaster;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.CustomerReturn;
import com.vertec.hibe.model.InvoiceItem;
import com.vertec.hibe.model.ProductMaster;
import com.vertec.hibe.model.ReturnByCustomer;
import com.vertec.hibe.model.ReturnBySupplier;
import com.vertec.hibe.model.ReturnStock;
import com.vertec.hibe.model.ReturnToSupplier;
import com.vertec.hibe.model.SupplierReturn;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.Save;
import com.vertec.util.VertecConstants;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author vertec-r
 */
public class ReturnDAOImpl {

    public List<InvoiceItem> loadProduct(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT i FROM InvoiceItem i WHERE i.invoiceId.invoiceId=:id");
                query.setParameter("id", id);
                List<InvoiceItem> bList = (List<InvoiceItem>) query.list();
                return bList;
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

    public ReturnStock loadReturnStockById(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT p FROM ReturnStock p WHERE p.id=:id");
                query.setParameter("id", id);
                ReturnStock bList = (ReturnStock) query.uniqueResult();
                return bList;
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
    
    public List<ProductMaster> loadProductMaster(int productid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT p FROM ProductMaster p WHERE p.productMasterId=:id");
                query.setParameter("id", productid);
                List<ProductMaster> bList = (List<ProductMaster>) query.list();
                return bList;
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

    public List<ReturnStock> loadReturnStock() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT r FROM ReturnStock r ");
                List<ReturnStock> bList = (List<ReturnStock>) query.list();
                return bList;
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

    public String UpdateStock(Branch b, int qty, int pmid, Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE BranchProductmaster b SET b.quantity=b.quantity-:qty WHERE b.branchId=:branch AND b.companyId=:company AND b.productMasterId.productMasterId=:pmid ");

                query.setParameter("pmid", pmid);
                query.setParameter("qty", qty);
                query.setParameter("branch", b);
                query.setParameter("company", com);
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

        return VertecConstants.ERROR;
    }

    public BranchProductmaster getBranchProductMaster(int pm, Branch branch, Company company) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT b FROM BranchProductmaster b WHERE b.branchId=:branch AND b.companyId=:company AND b.productMasterId.productMasterId=:pmid");
                query.setParameter("branch", branch);
                query.setParameter("company", company);
                query.setParameter("pmid", pm);
                BranchProductmaster bList = (BranchProductmaster) query.uniqueResult();
                return bList;
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

    public InvoiceItem getInvoiceItem(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT ii FROM InvoiceItem ii WHERE ii.invoiceItemId=:id");
                query.setParameter("id", id);
                InvoiceItem bList = (InvoiceItem) query.uniqueResult();
                return bList;
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

    public String UpdateReturnStock(int pm, Branch branch, Company company, int qty) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT b FROM ReturnStock b WHERE b.branchId=:branch AND b.productMasterId.productMasterId=:pmid");
                query.setParameter("branch", branch);
                query.setParameter("pmid", pm);
                ReturnStock bList = (ReturnStock) query.uniqueResult();

                if (bList == null) {
                    ReturnStock rs = new ReturnStock();
                    rs.setBranchId(branch);
                    rs.setProductMasterId(new ProductMaster(pm));
                    rs.setQty(qty);
                    String result = new Save().Save(rs);
                    return result;

                } else {

                    Query query1 = session.createQuery("UPDATE ReturnStock b SET b.qty=b.qty+:qty WHERE b.branchId=:branch AND b.productMasterId.productMasterId=:pmid ");
                    query1.setParameter("pmid", pm);
                    query1.setParameter("qty", qty);
                    query1.setParameter("branch", branch);
                    query1.executeUpdate();
                    transaction.commit();
                    return VertecConstants.SUCCESS;
                }

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

    public String ReduceReturnStock(int rsId, int qty) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query1 = session.createQuery("UPDATE ReturnStock b SET b.qty=b.qty-:qty WHERE b.id=:id ");
                query1.setParameter("id", rsId);
                query1.setParameter("qty", qty);
                query1.executeUpdate();
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
        return VertecConstants.ERROR;
    }
    
    
    
    public String AddStock(Branch b, int qty, int pmid, Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("UPDATE BranchProductmaster b SET b.quantity=b.quantity+:qty WHERE b.branchId=:branch AND b.companyId=:company AND b.productMasterId.productMasterId=:pmid ");
                query.setParameter("pmid", pmid);
                query.setParameter("qty", qty);
                query.setParameter("branch", b);
                query.setParameter("company", com);
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
        return VertecConstants.ERROR;
    }
    
    
    
    public CustomerReturn getLastCustomerInvoice(Branch branch) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT c FROM CustomerReturn c WHERE c.branchId=:id ORDER BY invoice_id DESC");
                query.setParameter("id", branch);
                query.setMaxResults(1);
                CustomerReturn bList = (CustomerReturn) query.uniqueResult();
                return bList;
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
    public List<ReturnByCustomer> getLastReturnByCustomer(CustomerReturn cr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT r FROM ReturnByCustomer r WHERE r.customerReturnId=:id");
                query.setParameter("id", cr);
                List<ReturnByCustomer> bList = (List<ReturnByCustomer>) query.list();
                
                return bList;
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
    public List<ReturnToSupplier> getLastReturnBySupplier(SupplierReturn cr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT r FROM ReturnToSupplier r WHERE r.supplierReturnId=:id");
                query.setParameter("id", cr);
                List<ReturnToSupplier> bList = (List<ReturnToSupplier>) query.list();
                return bList;
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
    
    
    public List<CustomerReturn> getCustomerReturn(Branch branch) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT c FROM CustomerReturn c WHERE c.branchId=:id ORDER BY invoice_id DESC");
                query.setParameter("id", branch);
                List<CustomerReturn> bList = (List<CustomerReturn>) query.list();
                return bList;
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
    public List<SupplierReturn> getSupplierReturn(Branch branch) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT c FROM SupplierReturn c WHERE c.branchId=:id");
                query.setParameter("id", branch);
                List<SupplierReturn> bList = (List<SupplierReturn>) query.list();
                return bList;
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
    public CustomerReturn getCustomerReturnById(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT c FROM CustomerReturn c WHERE c.id=:id");
                query.setParameter("id", id);
                CustomerReturn bList = (CustomerReturn) query.uniqueResult();
                return bList;
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
    public SupplierReturn getSupplierReturnById(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT c FROM SupplierReturn c WHERE c.id=:id");
                query.setParameter("id", id);
                SupplierReturn bList = (SupplierReturn) query.uniqueResult();
                return bList;
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
}
