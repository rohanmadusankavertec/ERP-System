

<%@page import="com.vertec.hibe.model.Branch"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/report.js"></script>
<script src="app/js/notAlert.js"></script>
<%if (ca.checkUserAuth("VIEW_STOCK_REPORT", group) != null) {%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Reports
                <small>

                </small>
            </h3>
        </div>
    </div>
    <%List<Branch> bList = (List<Branch>) request.getAttribute("bList");%>
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

                    <div class="item form-group" style="padding-top: 50px;">
                        <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Report Type <span class="required"></span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="reportType" id="reportType"  required="required" onchange="selectStockRepType();">
                                <option selected="true" disabled value="">Select Report Type</option>
                                <option value="1">Branch Wise Product Stock</option>
                                <option value="2">Branch Wise Product Master Stock</option>
                                <option value="3">Branch Wise Periodically Release Product</option>
                                <option value="4">Vehicle Wise Periodically Release Product</option>

                            </select>                              
                        </div>
                    </div>
                    <div class="hidden" style="padding-top: 50px;" id="selGroup">
                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Branch <span class="required"></span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="branchId" id="branchId" >
                                <option selected="true" disabled value="">Select Branch</option>

                                <%for (Branch b : bList) {%>
                                <option value="<%=b.getBranchId()%>"><%=b.getBranchName()%></option>
                                <%}%>
                            </select>                              
                        </div>
                    </div>
                    <div class="hidden" style="padding-top: 50px;" id="branchP">
                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" id="viewInvoices" onclick="viewBranchStock();"><i class="fa fa-arrow-right"></i>  View Product Stock </button>
                            </div>
                        </div>
                    </div>   
                    <div class="hidden" style="padding-top: 50px;" id="branchPM">


                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" id="viewInvoices" onclick="viewBranchPMStock();"><i class="fa fa-arrow-right"></i>  View PM Stock </button>
                            </div>
                        </div>
                    </div>   
                    <div class="hidden" style="padding-top: 50px;" id="branchRP">
                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">From Date <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="fromDate" name="fromDate" required="required" placeholder="From Date" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">To Date <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="toDate" name="toDate" required="required" placeholder="From Date" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" id="viewInvoices" onclick="viewReleasePM();"><i class="fa fa-arrow-right"></i>  View Outstanding </button>
                            </div>
                        </div>
                    </div>   
                    <div class="hidden" style="padding-top: 50px;" id="vStockP">
                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">From Date <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="fromADate" name="fromADate" required="required" placeholder="From Date" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">To Date <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="toADate" name="toADate" required="required" placeholder="From Date" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" id="viewInvoices" onclick="rProductVehicle();"><i class="fa fa-arrow-right"></i>  View Outstanding </button>
                            </div>
                        </div>
                    </div>   

                </div>
            </div>
        </div>

    </div>
</div>
<%} else {%>
<script type="text/javascript">
    window.location = '../../error403.jsp';
</script>
<%}%>
<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
<script type="text/javascript">
    $("#modal_trigger").leanModal({top: 200, overlay: 0.6, closeButton: ".modal_close"});

    $(function () {


        // Calling Register Form
        $("#modal_trigger").click(function () {
            $(".user_register").show();
            $(".header_title").text('Add Quick Customer');
            return false;
        });



    });
</script>