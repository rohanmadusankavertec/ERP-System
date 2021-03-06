/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.TaxDAOImpl;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Invoice;
import com.vertec.hibe.model.SysUser;
import com.vertec.hibe.model.Tax;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

/**
 *
 * @author Ruchira
 */
@WebServlet(name = "TaxController", urlPatterns = {"/TaxController"})
public class TaxController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final TaxDAOImpl taxDAOImpl = new TaxDAOImpl();

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
                case "loadTaxPage": {
                    List<Tax> taxList = taxDAOImpl.getallTax(company);
                    request.setAttribute("taxList", taxList);
                    requestDispatcher = request.getRequestDispatcher("app/tax/addTax.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "Register": {
                    String name = request.getParameter("name");
                    String tax = request.getParameter("Percentage");
                    Tax t = new Tax();
                    t.setName(name);
                    t.setPercentage(Double.parseDouble(tax));
                    t.setCompanyId(company);
                    String result = taxDAOImpl.saveTax(t);
                    if (result.equals(VertecConstants.SUCCESS)) {
                        request.getSession().removeAttribute("Success_Message");
                        request.getSession().setAttribute("Success_Message", "Successfully Added");
                        response.sendRedirect("Tax?action=loadTaxPage");
                    } else {
                        request.getSession().removeAttribute("Error_Message");
                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                        response.sendRedirect("Tax?action=loadTaxPage");
                    }
                    break;
                }
                case "loadUpdateTax": {
                    String id = request.getParameter("taxId").trim();
                    Tax tax = taxDAOImpl.getTaxDetail(company, Integer.parseInt(id));
                    request.setAttribute("tax", tax);
                    requestDispatcher = request.getRequestDispatcher("app/tax/viewTaxDetails.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "updateTax": {
                    String id = request.getParameter("taxid");
                    String name = request.getParameter("name");
                    String per = request.getParameter("Percentage");
                    System.out.println(".." + id);
                    System.out.println(".." + name);
                    System.out.println(".." + per);

                    Tax tax = new Tax();
                    tax.setId(Integer.parseInt(id));
                    tax.setName(name);
                    tax.setPercentage(Double.parseDouble(per));
                    String result = taxDAOImpl.updateTax(tax);

                    if (result.equals(VertecConstants.UPDATED)) {

                        request.getSession().removeAttribute("Success_Message");

                        request.getSession().setAttribute("Success_Message", "Successfully Updateded");
                        response.sendRedirect("Tax?action=loadTaxPage");
                    } else {
                        request.getSession().removeAttribute("Error_Message");

                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                        response.sendRedirect("Tax?action=loadTaxPage");
                    }

                    break;

                }
                case "SearchTaxDetails": {
                    requestDispatcher = request.getRequestDispatcher("app/report/SearchTaxdetails.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "TaxDetailsReport": {
                    String fromDate = request.getParameter("fromDate");
                    String toDate = request.getParameter("toDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date from = null;
                    Date to = null;
                    try {
                        from = sdf.parse(fromDate);
                        to = sdf.parse(toDate);
                    } catch (ParseException ex) {
                        Logger.getLogger(TaxController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    List<Invoice> i = taxDAOImpl.invoiceForDateRange(from, to,company);
                    request.setAttribute("invoices", i);
                    requestDispatcher = request.getRequestDispatcher("app/report/TaxDetailsReport.jsp");
                    requestDispatcher.forward(request, response);
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
