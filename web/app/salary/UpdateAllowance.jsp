<%-- 
    Document   : addAllowance
    Created on : Sep 30, 2016, 9:09:08 AM
    Author     : Rohan Madusanka
--%>

<%@page import="com.vertec.hibe.model.AllowanceDeduction"%>
<%@page import="com.vertec.hibe.model.Employee"%>
<%@page import="com.vertec.hibe.model.Department"%>
<%@page import="com.vertec.hibe.model.Designation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>
<%
    AllowanceDeduction al = (AllowanceDeduction) request.getAttribute("allowance");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Allowance / Deduction
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
                    <form action="Allowance?action=updateallowance" method="post" class="form-horizontal form-label-left" novalidate>
                        <input type="hidden" name="aid" value="<%=al.getId()%>"/>
                        
                        <span class="section">Allowance / Deduction  Info</span>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Employee<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input disabled id="employee" value="<%= al.getEmployeeId().getFname() + " " + al.getEmployeeId().getLname()%>" class="form-control col-md-7 col-xs-12" data-validate-words="1" name="employee" placeholder="Enter Name" required="required" type="text">
                            </div>
                        </div> 
                        <%
                        String type="Deduction";
                        if(al.getIsAllowance()){
                        type="Allowance";
                        }
                        %>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Type<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input disabled id="type" value="<%=type%>" class="form-control col-md-7 col-xs-12" data-validate-words="1" name="type" placeholder="Enter Name" required="required" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Allowance / Deduction Name<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="name" value="<%=al.getName()%>" class="form-control col-md-7 col-xs-12" data-validate-words="1" name="name" placeholder="Enter Name" required="required" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Amount<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="amount" value="<%=al.getValue()%>" class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="amount" placeholder="Enter Amount" required="required" type="text">
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
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
    window.location = '../../error403.jsp';
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
