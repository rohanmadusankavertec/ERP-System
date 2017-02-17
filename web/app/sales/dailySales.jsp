
<%@page import="com.vertec.hibe.model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="../js/sales.js"></script>
<script src="../js/notAlert.js"></script>



<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Sales Details

            </h3>
        </div>

    </div>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Sales Management<small></small></h2>
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
                            <input type="date" id="toDate" name="toDate" required="required" placeholder="UpTo Date" class="form-control col-md-7 col-xs-12">
                        </div>
                    </div> 
                    <div class="item form-group" style="padding-top: 50px;">
                        <div class="col-xs-12 col-lg-offset-3">
                            <button class="btn btn-success" id="viewSales" onclick="checkSalesDetails();"><i class="fa fa-arrow-right"></i>  View Sales </button>
                        </div>
                    </div> 

                </div>
            </div>
            <div class="" id="salesLoadTable">
                <div class="x_panel">
                    <div class="x_title">

                        <ul class="nav navbar-right panel_toolbox">
                            <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                            </li>


                            <li><a class="close-link"><i class="fa fa-close"></i></a>
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <div class="table-responsive">
                            <table id="example" class="table table-striped responsive-utilities jambo_table">
                                <thead>
                                    <tr class="headings">

                                        <th>Date</th>
                                        <th>Total Sale</th>

                                        </th>
                                    </tr>
                                </thead>

                                <tbody>



                                </tbody>

                            </table>
                        </div>
                    </div>
                </div>
            </div>


        </div>
    </div>
</div>


<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
