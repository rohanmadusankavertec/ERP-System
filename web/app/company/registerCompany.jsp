<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="../js/customer.js"></script>
<script src="../js/notAlert.js"></script>

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
    
    
    // validate and save Company
    function SaveCompany() {
        
        var name = document.getElementById("Name").value;
        
        var add = document.getElementById("address").value;

        var contact = document.getElementById("hotline").value;
        
        var email = document.getElementById("Email").value;
        
        if (name === "") {
            sm_warning("Please fill the name......");
        } else if (add === "") {
            sm_warning("Please fill address......");
        } else if (contact === "") {
            sm_warning("Please fill Contact Number......");
        } else if (email === "") {
            sm_warning("Please fill email......");
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
                        setTimeout("location.href = 'Company?action=loadCompanypage';", 1500);
                    } else {
                        sm_warning("Company is Not Added, Please Try again");
                    }
                }
            };
            xmlHttp.open("POST", "Company?action=saveCompany&name=" + name + "&address=" + add + "&contact=" + contact + "&email=" + email , true);
            xmlHttp.send();
        }
    }
    
</script>


<%if (ca.checkUserAuth("ADD_CUSTOMER", group) != null) {%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Company Registration
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
                    <form action="#" method="post" class="form-horizontal form-label-left" novalidate>
                        </p>
                        <span class="section">Company Info</span>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Company Name <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="Name" class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="Name" placeholder="Enter Customer Name...." required="required" type="text">
                            </div>
                        </div>

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Address <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="text" id="address" name="address" placeholder="Enter Address...." required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Contact Number <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="tel" id="hotline" name="hotline" placeholder="Enter Contact number...." required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        
                        
                        
                        

                        <div class="item form-group">
                            <label for="password" class="control-label col-md-3">Email</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="Email" type="email" name="Email"  class="form-control col-md-7 col-xs-12" placeholder="Enter Email...." required="required" onblur="checkEmail();" >
                            </div>
                        </div>
                        
                        
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <button id="send" type="button" onclick="SaveCompany()" class="btn btn-success">Submit</button>
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
