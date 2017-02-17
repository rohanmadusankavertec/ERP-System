<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Salary"%>
<%@page import="com.vertec.hibe.model.Employee"%>
<%@page import="com.vertec.hibe.model.Department"%>
<%@page import="com.vertec.hibe.model.Designation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>




<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>

    <%
Salary s = (Salary) request.getAttribute("salary");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Pay Slip
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

                        <div style="margin-left: 200px;">
                        <h4>
                            <div class="table-responsive">
                                <table class="table" style="width: 500px;">
                                        <tbody>
                                            <tr>
                                                <th>Employee Name</th>
                                                <td><%=s.getEmployeeId().getFname() +" "+s.getEmployeeId().getLname() %></td>
                                            </tr>
                                            <tr>
                                                <th>Calculated From</th>
                                                <td><%=s.getFromdate()%></td>
                                            </tr>
                                            <tr>
                                                <th>Calculated To</th>
                                                <td><%=s.getTodate()%></td>
                                            </tr>
                                            <tr>
                                                <th>Basic Salary</th>
                                                <td><%=s.getEmployeeId().getBasicSalary() %></td>
                                            </tr>
                                            <tr>
                                                <th>Allowance</th>
                                                <td><%=s.getAllowance()%></td>
                                            </tr>
                                            <tr>
                                                <th>Deduction</th>
                                                <td><%=s.getDeduction() %></td>
                                            </tr>
                                            <tr>
                                                <th>Late Arrival Deduction</th>
                                                <td><%=s.getLateDeduct() %></td>
                                            </tr>
                                            <tr>
                                                <th>OT Allowance</th>
                                                <td><%=s.getOtpay() %></td>
                                            </tr>
                                            <tr>
                                                <th>Leave Deduction</th>
                                                <td><%=s.getNopayDeduct() %></td>
                                            </tr>
                                            <tr>
                                                <th>EPF Deduction</th>
                                                <td><%=s.getEpf() %></td>
                                            </tr>
                                            <tr>
                                                <th>ETF</th>
                                                <td><%=s.getEtf() %></td>
                                            </tr>
                                            <tr>
                                                <th>EPF Payable</th>
                                                <td><%=s.getEpfPayable() %></td>
                                            </tr>
                                            
                                            
                                            
                                            
                                            <tr>
                                                <th>Total Salary</th>
                                                <td><%=s.getTotalSalary() %></td>
                                            </tr>
                                            <tr>
                                                <th>Advance Paid</th>
                                                <td><%=s.getAdvancePaid() %></td>
                                            </tr>
                                            <tr>
                                                <th>Loan Paid</th>
                                                <td><%=s.getLoanPaid() %></td>
                                            </tr>
                                            <tr>
                                                <th>Salary Payable</th>
                                                <td><%=s.getSalaryPayable()%></td>
                                            </tr>
                                            
                                            
                                            
                                        </tbody>
                                    </table>
                                </div>
                         
                        </h4>
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

