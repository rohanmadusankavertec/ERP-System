

<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.SessionFactory"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page import="com.vertec.hibe.model.Branch"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/invoice.js"></script>
<script src="app/js/notAlert.js"></script>

<script type="text/javascript">
    //to call start time method after 1 second
    var t = setTimeout(startTime, 1000);

    //view time
    function startTime() {

        var today = new Date();
        var h = today.getHours();
        var m = today.getMinutes();
        var s = today.getSeconds();
        m = checkTime(m);
        s = checkTime(s);
        document.getElementById('datetime').innerHTML = "  " + h + ":" + m + ":" + s;
        var t = setTimeout(startTime, 500);
    }


    // add zero in front of numbers < 10
    function checkTime(i) {
        if (i < 10) {
            i = "0" + i
        }
        return i;
    }


    //Load products to select element
    function loadProducts() {
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                document.getElementById("productId").innerHTML = "";

                var MainElement = document.getElementById("productId");

                var op = document.createElement("option");
                op.innerHTML = "Select Product";
                op.disabled = "true";
                op.selected = "true";
                MainElement.appendChild(op);

                var reply = xmlHttp.responseText;
                if (reply !== "") {
                    var product = reply.split(";;;;;");
                    for (var i = 0; i < product.length; i++) {
                        var pd = product[i].split(":::::");
                        var option = document.createElement("option");
                        option.innerHTML = pd[0] + "_" + pd[1] + "_" + pd[2];
                        MainElement.appendChild(option);
                    }
                }
            }
        };
        var cid = document.getElementById("getcat").value;
        var branch = document.getElementById("branchId").value;
        xmlHttp.open("POST", "Invoice?action=ProductFromCategory&cid=" + cid + "&branchId=" + branch, true);
        xmlHttp.send();
    }


    //Change fields' visibility according to payment type
    function paymentType() {
        var ptype = document.getElementById("cash");
        if (ptype.checked) {
            document.getElementById('cn').className = 'hidden';
            document.getElementById('bn').className = 'hidden';
            document.getElementById('cd').className = 'hidden';
        } else {
            document.getElementById('cn').className = '';
            document.getElementById('bn').className = '';
            document.getElementById('cd').className = '';
        }
    }

    //view grand total and outstanding
    function outstanding() {
        var total = document.getElementById('gTot').innerHTML;
        var payment = document.getElementById('payment').value;
        if (payment === "") {
            payment = 0;
        }
        document.getElementById("ost").innerHTML = parseFloat(total) - parseFloat(payment);
    }
    function isEmpty(obj) {
        for (var i in obj) {
            return false;
        }
        return true;
    }

// Save invoice
    function submitInvoice() {
        
        var customerId = document.getElementById('customerId').value;
        var branchId = document.getElementById('branchId').value;
        var data = {};
        var totalInAmount = document.getElementById('subtot').innerHTML;
        var invoiceDiscount = document.getElementById('inDis').value;
        var totAmountAfterDiscount = document.getElementById('totaftdis').innerHTML;
        var tax = document.getElementById('tax').innerHTML;
        var gTot = document.getElementById('gTot').innerHTML;



        var chequeNo = document.getElementById('chequeNo').value;
        var bankName = document.getElementById('bankName').value;
        var chequeDate = document.getElementById('chequeDate').value;
        var payment = document.getElementById('payment').value;
        var pt = 0;
        var ptype = document.getElementById("cash");
        if (ptype.checked) {
            pt = 1;
        }
        data["chequeNo"] = chequeNo;
        data["bankName"] = bankName;
        data["chequeDate"] = chequeDate;
        data["payment"] = payment;
        data["pt"] = pt;
        data["customerId"] = customerId;
        data["branchId"] = branchId;
        data["totalInAmount"] = totalInAmount;
        data["invoiceDiscount"] = invoiceDiscount;
        data["totAmountAfterDiscount"] = totAmountAfterDiscount;
        data["tax"] = tax;
        data["gTot"] = gTot;
        data["item_details"] = item_details;
        
        if(isEmpty(item_details)){
            sm_warning("Please Add Item...");
        }else if(payment ==""){
            sm_warning("Please Add Payment Amount...");
        }else{
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
                                        setTimeout("location.href = 'Invoice?action=ToPrint';", 1500);
                                    } else {
                                        sm_warning("Invoice Not Correctly Entered Please Try Again");
                                    }
                                }
                            };
                            xmlHttp.open("POST", "Invoice?action=SubmitInvoice&data=" + jsonDetails, true);
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

    function ItemwiseTotal() {
        var bpm = document.getElementById('bpmId').value;
        var quantity = document.getElementById('bpmQuantity').value;
        var disType = document.getElementById('disType').value;
        var disAmount = document.getElementById('disAmount').value;
        var bmpArr = bpm.split("_");
        var sellPrice = bmpArr[1];
        var totalAmount = quantity * sellPrice;
        var dAmount = 0;
        if (disType === "Percentage") {
            if (disAmount === "") {
            } else {
                dAmount = (disAmount * totalAmount) / 100;
            }
        } else if (disType === "Price") {
            if (disAmount === "") {
            } else {
                dAmount = disAmount;
            }
        }
        document.getElementById('ittot').innerHTML = totalAmount - dAmount;
    }

// load product master details to page
    function loadBranchPM() {
        $("#bpmId").empty();
        var s1 = document.getElementById('bpmId');
        var t1 = document.createElement("option");
        t1.value = "";
        t1.innerHTML = "Select Price Master";
        s1.appendChild(t1);
        var productId = document.getElementById('productId').value;
        var branchId = document.getElementById('branchId').value;
        var customerId = document.getElementById('customerId').value;
        var pid = productId.split("_");
        var dataArr = [branchId, pid[0], customerId];
        $.ajax({
            type: "POST",
            url: "Invoice?action=LoadBPMToInvoice&dataArr=" + dataArr,
            success: function (msg) {
                var reply = eval('(' + msg + ')');
                var arrLn1 = reply.jArr1;
                var bpm = document.getElementById('bpmId');
                document.getElementById('pmasterDiv').className = 'form-group';
                document.getElementById('quanDiv').className = 'form-group';
                document.getElementById('discountDiv').className = 'form-group';
                document.getElementById('itemtot').className = 'form-group';
                document.getElementById('btnDiv').className = 'form-group';
                for (var f = 0; f < arrLn1.length; f++) {
                    var t = document.createElement("option");
                    var val = arrLn1[f].bpmid + "_" + arrLn1[f].sprice + "_" + arrLn1[f].branquan;
                    t.value = val;
                    t.innerHTML = arrLn1[f].pprice + "_" + arrLn1[f].sprice;
                    bpm.appendChild(t);
                }
            }
        });
    }
</script>


<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Invoice
                <small>

                </small>
            </h3>
        </div>
        <%
            Branch branch = (Branch) request.getAttribute("branch");

            Customer customer = (Customer) request.getAttribute("customer");
            List<Object[]> pList = (List<Object[]>) request.getAttribute("pList");
            String bAddress = "";
            String cAddress = "";
            if (branch != null) {
                if (branch.getAddress() != null) {
                    bAddress = branch.getAddress().replace(",", "<br/>");
                }
            }
            if (customer != null) {
                if (customer.getAddress() != null) {
                    if (customer.getAddress() != "null") {
                        cAddress = customer.getAddress().replace(",", "<br/>");
                    }
                }
            }

            HttpSession httpSession = request.getSession();
            SysUser user1 = (SysUser) httpSession.getAttribute("user");

            Date date = new Date();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String today = formatter.format(date);
        %>

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
                                    <small class="pull-right"><%= today%><span id="datetime"></span></small>
                                </h1>
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- info row -->
                        <div class="row invoice-info">
                            <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                From
                                <input type="hidden" id="branchId" name="branchId" required="required" class="form-control col-md-7 col-xs-12" value="<%=branch.getBranchId()%>">

                                <address>
                                    <strong><%=user.getFirstName() + " " + user.getLastName()%></strong><br/>
                                    <%=bAddress%>
                                </address>

                                <div class="clearfix"></div>

                            </div>
                            <!-- /.col -->
                            <div class="col-sm-12 col-lg-6 col-md-6 invoice-col">
                                To
                                <input type="hidden" id="customerId" name="customerId" required="required" class="form-control col-md-7 col-xs-12" value="<%=customer.getCustomerId()%>">

                                <address>
                                    <strong><%=customer.getCustomerName()%></strong><br/>
                                    <%=cAddress%>
                                </address>
                            </div>


                        </div>
                        <!-- /.row -->
                        <!-- Start Item row -->
                        <div class="row" style="padding-top: 10px;">
                            <div id="invoiceenter">
                                <div class="item form-group"  style="padding-top: 10px;">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Select Product Category</label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">

                                        <select class="form-control" name="category" id="getcat"  required="required" onchange="loadProducts()">
                                            <option selected="true" >Select Product Category</option>
                                            <%
                                                SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                                Session ses = sf.openSession();
                                                String sql = "SELECT product_category_id,product_category_name FROM product_category WHERE company_id='" + user1.getBranchBranchId().getCompanyId().getId() + "'";

                                                SQLQuery querysql = ses.createSQLQuery(sql);

                                                List<Object[]> inListsql = querysql.list();

                                                for (Object[] list : inListsql) {%>

                                            <option value="<%= list[0].toString()%>"><%= list[1].toString()%></option>
                                            <%
                                                }
                                            %>
                                        </select>                            
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <div class="form-group invoiceenter" style="padding-top: 10px;">
                                    <label for="itemSelect" class="control-label col-lg-3 col-md-3 lbl_name">Select Product : </label>
                                    <div class="col-lg-6 col-md-6">
                                        <select class="form-control" name="productId" id="productId" onchange="loadBranchPM();">
                                            <option selected="true" value="" disabled="true">Select Product</option>
                                            <%for (Object[] p : pList) {%>
                                            <option value="<%=p[0].toString() + "_" + p[1].toString() + "_" + p[2].toString()%>"><%=p[1].toString() + "_" + p[2].toString()%></option>
                                            <%}%>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="hidden" style="padding-top: 40px;" id="pmasterDiv">
                                <label for="itemSelect" class="control-label col-lg-3 col-md-3 lbl_name">Select Price Master : </label>
                                <div class="col-lg-6 col-md-6">
                                    <select class="form-control" name="bpmId" id="bpmId" onchange="viewAvailableQuan();">
                                        <option selected="true" value="" disabled="true">Select Price Master</option>

                                    </select>
                                </div>
                                <div class="hidden" id="divAvaQuan">

                                </div>
                            </div>
                            <div class="hidden" style="padding-top: 40px;" id="quanDiv">
                                <label for="itemSelect" class="control-label col-lg-3 col-md-3 lbl_name">Enter Quantity</label>
                                <div class="col-lg-6 col-md-6">
                                    <input type="number" id="bpmQuantity" name="bpmQuantity" required="required" onkeyup="ItemwiseTotal();" class="form-control col-lg-6 col-md-6 col-xs-12"/>
                                </div>

                            </div>
                            <div class="hidden" style="padding-top: 40px;" id="discountDiv">
                                <label for="itemSelect" class="control-label col-lg-3 col-md-3 lbl_name">Select Discount Type : </label>
                                <div class="col-lg-6 col-md-6">
                                    <select class="form-control" name="disType" id="disType" onchange="ItemwiseTotal();">
                                        <option  value="NODiscount">No Discount</option>
                                        <option  value="Percentage">Percentage</option>
                                        <option  value="Price">Price</option>
                                    </select>
                                </div>
                                <div class="col-lg-3 col-md-3">
                                    <input type="text" id="disAmount" name="disAmount" placeholder="Enter Discount Aount" class="form-control col-lg-6 col-md-6 col-xs-12" onkeyup="ItemwiseTotal();"/>
                                </div>
                            </div>
                            <div class="hidden" style="padding-top: 40px;" id="itemtot">
                                <label class="control-label col-lg-3 col-md-3 lbl_name">Total</label>
                                <label class="control-label col-lg-6 col-md-6 lbl_name" id="ittot">0000.00</label>

                            </div>
                            <div class="hidden" style="padding-top: 40px;" id="btnDiv">
                                <button type="submit" class="btn btn-success pull-right col-lg-3 col-md-3" onclick="checkAddToGrid();"><i class="fa fa-briefcase"></i> Add</button>
                            </div>

                        </div>
                        <!-- Finish Item row -->
                        <!-- Table row -->
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
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->

                        <div class="hidden" id="tottableDiv">
                            <!-- accepted payments column -->
                            <div class="col-xs-6">

                            </div>
                            <!-- /.col -->
                            <div class="col-xs-6" >
                                <!--                                <p class="lead">Amount Due 2/22/2014</p>-->
                                <div class="table-responsive">
                                    <table class="table">
                                        <tbody>
                                            <tr>
                                                <th style="width:50%">Subtotal:</th>
                                                <td id="subtot"></td>
                                            </tr>
                                            <tr>
                                                <th>Discount</th>
                                                <td><input type="text" id="inDis" name="inDis" placeholder="Ex:10% or 10000" class="form-control" onkeyup="addDiscount();" onblur=""/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>Total After DIscount</th>
                                                <td id="totaftdis"></td>
                                            </tr>
                                            <tr class="hidden">
                                                <th>Tax (15%)</th>
                                                <td id="tax"></td>
                                            </tr>
                                            <tr>
                                                <th>Total:</th>
                                                <td id="gTot"></td>
                                            </tr>
                                            <tr>
                                                <th>Payment Type:</th>
                                                <td>
                                                    <input type="radio"  value="cash" name="pt" id="cash" checked onchange="paymentType()" />Cash
                                                    <input type="radio" value="cheque" name="pt" id="cheque" onchange="paymentType()"/>Cheque
                                                </td>
                                            </tr>
                                            <tr class="hidden" id="cn">
                                                <th>Cheque No</th>
                                                <td>
                                                    <input type="text" id="chequeNo" placeholder="Cheque No" class="form-control"/>
                                                </td>
                                            </tr>
                                            <tr class="hidden" id="bn">
                                                <th>Bank Name:</th>
                                                <td>
                                                    <input type="text" id="bankName" placeholder="Bank Name" class="form-control"/>
                                                </td>
                                            </tr>
                                            <tr class="hidden" id="cd">
                                                <th>Cheque Date:</th>
                                                <td> <input type="date" id="chequeDate" /></td>
                                            </tr>
                                            <tr>
                                                <th>Amount:</th>
                                                <td> <input type="text" id="payment" placeholder="Payment" class="form-control" onkeyup="outstanding()"/></td>
                                            </tr>
                                            <tr>
                                                <th>Outstanding:</th>
                                                <td id="ost"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->

                        <!-- this row will not appear when printing -->
                        <div class="row no-print">
                            <div class="col-xs-12">
                                <button class="btn btn-default" onclick="clearItem();"><i class="fa fa-print"></i> Print</button>
                                <button class="btn btn-success pull-right" onclick="submitInvoice();"><i class="fa fa-credit-card"></i> Submit Invoice</button>

                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
