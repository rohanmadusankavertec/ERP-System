/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;


import com.vertec.hibe.model.Branch;
import com.vertec.hibe.model.BranchProductmaster;
import com.vertec.hibe.model.BranchStock;
import com.vertec.hibe.model.Company;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author User
 */
public class StockDAOImpl{

    /**
     * call from InvoiceController case "ToInvoice" call from StockController
     * case "ToBranch" and "ToVehicle"
     *
     * @param branchId
     * @return
     */
    
    public List<Object[]> loadProductFromBranchStock(int branchId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("Select p.product_id,p.product_code,p.product_name From branch_stock bs inner join product p on bs.product_id=p.product_id where bs.branch_id=:branchId group by p.product_id order by p.product_code asc");
                query.setParameter("branchId", branchId);
                List<Object[]> prList = query.list();
                return prList;

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
    
    public BranchProductmaster loadBranchProductMaster(Branch branchId,int pmid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT b FROM BranchProductmaster b WHERE b.branchId=:branchId AND b.productMasterId.productMasterId=:pmid");
                query.setParameter("branchId", branchId);
                query.setParameter("pmid", pmid);
                BranchProductmaster prList = (BranchProductmaster) query.uniqueResult();
                return prList;

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
    
    public List<Object[]> loadProductFromWarehouseStock() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("Select p.product_id,p.product_code,p.product_name From warehouse_stock ws inner join product_master pm on pm.product_master_id=ws.product_master_product_master_id inner join product p on pm.product_id=p.product_id group by p.product_id order by p.product_code asc");
                
                List<Object[]> prList = query.list();
                return prList;

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
     * from StockController case "ToBranchPM"
     *
     * @param arr
     * @return
     */
    
    public List<Object[]> loadProductMasterFromBPM(int[] arr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("AAA");
        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("SELECT bp.bpm_id,bp.product_master_id,p.product_id,p.product_code,p.product_name,bp.quantity,pm.purchased_price,pm.selling_price\n"
                        + "FROM branch_productmaster bp inner join product_master pm on bp.product_master_id=pm.product_master_id\n"
                        + "inner join product p on pm.product_id=p.product_id where bp.branch_id=:branch_id and p.product_id=:product_id");
                query.setParameter("branch_id", arr[0]);
                query.setParameter("product_id", arr[1]);
                
                
                List<Object[]> prList = query.list();
                return prList;

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
     * from StockController case "ToAddBPM"
     *
     * @param arr
     * @return
     */
    
    public List<Object[]> loadProductMasterToAddBPM(int[] arr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("SELECT * FROM product_master pm where pm.product_id=:product_id and pm.product_master_id not in (SELECT product_master_id FROM branch_productmaster where branch_id=:branchId);");
//                SQLQuery query = session.createSQLQuery("SELECT pm.product_master_id,pm.product_id,pm.purchased_price,pm.selling_price,pm.is_available FROM product_master pm inner join branch_productmaster bpm on bpm.product_master_id=pm.product_master_id where pm.product_id:product_id and bpm.branch_id=:branchId ;");
                
                query.setParameter("branchId", arr[0]);
                query.setParameter("product_id", arr[1]);
                List<Object[]> prList = query.list();
                return prList;

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
     * call from InvoiceController case "SubmitInvoice" call from
     * StockController case "SaveBPM" and "SaveUpdatedStock"
     *
     * @param arr
     * @return
     */
    
    public List<BranchStock> viewAvailability(int[] arr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                System.out.println("___________________________________________");
                System.out.println(arr[0]);
                System.out.println(arr[1]);
                System.out.println("___________________________________________");
                
                Query query = session.createQuery("select bs from BranchStock bs where bs.branchId.branchId='"+arr[0]+"' and bs.productId.productId='"+arr[1]+"'");
                
                List<BranchStock> prList = query.list();
                return prList;

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

    
    public List<Object[]> viewAvailabilitylist(int[] arr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                System.out.println("___________________________________________");
                System.out.println(arr[0]);
                System.out.println(arr[1]);
                System.out.println("___________________________________________");
                
                Query query = session.createQuery("select branch_stock_id,quantity from BranchStock where branchId.branchId=:branchId and productId.productId=:productid");
                
                query.setParameter("branchId", arr[0]);
                query.setParameter("productid", arr[1]);
                List<Object[]> prList = query.list();
                return prList;

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
     * call from StockController case "SaveBPM"
     *
     * @param branchProductmaster
     * @return
     */
    
    public String saveBranchPM(BranchProductmaster branchProductmaster) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(branchProductmaster);
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
     * call from StockController case "SaveBPM"
     *
     * @param branchStock
     * @return
     */
    
    public String saveBranchStock(BranchStock branchStock) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(branchStock);
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
     * call from StockController case
     * "SaveBPM","SaveUpdatedStock","SaveVehicleStock","SaveUpdatedVehicleStock"
     * call from InvoiceController case
     * "SubmitInvoice","DeleteInvoice","UpdateAll"
     *
     * @param branchStock
     * @return
     */
    
    public String updateBranchStock(BranchStock branchStock) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE BranchStock as bs set  bs.quantity=:quantity,bs.lastUpdated=:lastUpdated,bs.updatedBy=:updatedBy where bs.branchStockId=:branchStockId");

                query.setParameter("quantity", branchStock.getQuantity());
                query.setParameter("lastUpdated", branchStock.getLastUpdated());
                query.setParameter("updatedBy", branchStock.getUpdatedBy());
                query.setParameter("branchStockId", branchStock.getBranchStockId());
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

    public String updateBranchStockByAdjustment(int qty, int bpmid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("UPDATE BranchProductmaster b SET b.quantity=:qty WHERE b.bpmId=:bpmid");
                query.setParameter("bpmid", bpmid);
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
    /**
     * call from StockController case
     * "SaveBPM","SaveUpdatedStock","SaveVehicleStock","SaveUpdatedVehicleStock"
     * call from InvoiceController case "SubmitInvoice"
     *
     * @param bpmId
     * @return
     */
    
    public BranchProductmaster viewBranchPM(int bpmId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("BranchProductmaster.findByBpmId");
                query.setParameter("bpmId", bpmId);
                BranchProductmaster bp = (BranchProductmaster) query.uniqueResult();
                return bp;

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
     * call from StockController case
     * "SaveUpdatedStock","SaveVehicleStock","SaveUpdatedVehicleStock" call from
     * InvoiceController case "SubmitInvoice","DeleteInvoice","UpdateAll"
     *
     * @param branchProductmaster
     * @return
     */
    
    public String updateBranchPM(BranchProductmaster branchProductmaster) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE BranchProductmaster as bp set bp.quantity=:quantity,bp.lastUpdatedDate=:lastUpdatedDate Where bp.bpmId=:bpmId");

                query.setParameter("quantity", branchProductmaster.getQuantity());
                query.setParameter("lastUpdatedDate", branchProductmaster.getLastUpdatedDate());
                query.setParameter("bpmId", branchProductmaster.getBpmId());

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
     * call from StockController case "ToAddNewVehicleStock"
     *
     * @param arr
     * @return
     */
    
    public List<Object[]> loadToAddVehicleStock(int[] arr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("SELECT bp.bpm_id,bp.product_master_id,pmr.purchased_price,pmr.selling_price,bp.quantity FROM branch_productmaster bp\n"
                        + "inner join product_master pmr on bp.product_master_id=pmr.product_master_id\n"
                        + "where pmr.product_id=:product_id and bp.branch_id=:branch_id and pmr.is_available=:is_available and bp.bpm_id not in (SELECT vs.bpm_id\n"
                        + "FROM vehicle_stock vs\n"
                        + "inner join vehicle v on vs.vehicle_id=v.vehicle_id\n"
                        + "inner join branch_productmaster bpm on vs.bpm_id=bpm.bpm_id\n"
                        + "inner join product_master pm on bpm.product_master_id=pm.product_master_id where pm.product_id=:product_id and v.branch_id=:branch_id and vs.vehicle_id=:vehicle_id)");
                query.setParameter("branch_id", arr[0]);
                query.setParameter("product_id", arr[1]);
                query.setParameter("vehicle_id", arr[2]);
                query.setParameter("is_available", true);
                List<Object[]> prList = query.list();
                return prList;

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
     * call from StockController case "ToUpdateVehicleStock"
     *
     * @param arr
     * @return
     */
    
    public List<Object[]> loadToUpdateVehicleStock(int[] arr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("SELECT vs.vehicle_stock_id,vs.bpm_id,vs.quantity as vquan,pm.purchased_price,pm.selling_price,bpm.quantity FROM vehicle_stock vs\n"
                        + "inner join branch_productmaster bpm on vs.bpm_id=bpm.bpm_id\n"
                        + "inner join product_master pm on bpm.product_master_id=pm.product_master_id\n"
                        + "where pm.product_id=:product_id and vs.vehicle_id=:vehicle_id and pm.is_available=:is_available");
                query.setParameter("product_id", arr[1]);
                query.setParameter("vehicle_id", arr[2]);
                query.setParameter("is_available", true);
                List<Object[]> prList = query.list();
                return prList;

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
     * call from StockController case "SaveVehicleStock"
     *
     * @param vehicleStock
     * @return
     */
    
//    public String saveVehicleStock(VehicleStock vehicleStock) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//
//                session.save(vehicleStock);
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

    /**
     * 
     *
     * @param productId
     * @return
     */
    
    public BranchStock viewBranchStock(int productId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.createQuery("From BranchStock bs Where bs.branchId.branchId=:branchId");
                query.setParameter("branchId", productId);
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
     * call from StockController case "SaveUpdatedVehicleStock"
     *
     * @param vsId
     * @return
     */
    
//    public VehicleStock viewVehicleStock(int vsId) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//                Query query = session.createQuery("From VehicleStock vs Where vs.vehicleStockId=:vehicleStockId");
//                query.setParameter("vehicleStockId", vsId);
//                VehicleStock vs = (VehicleStock) query.uniqueResult();
//                return vs;
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

    /**
     * call from StockController case "SaveUpdatedVehicleStock"
     *call from InvoiceController case "SubmitVInvoice","DeleteInvoice","UpdateAll"
     * @param vehicleStock
     * @return
     */
    
//    public String saveUpdateVehicleStock(VehicleStock vehicleStock) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//
//                Query query = session.createQuery("UPDATE VehicleStock vs set  vs.quantity=:quantity,vs.lastUpdatedDate=:lastUpdatedDate Where vs.vehicleStockId=:vehicleStockId");
//
//                query.setParameter("quantity", vehicleStock.getQuantity());
//                query.setParameter("lastUpdatedDate", vehicleStock.getLastUpdatedDate());
//                query.setParameter("vehicleStockId", vehicleStock.getVehicleStockId());
//
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

    /**
     * call from InvoiceController case "LoadBPMToInvoice"
     *
     * @param arr
     * @return
     */
    
    public List<Object[]> loadBPMForInvoice(int[] arr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("SELECT bpm.bpm_id,bpm.product_master_id,bpm.quantity as branchquan,pm.purchased_price,pm.selling_price\n"
                        + "FROM branch_productmaster bpm inner join product_master pm on (bpm.product_master_id=pm.product_master_id)\n"
                        + "where pm.product_id=:product_id and bpm.branch_id=:branch_id and pm.is_available=:is_available");
                query.setParameter("product_id", arr[1]);
                query.setParameter("branch_id", arr[0]);
                query.setParameter("is_available", true);
                List<Object[]> bpmList = query.list();
                return bpmList;

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

    
    
    
    public List<Object[]> loadWSForInvoice(int pro) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                
                SQLQuery query2=session.createSQLQuery("select ws.warehouse_stock_id,ws.qty,pm.purchased_price,pm.selling_price from warehouse_stock ws inner join product_master pm on pm.product_master_id=ws.product_master_product_master_id where pm.product_id=:product_id and ws.qty>0");
                
                
//                SQLQuery query = session.createSQLQuery("SELECT bpm.bpm_id,bpm.product_master_id,bpm.quantity as branchquan,pm.purchased_price,pm.selling_price\n"
//                        + "FROM branch_productmaster bpm inner join product_master pm on (bpm.product_master_id=pm.product_master_id)\n"
//                        + "where pm.product_id=:product_id and bpm.branch_id=:branch_id and pm.is_available=:is_available");
                query2.setParameter("product_id", pro);
//                query.setParameter("is_available", true);
                List<Object[]> bpmList = query2.list();
                return bpmList;

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
     * call from InvoiceController case "LoadBPMToInvoice"
     *
     * @param arr
     * @return
     */
    
    public List<Object[]> loadVSForInvoice(int[] arr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("select vs.bpm_id,pm.product_master_id,vs.quantity as vquan,pm.purchased_price,pm.selling_price from vehicle_stock vs\n"
                        + "inner join branch_productmaster bpm on vs.bpm_id=bpm.bpm_id\n"
                        + "inner join product_master pm on bpm.product_master_id=pm.product_master_id\n"
                        + "inner join vehicle v on vs.vehicle_id=v.vehicle_id\n"
                        + "where pm.product_id=:product_id and v.vehicle_id=:vehicle_id and pm.is_available=:is_available");
                query.setParameter("product_id", arr[1]);
                query.setParameter("vehicle_id", arr[0]);
                query.setParameter("is_available", true);
                List<Object[]> bpmList = query.list();
                return bpmList;

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
     * call from InvoiceController case "SubmitVInvoice"
     *
     * @param bpmId
     * @return
     */
    
//    public VehicleStock loadVS(int bpmId) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//                Query query = session.createQuery("From VehicleStock vs Where vs.bpmId.bpmId=:bpmId");
//                query.setParameter("bpmId", bpmId);
//                VehicleStock vs = (VehicleStock) query.uniqueResult();
//                return vs;
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

    /**
     * call from InvoiceController case "DeleteInvoice" and "UpdateAll"
     *
     * @param arr
     * @return
     */
    
    public BranchProductmaster loadBranchPM(int[] arr) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.createQuery("From BranchProductmaster bpm Where bpm.productMasterId.productMasterId=:productMasterId and bpm.branchId.branchId=:branchId");
                query.setParameter("productMasterId", arr[0]);
                query.setParameter("branchId", arr[1]);
                BranchProductmaster bpm = (BranchProductmaster) query.uniqueResult();
                return bpm;

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
     * call from InvoiceController case "DeleteInvoice" and "UpdateAll"
     *
     * @param arr
     * @return
     */
    
//    public VehicleStock viewVStock(int[] arr) {
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//
//        if (session != null) {
//            try {
//                Query query = session.createQuery("From VehicleStock vs Where vs.bpmId.bpmId=:bpmId and vs.vehicleId.vehicleId=:vehicleId");
//                query.setParameter("bpmId", arr[0]);
//                query.setParameter("vehicleId", arr[1]);
//                VehicleStock vs = (VehicleStock) query.uniqueResult();
//                return vs;
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
    
    
    public List<String[]> viewProductByBranch(String bid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                
                String hql="";
                if(bid.equals("All_All")){
                    hql="SELECT b.productMasterId.productId.productCode,b.productMasterId.productId.productName,b.productMasterId.productId.productDescription,b.productMasterId.productId.reOrderLevel,SUM(b.quantity) FROM BranchProductmaster b GROUP BY b.productMasterId.productId.productId ";
                }else{
                    hql="SELECT b.productMasterId.productId.productCode,b.productMasterId.productId.productName,b.productMasterId.productId.productDescription,b.productMasterId.productId.reOrderLevel,SUM(b.quantity) FROM BranchProductmaster b WHERE b.branchId.branchId=:bid GROUP BY b.productMasterId.productId.productId ";
                }
                
                
                Query query2=session.createQuery(hql);
                if(!bid.equals("All_All")){
                query2.setParameter("bid", Integer.parseInt(bid));
                }
                List<Object[]> bpmList = query2.list();
                
                List<String[]> Stringarr=new ArrayList<String[]>();
                for(Object[] arr:bpmList){
                    
                    if(Integer.parseInt(arr[3].toString())>Integer.parseInt(arr[4].toString())){
                        
                    String[] sarr={arr[0].toString(),arr[1].toString(),arr[2].toString(),arr[3].toString(),arr[4].toString()};
                    Stringarr.add(sarr);
                        
                    }
                }
                
                return Stringarr;

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
    public List<Branch> loadAllBran(Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
//                Query query = session.getNamedQuery("Branch.findAll");
                Query query = session.createQuery("SELECT b FROM Branch b WHERE b.companyId=:com");
                query.setParameter("com", com);
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

}
