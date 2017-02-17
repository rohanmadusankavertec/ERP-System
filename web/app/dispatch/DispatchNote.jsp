<%-- 
    Document   : po
    Created on : Aug 22, 2016, 3:57:00 PM
    Author     : vertec-r
--%>


<%@page import="com.vertec.hibe.model.InvoiceItem"%>
<%@page import="com.vertec.hibe.model.Invoice"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/notAlert.js"></script>


<script type="text/javascript">
    
    //Approve Invoice
    function ApproveDispatch(id) {
        BootstrapDialog.show({
            message: 'Do you want to Submit ?',
            closable: false,
            buttons: [{
                    label: 'Yes',
                    action: function (dialogRef) {
                        dialogRef.close();
                        var xmlHttp = getAjaxObject();
                        xmlHttp.onreadystatechange = function ()
                        {
                            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                            {
                                var reply = xmlHttp.responseText;

                                if (reply === "Success") {
                                    nom_Success("Invoice Approved Successfully");
                                    setTimeout("location.href = 'Dispatch?action=CreateDispatch';", 1500);
                                } else {
                                    sm_warning("Invoice Not Approved Successfully. Please Try Again..");
                                }
                            }
                        };
                        xmlHttp.open("POST", "Dispatch?action=ApproveInvoice&id="+id, true);
                        xmlHttp.send();
                    }
                }, {
                    label: 'No',
                    action: function (dialogRef) {
                        dialogRef.close();
                    }
                }]
        });
    }
    //Cancel Invoice
    function CancelDispatch(invoiceId) {
    BootstrapDialog.show({
        message: 'Do you want to Delete This Invoice ?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function(dialogRef) {
                    dialogRef.close();


                    var xmlHttp = getAjaxObject();
                    xmlHttp.onreadystatechange = function()
                    {
                        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                        {
                            var reply = xmlHttp.responseText;

                            if (reply === "Success") {
                                nom_Success("Requested to delete Invoice.");
                                setTimeout("location.href = 'Invoice?action=MaintainInvoice';", 1500);
                            } else {

                                sm_warning("Invoice Not Correctly Deleted Please Try Again");

                            }
                        }
                    };
                    xmlHttp.open("POST", "Invoice?action=reqDeleteInvoice&invoiceId=" + invoiceId, true);
                    xmlHttp.send();


                }
            }, {
                label: 'No',
                action: function(dialogRef) {
                    dialogRef.close();
                }
            }]
    });
}
</script>

<%
    Invoice invoice = (Invoice) request.getAttribute("invoice");
    List<InvoiceItem> invoiceitem = (List<InvoiceItem>) request.getAttribute("invoiceitem");
%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Dispatch Note
            </h3>
        </div>
    </div>

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small></small></h2>
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
                            <div class="item form-group" style="margin-top: 20px;">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12">Invoice No</label>
                                <label class="control-label col-md-7 col-sm-3 col-xs-12"><%=invoice.getInvoiceId()%></label>
                            </div>
                            <div class="item form-group" style="margin-top: 20px;">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12">Invoice Date</label>
                                <label class="control-label col-md-7 col-sm-3 col-xs-12"><%=invoice.getInvoicedDate()%></label>
                            </div>
                            <div class="item form-group" style="margin-top: 20px;">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12">Customer Name</label>
                                <label class="control-label col-md-7 col-sm-3 col-xs-12"><%=invoice.getCustomerId().getCustomerName()%></label>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="row" style="padding-top: 20px;">
                                <div class="col-xs-12 table">
                                    <table class="table table-striped" id="poItemTable">
                                        <thead>
                                            <tr>
                                                <th>Product</th>
                                                <th>Qty</th>
                                                <th>Price</th>
                                                <th>Sub Total</th>
                                                <th>Discount</th>
                                                <th>Total</th>
                                            </tr>
                                        </thead>
                                        <tbody id="poItemBody">
                                            <%
                                                for (InvoiceItem ii : invoiceitem) {
                                            %>
                                            <tr>
                                                <td><%=ii.getProductMasterId().getProductId().getProductCode() + "_" + ii.getProductMasterId().getProductId().getProductName()%></td>
                                                <td><%=ii.getQuantity()%></td>
                                                <td><%=ii.getUnitPrice()%></td>
                                                <td><%=ii.getTotAmount()%></td>
                                                <td><%=ii.getDiscount()%></td>
                                                <td><%=ii.getTotAfterDis()%></td>
                                            </tr>
                                            <%
                                                }
                                            %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>     
                        </div>
                        <div class="item form-group" style="margin-top: 20px;">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12"> Total</label>
                            <label class="control-label col-md-7 col-sm-3 col-xs-12"><%=invoice.getInvoiceTotal()%></label>
                        </div>
                        <div class="item form-group" style="margin-top: 20px;">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Discount</label>
                            <label class="control-label col-md-7 col-sm-3 col-xs-12"><%=invoice.getDiscount()%></label>
                        </div>
                        <div class="item form-group" style="margin-top: 20px;">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Grand Total</label>
                            <label class="control-label col-md-7 col-sm-3 col-xs-12"><%=invoice.getTotAfterDiscount()%></label>
                        </div>
                        <div class="row no-print">
                            <div class="col-xs-12">
                                <button class="btn btn-success pull-right" onclick="ApproveDispatch(<%=invoice.getInvoiceId()%>);"><i class="fa fa-check"></i> Approve</button>
                                <button class="btn btn-danger pull-right" onclick="CancelDispatch(<%=invoice.getInvoiceId()%>);"><i class="fa fa-remove"></i> Cancel</button>
                            </div>
                        </div>
                    </section>

                </div>
            </div>
        </div>
    </div>












</div>




<%@include file="../../template/footer.jsp"%>