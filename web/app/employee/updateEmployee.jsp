<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.EmployeeType"%>
<%@page import="com.vertec.hibe.model.Department"%>
<%@page import="com.vertec.hibe.model.Designation"%>
<%@page import="com.vertec.hibe.model.Employee"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="js/customer.js"></script>
<script src="js/notAlert.js"></script>
<%
    Employee e = (Employee) request.getAttribute("employee");
    List<Designation> designation = (List<Designation>) request.getAttribute("designations");
    List<Department> department = (List<Department>) request.getAttribute("departments");
    List<EmployeeType> empt = (List<EmployeeType>) request.getAttribute("empt");

    String gen = "Male";
    if (!e.getGender()) {
        gen = "Female";
    }


%>
<script type="text/javascript">
    //Load employee details after 1 second
    setTimeout("document.getElementById('departmentname').value='<%=e.getDesignationId().getDepartmentId().getId()%>';loadDesignation();", 1000);
    //Load employee details
    function loadDesignation() {
        $("#designation").empty();
        var desi = document.getElementById('designation');
        var t1 = document.createElement("option");
        t1.selected = true;
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
                document.getElementById('designation').value = '<%=e.getDesignationId().getId()%>';
                var cvl = "0";
                if (<%=e.getCivilStatus()%>) {
                    cvl = "1";
                }
                document.getElementById('designation').value = '<%=e.getDesignationId().getId()%>';
                document.getElementById('employeetype').value = '<%=e.getEmployeeTypeId().getId() %>';
                document.getElementById('civil').value = cvl;
            }
        });
    }
</script>

<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>


<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Update Employee Details
            </h3>
        </div>
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

                    <form action="Employee?action=ChangeEmployee" method="post" class="form-horizontal form-label-left" validate >

                        <span class="section">Employee Update</span>
                        <input type="hidden" value="<%= e.getId()%>" name="eid"/>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">First Name<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12" value="<%= e.getFname()%>" data-validate-words="1" name="firstname" placeholder="Enter Firt Name" required="required" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Last Name<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12"  value="<%=e.getLname()%>" data-validate-words="1" name="lastname" placeholder="Enter Last Name" required="required" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">NIC<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12"  value="<%=e.getNic()%>" data-validate-words="1" id="nic" name="nic" onkeyup="getNIC()" placeholder="Enter NIC" required="required" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Date of Birth<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="dob" class="form-control col-md-7 col-xs-12" value="<%=e.getDob()%>" data-validate-words="1" name="dob"required="required">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Gender</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control"  value="<%=gen%>" name="gender" id="gender"  required="required" >
                                    <option disabled="true" >Select Gender</option>
                                    <option value="1">Male</option>
                                    <option value="0">Female</option>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Contact No<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="contact" class="form-control col-md-7 col-xs-12" value="<%=e.getContactNo()%>" required="required" name="contact" placeholder="Enter Contact No" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">In an Emergency<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="emergency" class="form-control col-md-7 col-xs-12" required="required" value="<%=e.getEmergency()%>" name="emergency" placeholder="Enter Emergency Contact" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Weight (Kg)<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="weight" class="form-control col-md-7 col-xs-12" required="required" value="<%=e.getWeight()%>" name="weight" placeholder="Enter Weight" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Height<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="height" class="form-control col-md-7 col-xs-12" required="required" value="<%=e.getHeight()%>" name="height" placeholder="Enter Height" type="text">
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
                                <input id="expense" class="form-control col-md-7 col-xs-12"  value="<%= e.getAddress()%>" data-validate-words="1" name="address" placeholder="Enter Address" required="required" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Department</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select onchange="loadDesignation()" class="form-control" value="<%=e.getDesignationId().getDepartmentId().getId()%>" name="department" id="departmentname"  required="required" >
                                    <option disabled="true" value="">Select Department</option>
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
                                <select class="form-control"  value="<%=e.getDesignationId().getId()%>" name="designation" id="designation"  required="required" >
                                    <option disabled="true">Select Designation</option>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Basic Salary<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="expense" class="form-control col-md-7 col-xs-12"  value="<%=e.getBasicSalary()%>" data-validate-words="1" name="basicsalary" placeholder="Enter the Basic Salary" required="required" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Date of appoint<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="appoint" class="form-control col-md-7 col-xs-12" value="<%=e.getAppoint()%>" data-validate-words="1" name="appoint" placeholder="Enter the appoint date" required="required" type="date">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Employee Type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="employeetype" id="employeetype"  required="required" >
                                    <option selected="true" disabled="true" >Select Employee Type</option>
                                    <%for (EmployeeType emp : empt) {%>
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
                                <textarea id="educational" name="educational" class="form-control col-md-7 col-xs-12"><%=e.getEducational()%></textarea>
                            </div>
                        </div>       
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Qualification<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <textarea id="educational" name="qualification" class="form-control col-md-7 col-xs-12"><%=e.getQualification()%></textarea>
                            </div>
                        </div>  
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Working Experience<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <textarea id="educational" name="experience" class="form-control col-md-7 col-xs-12"><%=e.getExperience()%></textarea>
                            </div>
                        </div> 
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">FP ID<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="expense" class="form-control col-md-7 col-xs-12"  value="<%=e.getFpid()%>" data-validate-words="1" name="fpid" placeholder="Enter the Finger Print ID" type="text">
                            </div>
                        </div>

                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
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
