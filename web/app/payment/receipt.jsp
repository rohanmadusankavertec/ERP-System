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
            
            Invoice invoice = (Invoice) request.getAttribute("invoice");
            
            NumberFormat formatter = new DecimalFormat("#,###.00");
            
            double tot;
            
            Object tot2 =(Object) request.getAttribute("tot");
            String bal = (String) request.getAttribute("bal");
            String pay = (String) request.getAttribute("pay");
            
            tot=Double.valueOf(tot2+"");
            
            
            
            double balance = Double.parseDouble(bal);
            double paymet = Double.parseDouble(pay);
            tot-=paymet;
//            System.out.println(">>>>>>>>>>>>>"+invoice.getInvoiceId());
//            System.out.println(">>>>>>>>>>>>>"+balance);
//            System.out.println(">>>>>>>>>>>>>"+paymet);
//            System.out.println(">>>>>>>>>>>>>"+tot);
            


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
                                            <i class="fa fa-globe"></i> Receipt.
                                            <small class="pull-right"><%                                                if (invoice.getInvoicedDate().toString().endsWith("0")) {
                                                    out.write(invoice.getInvoicedDate().toString().substring(0, invoice.getInvoicedDate().toString().length() - 1));
                                                } else {
                                                    out.write(invoice.getInvoicedDate().toString());
                                                }
                                                %>
                                                <span id="datetime"></span></small>
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
                         
                                        <center>
                                <div style="text-align: center;" class="item form-group" id="tottableDiv">
                                    <div class="col-xs-6">
                                    </div>
                                    <div class="col-xs-6" >
                                        <div class="table-responsive">
                                            <table class="table">
                                                <tbody>
                                                    <tr>
                                                        <th style="width:50%">Invoice Id:</th>
                                                        <td id="">
                                                         INV:   <%=invoice.getInvoiceId() %>
                                                        </td>
                                                    </tr>
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
                                                        <th>Total Paid</th>
                                                        <td id="totalpaid">
                                                            <% out.println(formatter.format(tot));%>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <th>Payment Amount:</th>
                                                        <td> 
                                                            <%
                                                                
                                                                    out.println(pay);
                                                                
                                                            %>
                                                            
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th>Outstanding:</th>
                                                        <td id="ost">
                                                            <%
                                                               
                                                                    out.println(balance-paymet);
                                                                
                                                            %>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <!-- /.col -->
                                </div>
                               </center>                         

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
