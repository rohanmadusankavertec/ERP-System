<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>




<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Sales Report
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
                    <form action="SalesReportWithExpenses.jsp">
                        </p>

                        <div class="clearfix"></div>
                        <div style="padding-top: 10px;" id="branchwise">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Branch <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="branchId" id="branchId" >
                                    <option selected="true" disabled value="">Select Branch</option>

                                    <%
                                        SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                        Session ses = sf.openSession();
                                        String hql = "SELECT branch_id,branch_name FROM branch";
                                        if (ca.checkUserAuth("OTHER_BRANCH", group) == null) {
                                            hql += " where branch_id='" + user.getBranchBranchId().getBranchId() + "'";
                                        }
                                        SQLQuery query = ses.createSQLQuery(hql);

                                        List<Object[]> inList = query.list();

                                        for (Object[] b : inList) {%>

                                    <option value="<%=b[0]%>"><%=b[1]%></option>
                                    <%
                                        }

                                    %>

                                </select>                              
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">From Date <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="fromDate" name="fromDate" required="required" placeholder="From Date" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">To Date <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="toDate" name="toDate" required="required" placeholder="From Date" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        
                        <div class="form-group" style="padding-top: 10px;">
                            <div class="col-md-6 col-md-offset-3">
                                <button id="send" onclick="" type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>





    <center>

    </center>
    <div class="clearfix"></div>
    <div class="clearfix"></div>
</div>




<%@include file="../../template/footer.jsp"%>