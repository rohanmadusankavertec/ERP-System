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
    
    
    //show and hide  fields according to report types
    function ChangeFilter() {
        var reportType = document.getElementById('reportType').value;
        if (reportType === "1") {
            document.getElementById('datefrom').className = 'hidden';
            document.getElementById('dateto').className = 'hidden';
        } else if (reportType === "2") {
            document.getElementById('fromlbl').innerHTML = 'From Date';
            document.getElementById('datefrom').className = 'item form-group';
            document.getElementById('dateto').className = 'item form-group';
        } else if (reportType === "3") {
            document.getElementById('fromlbl').innerHTML = 'Date';
            document.getElementById('datefrom').className = 'item form-group';
            document.getElementById('dateto').className = 'hidden';
        }
    }
    // validatetion and send data to serverside
    function SentData() {
        
        
        var type = document.getElementById('reportType').value;
        var fromDate = document.getElementById('fromDate').value;
        var toDate = document.getElementById('toDate').value;
        if(type==="1"){
            window.location = "../../PRN?action=viewPRN&type=" + type + "&from=" + fromDate + "&to=" + toDate;
        }else if(type==="2"){
            if(fromDate === "" || toDate === ""  ){
                sm_warning("Please enter the dates..");
//                alert("Please enter the dates..");
            }else{
                window.location = "../../PRN?action=viewPRN&type=" + type + "&from=" + fromDate + "&to=" + toDate;
            }
        }else if(type==="3"){
            if(fromDate===""){
                sm_warning("Please enter the date..");
//                alert("Please enter the date..");
            }else{
                window.location = "../../PRN?action=viewPRN&type=" + type + "&from=" + fromDate + "&to=" + toDate;
            }
        }else{
            sm_warning("Please select the type..");
//            alert("Please select the type..");
        }
    }
</script>




<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Search Purchase Requisition Note
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
                    <!--<form action="../../PO?action=viewPO" method="post">-->

                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Search Type 
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="reportType" id="reportType"  required="required" onchange="ChangeFilter()">
                                    <option selected="true" disabled="true" value="">Select Type</option>
                                    <option value="1">All</option>
                                    <option value="2">Date Range</option>
                                    <option value="3">Daily Purchased Orders</option>
                                </select>                              
                            </div>
                        </div>
                    <div class="clearfix"></div>
                        <div class="hidden"  id="datefrom">           
                            <div class="item form-group" style="padding-top: 10px;">
                                <label class="control-label col-md-4 col-sm-4 col-xs-12" id="fromlbl" for="name">From Date <span class="required"></span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type="date" id="fromDate" name="fromDate" required="required" placeholder="From Date" class="form-control col-md-7 col-xs-12">
                                </div>
                            </div>
                        </div>
                     <div class="clearfix"></div>
                        <div class="hidden"  id="dateto">
                            <div class="item form-group" style="padding-top: 10px;">
                                <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">To Date <span class="required"></span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type="date" id="toDate" name="toDate" required="required" placeholder="From Date" class="form-control col-md-7 col-xs-12">
                                </div>
                            </div>
                        </div>
                      <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <!--<input type="submit" value="View Purchased Orders" class="btn btn-success"/>-->
                                <button class="btn btn-success" onclick="SentData();">View Purchase Requisition Note</button>
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
