/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.DrawingDAOImpl;
import com.vertec.daoimpl.FinaceTransferDAOImpl;
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
@WebServlet(name = "FinanceTransferController", urlPatterns = {"/FinanceTransferController"})
public class FinanceTransferController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private  final FinaceTransferDAOImpl financetransferdao = new FinaceTransferDAOImpl();
    private final DrawingDAOImpl drawingdao = new DrawingDAOImpl();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String action = request.getParameter("action");
            HttpSession httpsession = request.getSession();
            
            SysUser user1 = (SysUser) httpsession.getAttribute("user");
            Company company = (Company) httpsession.getAttribute("company");
            RequestDispatcher requestDispatcher;
            
            switch(action){
                case "loadFinance":{
                    
                    requestDispatcher = request.getRequestDispatcher("/app/transaction/FinanceTransfer.jsp");
                    requestDispatcher.forward(request, response);
                    
                    break;
                }
                case "loadAccountByPayName":{
                    String sName = request.getParameter("accountType").trim();
                    System.out.println(sName);
                    System.out.println(company.getCompanyName());
                    List<Account> acList = financetransferdao.loadAccountByPayName(sName, company);
                    JSONObject JOB = new JSONObject();
                    JSONArray jar =new JSONArray();
                    JSONObject job =null;
                            for(Account a: acList){
                                job = new JSONObject();
                                job.put("id", a.getId());
                                job.put("name", a.getName());
                                
                                jar.add(job);
                            }
                            
                            JOB.put("account", jar);
                    
                    response.getWriter().write(JOB.toString());
                    break;
                }
                
                case "saveFinance":{
                    String dAcc = request.getParameter("credit").trim();//to account
                    String amount = request.getParameter("amount").trim();
                    String descrip = request.getParameter("descrip").trim();
                    String cAcc = request.getParameter("account").trim(); // from account
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
                    
                    String  result = drawingdao.saveDrawing(t);
                    out.write(result);
                    
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
