<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.SysUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="../js/sysuser.js"></script>
<script src="../js/notAlert.js"></script>




<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                User Update
            </h3>
        </div>
        <%SysUser su = (SysUser) session.getAttribute("user");%>

    </div>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small></small></h2>
                    
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <form action="../../User?action=UpPw" method="post" class="form-horizontal form-label-left" novalidate >

                        
                        <span class="section">User Update</span>
                        <p><font color="red">After You change Password it's automatically Logout and You have to Login again Using New Password</font></p>
                        <div class="item form-group">
                            <label for="password" class="control-label col-md-3">Current Password</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="currentPass" type="password" name="currentPass"  class="form-control col-md-7 col-xs-12" required="required" onchange="checkPW();">
                                <input id="userId" type="hidden" name="userId" value="<%=su.getSysuserId()%>">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password" class="control-label col-md-3">New Password</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="password" type="password" name="password"  class="form-control col-md-7 col-xs-12" required="required">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Repeat New Password</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="password2" type="password" name="password2" data-validate-linked="password" class="form-control col-md-7 col-xs-12" required="required">
                            </div>
                        </div>





                        <!--                        <div class="item form-group">
                                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                                        <input type="button" onclick="emailValid();">
                                                    </div>
                                                </div>-->

                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <!--                                <button type="submit" class="btn btn-primary">Cancel</button>-->
                                <button id="send" type="submit" class="btn btn-success">Update</button>
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
