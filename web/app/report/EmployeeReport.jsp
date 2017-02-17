<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Employee"%>
<%@page import="com.vertec.hibe.model.Department"%>
<%@page import="com.vertec.hibe.model.Designation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>




<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>

<%
    List<Employee> emp = (List<Employee>) request.getAttribute("employee");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Employees
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
                    <span class="section">Employee Report</span>
                    <div >
                       
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Employee Name</th>
                                            <th>NIC</th>
                                            <th>DOB</th>
                                            <th>Gender</th>
                                            <th>Address</th>
                                            <th>Department</th>
                                            <th>Designation</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        
                                        <%
                                        for(Employee e : emp){
                                        
                                        %>
                                        
                                        <tr>
                                            <td><%=e.getFname()%> <%=e.getLname()%></td>
                                            <td><%=e.getNic()%></td>
                                            <td><%=e.getDob()%></td>
                                            <td><%
                                                if (e.getGender()) {
                                                    out.write("Male");
                                                } else {
                                                    out.write("Female");
                                                }
                                                %></td>
                                            <td><%=e.getAddress()%></td>
                                            <td><%=e.getDesignationId().getDepartmentId().getName()%></td>
                                            <td><%=e.getDesignationId().getName()%></td>
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

