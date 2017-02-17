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





<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Search Stock
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
                    <form action="ViewStock.jsp" method="post">



                        <div class="item form-group" style="padding-top: 50px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Branch 
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="branch" id="branch"  required="required" onchange="ChangeFilter()">
                                    <option selected="true" disabled value="">Select Supplier</option>
                                    <%
                                        SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                        Session ses = sf.openSession();
                                        SQLQuery query = ses.createSQLQuery("SELECT branch_id,branch_name FROM branch");
                                        List<Object[]> inList = query.list();
                                        for (Object[] list : inList) {
                                    %>
                                    <option value="<%= list[0].toString()%>"><%=list[1].toString()%></option>
                                    <%
                                        }
                                    %>
                                </select>                              
                            </div>
                        </div>



                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Search Type 
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="reportType" id="reportType"  required="required" onchange="ChangeFilter()">
                                    <option selected="true" disabled value="">Select Type</option>
                                    <option value="1">All</option>
                                    <option value="2">Local Items</option>
                                    <option value="3">Import Items</option>
                                    <option value="4">Re-Order Items</option>
                                </select>                              
                            </div>
                        </div>




                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" type="submit">View Stock Reports</button>
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
