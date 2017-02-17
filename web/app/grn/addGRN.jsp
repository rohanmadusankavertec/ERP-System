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
    //view hidden fields 
    function ChangeFilter() {
        var reportType = document.getElementById('grnType').value;
        if (reportType === "1") {
            document.getElementById('supfield').className = 'item form-group';
            document.getElementById('pofield').className = 'hidden';
        } else if (reportType === "2") {
            document.getElementById('supfield').className = 'hidden';
            document.getElementById('pofield').className = 'item form-group';
        }
    }
    // send data to serverside
    function SentData() {
        var sup = document.getElementById('sup').value;
        var type = document.getElementById('grnType').value;
        var po = document.getElementById('po').value;
        window.location = "GRN?action=toGRN&type=" + type + "&po=" + po + "&sup=" + sup;
    }
</script>




<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Create GRN (Goods Received Note)
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

                    <div class="hidden" style="padding-top: 50px;">
                        <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select GRN Type 
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="grnType2" id="grnType2"  required="required" onchange="ChangeFilter()">
                                <option selected="true" disabled value="">Select Type</option>
                                <option value="1">Add Without PO (Purchasing Order)</option>
                                <option value="2">Add With Po (Purchasing Order)</option>
                            </select>                              
                        </div>
                    </div>
                    <div class="hidden" style="padding-top: 50px;" id="supfield">
                        <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Supplier 
                        </label>
                        <input type="hidden" value="" name="po"/>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="sup2" id="sup2"  required="required" onchange="ChangeFilter()">
                                <option selected="true" disabled value="">Select Supplier</option>
                                <%
                                    HttpSession httpSession = request.getSession();
                                    Company company = (Company) httpSession.getAttribute("company");
                                    SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                    Session ses = sf.openSession();

                                    SQLQuery query = ses.createSQLQuery("SELECT supplier_id,company_name FROM supplier where company_id='" + company.getId() + "'");

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
                    <input type="hidden" value="2" id="grnType"/>
                    <input type="hidden" value="" id="sup"/>
                    <div class="item form-group" style="padding-top: 50px;" id="pofield">
                        <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select PO 
                        </label>
                        <input type="hidden" value="" name="sup"/>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="po" id="po"  required="required" onchange="ChangeFilter()">
                                <option selected="true" disabled value="">Select Purchase Order</option>
                                <%
                                    SQLQuery query1 = ses.createSQLQuery("SELECT pi.po_info_id,pi.date,pi.total,s.company_name FROM po_info pi INNER JOIN supplier s ON s.supplier_id=pi.supplier_supplier_id WHERE pi.po_info_id IN (SELECT po_info_po_info_id FROM purchasing_order where available_qty>0) AND pi.company_id='" + company.getId() + "' GROUP BY pi.po_info_id");
                                    List<Object[]> inList1 = query1.list();
                                    for (Object[] list : inList1) {
                                %>
                                <option value="<%= list[0].toString()%>"><%= list[0].toString()%>_<%= list[1].toString()%>_<%= list[2].toString()%>_<%= list[3].toString()%></option>

                                <%
                                    }
                                %>
                            </select>                              
                        </div>
                    </div>

                    <div class="item form-group" style="padding-top: 50px;">
                        <div class="col-xs-12 col-lg-offset-3">
                            <button class="btn btn-success" onclick="SentData()">Create GRN</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
