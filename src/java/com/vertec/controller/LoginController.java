package com.vertec.controller;

import com.vertec.daoimpl.UserDAOImpl;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.MD5Hashing;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class LoginController extends HttpServlet {

    /**
     * LoginController URL=Login This Controller Only used for User Login
     */
    private final UserDAOImpl userDAO = new UserDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        
        
        
        RequestDispatcher requestDispatcher;
        boolean isValidated = true;

        if (username.length() == 0 && password.length() == 0) {
            isValidated = false;
            request.setAttribute("Error_Message", "Please Enter Username and password");
            requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        } else if (password.length() == 0 && password != null) {
            isValidated = false;
            request.setAttribute("Error_Message", "Please Enter Password");
            requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        } else if (username.length() == 0 & username != null) {
            isValidated = false;
            request.setAttribute("Error_Message", "Please Enter Username");
            requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        }
        if (isValidated) {
            SysUser user = new SysUser();
            user.setUsername(username);
            MD5Hashing mD5Hashing = new MD5Hashing();
            String encryptPassword = mD5Hashing.encryptByteToHex(password);
            user.setPassword(encryptPassword);

            SysUser sysuser = userDAO.userlogin(user);

            String loginStatus = sysuser.getLoginStatus();

            switch (loginStatus) {
                case VertecConstants.SUCCESS:
                    HttpSession session = request.getSession();
                    session.setAttribute("user", sysuser);
                    session.setAttribute("company", sysuser.getCompanyId());
                    request.setAttribute("username", sysuser.getUsername());
                    request.setAttribute("Success_Message", "User Logged SuccessFully");
                    requestDispatcher = request.getRequestDispatcher("dashboard.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                case VertecConstants.ERROR:
                    request.setAttribute("Error_Message", "User Account is deactivated.Please Contact Administrator");
                    requestDispatcher = request.getRequestDispatcher("index.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                case VertecConstants.INVALID_LOGIN:
                    request.setAttribute("Error_Message", "Invalid Login Details");
                    requestDispatcher = request.getRequestDispatcher("index.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                case VertecConstants.ERROR_MESSAGE:
                    request.setAttribute("Error_Message", "Please Contact System Administrator");
                    requestDispatcher = request.getRequestDispatcher("index.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                default:
                    break;
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
