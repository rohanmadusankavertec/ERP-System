<%-- 
    Document   : BinCard
    Created on : Jan 27, 2017, 2:21:29 PM
    Author     : Rohan
--%>

<%@page import="com.vertec.hibe.model.Attendance"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>




<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>

<%
    List<Attendance> leave = (List<Attendance>) request.getAttribute("leaves");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Attendance
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
                    <span class="section">Attendance Report</span>
                    <div >

                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Employee Name</th>
                                        <th>Date</th>
                                        <th>Intime</th>
                                        <th>Out Time</th>
                                        <th>Marked By</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                    for(Attendance l: leave){
                                        
                                    %>
                                    <tr>
                                        <td><%=l.getEmployeeId().getFname()%> <%=l.getEmployeeId().getLname() %></td>
                                        <td><%=l.getDate() %></td>
                                        <td><%=l.getIntime() %></td>
                                        <td><%=l.getOuttime() %></td>
                                        <td><%=l.getMarkedBy().getFirstName() %></td>
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

