/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.GINDAOImpl;
import com.vertec.daoimpl.PoDAOImpl;
import com.vertec.hibe.model.BranchProductmaster;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Gin;
import com.vertec.hibe.model.GinInfo;
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
@WebServlet(name = "GINController", urlPatterns = {"/GINController"})
public class GINController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final GINDAOImpl GINDAOImpl = new GINDAOImpl();
    private final PoDAOImpl PoDAOImpl = new PoDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            RequestDispatcher requestDispatcher;

            HttpSession httpSession = request.getSession();
            SysUser user1 = (SysUser) httpSession.getAttribute("user");
            Company com = (Company) httpSession.getAttribute("company");

            if (action.equals("createGIN")) {
                System.out.println("CALLED TO GIN");
                requestDispatcher = request.getRequestDispatcher("app/gin/addGIN.jsp");
                requestDispatcher.forward(request, response);

            } else if (action.equals("toGIN")) {
                String type = request.getParameter("type");
                String mrn = request.getParameter("mrn");

                request.setAttribute("type", type);
                request.setAttribute("mrn", mrn);

                requestDispatcher = request.getRequestDispatcher("app/gin/gin.jsp");
                requestDispatcher.forward(request, response);
            } else if (action.equals("LoadMRN")) {
                System.out.println("READY TO LOAD MRN");
                JSONObject job = GINDAOImpl.LoadMRN(request.getParameter("poid"));
                response.getWriter().write(job.toString());
            } else if (action.equals("SubmitGIN")) {
                try {
                    System.out.println("Saving GIN");
                    String data = request.getParameter("data");
                    System.out.println(data);
                    JSONParser parser = new JSONParser();
                    Object obj;
                    obj = parser.parse(data);
                    System.out.println("0000");
                    JSONObject jSONObject = (JSONObject) obj;
                    String grntotal = jSONObject.get("gintotal").toString();
                    String mId = jSONObject.get("mrnId").toString();
                    System.out.println("0001");
                    Date date = new Date();

                    GinInfo gininfo = new GinInfo();
                    gininfo.setDate(date);
                    gininfo.setSysuserId(user1);
                    gininfo.setCompanyId(com);
                    gininfo.setTotal(Double.parseDouble(grntotal));
                    System.out.println("0002");
                    String result1 = GINDAOImpl.SaveGIN(gininfo);
                    String result2 = "";
                    String result3 = "";
                    String result4 = "";
                    System.out.println("SAVED GIN INFO : " + result1);
                    JSONObject itemDetails = (JSONObject) jSONObject.get("item_details");

                    System.out.println("Milestone 00");
                    for (Iterator iterator = itemDetails.keySet().iterator(); iterator.hasNext();) {
                        String key = (String) iterator.next();
                        JSONObject jo = (JSONObject) itemDetails.get(key);
                        System.out.println("Milestone 01");
                        String productId = jo.get("productId").toString();
                        String qty = jo.get("qty").toString();
                        String sprice = jo.get("sprice").toString();
                        System.out.println("PRODUCT ID IS : " + productId);
                        System.out.println("Selling Price : " + sprice);
//                        Product p = PoDAOImpl.getProduct(Integer.parseInt(productId));

                        Gin gin = new Gin();
                        BranchProductmaster bpm = GINDAOImpl.GetBranchProductMaster(sprice, com);
                        gin.setBpmId(bpm);
                        gin.setQty(Integer.valueOf(qty));
                        gin.setGinInfoId(gininfo);
                        gin.setTotal(bpm.getProductMasterId().getSellingPrice() * Double.valueOf(qty));

                        result2 = GINDAOImpl.SaveGINItem(gin);
                        System.out.println("Milestone 02");
                        System.out.println("UPDATING PO poid: " + mId + " product ID : " + productId + " Qty: " + qty);
                        result3 = GINDAOImpl.UpdateMRN(mId, productId, qty);
                        System.out.println("Milestone 03");
                        result4 = GINDAOImpl.UpdateBranchStock(gin);
                        System.out.println("Milestone 05");
                    }
                    String result = VertecConstants.FAILED;

                    if (result1.equals(VertecConstants.SUCCESS) & result2.equals(VertecConstants.SUCCESS) & result3.equals(VertecConstants.SUCCESS) & result4.equals(VertecConstants.SUCCESS)) {
                        result = VertecConstants.SUCCESS;
                    }
                    System.gc();
                    response.getWriter().write(result);
                } catch (Exception ex) {
                    System.out.println("ERROR IN GIN CONTROLLER");
                    ex.printStackTrace();
                }

            } else if (action.equals("viewGIN")) {

                System.out.println("In View GIN");
                String type = request.getParameter("type").trim();

                request.setAttribute("type", type);

                
                if (type.equals("3")) {
                    String from = request.getParameter("from").trim();
                    String to = request.getParameter("to").trim();
                    request.setAttribute("from", from);
                    request.setAttribute("to", to);
                }
                if (type.equals("4")) {
                    String date = request.getParameter("from").trim();
                    request.setAttribute("from", date);
                }
                requestDispatcher = request.getRequestDispatcher("app/gin/ViewGIN.jsp");
                requestDispatcher.forward(request, response);

            } else if (action.equals("GetItems")) {

                String id = request.getParameter("id");
                String html = GINDAOImpl.getItems(id);
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
