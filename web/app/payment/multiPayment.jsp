

<%@page import="com.vertec.hibe.model.Branch"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<!--<script src="app/js/payment.js"></script>-->
<script src="app/js/notAlert.js"></script>

<script>
//load customer's total outstanding
    function loadDetails11() {
        var customerId = document.getElementById('customerId').value;
        document.getElementById('paymentTable').className = "";
        $.ajax({
            type: 'POST',
            url: "Payment?action=LoadCusBal&customerId=" + customerId,
            success: function (msg) {
                var reply = eval('(' + msg + ')');
                var arrLn1 = reply.jArr1;
                var tot = 0;
                for (var i = 0; i < arrLn1.length; i++) {
                    var balance = arrLn1[i].balance_amount;
                    tot += Number(balance);
                }
                document.getElementById("inAmount").value = tot;
            }
        });
    }

    var arr = [];
//Load outstanding according to customer
    function loadAccordingCus() {
        $("#paymentAvaTable").empty();

        var customerId = document.getElementById('customerId').value;
        $.ajax({
            type: "POST",
            url: "Payment?action=LoadCusBal&customerId=" + customerId,
            success: function (msg) {
                // alert(msg);
                var reply = eval('(' + msg + ')');
                var arrLn1 = reply.jArr1;

                var bpmtable = document.getElementById('paymentAvaTable');

//            var bpmhead = document.getElementById('chequeStaHead');
                var thead = document.createElement("thead");
                var row1 = document.createElement("tr");
                row1.id = 'headRow';
                var colu1 = document.createElement("td");
                colu1.align = "center";
                colu1.type = "text";
                colu1.innerHTML = 'Out_In_Id';
                var colu2 = document.createElement("td");
                colu2.align = "center";
                colu2.type = "text";
                colu2.innerHTML = 'Invoice ID';
                var colu3 = document.createElement("td");
                colu3.align = "center";
                colu3.type = "text";
                colu3.innerHTML = 'Invoiced Date';
                var colu4 = document.createElement("td");
                colu4.align = "center";
                colu4.type = "text";
                colu4.innerHTML = 'Total Invoice Amount';
                var colu5 = document.createElement("td");
                colu5.align = "center";
                colu5.type = "text";
                colu5.innerHTML = 'Balance Outstanding';

                row1.appendChild(colu1);
                row1.appendChild(colu2);
                row1.appendChild(colu3);
                row1.appendChild(colu4);
                row1.appendChild(colu5);
                thead.appendChild(row1);
                bpmtable.appendChild(thead);
                var totOutstanding = 0;

                for (var f = 0; arrLn1.length > f; f++) {
                    var oiId = reply.jArr1[f].oi_id;
                    var balance = reply.jArr1[f].balance_amount;
                    var iId = reply.jArr1[f].inv_id;
                    var iTotal = reply.jArr1[f].total_invoice;
                    var dateI = reply.jArr1[f].date;

                    arr.push(balance);

                    var row = document.createElement("tr");
                    row.id = oiId;
                    var col1 = document.createElement("td");
                    col1.align = "center";
                    col1.type = "text";
                    col1.value = oiId;
                    col1.innerHTML = oiId;
                    var col2 = document.createElement("td");
                    col2.align = "center";
                    col2.type = "text";
                    col2.value = iId;
                    col2.innerHTML = iId;
                    var col3 = document.createElement("td");
                    col3.align = "center";
                    col3.type = "text";
                    col3.value = dateI;
                    col3.innerHTML = dateI;
                    var col4 = document.createElement("td");
                    col4.align = "center";
                    col4.type = "text";
                    col4.value = iTotal;
                    col4.innerHTML = iTotal;
                    var col5 = document.createElement("td");
                    col5.align = "center";
                    col5.type = "text";
                    col5.value = balance;
                    col5.innerHTML = balance;
                    
                    row.appendChild(col1);
                    row.appendChild(col2);
                    row.appendChild(col3);
                    row.appendChild(col4);
                    row.appendChild(col5);
                    bpmtable.appendChild(row);
                    totOutstanding = Number(totOutstanding) + Number(balance);
                }
                var rowFootBef = document.createElement("tr");
                rowFootBef.id = 'footRowBef';
                var coluFootBef1 = document.createElement("td");
                coluFootBef1.align = "left";
                coluFootBef1.setAttribute("colspan", 6);
                rowFootBef.appendChild(coluFootBef1);
                bpmtable.appendChild(rowFootBef);
                var rowFoot = document.createElement("tr");
                rowFoot.id = 'footRow';
                var coluFoot1 = document.createElement("td");
                coluFoot1.align = "left";
                coluFoot1.setAttribute("colspan", 4);
                coluFoot1.type = "text";
                coluFoot1.innerHTML = 'Total Outstanding';
                var coluFoot2 = document.createElement("td");
                coluFoot2.align = "center";
                coluFoot2.type = "text";
                coluFoot2.innerHTML = totOutstanding + ".0";
                rowFoot.appendChild(coluFoot1);
                rowFoot.appendChild(coluFoot2);
                bpmtable.appendChild(rowFoot);
            }
        });
    }
    //show and hide cheque details
    function loadChekDetails() {
        var paymentType = document.getElementById('paymentType').value;

        if (paymentType === "1") {
            document.getElementById('chequeNo').className = 'hidden';
            document.getElementById('bank').className = 'hidden';
            document.getElementById('dateChk').className = 'hidden';
        } else if (paymentType === "2") {
            document.getElementById('chequeNo').className = '';
            document.getElementById('bank').className = '';
            document.getElementById('dateChk').className = '';
        }
    }
    // validate and save Payment
    function sendData() {
        var dataArr = [];
        var payAmount = document.getElementById('payAmount').value;
        var bal = document.getElementById('inAmount').value;
        var payType = document.getElementById('paymentType').value;
        var customerId = document.getElementById('customerId').value;

        if (payAmount == "" || payType == "") {
            sm_warning("Please Fill the Feilds....");
        }
        if (payType === "2") {
            var chkNo = document.getElementById('chkNo').value;
            var bankName = document.getElementById('bankName').value;
            var chDate = document.getElementById('chDate').value;
            dataArr = [payType, payAmount, customerId, chkNo, bankName, chDate];
        } else {
            dataArr = [payType, payAmount, customerId];
        }
        if (parseFloat(bal) < parseFloat(payAmount)) {
            sm_warning("Paymet Amount is greater than your balance Payment, Please Try again");
            document.getElementById('payAmount').value = "";
        } else {
            $.ajax({
                type: "POST",
                url: "Payment?action=savePaymeny&dataArr=" + dataArr,
                success: function (msg) {
                    if (msg === "Success") {
                        nom_Success("Successfully Payment Added");
                        setTimeout("sendMultiReceipt()", 800);
                        setTimeout("location.href = 'Payment?action=ToDoMultiPayment';", 1500);
                    } else {
                        sm_warning("Not Submited Payment, Please Try again");
                    }
                }
            });
        }
    }

    //send data to controller
    function sendMultiReceipt() {
        var cus = document.getElementById("customerId").value;
        var pay = document.getElementById("payAmount").value;
        window.open("Payment?action=multiReceipt&customer=" + cus + "&payment=" + pay + "&arr=" + arr, "_blank");
    }


</script>


<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Multiple Payment
                <small>
                    <!--                    Some examples to get you started-->
                </small>
            </h3>
        </div>

    </div>
    <%
        List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");
    %>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Payment <small>Sample user payment design</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>

                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <div class="item form-group" style="padding-top: 50px;">
                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Customer <span class="required"></span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="customerId" id="customerId"  required="required" onchange="loadAccordingCus();">
                                <option selected="true" disabled value="">Select Customer</option>

                                <%for (Customer c : customerList) {%>
                                <option value="<%=c.getCustomerId()%>"><%=c.getCustomerName()%></option>
                                <%}%>

                            </select>

                        </div>
                    </div>  
                    <!--                        <div class="item form-group" style="padding-top: 50px;">
                                                <div class="col-xs-12 col-lg-offset-3">
                                                    <button class="btn btn-success" id="updateP"><i class="fa fa-arrow-right"></i>  Go </button>
                                                </div>
                                            </div> -->
                    <div class="row" style="padding-top: 100px;">
                        <div class="table-responsive">
                            <div class="col-xs-12 table">
                                <table class="table table-striped" id="paymentAvaTable">
                                    <thead>
                                        <!--                                    <tr>
                                                                                <th>Out_In_Id</th>
                                                                                <th>In ID</th>
                                                                                <th>Date</th>
                                                                                <th>Total Amount</th>
                                                                                <th>Balance</th>
                                                                            </tr>-->
                                    </thead>
                                    <tbody id="paymentAvaBody">


                                    </tbody>
                                </table>
                            </div>
                            <!-- /.col -->
                        </div>
                    </div>

                    <div class="ln_solid"></div>
                    <div class="form-group" >
                        <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                            <button id="send" type="button" onclick="loadDetails11()" class="btn btn-success">Next</button>
                        </div>
                    </div>


                    <div class="hidden" id="paymentTable" style="padding-top: 100px;">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover table-striped" id="invDate" >

                                <tbody id="gendata">


                                    <tr>
                                        <td width="20%" align="center" > Invoice Amount</td>
                                        <td width="20%" align="center" > <input class="form-control" type="text" name="inAmount" id="inAmount" readonly/>
                                            <input class="form-control" type="hidden" name="invoiceId" id="invoiceId" value=""/>
                                        </td>

                                    </tr>

                                    <tr>
                                        <td width="20%" align="center" > Payment Type</td>
                                        <td width="20%" align="center" > <select class="form-control" name="paymentType" id="paymentType"  required="required" onchange="loadChekDetails();">
                                                <option selected="true" disabled value="">Select Payment Type</option>
                                                <option value="1">Cash Payment</option>
                                                <option value="2">Cheque Payment</option>

                                            </select>   </td>

                                    </tr>
                                    <tr>
                                        <td width="20%" align="center" > Payment</td>
                                        <td width="20%" align="center" > <input class="form-control" type="text" name="payAmount" id="payAmount" onblur="checkAmunt();"/></td>

                                    </tr>

                                    <tr class="hidden" id="chequeNo">
                                        <td width="20%" align="center" > Cheque No</td>
                                        <td width="20%" align="center" > <input class="form-control" type="text" name="chkNo" id="chkNo"/></td>

                                    </tr>
                                    <tr class="hidden" id="bank">
                                        <td width="20%" align="center" > Bank Name</td>
                                        <td width="20%" align="center" > <input class="form-control" type="text" name="bankName" id="bankName"/></td>

                                    </tr>
                                    <tr class="hidden" id="dateChk">
                                        <td width="20%" align="center" > Cheque Date</td>
                                        <td width="20%" align="center" > <input class="form-control" type="date" name="chDate" id="chDate"/></td>

                                    </tr>
                                    <tr>
                                        <td colspan="2"  align="center" > 
                                            <input type="button" onclick="sendData();" name="submitPay" class="col-lg-offset-3 col-md-offset-3 col-lg-5 col-md-6 col-sm-12 col-xs-12 btn btn-success" id="submitPay" value="Submit"/>
                                        </td>

                                    </tr>


                                </tbody>
                            </table>
                        </div>
                        <!-- /.col -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- footer content -->
<%@include file="../../template/footer.jsp"%>

