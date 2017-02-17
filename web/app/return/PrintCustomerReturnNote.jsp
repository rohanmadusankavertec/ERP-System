<%-- 
    Document   : PrintInvoice
    Created on : Sep 20, 2016, 4:53:29 PM
    Author     : vertec-r
--%>


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
            List<ReturnByCustomer> returnedList = (List<ReturnByCustomer>) request.getAttribute("rbc");
            CustomerReturn customerReturn = (CustomerReturn) request.getAttribute("cr");
            
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
                                            <i class="fa fa-globe"></i> Good Return Note.
                                            <small class="pull-right"><% %><span id="datetime"></span></small>
                                        </h1>
                                    </div>
                                </div>
                                <div class="row invoice-info">
                                    <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                        <address>
                                            <strong>
                                                GRN NO: CR<%=customerReturn.getId() %><br>
                                        <div class="clearfix"></div>
                                            </strong><br/>
                                        </address>
                                    </div>
                                    <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                        <strong>
                                        To
                                        <%
                                            String caddr = "";
                                            if (customerReturn.getInvoiceId().getCustomerId().getAddress() != null) {
                                                if (customerReturn.getInvoiceId().getCustomerId().getAddress().contains(",")) {
                                                    caddr = customerReturn.getInvoiceId().getCustomerId().getAddress().replace(",", "</br>");
                                                }
                                            }
                                        %>
                                        <address>
                                            <%= customerReturn.getInvoiceId().getCustomerId().getCustomerName()%><br>
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
                                                    <th>Total </th>
                                                </tr>
                                            </thead>
                                            <%
                                                double Total=0.0;
                                                
                                                for (ReturnByCustomer result : returnedList) {
                                                    Total+=(Double.parseDouble(result.getInvoiceItemId().getQuantity()+"")*result.getInvoiceItemId().getUnitPrice());
                                            %>
                                            <tbody id="invoiceItemBody">
                                            <td><%=result.getInvoiceItemId().getProductMasterId().getProductId().getProductName() %></td>
                                            <td><%=result.getInvoiceItemId().getUnitPrice() %></td>
                                            <td><%=result.getInvoiceItemId().getQuantity() %></td>
                                            <td><%=(Double.parseDouble(result.getInvoiceItemId().getQuantity()+"")*result.getInvoiceItemId().getUnitPrice()) %></td>
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
