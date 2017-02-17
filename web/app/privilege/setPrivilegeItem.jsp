<%-- 
    Document   : tableEdit
    Created on : Mar 21, 2016, 2:48:15 PM
    Author     : User
--%>
<%@page import="com.vertec.hibe.model.UserGroup"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<!--<script src="app/js/privilege.js"></script>-->
<script src="app/js/notAlert.js"></script>

<script>
    //call to viewUserDetails2
    $(document).on('click', '#viewDetials_tbl2 tbody tr', function () {
        document.getElementById('permissiondiv').className = 'x_panel tile';

        viewUserDetails2($(this).find('td:first').text().trim());
    });
    //load privilege items according to user group
    function viewUserDetails2(prgid) {
        $("#usermanage").empty();
        $("#privilegemanage").empty();
        $("#customermanage").empty();
        $("#branchmanage").empty();
        $("#invoicemanage").empty();
        $("#suppliermanage").empty();
        $("#productmanage").empty();
        $("#prnmanage").empty();
        $("#pomanage").empty();
        $("#grnmanage").empty();
        $("#stockmanage").empty();
        $("#gtnmanage").empty();
        $("#paymentmanage").empty();
        $("#inventoryreportmanage").empty();
        $("#returnitemsmanage").empty();
        $("#employeemanage").empty();
        $("#attendancemanage").empty();
        $("#bankdetailsmanage").empty();
        $("#allowancedeductionmanage").empty();
        $("#staffloanmanage").empty();
        $("#salarymanage").empty();
        $("#payrollreportmanage").empty();
        $("#accountmanage").empty();
        $("#transactionmanage").empty();
        $("#accountreportmanage").empty();

        $("#upbtn").empty();

        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                var reply = eval('(' + xmlHttp.responseText + ')');
                console.log(reply);
                var arrLn1 = reply.jArr1.length;

                var permission = document.getElementById('allPutty');

                var usermanage = document.getElementById('usermanage');
                var privilegemanage = document.getElementById('privilegemanage');
                var customermanage = document.getElementById('customermanage');
                var branchmanage = document.getElementById('branchmanage');
                var invoicemanage = document.getElementById('invoicemanage');
                var suppliermanage = document.getElementById('suppliermanage');
                var productmanage = document.getElementById('productmanage');
                var prnmanage = document.getElementById('prnmanage');
                var pomanage = document.getElementById('pomanage');
                var grnmanage = document.getElementById('grnmanage');
                var stockmanage = document.getElementById('stockmanage');
                var gtnmanage = document.getElementById('gtnmanage');
                var paymentmanage = document.getElementById('paymentmanage');
                var inventoryreportmanage = document.getElementById('inventoryreportmanage');
                var returnitemsmanage = document.getElementById('returnitemsmanage');

                var employeemanage = document.getElementById('employeemanage');
                var attendancemanage = document.getElementById('attendancemanage');
                var bankdetailsmanage = document.getElementById('bankdetailsmanage');
                var allowancedeductionmanage = document.getElementById('allowancedeductionmanage');
                var staffloanmanage = document.getElementById('staffloanmanage');
                var salarymanage = document.getElementById('salarymanage');
                var payrollreportmanage = document.getElementById('payrollreportmanage');
                var accountmanage = document.getElementById('accountmanage');
                var transactionmanage = document.getElementById('transactionmanage');
                var accountreportmanage = document.getElementById('accountreportmanage');

                if (arrLn1 !== 0) {
                    for (var f = 0; arrLn1 > f; f++) {
                        var prid = reply.jArr1[f].pid;
                        var pritId = reply.jArr1[f].piid;
                        var piname = reply.jArr1[f].piname;
                        var pname = reply.jArr1[f].pname;
                        var status = reply.jArr1[f].status;
                        var row = document.createElement("div");
                        row.className = "form-group";
                        var td1 = document.createElement("label");
                        td1.className = "control-label";
                        td1.innerHTML = piname;
                        td1.for = pritId;

                        var td2 = document.createElement("input");
                        td2.type = "checkbox";
                        td2.name = "permission";
                        td2.className = "pull-right";
                        if (status === "NO") {
                            td2.value = pritId;
                            td2.checked = false;
                        } else if (status === "YES") {
                            td2.value = pritId;
                            td2.checked = true;
                        }
                        if (prid === "1") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            usermanage.appendChild(row);
                        } else if (prid === "2") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            privilegemanage.appendChild(row);
                        } else if (prid === "3") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            branchmanage.appendChild(row);
                        } else if (prid === "4") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            customermanage.appendChild(row);
                        } else if (prid === "5") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            invoicemanage.appendChild(row);
                        } else if (prid === "6") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            suppliermanage.appendChild(row);
                        } else if (prid === "7") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            productmanage.appendChild(row);
                        } else if (prid === "8") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            prnmanage.appendChild(row);
                        } else if (prid === "9") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            pomanage.appendChild(row);
                        } else if (prid === "10") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            grnmanage.appendChild(row);
                        } else if (prid === "11") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            stockmanage.appendChild(row);
                        } else if (prid === "12") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            gtnmanage.appendChild(row);
                        } else if (prid === "13") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            paymentmanage.appendChild(row);
                        } else if (prid === "14") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            inventoryreportmanage.appendChild(row);
                        } else if (prid === "15") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            returnitemsmanage.appendChild(row);
                        } else if (prid === "16") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            employeemanage.appendChild(row);
                        } else if (prid === "17") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            attendancemanage.appendChild(row);
                        } else if (prid === "18") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            bankdetailsmanage.appendChild(row);
                        } else if (prid === "19") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            allowancedeductionmanage.appendChild(row);
                        } else if (prid === "20") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            staffloanmanage.appendChild(row);
                        } else if (prid === "21") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            salarymanage.appendChild(row);
                        } else if (prid === "22") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            payrollreportmanage.appendChild(row);
                        } else if (prid === "23") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            accountmanage.appendChild(row);
                        } else if (prid === "24") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            transactionmanage.appendChild(row);
                        } else if (prid === "25") {
                            row.appendChild(td1);
                            row.appendChild(td2);
                            accountreportmanage.appendChild(row);
                        }
                    }
                }
                if (arrLn1 !== 0) {
                    var row2 = document.getElementById('upbtn');
                    var hidden = document.createElement("input");
                    hidden.type = "hidden";
                    hidden.value = prgid;
                    hidden.name = "prgid";
                    hidden.id = "prgid";
                    var elem1 = document.createElement("span");
                    elem1.id = "updateBtn";
                    elem1.name = "updateBtn";
                    elem1.type = "button";
                    elem1.className = "btn btn-success glyphicon glyphicon-edit col-md-offset-5 col-lg-offset-5";

                    row2.appendChild(hidden);
                    row2.appendChild(elem1);
                    permission.appendChild(row2);
                }
            }
        };
        xmlHttp.open("POST", "Privilege?action=SetPrivilegeItem&groupId=" + prgid, true);
        xmlHttp.send();
    }
    //update user group privilege
    $(document).on('click', '#allPutty span', function () {

        var r = confirm("Are you Sure You want to Update this?");
        if (r === true) {
            var prgid = document.getElementById("prgid").value;
            var checkboxes = document.getElementsByName('permission');

            var checkboxesChecked = [];
            checkboxesChecked.push(prgid);
            for (var i = 0; i < checkboxes.length; i++) {
                // And stick the checked ones onto an array...
                if (checkboxes[i].checked) {
                    checkboxesChecked.push(checkboxes[i].value);
                }
            }
            var xmlHttp = getAjaxObject();
            xmlHttp.onreadystatechange = function ()
            {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                {
                    var reply = xmlHttp.responseText;
                    if (reply === "Success") {
                        nom_Success("Successfully Updated");
                        setTimeout("location.href = 'Privilege?action=LoadUserGroupsForPI';", 1500);
//                    window.location = "Privilege?action=LoadUserGroupsForPI";
                    } else {
                        sm_warning("Privilege is Not Updated, Please Try again");
                    }

                }
            };
            xmlHttp.open("POST", "Privilege?action=UpdatePriviledgeItem&dataArr=" + checkboxesChecked, true);
            xmlHttp.send();

        } else {

        }

    });

</script>


<%if (ca.checkUserAuth("SET_PRIVILEGE_ITEM", group) != null) {%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Privileges Item Details
                <small>

                </small>
            </h3>
        </div>

        <div class="title_right">
            <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">

            </div>
        </div>
    </div>
    <div class="clearfix"></div>

    <br/>


    <div class="clearfix"></div>
    <%List<UserGroup> ugList = (List<UserGroup>) request.getAttribute("ugList");%>
    <div class="row">

        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">

                <div class="x_content">

                    <div class="col-lg-6">
                        <div class="x_panel tile">
                            <div class="x_title">
                                <h2>User Groups</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <table border="0" class="table table-hover table-responsive" id="viewDetials_tbl2">
                                    <%for (UserGroup ug : ugList) {%>
                                    <tr style="cursor: pointer;">
                                        <td><%=ug.getUserGroupId()%></td>
                                        <td><%=ug.getUserGroupName()%></td>
                                    </tr>
                                    <%}%>

                                </table>

                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

    </div>
    <div class="row">
        <div class="col-lg-12">



            <div class="hidden" id="permissiondiv">
                <div class="x_title">
                    <h2>Privileges</h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>

                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content" id="allPutty">
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>User Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="usermanage">

                            </div>
                        </div>

                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Privilege Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="privilegemanage">

                            </div>
                        </div>

                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Branch Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content" id="branchmanage">

                            </div>
                        </div>

                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Customer Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="customermanage">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Invoice Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="invoicemanage">

                            </div>
                        </div>
                    </div>








                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Supplier Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="suppliermanage">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Product Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="productmanage">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>PRN Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="prnmanage">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>PO Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="pomanage">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>GRN Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="grnmanage">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Stock Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="stockmanage">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>GTN Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="gtnmanage">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Payment Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="paymentmanage">

                            </div>
                        </div>
                    </div>






                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Inventory Report Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>
                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content" id="inventoryreportmanage">
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Return Management</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>
                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content" id="returnitemsmanage">
                            </div>
                        </div>
                    </div>
































                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Employee Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="employeemanage">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Attendance Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="attendancemanage">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Bank Details Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="bankdetailsmanage">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Allowance & Deduction Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="allowancedeductionmanage">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Staff Loan Management</h2>

                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="staffloanmanage">

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Salary Management</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>

                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content" id="salarymanage">
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 600px;">
                            <div class="x_title">
                                <h2>Payroll Report Management</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>
                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content" id="payrollreportmanage">
                            </div>
                        </div>
                    </div>







                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 300px;">
                            <div class="x_title">
                                <h2>Account Management</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>
                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content" id="accountmanage">
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 600px;">
                            <div class="x_title">
                                <h2>Transaction Management</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>
                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content" id="transactionmanage">
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="x_panel tile" style="height: 400px;">
                            <div class="x_title">
                                <h2>Account Report Management</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>
                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content" id="accountreportmanage">
                            </div>
                        </div>
                    </div>






                </div>
                <div class="row" id="upbtn"></div>
            </div>
        </div>
    </div>
</div>
<%} else {%>
<script type="text/javascript">
    window.location = 'error403.jsp';
</script>
<%}%>
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




<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
