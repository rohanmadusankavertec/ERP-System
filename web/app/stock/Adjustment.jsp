<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.ProductMaster"%>
<%@page import="com.vertec.hibe.model.Invoice"%>
<%@page import="com.vertec.hibe.model.InvoiceItem"%>
<%@page import="com.vertec.hibe.model.Supplier"%>
<%@page import="com.vertec.hibe.model.Product"%>
<%@page import="com.vertec.hibe.model.ProductCategory"%>
<%@page import="com.vertec.hibe.model.Branch"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/product.js"></script>
<script src="app/js/notAlert.js"></script>


<script type="text/javascript">

    function LoadProductMaster() {
        var product = document.getElementById("product").value;
        var arr = product.split("_");
        $.ajax({
            type: 'POST',
            url: "Return?action=loadProductMaster&product=" + arr[0],
            success: function (msg) {
                var reply = eval('(' + msg + ')');
                var arrLn1 = reply.InvoiceItem;
                for (var i = 0; i < arrLn1.length; i++) {
                    var invoiceItemTable = document.getElementById('productmaster');
                    var col1 = document.createElement("option");
                    col1.type = "text";
                    col1.value = arrLn1[i].id;
                    col1.innerHTML = " Rs:" + arrLn1[i].pprice+" - Rs:" + arrLn1[i].price;
                    invoiceItemTable.appendChild(col1);
                }
            }
        });
    }

    function LoadCurrentQty() {
        var pmid = document.getElementById("productmaster").value;
        $.ajax({
            type: 'POST',
            url: "Stock?action=loadProductFromStock&pmid=" + pmid,
            success: function (msg) {
                var reply = eval('(' + msg + ')');
                var arrLn1 = reply.qty;
                var arrLn2 = reply.id;
                document.getElementById('cqty').value=arrLn1;
                document.getElementById('bpmId').value=arrLn2;
            }
        });
    }

    function ChangeStock() {
        
        var bpmid=document.getElementById('bpmId').value;
        var qty=document.getElementById('qty').value;
        var product=document.getElementById('product').value;
        var productmaster=document.getElementById('productmaster').value;
        
        if (product==="") {
            sm_warning("Please Select a Product..");
        }else if (productmaster==="") {
            sm_warning("Please Select Product Master ..");
        }else if (qty==="") {
            sm_warning("Please Enter the Quantity..");
        } else {
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
                                        nom_Success("Successfully Changed Stock");
                                        setTimeout("location.href = 'Stock?action=Adjustment';", 1500);
                                    } else {
                                        sm_warning("Stock Change Not Correctly Saved. Please Try Again.");
                                    }
                                }
                            };
                            xmlHttp.open("POST", "Stock?action=SubmitAdjustment&bpmid=" + bpmid+"&qty="+qty, true);
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
    }
</script>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Stock Adjustment
            </h3>
        </div>
    </div>
    <div class="clearfix"></div>
    <%
        List<Product> product = (List<Product>) request.getAttribute("product");
    %>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Adjustment<small></small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <form method="post" class="form-horizontal form-label-left" novalidate>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Product<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="product" id="product" onchange="LoadProductMaster()" required="required" >
                                    <option selected="true" disabled="true" value="">Select Product</option>

                                    <%for (Product p : product) {%>
                                    <option value="<%=p.getProductId() + "_" + p.getProductName()%>"><%=p.getProductCode() + " ~ " + p.getProductName()%></option>
                                    <%}%>
                                </select>                              
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Product Master <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" onchange="LoadCurrentQty()" name="productmaster" id="productmaster" required="required" >
                                    <option selected="true" disabled="true" value="">Select Product Master</option>
                                </select>                              
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Current Quantity<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="number" id="cqty" disabled class="form-control col-md-7 col-xs-12" placeholder="Current Quantity" required="required" />
                                <input type="hidden" value="" id="bpmId"/>
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">New Quantity<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="number" id="qty" class="form-control col-md-7 col-xs-12" placeholder="Enter New Quantity" name="qty" required="required" value="" />
                            </div>
                        </div>

                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <button id="send" type="button" class="btn btn-success" onclick="ChangeStock()">Change Stock</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>

</div>

<%@include file="../../template/footer.jsp"%>