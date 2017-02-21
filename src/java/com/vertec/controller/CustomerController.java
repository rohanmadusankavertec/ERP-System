/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.CustomerDAOImpl;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Customer;
import com.vertec.hibe.model.CustomerGroup;
import com.vertec.hibe.model.CustomerHasGroup;
import com.vertec.hibe.model.CustomerRating;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sandun
 */
public class CustomerController extends HttpServlet {

    /**
     * CustomerController url=Customer This Controller all the action calls get
     * from app/customer/(customerDetails.jsp,registerCustomer.jsp,viewCustomer)
     * and all functions call get from app/js/customer.js
     */
    private final CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession httpSession = request.getSession();
        SysUser user1 = (SysUser) httpSession.getAttribute("user");
        Company company = (Company) httpSession.getAttribute("company");
        RequestDispatcher requestDispatcher;
        boolean isValidated = true;

        switch (action) {
            
            case "Register": {// add new customer
                String customerName = request.getParameter("customerName").trim();
                String address = request.getParameter("address").trim();
                String hotline = request.getParameter("hotline").trim();
                String officeNo = request.getParameter("officeNo").trim();
                String faxNo = request.getParameter("faxNo").trim();
                String contactPerson = request.getParameter("contactPerson").trim();
                String contactPersonNo = request.getParameter("contactPersonNo").trim();
                String contactPersonEmail = request.getParameter("contactPersonEmail").trim();
                String rating = request.getParameter("rating").trim();
                int rate = 0;
                if (rating != null) {
                    rate = Integer.parseInt(rating);
                }
                Date date = new Date();
                CustomerRating cr = new CustomerRating(rate);
//                Customer customer = new Customer(customerName, address, hotline, officeNo, faxNo, contactPerson, contactPersonNo, contactPersonEmail, date, user1, cr, true);
                Customer c = new Customer();
                c.setAddress(address);
                c.setContactPerson(contactPerson);
                c.setContactPersonEmail(contactPersonEmail);
                c.setContactPersonNo(contactPersonNo);
                c.setCustomerName(customerName);
                c.setCustomerRatingId(cr);
                c.setFaxNo(faxNo);
                c.setHotline(hotline);
                c.setIsActive(true);
                c.setCompanyId(company);
                c.setOfficeNo(officeNo);
                c.setRegisterdDate(date);
                c.setRegisteredBy(user1);

                String result = customerDAOImpl.saveCustomer(c);

                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");
                    request.getSession().setAttribute("Success_Message", "Successfully Added");
                    response.sendRedirect("dashboard.jsp");
                } else {
                    request.getSession().removeAttribute("Error_Message");
                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    response.sendRedirect("dashboard.jsp");
                }

                break;
            }
            /**
             * URL to customerDetails.jsp
             */
            case "ViewCustomer": { // load all customer according to the company
                List<Customer> cuList = customerDAOImpl.getListofUsers(company);
                request.setAttribute("cuList", cuList);
                requestDispatcher = request.getRequestDispatcher("app/customer/customerDetails.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             *
             * This action is used to disable customer status
             */
            case "RemoveCustomer": { // remove the customer
                String userId = request.getParameter("customerId").trim();
                int customerId = Integer.parseInt(userId);
                String status = customerDAOImpl.removeCustomer(customerId);
                response.getWriter().write(status);
                break;
            }
            /**
             *
             * This is used to load customer details to update
             */
            case "UpdateCustomer": { // load customer details to update
                String userId = request.getParameter("customerId").trim();
                int customerId = Integer.parseInt(userId);
                Customer customer = customerDAOImpl.viewCustomer(customerId);
//                System.out.println("Customer Name : "+customer.getCustomerName());
                request.setAttribute("cu", customer);
                requestDispatcher = request.getRequestDispatcher("app/customer/viewCustomer.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * This used to update selected customer
             *
             */
            case "UpSel": { // update customer details
                String customerId = request.getParameter("customerId").trim();
                String customerName = request.getParameter("customerName").trim();
                String address = request.getParameter("address").trim();
                String hotline = request.getParameter("hotline").trim();
                String officeNo = request.getParameter("officeNo").trim();
                String faxNo = request.getParameter("faxNo").trim();
                String contactPerson = request.getParameter("contactPerson").trim();
                String contactPersonNo = request.getParameter("contactPersonNo").trim();
                String contactPersonEmail = request.getParameter("contactPersonEmail").trim();

                int cuId = 0;
                if (customerId != null) {
                    cuId = Integer.parseInt(customerId);
                }

//                Customer customer = new Customer(cuId, customerName, address, hotline, officeNo, faxNo, contactPerson, contactPersonNo, contactPersonEmail);
                Customer c = new Customer();
                c.setCustomerId(cuId);
                c.setAddress(address);
                c.setContactPerson(contactPerson);
                c.setContactPersonEmail(contactPersonEmail);
                c.setContactPersonNo(contactPersonNo);
                c.setCustomerName(customerName);
                c.setFaxNo(faxNo);
                c.setHotline(hotline);
                c.setOfficeNo(officeNo);

                String result = customerDAOImpl.updateCustomer(c);

                if (result.equals(VertecConstants.UPDATED)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Updated");
                    response.sendRedirect("Customer?action=ViewCustomer");
                } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Updated,Please Try again");
                    response.sendRedirect("Customer?action=ViewCustomer");
                }

                break;
            }
            
            case "CheckEmail": { // check the e mail
                String email = request.getParameter("email");
                String status = customerDAOImpl.checkEmail(email);
                response.getWriter().write(status);
                break;
            }
            /**
             *
             * This used to save customer quickly from create invoice page
             */
            case "SaveQuickCustomer": { // add new customer quickly
                System.out.println("aaaaaaaaaa");
                String cusName = request.getParameter("cusName");

                if (cusName != null) {
                    cusName = cusName.trim();
                }

                Customer c = new Customer();
                c.setCustomerName(cusName);
                c.setRegisteredBy(user1);
                c.setCustomerRatingId(new CustomerRating(3));
                c.setRegisterdDate(new Date());
                c.setCompanyId(company);
                
                c.setIsActive(isValidated);
                
                
                String result = customerDAOImpl.saveCustomer(c);

                if (result.equals(VertecConstants.SUCCESS)) {
                    response.sendRedirect("Invoice?action=ToCreateInvoice");

                } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    response.sendRedirect("Invoice?action=ToCreateInvoice");
                }

                break;
            }
            
//-----------------------------------------------------------------------------------------------------------------------------            
            
            case "LoadCustomerGroup": { // load customer group page
                requestDispatcher = request.getRequestDispatcher("app/customer/addCustomerGroup.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            
            case "LoadmanageGroup": { // load customer group details
                List<CustomerGroup> cList = customerDAOImpl.getListofCustomerGroup(company);
                request.setAttribute("cList", cList);
                requestDispatcher = request.getRequestDispatcher("app/customer/customerGroupDetails.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "viewGroupDetail": { // load  customer group details by customer id 
                String id = request.getParameter("groupId").trim();
//                System.out.println("......... "+id);
                CustomerGroup sgList = customerDAOImpl.viewCustomerGroup(Integer.parseInt(id));
                request.setAttribute("cg", sgList);
                requestDispatcher = request.getRequestDispatcher("app/customer/viewCustomerGroup.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            
            case "saveCustomerGroup": { // add new customer group
                String name = request.getParameter("customerGName").trim();
                CustomerGroup c = new CustomerGroup();
                c.setName(name);
                c.setCompanyId(company);
                String result = customerDAOImpl.saveCustomerGroup(c);
                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Added");
                    response.sendRedirect("Customer?action=LoadCustomerGroup");
                } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    response.sendRedirect("Customer?action=LoadCustomerGroup");
                }
                break;
            }
            
            case "updateCustomerGroup": { // update customer group
                String name = request.getParameter("customerGName").trim();
                String id = request.getParameter("groupId").trim();
                
                CustomerGroup cg = new CustomerGroup();
                
                cg.setId(Integer.parseInt(id));
                cg.setName(name);
                
                String result  = customerDAOImpl.updateCustomerGroup(cg);
                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Added");
                    response.sendRedirect("Customer?action=LoadmanageGroup");
                } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    response.sendRedirect("Customer?action=LoadmanageGroup");
                }
                break;
            }

            case "manageCustomer": { // load customers details accourdint to group
                String gid = request.getParameter("groupId").trim();
                
                request.setAttribute("gid", gid);
//                System.out.println(".........."+gid);
                List<Customer> cList = customerDAOImpl.getListofUsers(company);
                request.setAttribute("cList", cList);
                List<CustomerHasGroup> chList = customerDAOImpl.loadCustomerByGroup(Integer.parseInt(gid));
                request.setAttribute("chList", chList);
                requestDispatcher = request.getRequestDispatcher("app/customer/manageCustomer.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "addcustomerToGroup": { // add customer into group
//                System.out.println("Call to Add Customer.....");
                String id = request.getParameter("groupId").trim();
//                System.out.println("!!!!!!!!!!"+id);
                String data = request.getParameter("dataArr");
//                System.out.println(">>>"+data);
                String arrL[] = data.split(",");
                
                boolean bool =true;
                
                for(int i=0; i<arrL.length ;i++){
                    CustomerHasGroup c = new CustomerHasGroup();
                    c.setCustomerGroupId(new CustomerGroup(Integer.parseInt(id)));
                    c.setCustomerId(new Customer(Integer.parseInt(arrL[i])));
                    String result  = customerDAOImpl.saveCustomerHasGroup(c);
                    if(result.equals(VertecConstants.SUCCESS)){
                        bool=true;
                    }else{
                        bool = false;
                    }
                    
                }
                
                
                
                
                if (bool) {
                   response.getWriter().write(VertecConstants.SUCCESS);
                } else{
                    response.getWriter().write(VertecConstants.ERROR);
                }
                break;
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
