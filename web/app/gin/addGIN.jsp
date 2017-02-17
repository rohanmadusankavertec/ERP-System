<%-- 
    Document   : FilterPO
    Created on : Aug 23, 2016, 12:31:21 PM
    Author     : vertec-r
--%>

<%@page import="com.vertec.hibe.model.Company"%>
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
//    Change Elements according to grn type
    function ChangeFilter() {
        var reportType = document.getElementById('grnType').value;
        if (reportType === "1") {
            document.getElementById('mrnfield').className = 'hidden';
        } else if (reportType === "2") {
            document.getElementById('mrnfield').className = 'item form-group';
        }
    }
    //Send filtered data to serverside
    function SentData() {
        var type = document.getElementById('ginType').value;
        var mrn = document.getElementById('mrn').value;
        window.location = "GIN?action=toGIN&type=" + type + "&mrn=" + mrn;
    }
</script>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Create GIN (Goods Issue Note)
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

                    <div class="hidden" style="padding-top: 50px;">
                        <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select GIN Type 
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="ginType" id="ginType"  required="required" onchange="ChangeFilter()">
                                <option selected="true" disabled value="">Select Type</option>
                                <option value="1">Add Without MRN (Material Requisition Note)</option>
                                <option value="2">Add With MRN (Material Requisition Note)</option>
                            </select>                              
                        </div>
                    </div>
                    <div class="item form-group" style="padding-top: 50px;" id="mrnfield">
                        <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select MRN 
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="mrn" id="mrn"  required="required" onchange="ChangeFilter()">
                                <option selected="true" disabled value="">Select Material Requisition Note</option>
                                <%
                                    HttpSession httpSession = request.getSession();
                                    Company company = (Company) httpSession.getAttribute("company");
                                    SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                    Session ses = sf.openSession();
                                    SQLQuery query1 = ses.createSQLQuery("SELECT mi.id,mi.date FROM mrn_info mi WHERE mi.id IN (SELECT mrn_info_id FROM mrn where available_qty>0) and mi.company_id='" + company.getId() + "' GROUP BY mi.id");
                                    List<Object[]> inList1 = query1.list();
                                    for (Object[] list : inList1) {
                                %>
                                <option value="<%= list[0].toString()%>"><%= list[0].toString()%>_<%= list[1].toString()%></option>
                                <%
                                    }
                                %>
                            </select>                              
                        </div>
                    </div>
                    <div class="item form-group" style="padding-top: 50px;">
                        <div class="col-xs-12 col-lg-offset-3">
                            <button class="btn btn-success" onclick="SentData()">Create GIN</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
