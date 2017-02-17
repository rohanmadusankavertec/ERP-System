/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.LoanPayDAOImpl;
import com.vertec.hibe.model.Account;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Loan;
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
@WebServlet(name = "LoanPayController", urlPatterns = {"/LoanPayController"})
public class LoanPayController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private  final LoanPayDAOImpl loanpaydao = new LoanPayDAOImpl();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String action = request.getParameter("action");
            HttpSession httpSession = request.getSession();
            SysUser user1 = (SysUser) httpSession.getAttribute("user");
            Company company = (Company) httpSession.getAttribute("company");
            RequestDispatcher requestDispatcher;
            
            switch(action){
                case "loadLoanPayPage":{
                    
                    requestDispatcher = request.getRequestDispatcher("/app/transaction/LoanPay.jsp");
                    requestDispatcher.forward(request, response);
                    
                    break;
                }
                
                case "loadLoanAccount":{
                    String sub = request.getParameter("LoanType").trim();
                    List<Account> aList = loanpaydao.getLoanAccount(company, sub);
                    
                    JSONObject JOB = new JSONObject();
                    JSONArray jar = new JSONArray();
                    JSONObject job = null;
                    
                    for(Account a: aList){
                        job= new JSONObject();
                        
                        job.put("id", a.getId());
                        job.put("name", a.getName());
                        
                        jar.add(job);
                    }
                    JOB.put("account", jar);
                    
                    response.getWriter().write(JOB.toString());
                            
                    break;
                }
                case "loadPayType":{
                    String payType = request.getParameter("payType").trim();
                    List<Account> aList = loanpaydao.getLoanAccount(company, payType);
                    
                    JSONObject JOB = new JSONObject();
                    JSONArray jar = new JSONArray();
                    JSONObject job = null;
                    
                    for(Account a: aList){
                        job= new JSONObject();
                        
                        job.put("id", a.getId());
                        job.put("name", a.getName());
                        
                        jar.add(job);
                    }
                    JOB.put("account", jar);
                    
                    response.getWriter().write(JOB.toString());
                            
                    break;
                    
                }
                case "loadLoan":{
                    
                    String payType = request.getParameter("Loan").trim();
                    System.out.println(payType);
                    List<Loan> aList = loanpaydao.loadLoanByAccount(Integer.parseInt(payType));
                    
                    JSONObject JOB = new JSONObject();
                    JSONArray jar = new JSONArray();
                    JSONObject job = null;
                    
                    for(Loan a: aList){
                        job= new JSONObject();
                        
                        job.put("id", a.getId());
                        job.put("amount", (a.getTotalPayable()- a.getPaid()));
                        job.put("incriment", a.getInstallment());
                        job.put("descrip", a.getDescription());
                        job.put("paid", a.getPaid());
                        
                        
                        jar.add(job);
                    }
                    JOB.put("loan", jar);
                    
                    response.getWriter().write(JOB.toString());
                            
                    break;
                    
                }
                
                case "saveLoanPayment":{
                    String ty = request.getParameter("loanType").trim();
                    System.out.println("/////////////////////");
                    
                    String Acc1 = request.getParameter("account").trim();
                    String amount = request.getParameter("amount").trim();
                    String descrip = request.getParameter("descrip").trim();
                    String Acc2 = request.getParameter("acc").trim();
                    String lId = request.getParameter("loanId").trim();
                    
//                    System.out.println(Acc1);
//                    System.out.println(amount);
//                    System.out.println(descrip);
//                    System.out.println(Acc2);
                    String result="";
                    String result1="";
                    double aa = loanpaydao.getPaidAmount(Integer.parseInt(lId));
                    
                    
                    double paidA = loanpaydao.getPaidAmount(Integer.parseInt(lId))+Double.parseDouble(amount);
                    
                    
                    
                    
                    
                    if(ty.equals("Creditors")){
                        
                        String cAcc = Acc2;
                        Transaction t = new Transaction();
                        t.setCredit(new Account(Integer.parseInt(cAcc)));
//                    t.setAmount(Double.parseDouble(amount));
                        t.setPaidAmount(Double.parseDouble(amount));
                        t.setPrice(Double.parseDouble(amount));
                        t.setDiscount(0.0);
                        t.setDescription(descrip);
                        t.setDebit(new Account(Integer.parseInt(Acc1)));
                        t.setDate(new Date());
                        t.setCompanyId(company);
                        t.setLoanId(new Loan(Integer.parseInt(lId)));
                    
                        result = loanpaydao.saveLoanPayment(t);
                        result1 = loanpaydao.updateLoanPaid(Integer.parseInt(lId),paidA);
                        
                    }else{
                        
                        String dAcc = Acc2;
                        Transaction t1 = new Transaction();
                        t1.setCredit(new Account(Integer.parseInt(Acc1)));
    //                    t.setAmount(Double.parseDouble(amount));
                        t1.setPaidAmount(Double.parseDouble(amount));
                        t1.setPrice(Double.parseDouble(amount));
                        t1.setDiscount(0.0);
                        t1.setDescription(descrip);
                        t1.setDebit(new Account(Integer.parseInt(dAcc)));
                        t1.setDate(new Date());
                        t1.setCompanyId(company);
                        t1.setLoanId(new Loan(Integer.parseInt(lId)));

                        result = loanpaydao.saveLoanPayment(t1);
                        result1 = loanpaydao.updateLoanPaid(Integer.parseInt(lId),paidA);
                    }
                    
                    if (result.equals(VertecConstants.SUCCESS) && result1.equals(VertecConstants.UPDATED)) {
                    out.write(VertecConstants.SUCCESS);
                    } else {
                        out.write(VertecConstants.ERROR);
                    }
                    
                    break;
                    
                }
                
                case"loadLoanById":{
                    String lId = request.getParameter("loanId").trim();
                    System.out.println("........."+lId);
                    Loan a = loanpaydao.getloan(Integer.parseInt(lId));
                    System.out.println("aa"+a.getAmount());
                    JSONObject JOB = new JSONObject();
                    JSONArray jar = new JSONArray();
                    JSONObject job = null;
                        job= new JSONObject();
                        JOB.put("id", a.getId());
                        JOB.put("tot", a.getTotalPayable());
                        JOB.put("amount", a.getAmount());
                        JOB.put("paid", a.getPaid());
                        System.out.println(JOB.toString());
                    response.getWriter().write(JOB.toString());
                    
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
