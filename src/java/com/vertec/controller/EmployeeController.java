/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.EmployeeDAOImpl;
import com.vertec.hibe.model.Department;
import com.vertec.hibe.model.Designation;
import com.vertec.hibe.model.Employee;
import com.vertec.hibe.model.EmployeeType;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.ReadNic;
import com.vertec.util.Save;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author vertec-r
 */
@WebServlet(name = "EmployeeController", urlPatterns = {"/EmployeeController"})
public class EmployeeController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final EmployeeDAOImpl EmployeeDAOImpl = new EmployeeDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String action = request.getParameter("action");
            RequestDispatcher requestDispatcher;

            HttpSession httpSession = request.getSession();
            SysUser user1 = (SysUser) httpSession.getAttribute("user");

            switch (action) {

                case "Department": {
                    List<Department> dep = EmployeeDAOImpl.getDepartments();
                    request.setAttribute("departments", dep);
                    requestDispatcher = request.getRequestDispatcher("app/employee/addDepartment.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "EmployeeImage": {
                    List<Employee> cuList = EmployeeDAOImpl.getEmployees();
                    request.setAttribute("employee", cuList);
                    requestDispatcher = request.getRequestDispatcher("app/employee/ImageUpload.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                
                case "addDepartment": {
                    String dep = request.getParameter("departmentname").trim();

                    Department d = new Department();
                    d.setName(dep);
                    d.setIsValid(true);

                    String result = Save.Save(d);

                    if (result.equals(VertecConstants.SUCCESS)) {
                        request.getSession().removeAttribute("Success_Message");
                        request.getSession().setAttribute("Success_Message", "Successfully Added");
                        response.sendRedirect("Employee?action=Department");
                    } else {
                        request.getSession().removeAttribute("Error_Message");

                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                        response.sendRedirect("Employee?action=Department");
                    }
                    break;
                }
                case "deleteDepartment": {
                    String id = request.getParameter("id");
                    String result = EmployeeDAOImpl.deleteDepartment(Integer.parseInt(id));
                    response.getWriter().write(result);
                    break;
                }
                case "getDesignation": {
                    String id = request.getParameter("id");
                    List<Designation> des = EmployeeDAOImpl.getDesignation(Integer.parseInt(id));

                    JSONObject jOB = new JSONObject();
                    JSONArray jar1 = new JSONArray();
                    JSONObject job1 = null;
                    for (Designation d : des) {
                        job1 = new JSONObject();
                        job1.put("id", d.getId());
                        job1.put("designation", d.getName());
                        jar1.add(job1);
                    }
                    jOB.put("des", jar1);
                    response.getWriter().write(jOB.toString());
                    break;
                }
                case "Designation": {
                    List<Designation> desig = EmployeeDAOImpl.getDesignations();
                    List<Department> dep = EmployeeDAOImpl.getDepartments();
                    request.setAttribute("departments", dep);
                    request.setAttribute("designations", desig);
                    requestDispatcher = request.getRequestDispatcher("app/employee/addDesignation.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "addDesignation": {
                    String depar = request.getParameter("departmentname").trim();
                    String desig = request.getParameter("designationname").trim();

                    Designation d = new Designation();
                    Department dep = new Department(Integer.parseInt(depar.toString()));
                    d.setDepartmentId(dep);
                    d.setName(desig);
                    d.setIsValid(true);

                    String result = Save.Save(d);

                    if (result.equals(VertecConstants.SUCCESS)) {
                        request.getSession().removeAttribute("Success_Message");

                        request.getSession().setAttribute("Success_Message", "Successfully Added");
                        response.sendRedirect("Employee?action=Designation");
                    } else {
                        request.getSession().removeAttribute("Error_Message");

                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                        response.sendRedirect("Employee?action=Designation");
                    }
                    break;
                }
                case "deleteDesignation": {
                    String id = request.getParameter("id");
                    String result = EmployeeDAOImpl.deleteDesignation(Integer.parseInt(id));
                    response.getWriter().write(result);
                    break;
                }
                case "EmployeeReg": {
                    List<Designation> desig = EmployeeDAOImpl.getDesignations();
                    List<Department> dep = EmployeeDAOImpl.getDepartments();
                    List<EmployeeType> emp = EmployeeDAOImpl.getEmployeeTypes();
                    request.setAttribute("employeetype", emp);
                    request.setAttribute("departments", dep);
                    request.setAttribute("designations", desig);
                    
                    requestDispatcher = request.getRequestDispatcher("app/employee/registerEmployee.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "ViewEmployee": {
                    List<Employee> cuList = EmployeeDAOImpl.getEmployees();
                    request.setAttribute("employee", cuList);
                    requestDispatcher = request.getRequestDispatcher("app/employee/ViewEmployee.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "addEmployee": {
                    String first = request.getParameter("firstname").trim();
                    String last = request.getParameter("lastname").trim();
                    String nic = request.getParameter("nic").trim();
                    String dob = request.getParameter("dob").trim();
                    String gender = request.getParameter("gender").trim();
                    String address = request.getParameter("address").trim();
                    String designation = request.getParameter("designation").trim();
                    String basic = request.getParameter("basicsalary").trim();

                    String contact = request.getParameter("contact").trim();
                    String emergency = request.getParameter("emergency").trim();
                    String weight = request.getParameter("weight").trim();
                    String height = request.getParameter("height").trim();
                    String civil = request.getParameter("civil").trim();
                    String educational = request.getParameter("educational").trim();
                    String qualification = request.getParameter("qualification").trim();
                    String experience = request.getParameter("experience").trim();
                    String employeetype = request.getParameter("employeetype").trim();
                    String appoint = request.getParameter("appoint").trim();

                    boolean gen = false;
                    if (gender.equals("1")) {
                        gen = true;
                    }

                    SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    Date appointdate=null;
                    try {
                        date = DateFormat.parse(dob);
                        appointdate = DateFormat.parse(appoint);
                    } catch (ParseException ex) {
                        Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Employee e = new Employee();
                    e.setFname(first);
                    e.setLname(last);
                    e.setNic(nic);
                    e.setAppoint(appointdate);
                    e.setEmployeeTypeId(new EmployeeType(Integer.parseInt(employeetype)));
                    e.setDob(date);
                    e.setGender(gen);
                    e.setAddress(address);
                    e.setDesignationId(new Designation(Integer.parseInt(designation)));
                    e.setBasicSalary(Double.parseDouble(basic));
                    e.setIsValid(true);
                    e.setContactNo(contact);
                    e.setEmergency(emergency);
                    e.setWeight(weight);
                    e.setHeight(height);
                    e.setEducational(educational);
                    e.setExperience(experience);
                    e.setQualification(qualification);
                    boolean cvl = false;
                    if (civil.equals("1")) {
                        cvl = true;//married
                    }
                    e.setCivilStatus(cvl);

                    String result = Save.Save(e);

                    if (result.equals(VertecConstants.SUCCESS)) {
                        request.getSession().removeAttribute("Success_Message");
                        request.getSession().setAttribute("Success_Message", "Successfully Added");
                        response.sendRedirect("Employee?action=EmployeeReg");

                    } else {
                        request.getSession().removeAttribute("Error_Message");
                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                        response.sendRedirect("Employee?action=EmployeeReg");

                    }
                    break;
                }
                case "AutoFill": {
                    String nic = request.getParameter("nic");
                    ReadNic r = new ReadNic();
                    String date = r.setMonth(nic);
                    String gender = r.getSex(nic);
                    String result = date + "_" + gender;
                    response.getWriter().write(result);
                    break;
                }
                case "UpdateEmployee": {
                    String emp = request.getParameter("employeeId");
                    int eid = 0;
                    eid = Integer.parseInt(emp);
                    Employee employee = EmployeeDAOImpl.getEmployee(eid);
                    List<Designation> desig = EmployeeDAOImpl.getDesignations();
                    List<Department> dep = EmployeeDAOImpl.getDepartments();
                    List<EmployeeType> empt = EmployeeDAOImpl.getEmployeeTypes();
                    request.setAttribute("empt", empt);
                    request.setAttribute("departments", dep);
                    request.setAttribute("designations", desig);
                    request.setAttribute("employee", employee);
                    requestDispatcher = request.getRequestDispatcher("app/employee/updateEmployee.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "ChangeEmployee": {
                    String eid = request.getParameter("eid").trim();
                    String first = request.getParameter("firstname").trim();
                    String last = request.getParameter("lastname").trim();
                    String nic = request.getParameter("nic").trim();
                    String dob = request.getParameter("dob").trim();
                    String gender = request.getParameter("gender").trim();
                    String address = request.getParameter("address").trim();
                    String designation = request.getParameter("designation").trim();
                    String basic = request.getParameter("basicsalary").trim();
                    String fpid = request.getParameter("fpid").trim();

                    String contact = request.getParameter("contact").trim();
                    String emergency = request.getParameter("emergency").trim();
                    String weight = request.getParameter("weight").trim();
                    String height = request.getParameter("height").trim();
                    String civil = request.getParameter("civil").trim();
                    String educational = request.getParameter("educational").trim();
                    String qualification = request.getParameter("qualification").trim();
                    String experience = request.getParameter("experience").trim();
                    String employeetype = request.getParameter("employeetype").trim();
                    String appoint = request.getParameter("appoint").trim();
                    boolean gen = false;
                    if (gender.equals("1")) {
                        gen = true;
                    }

                    SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    Date appointdate=null;
                    try {
                        date = DateFormat.parse(dob);
                        appointdate = DateFormat.parse(appoint);
                    } catch (ParseException ex) {
                        Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Employee e = new Employee();
                    e.setId(Integer.parseInt(eid));
                    e.setFname(first);
                    e.setLname(last);
                    e.setAppoint(appointdate);
                    e.setEmployeeTypeId(new EmployeeType(Integer.parseInt(employeetype)));
                    e.setNic(nic);
                    e.setDob(date);
                    e.setGender(gen);
                    e.setAddress(address);
                    e.setDesignationId(new Designation(Integer.parseInt(designation)));
                    e.setBasicSalary(Double.parseDouble(basic));
                    e.setIsValid(true);
                    e.setFpid(fpid);

                    e.setContactNo(contact);
                    e.setEmergency(emergency);
                    e.setWeight(weight);
                    e.setHeight(height);
                    e.setEducational(educational);
                    e.setExperience(experience);
                    e.setQualification(qualification);
                    boolean cvl = false;
                    if (civil.equals("1")) {
                        cvl = true;//married
                    }
                    e.setCivilStatus(cvl);

                    System.out.println("Changing Employee" + e.getId() + e.getDesignationId().getId());
                    String result = EmployeeDAOImpl.updateEmployee(e);
                    System.out.println("Changed");
                    if (result.equals(VertecConstants.SUCCESS)) {
                        request.getSession().removeAttribute("Success_Message");
                        request.getSession().setAttribute("Success_Message", "Successfully Added");
                        response.sendRedirect("Employee?action=ViewEmployee");

                    } else {
                        request.getSession().removeAttribute("Error_Message");
                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                        response.sendRedirect("Employee?action=EmployeeReg");

                    }
                    break;
                }
                case "deleteEmployee": {
                    String id = request.getParameter("eid");
                    String result = EmployeeDAOImpl.deleteEmployee(id);
                    response.getWriter().write(result);
                    break;
                }
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                 case "EmployeeTypeRegister": {
                String type = request.getParameter("employeeType").trim();
                String shortLeaves = request.getParameter("shortLeaves").trim();
                String monthlyLeaves = request.getParameter("monthlyLeaves").trim();
                String annual = request.getParameter("annual").trim();
                String epfetf2 = request.getParameter("epfetf").trim();
                
                boolean etfepf=false;
                if(epfetf2.equals("1")){
                    etfepf=true;
                }
                
                
                
                EmployeeType et = new EmployeeType();
                et.setType(type);
                et.setAnnualLeaves(Double.parseDouble(annual));
                et.setMonthlyLeaves(Double.parseDouble(monthlyLeaves));
                et.setMonthlyShortLeaves(Double.parseDouble(shortLeaves));
                et.setEtfEpf(etfepf);
                String result = EmployeeDAOImpl.saveEmployeeType(et);
                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().removeAttribute("Success_Message");
                    request.getSession().setAttribute("Success_Message", "Successfully Added");
                    response.sendRedirect("Employee?action=ViewEmployeeType");
                } else {
                    request.getSession().removeAttribute("Error_Message");
                    request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                    response.sendRedirect("Employee?action=ViewEmployeeType");
                }

                break;
            }

            case "ViewEmployeeType": {
                List<EmployeeType> et = EmployeeDAOImpl.getListOfEmployeeType();
                request.setAttribute("employeetype", et);
                requestDispatcher = request.getRequestDispatcher("app/employee/registerEmployeeType.jsp");
                requestDispatcher.forward(request, response);
                break;
            }

            case "UpdateEmployeeType": {
                String cId = request.getParameter("employeeTypeId").trim();
                int cuId = Integer.parseInt(cId);
                EmployeeType et = EmployeeDAOImpl.viewEmployeeType(cuId);
                request.setAttribute("employeeType", et);
                requestDispatcher = request.getRequestDispatcher("app/employee/viewEmployeeType.jsp");
                requestDispatcher.forward(request, response);
                break;
            }

            case "UpdateEmpType": {
                String id = request.getParameter("employeeTypeId").trim();
                String type = request.getParameter("employeeType").trim();
                String shortLeaves = request.getParameter("shortLeaves").trim();
                String monthlyLeaves = request.getParameter("monthlyLeaves").trim();
                String annual = request.getParameter("annual").trim();
                String epfetf2 = request.getParameter("epfetf").trim();
                
                boolean etfepf=false;
                if(epfetf2.equals("1")){
                    etfepf=true;
                }
                EmployeeType et = new EmployeeType();
                et.setType(type);
                et.setAnnualLeaves(Double.parseDouble(annual));
                et.setMonthlyLeaves(Double.parseDouble(monthlyLeaves));
                et.setMonthlyShortLeaves(Double.parseDouble(shortLeaves));
                et.setEtfEpf(etfepf);
                et.setId(Integer.parseInt(id));
                
                String result = EmployeeDAOImpl.updateEmployeeType(et);
                if (result.equals(VertecConstants.UPDATED)) {
                    request.getSession().removeAttribute("Success_Message");
                    request.getSession().setAttribute("Success_Message", "Successfully Updated");
                    response.sendRedirect("Employee?action=ViewEmployeeType");
                } else {
                    request.getSession().removeAttribute("Error_Message");
                    request.getSession().setAttribute("Error_Message", "Not Updated,Please Try again");
                    response.sendRedirect("Employee?action=ViewEmployeeType");
                }
                break;
            }

            case "RemoveEmployeeType": {
                String empId = request.getParameter("employeeTypeId").trim();
                int csId = Integer.parseInt(empId);
                String status = EmployeeDAOImpl.removeEmployeeType(csId);
                if (status.equals(VertecConstants.UPDATED)) {
                    request.getSession().removeAttribute("Success_Message");

                    request.getSession().setAttribute("Success_Message", "Successfully Deleted");
                    response.sendRedirect("Employee?action=ViewEmployeeType");
                } else {
                    request.getSession().removeAttribute("Error_Message");

                    request.getSession().setAttribute("Error_Message", "Not Deleted,Please Try again");
                    response.sendRedirect("Employee?action=ViewEmployeeType");
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
