<%-- 
    Document   : BinCard
    Created on : Jan 27, 2017, 2:21:29 PM
    Author     : Rohan
--%>

<%@page import="com.vertec.hibe.model.AllowanceDeduction"%>
<%@page import="com.vertec.hibe.model.Attendance"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>




<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>

<%
    List<AllowanceDeduction> al = (List<AllowanceDeduction>) request.getAttribute("allowance");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Allowance & Deduction
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
                    <span class="section">Allowance & Deduction Report</span>
                    <div >

                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Employee</th>
                                        <th>Type</th>
                                        <th>Reason</th>
                                        <th>Value</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                    for(AllowanceDeduction a: al){
                                    %>
                                    <tr>
                                        <td><%=a.getEmployeeId().getFname()%> <%=a.getEmployeeId().getLname() %></td>
                                        <td><%
                                        if(a.getIsAllowance()){
                                        out.write("Allowance");
                                        }else{
                                        out.write("Deductions");
                                        }
                                        %></td>
                                        <td><%=a.getName() %></td>
                                        <td><%="Rs : "+a.getValue() %></td>
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

