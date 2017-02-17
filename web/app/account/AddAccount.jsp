<%-- 
    Document   : AddAccount
    Created on : Nov 17, 2016, 9:38:08 AM
    Author     : Java-Dev-Ruchira
--%>

<%@page import="com.vertec.hibe.model.Account"%>
<%@page import="com.vertec.hibe.model.Type"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

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
    //Check validation and save Account
    function SaveAccount() {
        var acc = document.getElementById("accountType").value;
        var sType = document.getElementById("subType").value;
        var bal = document.getElementById("balance").value;
        var aName = document.getElementById("accountName").value;
        if (acc === "") {
            sm_warning("Please Select Tyoe of Account......");
        } else if (sType === "") {
            sm_warning("Please fill Sub Type......");
        } else if (aName === "") {
            sm_warning("Please fill Account Name......");
        } else if (bal === "") {
            sm_warning("Please Select Balance......");
        }else {
            var xmlHttp = getAjaxObject();
            xmlHttp.onreadystatechange = function ()
            {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                {
                    var reply = xmlHttp.responseText;
                    if (reply === "Success") {
                        nom_Success("Successfully Saved");
                        setTimeout("location.href = 'Account?action=loadOfType';", 1500);
                    } else {
                        sm_warning("Account is Not Added, Please Try again");
                    }
                }
            };
            xmlHttp.open("POST", "Account?action=saveAccount&accountName=" + aName + "&balance=" + bal + "&subType=" + sType , true);
            xmlHttp.send();
        }
    }
    
    
    //Load Sub type to select element
    function loadSubType(){
        var type = document.getElementById("accountType").value;
        $("subType").empty();
        $.ajax({
            type: "POST",
            url: "Account?action=loadofSubType&typeId="+type,
            success: function (msg) {
                var reply = eval('('+ msg +')');
                var arrLn1 = reply.subType;
                var sType = document.getElementById("subType");
                var ihtml = "";
                ihtml +="<option selected='true' value=''>Select Sub type</option>";
                for(var i=0 ;i<arrLn1.length;i++){
                    ihtml +="<option value='"+arrLn1[i].id+"'>"+arrLn1[i].name+"</option>";
                }
                 sType.innerHTML=ihtml;       
            }
        })
    }
</script>
<%
    List<Type> typeList =(List<Type>) request.getAttribute("type"); 
    List<Account> accList =(List<Account>) request.getAttribute("aList"); 
%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Add New Account
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
                        <span class="section">Account Info</span>
                        
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Select Account type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="accountType" id="accountType" onchange="loadSubType()"  required="required" >
                                    <option selected="true" disabled="true" >Select Account type</option>
                                    <% for (Type t : typeList) {%>
                                    <option value="<%= t.getId() %>"><%= t.getName() %></option>
                                    <%}%>
                                </select>
                            </div>
                        </div>
                        
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Select Sub type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="subType" id="subType"  required="required" >
                                    
                                    
                                </select>
                            </div>
                        </div>        

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name"> Account Name <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="accountName" class="form-control col-md-7 col-xs-12" data-validate-length-range="6" data-validate-words="2" name="accountName" placeholder="Enter the account Name" required="required" type="text">
                            </div>
                        </div> 
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Opening Balance</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="balance" type="number" name="balance" data-validate-linked="password" class="form-control col-md-7 col-xs-12" placeholder="Enter the Opening Balance" required="required">
                            </div>
                        </div>
                        <div class="ln_solid"></div>        
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <!--                                <button type="submit" class="btn btn-primary">Cancel</button>-->
                                <button id="send" type="button" onclick="SaveAccount()" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                        
                       


<!--                        <div class="ln_solid"></div>-->
                        
                    </form>
                                
                                   <div class="x_content">
                            <div class="table-responsive">
                                <table id="example" class="table table-striped responsive-utilities jambo_table">
                                <thead>
                                <tr class="headings">

                                    <th>Type Name </th>
                                    <th>Sub Type Name </th>
                                    <th>Account Name </th>
                                    <th>Balance </th>
                                    
                                    <th class=" no-link last"><span class="nobr">Action</span>
                                    <th class=" no-link last"><span class="nobr">Action</span>
                                    </th>
                                </tr>
                                </thead>

                                <tbody>
                                    
                                    <% for(Account a : accList ){%>
                                        <tr>
                                        <td><%=a.getSubtypeId().getTypeId().getName() %></td>
                                        <td><%=a.getSubtypeId().getName() %></td>
                                        <td><%=a.getName() %></td>
                                        <td><%=a.getBalance() %></td>
                                        <td >
                                            <form name="form1" method="post" action="Account?action=ViewToUpdate"><input type="hidden" name="accountId" value="<%=a.getId() %>"/>
                                            <button  type="submit" class="glyphicon glyphicon-edit">
                                            </button>
                                        </form>
                                        </td>
                                        <td>
                                            <form name="form1" method="post" action="Account?action=DeleteAccont"><input type="hidden" name="accountId" value="<%=a.getId()%>"/>
                                            <button id="send" type="submit" class="btn btn-danger">Delete</button>
                                            </form>
                                        </td>
                                        </tr>
                                    <%}%>  
                                    
                                </tbody>

                            </table>
                            </div>
                        </div>
                                    
                                    
                                       <div class="container">
                        <!--<a id="modal_trigger" href="#modal" class="btn btn-success pull-right" style="width: 200px;">Add New Feature</a>-->

                        <div id="modal" class="popupContainer" style="display:none;">
                            <header class="popupHeader">
                                <span class="header_title">Add new Feature</span>
                                <span class="modal_close"><i class="fa fa-times"></i></span>
                            </header>
                            <section class="popupBody">
                                <!-- Register Form -->
                                <div class="user_register col-md-12 col-sm-12 col-xs-12">
                                    <form action="Quotation?action=SaveFeature" method="post">
                                        <div class="item form-group">
                                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Enter Feature Name<span class="required"></span>
                                            </label>
                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                <input type="text" id="newfeature" name="newfeature" required="required" class="form-control col-md-7 col-xs-12">
                                            </div>
                                        </div>   
                                        <br/>
                                        <br/>
                                        <div class="action_btns" style="padding-top: 30px;">
                                            <div class="one_half last col-md-offset-3"><button type="button" onclick="AddFeature()" class="btn btn-success pull-right"><i class="fa fa-briefcase"></i>Save Feature</button></div>
                                        </div>
                                    </form>
                                </div>
                            </section>
                        </div>
                    </div> 
                                    

                </div>
            </div>
        </div>
    </div>
</div>












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

<%@include file="../../template/footer.jsp"%>

<script type="text/javascript">

    $("#modal_trigger").leanModal({top: 200, overlay: 0.6, closeButton: ".modal_close"});

    $(function() {


        // Calling Register Form
        $("#modal_trigger").click(function() {
            $(".user_register").show();
            $(".header_title").text('Add New Feature');
            return false;
        });



    });
</script>