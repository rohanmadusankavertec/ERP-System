
<%@page import="com.vertec.hibe.model.InvoiceItem"%>
<%@page import="com.vertec.hibe.model.Invoice"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%@page import="com.vertec.hibe.model.Branch"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/invoice.js"></script>
<script src="app/js/notAlert.js"></script>
<%if (ca.checkUserAuth("VIEW_INVOICE", group) != null) {%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Invoice
                <small>
                    <!--                    Some examples to get you started-->
                </small>
            </h3>
        </div>

    </div>
    <%
        List<InvoiceItem> invoiceList = (List<InvoiceItem>) request.getAttribute("iiList");
        Invoice invoice = (Invoice) request.getAttribute("invoice");
        NumberFormat formatter = new DecimalFormat("#,###.00");
    %>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>View Invoice
                        <!--                        <small>to update,view and delete invoices</small>-->
                    </h2>
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
                        <table>
                                <tr>
                                    <td>Invoice No</td>
                                    <td> : </td>
                                    <td><%=invoice.getInvoiceId()%></td>
                                </tr>
                                <tr>
                                    <td>Date</td>
                                    <td> : </td>
                                    <td><%=invoice.getInvoicedDate()%></td>
                                </tr>
                            </table>
                                
                                
                                
                                
                                
                                
<!--                        <table class="table table-bordered table-hover table-striped" id="invDate" >

                            <tbody id="gendata">

                                <tr>
                                    <td width="20%" align="center" > Invoice NO</td>
                                    <td width="20%" align="center" > <%=invoice.getInvoiceId()%></td>

                                </tr>
                                <tr>
                                    <td width="20%" align="center" > Invoice Date</td>
                                    <td width="20%" align="center" > <%=invoice.getInvoicedDate()%></td>

                                </tr>
                                <tr>
                                    <td width="20%" align="center" > Invoice Total</td>
                                    <td width="20%" align="center" > <%=formatter.format(invoice.getInvoiceTotal())%></td>

                                </tr>
                                <tr>
                                    <td width="20%" align="center" > Discount</td>
                                    <td width="20%" align="center" > <%=formatter.format(invoice.getDiscount())%></td>

                                </tr>
                                <tr>
                                    <td width="20%" align="center" > Vat</td>
                                    <td width="20%" align="center" > <%=formatter.format((invoice.getTotAfterDiscount() - invoice.getInvoiceTotal() - invoice.getDiscount()))%></td>

                                </tr>
                                <tr>
                                    <td width="20%" align="center" > Invoice Total Gross Amount</td>
                                    <td width="20%" align="center" > <%=formatter.format(invoice.getTotAfterDiscount())%></td>

                                </tr>
                            </tbody>
                        </table>-->
                    </div>

                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
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
                        <table class="table table-bordered table-hover table-striped" id="invDate" >
                            <thead>
                                <tr>
                                    <!--                                            <th bgcolor="#EFEFEF" >Invoice ID</th>-->
                                    <th bgcolor="#EFEFEF" ><center>Item Code</center></th>
                            <th bgcolor="#EFEFEF" ><center>Quantity</center></th>
                            <th bgcolor="#EFEFEF" ><center>Amount</center></th>
                            <th bgcolor="#EFEFEF" ><center>Discount</center></th>
                            <th bgcolor="#EFEFEF" ><center>GrossAmount</center></th>

                            </tr>
                            </thead>
                            <tbody id="gendata">
                                <%

                                    for (InvoiceItem result : invoiceList) {
                                        InvoiceItem ii = ((InvoiceItem) result);


                                %>
                                <tr>
<!--                                            <td width="20%" align="center" > <% out.println(ii.getProductMasterId().getProductId().getProductCode());%></td>-->
                                    <td width="20%" align="center" > <% out.println(ii.getProductMasterId().getProductId().getProductCode());%></td>
                                    <td width="30%" align="center" > <% out.println(ii.getQuantity());%></td>
                                    <td width="30%" align="center" > <% out.println(formatter.format(ii.getTotAmount()));%></td>
                                    <td width="30%" align="center" > <% out.println(formatter.format(ii.getDiscount()));%></td>
                                    <td width="30%" align="center" > <% out.println(formatter.format(ii.getTotAfterDis()));%></td>
                                </tr>
                                <%}%>

                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    </div>
                                
                                
                                
                                <table style="float: right;">
                                    <tr>
                                        <td>Total</td>
                                        <td>:</td>
                                        <td><%=formatter.format(invoice.getInvoiceTotal())%></td>
                                    </tr>
                                    <tr>
                                        <td>Discount</td>
                                        <td>:</td>
                                        <td><%=formatter.format(invoice.getDiscount())%></td>
                                    </tr>
                                    <tr>
                                        <td>Invoice Total</td>
                                        <td>:</td>
                                        <td><%=formatter.format(invoice.getTotAfterDiscount())%></td>
                                    </tr>
                                </table>                  
                                
                                
                                
                                
                                
                                
    <div class="row no-print">
        <div class="col-xs-12">
            <button class="btn btn-default" onclick="window.print();"><i class="fa fa-print"></i> Print</button>

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

