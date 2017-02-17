

<%@page import="com.vertec.hibe.model.Supplier"%>
<%@page import="com.vertec.hibe.model.Branch"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<script src="app/js/notAlert.js"></script>

<script>

    function sm_warning(text) {
        BootstrapDialog.show({
            title: 'Warning',
            type: BootstrapDialog.TYPE_WARNING,
            message: text,
            size: BootstrapDialog.SIZE_SMALL
        });
    }
    function nom_Success(text) {
        BootstrapDialog.show({
            title: 'Notification',
            type: BootstrapDialog.TYPE_SUCCESS,
            message: text,
            size: BootstrapDialog.SIZE_NORMAL
        });
    }
    
    //Load outstanding details according to supplier
    function loadAccordingSupp() {
        $("#paymentAvaTable").empty();

        var suppId = document.getElementById('supplierId').value;
        $.ajax({
            type: "POST",
            url: "Payment?action=doGRNPayment&supplierId=" + suppId,
            success: function (msg) {
                // alert(msg);
                var reply = eval('(' + msg + ')');
                var arrLn1 = reply.grninfo;
                var bpmtable = document.getElementById('paymentAvaTable');
//            var bpmhead = document.getElementById('chequeStaHead');
                var thead = document.createElement("thead");
                var row1 = document.createElement("tr");
                row1.id = 'headRow';
                var colu1 = document.createElement("td");
                colu1.align = "center";
                colu1.type = "text";
                colu1.innerHTML = 'GRN_Id';
                var colu2 = document.createElement("td");
                colu2.align = "center";
                colu2.type = "text";
                colu2.innerHTML = 'GRN Date';
                var colu3 = document.createElement("td");
                colu3.align = "center";
                colu3.type = "text";
                colu3.innerHTML = 'Total GRN Amount';
                var colu4 = document.createElement("td");
                colu4.align = "center";
                colu4.type = "text";
                colu4.innerHTML = 'Balance Outstanding';
                var colu5 = document.createElement("td");
                colu5.align = "center";
                colu5.type = "text";
                colu5.innerHTML = 'Action';

                row1.appendChild(colu1);
                row1.appendChild(colu2);
                row1.appendChild(colu3);
                row1.appendChild(colu4);
                row1.appendChild(colu5);

                thead.appendChild(row1);
                bpmtable.appendChild(thead);
                var totOutstanding = 0;

                for (var f = 0; arrLn1.length > f; f++) {
                    var supID = arrLn1[f].id;
                    var balance = arrLn1[f].outstanding;
                    var tot = arrLn1[f].total;
                    var date = arrLn1[f].date;

                    var row = document.createElement("tr");
                    row.id = supID;
                    var col1 = document.createElement("td");
                    col1.align = "center";
                    col1.type = "text";
                    col1.value = supID;
                    col1.innerHTML = supID;
                    var col2 = document.createElement("td");
                    col2.align = "center";
                    col2.type = "text";
                    col2.value = date;
                    col2.innerHTML = date;
                    var col3 = document.createElement("td");
                    col3.align = "center";
                    col3.type = "text";
                    col3.value = tot;
                    col3.innerHTML = tot;
                    var col4 = document.createElement("td");
                    col4.align = "center";
                    col4.type = "text";
                    col4.value = balance;
                    col4.innerHTML = balance;
                    var col5 = document.createElement("td");
                    var elem1 = document.createElement("span");
                    elem1.id = 'update';
                    elem1.name = 'update';
                    elem1.type = "button";
                    elem1.className = "btn btn-default glyphicon glyphicon-edit";
                    col5.appendChild(elem1);


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
                coluFoot1.setAttribute("colspan", 3);
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

    //Load payment details when press payment button
    $(document).on('click', '#paymentAvaTable span', function () {
        var tr = $(this).closest('tr');
        var supId = tr.find('td:first-child').text();
        var date = tr.find('td:nth-child(2)').text();
        var tot = tr.find('td:nth-child(3)').text();
        var bal = tr.find('td:nth-child(4)').text();
        BootstrapDialog.show({
            message: 'Are you select Correct Invoice to Pay?',
            closable: false,
            buttons: [{
                    label: 'Yes',
                    action: function (dialogRef) {
                        dialogRef.close();

                        document.getElementById('paymentTable').className = 'row';
                        document.getElementById('grnId').value = supId;
                        document.getElementById('grnTot').value = tot;
                        document.getElementById('balAmount').value = bal;


                    }
                }, {
                    label: 'No',
                    action: function (dialogRef) {
                        dialogRef.close();
                    }
                }]
        });


    });

    //show and hide cheque details according to payment type
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
    // save payment and validation
    function sendData() {
        var dataArr = [];
        var payAmount = document.getElementById('payAmount').value;
        var bal = document.getElementById('balAmount').value;
        var tot = document.getElementById('grnTot').value;
        var payType = document.getElementById('paymentType').value;
        var grnId = document.getElementById('grnId').value;

        if (payAmount == "" || payType == "") {
            sm_warning("Please Fill the Feilds....");
        }
        if (payType === "2") {
            var chkNo = document.getElementById('chkNo').value;
            var bankName = document.getElementById('bankName').value;
            var chDate = document.getElementById('chDate').value;
            if (chkNo != "" && bankName != "" && chDate != "") {
                dataArr = [payType, payAmount, tot, bal, grnId, chkNo, bankName, chDate];
            } else {
                sm_warning("Please Fill the Feilds....");
            }
        } else {
            dataArr = [payType, payAmount, tot, bal, grnId];
        }

        if (parseFloat(bal) < parseFloat(payAmount)) {
            sm_warning("Paymet Amount is greater than your balance Payment, Please Try again");
            document.getElementById('payAmount').value = "";
        } else {
            $.ajax({
                type: "POST",
                url: "Payment?action=saveGRNpayment&dataArr=" + dataArr,
                success: function (msg) {
                    // alert(msg);
                    if (msg === "Success") {
                        nom_Success("Successfully Payment Added");
                        setTimeout("location.href = 'Payment?action=ToDoGRNPayment';", 1000);
                    } else {
                        sm_warning("Not Submited Payment, Please Try again");
                    }
                }
            });
        }
    }


</script>


<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                GRN Payment
            </h3>
        </div>

    </div>
    <%
        List<Supplier> suppList = (List<Supplier>) request.getAttribute("suppList");
    %>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Payment for GRN</h2>
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
                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Supplier <span class="required"></span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="supplierId" id="supplierId"  required="required" onchange="loadAccordingSupp();">
                                <option selected="true" disabled value="">Select Supplier</option>
                                <%for (Supplier c : suppList) {%>
                                <option value="<%=c.getSupplierId()%>"><%=c.getSupplierName() + "-" + c.getCompanyName()%></option>
                                <%}%>
                            </select>
                        </div>
                    </div>  
                    <div class="row" style="padding-top: 100px;">
                        <div class="table-responsive">
                            <div class="col-xs-12 table">
                                <table class="table table-striped" id="paymentAvaTable">
                                    <thead>
                                        
                                    </thead>
                                    <tbody id="paymentAvaBody">


                                    </tbody>
                                </table>
                            </div>
                            <!-- /.col -->
                        </div>
                    </div>

                    <div class="hidden" id="paymentTable" style="padding-top: 100px;">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover table-striped" id="invDate" >

                                <tbody id="gendata">

                                    <tr>
                                        <td width="20%" align="center" > GRN Amount</td>
                                        <td width="20%" align="center" > <input class="form-control" type="hidden" name="grnId" id="grnId" value=""/><input class="form-control" type="text" name="grnTot" id="grnTot" readonly/></td>

                                    </tr>
                                    <tr>
                                        <td width="20%" align="center" > Balance Amount</td>
                                        <td width="20%" align="center" > <input class="form-control" type="text" name="balAmount" id="balAmount" readonly/></td>

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

