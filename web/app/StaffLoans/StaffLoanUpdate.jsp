<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Loan"%>
<%@page import="com.vertec.hibe.model.Attendance"%>
<%@page import="com.vertec.hibe.model.Employee"%>
<%@page import="com.vertec.hibe.model.Department"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/notAlert.js"></script>

<script type="text/javascript">
    // calculate total payable value and monthly payable value
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
        }
    }

    var amount = 0;
    var tamount = 0;
    var interest = 0;
    var terms = 0;
    //Round float value to two decimal
    function roundFloat(num, dec) {
        var d = 1;
        for (var i = 0; i < dec; i++) {
            d += "0";
        }
        return Math.round(num * d) / d;
    }
    //Calculate Total Payable
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
//                alert(tamount);
                var a = parseInt(terms);
                var b = parseInt(12);
                var dmonths = a % b;
                for (var i = 0; i < Math.round(a / b); i++) {
                    inter += tamount * (interest / 100);
                    tamount += inter;
                    inter = 0;
                }
//                alert("Yearly "+tamount);
//                alert("Due Months "+dmonths);
                inter += (tamount * (interest / 100)) * (dmonths / 12);
                tamount += inter;
            }
        }
    }





</script>

<%
    List<Employee> employeeList = (List<Employee>) request.getAttribute("employee");
    Loan loan = (Loan) request.getAttribute("loan");
%>

<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Staff Loans
            </h3>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Add Staff Loans<small></small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form action="Salary?action=SaveLoans" method="post" class="form-horizontal form-label-left" validate>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Employee</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="employee" id="employee"  required="required">
                                    <option selected="true" disabled="true" >Select Employee</option>
                                    <% for (Employee e : employeeList) {%>
                                    <option value="<%=e.getId()%>"><%=e.getFname() + " " + e.getLname() + " " + e.getNic()%></option>
                                    <%}%>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Interest Type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="itype" id="itype"  required="required">
                                    <option selected="true" disabled="true" >Select Interest Type</option>
                                    <option value="0">Simple interest</option>
                                    <option value="1">Compound interest</option>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Interest Term</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="iterm" id="iterm"  required="required">
                                    <option selected="true" disabled="true" >Select Interest Term</option>
                                    <option value="0">Monthly</option>
                                    <option value="1">Yearly</option>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Interest (%)<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="interest" class="form-control col-md-7 col-xs-12" value="<%=loan.getInterest() %>" name="interest" onkeyup="calculatePayable()" required="required" type="number">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Terms (Months)<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="terms" class="form-control col-md-7 col-xs-12"  value="<%=loan.getTerms() %>" name="terms" onkeyup="calculatePayable()" required="required" type="number">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Amount (LKR)<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="amount" class="form-control col-md-7 col-xs-12"  value="<%=loan.getAmount() %>" name="amount" onkeyup="calculatePayable()" required="required" type="number">
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
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <button id="send" type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
</div>
<%} else {%>
<script type="text/javascript">
    window.location = 'error403.jsp';</script>
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
    }).prop('checked', false);</script>
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