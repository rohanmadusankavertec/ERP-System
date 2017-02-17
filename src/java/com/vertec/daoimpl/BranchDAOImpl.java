/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;


import com.vertec.hibe.model.Branch;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author User
 */
public class BranchDAOImpl{

    public String saveBranch(Branch branch) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(branch);
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

//    public String saveBranchExpense(BranchExpenses branch) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//
//                session.save(branch);
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
    
    
    public List<Branch> loadAllBranches(Branch branch) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
//                Query query = session.getNamedQuery("Branch.findAll");
                Query query = session.createQuery("SELECT b FROM Branch b WHERE b.branchId=:branch");
                query.setParameter("branch", branch.getBranchId());
//                Query query = session.createQuery("SELECT b FROM branch b WHERE b.branch_id>0");

                List<Branch> bList = query.list();
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
 
    public Branch viewBranch(int branchId) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("Branch.findByBranchId");
                query.setParameter("branchId", branchId);

                Branch branch = (Branch) query.uniqueResult();
                return branch;

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

    public String updateBranch(Branch branch) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE Branch as b set  b.branchName=:branchName,b.contactNo=:contactNo,b.address=:address where b.branchId=:branchId");

                query.setParameter("branchName", branch.getBranchName());
                query.setParameter("contactNo", branch.getContactNo());
                query.setParameter("address", branch.getAddress());
                query.setParameter("branchId", branch.getBranchId());
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
    
    
     public String deleteExpenses(String id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("delete from BranchExpenses where branchExpensesId='"+id+"'");
                
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
    
    
    
//    public String saveWarehouse(Warehouse warehouse) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//
//                session.save(warehouse);
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

//    public List<Warehouse> loadAllWarehouses() {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//                Query query = session.getNamedQuery("Warehouse.findAll");
//
//                List<Warehouse> wList = query.list();
//                return wList;
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

//    public Warehouse viewWarehouse(int branchId) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//                Query query = session.getNamedQuery("Warehouse.findByWarehouseId");
//                query.setParameter("warehouseId", branchId);
//
//                Warehouse warehouse = (Warehouse) query.uniqueResult();
//                return warehouse;
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

//    public String updateWarehouse(Warehouse warehouse) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//
//                Query query = session.createQuery("UPDATE Warehouse as b set  b.warehouseName=:branchName,b.contactNo=:contactNo,b.address=:address where b.warehouseId=:branchId");
//
//                query.setParameter("branchName", warehouse.getWarehouseName());
//                query.setParameter("contactNo", warehouse.getContactNo());
//                query.setParameter("address", warehouse.getAddress());
//                query.setParameter("branchId", warehouse.getWarehouseId());
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
//    }

}
