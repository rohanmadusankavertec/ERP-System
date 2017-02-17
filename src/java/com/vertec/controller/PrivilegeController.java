/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.PrivilegeDAOImpl;
import com.vertec.daoimpl.UserDAOImpl;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Priviledge;
import com.vertec.hibe.model.PrivilegeItem;
import com.vertec.hibe.model.SysUser;
import com.vertec.hibe.model.UserGroup;
import com.vertec.hibe.model.UserGroupPriviledge;
import com.vertec.hibe.model.UserGroupPrivilegeItem;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author User
 */
public class PrivilegeController extends HttpServlet {

    private final PrivilegeDAOImpl privilegeDAOImpl = new PrivilegeDAOImpl();

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
             * Load add privilage page
             */
            case "ViewPrivilege": {
                List<Priviledge> prList = privilegeDAOImpl.loadAllPrivileges(company);
                request.setAttribute("prList", prList);
                requestDispatcher = request.getRequestDispatcher("app/privilege/addPrivilege.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * Save Privilege
             */
            case "SavePrivilege": {
                String privilege = request.getParameter("privilege").trim();
                Priviledge priviledge = new Priviledge(privilege,company);
                String status = privilegeDAOImpl.savePrivilege(priviledge);
                response.getWriter().write(status);
                break;
            }
            /**
             * Update privilage
             */
            case "UpdatePrivilege": {
                String dataArr = request.getParameter("dataArr").trim();
                String newArr[] = dataArr.split(",");
                int prId = Integer.parseInt(newArr[0]);
                Priviledge priviledge = new Priviledge(prId, newArr[1]);
                String status = privilegeDAOImpl.updatePrivilege(priviledge);
                response.getWriter().write(status);
                break;
            }
            /**
             * Load user group according to company
             */
            case "LoadUserGroups": {
                List<UserGroup> ugList = privilegeDAOImpl.loadAllUserGroup(company);
                request.setAttribute("ugList", ugList);
                requestDispatcher = request.getRequestDispatcher("app/privilege/setPrivilege.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * Load user group for privilege item
             */
            case "LoadUserGroupsForPI": {
                List<UserGroup> ugList = privilegeDAOImpl.loadAllUserGroup(company);
                request.setAttribute("ugList", ugList);
                requestDispatcher = request.getRequestDispatcher("app/privilege/setPrivilegeItem.jsp");
                requestDispatcher.forward(request, response);

                break;
            }
            /**
             * get all user group Privilege
             */
            case "SetPrivilege": {
                String groupId = request.getParameter("groupId").trim();
                int group = Integer.parseInt(groupId);
                List<Object[]> prList = privilegeDAOImpl.loadNotInAllPrivileges(group);
                List<UserGroupPriviledge> ugpList = privilegeDAOImpl.loadAllUserGroupPrivilege(group);

                JSONObject jOB = new JSONObject();
                JSONArray jar1 = new JSONArray();
                JSONArray jar2 = new JSONArray();
                JSONObject job1 = null;
                JSONObject job2 = null;
                for (Object[] p : prList) {
                    job1 = new JSONObject();
                    job1.put("pid", p[0].toString());
                    job1.put("pname", p[1].toString());
                    jar1.add(job1);
                }
                for (UserGroupPriviledge ugp : ugpList) {
                    job2 = new JSONObject();
                    job2.put("pid", ugp.getPriviledgeId().getPriviledgeId());
                    job2.put("pname", ugp.getPriviledgeId().getPriviledgeName());
                    jar2.add(job2);
                }
                jOB.put("jArr1", jar1);

                jOB.put("jArr2", jar2);
                response.getWriter().write(jOB.toString());
                break;
            }
            /**
             * Get user group privilege item
             */
            case "SetPrivilegeItem": {
                String groupId = request.getParameter("groupId").trim();
                int group = Integer.parseInt(groupId);
                List<Object[]> prList = privilegeDAOImpl.loadNotInAllPrivilegeItems(group);
                List<Object[]> ugpList = privilegeDAOImpl.loadAllUserGroupPrivilegeItem(group);

                JSONObject jOB = new JSONObject();
                JSONArray jar1 = new JSONArray();
                JSONArray jar2 = new JSONArray();
                JSONObject job1 = null;
                JSONObject job2 = null;
                for (Object[] p : prList) {
                    job1 = new JSONObject();
                    job1.put("piid", p[0].toString());
                    job1.put("pid", p[1].toString());
                    job1.put("piname", p[2].toString());
                    job1.put("pname", p[3].toString());
                    job1.put("status", "NO");
                    jar1.add(job1);
                }
                for (Object[] p : ugpList) {
                    job1 = new JSONObject();
                    job1.put("piid", p[0].toString());
                    job1.put("pid", p[1].toString());
                    job1.put("piname", p[2].toString());
                    job1.put("pname", p[3].toString());
                    job1.put("status", "YES");
                    jar1.add(job1);
                }
                jOB.put("jArr1", jar1);

                response.getWriter().write(jOB.toString());
                break;
            }
            /**
             * Update privilege
             */
            case "UpdatePriviledge": {
                String dataArr = request.getParameter("dataArr").trim();
                String arr[] = dataArr.split(",");
                int length = arr.length;
                int ugId = 0;
                if (arr[0] != null) {
                    ugId = Integer.parseInt(arr[0]);
                }
                String result = privilegeDAOImpl.removeUGPrivilege(ugId);
                for (int i = 1; i < length; i = i + 1) {
                    int perId = 0;
                    if (arr[i] != null) {
                        perId = Integer.parseInt(arr[i]);
                    }
                    UserGroup ug = new UserGroup(ugId);
                    Priviledge p = new Priviledge(perId);
                    UserGroupPriviledge ugp2 = new UserGroupPriviledge(p, ug);
                    result = privilegeDAOImpl.saveUGPrivilege(ugp2);
                }

                response.getWriter().write(result);
                break;
            }
            /**
             * Update privilege item
             */
            case "UpdatePriviledgeItem": {
                String dataArr = request.getParameter("dataArr").trim();
                String arr[] = dataArr.split(",");
                int length = arr.length;
                int ugId = 0;
                if (arr[0] != null) {
                    ugId = Integer.parseInt(arr[0]);
                }

                String result = privilegeDAOImpl.removeUGPrivilegeItem(ugId);
//                List<Integer> newAr = new ArrayList<Integer>();
                for (int i = 1; i < length; i = i + 1) {

                    int perId = 0;
                    if (arr[i] != null) {
                        perId = Integer.parseInt(arr[i]);
                    }

                    UserGroup ug = new UserGroup(ugId);
                    PrivilegeItem p = new PrivilegeItem(perId);
                    UserGroupPrivilegeItem ugp2 = new UserGroupPrivilegeItem(p, ug);
                    result = privilegeDAOImpl.saveUGPrivilegeItem(ugp2);
                }
                if (ugId == user1.getUserGroupId().getUserGroupId()) {
                    SysUser user2 = new UserDAOImpl().viewUser(user1.getSysuserId());

                    request.getSession().invalidate();
                    request.getSession().removeAttribute("user");

                    HttpSession session = request.getSession();
                    session.setAttribute("user", user2);
                }
                response.getWriter().write(result);
                break;
            }
            /**
             * Load add privilege item page
             */
            case "ForPrivilegeItem": {
                List<Priviledge> prevList = privilegeDAOImpl.loadAllPrivileges(company);
                List<PrivilegeItem> prevItemList = privilegeDAOImpl.loadAllPrivilegeItems(company);
                request.setAttribute("prevList", prevList);
                request.setAttribute("prevItemList", prevItemList);
                requestDispatcher = request.getRequestDispatcher("app/privilege/addPrivilegeItem.jsp");
                requestDispatcher.forward(request, response);

                break;
            }
            /**
             * Save privilege Item
             */
            case "SavePI": {
                String privilegeName = request.getParameter("privilegeName");
                String privilegeCode = request.getParameter("privilegeCode");
                String privilegeStatus = request.getParameter("privilegeStatus");
                String privilege = request.getParameter("privilege");
                Date date = new Date();

                int privId = 0;
                if (privilege != null) {
                    privId = Integer.parseInt(privilege);
                }
                Priviledge p = new Priviledge(privId);

                PrivilegeItem pi = new PrivilegeItem(privilegeName, privilegeCode, privilegeStatus, date, p,company);
                String result = privilegeDAOImpl.savePrivilegeItem(pi);
                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().setAttribute("Success_Message", "Successfully Added");
                    response.sendRedirect("Privilege?action=ForPrivilegeItem");
                } else {
                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    response.sendRedirect("Privilege?action=ForPrivilegeItem");
                }

                break;
            }
            /**
             * View privilege item
             */
            case "ViewPI": {
                String pId = request.getParameter("pId");

                int privId = 0;
                if (pId != null) {
                    privId = Integer.parseInt(pId);
                }

                PrivilegeItem pi = new PrivilegeItem(privId);
                PrivilegeItem privilegeItem = privilegeDAOImpl.ViewPrivilegeItem(pi);
                List<Priviledge> prevList = privilegeDAOImpl.loadAllPrivileges(company);

                request.setAttribute("privilegeItem", privilegeItem);
                request.setAttribute("prevList", prevList);

                requestDispatcher = request.getRequestDispatcher("app/privilege/viewPI.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * Update privilege item
             */
            case "SaveUpdatedPI": {

                String dataArr = request.getParameter("dataArr");
                String arr[] = dataArr.split(",");
                String privilegeItId = arr[0];
                String privilegeName = arr[1];
                String privilegeCode = arr[2];
                String privilegeStatus = arr[3];
                String privilege = arr[4];
                int priItId = 0;
                int priId = 0;

                if (privilegeItId != null) {
                    priItId = Integer.parseInt(privilegeItId);
                }
                if (privilege != null) {
                    priId = Integer.parseInt(privilege);
                }

                PrivilegeItem pi = new PrivilegeItem(priItId, privilegeName, privilegeCode, privilegeStatus, new Priviledge(priId));
                String result = privilegeDAOImpl.updatePrivilegeItem(pi);
                response.getWriter().write(result);

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
