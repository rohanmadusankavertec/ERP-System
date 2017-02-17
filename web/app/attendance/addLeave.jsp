<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Employee"%>
<%@page import="com.vertec.hibe.model.Department"%>
<%@page import="com.vertec.hibe.model.Designation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>


<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>
<%List<Employee> employeeList = (List<Employee>) request.getAttribute("employee");%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Add Leaves
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
                    <form action="Attendance?action=addLeave" method="post" class="form-horizontal form-label-left" validate>
                        <span class="section">Leave Information</span>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Employee</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="employee" id="employee"  required="required" >
                                    <option selected="true" disabled="true" >Select Employee</option>
                                    <% for (Employee e : employeeList) {%>
                                    <option value="<%=e.getId()%>"><%=e.getFname()+" "+e.getLname()+" "+e.getNic()%></option>
                                    <%}%>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Leave Type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="leavetype" id="leavetype"  required="required" >
                                    <option selected="true" disabled="true" >Select Leave Type</option>
                                    <option>Casual Leave</option>
                                    <option>Sick Leave</option>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Description<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="expense" class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="description" placeholder="Enter description" required="required" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">From Date<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="expense" class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="fromdate" required="required" type="date">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">To Date<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12"  data-validate-words="1" id="nic" name="todate"required="required" type="date">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Total Days<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="text" id="days" class="form-control col-md-7 col-xs-12" name="days"required="required">
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
