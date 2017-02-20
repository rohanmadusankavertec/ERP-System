<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.StaffLoan"%>
<%@page import="com.vertec.hibe.model.AllowanceDeduction"%>
<%@page import="com.vertec.hibe.model.Attendance"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>

<%
    List<StaffLoan> loan = (List<StaffLoan>) request.getAttribute("loan");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Employee's Loan
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
                    <span class="section">Staff Loan Report</span>
                    <div>
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Employee</th>
                                    <th>Date</th>
                                    <th>Amount</th>
                                    <th>I. Type</th>
                                    <th>I. Term</th>
                                    <th>Interest</th>
                                    <th>Terms</th>
                                    <th>Paid</th>
                                    <th>Balance</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%for (StaffLoan l : loan) {%>
                                <tr>
                                    <td><%= l.getEmployeeId().getFname() + " " + l.getEmployeeId().getLname()%></td>
                                    <td><%=l.getDate()%></td>
                                    <td><%=l.getAmount()%></td>
                                    <td>
                                        <%
                                            if (l.getInterestType() == 0) {
                                                out.write("Simple Interest");
                                            } else if (l.getInterestType() == 1) {
                                                out.write("Compound Interest");
                                            }
                                        %>
                                    </td>
                                    <td>
                                        <%
                                            if (l.getInterestTerm()) {
                                                out.write("Yearly");
                                            } else {
                                                out.write("Monthly");
                                            }
                                        %>
                                    </td>
                                    <td><%=l.getInterest()%></td>
                                    <td><%=l.getTerm()%> months</td>
                                    <td><%=l.getPaid()%></td>
                                    <td><%=l.getBalance()%></td>
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

