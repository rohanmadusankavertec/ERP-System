<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<%
    List<Account> account = (List<Account>) request.getAttribute("account");
    List<Account> creditor = (List<Account>) request.getAttribute("creditor");
    List<Account> debtor = (List<Account>) request.getAttribute("debtor");
%>


<script type="text/javascript">

    //Load second account to select element
    function LoadSecontAccounts() {
        var ptype = document.getElementById("pType").value;
        $("#paccount").empty();
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                var reply = eval('(' + xmlHttp.responseText + ')');
                var arr = reply.jArr1;
                var acc1 = document.getElementById("paccount");
                for (var f = 0; arr.length > f; f++) {
                    var acc = document.createElement("option");
                    acc.value = arr[f].id;
                    acc.innerHTML = arr[f].type;
                    acc1.appendChild(acc);
                }
            }
        };
        xmlHttp.open("POST", "Sell?action=getAccountBysubtype&subType=" + ptype, true);
        xmlHttp.send();
    }

    // calculate discount
    function calculatediscount() {

        var price = document.getElementById("price").value;
        var dis2 = document.getElementById("discount").value;
        
        
        var dis = 0.0;
        if(dis2!==""){
             dis = parseFloat(document.getElementById("discount").value);
        }
        if (dis2 === "" || dis === 0) {
            document.getElementById("totalpayable").innerHTML = price;
             document.getElementById("totaldis").innerHTML = 0.0;
        } else if (dis2.indexOf("%") !== -1) {
            dis2.replace("%", "");
            document.getElementById("totalpayable").innerHTML = parseFloat(price) - (parseFloat(price)) * ((parseFloat(dis2)) / 100);
            document.getElementById("totaldis").innerHTML = (parseFloat(price)) * ((parseFloat(dis2)) / 100);

        } else {
            document.getElementById("totalpayable").innerHTML = parseFloat(price) - parseFloat(dis2);
            document.getElementById("totaldis").innerHTML = parseFloat(dis2);
        }
    }


    // calculate payable amount 
    function setPaidAmount() {
        //check for - discount
        calculatediscount();
        document.getElementById("paidamount").value = document.getElementById("totalpayable").innerHTML;
    }
    var cdtype="null";
    //calculate outstanding amount
    function CheckDue() {
        var ptype = document.getElementById("pType").value;
        if (ptype !== "Loan") {
            var pamount = document.getElementById("paidamount");
            pamount.disabled = false;
            var due = parseFloat(document.getElementById("totalpayable").innerHTML) - parseFloat(document.getElementById("paidamount").value);
            if (due < 0) {
                cdtype="creditor";
                document.getElementById("creditordv").className = "";
                document.getElementById("debtordv").className = "hidden";
            } else if (due > 0) {
                cdtype="debtor";
                document.getElementById("creditordv").className = "hidden";
                document.getElementById("debtordv").className = "";
            } else {
                cdtype="null";
                document.getElementById("creditordv").className = "hidden";
                document.getElementById("debtordv").className = "hidden";
            }
        } else {
            var pamount = document.getElementById("paidamount");
            pamount.disabled = true;
            document.getElementById("paidamount").value=document.getElementById("totalpayable").innerHTML;
        }
    }

    function nom_Success(text) {
        BootstrapDialog.show({
            title: 'Notification',
            type: BootstrapDialog.TYPE_SUCCESS,
            message: text,
            size: BootstrapDialog.SIZE_NORMAL
        });
    }
    function sm_warning(text) {
        BootstrapDialog.show({
            title: 'Warning',
            type: BootstrapDialog.TYPE_WARNING,
            message: text,
            size: BootstrapDialog.SIZE_SMALL
        });
    }
    // save sell (Transaction)
    function SaveSell() {
        calculatediscount();
        var acc = document.getElementById("accountName").value;
        var price = document.getElementById("price").value;
        var desc = document.getElementById("description").value;
        var pacc = document.getElementById("paccount").value;
        var tdis = document.getElementById("totaldis").innerHTML;
        var pamount = document.getElementById("paidamount").value;
        var balance = parseFloat(document.getElementById("totalpayable").innerHTML) - parseFloat(pamount);
        
        var cdacc=0;
        
        if(cdtype==="null"){
            cdacc=0;
        }else if(cdtype==="creditor"){
            cdacc=document.getElementById("creditor").value;
        }else if(cdtype==="debtor"){
            cdacc=document.getElementById("debtor").value;
        }
        
        if (acc === "") {
            sm_warning("Please Select Selling Account......");
        } else if (price === "") {
            sm_warning("Please fill Price......");
        } else if (desc === "") {
            sm_warning("Please fill Description......");
        } else if (pacc === "") {
            sm_warning("Please Select Account......");
        } else if (pamount === "") {
            sm_warning("Please fill paid amount......");
        } else {
            var xmlHttp = getAjaxObject();
            xmlHttp.onreadystatechange = function ()
            {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                {
                    var reply = xmlHttp.responseText;
                    if (reply === "Success") {
                        nom_Success("Successfully Saved");
                        setTimeout("location.href = 'Sell?action=ViewSell';", 1500);
                    } else {
                        sm_warning("Sell is Not Added, Please Try again");
                    }
                }
            };
            xmlHttp.open("POST", "Sell?action=SaveSell&acc=" + acc + "&price=" + price + "&desc=" + desc + "&pacc=" + pacc + "&tdis=" + tdis + "&pamount=" + pamount + "&balance=" + balance+ "&cdtype=" + cdtype+ "&cdacc=" + cdacc, true);
            xmlHttp.send();
        }
    }
</script>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Sell
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
                    <form action="#" method="post" class="form-horizontal form-label-left" novalidate>
                        <p>
                            <span class="section">Selling Info</span> </p>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Account Name</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="accountName" id="accountName"  required="required" >
                                    <option selected="true" disabled="true" value="">Select Account Name</option>
                                    <%
                                        for (Account ac : account) {
                                    %>
                                    <option value="<%=ac.getId()%>"><%= ac.getName()%></option>
                                    <%}%>
                                </select>
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Price
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input onkeyup="setPaidAmount()" type="number" id="price" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Description
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <textarea class="form-control col-md-7 col-xs-12" id="description" ></textarea>
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Payment/Receive Type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="pType" id="pType"  required="required" onchange="LoadSecontAccounts();CheckDue();">
                                    <option selected="true" disabled="true" value="">Select Payment Type</option>
                                    <option value="Cash">Cash</option>
                                    <option value="Bank">Bank</option>
                                    <option value="Loan">Loan</option>
                                </select>
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Account</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="paccount" id="paccount"  required="required" >
                                    <option selected="true" disabled="true" value="">Select Account</option>

                                </select>
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Discount</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="text" id="discount" name="discount" placeholder="Discount Ex:10% or 10000" required="required" class="form-control col-md-7 col-xs-12" onkeyup="setPaidAmount()">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Discount</label>
                            <label class="control-label col-md-6 col-sm-6 col-xs-12" style="text-align: left;" id="totaldis">0000.00</label>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Total Payable</label>
                            <label class="control-label col-md-6 col-sm-6 col-xs-12" style="text-align: left;" id="totalpayable">0000.00</label>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Paid Amount
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="number" id="paidamount" name="paidamount" placeholder="Enter Paid Amount" required="required" class="form-control col-md-7 col-xs-12" onkeyup="CheckDue()">
                            </div>
                        </div>
                        <div class="hidden" id="creditordv" style="padding-bottom: 20px;">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Creditor Account</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="creditor" id="creditor"  required="required" >
                                    <option selected="true" disabled="true" value="">Select Creditor Account</option>
                                    <%
                                        for (Account ac : creditor) {
                                    %>
                                    <option value="<%=ac.getId()%>"><%= ac.getName()%></option>
                                    <%}%>
                                </select>
                            </div>
                        </div>
                        <div class="hidden" id="debtordv" style="padding-bottom: 20px;">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Debtor Account</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="debtor" id="debtor"  required="required" >
                                    <option selected="true" disabled="true" value="">Select Debtor Account</option>
                                    <%
                                        for (Account ac : debtor) {
                                    %>
                                    <option value="<%=ac.getId()%>"><%= ac.getName()%></option>
                                    <%}%>
                                </select>
                            </div>
                        </div>
                        <div class="form-group" style="margin-top: 10px;">
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <button type="button" onclick="SaveSell()" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>





<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
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
<script type="text/javascript">

    $("#modal_trigger").leanModal({top: 200, overlay: 0.6, closeButton: ".modal_close"});

    $(function () {


        // Calling Register Form
        $("#modal_trigger").click(function () {
            $(".user_register").show();
            $(".header_title").text('Add New Feature');
            return false;
        });



    });
</script>
