
<%@page import="com.vertec.hibe.model.BankAccounts"%>
<%@page import="com.vertec.hibe.model.Bank"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/invoice.js"></script>
<script src="app/js/notAlert.js"></script>

<div class="">
    <%
        BankAccounts bankAccount = (BankAccounts) request.getAttribute("bankAccount");
        List<Bank> bList = (List<Bank>) request.getAttribute("bList");
    %>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Bank Account<small>Update Bank Account</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form action="Bank?action=UpdateSelected" method="post" class="form-horizontal" novalidate>
                        <div class="item form-group" style="padding-top: 20px;" id="branchwise">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Enter Account No <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="hidden" id="accId" name="accId" required="required"  value="<%=bankAccount.getId()%>">
                                <input type="text" id="accNo" name="accNo" required="required" class="form-control col-md-7 col-xs-12" value="<%=bankAccount.getAccountNo()%>">
                            </div>
                        </div>   
                        <div class="item form-group" style="padding-top: 20px;">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Select Account Type <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="accType" id="accType"  required="required">
                                    <option selected="true"  value="Saving Account">Saving Account</option>
                                    <option selected="true"  value="Current Account">Current Account</option>
                                </select>                              
                            </div>
                        </div>
                            <div class="item form-group" style="padding-top: 20px;">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Select Bank <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="bankId" id="bankId"  required="required">
                                    <option selected="true" disabled value="">Select Bank</option>
                                    <%for (Bank b : bList) {%>
                                    <option selected="true"  value="<%=b.getId()%>"><%=b.getBankName() + " " + b.getBranch() %></option>
                                    <%}%>
                                </select>                              
                            </div>
                        </div> 
                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" id="updateP"><i class="fa fa-arrow-right"></i>  Save </button>
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
<script type="text/javascript">
    $("#modal_trigger").leanModal({top: 200, overlay: 0.6, closeButton: ".modal_close"});
    $(function () {
        // Calling Register Form
        $("#modal_trigger").click(function () {
            $(".user_register").show();
            $(".header_title").text('Add Bank');
            return false;
        });
    });
</script>
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
        $('input.tableflat').iCheck({
            checkboxClass: 'icheckbox_flat-green',
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