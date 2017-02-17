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
<script src="app/js/po.js"></script>
<script src="app/js/notAlert.js"></script>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Salary Calculation
                <%
                    List<Employee> employee = (List<Employee>) request.getAttribute("employee");
                %>
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
                    <form action="Salary?action=toCalculate" method="post" validate>
                        <div class="item form-group" style="padding-top: 50px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Employee</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="employee" required="required">
                                    <option selected="true" disabled value="">Select Employee</option>
                                     <%for (Employee emp : employee) {%>
                                    <option value="<%= emp.getId() %>"><%=emp.getFname()+" "+emp.getLname() %></option>
                                    <%}%>
                                </select>                              
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">From Date <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="fromDate" name="fromDate" required="required" placeholder="From Date Ex: yyyy-mm-dd" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">To Date <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="toDate" name="toDate" required="required" placeholder="To Date Ex: yyyy-mm-dd" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" type="submit">Calculate Salary</button>
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
