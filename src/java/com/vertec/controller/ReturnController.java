/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.InvoiceDAOImpl;
import com.vertec.daoimpl.ProductDAOImpl;
import com.vertec.daoimpl.ReturnDAOImpl;
import com.vertec.daoimpl.SupplierDAOImpl;
import com.vertec.hibe.model.BranchProductmaster;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.CustomerReturn;
import com.vertec.hibe.model.DisposeItems;
import com.vertec.hibe.model.Invoice;
import com.vertec.hibe.model.InvoiceItem;
import com.vertec.hibe.model.Product;
import com.vertec.hibe.model.ProductMaster;
import com.vertec.hibe.model.ReturnByCustomer;
import com.vertec.hibe.model.ReturnBySupplier;
import com.vertec.hibe.model.ReturnStock;
import com.vertec.hibe.model.ReturnToCustomer;
import com.vertec.hibe.model.ReturnToSupplier;
import com.vertec.hibe.model.StockReturn;
import com.vertec.hibe.model.Supplier;
import com.vertec.hibe.model.SupplierReturn;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.Save;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author User
 */
public class ReturnController extends HttpServlet {

    private final ReturnDAOImpl returnDAOImpl = new ReturnDAOImpl();
    private final InvoiceDAOImpl invoiceDAOImpl = new InvoiceDAOImpl();
    private final ProductDAOImpl productDAOImpl = new ProductDAOImpl();
    private final SupplierDAOImpl supplierDAOImpl = new SupplierDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession httpSession = request.getSession();
        SysUser user1 = (SysUser) httpSession.getAttribute("user");
        Company company = (Company) httpSession.getAttribute("company");
        RequestDispatcher requestDispatcher;
        boolean isValidated = true;

        switch (action) {
            //load customer return page
            case "CustomerReturn": {
                List<Invoice> invoice = invoiceDAOImpl.getInvoices(company);
                request.setAttribute("invoice", invoice);
                List<Product> product = productDAOImpl.loadAllProducts(company);
                request.setAttribute("product", product);
                requestDispatcher = request.getRequestDispatcher("app/return/CustomerReturn.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            //Load stock return page
            case "StockReturn": {
                List<Product> product = productDAOImpl.loadAllProducts(company);
                request.setAttribute("product", product);
                requestDispatcher = request.getRequestDispatcher("app/return/StockReturn.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * Load supplier return page
             */
            case "SupplierReturn": {
                List<ReturnStock> rs = invoiceDAOImpl.getReturnStock(user1.getBranchBranchId());
                request.setAttribute("ReturnStock", rs);
                List<Supplier> supplier = supplierDAOImpl.getListofUsers(company);
                request.setAttribute("supplier", supplier);
                List<Product> product = productDAOImpl.loadAllProducts(company);
                request.setAttribute("product", product);
                requestDispatcher = request.getRequestDispatcher("app/return/SupplierReturn.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * Load dispose page
             */
            case "Dispose": {
                List<ReturnStock> rs = invoiceDAOImpl.getReturnStock(user1.getBranchBranchId());
                request.setAttribute("ReturnStock", rs);
                requestDispatcher = request.getRequestDispatcher("app/return/Dispose.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * Load issued invoice products
             */
            case "loadInvoiceProduct": {
                String invoice = request.getParameter("invoice");
                List<InvoiceItem> pList = returnDAOImpl.loadProduct(Integer.parseInt(invoice));
                JSONObject JOB = new JSONObject();
                JSONArray jar = new JSONArray();
                JSONObject job = null;
                for (InvoiceItem p : pList) {
                    job = new JSONObject();
                    job.put("id", p.getInvoiceItemId());
                    job.put("product", p.getProductMasterId().getProductId().getProductName());
                    job.put("price", p.getUnitPrice());
                    job.put("qty", p.getQuantity());
                    jar.add(job);
                }
                JOB.put("InvoiceItem", jar);
                response.getWriter().write(JOB.toString());
                break;
            }
            //get selling price and purchasing price
            case "loadProductMaster": {
                String product = request.getParameter("product");
                List<ProductMaster> pList = returnDAOImpl.loadProductMaster(Integer.parseInt(product));
                JSONObject JOB = new JSONObject();
                JSONArray jar = new JSONArray();
                JSONObject job = null;
                for (ProductMaster p : pList) {
                    job = new JSONObject();
                    job.put("id", p.getProductMasterId());
                    job.put("price", p.getSellingPrice());
                    job.put("price", p.getSellingPrice());
                    job.put("pprice", p.getPurchasedPrice());
                    jar.add(job);
                }
                JOB.put("InvoiceItem", jar);
                response.getWriter().write(JOB.toString());
                break;
            }
            //save customer return
            case "SubmitCustomerReturn": {
                String result = null;
                String data = request.getParameter("data");
                System.out.println(data);
                JSONParser parser = new JSONParser();
                Object obj = null;
                try {
                    obj = parser.parse(data);
                } catch (ParseException ex) {
                    Logger.getLogger(ReturnController.class.getName()).log(Level.SEVERE, null, ex);
                }
                JSONObject jSONObject = (JSONObject) obj;
                String invoiceId = jSONObject.get("invoiceId").toString();
                String total = jSONObject.get("total").toString();
                System.out.println("Total is : "+total);
                Date date = new Date();
                CustomerReturn cr = new CustomerReturn();
                cr.setAmount(Double.parseDouble(total));
                cr.setDate(date);
                cr.setBranchId(user1.getBranchBranchId());
                cr.setInvoiceId(new Invoice(Integer.parseInt(invoiceId)));
                result = new Save().Save(cr);
                JSONObject itemDetails = (JSONObject) jSONObject.get("item_details");
                for (Iterator iterator = itemDetails.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    JSONObject jSONObject1 = (JSONObject) itemDetails.get(key);
                    String invoiceItem = jSONObject1.get("invoiceItemId").toString();
                    String qty = jSONObject1.get("qty").toString();
                    ReturnByCustomer rbc = new ReturnByCustomer();
                    rbc.setCustomerReturnId(cr);
                    rbc.setInvoiceItemId(new InvoiceItem(Integer.parseInt(invoiceItem)));
                    rbc.setQty(Integer.parseInt(qty));
                    result = new Save().Save(rbc);
                    InvoiceItem ii = returnDAOImpl.getInvoiceItem(Integer.parseInt(invoiceItem));
                    BranchProductmaster bpm = returnDAOImpl.getBranchProductMaster(ii.getProductMasterId().getProductMasterId(), user1.getBranchBranchId(), company);
                    result = returnDAOImpl.UpdateReturnStock(ii.getProductMasterId().getProductMasterId(), user1.getBranchBranchId(), company, Integer.parseInt(qty));
                }
                JSONObject itemDetails2 = (JSONObject) jSONObject.get("returnedProducts");
                for (Iterator iterator = itemDetails2.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    JSONObject jSONObject1 = (JSONObject) itemDetails2.get(key);
                    String qty = jSONObject1.get("qty").toString();
                    String pm = jSONObject1.get("pm").toString();
                    ReturnToCustomer rtc = new ReturnToCustomer();
                    rtc.setCustomerReturnId(cr);
                    rtc.setQty(Integer.parseInt(qty));
                    rtc.setProductMasterId(new ProductMaster(Integer.parseInt(pm)));
                    result = new Save().Save(rtc);
                    returnDAOImpl.UpdateStock(user1.getBranchBranchId(), Integer.parseInt(qty), Integer.parseInt(pm), company);
                }

                if (result.equals(VertecConstants.SUCCESS)) {
                    response.getWriter().write(VertecConstants.SUCCESS);
                } else {
                    response.getWriter().write(VertecConstants.ERROR);
                }
                break;
            }
            /**
             * Open return stock page
             */
            case "ReturnStock": {
                List<ReturnStock> iiList = returnDAOImpl.loadReturnStock();
                request.setAttribute("returnstock", iiList);
                requestDispatcher = request.getRequestDispatcher("app/return/ReturnStock.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * Save stock return
             */
            case "SubmitStockReturn": {
                String result = VertecConstants.SUCCESS;
                String data = request.getParameter("data");
                System.out.println(data);
                JSONParser parser = new JSONParser();
                Object obj = null;
                try {
                    obj = parser.parse(data);
                } catch (ParseException ex) {
                    Logger.getLogger(ReturnController.class.getName()).log(Level.SEVERE, null, ex);
                }
                JSONObject jSONObject = (JSONObject) obj;
                Date date = new Date();
                JSONObject itemDetails2 = (JSONObject) jSONObject.get("returnedProducts");
                for (Iterator iterator = itemDetails2.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    JSONObject jSONObject1 = (JSONObject) itemDetails2.get(key);
                    String qty = jSONObject1.get("qty").toString();
                    String pm = jSONObject1.get("pm").toString();
                    StockReturn sr = new StockReturn();
                    sr.setDate(date);
                    sr.setProductMasterId(new ProductMaster(Integer.parseInt(pm)));
                    sr.setQty(Integer.parseInt(qty));
                    sr.setBranchId(user1.getBranchBranchId());
                    result = new Save().Save(sr);
                    result = returnDAOImpl.UpdateReturnStock(Integer.parseInt(pm), user1.getBranchBranchId(), company, Integer.parseInt(qty));
                    result = returnDAOImpl.UpdateStock(user1.getBranchBranchId(), Integer.parseInt(qty), Integer.parseInt(pm), company);
                }
                if (result.equals(VertecConstants.SUCCESS)) {
                    response.getWriter().write(VertecConstants.SUCCESS);
                } else {
                    response.getWriter().write(VertecConstants.ERROR);
                }
                break;
            }
            /**
             * Save supplier return
             */
            case "SubmitSupplierReturn": {
                String result = VertecConstants.SUCCESS;
                String data = request.getParameter("data");
                System.out.println(data);
                JSONParser parser = new JSONParser();
                Object obj = null;

                try {
                    obj = parser.parse(data);
                } catch (ParseException ex) {
                    Logger.getLogger(ReturnController.class.getName()).log(Level.SEVERE, null, ex);
                }
                JSONObject jSONObject = (JSONObject) obj;

                String supplierId = jSONObject.get("supplier").toString();
                Date date = new Date();

                SupplierReturn sr = new SupplierReturn();
                sr.setDate(date);
                sr.setSupplierId(new Supplier(Integer.parseInt(supplierId)));

                result = new Save().Save(sr);
                JSONObject itemDetails = (JSONObject) jSONObject.get("item_details");

                for (Iterator iterator = itemDetails.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    JSONObject jSONObject1 = (JSONObject) itemDetails.get(key);
                    String invoiceItem = jSONObject1.get("invoiceItemId").toString();
                    String qty = jSONObject1.get("qty").toString();

                    ReturnToSupplier rts = new ReturnToSupplier();
                    rts.setQty(Integer.parseInt(qty));
                    rts.setReturnStockId(new ReturnStock(Integer.parseInt(invoiceItem)));
                    rts.setSupplierReturnId(sr);
                    result = new Save().Save(rts);

//                    result = returnDAOImpl.UpdateReturnStock(ii.getProductMasterId().getProductMasterId(), user1.getBranchBranchId(), company, Integer.parseInt(qty));
                    result = returnDAOImpl.ReduceReturnStock(Integer.parseInt(invoiceItem), Integer.parseInt(qty));

                }

                JSONObject itemDetails2 = (JSONObject) jSONObject.get("returnedProducts");
                for (Iterator iterator = itemDetails2.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    JSONObject jSONObject1 = (JSONObject) itemDetails2.get(key);
                    String qty = jSONObject1.get("qty").toString();
                    String pm = jSONObject1.get("pm").toString();

                    ReturnBySupplier rbs = new ReturnBySupplier();
                    rbs.setProductMasterId(new ProductMaster(Integer.parseInt(pm)));
                    rbs.setQty(Integer.parseInt(qty));
                    rbs.setSupplierReturnId(sr);

                    result = new Save().Save(rbs);

//                    returnDAOImpl.UpdateStock(user1.getBranchBranchId(), Integer.parseInt(qty), Integer.parseInt(pm), company);
                    returnDAOImpl.AddStock(user1.getBranchBranchId(), Integer.parseInt(qty), Integer.parseInt(pm), company);
                }

                if (result.equals(VertecConstants.SUCCESS)) {
                    response.getWriter().write(VertecConstants.SUCCESS);
                } else {
                    response.getWriter().write(VertecConstants.ERROR);
                }

                break;
            }
            /**
             * Save dispose
             */
            case "SubmitDispose": {
                String result = VertecConstants.SUCCESS;
                String data = request.getParameter("data");
                JSONParser parser = new JSONParser();
                Object obj = null;
                try {
                    obj = parser.parse(data);
                } catch (ParseException ex) {
                    Logger.getLogger(ReturnController.class.getName()).log(Level.SEVERE, null, ex);
                }
                JSONObject jSONObject = (JSONObject) obj;

                Date date = new Date();

                JSONObject itemDetails2 = (JSONObject) jSONObject.get("returnedProducts");
                for (Iterator iterator = itemDetails2.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    JSONObject jSONObject1 = (JSONObject) itemDetails2.get(key);
                    String qty = jSONObject1.get("qty").toString();
                    String rsid = jSONObject1.get("rsid").toString();

                    System.out.println(" Saving 01");
                    DisposeItems di = new DisposeItems();
                    di.setBranchId(user1.getBranchBranchId());
                    di.setDate(date);
                    ReturnStock rs = returnDAOImpl.loadReturnStockById(Integer.parseInt(rsid));
                    di.setProductMasterId(rs.getProductMasterId());
                    di.setQty(Integer.parseInt(qty));
                    result = new Save().Save(di);
                    System.out.println(" Saving 02");

                    result = returnDAOImpl.ReduceReturnStock(Integer.parseInt(rsid), Integer.parseInt(qty));
                    System.out.println(" Saved");
                }
                System.out.println(">>>>>>>>>>>>>>>>>>"+result);
                if (result.equals(VertecConstants.SUCCESS)) {
                    response.getWriter().write(VertecConstants.SUCCESS);
                } else {
                    response.getWriter().write(VertecConstants.ERROR);
                }
                break;
            }
            /**
             * View supplier return note
             */
            case "SupplierReturnNote": {
                List<ReturnStock> rs = invoiceDAOImpl.getReturnStock(user1.getBranchBranchId());
                request.setAttribute("ReturnStock", rs);
                System.out.println("!!!!!!!!!!!!!!!!!!");
                System.out.println(rs);
                List<Supplier> supplier = supplierDAOImpl.getListofUsers(company);
                request.setAttribute("supplier", supplier);
                System.out.println(">>>>>>>>>>>>>>>");
                System.out.println(supplier);
                requestDispatcher = request.getRequestDispatcher("app/return/SupplierReturnNote.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * Save supplier return note
             */
            case "SubmitSupplierReturnNote": {
                String result = VertecConstants.SUCCESS;
                String data = request.getParameter("data");
                System.out.println(data);
                JSONParser parser = new JSONParser();
                Object obj = null;

                try {
                    obj = parser.parse(data);
                } catch (ParseException ex) {
                    Logger.getLogger(ReturnController.class.getName()).log(Level.SEVERE, null, ex);
                }
                JSONObject jSONObject = (JSONObject) obj;

                String supplierId = jSONObject.get("supplier").toString();
                String tot = jSONObject.get("total").toString();
                Date date = new Date();
                SupplierReturn sr = new SupplierReturn();
                sr.setDate(date);
                sr.setBranchId(user1.getBranchBranchId());
                sr.setAmount(Double.parseDouble(tot));
                sr.setSupplierId(new Supplier(Integer.parseInt(supplierId)));
                result = new Save().Save(sr);
                JSONObject itemDetails = (JSONObject) jSONObject.get("item_details");
                for (Iterator iterator = itemDetails.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    JSONObject jSONObject1 = (JSONObject) itemDetails.get(key);
                    String invoiceItem = jSONObject1.get("invoiceItemId").toString();
                    String qty = jSONObject1.get("qty").toString();
                    ReturnToSupplier rts = new ReturnToSupplier();
                    rts.setQty(Integer.parseInt(qty));
                    rts.setReturnStockId(new ReturnStock(Integer.parseInt(invoiceItem)));
                    rts.setSupplierReturnId(sr);
                    result = new Save().Save(rts);
//                    result = returnDAOImpl.UpdateReturnStock(ii.getProductMasterId().getProductMasterId(), user1.getBranchBranchId(), company, Integer.parseInt(qty));
                    result = returnDAOImpl.ReduceReturnStock(Integer.parseInt(invoiceItem), Integer.parseInt(qty));
                }
                if (result.equals(VertecConstants.SUCCESS)) {
                    response.getWriter().write(VertecConstants.SUCCESS);
                } else {
                    response.getWriter().write(VertecConstants.ERROR);
                }
                break;
            }
            /**
             * Open Customer return note
             */
            case "CustomerReturnNote": {
                List<Invoice> invoice = invoiceDAOImpl.getInvoices(company);
                request.setAttribute("invoice", invoice);
                requestDispatcher = request.getRequestDispatcher("app/return/CustomerReturnNote.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * Save customer return note
             */
            
            case "SubmitCustomerReturnNote": {
                String result = null;
                String data = request.getParameter("data");
                System.out.println(data);
                JSONParser parser = new JSONParser();
                Object obj = null;
                try {
                    obj = parser.parse(data);
                } catch (ParseException ex) {
                    Logger.getLogger(ReturnController.class.getName()).log(Level.SEVERE, null, ex);
                }
                JSONObject jSONObject = (JSONObject) obj;

                String invoiceId = jSONObject.get("invoiceId").toString();
                String tot = jSONObject.get("total").toString();
                System.out.println("Total is : "+tot);
                Date date = new Date();

                CustomerReturn cr = new CustomerReturn();
                cr.setDate(date);
                cr.setAmount(Double.parseDouble(tot));
                cr.setBranchId(user1.getBranchBranchId());
                cr.setInvoiceId(new Invoice(Integer.parseInt(invoiceId)));
                result = new Save().Save(cr);
                JSONObject itemDetails = (JSONObject) jSONObject.get("item_details");

                for (Iterator iterator = itemDetails.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    JSONObject jSONObject1 = (JSONObject) itemDetails.get(key);
                    String invoiceItem = jSONObject1.get("invoiceItemId").toString();
                    String qty = jSONObject1.get("qty").toString();

                    ReturnByCustomer rbc = new ReturnByCustomer();
                    rbc.setCustomerReturnId(cr);
                    rbc.setInvoiceItemId(new InvoiceItem(Integer.parseInt(invoiceItem)));
                    rbc.setQty(Integer.parseInt(qty));
                    result = new Save().Save(rbc);
                    InvoiceItem ii = returnDAOImpl.getInvoiceItem(Integer.parseInt(invoiceItem));
                    BranchProductmaster bpm = returnDAOImpl.getBranchProductMaster(ii.getProductMasterId().getProductMasterId(), user1.getBranchBranchId(), company);
                    result = returnDAOImpl.UpdateReturnStock(ii.getProductMasterId().getProductMasterId(), user1.getBranchBranchId(), company, Integer.parseInt(qty));
                }
                if (result.equals(VertecConstants.SUCCESS)) {
                    response.getWriter().write(VertecConstants.SUCCESS);
                } else {
                    response.getWriter().write(VertecConstants.ERROR);
                }
                break;
            }
            /**
             * Print customer return Note
             */
            case "PrintCustomerReturnNote": {
                CustomerReturn cr = returnDAOImpl.getLastCustomerInvoice(user1.getBranchBranchId());
                List<ReturnByCustomer> rbc = returnDAOImpl.getLastReturnByCustomer(cr);
                request.setAttribute("cr", cr);
                request.setAttribute("rbc", rbc);
                requestDispatcher = request.getRequestDispatcher("app/return/PrintCustomerReturnNote.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * View customer return note
             */
            case "ViewCustomerReturnNote": {
                List<CustomerReturn> cr = returnDAOImpl.getCustomerReturn(user1.getBranchBranchId());
                request.setAttribute("cr", cr);
                requestDispatcher = request.getRequestDispatcher("app/return/ViewCustomerGRN.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * View supplier return note
             */
            case "ViewSupplierReturnNote": {
                List<SupplierReturn> cr = returnDAOImpl.getSupplierReturn(user1.getBranchBranchId());
                request.setAttribute("cr", cr);
                requestDispatcher = request.getRequestDispatcher("app/return/ViewSupplierGRN.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * View credit note
             */
            case "ViewCreditNote": {
                String id= request.getParameter("id").trim();
                CustomerReturn cr = returnDAOImpl.getCustomerReturnById(Integer.parseInt(id));
                request.setAttribute("cr", cr);
                List<ReturnByCustomer> rbc = returnDAOImpl.getLastReturnByCustomer(cr);
                request.setAttribute("rbc", rbc);
                requestDispatcher = request.getRequestDispatcher("app/return/PrintCreditNote.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * Print supplier return note
             */
            case "PrintSupplierReturnNote": {
                String id= request.getParameter("id").trim();
                SupplierReturn sr = returnDAOImpl.getSupplierReturnById(Integer.parseInt(id));
                request.setAttribute("sr", sr);
                List<ReturnToSupplier> rbs = returnDAOImpl.getLastReturnBySupplier(sr);
                request.setAttribute("rbs", rbs);
                requestDispatcher = request.getRequestDispatcher("app/return/PrintSupplierReturnNote.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
        }
    }

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
