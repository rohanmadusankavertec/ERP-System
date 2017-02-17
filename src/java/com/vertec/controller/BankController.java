/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.BankDAOImpl;
import com.vertec.hibe.model.Bank;
import com.vertec.hibe.model.BankAccounts;
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
 * @author User
 */
public class BankController extends HttpServlet {

    private BankDAOImpl bankDAOImpl = new BankDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession httpSession = request.getSession();
        SysUser user1 = (SysUser) httpSession.getAttribute("user");
        RequestDispatcher requestDispatcher;
        boolean isValidated = true;
        switch (action) {
            case "SaveBank": { // add new bank details
                String bankName = request.getParameter("bankName").trim();
                String branchName = request.getParameter("branchName").trim();
                String address = request.getParameter("address").trim();
                String contactNo = request.getParameter("contactNo").trim();
                Bank bank =new Bank();
                bank.setBankName(bankName);
                bank.setBranch(branchName);
                bank.setAddress(address);
                bank.setOfficeNo(contactNo);
                String result = bankDAOImpl.saveBank(bank);
                requestDispatcher = request.getRequestDispatcher("Bank?action=LoadBank");
                requestDispatcher.forward(request, response);
                break;
            }
            case "LoadBank": { // load all bank account with bank details
                
//                System.out.println("Called Load Bank");
                
                
                List<Bank> bList = bankDAOImpl.loadAllBank();
//                System.out.println("bank list ok");
                List<BankAccounts> baList = bankDAOImpl.loadAllBankAccount();
//                System.out.println("bank account list ok");
                request.setAttribute("bList", bList);
                request.setAttribute("baList", baList);
                requestDispatcher = request.getRequestDispatcher("app/bank/bankRegister.jsp");
                requestDispatcher.forward(request, response);
                break;

            }
            case "SaveAccount": { // add new bank account
                String accNo = request.getParameter("accNo").trim();
                String accType = request.getParameter("accType").trim();
                String bnkId = request.getParameter("bankId").trim();

                int bankId = 0;

                if (bnkId != null) {
                    bankId = Integer.parseInt(bnkId);
                }
                Bank bank = new Bank(bankId);
                Date date = new Date();
                BankAccounts bankAccount = new BankAccounts();
                bankAccount.setAccountNo(accNo);
                bankAccount.setAccountType(accType);
                bankAccount.setAddedDate(date);
                bankAccount.setLastUpdatedDate(date);
                bankAccount.setAccountStatus(true);
                bankAccount.setLastUpdatedBy(user1);
                bankAccount.setBankId(bank);
                bankAccount.setAddedBy(user1);
                String result = bankDAOImpl.saveBankAccount(bankAccount);
                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");
                    request.getSession().setAttribute("Success_Message", "Successfully Added");
                } else {
                    request.getSession().removeAttribute("Error_Message");
                    request.getSession().setAttribute("Error_Message", "Not Added,Please Tri again");
                }
                requestDispatcher = request.getRequestDispatcher("Bank?action=LoadBank");
                requestDispatcher.forward(request, response);
                break;

            }
            case "UpdateAccount": { // load account details to update
                String bankAccId = request.getParameter("bankAccountId").trim();
                int bankAccountId = 0;
                if (bankAccId != null) {
                    bankAccountId = Integer.parseInt(bankAccId);
                }
                BankAccounts bankAccount = bankDAOImpl.ViewBankAccount(bankAccountId);
                request.setAttribute("bankAccount", bankAccount);
                List<Bank> bList = bankDAOImpl.loadAllBank();
                request.setAttribute("bList", bList);
                requestDispatcher = request.getRequestDispatcher("app/bank/viewBank.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            
            case "UpdateSelected": {// update account
                String accId = request.getParameter("accId").trim();
                String accNo = request.getParameter("accNo").trim();
                String bankId2 = request.getParameter("bankId").trim();
                String accType = request.getParameter("accType").trim();
                int bankAccountId = 0;
                if (accId != null) {
                    bankAccountId = Integer.parseInt(accId);
                }
                
                int bankId = 0;
                if (bankId2 != null) {
                    bankId = Integer.parseInt(bankId2);
                }
                
                Date date = new Date();
                BankAccounts ba = new BankAccounts();
                ba.setId(bankAccountId);
                ba.setAccountNo(accNo);
                ba.setBankId(new Bank(bankId));
                ba.setAccountType(accType);
                ba.setLastUpdatedBy(user1);
                ba.setLastUpdatedDate(date);
                
                
                String result = bankDAOImpl.updateBankAccount(ba);
                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");
                    request.getSession().setAttribute("Success_Message", "Successfully Updated");
                } else {
                    request.getSession().removeAttribute("Error_Message");
                    request.getSession().setAttribute("Error_Message", "Not Updated,Please Try again");
                }
                requestDispatcher = request.getRequestDispatcher("Bank?action=LoadBank");
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
