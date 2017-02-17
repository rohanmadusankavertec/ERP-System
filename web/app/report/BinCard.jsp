<%-- 
    Document   : BinCard
    Created on : Jan 27, 2017, 2:21:29 PM
    Author     : Rohan
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vertec.hibe.model.BranchProductmaster"%>
<%@page import="com.vertec.hibe.model.Bin"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bin Card</title>
        <link rel="shortcut icon" href="${context}/resources/images/erp_icon.ico" />

        <link href="resources/css/bootstrap.min.css" rel="stylesheet">
        <link href="resources/css/bootstrap-dialog.min.css" rel="stylesheet">
        <link href="resources/fonts/css/font-awesome.min.css" rel="stylesheet">
        <link href="resources/css/animate.min.css" rel="stylesheet">

        <link href="resources/css/custom.css" rel="stylesheet">
        <link href="resources/css/floatexamples.css" rel="stylesheet" type="text/css" />
        <link href="resources/css/datatables/tools/css/dataTables.tableTools.css" rel="stylesheet">

    </head>
    <body onload="window.print()">

        <%
            List<Bin> binList = (List<Bin>) request.getAttribute("binList");
            BranchProductmaster pm = (BranchProductmaster) request.getAttribute("bpmid");
        %>



        <div>
            <div class="page-title">
                <div class="title_left">

                </div>
            </div>
            <div class="clearfix"></div>

            <div class="row">
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_title">
                            <div class="clearfix"><h1>BIN CARD</h1></div>
                        </div>
                        <div class="x_content">
                            <section class="content invoice">


                                <div class="item form-group" style="float: left; width: 100%;margin-left: -350px;">
                                    <div class="col-xs-6">
                                    </div>
                                    <div class="col-xs-6" >
                                        <div class="table-responsive">
                                            <table class="table">
                                                <tbody>
                                                    <tr>
                                                        <th style="width:50%">Product Name :</th>
                                                        <td id="subtot">
                                                            <%=pm.getProductMasterId().getProductId().getProductName()%>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th style="width:50%">Category :</th>
                                                        <td id="subtot">
                                                            <%=pm.getProductMasterId().getProductId().getProductCategoryId().getProductCategoryName()%>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th style="width:50%">Description :</th>
                                                        <td id="subtot">
                                                            <%=pm.getProductMasterId().getProductId().getProductDescription()%>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <!-- /.col -->
                                </div>

                                <div class="row" style="padding-top: 20px;">
                                    <div class="col-xs-12 table">
                                        <table class="table table-striped" id="invoiceItemTable">
                                            <tr >
                                                <td>No.</td>
                                                <td>Date</td>
                                                <td>In</td>
                                                <td>Out</td>
                                                <td>Balance</td>
                                                <td>Description</td>
                                            </tr>
                                            <%
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                int i = 0;
                                                int balance = 0;
                                                for (Bin b : binList) {
                                                    i++;
                                                    String d = sdf.format(b.getDate());
                                            %>
                                            <tr>
                                                <td><%=i%></td>
                                                <td><%=d%></td>
                                                <%if (b.getQty() > 0) {
                                                        balance += b.getQty();
                                                %>
                                                <td><%=b.getQty()%></td>
                                                <td></td>
                                                <%} else if (b.getQty() < 0) {
                                                    balance += b.getQty();
                                                %>
                                                <td></td>
                                                <td><%=(b.getQty() * (-1))%></td>
                                                <%}%>

                                                <td><%=balance%></td>
                                                <td><%=b.getDes()%></td>
                                            </tr>
                                            <%
                                                }
                                            %>
                                        </table>
                                    </div>
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
