<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.SupplierHasGroup"%>
<%@page import="com.vertec.hibe.model.Supplier"%>
<%@page import="com.vertec.hibe.model.CustomerHasGroup"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page import="com.vertec.hibe.model.CustomerGroup"%>
<%@page import="com.vertec.hibe.model.SupplierGroup"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>



<script>
    
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
    
    var pkgf = [];
    //add supplier details to table
    function addSupplierDetails() {
        var featureid = document.getElementById("supplierId").value;
        if(featureid!=="Select Supplier...."){
        var fea = featureid.split("~");
        var tbl = document.getElementById("employeeItemBody");
        var data = tbl.innerHTML;
        var bool = true;
        for (var i = 0; pkgf.length > i; i++) {
            if (pkgf[i] === fea[0]) {
                bool = false;
                sm_warning("This Feature is already exist.");
            }
        }
        if (bool) {
            pkgf.push(fea[0]);
            var data = data + "<tr id='" + fea[0] + "'><td>" + fea[1] + "</td><td>" + fea[2] + "</td><td>" + fea[3] + "</td><td>" + fea[4] + "</td><td><a href='#' id='deleteUser' onclick='DeleteSupplier(" + fea[0] + ")' class='glyphicon glyphicon-remove'></a></td></tr>";
            tbl.innerHTML = data;
        }
        }else{
            sm_warning("Select an Employee...");
        }
    }
    // Add supplier to a group
    function sendData(){
    var gid = document.getElementById('groupId').value;
        if(pkgf.length>0){
            $.ajax({
                    type: "POST",
                    url: "Supplier?action=addSupplierToGroup&dataArr=" + pkgf+"&groupId="+gid,
                    success: function (msg) {
                            // alert(msg);
                            if (msg === "Success") {
                            nom_Success("Successfully Added");
                            setTimeout("location.href = 'Supplier?action=LoadmanageGroup';", 1000);

                            } else {
                            sm_warning("Not Added, Please Try again");
                            }
                    }
            });
        }else{
            sm_warning("Please Add Supplier...");
        }
    }
    //Delete Supplier from array and table using id
    function DeleteSupplier(id) {
        var elem = document.getElementById(id);
        elem.parentElement.removeChild(elem);
        for (var i = 0; pkgf.length > i; i++) {
            if (pkgf[i].indexOf(id) > -1) {
                //pkgf.shift(pkgf[i]);
                pkgf.splice(i, 1);
            }
        }
    }
        
        
        
    
</script>


<%if (ca.checkUserAuth("ADD_CUSTOMER", group) != null) {%>

<%
    List<Supplier> slist = (List<Supplier>)request.getAttribute("sList");
    List<SupplierHasGroup> shList = (List<SupplierHasGroup>)request.getAttribute("shList");
    String gid = (String)request.getAttribute("gid");
//        CustomerGroup sg = (CustomerGroup) request.getAttribute("cg");
%>

<div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>
                    Adding Supplier into Group
                </h3>
            </div>
    
    
        </div>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small>view Supplier Group</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>

                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <form action="#" method="post" class="form-horizontal form-label-left" novalidate>

                        </p>
                        <span class="section">Supplier Group Registration</span>

                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Supplier <span class="required"></span>
                            </label>
                            <input type="hidden" name="groupId" id="groupId" value="<%=gid%>"/>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="supplierId" id="supplierId"  required="required" onchange="addSupplierDetails()">
                                    <option selected="true" disabled value="">Select Supplier....</option>

                                    <%for (Supplier c : slist) {%>
                                    <option value="<%=c.getSupplierId()+"~"+c.getSupplierName()+"~"+c.getCompanyName()+"~"+c.getAddress()+"~"+c.getEmail()%>"><%=c.getSupplierName()%></option>
                                    <%}%>

                                </select>

                            </div>
                        </div>
                        <div class="ln_solid"></div>            
                                    
                        <div class="row" style="padding-top: 20px;">
                            <center>
                                <div class="col-xs-12 table">
                                    <table class="table table-striped" id="featureTable" style="width: 700px;">
                                        <thead>
                                            <tr>
                                                
                                                <th>Supplier Name</th>
                                                <th>Company Name </th>
                                                <th>Address</th>
                                                <th>E-mail</th>
                                                <th>Delete</th>
                                            </tr>
                                        </thead>
                                        <tbody id="employeeItemBody">


                                        </tbody>
                                    </table>
                                    
                                    
                                    
                                </div>
                            </center>
                        </div>             
                                    
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <!--                                <button type="submit" class="btn btn-primary">Cancel</button>-->
                                <button id="send" type="button" onclick="sendData()" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                                   
                                    
                        <div class="table-responsive">
                            <table id="example" class="table table-striped responsive-utilities jambo_table">
                                <thead>
                                    <tr class="headings">

                                        <th>Supplier Name</th>
                                        <th>Company Name</th>
                                        <th>Address </th>
                                        <th>E-mail</th>
                                        

                                        
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (SupplierHasGroup c : shList) {%>
                                        <tr>
                                            <td class=" "><%=c.getSupplierId().getSupplierName() %></td>
                                            <td class=" "><%=c.getSupplierId().getCompanyName()%></td>
                                            <td class=" "><%=c.getSupplierId().getAddress()%></td>
                                            <td class=" "><%=c.getSupplierId().getEmail()%></td>
                                        </tr>
                                    <%}%>
                                </tbody>

                            </table>
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
