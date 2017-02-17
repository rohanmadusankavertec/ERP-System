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
%>


<script type="text/javascript">

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
        xmlHttp.open("POST", "Purchase?action=getAccountBysubtype&subType=" + ptype, true);
        xmlHttp.send();
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

    function SaveLiability() {
        var acc = document.getElementById("accountName").value;
        var pacc = document.getElementById("paccount").value;
        var itype = document.getElementById("itype").value;
        var iterm = document.getElementById("iterm").value;
        var interest = document.getElementById("interest").value;
        var terms = document.getElementById("terms").value;
        var amount = document.getElementById("amount").value;
        var tpayable = document.getElementById("tpayable").innerHTML;
        var mpayable = document.getElementById("mpayable").innerHTML;
        var desc = document.getElementById("description").value;
        if (acc === "") {
            sm_warning("Please Select Debtor Account......");
        } else if (pacc === "") {
            sm_warning("Please fill Account......");
        } else if (desc === "") {
            sm_warning("Please fill Description......");
        } else if (itype === "") {
            sm_warning("Please Select Interest Type......");
        } else if (iterm === "") {
            sm_warning("Please Enter Interest Terms......");
        }else if (interest === "") {
            sm_warning("Please Enter Interest......");
        }else if (terms === "") {
            sm_warning("Please Enter Terms......");
        }else if (amount === "") {
            sm_warning("Please Enter Amount......");
        } else {
            var xmlHttp = getAjaxObject();
            xmlHttp.onreadystatechange = function ()
            {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                {
                    var reply = xmlHttp.responseText;
                    if (reply === "Success") {
                        nom_Success("Successfully Saved");
                        setTimeout("location.href = 'AssetLoan?action=ViewLoan';", 1500);
                    } else {
                        sm_warning("Libility is Not Added, Please Try again");
                    }
                }
            };
            xmlHttp.open("POST", "AssetLoan?action=SaveLoan&acc=" + acc + "&pacc=" + pacc + "&itype=" + itype + "&iterm=" + iterm + "&interest=" + interest + "&terms=" + terms + "&amount=" + amount+ "&tpayable=" + tpayable+ "&mpayable=" + mpayable+ "&desc=" + desc, true);
            xmlHttp.send();
        }
    }
    
    
    
    
    function calculatePayable() {

        var a1 = document.getElementById("amount").value;
        var i1 = document.getElementById("interest").value;
        var t1 = document.getElementById("terms").value;
        if (a1 !== "" && i1 !== "" && t1 !== "") {
            amount = parseFloat(a1);
            interest = parseFloat(i1);
            terms = parseInt(t1);
            caltotalPayable();
            document.getElementById("tpayable").innerHTML = roundFloat(tamount, 2);
            document.getElementById("mpayable").innerHTML = roundFloat((tamount / terms), 2);
        }else{
            document.getElementById("tpayable").innerHTML = 0.0;
            document.getElementById("mpayable").innerHTML = 0.0;
        }
    }

    var amount = 0;
    var tamount = 0;
    var interest = 0;
    var terms = 0;
    function roundFloat(num, dec) {
        var d = 1;
        for (var i = 0; i < dec; i++) {
            d += "0";
        }
        return Math.round(num * d) / d;
    }

    function caltotalPayable() {
        var itype = document.getElementById("itype").value;
        var iterm = document.getElementById("iterm").value;
        if (itype !== "Select Interest Type" && iterm !== "Select Interest Term") {
            var inter = 0;
            tamount = amount;
            if (itype === "0" && iterm === "0") {

                inter += (tamount * (interest / 100)) * terms;
                tamount += inter;

            } else if (itype === "1" && iterm === "0") {

                for (var i = 0; i < terms; i++) {
                    inter += tamount * (interest / 100);
                    tamount += inter;
                    inter = 0;
                }
            } else if (itype === "0" && iterm === "1") {
                inter += (tamount * (interest / 100)) * (terms / 12);
                tamount += inter;
            } else if (itype === "1" && iterm === "1") {
                var a = parseInt(terms);
                var b = parseInt(12);
                var dmonths = a % b;
                for (var i = 0; i < Math.round(a / b); i++) {
                    inter += tamount * (interest / 100);
                    tamount += inter;
                    inter = 0;
                }
                inter += (tamount * (interest / 100)) * (dmonths / 12);
                tamount += inter;
            }
        }
    }
</script>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Company Loan
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
                            <span class="section">Company Loan Info</span> </p>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Payment Type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="pType" id="pType"  required="required" onchange="LoadSecontAccounts();">
                                    <option selected="true" disabled="true" value="">Select Payment Type</option>
                                    <option value="Cash">Cash</option>
                                    <option value="Bank">Bank</option>
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
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Interest Type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="itype" id="itype"  required="required">
                                    <option selected="true" disabled="true" value="">Select Interest Type</option>
                                    <option value="0">Simple interest</option>
                                    <option value="1">Compound interest</option>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Interest Term</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="iterm" id="iterm"  required="required">
                                    <option selected="true" disabled="true" value="">Select Interest Term</option>
                                    <option value="0">Monthly</option>
                                    <option value="1">Yearly</option>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Interest (%)<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="interest" class="form-control col-md-7 col-xs-12" name="interest" onkeyup="calculatePayable()" required="required" type="number">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Terms (Months)<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="terms" class="form-control col-md-7 col-xs-12" name="terms" onkeyup="calculatePayable()" required="required" type="number">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Amount (LKR)<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="amount" class="form-control col-md-7 col-xs-12" name="amount" onkeyup="calculatePayable()" required="required" type="number">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Total Payable</label>
                            <label class="control-label col-md-7 col-sm-3 col-xs-12" style="text-align: left;" for="name" id="tpayable">0000.00</label>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Monthly Payable</label>
                            <label class="control-label col-md-7 col-sm-3 col-xs-12" style="text-align: left;" for="name" id="mpayable">0000.00</label>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Description
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <textarea class="form-control col-md-7 col-xs-12" id="description" ></textarea>
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                        <div class="form-group" style="margin-top: 10px;">
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <button type="button" onclick="SaveLiability()" class="btn btn-success">Submit</button>
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
