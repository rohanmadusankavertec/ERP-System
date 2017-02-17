/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vertec.daoimpl;


import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Supplier;
import com.vertec.hibe.model.SupplierGroup;
import com.vertec.hibe.model.SupplierHasGroup;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author vertec-r
 */
public class SupplierDAOImpl {

    
    public String saveSupplier(Supplier supplier) {
      Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(supplier);
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

    
    public List<Supplier> getListofUsers(Company com) {
       Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Supplier s Where s.isActive=:isActive AND s.companyId=:com");
                query.setParameter("isActive", true);
                query.setParameter("com", com);
                List<Supplier> cuList = query.list();
                return cuList;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;}

    
    public String removeSupplier(int supplierId) {
       Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("Update supplier set is_active=:is_active where supplier_id=:supplier_id");

                query.setParameter("supplier_id", supplierId);
                query.setParameter("is_active", false);

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

    
    public String updateSupplier(Supplier supplier) {
      

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("Update supplier set supplier_name=:supplier_name,company_name=:company_name,address=:address,fax=:fax,land=:land,mobile=:mobile,email=:email,type=:type where supplier_id=:supplier_id");
                System.out.println("GOT QUERY....");
                query.setParameter("supplier_name", supplier.getSupplierName());
                query.setParameter("company_name", supplier.getCompanyName());
                query.setParameter("email", supplier.getEmail());
                query.setParameter("mobile", supplier.getMobile());
                query.setParameter("land", supplier.getLand());
                query.setParameter("fax", supplier.getFax());
                query.setParameter("address", supplier.getAddress());
                query.setParameter("supplier_id", supplier.getSupplierId());
                query.setParameter("type", supplier.getType());

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

    
    public String checkEmail(String email) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        String status = "";
        if (session != null) {
            try {
                String HQL = "From Supplier c where c.email=:email";
                Query query = session.createQuery(HQL);
                query.setParameter("email", email);
                Supplier c = (Supplier) query.uniqueResult();
                if (c != null) {
                    status = VertecConstants.ALREADYEXIST;
                    return status;
                } else {
                    status = VertecConstants.NOT_EXIST;
                    return status;
                }
            } catch (Exception e) {
                e.printStackTrace();
                status = VertecConstants.ERROR;
                return status;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return status;}

    
    public Supplier viewSupplier(int supplierId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("Supplier.findBySupplierId");
                query.setParameter("supplierId", supplierId);

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

        return null;}
    
    
    public String saveSupplierGroup(SupplierGroup suppg) {
      Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(suppg);
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
    public List<SupplierGroup> getListofSupplerGroup(Company com) {
       Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM SupplierGroup s WHERE s.companyId=:com");
                query.setParameter("com", com);
                List<SupplierGroup> cuList = query.list();
                return cuList;

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
    
    public SupplierGroup viewSupplerGroup(int id) {
       Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM SupplierGroup s WHERE s.id=:id");
                query.setParameter("id", id);
                SupplierGroup sg =(SupplierGroup) query.uniqueResult();
                return sg;

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
    
    
    public String updateSupplierGroup(SupplierGroup sg) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE SupplierGroup as s set s.name=:name WHERE s.id=:id");

                query.setParameter("name", sg.getName());
                query.setParameter("id", sg.getId());
//                query.setParameter("bpmid", bpmid);
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
    public List<SupplierHasGroup> loadSupplierByGroup(int id) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM SupplierHasGroup s Where s.supplierGroupId.id=:gId");
//                query.setParameter("isActive", true);
                query.setParameter("gId", id);

//                SQLQuery query = session.createSQLQuery("SELECT customer_id,customername FROM customer c where is_active=:isActive");
//                query.setParameter("isActive", true);
                List<SupplierHasGroup> cuList = query.list();
                return cuList;

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
    public String saveSupplierHasGroup(SupplierHasGroup cusgroup) {
      Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(cusgroup);
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

     public List<Supplier> getListofUsers() {
       Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Supplier s Where s.isActive=:isActive ");
                query.setParameter("isActive", true);
                List<Supplier> cuList = query.list();
                return cuList;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;}

}
