/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.UserDAOImpl;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.PrivilegeItem;
import com.vertec.hibe.model.SysUser;
import com.vertec.hibe.model.UserGroup;
import com.vertec.hibe.model.UserGroupPrivilegeItem;
import com.vertec.util.MD5Hashing;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

/**
 *
 * @author User
 */
public class UserController extends HttpServlet {

    private final UserDAOImpl userDAO = new UserDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException {

        String action = request.getParameter("action");
        HttpSession httpSession = request.getSession();
        SysUser user1 = (SysUser) httpSession.getAttribute("user");
        Company company = (Company) httpSession.getAttribute("company");
        RequestDispatcher requestDispatcher;
        boolean isValidated = true;

        switch (action) {
            case "CheckUsername": {
                String username = request.getParameter("username").trim();
                String status = userDAO.checkUsername(username);
                response.getWriter().write(status);
                break;
            }
            case "Save": {
                String name = request.getParameter("name");
                String usercompany = request.getParameter("company");
                String email = request.getParameter("email");
                String contactNo = request.getParameter("contactNo");
                String nicNo = request.getParameter("nicNo");
                String dob = request.getParameter("dob");
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String userGroup = request.getParameter("userGroup");
                String userBranch = request.getParameter("userBranch");
                int userGroupId = 0;
                if (userGroup != null) {
                    userGroupId = Integer.parseInt(userGroup);
                }
                int userBranchId = 0;
                if (userBranch != null) {
                    userBranchId = Integer.parseInt(userBranch);

                }
                UserGroup group = new UserGroup(userGroupId);

                String arr[] = name.split(" ");
                String firstName = arr[0];
                String lastName = arr[1];
                Date date = new Date();
                int userId = user1.getSysuserId();

                MD5Hashing mD5Hashing = new MD5Hashing();
                String encryptPassword = mD5Hashing.encryptByteToHex(password);
                
//                Branch b= new Branch(1);
                
                SysUser sysUser = new SysUser(username, encryptPassword, true, firstName, lastName, email, contactNo, nicNo, dob, date, userId, date, userId, group,new Company(Integer.parseInt(usercompany)),user1.getBranchBranchId());
                String result = userDAO.registerUser(sysUser);
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
            case "ViewUsers": {
                List<SysUser> userList = userDAO.getListofUsers(company);
                request.setAttribute("userList", userList);
                requestDispatcher = request.getRequestDispatcher("app/users/userDetails.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "RemoveUser": {
                String userId = request.getParameter("userId").trim();
                int usId = Integer.parseInt(userId);
                String status = userDAO.removeUser(usId);
                response.getWriter().write(status);
                break;
            }
            case "UpdateUser": {
                String userId = request.getParameter("userId").trim();
                int usId = Integer.parseInt(userId);
                SysUser su = userDAO.viewUser(usId);
                request.setAttribute("su", su);
                
                List<Company> co = userDAO.getCompanies(company.getCompanyGroupId());
                request.setAttribute("company", co);
                
                requestDispatcher = request.getRequestDispatcher("app/users/viewUser.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "CreateUG": {
                String userGroup = request.getParameter("userGroup").trim();
                List<PrivilegeItem> pList = userDAO.allDefPriv();
                UserGroup group = new UserGroup(userGroup,company);
                UserGroup su = userDAO.createUserGroup(group);
                for (PrivilegeItem pl : pList) {
                    int pi = pl.getPrivilegeItemId();
                    UserGroupPrivilegeItem ugpi = new UserGroupPrivilegeItem(pl, group);
                    String res = userDAO.saveUGPI(ugpi);
                }
                requestDispatcher = request.getRequestDispatcher("dashboard.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "UserRegistration": {
                List<Company> co = userDAO.getCompanies(company.getCompanyGroupId());
                request.setAttribute("company", co);
                requestDispatcher = request.getRequestDispatcher("app/users/userRegister.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "UpSel": {
                String userId = request.getParameter("userId").trim();
                String name = request.getParameter("name").trim();
                String email = request.getParameter("email").trim();
                String contactNo = request.getParameter("contactNo").trim();
                String nicNo = request.getParameter("nicNo").trim();
                String dob = request.getParameter("dob").trim();
                String usercompany = request.getParameter("company").trim();
                
                 String userGroup = request.getParameter("userGroup");
                 
                int userGroupId = 0;
                if (userGroup != null) {
                    userGroupId = Integer.parseInt(userGroup);

                }
                UserGroup group = new UserGroup(userGroupId);

                
                
                String branch = request.getParameter("userBranch");
                 
                int userBranchId = 0;
                if (branch != null) {
                    userBranchId = Integer.parseInt(branch);

                }

                
                
                int usId = 0;
                if (userId != null) {
                    usId = Integer.parseInt(userId);
                }
                String nameArr[] = name.split(" ");
                String firstName = nameArr[0];
                String lastName = nameArr[1];

                Date date = new Date();
                int updateUser = user1.getSysuserId();

                SysUser sysUser = new SysUser(usId, firstName, lastName, email, contactNo, nicNo, dob, date, updateUser,group,new Company(Integer.parseInt(usercompany)));
                String result = userDAO.updateUser(sysUser);
                if (result.equals(VertecConstants.UPDATED)) {
                    request.getSession().removeAttribute("Success_Message");
                    request.getSession().setAttribute("Success_Message", "Successfully Updated User");
                    response.sendRedirect("User?action=ViewUsers");
                } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    response.sendRedirect("User?action=ViewUsers");
                }
                break;
            }
            case "UpOwn": {
                String userId = request.getParameter("userId").trim();
                String name = request.getParameter("name").trim();
                String email = request.getParameter("email").trim();
                String contactNo = request.getParameter("contactNo").trim();
                String nicNo = request.getParameter("nicNo").trim();
                String dob = request.getParameter("dob").trim();
                int usId = 0;
                if (userId != null) {
                    usId = Integer.parseInt(userId);
                }
                String nameArr[] = name.split(" ");
                String firstName = nameArr[0];
                String lastName = nameArr[1];

                Date date = new Date();
                int updateUser = user1.getSysuserId();

                SysUser sysUser = new SysUser(usId, firstName, lastName, email, contactNo, nicNo, dob, date, updateUser);
                String result = userDAO.updateUser(sysUser);

                SysUser user2 = userDAO.viewUser(usId);

                request.getSession().invalidate();
                request.getSession().removeAttribute("user");

                HttpSession session = request.getSession();
                session.setAttribute("user", user2);
                if (result.equals(VertecConstants.UPDATED)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Updated User");
                    response.sendRedirect("dashboard.jsp");
                } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    response.sendRedirect("dashboard.jsp");
                }

                break;
            }

            case "CheckPW": {
                String dataArr = request.getParameter("dataArr");
                String values[] = dataArr.split(",");
                String password = values[0];
                String userId = values[1];
                int uId = 0;
                if (userId != null) {
                    uId = Integer.parseInt(userId);
                }

                MD5Hashing mD5Hashing = new MD5Hashing();
                String encryptOldPassword = mD5Hashing.encryptByteToHex(password);
                JSONObject jSONObject = new JSONObject();
                if (user1.getPassword().equals(encryptOldPassword)) {
                    jSONObject.put("result", 1);
                } else {
                    jSONObject.put("result", 0);
                }

                response.getWriter().write(jSONObject.toString());
                break;

            }
            case "UpPw": {
                String password = request.getParameter("password");
                int userId = user1.getSysuserId();

                MD5Hashing mD5Hashing = new MD5Hashing();
                String encryptOldPassword = mD5Hashing.encryptByteToHex(password);

                SysUser su = new SysUser(userId, encryptOldPassword);

                String result = userDAO.updatePassword(su);
                if (result.equals(VertecConstants.UPDATEDPASSWORD)) {
                    requestDispatcher = request.getRequestDispatcher("Logout");
                    requestDispatcher.forward(request, response);
                } else {
                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    requestDispatcher = request.getRequestDispatcher("dashboard.jsp");
                    requestDispatcher.forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
