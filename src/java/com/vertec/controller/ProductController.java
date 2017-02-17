/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.ProductDAOImpl;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Product;
import com.vertec.hibe.model.ProductCategory;
import com.vertec.hibe.model.ProductMaster;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author User
 */
public class ProductController extends HttpServlet {

    private final ProductDAOImpl productDAOImpl = new ProductDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession httpSession = request.getSession();
        SysUser user1 = (SysUser) httpSession.getAttribute("user");
        Company company = (Company) httpSession.getAttribute("company");
        RequestDispatcher requestDispatcher;
        boolean isValidated = true;

        switch (action) {
            case "SaveProductCategory": {
                String categoryName = request.getParameter("categoryName").trim();
                String description = request.getParameter("description").trim();

                ProductCategory productCategory = new ProductCategory(categoryName, description, company);
                String result = productDAOImpl.saveProductCategory(productCategory);
                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Added Product Category");
                    response.sendRedirect("Product?action=ViewProductCategories");
                } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    response.sendRedirect("Product?action=ViewProductCategories");
                }

                break;
            }
            case "ViewProductCategories": {
                List<ProductCategory> pcList = productDAOImpl.loadAllProductCategories(company);
                request.setAttribute("pcList", pcList);
                requestDispatcher = request.getRequestDispatcher("app/product/addProductCategory.jsp");
                requestDispatcher.forward(request, response);
                break;

            }
            case "ViewProductCategory": {
                String pcId = request.getParameter("pcId").trim();
                int productCateId = 0;
                if (pcId != null) {
                    productCateId = Integer.parseInt(pcId);
                }
                ProductCategory pc = productDAOImpl.viewCategories(productCateId);
                request.setAttribute("pc", pc);
                requestDispatcher = request.getRequestDispatcher("app/product/viewProductCategory.jsp");
                requestDispatcher.forward(request, response);
                break;

            }
            case "SaveUpdatedPC": {
                String dataArr = request.getParameter("dataArr");
                String arr[] = dataArr.split("_,");
                String bId = arr[0];
                String branchName = arr[1];
                String description = arr[2];
                int branchId = 0;
                if (bId != null) {
                    branchId = Integer.parseInt(bId);
                }

                ProductCategory productCategory = new ProductCategory(branchId, branchName, description);
                String result = productDAOImpl.saveUpdatedPC(productCategory);

                response.getWriter().write(result);
                break;

            }
            case "SaveProduct": {
                System.out.println("IN SAVE METHOD of PRODUCT CONTROLLER");
                String productCode = request.getParameter("productCode").trim();
                String productName = request.getParameter("productName").trim();
                String description = request.getParameter("description").trim();
                String reorderLevel = request.getParameter("reorderLevel").trim();
                String productCategory = request.getParameter("productCategory").trim();
                
                int reO = 0;
                int pcId = 0;
                if (reorderLevel != null) {
                    reO = Integer.parseInt(reorderLevel);
                }
                if (productCategory != null) {
                    pcId = Integer.parseInt(productCategory);
                }
                ProductCategory pc = new ProductCategory(pcId);
                Product product = new Product(productCode, productName, description, reO, isValidated, pc,company);

                String result = productDAOImpl.saveProduct(product);

                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");
                    request.getSession().setAttribute("Success_Message", "Successfully Added Product");
                    response.sendRedirect("Product?action=ViewProducts");
                } else {
                    request.getSession().removeAttribute("Error_Message");
                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    response.sendRedirect("Product?action=ViewProducts");
                }

                break;
            }
            case "ViewProducts": {
                List<ProductCategory> pcList = productDAOImpl.loadAllProductCategories(company);
                List<Product> pList = productDAOImpl.loadAllProducts(company);
                request.setAttribute("pcList", pcList);
                request.setAttribute("pList", pList);
                requestDispatcher = request.getRequestDispatcher("app/product/addProduct.jsp");
                requestDispatcher.forward(request, response);
                break;

            }
            case "ViewProduct": {
                String pcId = request.getParameter("pcId").trim();
                int productId = 0;
                if (pcId != null) {
                    productId = Integer.parseInt(pcId);
                }
                Product pc = productDAOImpl.viewProducts(productId);
                request.setAttribute("pc", pc);
                requestDispatcher = request.getRequestDispatcher("app/product/viewProduct.jsp");
                requestDispatcher.forward(request, response);
                break;

            }
            case "SaveUpdatedProduct": {
                String dataArr = request.getParameter("dataArr");
                String arr[] = dataArr.split("_,");
                String proId = arr[0];
                String productName = arr[1];
                String productCode = arr[2];
                String description = arr[3];
                String reOLevel = arr[4];

                int productId = 0;
                int reorderLevel = 0;
                if (proId != null) {
                    productId = Integer.parseInt(proId);
                }
                if (reOLevel != null) {
                    reorderLevel = Integer.parseInt(reOLevel);
                }

                Product product = new Product(productId, productCode, productName, description, reorderLevel);
                String result = productDAOImpl.saveUpdatedProduct(product);

                response.getWriter().write(result);
                break;

            }
            case "DeleteProduct": {
                String dataArr = request.getParameter("dataArr");
                String arr[] = dataArr.split("_,");
                String proId = arr[0];
                int productId = 0;
                if (proId != null) {
                    productId = Integer.parseInt(proId);
                }
                String result = productDAOImpl.deleteProduct(productId);

                response.getWriter().write(result);
                break;
            }
            case "ToProductMaster": {
                List<Product> pList = productDAOImpl.loadAllProducts(company);
                request.setAttribute("productList", pList);
                requestDispatcher = request.getRequestDispatcher("app/product/manageProductMaster.jsp");
                requestDispatcher.forward(request, response);
                break;
            }

            case "SaveProductMaster": {
                String dataArr = request.getParameter("dataArr");
                String arr[] = dataArr.split("_,");
                String proId = arr[0];
                String pp = arr[1];
                String sp = arr[2];

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
//                ProductMaster productMaster = new ProductMaster(purchasedPrice, sellingPrice, isValidated, p);
                ProductMaster productMaster = new ProductMaster();
                productMaster.setPurchasedPrice(purchasedPrice);
                productMaster.setSellingPrice(sellingPrice);
                productMaster.setProductId(p);
                productMaster.setIsAvailable(isValidated);
                
                String result = productDAOImpl.savePM(productMaster);
                response.getWriter().write(result);
                break;
            }
            case "LoadProductMasters": {
                String pId = request.getParameter("productId");
                int productId = 0;
                if (pId != null) {
                    productId = Integer.parseInt(pId);
                }
                List<ProductMaster> pList = productDAOImpl.loadProductMasters(productId);

                JSONObject itemObject = new JSONObject();
                JSONArray itemPrice = new JSONArray();
                JSONObject ip = null;
                if (!pList.isEmpty()) {
                    for (ProductMaster im : pList) {
                        ip = new JSONObject();
                        ip.put("pmId", im.getProductMasterId());
                        ip.put("purchasedPrice", im.getPurchasedPrice());
                        ip.put("sellingPrice", im.getSellingPrice());
                        ip.put("pId", im.getProductId().getProductCode() + "_" + im.getProductId().getProductName());
                        itemPrice.add(ip);
                    }
                }
                itemObject.put("ip", itemPrice);

                response.getWriter().write(itemObject.toString());
                break;
            }
            case "SaveUpdatedPM": {
                String dataArr = request.getParameter("dataArr");
                String arr[] = dataArr.split("_,");
                String proId = arr[0];
                String product = arr[1];
                String pp = arr[2];
                String sp = arr[3];

                int pmId = 0;
                double purchasedPrice = 0;
                double sellingPrice = 0;

                if (proId != null) {
                    pmId = Integer.parseInt(proId);
                }
                if (pp != null) {
                    purchasedPrice = Double.parseDouble(pp);
                }
                if (sp != null) {
                    sellingPrice = Double.parseDouble(sp);
                }
//                ProductMaster productMaster = new ProductMaster(pmId, purchasedPrice, sellingPrice);
                ProductMaster productMaster = new ProductMaster();
                productMaster.setProductMasterId(pmId);
                productMaster.setPurchasedPrice(purchasedPrice);
                productMaster.setSellingPrice(sellingPrice);
                
                
                String result = productDAOImpl.saveUpdatedPM(productMaster);
                response.getWriter().write(result);
                break;
            }
            case "DeletePM": {
                String dataArr = request.getParameter("dataArr");
                int pmId = 0;
                if (dataArr != null) {
                    pmId = Integer.parseInt(dataArr);
                }
                String result = productDAOImpl.deletePM(pmId);
                response.getWriter().write(result);
                break;
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
