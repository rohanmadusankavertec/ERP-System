/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.AccountDAOImpl;
import com.vertec.daoimpl.IncomeDAOImpl;
import com.vertec.hibe.model.Account;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.SysUser;
import com.vertec.hibe.model.Transaction;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Java-Dev-Ruchira
 */
@WebServlet(name = "IncomeController", urlPatterns = {"/IncomeController"})
public class IncomeController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private  final AccountDAOImpl accountdao = new AccountDAOImpl();
    private  final IncomeDAOImpl incomedao = new IncomeDAOImpl();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String action = request.getParameter("action");
            HttpSession httpSession = request.getSession();
            SysUser user1 = (SysUser) httpSession.getAttribute("user");
            Company company = (Company) httpSession.getAttribute("company");
            RequestDispatcher requestDispatcher;
            
            switch (action){
                case "loadIncomePage":{
                    
                    List<Account> acList = incomedao.viewAllOfAccount();
                    request.setAttribute("acc", acList);
                    requestDispatcher = request.getRequestDispatcher("/app/transaction/AddIncome.jsp");
                    requestDispatcher.forward(request, response);
                    
                    break;
                }
                
                case "loadOfAccounyByPayType":{
                    
                    String payType = request.getParameter("PayType").trim();
                    
                    int companyId = company.getId();
                    
                    List<Account> acList = incomedao.viewAllOfAccountByPayType(payType,companyId);
                    
                    JSONObject JOB = new JSONObject();
                    JSONArray jarr = new JSONArray();
                    JSONObject job = null;
                    
                    for(Account a : acList){
                        job = new JSONObject();
                        
                        job.put("id", a.getId());
                        job.put("name", a.getName());
                        
                        jarr.add(job);
                    }
                    JOB.put("account", jarr);
                    
                    response.getWriter().write(JOB.toString());
                    
                    break;
                }
                
                case "saveIncome":{
                    System.out.println("kkkkkk");
                    String cAcc = request.getParameter("account").trim();
                    String amount = request.getParameter("amount").trim();
                    String descrip = request.getParameter("descrip").trim();
                    String dAcc = request.getParameter("acc").trim();
                    
                    System.out.println(cAcc);
                    System.out.println(amount);
                    System.out.println(descrip);
                    System.out.println(dAcc);
                    String  result= "";
                    Transaction t = new Transaction();
                    t.setCredit(new Account(Integer.parseInt(cAcc)));
//                    t.setAmount(Double.parseDouble(amount));
                    t.setPaidAmount(Double.parseDouble(amount));
                    t.setPrice(Double.parseDouble(amount));
                    t.setDiscount(0.0);
                    t.setDescription(descrip);
                    t.setDebit(new Account(Integer.parseInt(dAcc)));
                    t.setDate(new Date());
                    t.setCompanyId(company);
                    if(cAcc !="" && amount != "" && descrip != "" && dAcc != ""){
                        result = incomedao.saveIncome(t);
                    }
                    if (result.equals(VertecConstants.SUCCESS)) {
//                    request.getSession().removeAttribute("Success_Message");
//                    request.getSession().setAttribute("Success_Message", "Successfully Added");
//                    response.sendRedirect("dashboard.jsp");
                        out.write(VertecConstants.SUCCESS);
                    } 
                    else {
//                        request.getSession().removeAttribute("Error_Message");
//                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
//                        response.sendRedirect("dashboard.jsp");
                        out.write(VertecConstants.ERROR);
                    }
                    
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
