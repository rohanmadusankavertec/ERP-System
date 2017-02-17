/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;


import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Customer;
import com.vertec.hibe.model.CustomerGroup;
import com.vertec.hibe.model.CustomerHasGroup;
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
public class CustomerDAOImpl{

    
    public String saveCustomer(Customer customer) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(customer);
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

    
    public List<Customer> getListofUsers(Company company) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Customer s Where s.isActive=:isActive AND s.companyId=:company");
                query.setParameter("isActive", true);
                query.setParameter("company", company);

//                SQLQuery query = session.createSQLQuery("SELECT customer_id,customername FROM customer c where is_active=:isActive");
//                query.setParameter("isActive", true);
                List<Customer> cuList = query.list();
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

    
    public String removeCustomer(int customerId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("Update customer set is_active=:is_active where customer_id=:customer_id");

                query.setParameter("customer_id", customerId);
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

    
    public Customer viewCustomer(int customerId) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {

                Query query = session.createQuery("select c from Customer c where c.customerId=:customerId");
//                        
//                SQLQuery query = session.createSQLQuery("select customer_id,customer_name,address from customer where customer_id=:customerId");
                query.setParameter("customerId", customerId);

                
                Customer customer = (Customer) query.uniqueResult();
                
                
                
                
//                List<Object[]> inList = query.list();
//
//                Customer customer = new Customer();
//                for (Object[] list : inList) {
//                    customer.setCustomerId(Integer.parseInt(list[0].toString()));
//                    customer.setCustomerName(list[1].toString());
//                    String add=null;
//                    System.out.println("CUSTOMER ADDRESS : "+list[2]);
//                    if(list[2]!=null){
//                        add=list[2].toString();
//                    }
//                    
//                    customer.setAddress(add);
//                }

                return customer;

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

    
    public String updateCustomer(Customer customer) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                SQLQuery query = session.createSQLQuery("Update customer set customer_name=:customer_name,address=:address,hotline=:hotline,office_no=:office_no,fax_no=:fax_no,contact_person=:contact_person,contact_person_no=:contact_person_no,contact_person_email=:contact_person_email where customer_id=:customer_id");

                query.setParameter("customer_name", customer.getCustomerName());
                query.setParameter("address", customer.getAddress());
                query.setParameter("hotline", customer.getHotline());
                query.setParameter("office_no", customer.getOfficeNo());
                query.setParameter("fax_no", customer.getFaxNo());
                query.setParameter("contact_person", customer.getContactPerson());
                query.setParameter("contact_person_no", customer.getContactPersonNo());
                query.setParameter("contact_person_email", customer.getContactPersonEmail());
                query.setParameter("customer_id", customer.getCustomerId());

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
        Transaction transaction = session.beginTransaction();
        String status = "";
        if (session != null) {
            try {
                String HQL = "From Customer c where c.contactPersonEmail=:email";
                Query query = session.createQuery(HQL);
                query.setParameter("email", email);
                Customer c = (Customer) query.uniqueResult();
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
        return status;
    }
    
    
     public List<CustomerGroup> getListofCustomerGroup(Company com) {
       Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT c FROM CustomerGroup c WHERE c.companyId=:com");
                query.setParameter("com", com);
                List<CustomerGroup> cuList = query.list();
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
     public CustomerGroup viewCustomerGroup(int id) {
       Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM CustomerGroup s WHERE s.id=:id");
                query.setParameter("id", id);
                CustomerGroup sg =(CustomerGroup) query.uniqueResult();
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
    
    
    public String updateCustomerGroup(CustomerGroup cg) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE CustomerGroup as s set s.name=:name WHERE s.id=:id");

                query.setParameter("name", cg.getName());
                query.setParameter("id", cg.getId());
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
    
    public String saveCustomerGroup(CustomerGroup cusgroup) {
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
    public List<CustomerHasGroup> loadCustomerByGroup(int id) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM CustomerHasGroup s Where s.customerGroupId.id=:gId");
//                query.setParameter("isActive", true);
                query.setParameter("gId", id);

//                SQLQuery query = session.createSQLQuery("SELECT customer_id,customername FROM customer c where is_active=:isActive");
//                query.setParameter("isActive", true);
                List<CustomerHasGroup> cuList = query.list();
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
    public String saveCustomerHasGroup(CustomerHasGroup cusgroup) {
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

}
