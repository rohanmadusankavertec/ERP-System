/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.GTNDAOImpl;
import com.vertec.hibe.model.Branch;
import com.vertec.hibe.model.BranchProductmaster;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Gtn;
import com.vertec.hibe.model.GtnInfo;
import com.vertec.hibe.model.Product;
import com.vertec.hibe.model.ProductMaster;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
 * @author Java-Dev-Ruchira
 */
@WebServlet(name = "GTNController", urlPatterns = {"/GTNController"})
public class GTNController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private final GTNDAOImpl gtndao = new GTNDAOImpl();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession httpSession = request.getSession();
            SysUser user1 = (SysUser) httpSession.getAttribute("user");
            Company company = (Company) httpSession.getAttribute("company");
            RequestDispatcher requestDispatcher;
//            String action = request.getParameter("action");
//            HttpSession httpSession = request.getSession();
//            SysUser user1 = (SysUser) httpSession.getAttribute("user");
//            RequestDispatcher requestDispatcher;
            
            switch(action){
                /**
                 * load add gtn page 
                 */
                case "loadBranch":{
                    
                    List<Branch> bList = gtndao.loadBranch(company);
                    request.setAttribute("bList", bList);
                    
                    requestDispatcher = request.getRequestDispatcher("app/gtn/addGTN.jsp");
                    requestDispatcher.forward(request, response);
                    
                    break;
                }
                /**
                 * load gtn page
                 */
                case "loadGtnPage":{
                    
                    String fb = request.getParameter("fBranch");
                    String tb = request.getParameter("tBranch");
//                    System.out.println("fb..."+fb);
//                    System.out.println("tb..."+tb);
                    List<BranchProductmaster> pList = gtndao.loadProductCategory(Integer.parseInt(fb));
                   
                    
                    request.setAttribute("fb", fb);
                    request.setAttribute("tb", tb);
                    request.setAttribute("pList", pList);
                    
                    requestDispatcher = request.getRequestDispatcher("app/gtn/GTN.jsp");
                    requestDispatcher.forward(request, response);
                    
                    break;
                }
                /**
                 * load product on select element
                 */
                case "loadProcut":{ 
                    
                    String fb = request.getParameter("category");
                    
                    List<Product> pList = gtndao.loadProduct(Integer.parseInt(fb));
                    JSONObject JOB = new JSONObject();
                    JSONArray jar = new JSONArray();
                    JSONObject job = null;
                    for(Product p: pList){
                        
                        job = new JSONObject();
                        
                        job.put("id", p.getProductId());
                        job.put("name", p.getProductName());
                        job.put("code", p.getProductCode());
                        
                        jar.add(job);
                        
                    }
                    
                    JOB.put("product", jar);
                    
                   response.getWriter().write(JOB.toString());
                    break;
                }
                /**
                 * get the sale price
                 */
                case "loadSalePrice":{
                    String pid = request.getParameter("product");
                    
                    List<ProductMaster> pmList = gtndao.loadSellingPrice(Integer.parseInt(pid));
                    JSONObject JOB =new JSONObject();
                    JSONArray jar = new JSONArray();
                    JSONObject job = null;
                    
                    for(ProductMaster m: pmList){
                        job = new JSONObject();
                        
                        job.put("id", m.getProductMasterId());
                        job.put("price", m.getSellingPrice());
                        
                        jar.add(job);
                    }
                    JOB.put("saleprice", jar);
                    
                    response.getWriter().write(JOB.toString());
                    break;
                    
                }
                /**
                 * load avialable quantity
                 */
                case "loadAvialableQty":{
                    String pmid = request.getParameter("sprice");
                    
                    int aQty = gtndao.loadAvailableQty(Integer.parseInt(pmid));
                    
                    JSONObject JOB =new JSONObject();
                    JOB.put("aqty",aQty);
                    
                    response.getWriter().write(JOB.toString());
                    break;
                    
                }
                /**
                 * add new gtn
                 */
                case "SubmitGTN":{
                    double tot=0;
                    String gtnData = request.getParameter("data");
                    System.out.println(gtnData.toString());
                    
                    
                    
                    JSONParser parser = new JSONParser();
                    Object obj;
                    obj = parser.parse(gtnData);
                    JSONObject jSONObject = (JSONObject) obj;
                    
                    String fd = jSONObject.get("fdate").toString();
                    String td = jSONObject.get("todate").toString();
                    
                    System.out.println(fd+" bbbbbbbbb"+td);
                    
                    GtnInfo info = new GtnInfo();
                    JSONObject itemDetails = (JSONObject) jSONObject.get("item_details");
                    
                    
                    
                    
                   
//               
                String result="";
                    Collection<Gtn> gtnList = new ArrayList<>();
                
                for (Iterator iterator = itemDetails.keySet().iterator(); iterator.hasNext();) {

                    String key = (String) iterator.next();
                    JSONObject jSONObject1 = (JSONObject) itemDetails.get(key);

                    String product = jSONObject1.get("productId").toString();
                    System.out.println("-------"+product);
                    String bmp = jSONObject1.get("bpmid").toString();
                    System.out.println("-------"+bmp);
//                    String productName = jSONObject1.get("productName").toString();
                    String sellPrice = jSONObject1.get("sellPrice").toString();
                    System.out.println("-------"+sellPrice);
                    String qty = jSONObject1.get("quantity").toString();
                    System.out.println("-------"+qty);
                    String amount = jSONObject1.get("totalAmount").toString();
                    String aqty = jSONObject1.get("Aqty").toString();
                    System.out.println("-------"+amount);
                    

                    tot += Double.parseDouble(amount);
  


                        Gtn g = new Gtn();
                        g.setGtninfoId(info);
                        g.setProductMasterId(new ProductMaster(Integer.parseInt(bmp)));
                        g.setQty(Integer.parseInt(qty));
                        g.setTotal(Double.parseDouble(amount));
                        gtnList.add(g);
                        
                        int dqty = Integer.parseInt(aqty)-Integer.parseInt(qty);
                        
                        String result1 = gtndao.updateQty(Integer.parseInt(bmp), dqty);
                        System.out.println(" "+result1);
                        
                    
                }
                
                   System.out.println("-------"+tot);
                   info.setGtnCollection(gtnList);
                    info.setDate(new Date());
                    info.setAddedBy(user1);
                    info.setTotal(tot);
                    info.setFromBranch(new Branch(Integer.parseInt(fd)));
                    info.setToBranch(new Branch(Integer.parseInt(td)));
                    info.setIsValid(true);
                    info.setIsPending(true);
                    
                    result = gtndao.saveGtn(info);
                    
                    if (result.equals(VertecConstants.SUCCESS)) {
                            out.write(VertecConstants.SUCCESS);
                        } else {
                            out.write(VertecConstants.ERROR);
                        }
                    
                    
                    break;
                    
                }
                /**
                 * load details of gtn
                 */
                case "viewGTNInfo":{
                   
                    List<GtnInfo> gList = gtndao.viewGTNInfo(user1.getBranchBranchId());
                    request.setAttribute("gList", gList);
                    requestDispatcher = request.getRequestDispatcher("app/gtn/viewGTN.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                    
                }
                /**
                 * load gtn details according to the branch
                 */
                case "viewGTNInfoByToBranch":{
                   
                    int bid = user1.getBranchBranchId().getBranchId();
                    System.out.println(".........."+bid);
                    List<GtnInfo> gList = gtndao.viewGTNInfoByToBranch(bid);
                    request.setAttribute("gbList", gList);
                    requestDispatcher = request.getRequestDispatcher("app/gtn/viewToBranch.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                    
                }
                /**
                 * get the gtn details 
                 */
                case "viewGTN":{
                   
                    String gid = request.getParameter("employeeId").trim();
//                    System.out.println("----"+gid);
//                    Gtn g = gtndao.viewGTN(Integer.parseInt(gid));
                    List<Gtn> gList = gtndao.viewGTN(Integer.parseInt(gid));
                    GtnInfo info = gtndao.viewGTNinfo(Integer.parseInt(gid));
                    request.setAttribute("g", gList);
                    request.setAttribute("info", info);
                    requestDispatcher = request.getRequestDispatcher("app/gtn/viewToBranchDetails.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                    
                }
                /**
                 * update action to branch
                 */
                case "actionByToBranch1":{
                   
                    String gid = request.getParameter("hidden").trim();
                    boolean b = true;
                   
                    String result = gtndao.updateAction(Integer.parseInt(gid), b);
                  if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Added");
                    response.sendRedirect("GTN?action=viewGTNInfoByToBranch");
                     } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Action,Please Tri again");
                    response.sendRedirect("GTN?action=viewGTNInfoByToBranch");
                    
                    }
                  break;
                    
                }
                /**
                 * Update action from Branch
                 */
                case "actionByToBranch2":{
                   
//                    String act = request.getParameter("action1").trim();
                    String gid = request.getParameter("hidden").trim();
                    System.out.println("/////"+gid);
                    
                    boolean b = false;
                    
                    String result = gtndao.updateAction(Integer.parseInt(gid), b);
                    
                    String result1="";
                    
                    List<Gtn> gList = gtndao.viewGTN(Integer.parseInt(gid));
                    for (Gtn g : gList) {
                        int pmid = g.getProductMasterId().getProductMasterId();
                        int qty = g.getQty();
                        int aqty = gtndao.loadAvailableQty(pmid);
                        
                        int newQty = qty+aqty;
                        
                        result1 = gtndao.updateCancelGTN(pmid, newQty);
                    }
                    
                    
                    
                  if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Added");
                    response.sendRedirect("GTN?action=viewGTNInfoByToBranch");
                     } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not ACtion,Please Tri again");
                    response.sendRedirect("GTN?action=viewGTNInfoByToBranch");
                    
                    }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(GTNController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(GTNController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
