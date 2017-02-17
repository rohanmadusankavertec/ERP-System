/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vertec.controller;

import com.vertec.daoimpl.SupplierDAOImpl;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Supplier;
import com.vertec.hibe.model.SupplierGroup;
import com.vertec.hibe.model.SupplierHasGroup;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.VertecConstants;
import java.io.IOException;
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
@WebServlet(name = "SupplierController", urlPatterns = {"/SupplierController"})
public class SupplierController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final SupplierDAOImpl supplierDAOImpl = new SupplierDAOImpl();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession httpSession = request.getSession();
        SysUser user1 = (SysUser) httpSession.getAttribute("user");
        Company company = (Company) httpSession.getAttribute("company");
        RequestDispatcher requestDispatcher;
        boolean isValidated = true;

        switch (action) {
            /**
             * form action="../../Customer?action=Register" from
             * registerCustomer.jsp
             *
             */
            case "Register": {
                System.out.println("In Supplier Registration");
                System.out.println(user1.getFirstName());
                String supplierName = request.getParameter("supplierName").trim();
                String companyName = request.getParameter("companyName").trim();
                String address = request.getParameter("address").trim();
                String fax = request.getParameter("faxNo").trim();
                String land = request.getParameter("land").trim();
                String mobile = request.getParameter("mobile").trim();
                String email = request.getParameter("email").trim();
                String type = request.getParameter("suppliertype").trim();
                
                boolean type1=true;
                if(type.equals("0")){
                    type1=false;
                }
                
                Date date = new Date();
                System.out.println(supplierName+" "+ companyName+" "+ address+" "+ fax+" "+ land+" "+ mobile+" "+ email+" "+ date+" "+ isValidated+" "+ user1+" "+type1);
                Supplier supplier = new Supplier(supplierName, companyName, address, fax, land, mobile, email, date, isValidated, user1,type1,company);
                String result = supplierDAOImpl.saveSupplier(supplier);

                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Added");
                    response.sendRedirect("dashboard.jsp");
                } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Added,Please Tri again");
                    response.sendRedirect("dashboard.jsp");
                }

                break;
            }
            /**
             * URL to customerDetails.jsp
             */
            case "ViewSupplier": {
                List<Supplier> suList = supplierDAOImpl.getListofUsers(company);
                request.setAttribute("suList", suList);
                requestDispatcher = request.getRequestDispatcher("app/supplier/supplierDetails.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "RemoveSupplier": {
                String userId = request.getParameter("supplierId").trim();
                int supplierId = Integer.parseInt(userId);
                String status = supplierDAOImpl.removeSupplier(supplierId);
                response.getWriter().write(status);
                break;
            }
            case "UpdateSupplier": {
                String userId = request.getParameter("supplierId").trim();
                int supplierId = Integer.parseInt(userId);
                Supplier customer = supplierDAOImpl.viewSupplier(supplierId);
                request.setAttribute("su", customer);
                requestDispatcher = request.getRequestDispatcher("app/supplier/viewSupplier.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "UpSel": {
                System.out.println("CALLING UPDATE FUNCTION");
                String supplierId = request.getParameter("supplierId").trim();
                String supplierName = request.getParameter("supplierName").trim();
                String companyName = request.getParameter("companyName").trim();
                String address = request.getParameter("address").trim();
                String fax = request.getParameter("faxNo").trim();
                String land = request.getParameter("land").trim();
                String mobile = request.getParameter("mobile").trim();
                String email = request.getParameter("email").trim();
                String type = request.getParameter("suppliertype").trim();
                
                boolean type1=true;
                if(type.equals("0")){
                    type1=false;
                }
                int suId = 0;
                if (supplierId != null) {
                    suId = Integer.parseInt(supplierId);
                }
                System.out.println(suId+"  "+supplierName+"  "+companyName+"  "+address+"  "+fax+"  "+land+"  "+mobile+"  "+email+"  "+isValidated+"  "+user1);
                Supplier supplier = new Supplier(suId,supplierName, companyName, address, fax, land, mobile, email, isValidated, user1,type1);
                
//                Customer customer = new Customer(cuId, customerName, address, hotline, officeNo, faxNo, contactPerson, contactPersonNo, contactPersonEmail);
                String result = supplierDAOImpl.updateSupplier(supplier);

                if (result.equals(VertecConstants.UPDATED)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Updated");
                    response.sendRedirect("Supplier?action=ViewSupplier");
                } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Updated,Please Try again");
                    response.sendRedirect("Supplier?action=ViewSupplier");
                }

                break;
            }
            case "CheckEmail": {
                String email = request.getParameter("email");
                String status = supplierDAOImpl.checkEmail(email);
                response.getWriter().write(status);
                break;
            }
             case "LoadSupplierGroup": {
                requestDispatcher = request.getRequestDispatcher("app/supplier/AddSupplierGroup.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            
            case "LoadmanageGroup": {
                List<SupplierGroup> sgList = supplierDAOImpl.getListofSupplerGroup(company);
                request.setAttribute("sgList", sgList);
                requestDispatcher = request.getRequestDispatcher("app/supplier/sGroupDetails.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "viewGroupDetail": {
                String id = request.getParameter("groupId").trim();
                System.out.println("......... "+id);
                SupplierGroup sgList = supplierDAOImpl.viewSupplerGroup(Integer.parseInt(id));
                request.setAttribute("sg", sgList);
                requestDispatcher = request.getRequestDispatcher("app/supplier/viewSupplierGroup.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            
            case "saveSupplierGroup": {
                String name = request.getParameter("supplierGName").trim();
                SupplierGroup s = new SupplierGroup();
                s.setName(name);
                s.setCompanyId(company);
                String result = supplierDAOImpl.saveSupplierGroup(s);
                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Added");
                    response.sendRedirect("Supplier?action=LoadSupplierGroup");
                } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    response.sendRedirect("Supplier?action=LoadSupplierGroup");
                }
                break;
            }
            
            case "updateSupplierGroup": {
                String name = request.getParameter("supplierGName").trim();
                String id = request.getParameter("groupId").trim();
                
                SupplierGroup sg = new SupplierGroup();
                sg.setId(Integer.parseInt(id));
                sg.setName(name);
                
                String result  = supplierDAOImpl.updateSupplierGroup(sg);
                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Added");
                    response.sendRedirect("Supplier?action=LoadmanageGroup");
                } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    response.sendRedirect("Supplier?action=LoadmanageGroup");
                }
                break;
            }
            case "manageSupplier": {
                String gid = request.getParameter("groupId").trim();
                
                request.setAttribute("gid", gid);
                System.out.println(".........."+gid);
                List<Supplier> sList = supplierDAOImpl.getListofUsers();
                request.setAttribute("sList", sList);
                List<SupplierHasGroup> shList = supplierDAOImpl.loadSupplierByGroup(Integer.parseInt(gid));
                request.setAttribute("shList", shList);
                requestDispatcher = request.getRequestDispatcher("app/supplier/manageSupplier.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "addSupplierToGroup": {
                System.out.println("Call to Add Customer.....");
                String id = request.getParameter("groupId").trim();
                System.out.println("!!!!!!!!!!"+id);
                String data = request.getParameter("dataArr");
                System.out.println(">>>"+data);
                String arrL[] = data.split(",");
                
                boolean bool =true;
                
                for(int i=0; i<arrL.length ;i++){
                    SupplierHasGroup c = new SupplierHasGroup();
                    c.setSupplierGroupId(new SupplierGroup(Integer.parseInt(id)));
                    c.setSupplierId(new Supplier(Integer.parseInt(arrL[i])));
                    
                    String result  = supplierDAOImpl.saveSupplierHasGroup(c);
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
