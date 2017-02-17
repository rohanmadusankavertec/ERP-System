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
    function SentData() {
        var sup = document.getElementById('supplier').value;
        var type = document.getElementById('reportType').value;
        var fromDate = document.getElementById('fromDate').value;
        var toDate = document.getElementById('toDate').value;
        window.location = '../../PO?action=viewPO&supplier=' + sup + "&type=" + type + "&from=" + fromDate + "&to=" + toDate;
    }
</script>




<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Search Stocks in Branches
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
                    <form action="../../Stock?action=branchStock" method="post">

                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Search Type 
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="stockType" id="stockType"  required="required" >
                                    <option selected="true" disabled value="">Select Type</option>
                                    <option value="1">All</option>
                                    <option value="2">Import</option>
                                    <option value="3">Local</option>
                                    <option value="4">Re-Order</option>
                                </select>                              
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Branch 
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="branch" id="branch"  required="required" >
                                    <option selected="true" disabled value="">Select Branch</option>

                                    <%
                                        HttpSession httpSession = request.getSession();
                                        SysUser user1 = (SysUser) httpSession.getAttribute("user");
                                        if (ca.checkUserAuth("OTHER_BRANCH", group) != null) {
                                    %>
                                    <option value="0">All</option>
                                    <%
                                        SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                        Session ses = sf.openSession();
                                        SQLQuery query = ses.createSQLQuery("SELECT branch_id,branch_name FROM branch WHERE branch_id>0");
                                        List<Object[]> inList = query.list();
                                        for (Object[] list : inList) {
                                    %>
                                    <option value="<%= list[0].toString()%>"><%= list[1].toString()%></option>
                                    <%
                                        }
                                    } else {
                                    %>
                                    <option value="<%= user.getBranchBranchId().getBranchId()%>"><%= user.getBranchBranchId().getBranchName()%></option>
                                    <%
                                        }
                                    %>

                                </select>                              
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" onclick="">View Warehouse Stock</button>
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
