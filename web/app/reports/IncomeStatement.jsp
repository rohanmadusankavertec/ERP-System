<%-- 
    Document   : ProfitLoss
    Created on : Nov 26, 2016, 9:08:08 AM
    Author     : Java-Dev-Ruchira
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                 New Profit And Loss 
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
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                            
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <form  action="Report?action=IncomeStatement" method="post" target="_blank" class="form-horizontal form-label-left" novalidate>

                        </p>
                        <span class="section">Profit And Loss  Info</span>
                        
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">From Date</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="fromDay" type="date" name="fromDay" data-validate-linked="password" class="form-control col-md-7 col-xs-12" required="required">
                                
                            </div>
                        </div>
                                
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">To Date </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="toDate" type="date" name="toDay" data-validate-linked="password" class="form-control col-md-7 col-xs-12" required="required">
                            </div>
                        </div> 
                        
                        
                        
                        
                        
                        
                        
                        <div class="ln_solid"></div>        
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <!--                                <button type="submit" class="btn btn-primary">Cancel</button>-->
                                <!--<button id="send"  class="btn btn-success">Submit</button>-->
                                <button id="send" type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                        
                       


<!--                        <div class="ln_solid"></div>-->
                        
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>




<%@include file="../../template/footer.jsp"%>
