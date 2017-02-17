

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
                            <select class="form-control" name="customerId" id="customerId"  required="required" onchange="loadAccordingCus();">
                                <option selected="true" disabled value="">Select Customer</option>

                                <%for (Customer c : customerList) {%>
                                <option value="<%=c.getCustomerId()%>"><%=c.getCustomerName()%></option>
                                <%}%>

                            </select>

                        </div>
                    </div>  
                    <!--                        <div class="item form-group" style="padding-top: 50px;">
                                                <div class="col-xs-12 col-lg-offset-3">
                                                    <button class="btn btn-success" id="updateP"><i class="fa fa-arrow-right"></i>  Go </button>
                                                </div>
                                            </div> -->
                    <div class="row" style="padding-top: 100px;">
                        <div class="table-responsive">
                            <div class="col-xs-12 table">
                                <table class="table table-striped" id="paymentAvaTable">
                                    <thead>
                                        <!--                                    <tr>
                                                                                <th>Out_In_Id</th>
                                                                                <th>In ID</th>
                                                                                <th>Date</th>
                                                                                <th>Total Amount</th>
                                                                                <th>Balance</th>
                                                                            </tr>-->
                                    </thead>
                                    <tbody id="paymentAvaBody">


                                    </tbody>
                                </table>
                            </div>
                            <!-- /.col -->
                        </div>
                    </div>

                    <div class="hidden" id="paymentTable" style="padding-top: 100px;">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover table-striped" id="invDate" >

                                <tbody id="gendata">

                                    <tr>
                                <div class="clearfix"></div>
                                        <td width="20%" align="center" > Invoice ID</td>
                                        <td width="20%" align="center" > <input class="form-control" type="hidden" name="invoiceId" id="invoiceId" value=""/><input class="form-control" type="text" name="oiId" id="oiId" readonly/></td>

                                    </tr>
                                    <tr><div class="clearfix"></div>
                                        <td width="20%" align="center" > Invoice Amount</td>
                                        <td width="20%" align="center" > <input class="form-control" type="text" name="inAmount" id="inAmount" readonly/></td>

                                    </tr>
                                    <tr><div class="clearfix"></div>
                                        <td width="20%" align="center" > Balance Amount</td>
                                        <td width="20%" align="center" > <input class="form-control" type="text" name="balAmount" id="balAmount" readonly/></td>

                                    </tr>
                                    <tr><div class="clearfix"></div>
                                        <td width="20%" align="center" > Payment Type</td>
                                        <td width="20%" align="center" > <select class="form-control" name="paymentType" id="paymentType"  required="required" onchange="loadChekDetails();">
                                                <option selected="true" disabled value="">Select Payment Type</option>
                                                <option value="1">Cash Payment</option>
                                                <option value="2">Cheque Payment</option>

                                            </select>   </td>

                                    </tr>
                                    <tr><div class="clearfix"></div>
                                        <td width="20%" align="center" > Payment</td>
                                        <td width="20%" align="center" > <input class="form-control" type="text" name="payAmount" id="payAmount" onblur="checkAmunt();"/></td>

                                    </tr>

                                    <tr class="hidden" id="chequeNo"><div class="clearfix"></div>
                                        <td width="20%" align="center" > Cheque No</td>
                                        <td width="20%" align="center" > <input class="form-control" type="text" name="chkNo" id="chkNo"/></td>

                                    </tr>
                                    <tr class="hidden" id="bank"><div class="clearfix"></div>
                                        <td width="20%" align="center" > Bank Name</td>
                                        <td width="20%" align="center" > <input class="form-control" type="text" name="bankName" id="bankName"/></td>

                                    </tr>
                                    <tr class="hidden" id="dateChk"><div class="clearfix"></div>
                                        <td width="20%" align="center" > Cheque Date</td>
                                        <td width="20%" align="center" > <input class="form-control" type="date" name="chDate" id="chDate"/></td>

                                    </tr>
                                    <tr><div class="clearfix"></div>
                                        <td colspan="2"  align="center" > 
                                            <input type="button" onclick="submitPayment();" name="submitPay" class="col-lg-offset-3 col-md-offset-3 col-lg-5 col-md-6 col-sm-12 col-xs-12 btn btn-success" id="submitPay" value="Submit"/>
                                        </td>

                                    </tr>


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


<!-- footer content -->
<%@include file="../../template/footer.jsp"%>

