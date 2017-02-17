<%-- 
    Document   : PrintInvoice
    Created on : Sep 20, 2016, 4:53:29 PM
    Author     : vertec-r
--%>

<%@page import="com.vertec.hibe.model.Customer"%>
<%@page import="com.vertec.hibe.model.InvoicePayment"%>
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
            
            
            List<InvoicePayment> payList = (List<InvoicePayment>) request.getAttribute("invoicePay");
            Customer cus = (Customer) request.getAttribute("customer");
            
            NumberFormat formatter = new DecimalFormat("#,###.00");
            String pay = (String) request.getAttribute("pay");
            String arrList = (String) request.getAttribute("arrList");
            
            String arr[] = arrList.split(",");
                
                
            double paymet = Double.parseDouble(pay);
            double paymet1 = Double.parseDouble(pay);
            
            
            System.out.println("------tot payment-----"+paymet);
//            System.out.println(">>>>>>>>>>>>>"+balance);
//            System.out.println(">>>>>>>>>>>>>"+paymet);
//            System.out.println(">>>>>>>>>>>>>"+tot);
//            


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
                                            <small class="pull-right">
                                                <% // // //                                                if (invoice.getInvoicedDate().toString().endsWith("0")) {
//                                                    out.write(invoice.getInvoicedDate().toString().substring(0, invoice.getInvoicedDate().toString().length() - 1));
//                                                } else {
//                                                    out.write(invoice.getInvoicedDate().toString());
//                                                }
                                                %>
                                                <span id="datetime"></span></small>
                                        </h1>
                                    </div>
                                </div>
                                <div class="row invoice-info">
                                    <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                        <address>
                                            <strong>
                                                
                                        <div class="clearfix"></div>
                                            </strong><br/>
                                        </address>
                                    </div>
                                    <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                        <strong>
                                        To
                                        <%
                                            String caddr = "";
                                            if (cus.getAddress() != null) {
                                                if (cus.getAddress().contains(",")) {
                                                    caddr = cus.getAddress().replace(",", "</br>");
                                                }
                                            }
                                        %>
                                        <address>
                                            <%=cus.getCustomerName()%>
                                            <br>
                                            <%=caddr%>
                                        </address>
                                             </strong>
                                    </div>
                                </div>
                         
                         
                         
                         
                                        
                         <div style="text-align: center;" class="item form-group" id="tottableDiv">
                                    <div class="col-xs-12">
                                    </div>
                                    <div class="col-xs-12" >
                                        <div class="table-responsive">
                                            <center>
                                                <table style="" class="table-responsive">
                                                <tr style="padding-bottom: 15px;">
                                                    <th style="padding-bottom: 15px;padding-right: 25px">Invoice Id</th>
                                                        <th style="padding-bottom: 15px;padding-right: 25px">Sub Total</th>
                                                        <th style="padding-bottom: 15px;padding-right: 25px">Discount</th>
                                                        <th style="padding-bottom: 15px;padding-right: 25px">Grand Total</th>
                                                        <th style="padding-bottom: 15px;padding-right: 25px">Total Paid</th>
                                                        <th style="padding-bottom: 15px;padding-right: 25px">Payment Amount</th>
                                                        
                                                        <th style="padding-bottom: 15px;padding-right: 25px">Outstanding</th>
                                                        
                                                </tr>
                                                <tbody>
                                                    <% 
                                                        int i=0;
                                                        for(InvoicePayment p: payList)

                                                    {%>
                                                    
                                                    <tr style="padding: 15px;">
                                                        <!--<th style="width:50%">Invoice Id:</th>-->
                                                        <td style="padding-bottom: 15px;padding-right: 25px">
                                                         INV:   <%=p.getInvoiceId().getInvoiceId() %>
                                                        </td>
                                                        <td style="padding-bottom: 15px;padding-right: 25px">
                                                            <% out.println(formatter.format(p.getInvoiceId().getInvoiceTotal()));%>
                                                        </td>
                                                        <td style="padding-bottom: 15px;padding-right: 25px"><%
                                                                if (p.getInvoiceId().getDiscount() > 0) {
                                                                    out.println(formatter.format(p.getInvoiceId().getDiscount()));
                                                                } else {
                                                                    out.println("-");
                                                                }

                                                            
                                                              %>

                                                        </td>
                                                        <td style="padding-bottom: 15px;padding-right: 25px">
                                                            <% out.println(formatter.format(p.getInvoiceId().getTotAfterDiscount()));%>
                                                        </td>
                                                        <td style="padding-bottom: 15px;padding-right: 25px">
                                                            <% out.println(formatter.format(p.getInvoiceId().getTotAfterDiscount()-Double.parseDouble(arr[i])));%>
                                                        </td>
                                                        
                                                        <td style="padding-bottom: 15px;padding-right: 25px"> 
                                                            <% if(Double.parseDouble(arr[i])< paymet){
                                                                            out.println((arr[i]));
                                                                             paymet = paymet-Double.parseDouble(arr[i]);
                                                                         }else{
                                                                            out.print(formatter.format(paymet));
                                                                        }
                                                            %>
                                                            
                                                        </td>
                                                        
                                                        <td style="padding-bottom: 15px;padding-right: 25px">
                                                            <%
                                                               
                                                                if(Double.parseDouble(arr[i])< paymet1){
                                                                    out.println(0.0);
                                                                    paymet1=paymet1-Double.parseDouble(arr[i]);
                                                                }else{
                                                                    out.print(formatter.format(Double.parseDouble(arr[i])- paymet1));
                                                                } 
                                                            %>
                                                        </td>
                                                        
                                                        
                                                        
                                                    </tr>
                                                    <%
                                i++;
                                }
                            %>
                                                </tbody>
                                            </table>
                                                        </center>
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
