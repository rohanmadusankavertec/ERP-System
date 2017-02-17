/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.GRNDAOImpl;
import com.vertec.daoimpl.PoDAOImpl;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Grn;
import com.vertec.hibe.model.GrnInfo;
import com.vertec.hibe.model.Product;
import com.vertec.hibe.model.Supplier;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author vertec-r
 */
@WebServlet(name = "GRNController", urlPatterns = {"/GRNController"})
public class GRNController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final GRNDAOImpl GRNDAOImpl = new GRNDAOImpl();
    private final PoDAOImpl PoDAOImpl = new PoDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            RequestDispatcher requestDispatcher;

            HttpSession httpSession = request.getSession();
            SysUser user1 = (SysUser) httpSession.getAttribute("user");
            Company company = (Company) httpSession.getAttribute("company");

            if (action.equals("createGRN")) {
                /**
                 * load Create grn Page
                 */
                requestDispatcher = request.getRequestDispatcher("app/grn/addGRN.jsp");
                requestDispatcher.forward(request, response);
            } else if (action.equals("toGRN")) {
                /**
                 * Load goods received note Page
                 */
                String type = request.getParameter("type");
                String sup = request.getParameter("sup");
                String po = request.getParameter("po");

                if (type.equals("2")) {
                    System.out.println("RETRIEVING PO :" + po);
                    sup = GRNDAOImpl.getSupplier(po) + "";
                    System.out.println("PO SUPPLIER ID : " + sup);
                }
                System.out.println("Supplier " + sup);
                Supplier s = (Supplier) new PoDAOImpl().getSupplier(Integer.parseInt(sup));

                request.setAttribute("supId", s);
                request.setAttribute("type", type);
                request.setAttribute("po", po);

                requestDispatcher = request.getRequestDispatcher("app/grn/grn.jsp");
                requestDispatcher.forward(request, response);
            } else if (action.equals("LoadPO")) {
                /**
                 * Load PO into GRN
                 */
                System.out.println("READY TO LOAD PO");
                JSONObject job = GRNDAOImpl.LoadPO(request.getParameter("poid"));
                response.getWriter().write(job.toString());
            } else if (action.equals("SubmitGRN")) {
                /**
                 * Save goods received note
                 */
                try {
                    System.out.println("Saving GRN");
                    String data = request.getParameter("data");
                    System.out.println(data);
                    JSONParser parser = new JSONParser();
                    Object obj;

                    obj = parser.parse(data);

                    JSONObject jSONObject = (JSONObject) obj;

                    String supId = jSONObject.get("supplierId").toString();
                    String grntotal = jSONObject.get("grntotal").toString();
                    String poId = jSONObject.get("poId").toString();

                    Date date = new Date();
                    int supplierId = 0;
                    if (supId.isEmpty() || supId.equals("")) {

                    } else {
                        supplierId = Integer.parseInt(supId);
                    }

                    Supplier sup = GRNDAOImpl.getSupplierObj(supplierId + "");

                    Supplier supplier = new Supplier(supplierId);

                    GrnInfo grninfo = new GrnInfo();
                    grninfo.setDate(date);
                    grninfo.setSupplierSupplierId(supplier);
                    grninfo.setSysUserSysuserId(user1);
                    grninfo.setTotal(Double.parseDouble(grntotal));
                    grninfo.setCompanyId(company);
                    String result1 = GRNDAOImpl.SaveGRN(grninfo);
                    String result2 = "";
                    String result3 = "";
                    String result4 = "";
                    System.out.println("SAVED GRN INFO : " + result1);
                    JSONObject itemDetails = (JSONObject) jSONObject.get("item_details");

                    for (Iterator iterator = itemDetails.keySet().iterator(); iterator.hasNext();) {
                        String key = (String) iterator.next();
                        JSONObject jo = (JSONObject) itemDetails.get(key);

                        String productId = jo.get("productId").toString();
                        String qty = jo.get("qty").toString();
                        String price = jo.get("price").toString();
                        String sprice = jo.get("sprice").toString();
                        System.out.println("PRODUCT ID IS : " + productId);
                        Product p = PoDAOImpl.getProduct(Integer.parseInt(productId));

                        Grn grn = new Grn();
                        grn.setProductProductId(p);
                        grn.setPPrice(Double.parseDouble(price));
                        grn.setQty(Integer.valueOf(qty));
                        grn.setSPrice(Double.parseDouble(sprice));
                        grn.setGrnInfoGrnInfoId(grninfo);
                        grn.setTotal(Double.parseDouble(price) * Double.valueOf(qty));

                        result2 = GRNDAOImpl.SaveGRNItem(grn);

                        System.out.println("UPDATING PO poid: " + poId + " product ID : " + productId + " Qty: " + qty);
                        result3 = GRNDAOImpl.UpdatePO(poId, productId, qty);

                        result4 = GRNDAOImpl.UpdateBranchStock(grn, sup, user1,company);
                    }
                    String result = VertecConstants.FAILED;

                    if (result1.equals(VertecConstants.SUCCESS) & result2.equals(VertecConstants.SUCCESS) & result3.equals(VertecConstants.SUCCESS) & result4.equals(VertecConstants.SUCCESS)) {
                        result = VertecConstants.SUCCESS;
                    }
                    System.gc();
                    response.getWriter().write(result);
                } catch (Exception ex) {
                    System.out.println("ERROR IN GRN CONTROLLER");
                    System.out.println(ex);
                }

            } else if (action.equals("viewGRN")) {
                /**
                 * Load view grn Page
                 */

                System.out.println("In View GRN");
                String type = request.getParameter("type").trim();

                request.setAttribute("type", type);

                if (type.equals("2")) {
                    String sup = request.getParameter("supplier").trim();
                    request.setAttribute("supId", sup);
                }
                if (type.equals("3")) {
                    String from = request.getParameter("from").trim();
                    String to = request.getParameter("to").trim();
                    request.setAttribute("from", from);
                    request.setAttribute("to", to);
                }
                if (type.equals("3")) {
                    String date = request.getParameter("from").trim();
                    request.setAttribute("from", date);
                }
                requestDispatcher = request.getRequestDispatcher("app/grn/ViewGRN.jsp");
                requestDispatcher.forward(request, response);

            } else if (action.equals("GetItems")) {
                /**
                 * get GRN items according to gin
                 */
                String id = request.getParameter("id");
                String html = GRNDAOImpl.getItems(id);
                out.print(html);

            } else if (action.equals("ProductFromCategory2")) {
                /**
                 * get products according to category
                 */
                String cid = request.getParameter("cid");
                String html = GRNDAOImpl.ProductFromCategoryForPO(cid);
                out.print(html);

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
