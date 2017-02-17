/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.EmployeeDAOImpl;
import com.vertec.daoimpl.SalaryDAOImpl;
import com.vertec.hibe.model.Advance;
import com.vertec.hibe.model.BankAccounts;
import com.vertec.hibe.model.Cheque;
import com.vertec.hibe.model.Employee;
import com.vertec.hibe.model.HollyDay;
import com.vertec.hibe.model.Loan;
import com.vertec.hibe.model.PaymentType;
import com.vertec.hibe.model.PayrollDefault;
import com.vertec.hibe.model.Salary;
import com.vertec.hibe.model.SalaryAttendance;
import com.vertec.hibe.model.SalaryPayment;
import com.vertec.hibe.model.SalaryPaymentType;
import com.vertec.hibe.model.StaffLoan;
import com.vertec.hibe.model.SysUser;
import com.vertec.hibe.model.WorkingDays;
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

/**
 *
 * @author vertec-r
 */
@WebServlet(name = "SalaryController", urlPatterns = {"/SalaryController"})
public class SalaryController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final SalaryDAOImpl SalaryDAOImpl = new SalaryDAOImpl();
    private final EmployeeDAOImpl EmployeeDAOImpl = new EmployeeDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession httpSession = request.getSession();
            SysUser user1 = (SysUser) httpSession.getAttribute("user");
            RequestDispatcher requestDispatcher;

            switch (action) {
                case "ViewDefault": {
                    List<WorkingDays> wd = SalaryDAOImpl.getworkingDays();
                    request.setAttribute("wd", wd);
                    requestDispatcher = request.getRequestDispatcher("app/salary/DefaultData.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "UpdateWorkingDays": {
                    String monin = request.getParameter("monintime").trim();
                    String monout = request.getParameter("monouttime").trim();
                    String monlh = request.getParameter("monlhrs").trim();
                    String result1 = SalaryDAOImpl.updateWorkingDays("monday", monin, monout, monlh);

                    String tuein = request.getParameter("tueintime").trim();
                    String tueout = request.getParameter("tueouttime").trim();
                    String tuelh = request.getParameter("tuelhrs").trim();
                    String result2 = SalaryDAOImpl.updateWorkingDays("tuesday", tuein, tueout, tuelh);

                    String wedin = request.getParameter("wedintime").trim();
                    String wedout = request.getParameter("wedouttime").trim();
                    String wedlh = request.getParameter("wedlhrs").trim();
                    String result3 = SalaryDAOImpl.updateWorkingDays("wednesday", wedin, wedout, wedlh);

                    String thuin = request.getParameter("thuintime").trim();
                    String thuout = request.getParameter("thuouttime").trim();
                    String thulh = request.getParameter("thulhrs").trim();
                    String result4 = SalaryDAOImpl.updateWorkingDays("thursday", thuin, thuout, thulh);

                    String friin = request.getParameter("friintime").trim();
                    String friout = request.getParameter("friouttime").trim();
                    String frilh = request.getParameter("frilhrs").trim();
                    String result5 = SalaryDAOImpl.updateWorkingDays("friday", friin, friout, frilh);

                    String satin = request.getParameter("satintime").trim();
                    String satout = request.getParameter("satouttime").trim();
                    String satlh = request.getParameter("satlhrs").trim();
                    String result6 = SalaryDAOImpl.updateWorkingDays("saturday", satin, satout, satlh);

                    String sunin = request.getParameter("sunintime").trim();
                    String sunout = request.getParameter("sunouttime").trim();
                    String sunlh = request.getParameter("sunlhrs").trim();
                    String result7 = SalaryDAOImpl.updateWorkingDays("sunday", sunin, sunout, sunlh);

                    if (result1.equals(VertecConstants.SUCCESS) & result2.equals(VertecConstants.SUCCESS) & result3.equals(VertecConstants.SUCCESS) & result4.equals(VertecConstants.SUCCESS) & result5.equals(VertecConstants.SUCCESS) & result6.equals(VertecConstants.SUCCESS) & result7.equals(VertecConstants.SUCCESS)) {
                        request.getSession().removeAttribute("Success_Message");
                        request.getSession().setAttribute("Success_Message", "Successfully Added");
                        response.sendRedirect("Salary?action=ViewDefault");
                    } else {
                        request.getSession().removeAttribute("Error_Message");
                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                        response.sendRedirect("Salary?action=ViewDefault");
                    }
                    break;
                }
                case "ViewHolyday": {
                    List<HollyDay> hd = SalaryDAOImpl.getHollyDays();
                    request.setAttribute("hollyday", hd);
                    requestDispatcher = request.getRequestDispatcher("app/salary/addHolyDays.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "addHolyday": {
                    String name = request.getParameter("name").trim();
                    String date = request.getParameter("date").trim();

                    HollyDay hd = new HollyDay();
                    hd.setName(name);
                    SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date2 = null;
                    try {
                        date2 = DateFormat.parse(date);
                    } catch (ParseException ex) {
                        Logger.getLogger(SalaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    hd.setDate(date2);
                    String result = Save.Save(hd);

                    if (result.equals(VertecConstants.SUCCESS)) {
                        request.getSession().removeAttribute("Success_Message");
                        request.getSession().setAttribute("Success_Message", "Successfully Added");
                        response.sendRedirect("Salary?action=ViewHolyday");
                    } else {
                        request.getSession().removeAttribute("Error_Message");
                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                        response.sendRedirect("Salary?action=ViewHolyday");
                    }
                    break;
                }
                case "deleteHolyday": {
                    String id = request.getParameter("id");
                    String result = SalaryDAOImpl.deleteHolyday(id);
                    response.getWriter().write(result);
                    break;
                }
                case "getSalaryPayable": {
                    String id = request.getParameter("eid");
                    String result = SalaryDAOImpl.getSalaryPayable(Integer.parseInt(id));
                    response.getWriter().write(result);
                    break;
                }
                case "ViewDefaultPayments": {
                    List<PayrollDefault> wd = SalaryDAOImpl.getDefaultPayments();
                    request.setAttribute("dp", wd);
                    List<Employee> emp = EmployeeDAOImpl.getEmployees();
                    request.setAttribute("employee", emp);
                    requestDispatcher = request.getRequestDispatcher("app/salary/DefaultPayments.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "AddDefaultPayment": {
                    String emp = request.getParameter("employee").trim();
                    String ot = request.getParameter("ot").trim();
//                    String la = request.getParameter("la").trim();
                    String np = request.getParameter("np").trim();

                    PayrollDefault pd = new PayrollDefault();
                    pd.setLateHr(Double.parseDouble("0.0"));
                    pd.setNoPayHr(Double.parseDouble(np));
                    pd.setOtHr(Double.parseDouble(ot));
                    pd.setEmployeeId(new Employee(Integer.parseInt(emp)));
                    String result = SalaryDAOImpl.SaveDefault(pd);
                    if (result.equals(VertecConstants.SUCCESS)) {
                        request.getSession().removeAttribute("Success_Message");
                        request.getSession().setAttribute("Success_Message", "Successfully Added");
                        response.sendRedirect("Salary?action=ViewDefaultPayments");
                    } else {
                        request.getSession().removeAttribute("Error_Message");
                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                        response.sendRedirect("Salary?action=ViewDefaultPayments");
                    }
                    break;
                }
                case "ViewAdvancePayment": {
                    List<Employee> cuList = EmployeeDAOImpl.getEmployees();
                    request.setAttribute("employee", cuList);
                    List<BankAccounts> bankaccount = SalaryDAOImpl.getBankAccount();
                    request.setAttribute("bank", bankaccount);
                    List<Advance> advance = SalaryDAOImpl.getAdvance();
                    request.setAttribute("advance", advance);
                    requestDispatcher = request.getRequestDispatcher("app/salary/AdvancePayment.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "ViewSalaryPayment": {
                    List<Employee> cuList = EmployeeDAOImpl.getEmployees();
                    request.setAttribute("employee", cuList);
                    List<BankAccounts> bankaccount = SalaryDAOImpl.getBankAccount();
                    request.setAttribute("bank", bankaccount);
                    List<SalaryPayment> salary = SalaryDAOImpl.getSalaryPayments();
                    request.setAttribute("salary", salary);
                    requestDispatcher = request.getRequestDispatcher("app/salary/SalaryPayment.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "saveAdvance": {
                    String Employee = request.getParameter("e").trim();
                    String payType = request.getParameter("pt").trim();
                    String Bank = request.getParameter("bank").trim();
                    String amount = request.getParameter("amount").trim();
                    String cno = request.getParameter("cno").trim();
                    String cdate = request.getParameter("cdate").trim();
                    Cheque c = null;
                    if (payType.equals("3")) {
                        Date chdate = null;
                        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            chdate = DateFormat.parse(cdate);
                        } catch (ParseException ex) {
                            Logger.getLogger(SalaryController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        c = new Cheque();
                        c.setAmount(Double.parseDouble(amount));
                        c.setChequeNo(cno);
                        c.setChequeDate(chdate);
                        c.setIsValid(true);
                        c.setBankAccountsId(new BankAccounts(Integer.parseInt(Bank)));
                        Save.Save(c);
                    }
                    Advance a = new Advance();
                    a.setAmount(Double.parseDouble(amount));
                    a.setDue(Double.parseDouble(amount));
                    a.setDate(new Date());
                    a.setEmployeeId(new Employee(Integer.parseInt(Employee)));
                    a.setIsValid(true);
                    a.setSalaryPaymentTypeId(new SalaryPaymentType(Integer.parseInt(payType)));

                    if (payType.equals("3")) {
                        a.setChequeId(c);
                    } else {
                        a.setChequeId(c);
                    }
                    if (payType.equals("1")) {
                        a.setBankAccountsId(null);
                    } else {
                        a.setBankAccountsId(new BankAccounts(Integer.parseInt(Bank)));
                    }
                    String result = Save.Save(a);
                    response.getWriter().write(result);
                    break;
                }
                case "deleteAdvancePayment": {
                    String id = request.getParameter("id").trim();
                    String result = SalaryDAOImpl.deleteAdvancePayment(id);
                    response.getWriter().write(result);
                    break;
                }
                case "deletedefaultPayment": {
                    String id = request.getParameter("id").trim();
                    String result = SalaryDAOImpl.deleteDefaultPayment(id);
                    response.getWriter().write(result);
                    break;
                }
                case "CalSalary": {
                    List<Employee> cuList = EmployeeDAOImpl.getEmployees();
                    request.setAttribute("employee", cuList);
                    requestDispatcher = request.getRequestDispatcher("app/salary/CalculateSalary.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "toCalculate": {
                    String employee = request.getParameter("employee").trim();
                    String fromDate = request.getParameter("fromDate").trim();
                    String toDate = request.getParameter("toDate").trim();
                    List<Object> result = SalaryDAOImpl.SalaryCalculation(Integer.parseInt(employee), fromDate, toDate, user1);
                    request.setAttribute("array", result);
                    requestDispatcher = request.getRequestDispatcher("app/salary/Salary.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "SaveSalary": {
                    String eid = request.getParameter("eid").trim();
                    String from = request.getParameter("fromdate").trim();
                    String to = request.getParameter("todate").trim();
                    String salary = request.getParameter("totsalary").trim();
                    String allowance = request.getParameter("allowance").trim();
                    String deductions = request.getParameter("deduction").trim();
                    String ld = request.getParameter("Latededuction").trim();
                    String npd = request.getParameter("nopaydeduction").trim();
                    String otp = request.getParameter("otpay").trim();
                    String etf = request.getParameter("etf").trim();
                    String epf = request.getParameter("epf").trim();
                    String epfp = request.getParameter("epfpay").trim();
                    String adpaid = request.getParameter("advancepaid").trim();
                    String loan = request.getParameter("loanpaid").trim();
                    String salpaid = request.getParameter("salaryPayable").trim();
                    SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Salary s = new Salary();
                    Employee empOb = EmployeeDAOImpl.getEmployee(Integer.parseInt(eid));
                    s.setEmployeeId(empOb);
                    s.setDate(new Date());
                    s.setFromdate(DateFormat.parse(from));
                    s.setTodate(DateFormat.parse(to));
                    s.setTotalSalary(Double.parseDouble(salary));
                    s.setAllowance(Double.parseDouble(allowance));
                    s.setDeduction(Double.parseDouble(deductions));
                    s.setLateDeduct(Double.parseDouble(ld));
                    s.setNopayDeduct(Double.parseDouble(npd));
                    s.setOtpay(Double.parseDouble(otp));
                    s.setEpf(Double.parseDouble(epf));
                    s.setEtf(Double.parseDouble(etf));
                    s.setEpfPayable(Double.parseDouble(epfp));
                    s.setPaid(0.0);
                    s.setAdvancePaid(Double.parseDouble(adpaid));
                    s.setSalaryPayable(Double.parseDouble(salpaid));
                    s.setDue(Double.parseDouble(salary));
                    s.setLoanPaid(Double.parseDouble(loan));

                    String result1 = Save.Save(s);

                    String workinghours = request.getParameter("totworkingHours").trim();
                    String workedhours = request.getParameter("totworkedMin").trim();
                    String ea = request.getParameter("EA").trim();
                    String la = request.getParameter("LA").trim();
                    String el = request.getParameter("EL").trim();
                    String ll = request.getParameter("LL").trim();
                    String npl = request.getParameter("NPL").trim();
                    String pl = request.getParameter("PL").trim();
                    String otm = request.getParameter("OTM").trim();

                    SalaryAttendance sa = new SalaryAttendance();
                    sa.setWorkingMin(Integer.parseInt(workinghours));
                    sa.setWorkedMin(Integer.parseInt(workedhours));
                    sa.setEarlyArrival(Integer.parseInt(ea));
                    sa.setLateArrival(Integer.parseInt(la));
                    sa.setEarlyLeave(Integer.parseInt(el));
                    sa.setLateLeave(Integer.parseInt(ll));
                    sa.setNopayLeave(Integer.parseInt(npl));
                    sa.setPayLeave(Integer.parseInt(pl));
                    sa.setOtMin(Integer.parseInt(otm));
                    sa.setSalaryId(s);

                    String result2 = Save.Save(sa);

                    String result3 = SalaryDAOImpl.UpdateAdvanced(s);
                    String result4 = SalaryDAOImpl.UpdateLoan();

                    if (result1.equals(VertecConstants.SUCCESS) & result2.equals(VertecConstants.SUCCESS) & result3.equals(VertecConstants.SUCCESS) & result4.equals(VertecConstants.SUCCESS)) {
                        request.getSession().removeAttribute("Success_Message");
                        request.getSession().setAttribute("Success_Message", "Successfully Added");
                        response.sendRedirect("Salary?action=CalSalary");
                    } else {
                        request.getSession().removeAttribute("Error_Message");
                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                        response.sendRedirect("Salary?action=CalSalary");
                    }
                    break;
                }
                case "saveSalaryPayment": {
                    String id = request.getParameter("e").trim();
                    String payType = request.getParameter("pt").trim();
                    String Bank = request.getParameter("bank").trim();
                    String amount = request.getParameter("amount").trim();
                    String cno = request.getParameter("cno").trim();
                    String cdate = request.getParameter("cdate").trim();
                    Cheque c = null;
                    if (payType.equals("3")) {
                        Date chdate = null;
                        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            chdate = DateFormat.parse(cdate);
                        } catch (ParseException ex) {
                            Logger.getLogger(SalaryController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        c = new Cheque();
                        c.setAmount(Double.parseDouble(amount));
                        c.setChequeNo(cno);
                        c.setChequeDate(chdate);
                        c.setIsValid(true);
                        c.setBankAccountsId(new BankAccounts(Integer.parseInt(Bank)));
                        Save.Save(c);
                    }

                    String result = SalaryDAOImpl.UpdateSalary(Integer.parseInt(id), Double.parseDouble(amount), payType, Bank, c);

                    response.getWriter().write(result);
                    break;
                }
                case "staffloans": {
                    List<Employee> cuList = EmployeeDAOImpl.getEmployees();
                    request.setAttribute("employee", cuList);
                    List<StaffLoan> loan = SalaryDAOImpl.getLoans();
                    request.setAttribute("loan", loan);
                    requestDispatcher = request.getRequestDispatcher("app/StaffLoans/StaffLoan.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "SaveLoans": {
                    System.out.println("Calling");
                    String emp = request.getParameter("employee").trim();
                    String amount = request.getParameter("amount").trim();
                    String interest = request.getParameter("interest").trim();
                    String term = request.getParameter("terms").trim();
                    String itype = request.getParameter("itype").trim();
                    String iterm = request.getParameter("iterm").trim();

                    StaffLoan l = new StaffLoan();
                    l.setAmount(Double.parseDouble(amount));
                    l.setInterest(Double.parseDouble(interest));
                    l.setTerm(Integer.parseInt(term));
                    l.setDate(new Date());
                    l.setSysUserSysuserId(user1);
                    l.setPaid(0.0);
                    l.setPaidTerm(0);
                    l.setEmployeeId(EmployeeDAOImpl.getEmployee(Integer.parseInt(emp)));

                    if (itype.equals("0")) {
                        l.setInterestType(0);
                    } else {
                        l.setInterestType(1);
                    }

                    if (iterm.equals("0")) {
                        l.setInterestTerm(false);
                    } else {
                        l.setInterestTerm(true);
                    }

                    double inter = 0;
                    double tamount = Double.parseDouble(amount);
                    if (itype.equals("0") && iterm.equals("0")) {
                        inter += (tamount * (Double.parseDouble(interest) / 100)) * Integer.parseInt(term);
                        tamount += inter;
                    } else if (itype.equals("1") && iterm.equals("0")) {
                        for (int i = 0; i < Integer.parseInt(term); i++) {
                            inter += tamount * (Double.parseDouble(interest) / 100);
                            tamount += inter;
                            inter = 0;
                        }
                    } else if (itype.equals("0") && iterm.equals("1")) {
                        inter += (tamount * ((Double.parseDouble(interest) / 100)) * (Integer.parseInt(term) / 12));
                        tamount += inter;
                    } else if (itype.equals("1") && iterm.equals("1")) {
                        for (int i = 0; i < (Integer.parseInt(term) / 12); i++) {
                            inter += tamount * ((Double.parseDouble(interest) / 100));
                            tamount += inter;
                            inter = 0;
                        }
                        double duem = (Integer.parseInt(term) % 12);
                        inter += (tamount * ((Double.parseDouble(interest) / 100.0)) * (duem / 12.0));
                        tamount += inter;
                    }
                    l.setTotal(tamount);
                    l.setBalance(tamount);
                    l.setMonthlyPayment(tamount / Integer.parseInt(term));
                    String result1 = Save.Save(l);
                    if (result1.equals(VertecConstants.SUCCESS)) {
                        request.getSession().removeAttribute("Success_Message");
                        request.getSession().setAttribute("Success_Message", "Successfully Added");
                        response.sendRedirect("Salary?action=staffloans");
                    } else {
                        request.getSession().removeAttribute("Error_Message");
                        request.getSession().setAttribute("Error_Message", "Not Added,Please Try again");
                        response.sendRedirect("Salary?action=staffloans");
                    }
                    break;
                }
                case "viewUpdateLoan": {
                    String loanid = request.getParameter("id").trim();
                    List<Employee> cuList = EmployeeDAOImpl.getEmployees();
                    request.setAttribute("employee", cuList);
                    StaffLoan loan = SalaryDAOImpl.getLoanById(Integer.parseInt(loanid));
                    request.setAttribute("loan", loan);
                    requestDispatcher = request.getRequestDispatcher("app/StaffLoans/StaffLoanUpdate.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
            }

        } catch (ParseException ex) {
            Logger.getLogger(SalaryController.class.getName()).log(Level.SEVERE, null, ex);
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
