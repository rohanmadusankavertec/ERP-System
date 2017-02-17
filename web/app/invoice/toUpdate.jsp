
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

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Invoice
            </h3>
        </div>
    </div>
    <%
        List<InvoiceItem> invoiceList = (List<InvoiceItem>) request.getAttribute("iiList");
        int arrSize = invoiceList.size();

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
                        <table class="table table-bordered table-hover table-striped" id="invDate" >

                            <tbody id="gendata">

                                <tr>
                                    <td width="20%" align="center" > Invoice ID</td>
                                    <td width="20%" align="center" > <%=invoice.getInvoiceId()%>
                                        <input type="hidden" name="invoiceId" id="invoiceId" value="<%=invoice.getInvoiceId()%>"/>
                                    </td>

                                </tr>
                                <tr>
                                    <td width="20%" align="center" > Invoice Date</td>
                                    <td width="20%" align="center" > <%=invoice.getInvoicedDate()%></td>

                                </tr>
                                <tr>
                                    <td width="20%" align="center" > Invoice Total</td>
                                    <td width="20%" align="center" > <input type="text" name="invoiceTotal" id="invoiceTotal" value="<%=formatter.format(invoice.getInvoiceTotal())%>" readonly/>
                                        <input type="hidden" name="arrSize" id="arrSize" value="<%=arrSize%>"/></td>

                                </tr>
                                <tr>
                                    <td width="20%" align="center" > Discount</td>
                                    <td width="20%" align="center" > <input type="text" name="wholeDiscount" id="wholeDiscount" value="<%=formatter.format(invoice.getDiscount())%>" onblur="changeToWholeDiscount();"/></td>

                                </tr>
                                <tr>
                                    <td width="20%" align="center" > Vat</td>
                                    <td width="20%" align="center" > <input type="text" name="taxAm" id="taxAm" value="<%=formatter.format(invoice.getTotAfterDiscount() - (invoice.getInvoiceTotal() + invoice.getDiscount()))%>" readonly/></td>

                                </tr>
                                <tr>
                                    <td width="20%" align="center" > Invoice Total Gross Amount</td>
                                    <td width="20%" align="center" > <input type="text" name="InToGross" id="InToGross" value="<%=formatter.format(invoice.getTotAfterDiscount())%>" readonly/></td>

                                </tr>
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <!--                    <h2>View Invoice
                                                                    <small>to update,view and delete invoices</small>
                                        </h2>-->
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
                                    <th bgcolor="#EFEFEF" ><center>Item Code</center></th>
                            <th bgcolor="#EFEFEF" ><center>Unit Price</center></th>
                            <th bgcolor="#EFEFEF" ><center>Quantity</center></th>
                            <th bgcolor="#EFEFEF" ><center>Amount</center></th>
                            <th bgcolor="#EFEFEF" ><center>Discount</center></th>
                            <th bgcolor="#EFEFEF" ><center>GrossAmount</center></th>
                            <th bgcolor="#EFEFEF" ><center>Action</center></th>

                            </tr>
                            </thead>
                            <tbody id="gendata">
                                <%
                                    int i = 1;
                                    for (InvoiceItem result : invoiceList) {
                                        InvoiceItem ii = ((InvoiceItem) result);


                                %>
                                <tr id="<%=i%>">
                                    <td width="20%" align="center" > <% out.println(ii.getProductMasterId().getProductId().getProductCode());%><input type="hidden" name="pmid<%=i%>" id="pmid<%=i%>" value="<%=ii.getProductMasterId().getProductMasterId()%>"/><input type="hidden" name="iiId<%=i%>" id="iiId<%=i%>" value="<%=ii.getInvoiceItemId()%>"/></td>
                                    <td width="30%" align="center" > <input type="text" name="masteprice<%=i%>" id="masteprice<%=i%>" value="<% out.println(ii.getProductMasterId().getSellingPrice());%>" readonly/><input type="hidden" name="itemId<%=i%>" id="itemId<%=i%>" value="<%=ii.getProductMasterId().getProductId().getProductId()%>"/></td>
                                    <td width="30%" align="center" > <input type="text" name="quantity<%=i%>" id="quantity<%=i%>" value="<% out.println(ii.getQuantity());%>" onblur="updateFields(<%=i%>);"/></td>
                                    <td width="30%" align="center" > <input type="text" readonly name="amount<%=i%>" id="amount<%=i%>" value="<% out.println(ii.getTotAmount());%>"/></td>
                                    <td width="30%" align="center" > <input type="text" name="disAmount<%=i%>" id="disAmount<%=i%>" value="<% out.println(ii.getDiscount());%>" onblur="updateDisFields(<%=i%>);"/></td>
                                    <td width="30%" align="center" > <input type="text" name="totAftDis<%=i%>" id="totAftDis<%=i%>" value="<% out.println(ii.getTotAfterDis());%>" readonly/></td>
                                    <td width="30%" align="center" ><span id="<%=i%>" class="btn btn-default glyphicon glyphicon-edit text-center"></span></td>
                                </tr>
                                <%
                                        i = i + 1;
                                    }
                                %>

                            </tbody>
                        </table>
                    </div>
                    <div class="row">
                        <div class="col-lg-3">
                        </div>
                        <div class="col-lg-3">
                            <input type="button" onclick="updateInvoice();" name="saveIn" class="form-control btn btn-success" id="saveIn" value="Update"/>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- footer content -->
<%@include file="../../template/footer.jsp"%>

