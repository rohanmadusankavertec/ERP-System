
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>

<%@page import="com.vertec.hibe.model.Branch"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/invoice.js"></script>
<script src="app/js/notAlert.js"></script>



<script type="text/javascript">
    //Change fields according to report type
    function ChangeFilter() {
        var reportType = document.getElementById('reportType').value;
        if (reportType === "1") {
            document.getElementById('cust').className = 'hidden';
            document.getElementById('bran').className = 'hidden';
            document.getElementById('datefrom').className = 'hidden';
            document.getElementById('dateto').className = 'hidden';
        }else if (reportType === "2") {
            document.getElementById('cust').className = 'item form-group';
            document.getElementById('bran').className = 'hidden';
            document.getElementById('datefrom').className = 'hidden';
            document.getElementById('dateto').className = 'hidden';
        } else if (reportType === "3") {
            document.getElementById('fromlbl').innerHTML = 'From Date';
            document.getElementById('cust').className = 'hidden';
            document.getElementById('bran').className = 'hidden';
            document.getElementById('datefrom').className = 'item form-group';
            document.getElementById('dateto').className = 'item form-group';
        } else if (reportType === "4") {
            document.getElementById('fromlbl').innerHTML = 'Date';
            document.getElementById('cust').className = 'hidden';
            document.getElementById('bran').className = 'hidden';
            document.getElementById('datefrom').className = 'item form-group';
            document.getElementById('dateto').className = 'hidden';
        }else if (reportType === "5") {
            document.getElementById('fromlbl').innerHTML = 'hidden';
            document.getElementById('cust').className = 'hidden';
            document.getElementById('bran').className = 'item form-group';
            document.getElementById('datefrom').className = 'hidden';
            document.getElementById('dateto').className = 'hidden';
        }
    }
    
    //send data to server side
     function SentData(){
        var cus=document.getElementById('customerId').value;
        var branch=document.getElementById('branch').value;
        var type=document.getElementById('reportType').value;
        var fromDate=document.getElementById('fromDate').value;
        var toDate=document.getElementById('toDate').value;
        window.location = 'Invoice?action=LoadCustomerInvoice&customer='+cus+"&branch="+branch+"&type="+type+"&from="+fromDate+"&to="+toDate; 
    }
</script>



<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Invoice
            </h3>
        </div>
    </div>
    <%
        List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");
    %>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Maintain Invoice<small>to update,view and delete invoices</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form action="Invoice?action=LoadCustomerInvoice" method="post">

                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Search Type 
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="reportType" id="reportType"  required="required" onchange="ChangeFilter()">
                                    <option selected="true" disabled value="">Select Type</option>
                                    <option value="1">All</option>
                                    <option value="2">Customer Wise</option>
                                    <option value="3">Date Range</option>
                                    <option value="4">Daily Invoice</option>
                                    <option value="5">Branch Wise</option>
                                </select>                              
                            </div>
                        </div>
                        <div class="hidden" style="padding-top: 50px;" id="cust">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Customer <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="customerId" id="customerId"  required="required">
                                    <option selected="true" disabled value="">Select Customer</option>
                                    <%for (Customer c : customerList) {%>
                                    <option value="<%=c.getCustomerId()%>"><%=c.getCustomerName()%></option>
                                    <%}%>
                                </select>                              
                            </div>
                        </div>
                        <div class="hidden" style="padding-top: 50px;" id="bran">
                                <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Branch 
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <select class="form-control" name="branch" id="branch"  required="required" onchange="ChangeFilter()">
                                        <option selected="true" disabled value="">Select Branch</option>
                                        <%
                                            SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                            Session ses = sf.openSession();
                                            SQLQuery query = ses.createSQLQuery("SELECT branch_id,branch_name FROM branch");
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
                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" id="updateP" onclick="SentData()"><i class="fa fa-arrow-right"></i>Go</button>
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

