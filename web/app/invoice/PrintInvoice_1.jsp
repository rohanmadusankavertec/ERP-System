<%-- 
    Document   : PrintInvoice
    Created on : Sep 20, 2016, 4:53:29 PM
    Author     : vertec-r
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <link href="../../resources/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../resources/css/bootstrap-dialog.min.css" rel="stylesheet">
        <link href="../../resources/fonts/css/font-awesome.min.css" rel="stylesheet">
        <link href="../../resources/css/animate.min.css" rel="stylesheet">

        <link href="../../resources/css/custom.css" rel="stylesheet">
        <link href="../../resources/css/floatexamples.css" rel="stylesheet" type="text/css" />
        <link href="../../resources/css/datatables/tools/css/dataTables.tableTools.css" rel="stylesheet">



    </head>
    <body onload="window.print()" style="background-color: white;">
    <!--<body style="background-color: white;">-->

        <div>
            <div class="page-title">
                <div class="title_left">
                    <h3 style="width: 900px;">


                        <center>
                            <img src="../../resources/images/logo.png" width="50" height="50"> <h1>SUMAGA MARKETING</h1>
                            <small>
                                No.28/4, Gnanendra Road, 2nd Lane, Ratmalana, Sri Lanka.<br>
                                (+94)11 263 7676 / (+94)11 245 1967 / (+94)77 367 8284
                            </small>





                        </center>
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
                                <!-- title row -->
                                <div class="row">
                                    <div class="col-xs-12 invoice-header">
                                        <h1>
                                            <i class="fa fa-globe"></i> Invoice.
                                            <small class="pull-right">2016-09-20 12.00.00<span id="datetime"></span></small>
                                        </h1>
                                    </div>
                                    <!-- /.col -->
                                </div>
                                <!-- info row -->
                                <div class="row invoice-info">
                                    <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                        Issued By :

                                        <address>
                                            <strong>User Name</strong><br/>

                                        </address>
                                        Bill No : 00001
                                        <div class="clearfix"></div>
                                        Invoice Category : Invoice Category
                                    </div>
                                    <!-- /.col -->
                                    <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                        To
                                        <input type="hidden" id="customerId" name="customerId" required="required" class="form-control col-md-7 col-xs-12" value="">

                                        <address>
                                            <strong>Customer Name Here</strong><br/>
                                            No 123<br>
                                            templer Road.<br>
                                            Mt. Lavinia
                                        </address>
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
                                            <tbody id="invoiceItemBody">
                                            <td>Plastic Riverting Machine</td>
                                            <td>10000.00</td>
                                            <td>1</td>
                                            <td>10000.00</td>
                                            <td>0000.00</td>
                                            <td>10000.00</td>

                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- /.col -->
                                </div>
                                <!-- /.row -->

                                <div class="item form-group" id="tottableDiv">
                                    <div class="col-xs-6">

                                    </div>
                                    <div class="col-xs-6" >
                                        <div class="table-responsive">
                                            <table class="table">
                                                <tbody>
                                                    <tr>
                                                        <th style="width:50%">Subtotal:</th>
                                                        <td id="subtot">0000.00</td>
                                                    </tr>
                                                    <tr>
                                                        <th>Discount</th>
                                                        <td>
                                                            0000.00</td>
                                                    </tr>
                                                    <tr>
                                                        <th>Total After DIscount</th>
                                                        <td id="totaftdis">0000.00</td>
                                                    </tr>
                                                    <tr>
                                                        <th>Total:</th>
                                                        <td id="gTot">0000.00</td>
                                                    </tr>
                                                    <tr>
                                                        <th>Payment Type:</th>
                                                        <td>
                                                            CASH
                                                        </td>
                                                    </tr>
                                                    <tr class="hidden" id="cn">
                                                        <th>Cheque No</th>
                                                        <td>
                                                            12345-678-9123
                                                        </td>
                                                    </tr>
                                                    <tr class="hidden" id="bn">
                                                        <th>Bank Name:</th>
                                                        <td>
                                                            Bank of Ceylon
                                                        </td>
                                                    </tr>
                                                    <tr class="hidden" id="cd">
                                                        <th>Cheque Date:</th>
                                                        <td> 2016-09-20 12.00.00</td>
                                                    </tr>
                                                    <tr>
                                                        <th>Payment Amount:</th>
                                                        <td> 
                                                            0000.00
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th>Outstanding:</th>
                                                        <td id="ost">0000.00</td>
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
                
                
                
                    <div style="margin: 10px auto; width: 100%;">
                        
                        <ul style="list-style: none; width:100%;">
                            <li style="display: inline-block; margin-left: 10px; ">
                                COLOMBO BRANCH<br>
                        No : F-35,People's Park Shopping Complex,Colombo 11.<br>
                        Tel: +94 112451967 Mobile: +94768020344<br>
                        Email : sumaga.col@gmail.com
                            </li>
                            <li style="display: inline-block; margin-left: 100px; margin-right: 10px;">
                                RATHMALANA BRANCH<br>
                        No : 433 E3,Galle Road,Rathmalana.<br>
                        Tel: +94 112624466 / +94 11 4578993 / +94 769266145<br>
                        Email : sumaga.rat@gmail.com
                            </li>
                        </ul>
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
