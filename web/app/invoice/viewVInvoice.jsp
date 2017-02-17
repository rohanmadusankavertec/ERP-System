

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page import="com.vertec.hibe.model.Branch"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/invoice.js"></script>
<script src="app/js/notAlert.js"></script>
<%if (ca.checkUserAuth("CREATE_INVOICE", group) != null) {%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Invoice
                <small>

                </small>
            </h3>
        </div>
        <%
            Branch branch = (Branch) request.getAttribute("branch");
            Customer customer = (Customer) request.getAttribute("customer");
           
            List<Object[]> pList = (List<Object[]>) request.getAttribute("pList");

            String bAddress = branch.getAddress().replace(",", "<br/>");
            String cAddress = customer.getAddress().replace(",", "<br/>");

            Date date = new Date();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String today = formatter.format(date);
        %>

    </div>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <!--                    <h2>Invoice Design <small>Sample user invoice design</small></h2>-->
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>

                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <section class="content invoice">
                        <!-- title row -->
                        <div class="row">
                            <div class="col-xs-12 invoice-header">
                                <h1>
                                    <i class="fa fa-globe"></i> Invoice.
                                    <small class="pull-right"><%=today%></small>
                                </h1>
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- info row -->
                        <div class="row invoice-info">
                            <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                From
                                <input type="hidden" id="branchId" name="branchId" required="required" class="form-control col-md-7 col-xs-12" value="<%=branch.getBranchId()%>">
                                
                                <address>
                                    <strong><%=user.getFirstName() + " " + user.getLastName()%></strong><br/>
                                    <%=bAddress%>
                                </address>
                            </div>
                            <!-- /.col -->
                            <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                To
                                <input type="hidden" id="customerId" name="customerId" required="required" class="form-control col-md-7 col-xs-12" value="<%=customer.getCustomerId()%>">

                                <address>
                                    <strong><%=customer.getCustomerName()%></strong><br/>
                                    <%=bAddress%>
                                </address>
                            </div>


                        </div>
                        <!-- /.row -->
                        <!-- Start Item row -->
                        <div class="row" style="padding-top: 60px;">
                            <div class="form-group" style="padding-top: 40px;">
                                <label for="itemSelect" class="control-label col-lg-3 col-md-3 lbl_name">Select Product : </label>
                                <div class="col-lg-6 col-md-6">
                                    <select class="form-control" name="productId" id="productId" onchange="loadVehiclePMToInvoice();">
                                        <option selected="true" value="" disabled="true">Select Product</option>
                                        <%for (Object[] p : pList) {%>
                                        <option value="<%=p[0].toString()%>"><%=p[1].toString() + "_" + p[2].toString()%></option>
                                        <%}%>
                                    </select>
                                </div>
                            </div>
                            <div class="hidden" style="padding-top: 40px;" id="pmasterDiv">
                                <label for="itemSelect" class="control-label col-lg-3 col-md-3 lbl_name">Select Price Master : </label>
                                <div class="col-lg-6 col-md-6">
                                    <select class="form-control" name="bpmId" id="bpmId" onchange="viewAvailableQuan();">
                                        <option selected="true" value="" disabled="true">Select Price Master</option>

                                    </select>
                                </div>
                                <div class="hidden" id="divAvaQuan">

                                </div>
                            </div>
                            <div class="hidden" style="padding-top: 40px;" id="quanDiv">
                                <label for="itemSelect" class="control-label col-lg-3 col-md-3 lbl_name">Enter Quantity</label>
                                <div class="col-lg-6 col-md-6">
                                    <input type="number" id="bpmQuantity" name="bpmQuantity" required="required" class="form-control col-lg-6 col-md-6 col-xs-12"/>
                                </div>

                            </div>
                            <div class="hidden" style="padding-top: 40px;" id="discountDiv">
                                <label for="itemSelect" class="control-label col-lg-3 col-md-3 lbl_name">Select Discount Type : </label>
                                <div class="col-lg-6 col-md-6">
                                    <select class="form-control" name="disType" id="disType" onchange="">
                                        <option  value="NODiscount">No Discount</option>
                                        <option  value="Percentage">Percentage</option>
                                        <option  value="Price">Price</option>
                                    </select>
                                </div>
                                <div class="col-lg-3 col-md-3">
                                    <input type="text" id="disAmount" name="disAmount" placeholder="Enter Discount Aount" class="form-control col-lg-6 col-md-6 col-xs-12"/>
                                </div>
                            </div>
                            <div class="hidden" style="padding-top: 40px;" id="btnDiv">
                                <button type="submit" class="btn btn-success pull-right col-lg-3 col-md-3" onclick="checkAddToGrid();"><i class="fa fa-briefcase"></i> Add</button>
                            </div>

                        </div>
                        <!-- Finish Item row -->
                        <!-- Table row -->
                        <div class="row" style="padding-top: 100px;">
                            <div class="col-xs-12 table">
                                <table class="table table-striped" id="invoiceItemTable">
                                    <thead>
                                        <tr>
                                            <th>Product</th>
                                            <th>Price</th>
                                            <th>Qty#</th>
                                            <th>Total #</th>
                                            <th>Discount</th>
                                            <th>Gross Total</th>
                                        </tr>
                                    </thead>
                                    <tbody id="invoiceItemBody">


                                    </tbody>
                                </table>
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->

                        <div class="hidden" id="tottableDiv">
                            <!-- accepted payments column -->
                            <div class="col-xs-6">

                            </div>
                            <!-- /.col -->
                            <div class="col-xs-6" >
                                <!--                                <p class="lead">Amount Due 2/22/2014</p>-->
                                <div class="table-responsive">
                                    <table class="table">
                                        <tbody>
                                            <tr>
                                                <th style="width:50%">Subtotal:</th>
                                                <td id="subtot"></td>
                                            </tr>
                                            <tr>
                                                <th>Discount</th>
                                                <td><input type="text" id="inDis" name="inDis" placeholder="Ex:10% or 10000" class="form-control" onblur="addDiscount();"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>Total After DIscount</th>
                                                <td id="totaftdis"></td>
                                            </tr>
                                            <tr>
                                                <th>Tax (10%)</th>
                                                <td id="tax"></td>
                                            </tr>
                                            <tr>
                                                <th>Total:</th>
                                                <td id="gTot"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->

                        <!-- this row will not appear when printing -->
                        <div class="row no-print">
                            <div class="col-xs-12">
                                <button class="btn btn-default" onclick="window.print();"><i class="fa fa-print"></i> Print</button>
                                <button class="btn btn-success pull-right" onclick="submitVInvoice();"><i class="fa fa-credit-card"></i> Submit Invoice</button>

                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</div>
<%} else {%>
<script type="text/javascript">
    window.location = 'error403.jsp';
</script>
<%}%>

<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
