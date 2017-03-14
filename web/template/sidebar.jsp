<%-- 
    Document   : sidebar
    Created on : Mar 21, 2016, 2:25:42 PM
    Author     : User
--%>

<%@page import="com.vertec.util.CheckAuth"%>
<%@page import="com.vertec.hibe.model.UserGroupPrivilegeItem"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@page import="com.vertec.hibe.model.SysUser"%>


<c:set var="context" value="${pageContext.request.contextPath}" />

<%    SysUser user = (SysUser) session.getAttribute("user");
    String sidebar = (String) session.getAttribute("sidebar");
    CheckAuth ca = new CheckAuth();
    int group = user.getUserGroupId().getUserGroupId();
%>

<div class="col-md-3 left_col">
    <div class="left_col scroll-view">
        <div class="navbar nav_title" style="border: 0;">
            <a href="${context}/dashboard.jsp" class="site_title">
                <span>ERP <small>System</small></span></a>
        </div>
        <div class="clearfix"></div>
        <br/>

        <!-- sidebar menu -->
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">

            <div class="menu_section">
                <h3>General</h3>
                <ul class="nav side-menu">
                    <li><a href="${context}/dashboard.jsp"><i class="fa fa-home"></i> DashBoard </a>
                    </li>
                    <%if (sidebar != null) {%>
                    <%if (sidebar.equals("inventory")) {%>
                    <li><a><i class="fa fa-money"></i> Invoice <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("CREATE_INVOICE", group) != null) {%>
                            <li><a href="${context}/Invoice?action=ToCreateInvoice">Create Invoice</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("MAINTAIN_INVOICE", group) != null) {%>
                            <li><a href="${context}/Invoice?action=MaintainInvoice">Maintain Invoice</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("CHECK_INVOICE", group) != null) {%>
                            <li><a href="${context}/app/invoice/selectDate.jsp">Check Invoices</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("BRANCH_WISE_INVOICE", group) != null) {%>
                            <li><a href="${context}/app/invoice/FilterInvoice.jsp">View Branch wise Invoices</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("DISPATCH_NOTE", group) != null) {%>
                            <li><a href="${context}/Dispatch?action=CreateDispatch">Dispatch Note</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("MANAGE_DELETED_INVOICE", group) != null) {%>
                            <li><a href="${context}/app/invoice/ApproveDelete.jsp">Manage Deleted Invoice</a></li>
                                <%}%>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-users"></i>Supplier Management<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("ADD_SUPPLIER", group) != null) {%>
                            <li><a href="${context}/app/supplier/registerSupplier.jsp">Add New Supplier</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("MANAGE_SUPPLIER", group) != null) {%>
                            <li><a href="${context}/Supplier?action=ViewSupplier">Manage Supplier</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ADD_SUPPLIER_GROUP", group) != null) {%>
                            <li><a href="${context}/Supplier?action=LoadSupplierGroup">Add Supplier Group</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("VIEW_SUPPLIER_GROUP", group) != null) {%>
                            <li><a href="${context}/Supplier?action=LoadmanageGroup">Manage Supplier Group</a></li>
                                <%}%>

                        </ul>
                    </li>
                    <li><a><i class="fa fa-product-hunt"></i>Product Management<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("MANAGE_PRODUCT_CATEGORY", group) != null) {%>
                            <li><a href="${context}/Product?action=ViewProductCategories">Manage Product Category</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("MANAGE_PRODUCT", group) != null) {%>
                            <li><a href="${context}/Product?action=ViewProducts">Manage Products</a></li>
                                <%}%>

                        </ul>
                    </li>
                    <li><a><i class="fa fa-file-text-o"></i>PRN<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("ADD_PRN", group) != null) {%>
                            <li><a href="${context}/PRN?action=toPRN">Add Purchase Requisition Note</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("VIEW_PRN", group) != null) {%>
                            <li><a href="${context}/app/pr_note/FilterPRN.jsp">View Purchase Requisition Note</a></li>
                                <%}%>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-file-text-o"></i>Purchase Orders<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("ADD_PO", group) != null) {%>
                            <li><a href="${context}/app/po/CreatePO.jsp">Add Purchase Order</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("VIEW_PO", group) != null) {%>
                            <li><a href="${context}/app/po/FilterPO.jsp">View Purchase Orders</a></li>
                                <%}%>
                        </ul>
                    </li>

                    <li><a><i class="fa fa-file-text-o"></i>GRN<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("ADD_GRN", group) != null) {%>
                            <li><a href="${context}/GRN?action=createGRN">Add GRN</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("VIEW_GRN", group) != null) {%>
                            <li><a href="${context}/app/grn/toViewGRN.jsp">View GRN</a></li>
                                <%}%>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-shopping-cart"></i>Stock<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("VIEW_STOCK", group) != null) {%>
                            <li><a href="${context}/app/stock/Searchbranchstock.jsp">Stock</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("STOCK_ADJUSTMENT", group) != null) {%>
                            <li><a href="${context}/Stock?action=Adjustment">Adjustment</a></li>
                                <%}%>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-file-text-o"></i>GTN<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("ADD_GTN", group) != null) {%>
                            <li><a href="${context}/GTN?action=loadBranch">Add GTN</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("VIEW_SENT_GTN", group) != null) {%>
                            <li><a href="${context}/GTN?action=viewGTNInfo">View Sent GTN</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("VIEW_RECEIVED_GTN", group) != null) {%>
                            <li><a href="${context}/GTN?action=viewGTNInfoByToBranch">View Received GTN</a></li>
                                <%}%>

                        </ul>
                    </li>

                    <li><a><i class="fa fa-file-text-o"></i>MRN<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <li><a href="${context}/MRN?action=toMRN">Add Material Requisition Note</a></li>
                            <li><a href="${context}/app/mrn/FilterMRN.jsp">View Material Requisition Note</a></li>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-file-text-o"></i>GIN<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <li><a href="${context}/GIN?action=createGIN">Add GIN</a></li>
                            <li><a href="${context}/app/gin/toViewGIN.jsp">View GIN</a></li>
                        </ul>
                    </li>

                    <li><a><i class="fa fa-money"></i> Payment Management <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">

                            <%if (ca.checkUserAuth("DO_PAYMENT", group) != null) {%>
                            <li><a href="${context}/Payment?action=ToDoPayment">Do Payment</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("CLEAR_CHEQUE", group) != null) {%>
                            <li><a href="${context}/Payment?action=ClearCheque">Clear Cheque</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("PAYMENT_DETAILS", group) != null) {%>
                            <li><a href="${context}/app/payment/checkPayment.jsp">Payment Details</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("INVOICE_MULTIPAYMENT", group) != null) {%>
                            <li><a href="${context}/Payment?action=ToDoMultiPayment">MultPayment</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("GRN_PAYMENT", group) != null) {%>
                            <li><a href="${context}/Payment?action=ToDoGRNPayment">GRN Payment</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("GRN_MULTIPAYMENT", group) != null) {%>
                            <li><a href="${context}/Payment?action=ToDoGRNMultiPay">GRN multiple Payment</a></li>
                                <%}%>
                        </ul>
                    </li>



                    <li><a><i class="fa fa-file-text-o"></i>Inventory Report<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("SALES_REPORT", group) != null) {%>
                            <li><a href="${context}/app/report/SearchSales1.jsp">Total Sales Report</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("BRANCH_WISE_SALES_REPORT", group) != null) {%>
                            <li><a href="${context}/app/report/SalesWithExpenses.jsp">Branch wise Sales Report </a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("OUTSTANDING_REPORT", group) != null) {%>
                            <li><a href="${context}/app/report/OutstandingReport.jsp">Outstanding Report </a></li>
                                <%}%>
                            <li><a href="${context}/Invoice?action=toCanceledInvoice">Canceled Invoice Report </a></li>
                            <li><a href="${context}/Report?action=ToCreateBIN">BIN Card</a></li>
                            <li><a href="${context}/Stock?action=reorderlevel">Re Order Level Report </a></li>
                            <li><a href="${context}/Invoice?action=SearchCreditLimit">Customer Credit Limit </a></li>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-users"></i>Customer Management<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">

                            <%if (ca.checkUserAuth("ADD_CUSTOMER", group) != null) {%>
                            <li><a href="${context}/app/customer/registerCustomer.jsp">Add New Customer</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("VIEW_CUSTOMERS", group) != null) {%>
                            <li><a href="${context}/Customer?action=ViewCustomer">Manage Customers</a></li>
                                <%}%>

                            <%if (ca.checkUserAuth("ADD_CUSTOMER_GROUP", group) != null) {%>
                            <li><a href="${context}/Customer?action=LoadCustomerGroup">Add Customer Group</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("VIEW_CUSTOMER_GROUP", group) != null) {%>
                            <li><a href="${context}/Customer?action=LoadmanageGroup">Manage Customer Group</a></li>
                                <%}%>

                        </ul>
                    </li>
                    <li><a><i class="fa fa-building"></i>Branches<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("MANAGE_BRANCH", group) != null) {%>
                            <li><a href="${context}/Branch?action=ViewBranches">Manage Branch</a></li>
                                <%}%>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-recycle"></i>Return Items<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">

                            <li><a href="${context}/Return?action=CustomerReturnNote">Customer Return Note</a></li>
                            <li><a href="${context}/Return?action=ViewCustomerReturnNote">View Customer Return Note</a></li>
                            <!--<li><a href="${context}/Return?action=CustomerReturn">Customer Return</a></li>-->
                            <%if (ca.checkUserAuth("STOCK_RETURN", group) != null) {%>
                            <li><a href="${context}/Return?action=StockReturn">Stock Return</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("RETURN_STOCK", group) != null) {%>
                            <li><a href="${context}/Return?action=ReturnStock">Return Stock</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("PRODUCT_DISPOSE", group) != null) {%>
                            <li><a href="${context}/Return?action=Dispose">Product Dispose</a></li>
                                <%}%>
                            <!--<li><a href="${context}/Return?action=SupplierReturn">Supplier Return</a></li>-->

                            <li><a href="${context}/Return?action=SupplierReturnNote">Supplier Return Note</a></li>
                            <li><a href="${context}/Return?action=ViewSupplierReturnNote">View Supplier Return Note</a></li>
                        </ul>
                    </li>
                    <%}%>
                    <%if (sidebar.equals("payroll")) {%>
                    <!--<div class="ln_solid"></div>-->
                    <li><a><i class="fa fa-user-plus"></i>Employee<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("MANAGE_DEPARTMENT", group) != null) {%>
                            <li><a href="${context}/Employee?action=Department">Manage Departments</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("MANAGE_DESIGNATION", group) != null) {%>
                            <li><a href="${context}/Employee?action=Designation">Manage Designations</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("MANAGE_EMPLOYEE_TYPE", group) != null) {%>
                            <li><a href="${context}/Employee?action=ViewEmployeeType">Manage Employee Type</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("EMPLOYEE_REGISTRATION", group) != null) {%>
                            <li><a href="${context}/Employee?action=EmployeeReg">Employee Registration</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("EMPLOYEE_IMAGE", group) != null) {%>
                            <li><a href="${context}/Employee?action=EmployeeImage">Employee Image</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("VIEW_EMPLOYEE", group) != null) {%>
                            <li><a href="${context}/Employee?action=ViewEmployee">View Employee</a></li>
                                <%}%>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-desktop"></i> Attendance <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("ADD_LEAVES", group) != null) {%>
                            <li><a href="${context}/Attendance?action=ViewaddLeave">Add Leaves</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("VIEW_LEAVES", group) != null) {%>
                            <li><a href="${context}/Attendance?action=ViewLeave">View Leaves</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("MANAGE_ATTENDANCE", group) != null) {%>
                            <li><a href="${context}/Attendance?action=Attendance">Attendance</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("MANAGE_FINGER_PRINT", group) != null) {%>
                            <li><a href="${context}/Attendance?action=ReadFPData">Read Finger Print Data</a></li>
                                <%}%>

                        </ul>
                    </li>
                    <li><a><i class="fa fa-bank"></i> Bank Details <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("Bank_DETAILS", group) != null) {%>
                            <li><a href="${context}/Bank?action=LoadBank">Save Bank</a></li>
                                <%}%>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-arrow-circle-up"></i> Allowance & Deduction <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("ADD_ALLOWANCE&DEDUCTION", group) != null) {%>
                            <li><a href="${context}/Allowance?action=ViewaddAllowance">Allowance & Deductions</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("VIEW_ALLOWANCE&DEDUCTION", group) != null) {%>
                            <li><a href="${context}/Allowance?action=ViewAllowance">View Allowance & Deductions</a></li>
                                <%}%>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-recycle"></i> Staff Loans <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("MANAGE_STAFF_LOANS", group) != null) {%>
                            <li><a href="${context}/Salary?action=staffloans">Staff Loan</a></li>
                                <%}%>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-money"></i> Salary<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("WORKING_HOURS", group) != null) {%>
                            <li><a href="${context}/Salary?action=ViewDefault">Working Hours</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("HOLIDAYS", group) != null) {%>
                            <li><a href="${context}/Salary?action=ViewHolyday">HoliDays</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("DEFAULT_PAYMENT", group) != null) {%>
                            <li><a href="${context}/Salary?action=ViewDefaultPayments">Default Payments</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ADVANCE_PAYMENT", group) != null) {%>
                            <li><a href="${context}/Salary?action=ViewAdvancePayment">Advance payment</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("CALCULATE_SALARY", group) != null) {%>
                            <li><a href="${context}/Salary?action=CalSalary">Calculate Salary</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("SALARY_PAYMENT", group) != null) {%>
                            <li><a href="${context}/Salary?action=ViewSalaryPayment">Salary payment</a></li>
                                <%}%>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-newspaper-o"></i> Reports <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("EMPLOYEE_PROFILE", group) != null) {%>
                            <li><a href="${context}/Report?action=SearchEmployee">Employee Profile</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("EMPLOYEE_REPORT", group) != null) {%>
                            <li><a href="${context}/Report?action=Employees">Employees</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("MANAGE_LEAVES", group) != null) {%>
                            <li><a href="${context}/Report?action=Leaves">Leaves</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ATTENDANCE_REPORT", group) != null) {%>
                            <li><a href="${context}/Report?action=Attendance">Attendance</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("MANAGE_BANK_ACCOUNT", group) != null) {%>
                            <li><a href="${context}/Report?action=Bank">Bank Accounts</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("MANAGE_ALLOWANCE_DEDUCTION", group) != null) {%>
                            <li><a href="${context}/Report?action=Allowance">Allowance & Deduction</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("MANAGE_STAFFLOANS", group) != null) {%>
                            <li><a href="${context}/Report?action=staffloans">Staff Loans</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("HOLIDAYS_REPORT", group) != null) {%>
                            <li><a href="${context}/Report?action=holiday">Holidays</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ADVANCE_REPORT", group) != null) {%>
                            <li><a href="${context}/Report?action=advanse">Advance Payment</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("SALARY_REPORT", group) != null) {%>
                            <li><a href="${context}/Report?action=salary">Salary</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("SALARY_PAYMENT_REPORT", group) != null) {%>
                            <li><a href="${context}/Report?action=salarypayment">Salary Payments</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("PAY_SLIP", group) != null) {%>
                            <li><a href="${context}/Report?action=payslip">Pay Slip</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("EPF_PAYABLE", group) != null) {%>
                            <li><a href="${context}/Report?action=epf">EPF Payable</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ETF_PAYABLE", group) != null) {%>
                            <li><a href="${context}/Report?action=etf">ETF Payable</a></li>
                                <%}%>
                        </ul>
                    </li>

                    <%}%>
                    <%if (sidebar.equals("accounting")) {%>
                    <!--<div class="ln_solid"></div>-->
                    <li><a><i class="fa fa-check"></i>Account<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("MANAGE_ACCOUNT", group) != null) {%>
                            <li><a href="${context}/Account?action=loadOfType">Add Account </a></li>
                                <%}%>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-check"></i>Transaction<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("ACCOUNT_INCOME", group) != null) {%>
                            <li><a href="${context}/Income?action=loadIncomePage">Income </a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ACCOUNT_EXPENSE", group) != null) {%>
                            <li><a href="${context}/Expense?action=loadExpensePage">Expenses</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ACCOUNT_PURCHASE", group) != null) {%>
                            <li><a href="${context}/Purchase?action=ViewPurchase">Purchase</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ACCOUNT_SELL", group) != null) {%>
                            <li><a href="${context}/Sell?action=ViewSell">Sell</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ACCOUNT_COMPANY_LOAN", group) != null) {%>
                            <li><a href="${context}/AssetLoan?action=ViewLoan">Company Loan</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ACCOUNT_OTHER_LOAN", group) != null) {%>
                            <li><a href="${context}/Liability?action=ViewLiability">Other Loan</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ACCOUNT_INVESTMENT", group) != null) {%>
                            <li><a href="${context}/Investment?action=loadAccount">Investment</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ACCOUNT_DRAWING", group) != null) {%>
                            <li><a href="${context}/Drawing?action=loadAccount">Drawings</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ACCOUNT_ASSET", group) != null) {%>
                            <li><a href="${context}/FixedAsset?action=ViewPurchase">Assets</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ACCOUNT_FINANCE_TRANSFER", group) != null) {%>
                            <li><a href="${context}/FinanceTransfer?action=loadFinance">Finance Transfer</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ACCOUNT_LOAN_PAYMENT", group) != null) {%>
                            <li><a href="${context}/LoanPay?action=loadLoanPayPage">Loan Payment</a></li>
                                <%}%>
                        </ul>
                    </li>


                    <li><a><i class="fa fa-check"></i>TAX<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <li><a href="${context}/Tax?action=loadTaxPage">Manage Tax</a></li>
                            <li><a href="${context}/Tax?action=SearchTaxDetails">View Tax</a></li>
                        </ul>
                    </li>


                    <li><a><i class="fa fa-check"></i>Budget Plan<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <li><a href="${context}/Report?action=CreateBudgetPlan">Create Budget Plan</a></li>
                            <li><a href="${context}/Tax?action=SearchTaxDetails">View Tax</a></li>
                        </ul>
                    </li>
                    
                    <li><a><i class="fa fa-check"></i>Reports<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("GENERAL_JOURNAL", group) != null) {%>
                            <li><a href="${context}/Report?action=loadJournalEntries">General Journal Entries</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("CASHBOOK", group) != null) {%>
                            <li><a href="${context}/Report?action=ViewAssets">Cashbook</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("LEDGER_ACCOUNT", group) != null) {%>
                            <li><a href="${context}/Report?action=toLedgerAccounts">Ledger Accounts</a></li>
                                <%}%>
                            <li><a href="${context}/Report?action=loadIncomestatementPage">Income Statement</a></li>

                            <%if (ca.checkUserAuth("PROFIT_LOST", group) != null) {%>
                            <li><a href="${context}/Report?action=loadProfitLossPage">Profit & lost Statement</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("TRIAL_BALANCE", group) != null) {%>
                            <li><a href="${context}/Report?action=ViewCreateTrialBalance">Trial Balance</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("BALANCE_SHEET", group) != null) {%>
                            <li><a href="${context}/Report?action=ViewCreateBalanceSheet">Balance Sheet</a></li>
                                <%}%>
                            <li><a href="${context}/Report?action=LoadCCPRpage">Credit Card Payment Report</a></li>
                            <li><a href="${context}/Report?action=loadBPR">Budget Plan Report</a></li>
                        </ul>
                    </li>
                    <%}%>
                    <!--<div class="ln_solid"></div>-->
                    <%}%>
                    <li><a><i class="fa fa-check"></i>Company<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("COMPANY_GROUP", group) != null) {%>
                            <li><a href="${context}/Company?action=loadCompanyGroup">Company Group</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ADD_COMPANY", group) != null) {%>
                            <li><a href="${context}/Company?action=loadCompanypage">Add Company</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("MANAGE_COMPANY", group) != null) {%>
                            <li><a href="${context}/Company?action=updateCompany1">Manage Company</a></li>
                                <%}%>

                        </ul>
                    </li>
                    <li><a><i class="fa fa-user-secret"></i>User Management<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("ADD_USER", group) != null) {%>
                            <li><a href="${context}/User?action=UserRegistration">Add New User</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("VIEW_USERS", group) != null) {%>
                            <li><a href="${context}/User?action=ViewUsers">Manage Users</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ADD_USER_GROUP", group) != null) {%>
                            <li><a href="${context}/app/users/createUserGroup.jsp">Create User Group</a></li>
                                <%}%>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-check"></i>Privilege Management<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu" style="display: none">
                            <%if (ca.checkUserAuth("ADD_PRIVILEGE", group) != null) {%>
                            <li><a href="${context}/Privilege?action=ViewPrivilege">Manage Privilege Groups</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("ADD_PRIVILEGE_ITEM", group) != null) {%>
                            <li><a href="${context}/Privilege?action=ForPrivilegeItem">Manage Privileges</a></li>
                                <%}%>
                                <%if (ca.checkUserAuth("SET_PRIVILEGE_ITEM", group) != null) {%>
                            <li><a href="${context}/Privilege?action=LoadUserGroupsForPI">Manage User Group Privileges</a></li>
                                <%}%>
                        </ul>
                    </li>

                </ul>
            </div>
        </div>
        <!-- /sidebar menu -->

        <!-- /menu footer buttons -->
        <div class="sidebar-footer hidden-small">

            <a data-toggle="tooltip" data-placement="top" title="Logout" href="${context}/Logout">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
            </a>
        </div>
        <!-- /menu footer buttons -->
    </div>
</div>


<div class="top_nav">

    <div class="nav_menu">
        <nav class="" role="navigation">
            <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
            </div>
            <div style="" class="nav navbar-nav">
                <!-- Button -->
                <%
                    String success_message = (String) request.getAttribute("Success_Message");
                    String error_message = (String) request.getAttribute("Error_Message");
                    if (success_message == null) {
                        success_message = (String) session.getAttribute("Success_Message");
                    }
                    if (error_message == null) {
                        error_message = (String) session.getAttribute("Error_Message");
                    }
                    request.getSession().removeAttribute("Error_Message");

                %>
                <div class="" id="mydiv">
                    <strong><font color="green">
                        <% if (success_message != null) {
                                out.println(success_message);
                            }%>
                        </font>
                    </strong> 
                    <strong><font color="red">
                        <% if (error_message != null) {
                                out.println(error_message);
                            }%>
                        </font>
                    </strong> 
                </div>
            </div>
            <%
                request.getSession().removeAttribute("Error_Message");
                request.getSession().removeAttribute("Success_Message");

            %>
            <ul class="nav navbar-right">
                <li class="">
                    <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                        <%out.print(user.getFirstName() + " " + user.getLastName());%>
                        <span class=" fa fa-angle-down"></span>
                    </a>
                    <ul class="dropdown-menu dropdown-usermenu animated fadeInDown pull-right">
                        <li><a href="${context}/app/users/editOwn.jsp">  Update Profile</a>
                        </li>
                        <li><a href="${context}/app/users/changePassword.jsp">  Change Password</a>
                        </li>

                        <li><a href="${context}/Logout"><i class="fa fa-sign-out pull-right"></i> Log Out</a>
                        </li>
                    </ul>
                </li>



            </ul>
        </nav>
    </div>

</div>

<!-- page content -->
<div class="right_col" role="main">