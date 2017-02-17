/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.AccountDAOImpl;
import com.vertec.hibe.model.Account;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Subtype;
import com.vertec.hibe.model.SysUser;
import com.vertec.hibe.model.Type;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AccountController", urlPatterns = {"/AccountController"})
public class AccountController extends HttpServlet {

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
                
                case "loadOfType":{// load add account page
                    System.out.println("Calling to account Module");
                    List<Type> tList = (List<Type>)accountdao.loadofType();
                    request.setAttribute("type", tList);
                    List<Account> accList = (List<Account>)accountdao.viewAllOfAccount();
                    request.setAttribute("aList", accList);
//                    requestDispatcher = request.getRequestDispatcher("/app/account/AddAccount.jsp");
//                    requestDispatcher.forward(request, response);
                    requestDispatcher = request.getRequestDispatcher("/app/account/AddAccount.jsp");
                    requestDispatcher.forward(request, response);
                    
                    break;
                }
                
                case "loadofSubType":{// this is text
                    
                    String tId = request.getParameter("typeId").trim();
                    
                    List<Subtype> STlist = accountdao.loadOfSubType(Integer.parseInt(tId));
                    JSONObject jOB = new JSONObject();
                    JSONArray jar1 = new JSONArray();
                    JSONObject job1 = null;
                    
                    for(Subtype s : STlist){
                        job1=new JSONObject();
                        job1.put("name", s.getName());
                        job1.put("id", s.getId());
                        
                        jar1.add(job1);
                    }
                    
                    jOB.put("subType", jar1);
                    response.getWriter().write(jOB.toString());
                    
                    break;
                }
                
                case "saveAccount":{// add new account
                    String result = "";
                    
                    String aName = request.getParameter("accountName").trim();
                    String balance = request.getParameter("balance").trim();
                    String subId = request.getParameter("subType").trim();
                    System.out.println(aName);
                    System.out.println(balance);
                    System.out.println(subId);
                    Subtype st = new Subtype(Integer.parseInt(subId));
                    
                    
                    Account ac = new Account();
                    ac.setName(aName);
                    ac.setBalance(Double.parseDouble(balance));
                    ac.setSubtypeId(st);
                    ac.setCompanyId(company);
                    ac.setIsValid(true);
                     
                    
                     
                    result = accountdao.saveAccount(ac);
                        if (result.equals(VertecConstants.SUCCESS)) {
                        out.write(VertecConstants.SUCCESS);
                        } else {
                            out.write(VertecConstants.ERROR);
                        }
                    
                    break;
                }
                case "viewAccount":{ // balapan wada 
                    List<Account> accList = (List<Account>)accountdao.viewAllOfAccount();
                    request.setAttribute("aList", accList);
                    requestDispatcher = request.getRequestDispatcher("/app/account/AddAccount.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "ViewToUpdate":{
                    String aId = request.getParameter("accountId").trim();
                    System.out.println(aId);
                    Account acc = (Account)accountdao.viewToUpdate(Integer.parseInt(aId));
                    request.setAttribute("acc", acc);
                    List<Type> tList = (List<Type>)accountdao.loadofType();
                    request.setAttribute("type", tList);
                    requestDispatcher = request.getRequestDispatcher("/app/account/ViewAccount.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                    
                }
                
                case "loadToUpdate":{
                    String Id = request.getParameter("accountId").trim();
                    Account ac = accountdao.viewToUpdate(Integer.parseInt(Id));
                    JSONObject jOB = new JSONObject();
                        jOB.put("id", ac.getId());
                        jOB.put("name", ac.getName());
                        jOB.put("subType", ac.getSubtypeId().getName());
                        jOB.put("Type", ac.getSubtypeId().getTypeId().getName());
                    response.getWriter().write(jOB.toString());
                    break;
                }
                
                case "UpdateAccont":{
                    String id = request.getParameter("accountId").trim();
                    String sId = request.getParameter("subType").trim();
                    String Name = request.getParameter("accountName").trim();
                    String Bal = request.getParameter("balance").trim();
                    
                    
                    String result = "";
                    
                    Account ac = new Account();
                    ac.setId(Integer.parseInt(id));
                    ac.setName(Name);
                    ac.setBalance(Double.parseDouble(Bal));
                    ac.setSubtypeId(new Subtype(Integer.parseInt(sId)));
                    
                    result = accountdao.updateAccount(ac);
                    if (result.equals(VertecConstants.UPDATED)) {
                        request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Updated");
                    response.sendRedirect("Account?action=loadOfType");
                    }else {
                        request.getSession().removeAttribute("Error_Message");

                        request.getSession().setAttribute("Error_Message", "Not Updated,Please Try again");
                        response.sendRedirect("Account?action=loadOfType");
                    }
                    
                    break;
                }
                 
                case "DeleteAccont":{
                    String id = request.getParameter("accountId").trim();
                    
                    System.out.println(id+"aa");
                    
                    
                    String result = "";
                    
                    Account ac = new Account();
                    ac.setId(Integer.parseInt(id));
                    
                    
                    result = accountdao.DeleteAccount(ac);
                    if (result.equals(VertecConstants.UPDATED)) {
                        request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Deleted");
                    response.sendRedirect("Account?action=loadOfType");
                    }else {
                        request.getSession().removeAttribute("Error_Message");

                        request.getSession().setAttribute("Error_Message", "Not Updated,Please Try again");
                        response.sendRedirect("Account?action=loadOfType");
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
