/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vertec.daoimpl;


/**
 *
 * @author vertec-r
 */

import com.vertec.hibe.model.Department;
import com.vertec.hibe.model.Designation;
import com.vertec.hibe.model.Employee;
import com.vertec.hibe.model.EmployeeType;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class EmployeeDAOImpl{

    public List<Department> getDepartments() {
    Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT d FROM Department d WHERE d.isValid = :type");
                query.setParameter("type", true);
                List<Department> department = query.list();
                return department;

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
    public List<Designation> getDesignation(int id) {
    Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT d FROM Designation d WHERE d.departmentId='"+id+"'");
                List<Designation> desig = query.list();
                return desig;
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
    public String deleteDepartment(int depid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.createQuery("UPDATE Department as d set d.isValid='0' where d.id=:depId");
                query.setParameter("depId", depid);
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
     public String SetImage(int id,String path) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.createQuery("UPDATE Employee e set e.image=:path where e.id=:id");
                query.setParameter("path", path);
                query.setParameter("id", id);
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

     
    public List<Designation> getDesignations() {
       Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT d FROM Designation d WHERE d.isValid = :type");
                query.setParameter("type", true);
                List<Designation> designation = query.list();
                return designation;
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

    
    public String deleteDesignation(int desid) {
    Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("UPDATE Designation as d set d.isValid='0' where d.id=:desId");
                query.setParameter("desId", desid);
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
    public List<Employee> getEmployees() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Employee s Where s.isValid=:isValid ");
                query.setParameter("isValid", true);
                List<Employee> cuList = query.list();
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
    
    public Employee getEmployeeByUID(String fpid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Employee s Where s.fpid=:fpid");
                query.setParameter("fpid", fpid);
                Employee cuList =(Employee) query.uniqueResult();
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
    
    
    public Employee getEmployee(int eId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

            try {
                Query query = session.getNamedQuery("Employee.findById");
                query.setParameter("id", eId);
                Employee emp = (Employee) query.uniqueResult();
                return emp;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        

        return null;
    }
    
    
     public List<EmployeeType> getEmployeeTypes() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
            try {
                Query query = session.getNamedQuery("EmployeeType.findAll");
                List<EmployeeType> emp = (List<EmployeeType>) query.list();
                return emp;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        return null;
    }
    
    
    public String updateEmployee(Employee em) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("Update employee set fname=:fname,appoint=:appoint,employee_type_id=:etype,experience=:exp,qualification=:quo,educational=:edu,height=:height,weight=:weight,contact_no=:contact,emergency=:emergency,lname=:lname,dob=:dob,nic=:nic,gender=:gender,basic_salary=:basic_salary,address=:address,designation_id=:designation_id,fpid=:fpid where id=:id");
                query.setParameter("fname", em.getFname());
                query.setParameter("lname", em.getLname());
                query.setParameter("dob", em.getDob());
                query.setParameter("nic", em.getNic());
                query.setParameter("gender", em.getGender());
                query.setParameter("basic_salary", em.getBasicSalary());
                query.setParameter("address", em.getAddress());
                query.setParameter("designation_id", em.getDesignationId());
                query.setParameter("fpid", em.getFpid());
                query.setParameter("id", em.getId());
                query.setParameter("contact", em.getContactNo());
                query.setParameter("emergency", em.getEmergency());
                query.setParameter("weight", em.getWeight());
                query.setParameter("height", em.getHeight());
                query.setParameter("edu", em.getEducational());
                query.setParameter("exp", em.getExperience());
                query.setParameter("quo", em.getQualification());
                query.setParameter("appoint", em.getAppoint());
                query.setParameter("etype", em.getEmployeeTypeId());
                
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
    public String deleteEmployee(String id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("Update employee set is_valid='0' where id=:id");
                query.setParameter("id", Integer.parseInt(id));
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    public String saveEmployeeType(EmployeeType employeeType) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {

            try {
                session.save(employeeType);
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

    public List<EmployeeType> getListOfEmployeeType() {

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT c FROM EmployeeType c");
                List<EmployeeType> csList = query.list();

                return csList;

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

    public EmployeeType viewEmployeeType(int etId) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("EmployeeType.findById");
                query.setParameter("id", etId);

                EmployeeType user = (EmployeeType) query.uniqueResult();
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

    public String updateEmployeeType(EmployeeType et) {
        System.out.println("Updating");
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("Update EmployeeType et set et.type=:type,et.annualLeaves=:al,et.monthlyLeaves=:ml,et.monthlyShortLeaves=:msl,et.etfEpf=:etf where et.id=:Id");
                query.setParameter("type", et.getType());
                query.setParameter("al", et.getAnnualLeaves());
                query.setParameter("ml", et.getMonthlyLeaves());
                query.setParameter("msl", et.getMonthlyShortLeaves());
                query.setParameter("etf", et.getEtfEpf());
                query.setParameter("Id", et.getId());
                
                System.out.println(et.getType());
                System.out.println(et.getAnnualLeaves());
                System.out.println(et.getMonthlyLeaves());
                System.out.println(et.getMonthlyShortLeaves());
                System.out.println(et.getId());
                
                
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

    
    public String removeEmployeeType(int etId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.createQuery("DELETE FROM EmployeeType et where et.id=:etId");
                query.setParameter("etId", etId);
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

    
    
    
    
}
