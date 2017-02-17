<%-- 
    Document   : FilterPO
    Created on : Aug 23, 2016, 12:31:21 PM
    Author     : vertec-r
--%>

<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/po.js"></script>
<script src="app/js/notAlert.js"></script>
<script type="text/javascript">
    function ChangeFilter() {
        var reportType = document.getElementById('ginType').value;
        if (reportType === "1") {
            document.getElementById('datefrom').className = 'hidden';
            document.getElementById('dateto').className = 'hidden';
            document.getElementById('branchele').className = 'hidden';

        }else if (reportType === "2") {
            document.getElementById('fromlbl').innerHTML = 'From Date';
            document.getElementById('datefrom').className = 'item form-group';
            document.getElementById('dateto').className = 'item form-group';
            document.getElementById('branchele').className = 'hidden';
        } else if (reportType === "3") {
            document.getElementById('fromlbl').innerHTML = 'Date';
            document.getElementById('datefrom').className = 'item form-group';
            document.getElementById('dateto').className = 'hidden';
            document.getElementById('branchele').className = 'hidden';
        } else if (reportType === "4") {
            document.getElementById('datefrom').className = 'hidden';
            document.getElementById('dateto').className = 'hidden';
            document.getElementById('branchele').className = 'item form-group';
        } 

    }
    
    function SentData(){
        var type=document.getElementById('ginType').value;
        var fromDate=document.getElementById('fromDate').value;
        var toDate=document.getElementById('toDate').value;
        var branch=document.getElementById('branch').value;
        window.location = "../../GIN?action=viewGIN&type="+type+"&from="+fromDate+"&to="+toDate+"&branch="+branch; 
    }
</script>




<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Search Goods Issue Notes
                <small>

                </small>
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
                    <!--<form action="../../GIN?action=viewGIN" method="post">-->
                        
                            <div class="item form-group" style="padding-top: 50px;">
                                <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Search Type 
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <select class="form-control" name="ginType" id="ginType"  required="required" onchange="ChangeFilter()">
                                        <option selected="true" disabled value="">Select Type</option>
                                        <option value="1">All</option>
                                        <option value="2">Date Range</option>
                                        <option value="3">Daily GIN</option>
                                        <option value="4">Branch Wise</option>

                                    </select>                              
                                </div>
                            </div>
                            <div class="hidden"  id="datefrom">           
                                <div class="item form-group" style="padding-top: 50px;">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12" id="fromlbl" for="name">From Date <span class="required"></span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <input type="date" id="fromDate" name="fromDate" required="required" placeholder="From Date" class="form-control col-md-7 col-xs-12">
                                    </div>
                                </div>
                            </div>
                            <div class="hidden"  id="dateto">
                                <div class="item form-group" style="padding-top: 50px;">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">To Date <span class="required"></span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <input type="date" id="toDate" name="toDate" required="required" placeholder="From Date" class="form-control col-md-7 col-xs-12">
                                    </div>
                                </div>
                            </div>
                            <div class="hidden" style="padding-top: 50px;" id="branchele">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Branch <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="branch" id="branch"  required="required">
                                    <option selected="true" disabled value="">Select Branch</option>
                                    <%
                                        SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                        Session ses = sf.openSession();
                                        SQLQuery query = ses.createSQLQuery("SELECT branch_id,branch_name FROM branch WHERE branch_id > 0");
                                        List<Object[]> inList = query.list();
                                        for (Object[] list : inList) {
                                    %>
                                    <option value="<%= list[0].toString()%>"><%= list[1].toString()%></option>

                                    <%
                                        }
                                    %>
                                </select>                              
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <!--<input type="submit" value="View Purchased Orders" class="btn btn-success"/>-->
                                <button class="btn btn-success" onclick="SentData();">View Goods Issue Notes</button>
                            </div>
                        </div>
                    <!--</form>-->
                </div>
            </div>
        </div>

    </div>
</div>

<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
