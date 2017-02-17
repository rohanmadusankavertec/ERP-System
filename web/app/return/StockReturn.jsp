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

    //delete item from table
    $(document).on('click', '#productTable span', function () {
        var r = confirm("Are you Sure You want to delete this?");
        if (r === true) {
            var tr = $(this).closest('tr');
            $(this).closest('tr').remove();
            delete productItems[this.id];
        } else {
        }
    });

    // load product master to select element
    function LoadProductMaster() {
        var product = document.getElementById("product").value;
        var arr = product.split("_");
        $.ajax({
            type: 'POST',
            url: "Return?action=loadProductMaster&product=" + arr[0],
            success: function (msg) {
                var reply = eval('(' + msg + ')');
                var arrLn1 = reply.InvoiceItem;
                document.getElementById('productmaster').innerHTML="";
                for (var i = 0; i < arrLn1.length; i++) {
                    var invoiceItemTable = document.getElementById('productmaster');
                    var col1 = document.createElement("option");
                    col1.type = "text";
                    col1.value = arrLn1[i].id + "_" + arrLn1[i].price;
                    col1.innerHTML = " Rs:" + arrLn1[i].price;
                    invoiceItemTable.appendChild(col1);
                }
            }
        });
    }


    var productItems = {};

    // add return product to table
    function ReturnProductTable() {
        var productarr = document.getElementById("product").value;
        var pmarr = document.getElementById("productmaster").value;
        var qty = document.getElementById("qty").value;
        var arr = productarr.split("_");
        var arr2 = pmarr.split("_");
        var pid = arr[0];
        var product = arr[1];
        var price = arr2[1];
        var pm = arr2[0];
        var total = parseFloat(qty) * parseFloat(price);
        var item_detail = {};
        if (productItems[pid] == null) {
            item_detail["pid"] = pid;
            item_detail["product"] = product;
            item_detail["price"] = price;
            item_detail["pm"] = pm;
            item_detail["qty"] = qty;
            item_detail["totalAmount"] = total;

            productItems[pid] = item_detail;
            var invoiceItemTable = document.getElementById('productTable').getElementsByTagName('tbody')[0];
            var row = document.createElement("tr");
            row.id = pid;
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
            elem1.id = pid;
            elem1.name = pid;
            elem1.type = "button";
            elem1.className = "btn btn-default glyphicon glyphicon-remove text-center";
            col5.appendChild(elem1);

            row.appendChild(col1);
            row.appendChild(col2);
            row.appendChild(col3);
            row.appendChild(col4);
            row.appendChild(col5);
            invoiceItemTable.appendChild(row);

            document.getElementById("product").value = "";
            document.getElementById("productmaster").value = "";
            document.getElementById("qty").value = "";

        } else {
            sm_warning("This Item is already exist..");
        }
    }
    // check whether the array is empty
    function isEmpty(obj) {
        for (var i in obj) {
            return false;
        }
        return true;
    }

    //Save stock return
    function submitStockReturn() {

        if (isEmpty(productItems)) {
            sm_warning("Please Add Products..");
        } else {
            var data = {};
            data["returnedProducts"] = productItems;
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
                                        setTimeout("location.href = 'Return?action=StockReturn';", 1500);
                                    } else {
                                        sm_warning("Stock Return Not Correctly Entered. Please Try Again.");
                                    }
                                }
                            };
                            xmlHttp.open("POST", "Return?action=SubmitStockReturn&data=" + jsonDetails, true);
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
                Stock Return Management
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
                    <h2>Add Return<small></small></h2>
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
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Return Item <span class="required"></span>
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
                                <select class="form-control" name="productmaster" id="productmaster"  required="required" >
                                    <option selected="true" disabled="true" value="">Select Product Master</option>
                                </select>                              
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Return Quantity<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="number" id="qty" class="form-control col-md-7 col-xs-12" placeholder="Enter Quantity" name="qty" required="required" value="" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <button id="send" type="button" class="btn btn-success" style="float: right;" onclick="ReturnProductTable()">Add</button>
                            </div>
                        </div>
                        <center>
                            <div class="row">
                                <div class="row" style="padding-top: 20px;">
                                    <div class="col-xs-12 table">
                                        <table class="table table-striped" id="productTable" style="width: 80%; " >
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
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <button id="send" type="button" class="btn btn-success" onclick="submitStockReturn()">Submit</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>

</div>

<!-- footer content -->

<script>
    // initialize the validator function
    validator.message['date'] = 'not a real date';

    // validate a field on "blur" event, a 'select' on 'change' event & a '.reuired' classed multifield on 'keyup':
    $('form')
            .on('blur', 'input[required], input.optional, select.required', validator.checkField)
            .on('change', 'select.required', validator.checkField)
            .on('keypress', 'input[required][pattern]', validator.keypress);

    $('.multi.required')
            .on('keyup blur', 'input', function () {
                validator.checkField.apply($(this).siblings().last()[0]);
            });

    // bind the validation to the form submit event
    //$('#send').click('submit');//.prop('disabled', true);

    $('form').submit(function (e) {
        e.preventDefault();
        var submit = true;
        // evaluate the form using generic validaing
        if (!validator.checkAll($(this))) {
            submit = false;
        }
        if (submit)
            this.submit();
        return false;
    });

    /* FOR DEMO ONLY */
    $('#vfields').change(function () {
        $('form').toggleClass('mode2');
    }).prop('checked', false);

    $('#alerts').change(function () {
        validator.defaults.alerts = (this.checked) ? false : true;
        if (this.checked)
            $('form .alert').remove();
    }).prop('checked', false);
</script>
<script>
    $(document).ready(function () {
        $('input.tableflat').iCheck({checkboxClass: 'icheckbox_flat-green',
            radioClass: 'iradio_flat-green'
        });
    });
    var asInitVals = new Array();
    $(document).ready(function () {
        var oTable = $('#example').dataTable({
            "oLanguage": {
                "sSearch": "Search all columns:"
            },
            "aoColumnDefs": [{
                    'bSortable': false,
                    'aTargets': [0]
                } //disables sorting for column one
            ],
            'iDisplayLength': 12,
            "sPaginationType": "full_numbers",
            "dom": 'T<"clear">lfrtip',
            "tableTools": {
                "sSwfPath": "${context}/resources/js/datatables/tools/swf/copy_csv_xls_pdf.swf"
            }
        });
        $("tfoot input").keyup(function () {
            /* Filter on the column based on the index of this element's parent <th> */
            oTable.fnFilter(this.value, $("tfoot th").index($(this).parent()));
        });
        $("tfoot input").each(function (i) {
            asInitVals[i] = this.value;
        });
        $("tfoot input").focus(function () {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("tfoot input").blur(function (i) {
            if (this.value == "") {
                this.className = "search_init";
                this.value = asInitVals[$("tfoot input").index(this)];
            }
        });
    });
</script>

<%@include file="../../template/footer.jsp"%>