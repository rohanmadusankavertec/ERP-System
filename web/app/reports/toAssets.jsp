<%-- 
    Document   : AddIncome
    Created on : Nov 18, 2016, 3:12:41 PM
    Author     : Java-Dev-Ruchira
--%>

<%@page import="com.vertec.hibe.model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<script type="text/javascript">
    function PrintWindow() {
        $('#accType').val('value');
        $('#acc').val('value');
        $('#from').val('value');
        $('#to').val('value');
        $('#invisible_form').submit();
    }

    //load accounts to select according to acount type
    function LoadSecontAccounts() {
        var ptype = document.getElementById("accType").value;
        $("#acc").empty();
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                var reply = eval('(' + xmlHttp.responseText + ')');
                var arr = reply.jArr1;
                var acc1 = document.getElementById("acc");
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

</script>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Cash Book
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
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <form id="invisible_form" action="Report?action=PrintAssetReport" target="_blank" method="post" class="form-horizontal form-label-left" novalidate>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Account Type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="accType" id="accType" required="required" onchange="LoadSecontAccounts()">
                                    <option selected="true" disabled="true" value="">Select Account Type</option>
                                    <option value="Cash">Cash Accounts</option>
                                    <option value="Bank">Bank Accounts</option>
                                </select>
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Account</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="acc" id="acc" onchange="loadAcconutByPayType()"  required="required" >
                                    <option selected="true" disabled="true" >Select Account </option>
                                    <option value="Cash" >Cash </option>
                                    <option value="Bank" >Bank </option>
                                </select>
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">From Date</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="from" name="from" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">To Date</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="to" name="to" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="ln_solid"></div>        
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <button id="send" onclick="" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>                              

<%@include file="../../template/footer.jsp"%>

