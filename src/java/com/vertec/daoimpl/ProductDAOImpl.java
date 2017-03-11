/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Product;
import com.vertec.hibe.model.ProductCategory;
import com.vertec.hibe.model.ProductMaster;
import com.vertec.hibe.model.Tax;
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
public class ProductDAOImpl {

    public String saveProductCategory(ProductCategory productCategory) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(productCategory);
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

    public List<ProductCategory> loadAllProductCategories(Company company) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT c FROM ProductCategory c WHERE c.companyId=:com");
                query.setParameter("com", company);
                List<ProductCategory> pcList = query.list();
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

    public ProductCategory viewCategories(int pcId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("ProductCategory.findByProductCategoryId");
                query.setParameter("productCategoryId", pcId);

                ProductCategory pc = (ProductCategory) query.uniqueResult();
                return pc;

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

    public String saveUpdatedPC(ProductCategory productCategory) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE ProductCategory as b set  b.productCategoryName=:productCategoryName,b.description=:description where b.productCategoryId=:pcId");

                query.setParameter("productCategoryName", productCategory.getProductCategoryName());
                query.setParameter("description", productCategory.getDescription());
                query.setParameter("pcId", productCategory.getProductCategoryId());
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

    public List<Product> loadAllProducts(Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT p FROM Product p WHERE p.isAvailable=:isAvailable AND p.companyId=:com");
                query.setParameter("isAvailable", true);
                query.setParameter("com", com);
                List<Product> pList = query.list();
                return pList;

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

    public List<Tax> loadAllTax(Company com) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT p FROM Tax p WHERE p.companyId=:com");
                query.setParameter("com", com);
                List<Tax> pList = query.list();
                return pList;

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

    public String saveproductMaster(String proId, String pp, String sp) {

        int productId = 0;
        double purchasedPrice = 0;
        double sellingPrice = 0;

        if (proId != null) {
            productId = Integer.parseInt(proId);
        }
        if (pp != null) {
            purchasedPrice = Double.parseDouble(pp);
        }
        if (sp != null) {
            sellingPrice = Double.parseDouble(sp);
        }
        Product p = new Product(productId);
//        ProductMaster productMaster = new ProductMaster(purchasedPrice, sellingPrice, true, p);
        ProductMaster productMaster = new ProductMaster();

        productMaster.setIsAvailable(true);
        productMaster.setProductId(p);
        productMaster.setPurchasedPrice(purchasedPrice);
        productMaster.setSellingPrice(sellingPrice);

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(productMaster);
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

    public String saveProduct(Product product) {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(product);

                session.flush();

                transaction.commit();
//                if(saveproductMaster(product.getProductId().toString(), pp, sp).equals("Success")){
//                return VertecConstants.SUCCESS;
//                }
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

    public Product viewProducts(int pId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("Product.findByProductId");
                query.setParameter("productId", pId);

                Product pc = (Product) query.uniqueResult();
                return pc;

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

    public String saveUpdatedProduct(Product product) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE Product as p set  p.productName=:productName,p.productCode=:productCode,p.productDescription=:productDescription,p.reOrderLevel=:reOrderLevel where p.productId=:productId");

                query.setParameter("productName", product.getProductName());
                query.setParameter("productCode", product.getProductCode());
                query.setParameter("productDescription", product.getProductDescription());
                query.setParameter("reOrderLevel", product.getReOrderLevel());
                query.setParameter("productId", product.getProductId());
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

    public String deleteProduct(int pId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE Product as p set  p.isAvailable=:isAvailable where p.productId=:productId");

                query.setParameter("isAvailable", false);
                query.setParameter("productId", pId);
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

    public List<ProductMaster> loadProductMasters(int pId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.createQuery("From ProductMaster pm Where pm.productId.productId=:productId and pm.isAvailable=:isAvailable");
                query.setParameter("productId", pId);
                query.setParameter("isAvailable", true);

                List<ProductMaster> pList = query.list();
                return pList;

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

    public String savePM(ProductMaster productMaster) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(productMaster);
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

    public String saveUpdatedPM(ProductMaster productMaster) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE ProductMaster as b set  b.purchasedPrice=:purchasedPrice,b.sellingPrice=:sellingPrice where b.productMasterId=:productMasterId");

                query.setParameter("purchasedPrice", productMaster.getPurchasedPrice());
                query.setParameter("sellingPrice", productMaster.getSellingPrice());
                query.setParameter("productMasterId", productMaster.getProductMasterId());
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

    public String deletePM(int pmId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE ProductMaster as b set  b.isAvailable=:isAvailable where b.productMasterId=:productMasterId");

                query.setParameter("isAvailable", false);
                query.setParameter("productMasterId", pmId);
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

}
