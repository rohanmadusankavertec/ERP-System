/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.InvoiceDAOImpl;
import com.vertec.daoimpl.PoDAOImpl;
import com.vertec.hibe.model.Invoice;
import com.vertec.hibe.model.InvoiceItem;
import com.vertec.hibe.model.SysUser;
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

/**
 *
 * @author vertec-r
 */
@WebServlet(name = "DispatchController", urlPatterns = {"/DispatchController"})
public class DispatchController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final PoDAOImpl PoDAOImpl = new PoDAOImpl();
    private final InvoiceDAOImpl InvoiceDAOImpl = new InvoiceDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession httpSession = request.getSession();
            RequestDispatcher requestDispatcher;
            SysUser user1 = (SysUser) httpSession.getAttribute("user");
            switch (action) {
                
                case "CreateDispatch": {
                    List<Invoice> i =  InvoiceDAOImpl.getPendingInvoice();
                    request.setAttribute("invoice", i);
                    requestDispatcher = request.getRequestDispatcher("app/dispatch/CreateDispatch.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "toDispatch": {
                    System.out.println("Calling to dispatch");
                    String id = request.getParameter("id");
                    Invoice i =  InvoiceDAOImpl.getInvoice(Integer.parseInt(id));
                    request.setAttribute("invoice", i);
                    List<InvoiceItem> ii =  InvoiceDAOImpl.getInvoiceItem(Integer.parseInt(id));
                    request.setAttribute("invoiceitem", ii);
                    requestDispatcher = request.getRequestDispatcher("app/dispatch/DispatchNote.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "ApproveInvoice": {
                    String id = request.getParameter("id");
                    String result=InvoiceDAOImpl.updateInvoicePendingToSuccess(Integer.parseInt(id));
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
