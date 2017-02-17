
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.SQLQuery"%>

<%@page import="com.vertec.hibe.model.Branch"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="../../app/js/notAlert.js"></script>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Add MRN (Material Requisition Note)
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
                    <form action="../../MRN?action=toMRN" method="post">

                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Supplier <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="supId" id="supId"  required="required">
                                    <option selected="true" disabled value="">Select Supplier</option>
                                    <%
                                        SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                        Session ses = sf.openSession();
                                        SQLQuery query = ses.createSQLQuery("SELECT supplier_id,company_name FROM supplier");
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
                                <button class="btn btn-success" id="viewInvoices"><i class="fa fa-arrow-right"></i>  Add Purchasing Order </button>
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
