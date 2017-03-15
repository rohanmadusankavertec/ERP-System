/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.AccountDAOImpl;
import com.vertec.daoimpl.AttendanceDAOImpl;
import com.vertec.daoimpl.BankDAOImpl;
import com.vertec.daoimpl.DashboardDAOImpl;
import com.vertec.daoimpl.EmployeeDAOImpl;
import com.vertec.daoimpl.ReportDAOImpl;
import com.vertec.daoimpl.SalaryDAOImpl;
import com.vertec.daoimpl.StockDAOImpl;
import com.vertec.hibe.model.Account;
import com.vertec.hibe.model.Advance;
import com.vertec.hibe.model.AllowanceDeduction;
import com.vertec.hibe.model.Attendance;
import com.vertec.hibe.model.BalanceSheetData;
import com.vertec.hibe.model.BankAccounts;
import com.vertec.hibe.model.Bin;
import com.vertec.hibe.model.BranchProductmaster;
import com.vertec.hibe.model.BudgetPlan;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.DepreciationData;
import com.vertec.hibe.model.Employee;
import com.vertec.hibe.model.HollyDay;
import com.vertec.hibe.model.Leaves;
import com.vertec.hibe.model.Loan;
import com.vertec.hibe.model.Payment;
import com.vertec.hibe.model.Salary;
import com.vertec.hibe.model.SalaryPayment;
import com.vertec.hibe.model.StaffLoan;
import com.vertec.hibe.model.SysUser;
import com.vertec.hibe.model.SystemData;
import com.vertec.hibe.model.Transaction;
import com.vertec.util.Save;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
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
 * @author Java-Dev-Ruchira
 */
@WebServlet(name = "ReportController", urlPatterns = {"/ReportController"})
public class ReportController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final ReportDAOImpl reportdao = new ReportDAOImpl();
    private final ReportDAOImpl reportDAOImpl = new ReportDAOImpl();
    private final DashboardDAOImpl dashboarddao = new DashboardDAOImpl();
    private final StockDAOImpl stockDAOImpl = new StockDAOImpl();

    private final AttendanceDAOImpl AttendanceDAOImpl = new AttendanceDAOImpl();
    private final EmployeeDAOImpl EmployeeDAOImpl = new EmployeeDAOImpl();
    private final BankDAOImpl bankDAOImpl = new BankDAOImpl();
    private final SalaryDAOImpl salaryDAOImpl = new SalaryDAOImpl();
    private final AccountDAOImpl accountDAOImpl = new AccountDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String action = request.getParameter("action");
            HttpSession httpSession = request.getSession();
            SysUser user1 = (SysUser) httpSession.getAttribute("user");
            Company company = (Company) httpSession.getAttribute("company");
            RequestDispatcher requestDispatcher;
            boolean isValidated = true;
            String path2 = getServletContext().getInitParameter("pdftemp");
            String path1 = "\\resources\\pdf\\";
            String path = getServletContext().getRealPath(path1) + "\\";

            switch (action) {
                /**
                 * Load Asset Page
                 */
                case "ViewAssets": {
                    requestDispatcher = request.getRequestDispatcher("/app/reports/toAssets.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * Load journal Entries Page
                 */
                case "loadJournalEntries": {
                    requestDispatcher = request.getRequestDispatcher("/app/reports/JournalEntries.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * Load journal Entry Report
                 */
                case "loadJournalEntriesPage": {
                    String frdate = request.getParameter("fromDay").trim();
                    String todate = request.getParameter("toDate").trim();
                    try {
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                        Date fdate = sd.parse(frdate);
                        Date toodate = sd.parse(todate);
                        List<Transaction> tList = reportdao.loadAllOfAccountByPayType(fdate, toodate, company);
                        request.setAttribute("transaction", tList);
                        request.setAttribute("date", new Date().toString());
                        request.setAttribute("from", frdate);
                        request.setAttribute("to", todate);
                        requestDispatcher = request.getRequestDispatcher("/app/reports/ReportJounal.jsp");
                        requestDispatcher.forward(request, response);
                    } catch (Exception e) {
                    }
                    break;
                }
                /**
                 * Print Asset report
                 */
                case "PrintAssetReport": {
                    String acc = request.getParameter("acc");
                    String from = request.getParameter("from");
                    String to = request.getParameter("to");

                    System.out.println(acc);
                    System.out.println(from);
                    System.out.println(to);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date fromDate = null;
                    Date toDate = null;
                    try {
                        fromDate = sdf.parse(from);
                        toDate = sdf.parse(to);
                    } catch (ParseException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    List<Transaction> credit = reportdao.loadAssetCreditSide(new Account(Integer.parseInt(acc)), fromDate, toDate, company);
                    List<Transaction> debit = reportdao.loadAssetDebitSide(new Account(Integer.parseInt(acc)), fromDate, toDate, company);
                    request.setAttribute("accname", reportdao.getAccountById(Integer.parseInt(acc)).getName());
                    request.setAttribute("from", from);
                    request.setAttribute("to", to);
                    request.setAttribute("credit", credit);
                    request.setAttribute("debit", debit);
                    request.setAttribute("balanceback", reportdao.loadBalanceBackward(new Account(Integer.parseInt(acc)), fromDate, company));
                    request.setAttribute("openbal", reportdao.getAccountOpenBalance(Integer.parseInt(acc)));
                    request.setAttribute("debit", debit);
                    request.setAttribute("date", new Date().toString());
                    requestDispatcher = request.getRequestDispatcher("/app/reports/PrintAsset.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * load Profit and loss page
                 */
                case "loadProfitLossPage": {
                    requestDispatcher = request.getRequestDispatcher("/app/reports/ProfitLoss.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * print Profit and loss page
                 */
                case "listOfAcctByCash": {
                    String fdate = request.getParameter("fromDay").trim();
                    String todate = request.getParameter("toDay").trim();
                    try {
                        List<Account> aList = reportdao.loadAllOfAccountByCash1(company);
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                        Date fromDate = sd.parse(fdate);
                        Date toDate = sd.parse(todate);
                        reportdao.CalculateDepreciation(fromDate, toDate, company);
                        List<String[]> arr = reportdao.loadAccountsForProfit(aList, fromDate, toDate);
                        List<DepreciationData> dep = reportdao.getDepreciationData(fromDate, toDate, company);
                        request.setAttribute("arr", arr);
                        request.setAttribute("dep", dep);
                        request.setAttribute("date", new Date());
                        request.setAttribute("from", fdate);
                        request.setAttribute("to", todate);
                        requestDispatcher = request.getRequestDispatcher("/app/reports/ReportProfitLoss.jsp");
                        requestDispatcher.forward(request, response);
                    } catch (ParseException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                case "IncomeStatement": { // get the details of cash account
                    String fdate = request.getParameter("fromDay").trim();
                    String todate = request.getParameter("toDay").trim();
                    try {
                        List<Account> aList = reportdao.loadAllOfAccountByCash1(company);
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                        Date fromDate = sd.parse(fdate);
                        Date toDate = sd.parse(todate);
                        reportdao.CalculateDepreciation(fromDate, toDate, company);
                        List<String[]> arr = reportdao.loadAccountsForProfit(aList, fromDate, toDate);
                        List<DepreciationData> dep = reportdao.getDepreciationData(fromDate, toDate, company);
                        request.setAttribute("arr", arr);
                        request.setAttribute("dep", dep);
                        request.setAttribute("date", new Date());
                        request.setAttribute("from", fdate);
                        request.setAttribute("to", todate);
                        requestDispatcher = request.getRequestDispatcher("/app/reports/ReportIncomeStatement.jsp");
                        requestDispatcher.forward(request, response);
                    } catch (ParseException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                /**
                 * Load create trial balance page
                 */
                case "ViewCreateTrialBalance": {
                    requestDispatcher = request.getRequestDispatcher("/app/reports/toTrialBalance.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * Load create balance sheet page
                 */
                case "ViewCreateBalanceSheet": {
                    requestDispatcher = request.getRequestDispatcher("/app/reports/toBalanceSheet.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * Print balance sheet
                 */
                case "PrintBalanceSheetReport": {
                    String from = request.getParameter("from");
                    String to = request.getParameter("to");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date fromDate = null;
                    Date toDate = null;
                    try {
                        fromDate = sdf.parse(from);
                        toDate = sdf.parse(to);
                    } catch (ParseException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    reportdao.CalculateDepreciation(fromDate, toDate, company);
                    List<BalanceSheetData> starr = reportdao.getBalanceSheetData(fromDate, toDate, company);
                    List<DepreciationData> dep = reportdao.getDepreciationData(fromDate, toDate, company);

                    List<Account> aList = reportdao.loadAllOfAccountByCash1(company);
                    List<String[]> arr = reportdao.loadAccountsForProfit(aList, fromDate, toDate);

                    Double debit = 0.0;
                    Double credit = 0.0;
                    for (String[] a : arr) {

                        if (Double.parseDouble(a[1]) < 0) {
                            credit += ((-1) * Double.parseDouble(a[1]));
                        } else {
                            debit += Double.parseDouble(a[1]);
                        }
                    }
                    for (DepreciationData d : dep) {
                        debit += d.getDepreciation().getAmount() - d.getAmount() - d.getBeforedep();
                    }

                    request.setAttribute("data", starr);
                    request.setAttribute("dep", dep);
                    request.setAttribute("from", from);
                    request.setAttribute("to", to);
                    request.setAttribute("date", new Date().toString());
                    request.setAttribute("profit", (debit - credit));
                    requestDispatcher = request.getRequestDispatcher("/app/reports/PrintBalanceSheet.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * Print trial balance report
                 */
                case "PrintTrialBalanceReport": {
                    String from = request.getParameter("from");
                    String to = request.getParameter("to");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date fromDate = null;
                    Date toDate = null;
                    try {
                        fromDate = sdf.parse(from);
                        toDate = sdf.parse(to);
                    } catch (ParseException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    List<String[]> starr = reportdao.loadTrialbalance(fromDate, toDate, company);

                    request.setAttribute("data", starr);
                    request.setAttribute("from", from);
                    request.setAttribute("to", to);
                    request.setAttribute("date", new Date().toString());
                    requestDispatcher = request.getRequestDispatcher("/app/reports/PrintTrialBalance.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * Load ledger account report
                 */
                case "toLedgerAccounts": {
                    List<Account> acc = reportdao.getAccountsByCompany(company);
                    request.setAttribute("account", acc);
                    requestDispatcher = request.getRequestDispatcher("/app/reports/toLedgerAccounts.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * Print Ledger account
                 */
                case "PrintLedgerAccounts": {
                    String acc = request.getParameter("acc");
                    String from = request.getParameter("from");
                    String to = request.getParameter("to");

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date fromDate = null;
                    Date toDate = null;
                    try {
                        fromDate = sdf.parse(from);
                        toDate = sdf.parse(to);
                    } catch (ParseException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    List<Account> account = reportdao.getAccountsByCompany(company, acc);
                    request.setAttribute("account", account);
                    request.setAttribute("from", fromDate);
                    request.setAttribute("to", toDate);
                    requestDispatcher = request.getRequestDispatcher("/app/reports/PrintLedgerAccounts.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * Get details for dashboard
                 */
                case "dashboard": {
                    JSONObject jOB = new JSONObject();
                    JSONArray jar1 = new JSONArray();
                    JSONObject job1 = new JSONObject();
                    job1.put("liblitity", dashboarddao.getLiabilities(company));
                    job1.put("debtors", dashboarddao.getDebtorsAmount(company));
                    job1.put("income", dashboarddao.getIncomeAmount(company));
                    job1.put("expense", dashboarddao.getExpences(company));
                    job1.put("account", dashboarddao.getAccount(company));
//                    job1.put("incompleted", dashboarddao.getincompletedProject());
//                    job1.put("hold", dashboarddao.getHoldProject());
//                    job1.put("ongoing", dashboarddao.getOngoingProject());

                    jar1.add(job1);
                    jOB.put("des", jar1);
                    response.getWriter().write(jOB.toString());
                    break;
                }
                /**
                 * Load bin card page
                 */
                case "ToCreateBIN": {
                    List<Object[]> pList = stockDAOImpl.loadProductFromBranchStock(user1.getBranchBranchId().getBranchId());
                    request.setAttribute("pList", pList);
                    request.setAttribute("branch", user1.getBranchBranchId());
                    requestDispatcher = request.getRequestDispatcher("/app/report/SearchBinCard.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                /**
                 * Print bin card
                 */
                case "ToViewBIN": {
                    System.out.println("Creating...");

                    String pmid = request.getParameter("bpmId");

                    System.out.println("Branch Product master is..." + pmid);

                    BranchProductmaster bpm = stockDAOImpl.viewBranchPM(Integer.parseInt(pmid));

                    System.out.println("Product " + bpm.getProductMasterId().getProductId().getProductName());

                    List<Bin> binList = reportDAOImpl.GetBin(bpm, user1.getBranchBranchId());

                    request.setAttribute("bpmid", bpm);
                    request.setAttribute("binList", binList);
                    requestDispatcher = request.getRequestDispatcher("/app/report/BinCard.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }

                //Open Search employee Page
                case "SearchEmployee": {
                    System.out.println("Calling");
                    List<Employee> e = EmployeeDAOImpl.getEmployees(company);
                    request.setAttribute("employee", e);
                    requestDispatcher = request.getRequestDispatcher("app/report/SearchEmployee.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                //View Employee Profile
                case "ViewProfile": {
                    String id = request.getParameter("employee");
                    Employee e = EmployeeDAOImpl.getEmployee(Integer.parseInt(id));
                    System.out.println(e.getImage());
                    String Imagepath = getServletContext().getRealPath(e.getImage());
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>?" + Imagepath);
                    request.setAttribute("employee", e);
                    requestDispatcher = request.getRequestDispatcher("app/report/EmployeeProfile.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                // View Employee Report
                case "Employees": {
                    List<Employee> e = EmployeeDAOImpl.getEmployees(company);
                    request.setAttribute("employee", e);
                    requestDispatcher = request.getRequestDispatcher("app/report/EmployeeReport.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                // Open Leaves Search Page
                case "Leaves": {
                    List<Employee> e = EmployeeDAOImpl.getEmployees(company);
                    request.setAttribute("employee", e);
                    requestDispatcher = request.getRequestDispatcher("app/report/SearchLeaves.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                // Open Leaves Report
                case "LeaveReport": {
                    try {
                        String type = request.getParameter("type");
                        String employee = request.getParameter("emp");
                        String from = request.getParameter("from");
                        String to = request.getParameter("to");

                        List<Leaves> l = null;

                        if (type.equals("0")) {
                            l = AttendanceDAOImpl.getLeave();
                        } else if (type.equals("1")) {
                            l = AttendanceDAOImpl.getLeaveReportByEmployee(Integer.parseInt(employee));
                        } else if (type.equals("2")) {
                            SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");

                            l = AttendanceDAOImpl.getLeaveReportByDateRange(DateFormat.parse(from), DateFormat.parse(to));

                        } else if (type.equals("3")) {
                            SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            l = AttendanceDAOImpl.getLeaveReportByDate(DateFormat.parse(from));
                        }

                        request.setAttribute("leaves", l);
                        requestDispatcher = request.getRequestDispatcher("app/report/LeaveReport.jsp");
                        requestDispatcher.forward(request, response);
                        break;
                    } catch (ParseException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // Open Search Attendance Page
                case "Attendance": {
                    List<Employee> e = EmployeeDAOImpl.getEmployees(company);
                    request.setAttribute("employee", e);
                    requestDispatcher = request.getRequestDispatcher("app/report/SearchAttendance.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                // View Attendance Report
                case "AttendanceReport": {
                    try {
                        String type = request.getParameter("type");
                        String employee = request.getParameter("emp");
                        String from = request.getParameter("from");
                        String to = request.getParameter("to");

                        List<Attendance> l = null;

                        if (type.equals("0")) {
                            l = AttendanceDAOImpl.getAttendance();
                        } else if (type.equals("1")) {
                            l = AttendanceDAOImpl.getattendanceByEmployee(Integer.parseInt(employee));
                        } else if (type.equals("2")) {
                            SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");

                            l = AttendanceDAOImpl.getattendanceByDateRange(DateFormat.parse(from), DateFormat.parse(to));

                        } else if (type.equals("3")) {
                            SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            l = AttendanceDAOImpl.getattendanceByDate(DateFormat.parse(from));
                        }

                        request.setAttribute("leaves", l);
                        requestDispatcher = request.getRequestDispatcher("app/report/AttendanceReport.jsp");
                        requestDispatcher.forward(request, response);
                        break;
                    } catch (ParseException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //View Bank Account Report
                case "Bank": {
                    List<BankAccounts> e = bankDAOImpl.loadAllBankAccount();
                    request.setAttribute("bankaccounts", e);
                    requestDispatcher = request.getRequestDispatcher("app/report/BankAccounts.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                //View Allowance And deduction Report
                case "Allowance": {
                    List<AllowanceDeduction> e = salaryDAOImpl.getAllowanceDeduction();
                    request.setAttribute("allowance", e);
                    requestDispatcher = request.getRequestDispatcher("app/report/AllowanceDeduction.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                //View Staff Loan Report
                case "staffloans": {
                    System.out.println("Calling");
                    List<StaffLoan> loan = salaryDAOImpl.getLoans();
                    request.setAttribute("loan", loan);
                    requestDispatcher = request.getRequestDispatcher("app/report/StaffLoans.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                //View holiday Report
                case "holiday": {
                    List<HollyDay> hd = salaryDAOImpl.getHollyDays();
                    request.setAttribute("holiday", hd);
                    requestDispatcher = request.getRequestDispatcher("app/report/Holidays.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                //View Advance Payment Report
                case "advanse": {
                    List<Advance> ad = salaryDAOImpl.getAdvance();
                    request.setAttribute("advance", ad);
                    requestDispatcher = request.getRequestDispatcher("app/report/AdvancePayments.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                //View Salary Report
                case "salary": {
                    List<Salary> ad = salaryDAOImpl.getSalary();
                    request.setAttribute("salary", ad);
                    requestDispatcher = request.getRequestDispatcher("app/report/Salary.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                //View Salary Payment Report
                case "salarypayment": {
                    List<SalaryPayment> ad = salaryDAOImpl.getSalaryPayments();
                    request.setAttribute("sp", ad);
                    requestDispatcher = request.getRequestDispatcher("app/report/SalaryPayment.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                //View EPF Payable REport
                case "epf": {
                    List<Salary> ad = salaryDAOImpl.getSalary();
                    request.setAttribute("salary", ad);
                    requestDispatcher = request.getRequestDispatcher("app/report/EPFPayable.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                //View ETF Payable Report
                case "etf": {
                    List<Salary> ad = salaryDAOImpl.getSalary();
                    request.setAttribute("salary", ad);
                    requestDispatcher = request.getRequestDispatcher("app/report/ETFPayable.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                // View PaySlip Report
                case "payslip": {
                    List<Employee> e = EmployeeDAOImpl.getEmployees(company);
                    request.setAttribute("employee", e);
                    requestDispatcher = request.getRequestDispatcher("app/report/SearchPayslip.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                // Get Salary period Json Object
                case "getSalaryperiod": {
                    String id = request.getParameter("id");
                    List<Salary> des = salaryDAOImpl.getSalaryByEmployee(Integer.parseInt(id));
                    JSONObject jOB = new JSONObject();
                    JSONArray jar1 = new JSONArray();
                    JSONObject job1 = null;
                    for (Salary d : des) {
                        job1 = new JSONObject();
                        job1.put("id", d.getId());
                        String from = d.getFromdate().toString().replace("-", "/");
                        String to = d.getTodate().toString().replace("-", "/");
                        job1.put("from", from);
                        job1.put("to", to);
                        jar1.add(job1);
                    }
                    jOB.put("des", jar1);
                    response.getWriter().write(jOB.toString());
                    break;
                }
                //View payslip Report

                case "Viewpayslip": {
                    String salary = request.getParameter("salary");
                    Salary s = salaryDAOImpl.getSalaryByID(Integer.parseInt(salary));
                    request.setAttribute("salary", s);
                    requestDispatcher = request.getRequestDispatcher("app/report/PaySlip.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                //Load create budget plan page
                case "CreateBudgetPlan": {
                    List<Account> a = accountDAOImpl.viewAllOfAccount();
                    request.setAttribute("account", a);
                    requestDispatcher = request.getRequestDispatcher("app/reports/CreateBudgetPlan.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "LoadCCPRpage": {

                    requestDispatcher = request.getRequestDispatcher("app/reports/CreditCardPayment.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                }
                case "CCPReport": {
                    String fdate = request.getParameter("from").trim();
                    String tdate = request.getParameter("to").trim();
                    System.out.println("........."+fdate);
                    System.out.println("........."+tdate);
//                    Date fd = null;
//                    Date td = null;
                    try {
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                        Date fd = sd.parse(fdate);
                        Date td = sd.parse(tdate);


                    

                    List<Payment> pList = reportdao.getCreditCardPayment(fd, td);
                    SystemData sysData = reportdao.getCreditCardRate();
                    request.setAttribute("plist", pList);
                    System.out.println(".........date...."+fd);
                    request.setAttribute("fd", fd);
                    System.out.println(".........date...."+td);
                    request.setAttribute("td", td);
                    request.setAttribute("sysData", sysData);
                    request.setAttribute("company", company);
                    requestDispatcher = request.getRequestDispatcher("app/reports/CreditCardReport.jsp");
                    requestDispatcher.forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }

                // Get Budget Plan Json Object
                case "getBudgetPlan": {
                    System.out.println("Calling here");
                    String acc = request.getParameter("account");
                    String year = request.getParameter("year");
                    List<BudgetPlan> des = reportDAOImpl.getBudgetPlan(acc, year, company);
                    JSONObject jOB = new JSONObject();
                    JSONArray jar1 = new JSONArray();
                    JSONObject job1 = null;
                    for (BudgetPlan d : des) {
                        System.out.println(d.getMonth() + "  " + d.getValue());
                        job1 = new JSONObject();
                        job1.put("month", d.getMonth());
                        job1.put("value", d.getValue());
                        jar1.add(job1);
                    }
                    jOB.put("bp", jar1);
                    response.getWriter().write(jOB.toString());
                    break;
                }
                // Save Budget Plan 
                case "SaveBudgetPlan": {
                    System.out.println("Calling Save here");
                    String acc = request.getParameter("account");
                    String year = request.getParameter("year");
                    String month = request.getParameter("month");
                    String value = request.getParameter("value");

                    BudgetPlan b = new BudgetPlan();
                    b.setAccountId(new Account(Integer.parseInt(acc)));
                    b.setCompanyId(company);
                    b.setMonth(month);
                    b.setYear(year);
                    b.setValue(Double.parseDouble(value));

                    String s = new Save().Save(b);
                    response.getWriter().write(s);
                    break;
                }
                case "UpdateBudgetPlan": {
                    System.out.println("Calling Update here");
                    String acc = request.getParameter("account");
                    String year = request.getParameter("year");
                    String month = request.getParameter("month");
                    String value = request.getParameter("value");

                    BudgetPlan b = new BudgetPlan();
                    b.setAccountId(new Account(Integer.parseInt(acc)));
                    b.setCompanyId(company);
                    b.setMonth(month);
                    b.setYear(year);
                    b.setValue(Double.parseDouble(value));

                    String s = reportDAOImpl.updateBudgetPlan(b, company);
                    response.getWriter().write(s);
                    break;
                }
                case "loadBPR": {
                    List<Account> account = reportdao.getAccountsByCompany(company);
                    request.setAttribute("account", account);
                    requestDispatcher = request.getRequestDispatcher("app/reports/toBudgetPlan.jsp");
                    requestDispatcher.forward(request, response);
                }
                case "BudgetPlanReport": {
                    int year = Year.now().getValue();
                    int year1 = year-1;
                    int year2 = year-2;
                    String y1 = Integer.toString(year);
                    String y2 = Integer.toString(year1);
                    String y3 = Integer.toString(year2);
                    String acc = request.getParameter("accountId").trim();
                    String[] arr = acc.split("-");
                    String name = arr[1];
                    String accid = arr[0];
                    request.setAttribute("name", name);
                    request.setAttribute("accid", accid);
                    request.setAttribute("company", company);
//                    List<Object> valueList = reportdao.getBudgetOfYear(company, Integer.parseInt(accid));
//                    for (Object list : valueList) {
//                        System.out.println(list[0].toString());
//                    }
//                    for(int i=0; i<valueList.size();i++){
//                        System.out.println("val....."+valueList.get(i));
//                    }
                    double tot1 = reportdao.getBudgetOfYear(company, Integer.parseInt(accid),y1);
                    double tot2 = reportdao.getBudgetOfYear(company, Integer.parseInt(accid), y2);
                    double tot3 = reportdao.getBudgetOfYear(company, Integer.parseInt(accid), y3);
                    System.out.println("year  :"+year+"total value-------:"+tot1);
                    System.out.println("year  :"+year1+"total value-------:"+tot2);
                    System.out.println("year  :"+year2+"total value-------:"+tot3);
                    request.setAttribute("tot1", tot1);
                    request.setAttribute("tot2", tot2);
                    request.setAttribute("tot3", tot3);
                    requestDispatcher = request.getRequestDispatcher("app/reports/BudgetPlanReport.jsp");
                    requestDispatcher.forward(request, response);
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
