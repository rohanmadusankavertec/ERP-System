<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.ReturnStock"%>
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

    var item_details = {};
    //load invoice product to table
    function InvoiceProductTable() {
        var productarr = document.getElementById("ritem").value;
        var qty = document.getElementById("returnedqty").value;
        if (productarr === "") {
            sm_warning("Please Select the product.");
        } else if (qty === "") {
            sm_warning("Please Enter the quantity.");
        } else {
            document.getElementById("ritem").value = "";
            document.getElementById("returnedqty").value = "";
            var arr = productarr.split("_");
            var iiId = arr[0];
            var product = arr[1];
            var price = arr[2];
            var total = parseFloat(qty) * parseFloat(price);
            if (item_details[iiId] == null) {
                var item_detail = {};
                item_detail["rsid"] = iiId;
                item_detail["product"] = product;
                item_detail["price"] = price;
                item_detail["qty"] = qty;
                item_details[iiId] = item_detail;
                var invoiceItemTable = document.getElementById('invoiceItemTable').getElementsByTagName('tbody')[0];
                var row = document.createElement("tr");
                row.id = iiId;
                var col1 = document.createElement("td");
                col1.type = "text";
                col1.value = product;
                col1.innerHTML = product;
                var col2 = document.createElement("td");
                col2.type = "text";
                col2.value = price;
                col2.innerHTML = price;
                var col3 = document.createElement("td");
                col3.type = "text";
                col3.value = qty;
                col3.innerHTML = qty;
                var col4 = document.createElement("td");
                col4.type = "text";
                col4.value = total;
                col4.innerHTML = total;
                var col5 = document.createElement("td");
                var elem1 = document.createElement("span");
                elem1.id = iiId;
                elem1.name = iiId;
                elem1.type = "button";
                elem1.className = "btn btn-default glyphicon glyphicon-remove text-center";
                col5.appendChild(elem1);
                row.appendChild(col1);
                row.appendChild(col2);
                row.appendChild(col3);
                row.appendChild(col4);
                row.appendChild(col5);
                invoiceItemTable.appendChild(row);
                document.getElementById("ritem").value="";
                document.getElementById("returnedqty").value="";
            } else {
                sm_warning("This Item is already exist..");
            }
        }
    }
    
    //delte product from table
    $(document).on('click', '#invoiceItemTable span', function () {
        var r = confirm("Are you Sure You want to delete this?");
        if (r === true) {
            var tr = $(this).closest('tr');
            $(this).closest('tr').remove();
            delete item_details[this.id];
        } else {
        }
    });

    // check whether the array is empty
    function isEmpty(obj) {
        for (var i in obj) {
            return false;
        }
        return true;
    }

// validateion and save customer return
    function submitCustomerReturn() {
        if (isEmpty(item_details)) {
            nom_warning("Please add item to dispose...");
        } else {
            var data = {};
            data["returnedProducts"] = item_details;
            var jsonDetails = JSON.stringify(data);
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
                                        nom_Success("Successfully Added");
                                        setTimeout("location.href = 'Return?action=Dispose';", 1500);
                                    } else {
                                        sm_warning("Dispose Not Correctly Entered. Please Try Again.");
                                    }
                                }
                            };
                            xmlHttp.open("POST", "Return?action=SubmitDispose&data=" + jsonDetails, true);
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
                Dispose Management
            </h3>
        </div>
    </div>
    <div class="clearfix"></div>

    <%
        List<ReturnStock> returnStock = (List<ReturnStock>) request.getAttribute("ReturnStock");
    %>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Add Dispose<small></small></h2>
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
                        <span class="section"></span>

                        <div class="ln_solid"></div>

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Returned Item <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="ritem" id="ritem" required="required" >
                                    <option selected="true" disabled="true" value="">Select Item</option>
                                    <%for (ReturnStock i : returnStock) {%>
                                    <option value="<%=i.getId() + "_" + i.getProductMasterId().getProductId().getProductName() + "_" + i.getProductMasterId().getSellingPrice()%>"><%=i.getProductMasterId().getProductId().getProductName() + " ~ Qty:" + i.getQty()%></option>
                                    <%}%>
                                </select>                              
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Quantity<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="number" id="returnedqty" class="form-control col-md-7 col-xs-12" placeholder="Enter Quantity" name="returnedqty" required="required" value="" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <button id="send" type="button" class="btn btn-success" onclick="InvoiceProductTable()" style="float: right;">Add</button>
                            </div>
                        </div>
                    </form>


                    <center>
                        <div class="row">
                            <div class="row" style="padding-top: 20px;">
                                <div class="col-xs-12 table">
                                    <table class="table table-striped" id="invoiceItemTable" style="width: 80%">
                                        <thead>
                                            <tr>
                                                <th>Product</th>
                                                <th>Qty</th>
                                                <th>Price</th>
                                                <th>Total</th>
                                            </tr>
                                        </thead>
                                        <tbody id="poItemBody">

                                        </tbody>
                                    </table>
                                </div>
                            </div>     
                        </div>  
                    </center>
                    <form method="post" class="form-horizontal form-label-left" novalidate>
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <button id="send" type="button" class="btn btn-success" onclick="submitCustomerReturn()">Submit</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>

</div>


<%@include file="../../template/footer.jsp"%>