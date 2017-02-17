<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Advance"%>
<%@page import="com.vertec.hibe.model.BankAccounts"%>
<%@page import="com.vertec.hibe.model.Employee"%>
<%@page import="com.vertec.hibe.model.Designation"%>
<%@page import="com.vertec.hibe.model.PayrollDefault"%>
<%@page import="com.vertec.hibe.model.HollyDay"%>
<%@page import="com.vertec.hibe.model.Department"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/notAlert.js"></script>
<script type="text/javascript">
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
    //Delete Advance payment
    function DeleteAdvance(id) {
        BootstrapDialog.show({
            message: 'Do you want to Delete ?',
            closable: false,
            buttons: [{
                    label: 'Yes',
                    action: function(dialogRef) {
                        dialogRef.close();
                        var xmlHttp = getAjaxObject();
                        xmlHttp.onreadystatechange = function()
                        {
                            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                            {
                                var reply = xmlHttp.responseText;
                                if (reply === "Success") {
                                    nom_Success("Advance Payment Deleted ");
                                    setTimeout("location.href = 'Salary?action=ViewAdvancePayment';", 1500);
                                } else {
                                    sm_warning("Advance Payment is Not Deleted, Please Try again.");
                                }
                            }
                        };
                        xmlHttp.open("POST", "Salary?action=deleteAdvancePayment&id=" + id, true);
                        xmlHttp.send();
                    }
                }, {
                    label: 'No',
                    action: function(dialogRef) {
                        dialogRef.close();
                    }
                }]
        });
    }
    //Save advance payment
    function SaveAdvance() {
        var employee = document.getElementById("employee").value;
        var payType = document.getElementById("payType").value;
        var bank = document.getElementById("bank").value;
        var amount = document.getElementById("amount").value;
        var cno = document.getElementById("cno").value;
        var cdate = document.getElementById("cdate").value;
        var bool = false;
        if (employee==="Select Employee"){
            sm_warning("Please select an employee.");
        }else if(amount===""){
            sm_warning("Please enter the amount.");
        }else{
            bool=true;   
        }
        if(bool){
            if(payType==="2"){
                if(bank==="Select Bank Account"){
                    bool=false;
                    sm_warning("Please select the bank account.");
                }
            }else if(payType==="3"){
                if(bank==="Select Bank Account"){
                    bool=false;
                    sm_warning("Please select the bank account.");
                }else if(cno===""){
                    bool=false;
                    sm_warning("Please enter the chaque number.");
                }else if(cdate===""){
                    bool=false;
                    sm_warning("Please enter the cheque date.");
                }
            }
        }
        if (bool) {
            BootstrapDialog.show({
                message: 'Do you want to Save ?',
                closable: false,
                buttons: [{
                        label: 'Yes',
                        action: function(dialogRef) {
                            dialogRef.close();
                            var xmlHttp = getAjaxObject();
                            xmlHttp.onreadystatechange = function()
                            {
                                if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                                {
                                    var reply = xmlHttp.responseText;
                                    if (reply === "Success") {
                                        nom_Success("Advance Payment Successfully Saved ...");
                                        setTimeout("location.href = 'Salary?action=ViewAdvancePayment';", 1500);
                                    } else {
                                        sm_warning("Advance payment is Not Saved, Please Try again.");
                                    }
                                }
                            };
                            var employee = document.getElementById("employee").value;
                            var payType = document.getElementById("payType").value;
                            var bank = document.getElementById("bank").value;
                            var amount = document.getElementById("amount").value;
                            var cno = document.getElementById("cno").value;
                            var cdate = document.getElementById("cdate").value;

                            xmlHttp.open("POST", "Salary?action=saveAdvance&e=" + employee + "&pt=" + payType + "&bank=" + bank + "&amount=" + amount + "&cno=" + cno + "&cdate=" + cdate, true);
                            xmlHttp.send();
                        }
                    }, {
                        label: 'No',
                        action: function(dialogRef) {
                            dialogRef.close();
                        }
                    }]
            });
        }
    }
    //show and hide fields according to payment type
    function ChangeHides() {
        var Type = document.getElementById('payType').value;
        if (Type === "1") {
            document.getElementById('bafield').className = 'hidden';
            document.getElementById('cnofield').className = 'hidden';
            document.getElementById('cdatefield').className = 'hidden';
        } else if (Type === "2") {
            document.getElementById('bafield').className = 'item form-group';
            document.getElementById('cnofield').className = 'hidden';
            document.getElementById('cdatefield').className = 'hidden';
        } else if (Type === "3") {
            document.getElementById('bafield').className = 'item form-group';
            document.getElementById('cnofield').className = 'item form-group';
            document.getElementById('cdatefield').className = 'item form-group';
        }
    }
</script>



<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Advance Payment
                <%
                    List<Employee> employee = (List<Employee>) request.getAttribute("employee");
                    List<BankAccounts> bankaccounts = (List<BankAccounts>) request.getAttribute("bank");
                    List<Advance> advance = (List<Advance>) request.getAttribute("advance");
                %>
            </h3>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Default Payments<small></small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form action="" method="post" class="form-horizontal form-label-left" validate>
                        <span class="section"></span>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Employee</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="employee" id="employee"  required="required" >
                                    <option selected="true" disabled="true" >Select Employee</option>
                                    <%for (Employee emp : employee) {%>
                                    <option value="<%=emp.getId().toString()%>"><%= emp.getFname() + " " + emp.getLname()%></option>
                                    <%
                                        }
                                    %>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Payment Type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="payType" id="payType" onchange="ChangeHides()" required="required" >
                                    <option value="1">Cash</option>
                                    <option value="2">Bank Transfer</option>
                                    <option value="3">Cheque</option>
                                </select>                            
                            </div>
                        </div>        
                        <div class="hidden" id="bafield">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Bank Accounts</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="bank" id="bank"  required="required" >
                                    <option selected="true" disabled="true" >Select Bank Account</option>
                                    <%for (BankAccounts bank : bankaccounts) {%>
                                    <option value="<%= bank.getId()%>"><%= bank.getBankId().getBankName() + " " + bank.getAccountNo()%></option>
                                    <%
                                        }
                                    %>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Amount<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12" data-validate-words="1" id="amount" name="amount" required="required" type="number">
                            </div>
                        </div>
                        <div class="hidden" id="cnofield">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Cheque No<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="cno" id="cno" required="required" type="text">
                            </div>
                        </div>
                        <div class="hidden" id="cdatefield">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Cheque Date<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="cdate" id="cdate" required="required" type="date">
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <input type="button" value="Save" onclick="SaveAdvance()" class="btn btn-success"/>
                                <!--<button id="send" type="submit" class="btn btn-success">Submit</button>-->
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="row">

        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Current Default Payments <small>up to now</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="table-responsive">
                        <table id="example" class="table table-striped responsive-utilities jambo_table">
                            <thead>
                                <tr class="headings">
                                    <th>#</th>
                                    <th>Employee </th>
                                    <th>Date</th>
                                    <th>Amount</th>
                                    <th class=" no-link last"><span class="nobr">Action</span></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%for (Advance pi : advance) {%>
                                <tr>
                                    <td class=""><%= pi.getId()%></td>
                                    <td class=""><%=pi.getEmployeeId().getFname() + " " + pi.getEmployeeId().getLname()%></td>
                                    <td class=""><%=pi.getDate()%></td>
                                    <td class=""><%=pi.getAmount()%></td>
                                    <td class="last"><input type="submit" value="Delete" class="btn btn-warning" onclick="DeleteAdvance('<%=pi.getId()%>')" /> </td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <br/>
        <br/>
        <br/>
    </div>
</div>
<%} else {%>
<script type="text/javascript">
    window.location = 'error403.jsp';
</script>
<%}%>





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
            .on('keyup blur', 'input', function() {
                validator.checkField.apply($(this).siblings().last()[0]);
            });

    // bind the validation to the form submit event
    //$('#send').click('submit');//.prop('disabled', true);

    $('form').submit(function(e) {
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
    $('#vfields').change(function() {
        $('form').toggleClass('mode2');
    }).prop('checked', false);

    $('#alerts').change(function() {
        validator.defaults.alerts = (this.checked) ? false : true;
        if (this.checked)
            $('form .alert').remove();
    }).prop('checked', false);
</script>
<script>
    $(document).ready(function() {
        $('input.tableflat').iCheck({
            checkboxClass: 'icheckbox_flat-green',
            radioClass: 'iradio_flat-green'
        });
    });

    var asInitVals = new Array();
    $(document).ready(function() {
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
        $("tfoot input").keyup(function() {
            /* Filter on the column based on the index of this element's parent <th> */
            oTable.fnFilter(this.value, $("tfoot th").index($(this).parent()));
        });
        $("tfoot input").each(function(i) {
            asInitVals[i] = this.value;
        });
        $("tfoot input").focus(function() {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("tfoot input").blur(function(i) {
            if (this.value == "") {
                this.className = "search_init";
                this.value = asInitVals[$("tfoot input").index(this)];
            }
        });
    });
</script>