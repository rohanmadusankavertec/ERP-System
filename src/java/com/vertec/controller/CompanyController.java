/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.CompanyDAOImpl;
import com.vertec.hibe.model.Branch;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.CompanyGroup;
import com.vertec.hibe.model.SysUser;
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

/**
 *
 * @author Java-Dev-Ruchira
 */
@WebServlet(name = "CompanyController", urlPatterns = {"/CompanyController"})
public class CompanyController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final CompanyDAOImpl companydao = new CompanyDAOImpl();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession httpSession = request.getSession();
            SysUser user1 = (SysUser) httpSession.getAttribute("user");
            Company company = (Company) httpSession.getAttribute("company");
            RequestDispatcher requestDispatcher;
//            boolean isValidated = true;
            switch (action) {
                case "loadCompanyGroup":{
                    int id =user1.getCompanyId().getCompanyGroupId().getId();
                    CompanyGroup cg = companydao.viewCompanyGroup(id);
                    request.setAttribute("cg", cg);
                    requestDispatcher = request.getRequestDispatcher("app/company/updateCompany.jsp");
                    requestDispatcher.forward(request, response);
                    
                    break;
                    
                }
                case "updateCompany":{
                    String name = request.getParameter("Name").trim();
                    String id = request.getParameter("cuId").trim();
                    
                    String result = companydao.updateCompany(Integer.parseInt(id), name);
                    if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Updated");
                    response.sendRedirect("dashboard.jsp");
                    } else {
                        request.getSession().removeAttribute("Error_Message");

                        request.getSession().setAttribute("Error_Message", "Not Updated,Please Try again");
                        response.sendRedirect("Company?action=loadCompanyGroup");
                    }
                    break;
                    
                    
                }
                case "loadCompanypage":{
                    
                    requestDispatcher = request.getRequestDispatcher("app/company/registerCompany.jsp");
                    requestDispatcher.forward(request, response);
                    
                    break;
                    
                }
                case "saveCompany":{
                    String name = request.getParameter("name").trim();
                    String add = request.getParameter("address").trim();
                    String contact = request.getParameter("contact").trim();
                    String email = request.getParameter("email").trim();
                    
                    Company com = new Company();
                    com.setCompanyName(name);
                    com.setAddress(add);
                    com.setContactNo(contact);
                    com.setEmail(email);
                    com.setCompanyGroupId(company.getCompanyGroupId());
                    
                    String result = companydao.saveCompany(com);
                    if(result.equals(VertecConstants.SUCCESS)){
                        out.write(VertecConstants.SUCCESS);
                        
                    }else{
                        
                        out.write(VertecConstants.ERROR);
                    }
                    break;
                    
                }
                case "updateCompany1":{
                    List<Company> cList = companydao.viewAllCompany();
                    request.setAttribute("cList", cList);
                    
                    requestDispatcher = request.getRequestDispatcher("app/company/companyDetails.jsp");
                    requestDispatcher.forward(request, response);
                    
                    break;
                }
                case "updateCompany2":{
                    System.out.println("aaaaaaaaa");
                    String com = request.getParameter("cId").trim();
                    System.out.println("aaaaaaaaa"+com);
                    Company c = companydao.viewCompanyByid(Integer.parseInt(com));
                    request.setAttribute("c", c);
                    
                    requestDispatcher = request.getRequestDispatcher("app/company/viewCompany.jsp");
                    requestDispatcher.forward(request, response);
                    
                    break;
                }
                case "updateCompanyDetails":{
                    String name = request.getParameter("Name").trim();
                    String address = request.getParameter("address").trim();
                    String contact = request.getParameter("hotline").trim();
                    String email = request.getParameter("Email").trim();
                    String id = request.getParameter("cId").trim();
                    
                    Company c = new Company();
                    c.setCompanyName(name);
                    c.setAddress(address);
                    c.setContactNo(contact);
                    c.setEmail(email);
                    c.setId(Integer.parseInt(id));
                    c.setCompanyGroupId(new CompanyGroup(Integer.parseInt(id)));
                    
                    String result = companydao.updateCompanyDetails(c);
                    
                    if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Updated");
                    response.sendRedirect("Company?action=updateCompany1");
                    } else {
                        request.getSession().removeAttribute("Error_Message");

                        request.getSession().setAttribute("Error_Message", "Not Updated,Please Try again");
                        response.sendRedirect("Company?action=updateCompany2");
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
