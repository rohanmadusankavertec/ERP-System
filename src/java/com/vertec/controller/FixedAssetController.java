/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.FixedAssetDAOImpl;
import com.vertec.daoimpl.SaveDAOImpl;
import com.vertec.hibe.model.Account;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Depreciation;
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
@WebServlet(name = "FixedAssetController", urlPatterns = {"/FixedAssetController"})
public class FixedAssetController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final FixedAssetDAOImpl fixedassetDAOImpl = new FixedAssetDAOImpl();
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
                case "ViewPurchase": {
                    List<Account> acList = fixedassetDAOImpl.loadAccountsToPurchase(company);
                    request.setAttribute("account", acList);
                    List<Account> acList1 = fixedassetDAOImpl.loadCreditorAccounts(company);
                    request.setAttribute("creditor", acList1);
                    List<Account> acList2 = fixedassetDAOImpl.loadDebtorAccounts(company);
                    request.setAttribute("debtor", acList2);
                    List<Account> dep = fixedassetDAOImpl.loadDepreciationAccounts(company);
                    request.setAttribute("dep", dep);
                    requestDispatcher = request.getRequestDispatcher("app/transaction/FixedAsset.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "getAccountBysubtype": {
                    System.out.println("Calling");
                    String stype = request.getParameter("subType").trim();
                    if (stype.equals("Loan")) {
                        stype = "Creditors";
                    }

                    System.out.println(stype);
                    List<Account> prList = fixedassetDAOImpl.loadAccountsBySubtype(company, stype);

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
                case "SavePurchase": {

                    String acc = request.getParameter("acc").trim();
                    String price = request.getParameter("price").trim();
                    String desc = request.getParameter("desc").trim();
                    String pacc = request.getParameter("pacc").trim();
                    String tdisc = request.getParameter("tdis").trim();
                    String pamount = request.getParameter("pamount").trim();
                    String balance = request.getParameter("balance").trim();
                    String cdtype = request.getParameter("cdtype").trim();
                    String cdacc = request.getParameter("cdacc").trim();
                    String rate = request.getParameter("rate").trim();
                    String term = request.getParameter("term").trim();
                    String proacc = request.getParameter("proacc").trim();
                    String depacc = request.getParameter("depacc").trim();

                    System.out.println("_____________________________________");
                    System.out.println("Price" + price);
                    System.out.println("paid amount" + pamount);
                    System.out.println("balance" + balance);
                    System.out.println("discount" + tdisc);
                    System.out.println("_____________________________________");

                    
                    
                    Depreciation d = new Depreciation();
                    d.setAmount(Double.parseDouble(price));
                    d.setRate(Double.parseDouble(rate));
                    d.setTerm(Integer.parseInt(term));
                    d.setDate(new Date());
                    d.setCredit(new Account(Integer.parseInt(proacc)));
                    d.setDebit(new Account(Integer.parseInt(depacc)));
                    d.setIsClosed(false);
                    String Result1 = saveDAOImpl.saveObject(d);
                    
                    
                    
                    
                    
                    Transaction tr = new Transaction();
                    tr.setDebit(new Account(Integer.parseInt(acc)));
                    tr.setCredit(new Account(Integer.parseInt(pacc)));
                    tr.setCompanyId(company);
                    tr.setDate(new Date());
                    tr.setDescription(desc);
                    tr.setDiscount(Double.parseDouble(tdisc));
                    tr.setPaidAmount(Double.parseDouble(pamount));
                    tr.setPrice(Double.parseDouble(price));
                    tr.setDepreciationId(d);
                    String Result2 = saveDAOImpl.saveObject(tr);

                    double cd = 0;
                    try {
                        cd = Double.parseDouble(balance);
                    } catch (Exception e) {
                        cd = 0;
                    }

                    if (cdtype.equals("creditor")) {
                        Transaction tr1 = new Transaction();
                        tr1.setDebit(new Account(Integer.parseInt(cdacc)));
                        tr1.setCredit(new Account(Integer.parseInt(acc)));
                        tr1.setCompanyId(company);
                        tr1.setDate(new Date());
                        tr1.setDescription(desc);
                        tr1.setDiscount(0.0);
                        tr1.setPaidAmount(cd);
                        tr1.setPrice(cd);
                        String Result3 = saveDAOImpl.saveObject(tr1);
                    } else if (cdtype.equals("debtor")) {
                        Transaction tr2 = new Transaction();
                        tr2.setDebit(new Account(Integer.parseInt(acc)));
                        tr2.setCredit(new Account(Integer.parseInt(cdacc)));
                        tr2.setCompanyId(company);
                        tr2.setDate(new Date());
                        tr2.setDescription(desc);
                        tr2.setDiscount(0.0);
                        tr2.setPaidAmount(cd);
                        tr2.setPrice(cd);
                        String Result4 = saveDAOImpl.saveObject(tr2);
                    }

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
