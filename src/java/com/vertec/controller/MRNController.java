/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.MRNDAOImpl;
import com.vertec.daoimpl.PoDAOImpl;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Mrn;
import com.vertec.hibe.model.MrnInfo;
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
@WebServlet(name = "MRNController", urlPatterns = {"/MRNController"})
public class MRNController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final MRNDAOImpl PoDAOImpl = new MRNDAOImpl();

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

                case "toMRN": {
                    requestDispatcher = request.getRequestDispatcher("app/mrn/MRN.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }

                case "SubmitMRN": {
                    System.out.println("Called to MRN");
                    try {
                        String data = request.getParameter("data");
                        JSONParser parser = new JSONParser();
                        Object obj;
                        obj = parser.parse(data);
                        JSONObject jSONObject = (JSONObject) obj;
                        Date date = new Date();
                        MrnInfo mrninfo = new MrnInfo();
                        mrninfo.setDate(date);
                        mrninfo.setSysuserId(user1);
                        mrninfo.setCompanyId(company);
                        String result = PoDAOImpl.saveMRN(mrninfo);
                        JSONObject itemDetails = (JSONObject) jSONObject.get("item_details");
                        System.out.println("Saved MRN Info");
                        for (Iterator iterator = itemDetails.keySet().iterator(); iterator.hasNext();) {
                            String key = (String) iterator.next();
                            JSONObject jo = (JSONObject) itemDetails.get(key);

                            String productId = jo.get("productId").toString();
                            String qty = jo.get("qty").toString();
                            Product p = PoDAOImpl.getProduct(Integer.parseInt(productId));

                            Mrn mrn = new Mrn();
                            mrn.setProductId(p);
                            mrn.setMrnInfoId(mrninfo);
                            mrn.setQty(Integer.parseInt(qty));
                            mrn.setAvailableQty(Integer.parseInt(qty));
                            result = PoDAOImpl.saveMRNItems(mrn);
                        }
                        response.getWriter().write(result);
                    } catch (ParseException ex) {
                        Logger.getLogger(MRNController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    break;
                }
                case "viewMRN": {
                    String type = request.getParameter("type").trim();
                    System.out.println("........." + type);

                    request.setAttribute("type", type);

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
                    requestDispatcher = request.getRequestDispatcher("app/mrn/ViewMRN.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "GetItems": {
                    String id = request.getParameter("id");
                    String html = PoDAOImpl.getItems1(id);
                    out.print(html);
                    break;
                }
                case "ChangeStatus": {
                    String id = request.getParameter("poId");
                    request.setAttribute("type", "1");
                    String result = PoDAOImpl.ChangeStatus(id);
                    requestDispatcher = request.getRequestDispatcher("app/mrn/ViewMRN.jsp?");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "createMRN": {
                    requestDispatcher = request.getRequestDispatcher("app/mrn/CreateMRN.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "ProductFromCategory": {
                    String cid = request.getParameter("cid");
                    String html = PoDAOImpl.ProductFromCategory(cid);
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
