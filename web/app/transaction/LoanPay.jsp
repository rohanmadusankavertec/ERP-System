<%-- 
    Document   : LoanPay
    Created on : Nov 29, 2016, 9:39:09 AM
    Author     : Java-Dev-Ruchira
--%>

<%@page import="com.vertec.hibe.model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<script type="text/javascript">
    
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
    //Load account to select element
    function loadAccount() {
        var acc = document.getElementById("payType").value;
        $.ajax({
            type: 'POST',
            url: "LoanPay?action=loadLoanAccount&LoanType="+acc,
            success: function (msg) {
                var reply = eval('('+msg+')');
                var arrL = reply.account;
                var inhtml = "";
                inhtml+="<option selected='true' disabled='true' >Select Account </option>";
                var account = document.getElementById("account");
                    for(var i=0; i<arrL.length; i++){
                        inhtml+="<option value='"+arrL[i].id+"'>"+arrL[i].name+"</option>";
                    }
                    account.innerHTML=inhtml;
                }
        });
        
    }
    //Load account to select element according to payment type
    function loadAcconutByPayType(){
        var acc = document.getElementById("payType1").value;
        $.ajax({
            type: 'POST',
            url: "LoanPay?action=loadPayType&payType="+acc,
            success: function (msg) {
                
                var reply = eval('('+msg+')');
                var arrL = reply.account;
                var inhtml = "";
                inhtml+="<option selected='true' disabled='true' >Select Account </option>";
                var account = document.getElementById("acc");
                    for(var i=0; i<arrL.length; i++){
                        inhtml+="<option value='"+arrL[i].id+"'>"+arrL[i].name+"</option>";
                    }
                    account.innerHTML=inhtml;
                }
        });
    }
    var paid = "";
    // Load loan details
    function loadLoan(){
        var accId = document.getElementById("account").value;
        $.ajax({
            type: 'POST',
            url: "LoanPay?action=loadLoan&Loan="+accId,
            success: function (msg) {
                var reply = eval('('+msg+')');
                var arrL = reply.loan;
                var inhtml = "";
                inhtml+="<option selected='true' disabled='true' >Select Account </option>";
                var account = document.getElementById("loan");
                    for(var i=0; i<arrL.length; i++){
                        inhtml+="<option value='"+arrL[i].id+"'>"+arrL[i].descrip+"  "+"("+arrL[i].incriment+")"+"</option>";
                    }
                    account.innerHTML=inhtml;
                }
        });
    }
    
    //Save Loan Details
    function SaveLoan() {
        var acc1 = document.getElementById("payType").value;
        var loanId = document.getElementById("loan").value;
        var acc = document.getElementById("account").value;
        var amt = document.getElementById("amount").value;
        var desc = document.getElementById("descrip").value;
        var pacc = document.getElementById("acc").value;
        if (acc1 === "") {
            sm_warning("Please Select Loan Type......");
        } else if (acc === "") {
            sm_warning("Please fill Account......");
        } else if (amt === "") {
            sm_warning("Please fill amount......");
        } else if (desc === "") {
            sm_warning("Please fill Description......");
        } else if (pacc === "") {
            sm_warning("Please Select Account......");
        } 
         else {
            var xmlHttp = getAjaxObject();
            xmlHttp.onreadystatechange = function ()
            {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                {
                    var reply = xmlHttp.responseText;
                    if (reply === "Success") {
                        nom_Success("Successfully Saved");
                        setTimeout("location.href = 'LoanPay?action=loadLoanPayPage';", 1500);
                    } else {
                        sm_warning("Loan payment is Not Added, Please Try again");
                    }
                }
            };
            xmlHttp.open("POST", "LoanPay?action=saveLoanPayment&account=" + acc + "&amount=" + amt + "&descrip=" + desc + "&acc=" + pacc + "&loanId=" + loanId + "&loanType=" + acc1, true);
            xmlHttp.send();
        }
    }
    //Load Loan according to id
    function loadLoanById(){
        alert("aaaa");
        var lId = document.getElementById("loan").value;
        alert("aaaa"+lId);
        $.ajax({
            type: 'POST',
            url: "LoanPay?action=loadLoanById&loanId="+lId,
            success: function (msg) {
                var reply = eval('('+msg+')');
                alert(reply);
                document.getElementById("amount").innerHTML=reply.amount;
                document.getElementById("outAmount").innerHTML=reply.tot-reply.paid;
                }
        });
    }
</script>

<%
    List<Account> accList = (List<Account>) request.getAttribute("");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Add New Loan Payment
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

                    <form action="#" method="post" class="form-horizontal form-label-left" novalidate>

                        </p>
                        <span class="section">Loan Payment Info</span>
                        
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Loan Payment Type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="payType" id="payType" onchange="loadAccount()"  required="required" >
                                    <option selected="true" disabled="true" >Select Loan Type </option>
                                    <option value="Creditors" >Creditors </option>
                                    <option value="Debtors" >Debtors </option>
                                    
                                </select>
                            </div>
                        </div>
                        
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Select Account</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="account" id="account" onchange="loadLoan()"  required="required" >
                                    <option selected="true" disabled="true" >Select Account </option>
                                    
                                </select>
                            </div>
                        </div>
                        
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">The Loan</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="loan" id="loan" onchange="loadLoanById()"  required="required" >
                                    <option selected="true" disabled="true" >Select The Loan</option>
                                    
                                </select>
                            </div>
                        </div>
                        
                        <div  class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Amount </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <label id="amount" for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">RS: 000000.00</label>
                                <!--<input id="amount" type="number" name="amount" data-validate-linked="password" class="form-control col-md-7 col-xs-12" required="required">-->
                            </div>
                        </div>
                        <div  class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Outstanding Amount</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <label id="outAmount" for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">RS: 000000.00</label>
                                <!--<input id="amount" type="number" name="amount" data-validate-linked="password" class="form-control col-md-7 col-xs-12" required="required">-->
                            </div>
                        </div>
                                
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Amount </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="amount" type="number" name="amount" data-validate-linked="password" class="form-control col-md-7 col-xs-12" required="required">
                            </div>
                        </div> 
                        
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Description </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <textarea id="descrip" type="text" name="descrip" data-validate-linked="password" class="form-control col-md-7 col-xs-12" required="required" ></textarea>
                                
                            </div>
                        </div>
                        
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Payment Type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="payType1" id="payType1" onchange="loadAcconutByPayType()"  required="required" >
                                    <option selected="true" disabled="true" >Select Account </option>
                                    <option value="Cash" >Cash </option>
                                    <option value="Bank" >Bank </option>
                                    
                                </select>
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Accounts </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="acc" id="acc" onchange=""  required="required" >
                                    <option selected="true" disabled="true" >Select Accounts </option>
                                </select>
                            </div>
                        </div> 
                        
                        
                        <div class="ln_solid"></div>        
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <!--                                <button type="submit" class="btn btn-primary">Cancel</button>-->
                                <button id="send" type="button" onclick="SaveLoan()" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                        
                       


<!--                        <div class="ln_solid"></div>-->
                        
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>



<%@include file="../../template/footer.jsp"%>
