
<%@page import="com.vertec.hibe.model.Company"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Session"%>
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.SessionFactory"%>

<%@page import="com.vertec.hibe.model.Branch"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/invoice.js"></script>
<script src="app/js/notAlert.js"></script>

<script>
    function sm_warning(text) {
        BootstrapDialog.show({
            title: 'Warning',
            type: BootstrapDialog.TYPE_WARNING,
            message: text,
            size: BootstrapDialog.SIZE_SMALL
        });
    }


    function nom_Success(text) {
        BootstrapDialog.show({
            title: 'Notification',
            type: BootstrapDialog.TYPE_SUCCESS,
            message: text,
            size: BootstrapDialog.SIZE_NORMAL
        });
    }    
    
    
    
    
 function SentData(){
         
        var branch=document.getElementById('branch').value;
        var fromDate=document.getElementById('fromDate').value;
        var toDate=document.getElementById('toDate').value;
//        alert(branch);
        
        
        if(branch === "Select Branch"){
            sm_warning("Please Select the Branch....");
        }else if(fromDate === ""){
            sm_warning("Please Select the From Date....");
        }else if(toDate === ""){
            sm_warning("Please Select the To Date....");
        }else{
            window.location = '../../Invoice?action=SelectDates&branch='+branch+"&fromDate="+fromDate+"&toDate="+toDate; 
    
        }
} 
</script>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                View Invoices
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
                    <h2>View Invoices <small> </small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>

                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <!--<form  method="post">-->

                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Branch 
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="branch" id="branch"  >
                                    <option selected="true" disabled value="Select Branch">Select Branch</option>
                                    <option value="ALL">All</option>
                                    <%
                                        HttpSession httpSession = request.getSession();
                                        Company company = (Company) httpSession.getAttribute("company");
                                        SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                        Session ses = sf.openSession();
                                        String hql = "SELECT branch_id,branch_name FROM branch WHERE company_id='" + company.getId() + "'";
                                        SQLQuery query = ses.createSQLQuery(hql);
                                        List<Object[]> inList = query.list();
                                        for (Object[] list : inList) {
                                    %>
                                    <option value="<%= list[0].toString()%>"><%= list[1].toString()%></option>

                                    <%}%>

                                </select>                              
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">From Date <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="fromDate" name="fromDate"  placeholder="From Date" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>  
                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">To Date <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="toDate" name="toDate"  placeholder="UpTo Date" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>  
                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button type="button" class="btn btn-success" onclick="SentData();" id="viewInvoices"><i class="fa fa-arrow-right"></i>  View Invoices </button>
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

