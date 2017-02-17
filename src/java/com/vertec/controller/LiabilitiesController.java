/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.LiabilitiesDAOImpl;
import com.vertec.daoimpl.PurchaseDAOImpl;
import com.vertec.daoimpl.SaveDAOImpl;
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
 * @author vertec-r
 */
@WebServlet(name = "LiabilitiesController", urlPatterns = {"/LiabilitiesController"})
public class LiabilitiesController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final LiabilitiesDAOImpl liabilityDAOImpl = new LiabilitiesDAOImpl();
    private final SaveDAOImpl saveDAOImpl = new SaveDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession httpSession = request.getSession();
            SysUser user1 = (SysUser) httpSession.getAttribute("user");
            Company company = (Company) httpSession.getAttribute("company");
            RequestDispatcher requestDispatcher;
            switch (action) {
                case "ViewLiability": {
                    List<Account> acList = liabilityDAOImpl.loadAccountsToCreditor(company);
                    request.setAttribute("account", acList);
                    requestDispatcher = request.getRequestDispatcher("app/transaction/Liabilities.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "getAccountBysubtype": {
                    System.out.println("Calling");
                    String stype = request.getParameter("subType").trim();
                    
                    System.out.println(stype);
                    List<Account> prList = liabilityDAOImpl.loadAccountsBySubtype(company, stype);

                    JSONObject jOB = new JSONObject();
                    JSONArray jar1 = new JSONArray();
                    JSONObject job1 = null;
                    for (Account p : prList) {
                        System.out.println(p.getName());
                        job1 = new JSONObject();
                        job1.put("id", p.getId());
                        job1.put("type", p.getName());
                        jar1.add(job1);
                    }
                    jOB.put("jArr1", jar1);

                    response.getWriter().write(jOB.toString());

                    break;
                }
                case "SaveLiability": {

                    String acc = request.getParameter("acc").trim();
                    String pacc = request.getParameter("pacc").trim();
                    String itype = request.getParameter("itype").trim();
                    String iterm = request.getParameter("iterm").trim();
                    String interest = request.getParameter("interest").trim();
                    String terms = request.getParameter("terms").trim();
                    String amount = request.getParameter("amount").trim();
                    String tpayable = request.getParameter("tpayable").trim();
                    String mpayable = request.getParameter("mpayable").trim();
                    String desc = request.getParameter("desc").trim();

                    
                    
                    Loan loan=new Loan();
                    loan.setAmount(Double.parseDouble(amount));
                    loan.setDescription(desc);
                    loan.setInstallment(Double.parseDouble(mpayable));
                    loan.setInterest(Double.parseDouble(interest));
                    loan.setIsLiability(true);
                    loan.setPaid(0.0);
                    loan.setTerms(Integer.parseInt(terms));
                    loan.setTotalPayable(Double.parseDouble(tpayable));
                    loan.setType(Integer.parseInt(itype));
                    loan.setDate(new Date());
                    loan.setAccountId(new Account(Integer.parseInt(pacc)));
                    String Result1 = saveDAOImpl.saveObject(loan);
                    
                    
                    
                    Transaction tr = new Transaction();
                    tr.setDebit(new Account(Integer.parseInt(pacc)));
                    tr.setCredit(new Account(Integer.parseInt(acc)));
                    tr.setCompanyId(company);
                    tr.setDate(new Date());
                    tr.setDescription(desc);
                    tr.setDiscount(0.0);
                    tr.setPaidAmount(Double.parseDouble(amount));
                    tr.setPrice(Double.parseDouble(amount));
                    tr.setLoanId(loan);
                    String Result2 = saveDAOImpl.saveObject(tr);

                    
                    
                    

                    if (Result1.equals(VertecConstants.SUCCESS) && Result2.equals(VertecConstants.SUCCESS)) {
                        out.write(VertecConstants.SUCCESS);
                    } else {
                        out.write(VertecConstants.ERROR);
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
