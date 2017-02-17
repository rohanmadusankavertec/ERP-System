<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.EmployeeType"%>
<%@page import="com.vertec.hibe.model.Department"%>
<%@page import="com.vertec.hibe.model.Designation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>


<script type="text/javascript">
    //Set gender and dob according to nic
    function getNIC() {
        var nic = document.getElementById("nic").value;
        if (nic.length === 10 && ((nic.indexOf("v") !== -1) | (nic.indexOf("V") !== -1))) {
            var xmlHttp = getAjaxObject();
            xmlHttp.onreadystatechange = function ()
            {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                {
                    var reply = xmlHttp.responseText;
                    if (reply.length > 12) {
                        var data = reply.split("_");
                        document.getElementById("dob").value = data[0];
                        document.getElementById("dob2").value = data[0];
                        if (data[1] === "Male") {
                            document.getElementById("gender").value = "1";
                            document.getElementById("gender2").value = "1";
                        } else {
                            document.getElementById("gender").value = "0";
                            document.getElementById("gender2").value = "0";
                        }
                    }
                }
            };
            xmlHttp.open("POST", "Employee?action=AutoFill&nic=" + nic, true);
            xmlHttp.send();
        }
    }
//Load Designation to select element
    function loadDesignation() {
        $("#designation").empty();
        var desi = document.getElementById('designation');
        var t1 = document.createElement("option");
        t1.innerHTML = "Select Designation";
        desi.appendChild(t1);
        var dept = document.getElementById('departmentname').value;
        $.ajax({
            type: "POST",
            url: "Employee?action=getDesignation&id=" + dept,
            success: function (msg) {
                var reply = eval('(' + msg + ')');

                var arrLn1 = reply.des;
                for (var f = 0; f < arrLn1.length; f++) {
                    var t = document.createElement("option");
                    var val = arrLn1[f].id;
                    t.value = val;
                    t.innerHTML = arrLn1[f].designation;
                    desi.appendChild(t);
                }
            }
        });
    }
</script>


<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>
<%
    List<Designation> designation = (List<Designation>) request.getAttribute("designations");
    List<Department> department = (List<Department>) request.getAttribute("departments");
    List<EmployeeType> employeetype = (List<EmployeeType>) request.getAttribute("employeetype");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Employee Registration
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

                    <form action="Employee?action=addEmployee" method="post" class="form-horizontal form-label-left" validate>

                        <span class="section">Employee Info</span>

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">First Name<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="expense" class="form-control col-md-7 col-xs-12" required="required" name="firstname" placeholder="Enter First Name" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Last Name<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="expense" class="form-control col-md-7 col-xs-12" required="required" name="lastname" placeholder="Enter Last Name" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">NIC<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12" id="nic" name="nic" pattern="[0-9]{9}[v]" onkeyup="getNIC()" placeholder="Enter NIC" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Date of Birth<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="dob2" class="form-control col-md-7 col-xs-12" disabled data-validate-words="1" name="dob2"required="required" type="text">
                            </div>
                        </div>
                        <input type="hidden" name="dob" id="dob" value=""/>
                        <input type="hidden" name="gender" id="gender" value=""/>

                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Gender</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="gender2" id="gender2"  required="required"  disabled>
                                    <option selected="true" disabled="true" >Select Gender</option>
                                    <option value="1">Male</option>
                                    <option value="0">Female</option>
                                </select>                            
                            </div>
                        </div>

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Contact No<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="contact" class="form-control col-md-7 col-xs-12" required="required" name="contact" placeholder="Enter Contact No" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">In an Emergency<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="emergency" class="form-control col-md-7 col-xs-12" required="required" name="emergency" placeholder="Enter Emergency Contact" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Weight (Kg)<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="weight" class="form-control col-md-7 col-xs-12" required="required" name="weight" placeholder="Enter Weight" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Height<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="height" class="form-control col-md-7 col-xs-12" required="required" name="height" placeholder="Enter Height" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Civil Status</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="civil" id="civil" required="required">
                                    <option selected="true" disabled="true" >Select Civil Status</option>
                                    <option value="1">Married</option>
                                    <option value="0">Single</option>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Address<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="expense" class="form-control col-md-7 col-xs-12" name="address" placeholder="Enter Address" required="required" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Department</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="department" onchange="loadDesignation()" id="departmentname"  required="required" >
                                    <option selected="true" disabled="true">Select Department</option>
                                    <%for (Department depart : department) {%>
                                    <option value="<%=depart.getId().toString()%>"><%= depart.getName()%></option>
                                    <%
                                        }
                                    %>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Designation</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="designation" id="designation"  required="required" >
                                    <option selected="true" disabled="true" >Select Designation</option>
                                    <%for (Designation desig : designation) {%>
                                    <option value="<%=desig.getId().toString()%>"><%= desig.getName()%></option>
                                    <%
                                        }
                                    %>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Basic Salary<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="expense" class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="basicsalary" placeholder="Enter the Basic Salary" required="required" type="number">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Date of appoint<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="appoint" class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="appoint" placeholder="Enter the appoint date" required="required" type="date">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Employee Type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="employeetype" id="employeetype"  required="required" >
                                    <option selected="true" disabled="true" >Select Employee Type</option>
                                    <%for (EmployeeType emp : employeetype) {%>
                                    <option value="<%=emp.getId()%>"><%=emp.getType()%></option>
                                    <%
                                        }
                                    %>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Educational/ Professional<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <textarea id="educational" name="educational" class="form-control col-md-7 col-xs-12"></textarea>
                            </div>
                        </div>       
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Qualification<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <textarea id="educational" name="qualification" class="form-control col-md-7 col-xs-12"></textarea>
                            </div>
                        </div>  
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Working Experience<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <textarea id="educational" name="experience" class="form-control col-md-7 col-xs-12"></textarea>
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
