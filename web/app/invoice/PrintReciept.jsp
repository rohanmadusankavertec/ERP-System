<%-- 
    Document   : PrintInvoice
    Created on : Sep 20, 2016, 4:53:29 PM
    Author     : vertec-r
--%>

<%@page import="com.vertec.hibe.model.OutstandigInvoice"%>
<%@page import="com.vertec.hibe.model.InvoiceItem"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.vertec.hibe.model.Invoice"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        
        <link href="resources/css/bootstrap.min.css" rel="stylesheet">
        <link href="resources/css/bootstrap-dialog.min.css" rel="stylesheet">
        <link href="resources/fonts/css/font-awesome.min.css" rel="stylesheet">
        <link href="resources/css/animate.min.css" rel="stylesheet">

        <link href="resources/css/custom.css" rel="stylesheet">
        <link href="resources/css/floatexamples.css" rel="stylesheet" type="text/css" />
        <link href="resources/css/datatables/tools/css/dataTables.tableTools.css" rel="stylesheet">

        <script type="text/javascript">
            function onLoadMethods() {
                window.print();
                window.onfocus = function() {
                    window.close();
                }
            }
        </script>

    </head>
    <body onload="onLoadMethods()" style="background-color: white; ">
        
        <%
            List<InvoiceItem> invoiceList = (List<InvoiceItem>) request.getAttribute("iiList");
            Invoice invoice = (Invoice) request.getAttribute("invoice");
            OutstandigInvoice outstanding = (OutstandigInvoice) request.getAttribute("outs");
            NumberFormat formatter = new DecimalFormat("#,###.00");

            double payment = 0.00;

            if (outstanding != null) {
                payment = invoice.getTotAfterDiscount() - outstanding.getBalanceAmount();
            } else {
                System.out.println("OUTSTANDING NULL");
                payment = invoice.getTotAfterDiscount();
            }


        %>
        <div>
            <div class="page-title">
                <div class="title_left">
                    <h3 style="width: 900px;">
                    </h3>
                </div>
            </div>
            <div class="clearfix"></div>

            <div class="row">
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_title">
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <section class="content invoice">
                                <div class="row">
                                    <div class="col-xs-12 invoice-header">
                                        <h1>
                                            <i class="fa fa-globe"></i> Invoice.
                                            <small class="pull-right"><%                                                if (invoice.getInvoicedDate().toString().endsWith("0")) {
                                                    out.write(invoice.getInvoicedDate().toString().substring(0, invoice.getInvoicedDate().toString().length() - 1));
                                                } else {
                                                    out.write(invoice.getInvoicedDate().toString());
                                                }
                                                %><span id="datetime"></span></small>
                                        </h1>
                                    </div>
                                </div>
                                <div class="row invoice-info">
                                    <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                        <address>
                                            <strong>
                                                Invoice NO: INV<%=invoice.getInvoiceId()%><br>
                                        <div class="clearfix"></div>
                                            </strong><br/>
                                        </address>
                                    </div>
                                    <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                        <strong>
                                        To
                                        <%
                                            String caddr = "";
                                            if (invoice.getCustomerId().getAddress() != null) {
                                                if (invoice.getCustomerId().getAddress().contains(",")) {
                                                    caddr = invoice.getCustomerId().getAddress().replace(",", "</br>");
                                                }
                                            }
                                        %>
                                        <address>
                                            <%= invoice.getCustomerId().getCustomerName()%><br>
                                            <%=caddr%>
                                        </address>
                                             </strong>
                                    </div>
                                </div>
                                <div class="row" style="padding-top: 20px;">
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
                                            <%
                                                for (InvoiceItem result : invoiceList) {
                                                    InvoiceItem ii = ((InvoiceItem) result);
                                            %>
                                            <tbody id="invoiceItemBody">
                                            <td><%= ii.getProductMasterId().getProductId().getProductCode() + " " + ii.getProductMasterId().getProductId().getProductName()%></td>
                                            <td><% out.println(formatter.format(ii.getProductMasterId().getSellingPrice()));%></td>
                                            <td><% out.println(ii.getQuantity());%></td>
                                            <td><% out.println(formatter.format(ii.getTotAmount()));%></td>
                                            <td><%
                                                if (ii.getDiscount() > 0) {
                                                    out.println(formatter.format(ii.getDiscount()));
                                                } else {
                                                    out.println("-");
                                                }
                                                %></td>
                                            <td><% out.println(formatter.format(ii.getTotAfterDis()));%></td>
                                            </tbody>
                                            <%}%>
                                        </table>
                                    </div>
                                </div>

                                <div class="item form-group" id="tottableDiv">
                                    <div class="col-xs-6">
                                    </div>
                                    <div class="col-xs-6" >
                                        <div class="table-responsive">
                                            <table class="table">
                                                <tbody>
                                                    <tr>
                                                        <th style="width:50%">Subtotal:</th>
                                                        <td id="subtot">
                                                            <% out.println(formatter.format(invoice.getInvoiceTotal()));%>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th>Discount</th>
                                                        <td>
                                                            <%
                                                                if (invoice.getDiscount() > 0) {
                                                                    out.println(formatter.format(invoice.getDiscount()));
                                                                } else {
                                                                    out.println("-");
                                                                }
                                                            %>


                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th>Grand Total</th>
                                                        <td id="totaftdis">
                                                            <% out.println(formatter.format(invoice.getTotAfterDiscount()));%>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <th>Payment Amount:</th>
                                                        <td> 
                                                            <%
                                                                if (payment > 0) {
                                                                    out.println(formatter.format(payment));
                                                                } else {
                                                                    out.println("-");
                                                                }
                                                            %>
                                                            
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th>Outstanding:</th>
                                                        <td id="ost">
                                                            <%
                                                                if (outstanding.getBalanceAmount() > 0) {
                                                                    out.println(formatter.format(outstanding.getBalanceAmount()));
                                                                } else {
                                                                    out.println("-");
                                                                }
                                                            %>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <!-- /.col -->
                                </div>

                            </section>
                        </div>
                    </div>
                </div>



                

                <div class="clearfix"></div>

                <div>
                    <center>
                        <br>
                        <br>
                        <br>
                        Software Developed by Vertec IT Solutions!
                    </center>
                </div>
                </body>
                </html>
