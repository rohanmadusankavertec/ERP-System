

<%@page import="com.vertec.hibe.model.Branch"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/payment.js"></script>
<script src="app/js/notAlert.js"></script>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Payment
                <small>
                    <!--                    Some examples to get you started-->
                </small>
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
                    <h2>Payment <small>Sample user invoice design</small></h2>
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
                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Customer <span class="required"></span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="customerId" id="customerId"  required="required" onchange="loadChequeCust();">
                                <option selected="true" disabled value="">Select Customer</option>

                                <%for (Customer c : customerList) {%>
                                <option value="<%=c.getCustomerId()%>"><%=c.getCustomerName()%></option>
                                <%}%>

                            </select>

                        </div>
                    </div>  
                                
                    <div class="row" style="padding-top: 100px;">
                         <div class="table-responsive">
                        <div class="col-xs-12 table">
                            
                            <table class="table table-striped" id="chequeStaTable">
                                <thead id="chequeStaHead">

                                </thead>
                                <tbody id="chequeStaBody">


                                </tbody>
                            </table>
                        </div>
                        <!-- /.col -->
                    </div>
                    </div>

                    
                </div>
            </div>
        </div>
    </div>
</div>
                                

<!-- footer content -->
<%@include file="../../template/footer.jsp"%>

