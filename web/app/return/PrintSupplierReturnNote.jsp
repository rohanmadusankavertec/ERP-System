<%-- 
    Document   : PrintInvoice
    Created on : Sep 20, 2016, 4:53:29 PM
    Author     : vertec-r
--%>


<%@page import="com.vertec.hibe.model.ReturnToSupplier"%>
<%@page import="com.vertec.hibe.model.SupplierReturn"%>
<%@page import="com.vertec.hibe.model.ReturnBySupplier"%>
<%@page import="com.vertec.hibe.model.ReturnByCustomer"%>
<%@page import="java.util.List"%>
<%@page import="com.vertec.hibe.model.CustomerReturn"%>
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
            List<ReturnToSupplier> returnedList = (List<ReturnToSupplier>) request.getAttribute("rbs");
            SupplierReturn supplierReturn = (SupplierReturn) request.getAttribute("sr");
            
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
                                            <i class="fa fa-globe"></i> Supplier Return Note.
                                            <small class="pull-right"><% %><span id="datetime"></span></small>
                                        </h1>
                                    </div>
                                </div>
                                <div class="row invoice-info">
                                    <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                        <address>
                                            <strong>
                                                Return NO: <%=supplierReturn.getId() %><br>
                                        <div class="clearfix"></div>
                                            </strong><br/>
                                        </address>
                                    </div>
                                    <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                        <strong>
                                        FROM
                                        <%
                                            String caddr = "";
                                            if (supplierReturn.getSupplierId().getAddress() != null) {
                                                if (supplierReturn.getSupplierId().getAddress().contains(",")) {
                                                    caddr = supplierReturn.getSupplierId().getAddress().replace(",", "</br>");
                                                }
                                            }
                                        %>
                                        <address>
                                            <%= supplierReturn.getSupplierId().getCompanyName() %><br>
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
                                                    <th>Qty</th>
                                                    <th>Total</th>
                                                </tr>
                                            </thead>
                                            <%
                                                double Total=0.0;
                                                for (ReturnToSupplier result : returnedList) {
                                                    Total+=(Double.parseDouble(result.getQty()+"")*result.getReturnStockId().getProductMasterId().getPurchasedPrice());
                                            %>
                                            <tbody id="invoiceItemBody">
                                            <td><%=result.getReturnStockId().getProductMasterId().getProductId().getProductName() %></td>
                                            <td><%=result.getReturnStockId().getProductMasterId().getPurchasedPrice() %></td>
                                            <td><%=result.getQty() %></td>
                                            <td><%=(Double.parseDouble(result.getQty()+"")*result.getReturnStockId().getProductMasterId().getPurchasedPrice()) %></td>
                                            </tbody>
                                            <%}%>
                                        </table>
                                    </div>
                                </div>
                                 <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                        <address>
                                            <strong>
                                                Grand Total <%=Total %><br>
                                        <div class="clearfix"></div>
                                            </strong><br/>
                                        </address>
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
