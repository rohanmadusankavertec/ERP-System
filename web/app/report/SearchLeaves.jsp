<%-- 
    Document   : FilterPO
    Created on : Aug 23, 2016, 12:31:21 PM
    Author     : vertec-r
--%>

<%@page import="com.vertec.hibe.model.Employee"%>
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/notAlert.js"></script>
<script type="text/javascript">
    //show and hide fields according to search type
    function ChangeFilter() {
        var reportType = document.getElementById('type').value;
        if (reportType === "0") {
            document.getElementById('fromdate').className = 'hidden';
            document.getElementById('todate').className = 'hidden';
            document.getElementById('emp').className = 'hidden';

        } else if (reportType === "1") {
            document.getElementById('emp').className = 'item form-group';
            document.getElementById('fromdate').className = 'hidden';
            document.getElementById('todate').className = 'hidden';

        } else if (reportType === "2") {
            document.getElementById('emp').className = 'hidden';
            document.getElementById('fd').innerHTML = 'From Date';
            document.getElementById('fromdate').className = 'item form-group';
            document.getElementById('todate').className = 'item form-group';

        } else if (reportType === "3") {
            document.getElementById('emp').className = 'hidden';
            document.getElementById('fd').innerHTML = 'Date';
            document.getElementById('fromdate').className = 'item form-group';
            document.getElementById('todate').className = 'hidden';

        }

    }

// send data to server side
    function SendData() {
        var type = document.getElementById('type').value;
        var emp = document.getElementById('employee').value;
        var fromDate = document.getElementById('fromDate').value;
        var toDate = document.getElementById('toDate').value;
        window.location = 'Report?action=LeaveReport&type=' + type + "&emp=" + emp + "&from=" + fromDate + "&to=" + toDate;
    }
</script>
<%
    List<Employee> e = (List<Employee>) request.getAttribute("employee");
%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                View Employee's Leaves
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
                    <form action="#" method="post">
                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Type </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="type" onchange="ChangeFilter()" id="type"  required="required">
                                    <option selected="true" disabled value="">Select Search Type</option>
                                    <option value="0" value="">ALL</option>
                                    <option value="1" value="">Employee Wise</option>
                                    <option value="2" value="">Date Range</option>
                                    <option value="3" value="">Daily Leaves</option>
                                </select>                              
                            </div>
                        </div>
                        <div class="hidden" style="padding-top: 50px;" id="emp">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Employee </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="employee" id="employee"  required="required">
                                    <option selected="true" disabled value="">Select Employee</option>
                                    <option value="All" value="">ALL</option>
                                    <% for (Employee emp : e) {%>
                                    <option value="<%=emp.getId()%>"><%=emp.getFname() + "_" + emp.getLname()%></option>
                                    <%}%>
                                </select>                              
                            </div>
                        </div>
                        <div class="hidden" style="padding-top: 50px;" id="fromdate">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" id="fd">From Date <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="fromDate" name="fromDate" required="required" placeholder="From Date Ex: yyyy-mm-dd" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="hidden" style="padding-top: 50px;" id="todate">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">To Date <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="toDate" name="toDate" required="required" placeholder="To Date Ex: yyyy-mm-dd" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 150px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" onclick="SendData()" type="button">View Leaves</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>

<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
