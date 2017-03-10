<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="js/customer.js"></script>
<script src="js/notAlert.js"></script>




<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Customer Update
            </h3>
        </div>
        <%
            Customer cu = (Customer) request.getAttribute("cu");
        %>

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

                    <form action="Customer?action=UpSel" method="post" class="form-horizontal form-label-left" novalidate >

                        </p>
                        <span class="section">User Update</span>

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name"> Customer Name <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="customerName" class="form-control col-md-7 col-xs-12"  name="customerName" data-validate-words="2" required="required" type="text" value="<%=cu.getCustomerName()%>">
                                <input id="customerId" name="customerId" required="required" type="hidden" value="<%=cu.getCustomerId()%>">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Address <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="text" id="address" name="address" required="required" value="<%=cu.getAddress()%>" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Hotline <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="tel" id="hotline" name="hotline"  required="required" value="<%=cu.getHotline()%>" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="contactNo">Office No <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="tel" id="officeNo" name="officeNo" required="required" placeholder="Office Number" value="<%=cu.getOfficeNo()%>" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="contactNo">Fax No <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="tel" id="faxNo" name="faxNo" required="required" placeholder="Fax Number" value="<%=cu.getFaxNo()%>" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="contactNo">Contact Person <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="text" id="contactPerson" name="contactPerson" required="required" value="<%=cu.getContactPerson()%>" placeholder="Contact Person Name" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="contactNo">Contact Person No <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="tel" id="contactPersonNo" name="contactPersonNo" required="required" placeholder="Contact Person No" value="<%=cu.getContactPersonNo()%>"  class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password" class="control-label col-md-3">Contact Person Email</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="contactPersonEmail" type="email" name="contactPersonEmail"  class="form-control col-md-7 col-xs-12" value="<%=cu.getContactPersonEmail()%>" required="required">
                            </div>
                        </div>
                            
                        <div class="item form-group">
                            <label for="password" class="control-label col-md-3">Credit Limit</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="creditLimit" type="number" name="creditLimit"  class="form-control col-md-7 col-xs-12" value="<%=cu.getCreditLimit()%>" required="required">
                            </div>
                        </div>
                            
                        <div class="item form-group">
                            <label for="password" class="control-label col-md-3">Credit Period (Days)</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="creditPeriod" type="number" name="creditPeriod"  class="form-control col-md-7 col-xs-12" value="<%=cu.getCreditPeriod()%>" required="required">
                            </div>
                        </div>
                            
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
