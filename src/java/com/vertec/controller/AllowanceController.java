/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.EmployeeDAOImpl;
import com.vertec.daoimpl.SalaryDAOImpl;
import com.vertec.hibe.model.AllowanceDeduction;
import com.vertec.hibe.model.Employee;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.Save;
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

/**
 *
 * @author vertec-r
 */
@WebServlet(name = "AllowanceController", urlPatterns = {"/AllowanceController"})
public class AllowanceController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final EmployeeDAOImpl EmployeeDAOImpl = new EmployeeDAOImpl();
    private final SalaryDAOImpl SalaryDAOImpl = new SalaryDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession httpSession = request.getSession();
            SysUser user1 = (SysUser) httpSession.getAttribute("user");
            RequestDispatcher requestDispatcher;
            switch (action) {
                case "ViewaddAllowance": { // load add allowance page
                    List<Employee> cuList = EmployeeDAOImpl.getEmployees();
                    request.setAttribute("employee", cuList);
                    requestDispatcher = request.getRequestDispatcher("app/salary/addAllowance.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "ViewAllowance": { // load all allowances
                    List<AllowanceDeduction> cuList = SalaryDAOImpl.getAllowanceDeduction();
                    request.setAttribute("allowance", cuList);
                    requestDispatcher = request.getRequestDispatcher("app/salary/ViewAllowance.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "addallowance": { // add new allowance
                    String emp = request.getParameter("employee").trim();
                    String type = request.getParameter("type").trim();
                    String name = request.getParameter("name").trim();
                    String amount = request.getParameter("amount").trim();

                    boolean isAllo = false;
                    if (type.equals("1")) {
                        isAllo = true;
                    }
                    double value=0.0;
                    if(!amount.equals("")){
                        value=Double.parseDouble(amount);
                    }
                    AllowanceDeduction ad = new AllowanceDeduction();
                    ad.setAdtypeId(null);
                    ad.setDate(new Date());
                    ad.setIsAllowance(isAllo);
                    Employee employee = new Employee(Integer.parseInt(emp));
                    ad.setEmployeeId(employee);
                    ad.setIsAmount(true);
                    ad.setIsFixed(true);
                    ad.setName(name);
                    ad.setIsValid(true);
                    ad.setValue(value);

                    String result = Save.Save(ad);
                    if (result.equals(VertecConstants.SUCCESS)) {
                        request.getSession().removeAttribute("Success_Message");
                        request.getSession().setAttribute("Success_Message", "Successfully Added");
                        response.sendRedirect("Allowance?action=ViewaddAllowance");
                    } else {
                        request.getSession().removeAttribute("Error_Message");
                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                        response.sendRedirect("Allowance?action=ViewaddAllowance");
                    }
                    break;
                }
                case "toupdateAllowance": { // load the allowance details to update
                    String aid = request.getParameter("aid").trim();
                    AllowanceDeduction al = SalaryDAOImpl.getAllowanceDeductionbyId(Integer.parseInt(aid));
                    request.setAttribute("allowance", al);
                    requestDispatcher = request.getRequestDispatcher("app/salary/UpdateAllowance.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "deleteAllowance": { // remove the allowance
                    String id = request.getParameter("id");
                    String result = SalaryDAOImpl.deleteAllowance(Integer.parseInt(id));
                    response.getWriter().write(result);
                    break;
                }
                case "updateallowance": { // update allowance
                    System.out.println("Calling");
                    String aid = request.getParameter("aid").trim();
                    String name = request.getParameter("name").trim();
                    String value = request.getParameter("amount").trim();

                    System.out.println(aid);
                    int id=0;
                    if(aid!=null){
                        id=Integer.parseInt(aid);
                    }
                    
                    double val=0;
                    
                    if(!value.equals("")){
                    val=Double.valueOf(value);
                    }
                    
                    
                    AllowanceDeduction ad = new AllowanceDeduction();
                    ad.setId(id);
                    ad.setName(name);
                    ad.setValue(val);
                    String result=SalaryDAOImpl.updateAllowance(ad);
                    
                    if (result.equals(VertecConstants.SUCCESS)) {
                        request.getSession().removeAttribute("Success_Message");
                        request.getSession().setAttribute("Success_Message", "Successfully Added");
                        response.sendRedirect("Allowance?action=ViewAllowance");
                    } else {
                        request.getSession().removeAttribute("Error_Message");
                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                        response.sendRedirect("Allowance?action=ViewAllowance");
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
