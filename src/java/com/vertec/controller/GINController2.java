/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.GINDAOImpl2;
import com.vertec.hibe.model.Branch;
import com.vertec.hibe.model.SysUser;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vertec-r
 */
@WebServlet(name = "GINController2", urlPatterns = {"/GINController2"})
public class GINController2 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final GINDAOImpl2 GINDAOImpl = new GINDAOImpl2();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession httpSession = request.getSession();
            SysUser user1 = (SysUser) httpSession.getAttribute("user");
            RequestDispatcher requestDispatcher;
            System.out.println("IN GIN");
            switch (action) {
                case "toGIN": {
                    System.out.println("TO GIN");
                    String sid = request.getParameter("branch").trim();
                    System.out.println(sid);
                    Branch b = (Branch) GINDAOImpl.getBranch(Integer.parseInt(sid));
                    request.setAttribute("branch", b);
                    requestDispatcher = request.getRequestDispatcher("app/gin/gin.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
//                case "SubmitGIN": {
//                    try {
//                        System.out.println("Saving GIN");
//                        String data = request.getParameter("data");
//                        System.out.println(data);
//                        JSONParser parser = new JSONParser();
//                        Object obj;
//
//                        obj = parser.parse(data);
//
//                        JSONObject jSONObject = (JSONObject) obj;
//
//                        String bId = jSONObject.get("branch").toString();
//
//                        Date date = new Date();
//                        int branchid = 0;
//                        if (bId.isEmpty() || bId.equals("")) {
//
//                        } else {
//                            branchid = Integer.parseInt(bId);
//                        }
//
//                        Branch branch = new Branch(branchid);
//
//                        GinInfo gi = new GinInfo();
//                        gi.setDate(date);
//                        gi.setSysUserSysuserId(user1);
//                        gi.setBranchBranchId(branch);
//
//                        String result = GINDAOImpl.SaveGINInfo(gi);
//                        String result1 = "";
//                        String result2 = "";
//                        System.out.println("SAVED GIN INFO : " + result);
//                        JSONObject itemDetails = (JSONObject) jSONObject.get("item_details");
//
//                        for (Iterator iterator = itemDetails.keySet().iterator(); iterator.hasNext();) {
//                            String key = (String) iterator.next();
//                            JSONObject jo = (JSONObject) itemDetails.get(key);
//
//                            String productId = jo.get("productId").toString();
//                            String qty = jo.get("qty").toString();
//                            String sprice = jo.get("sprice").toString();
//                            String pprice = jo.get("pprice").toString();
//                            String wid = jo.get("wid").toString();
//
//                            WarehouseStock whs = new WarehouseStock(Integer.parseInt(wid));
//                            Gin gin = new Gin();
//                            gin.setPPrice(Double.parseDouble(pprice));
//                            gin.setSPrice(Double.parseDouble(sprice));
//                            gin.setWarehouseStockWarehouseStockId(whs);
//                            gin.setQty(Integer.parseInt(qty));
//                            gin.setGinInfoGinInfoId(gi);
//                            result1 = GINDAOImpl.SaveGIN(gin);
//                            System.out.println(productId);
//                            String proMaster = new GRNDAOImpl().getProductMaster(productId, pprice, sprice);
//
//                            ProductMaster pm = new ProductMaster();
//                            pm.setPurchasedPrice(Double.valueOf(pprice));
//                            pm.setSellingPrice(Double.parseDouble(sprice));
//                            pm.setProductMasterId(Integer.parseInt(proMaster));
//                            Product p = new Product(Integer.parseInt(productId));
//                            pm.setProductId(p);
//
//                            result2 = GINDAOImpl.UpdateBranchStock(pm, bId, wid, qty);
//                            result2 = GINDAOImpl.UpdateBranchStock2(pm, bId, wid, qty, user1);
//                            result2 = GINDAOImpl.UpdateWarehouseStock(wid, qty);
//
//                        }
//
//                        String res = VertecConstants.ERROR;
//
//                        if (result.equals(VertecConstants.SUCCESS) & result1.equals(VertecConstants.SUCCESS) & result2.equals(VertecConstants.SUCCESS)) {
//                            res = VertecConstants.SUCCESS;
//                        }
////                        System.gc();
//                        response.getWriter().write(res);
//                    } catch (Exception ex) {
//                        Logger.getLogger(POController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                    break;
//                }
//                case "viewGIN": {
//                    System.out.println("In View GIN");
//                    String type = request.getParameter("type").trim();
//
//                    request.setAttribute("type", type);
//
//                    if (type.equals("2")) {
//                        String from = request.getParameter("from").trim();
//                        String to = request.getParameter("to").trim();
//                        request.setAttribute("from", from);
//                        request.setAttribute("to", to);
//                    } else if (type.equals("3")) {
//                        String date = request.getParameter("from").trim();
//                        System.out.println("SET DATE AS :" + date);
//                        request.setAttribute("date", date);
//                    } else if (type.equals("4")) {
//                        String branch = request.getParameter("branch").trim();
//                        System.out.println("SET Branch AS :" + branch);
//                        request.setAttribute("branch", branch);
//                    }
//                    requestDispatcher = request.getRequestDispatcher("app/gin/ViewGIN.jsp");
//                    requestDispatcher.forward(request, response);
//                    break;
//                }
                case "GetItems": {
                    String id = request.getParameter("id");
                    String html = GINDAOImpl.getItems(id);
                    out.print(html);
                    break;
                }
                case "ProductFromCategory": {
                    String cid = request.getParameter("cid");
                    String html = GINDAOImpl.ProductFromCategory(cid);
                    out.print(html);
                    break;
                }
                case "ProductFromCategory2": {
                    String cid = request.getParameter("cid");
                    String html = GINDAOImpl.ProductFromCategoryForPO(cid);
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
