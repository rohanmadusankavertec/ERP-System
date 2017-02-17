/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.PrivilegeDAOImpl;
import com.vertec.daoimpl.SaveDAOImpl;
import com.vertec.hibe.model.Branch;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.CompanyGroup;
import com.vertec.hibe.model.CurrencyType;
import com.vertec.hibe.model.SysUser;
import com.vertec.hibe.model.UserGroup;
import com.vertec.util.MD5Hashing;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vertec-r
 */
@WebServlet(name = "SignupController", urlPatterns = {"/SignupController"})
public class SignupController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final SaveDAOImpl saveDAOImpl = new SaveDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //this controller helps to sign up a company
            String company = request.getParameter("company");
            String address = request.getParameter("address");
            String contact = request.getParameter("contact");
            String email = request.getParameter("email");
            
            String first = request.getParameter("first");
            String last = request.getParameter("last");
            String uemail = request.getParameter("uemail");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            CompanyGroup g = new CompanyGroup();
            g.setName(company);
            String result3 = saveDAOImpl.saveObject(g);

            Company c = new Company();
            c.setAddress(address);
            c.setCompanyName(company);
            c.setContactNo(contact);
            c.setEmail(email);
            c.setCompanyGroupId(g);
            c.setIsValid(true);
            c.setCurrencyTypeId(new CurrencyType(1));
            String result1=saveDAOImpl.saveObject(c);
            
            Branch b = new Branch();
            b.setAddress(address);
            b.setBranchName(company);
            b.setContactNo(contact);
            b.setCompanyId(c);
            String result4 = saveDAOImpl.saveObject(b);
            
            SysUser su = new SysUser();
            su.setAddedBy(1);
            su.setFirstName(first);
            su.setLastName(last);
            su.setEmailAdd(uemail);
            su.setUsername(username);
            su.setUserGroupId(new UserGroup(1));
            su.setAddedDate(new Date());
            su.setLastUpdatedDate(new Date());
            su.setDob(null);
            su.setIsActive(true);
            su.setNicNo(null);
            su.setCompanyId(c);
            su.setBranchBranchId(b);
            String pword="";
            try {
                pword=new MD5Hashing().encryptByteToHex(password);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
            }
            su.setPassword(pword);
            
            
            
            
            
            
            String result2=saveDAOImpl.saveObject(su);
            
            if(result1.equals(VertecConstants.SUCCESS)&&result2.equals(VertecConstants.SUCCESS)){
            out.write(VertecConstants.SUCCESS);
            }else{
            out.write(VertecConstants.ERROR);
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
