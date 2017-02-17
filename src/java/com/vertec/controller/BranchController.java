package com.vertec.controller;

import com.vertec.daoimpl.BranchDAOImpl;
import com.vertec.hibe.model.Branch;
import com.vertec.hibe.model.Company;
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
public class BranchController extends HttpServlet {

    /**
     * BranchController URL=Branch This Controller all the action calls get from
     * app/branch/(addBranch.jsp,addWarehouse.jsp,viewBranch.jsp,viewWarehouse.jsp)
     * and all functions call get from app/js/branch.js
     *
     */
    private final BranchDAOImpl branchDAOImpl = new BranchDAOImpl();

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
             * form action="Branch?action=SaveBranch" from addBranch.jsp
             */
            case "SaveBranch": { // add new branch 
                String branchName = request.getParameter("branchName").trim();
                String contactNo = request.getParameter("contactNo").trim();
                String address = request.getParameter("address").trim();

//                Branch branch = new Branch(branchName, contactNo, address);
                Branch branch = new Branch();
                branch.setBranchName(branchName);
                branch.setContactNo(contactNo);
                branch.setAddress(address);
                branch.setCompanyId(company);
                String result = branchDAOImpl.saveBranch(branch);

                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Added");
                    response.sendRedirect("Branch?action=ViewBranches");
                } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    response.sendRedirect("Branch?action=ViewBranches");
                }
                break;
            }

            /**
             * form action="Branch?action=SaveWarehouse" from addWarehouse.jsp
             */
//            case "SaveWarehouse": {
//                String branchName = request.getParameter("branchName").trim();
//                String contactNo = request.getParameter("contactNo").trim();
//                String address = request.getParameter("address").trim();
//                Warehouse warehouse = new Warehouse(branchName, contactNo, address);
//
//                String result = branchDAOImpl.saveWarehouse(warehouse);
//                if (result.equals(VertecConstants.SUCCESS)) {
//                    request.getSession().removeAttribute("Success_Message");
//
//                    request.getSession().setAttribute("Success_Message", "Successfully Added");
//                    response.sendRedirect("Branch?action=ViewWarehouses");
//                } else {
//                    request.getSession().removeAttribute("Error_Message");
//
//                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
//                    response.sendRedirect("Branch?action=ViewWarehouses");
//                }
//
//                break;
//            }
            /**
             * URL to addBranch.jsp
             */
            case "ViewBranches": { // load all branches
//                System.out.println("Calling View Branched");
                List<Branch> bList = branchDAOImpl.loadAllBranches(user1.getBranchBranchId());
                request.setAttribute("bList", bList);
                requestDispatcher = request.getRequestDispatcher("app/branch/addBranch.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "DeleteExpense": { // remove expense
                String id = request.getParameter("id");
                String result = branchDAOImpl.deleteExpenses(id);
                response.getWriter().write(result);
                break;
            }
            case "ViewBranchExpenses": { // load add expense page with branche details
                List<Branch> bList = branchDAOImpl.loadAllBranches(user1.getBranchBranchId());
                request.setAttribute("bList", bList);
                requestDispatcher = request.getRequestDispatcher("app/branch/addExpenses.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * URL to addWarehouse.jsp
             */
//            case "ViewWarehouses": {
//                List<Warehouse> wList = branchDAOImpl.loadAllWarehouses();
//                request.setAttribute("wList", wList);
//                requestDispatcher = request.getRequestDispatcher("app/branch/addWarehouse.jsp");
//                requestDispatcher.forward(request, response);
//                break;
//            }
            /**
             * url to viewBranch.jsp
             */
            case "ViewBranch": { // load branche details
                String bId = request.getParameter("bId").trim();
                int branchId = 0;
                if (bId != null) {
                    branchId = Integer.parseInt(bId);
                }
                Branch branch = branchDAOImpl.viewBranch(branchId);
                request.setAttribute("branch", branch);
                requestDispatcher = request.getRequestDispatcher("app/branch/viewBranch.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * url to viewWarehouse.jsp
             */
//            case "ViewWarehouse": {
//                String bId = request.getParameter("bId").trim();
//                int branchId = 0;
//                if (bId != null) {
//                    branchId = Integer.parseInt(bId);
//                }
//                Warehouse warehouse = branchDAOImpl.viewWarehouse(branchId);
//                request.setAttribute("warehouse", warehouse);
//                requestDispatcher = request.getRequestDispatcher("app/branch/viewWarehouse.jsp");
//                requestDispatcher.forward(request, response);
//                break;
//            }
            /**
             * function updateSelectedBranch() To save Update Selected Branch
             */
            case "SaveUpdatedBranch": { //update branche details
                String dataArr = request.getParameter("dataArr");
                String arr[] = dataArr.split("_,");
                String bId = arr[0];
                String branchName = arr[1];
                String contactNo = arr[2];
                String address = arr[3];
                int branchId = 0;
                if (bId != null) {
                    branchId = Integer.parseInt(bId);
                }

//                Branch branch = new Branch(branchId, branchName, contactNo, address);
                Branch branch = new Branch();
                branch.setBranchId(branchId);
                branch.setBranchName(branchName);
                branch.setContactNo(contactNo);
                branch.setAddress(address);
                String result = branchDAOImpl.updateBranch(branch);

                response.getWriter().write(result);
                break;

            }
            
           
            case "ToStockReport": { // load stock report page
                List<Branch> bList = branchDAOImpl.loadAllBranches(user1.getBranchBranchId());
                request.setAttribute("bList", bList);
                requestDispatcher = request.getRequestDispatcher("app/report/stockReport.jsp");
                requestDispatcher.forward(request, response);
                break;

            }
            case "WarehouseStock": { // load ware house stock page
                String type = request.getParameter("stockType");
                request.setAttribute("type", type);
                requestDispatcher = request.getRequestDispatcher("app/warehouse/WarehouseStock.jsp");
                requestDispatcher.forward(request, response);
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
