<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Supplier"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="js/supplier.js"></script>
<script src="js/notAlert.js"></script>



<%if (ca.checkUserAuth("UPDATE_SUPPLIER", group) != null) {%>


<div class="">
    <!--    <div class="page-title">
            <div class="title_left">
                <h3>
                    User Update
                </h3>
            </div>
            
    
        </div>-->

    <%Supplier cu = (Supplier) request.getAttribute("su");%>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small></small></h2>
                    <!--                    <ul class="nav navbar-right panel_toolbox">
                                            <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                            </li>
                                            <li class="dropdown">
                                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                                <ul class="dropdown-menu" role="menu">
                                                    <li><a href="#">Settings 1</a>
                                                    </li>
                                                    <li><a href="#">Settings 2</a>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li><a class="close-link"><i class="fa fa-close"></i></a>
                                            </li>
                                        </ul>-->
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <form action="Supplier?action=UpSel" method="post" class="form-horizontal form-label-left" novalidate >

                        </p>
                        <span class="section">Supplier Update</span>

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name"> Supplier Name <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="supplierName" class="form-control col-md-7 col-xs-12"  name="supplierName" data-validate-words="2" required="required" type="text" value="<%=cu.getSupplierName()%>">
                                <input id="supplierId" name="supplierId" required="required" type="hidden" value="<%=cu.getSupplierId()%>">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Company Name <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="text" id="companyName" name="companyName" required="required" value="<%=cu.getCompanyName()%>" class="form-control col-md-7 col-xs-12">
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
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Land Line No.<span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="tel" id="land" name="land"  required="required" value="<%=cu.getLand()%>" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="contactNo">Mobile No. <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="tel" id="mobile" name="mobile" required="required" placeholder="Enter Mobile Number" value="<%=cu.getMobile()%>" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="contactNo">Fax No <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="tel" id="faxNo" name="faxNo" required="required" placeholder="Enter Fax Number" value="<%=cu.getFax()%>" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Email<span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="text" id="email" name="email" required="required" value="<%=cu.getEmail()%>" placeholder="Enter Email" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                            <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Supplier Type <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="suppliertype" id="suppliertype"  required="required" >
                                    <option disabled="true">Supplier Type</option>

                                    
<%
                                        
                                            if (cu.getType()==false) {
                                        %>
                                        <option selected="" value="0">Local</option>
                                    <option value="1">Foreign</option>

                                        <%
                                        } else {
                                        %>
                                        <option value="0">Local</option>
                                        <option selected="" value="1">Foreign</option>

                                        <%
                                            }
                                        %>

                                </select>                              </div>
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
