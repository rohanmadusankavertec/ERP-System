<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.SalaryPayment"%>
<%@page import="com.vertec.hibe.model.Loan"%>
<%@page import="com.vertec.hibe.model.AllowanceDeduction"%>
<%@page import="com.vertec.hibe.model.Attendance"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>

<%
    List<SalaryPayment> sp = (List<SalaryPayment>) request.getAttribute("sp");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Salary Payment
            </h3>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small></small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <span class="section">Salary Payment Report</span>
                    <div>
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Employee</th>
                                        <th>Date</th>
                                        <th>Type</th>
                                        <th>Paid</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%for (SalaryPayment l : sp) {%>
                                    <tr>
                                        <td><%= l.getSalaryId().getEmployeeId().getFname() + " " + l.getSalaryId().getEmployeeId().getLname()%></td>
                                        <td><%=l.getDate()%></td>
                                        <td><%=l.getSalaryPaymentTypeId().getType() %></td>
                                        <td><%=l.getPayment() %></td>
                                    </tr>
                                    <%}%>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
                <input type="button" onclick="window.print()" value="Print Report"/>
            </div>
        </div>
    </div>
</div>



<%} else {%>
<script type="text/javascript">
    window.location = '../../error403.jsp';
</script>
<%}%>


<!-- footer content -->
<%@include file="../../template/footer.jsp"%>

