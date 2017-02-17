/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.PoDAOImpl;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.PoInfo;
import com.vertec.hibe.model.Product;
import com.vertec.hibe.model.PurchasingOrder;
import com.vertec.hibe.model.Supplier;
import com.vertec.hibe.model.SysUser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author vertec-r
 */
@WebServlet(name = "POController", urlPatterns = {"/POController"})
public class POController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final PoDAOImpl PoDAOImpl = new PoDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession httpSession = request.getSession();
            RequestDispatcher requestDispatcher;
            SysUser user1 = (SysUser) httpSession.getAttribute("user");
            Company company = (Company) httpSession.getAttribute("company");
            switch (action) {
                /**
                 * Load purchasing order Page
                 */
                case "toPO": {
                    String sid = request.getParameter("supId").trim();
                    Supplier s = (Supplier) PoDAOImpl.getSupplier(Integer.parseInt(sid));
                    request.setAttribute("supId", s);
                    requestDispatcher = request.getRequestDispatcher("app/po/po.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * Save Purchasing order
                 */
                case "SubmitPO": {
                    try {
                        String data = request.getParameter("data");
                        JSONParser parser = new JSONParser();
                        Object obj;

                        obj = parser.parse(data);

                        JSONObject jSONObject = (JSONObject) obj;

                        String supId = jSONObject.get("supplierId").toString();
                        String pototal = jSONObject.get("pototal").toString();

                        Date date = new Date();
                        int supplierId = 0;
                        if (supId.isEmpty() || supId.equals("")) {

                        } else {
                            supplierId = Integer.parseInt(supId);
                        }

                        Supplier supplier = new Supplier(supplierId);

                        PoInfo poinfo = new PoInfo();
                        poinfo.setDate(date);
                        poinfo.setStatus(false);
                        poinfo.setSupplierSupplierId(supplier);
                        poinfo.setTotal(Double.parseDouble(pototal));
                        poinfo.setCompanyId(company);
                        poinfo.setSysUserSysuserId(user1);
                        String result = PoDAOImpl.savePO(poinfo);
                        JSONObject itemDetails = (JSONObject) jSONObject.get("item_details");

                        for (Iterator iterator = itemDetails.keySet().iterator(); iterator.hasNext();) {
                            String key = (String) iterator.next();
                            JSONObject jo = (JSONObject) itemDetails.get(key);

                            String productId = jo.get("productId").toString();
                            String qty = jo.get("qty").toString();
                            String price = jo.get("price").toString();
                            String total = jo.get("total").toString();
                            Product p = PoDAOImpl.getProduct(Integer.parseInt(productId));
                            PurchasingOrder po = new PurchasingOrder();
                            po.setProductProductId(p);
                            po.setPoInfoPoInfoId(poinfo);
                            po.setPrice(Double.parseDouble(price));
                            po.setQty(Integer.parseInt(qty));
                            po.setAvailableQty(Integer.parseInt(qty));
                            po.setTotal(Double.parseDouble(total));
                            result = PoDAOImpl.savePOItems(po);
                        }
                        response.getWriter().write(result);
                    } catch (ParseException ex) {
                        Logger.getLogger(POController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    break;
                }
                /**
                 * View PO
                 */
                
                case "viewPO": {
                    String type = request.getParameter("type").trim();
                    System.out.println("........."+type);

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
                        request.setAttribute("date", date);
                    }
                    requestDispatcher = request.getRequestDispatcher("app/po/ViewPO.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * Get po items according to po id
                 */
                case "GetItems": {
                    String id = request.getParameter("id");
                    String html = PoDAOImpl.getItems1(id);
                    out.print(html);
                    break;
                }
                /**
                 * ]Change po status
                 */
                case "ChangeStatus": {
                    String id = request.getParameter("poId");
                    request.setAttribute("type", "1");
                    String result = PoDAOImpl.ChangeStatus(id);
                    requestDispatcher = request.getRequestDispatcher("app/po/ViewPO.jsp?");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * load create po Page
                 */
                case "createPO": {
                    requestDispatcher = request.getRequestDispatcher("app/po/CreatePO.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * Load Products according to category
                 */
                case "ProductFromCategory": {
                    String cid = request.getParameter("cid");
                    String html = PoDAOImpl.ProductFromCategory(cid,company);
                    out.print(html);
                    break;

                }
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
