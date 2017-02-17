<%-- 
    Document   : tableEdit
    Created on : Mar 21, 2016, 2:48:15 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Leaves"%>
<%@page import="com.vertec.hibe.model.Employee"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/notAlert.js"></script>

<script type="text/javascript">

    //Approve Leave by id
    function ApproveLeave(id) {
        var pay = "0";
        BootstrapDialog.show({
            message: 'Do you want to approve this Leave ?',
            closable: false,
            buttons: [{
                    label: 'Yes',
                    action: function (dialogRef) {
                        dialogRef.close();
                        BootstrapDialog.show({
                            message: 'Mark this as Pay Leave ?',
                            closable: false,
                            buttons: [{
                                    label: 'Yes',
                                    action: function (dialogRef2) {
                                        dialogRef2.close();
                                        pay = "1";
                                        SendApprove(id, pay);
                                    }
                                }, {
                                    label: 'No',
                                    action: function (dialogRef) {
                                        dialogRef.close();
                                        SendApprove(id, pay);
                                    }
                                }]
                        });
                    }
                }, {
                    label: 'No',
                    action: function (dialogRef) {
                        dialogRef.close();
                    }
                }]
        });
    }
    
    // Approve Leave. this method calling from approve leave
    function SendApprove(id, pay) {
        var remark2 = "";
        var remark = prompt("Please enter a Remark", "");
        if (remark !== null) {
            remark2 = remark;
        }
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                var reply = xmlHttp.responseText;
                if (reply === "Success") {
                    nom_Success("Successfully Approved");
                    setTimeout("location.href = 'Attendance?action=ViewLeave';", 1500);
                } else {
                    sm_warning("Not Updated, Please Try again");
                }
            }
        };
        xmlHttp.open("POST", "Attendance?action=ApproveLeave&lid=" + id + "&remark=" + remark2 + "&pay=" + pay, true);
        xmlHttp.send();
    }

    // this method will ignore leave
    function IgnoreLeave(id) {
        BootstrapDialog.show({
            message: 'Do you want to ignore this Leave ?',
            closable: false,
            buttons: [{
                    label: 'Yes',
                    action: function (dialogRef) {
                        dialogRef.close();


                        var xmlHttp = getAjaxObject();
                        xmlHttp.onreadystatechange = function ()
                        {
                            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                            {
                                var reply = xmlHttp.responseText;
                                console.log(reply);
                                if (reply === "Success") {
                                    nom_Success("Successfully Ignored.");
                                    setTimeout("location.href = 'Attendance?action=ViewLeave';", 1500);
                                } else {
                                    sm_warning("Not Updated, Please Try again");
                                }
                            }
                        };
                        xmlHttp.open("POST", "Attendance?action=IgnoreLeave&lid=" + id, true);
                        xmlHttp.send();


                    }
                }, {
                    label: 'No',
                    action: function (dialogRef) {
                        dialogRef.close();
                    }
                }]
        });
    }

</script>



<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Leave Details
                <small>
                    All Employees' Leave Details Here 
                </small>
            </h3>
        </div>

        <div class="title_right">
            <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">

            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <%List<Leaves> le = (List<Leaves>) request.getAttribute("leave");%>
    <div class="row">

        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>All Leaves <small>up to now</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>

                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="table-responsive">
                        <table id="example" class="table table-striped responsive-utilities jambo_table">
                            <thead>
                                <tr class="headings">
                                    <th>Employee Name </th>
                                    <th>NIC </th>
                                    <th>Leave Type</th>
                                    <th>Description</th>
                                    <th>From Date </th>
                                    <th>To Date </th>
                                    <th>Days </th>
                                    <th>Leave Status</th>
                                    <th>Payment Status</th>
                                    <th>Admin Remark</th>
                                    <th class=" no-link last"><span class="nobr">Action</span>
                                    </th>
                                </tr>
                            </thead>

                            <tbody>
                                <% for (Leaves l : le) {%>
                                <tr>
                                    <td><%= l.getEmployeeId().getFname() + " " + l.getEmployeeId().getLname()%></td>
                                    <td><%= l.getEmployeeId().getNic()%></td>
                                    <td><%= l.getType()%></td>
                                    <td><%= l.getDescription()%></td>
                                    <td><%= l.getFromdate()%></td>
                                    <td><%= l.getTodate()%></td>
                                    <td><%= l.getDays()%></td>
                                    <td><%
                                        if (l.getIsApproved() != null) {
                                            if (l.getIsApproved()) {
                                                out.write("Approved");
                                            } else {
                                                out.write("Ignored");
                                            }
                                        } else {
                                            out.write("No Action");
                                        }
                                        %></td>
                                    <td><%
                                        if (l.getIsPay() != null) {
                                            if (l.getIsPay()) {
                                                out.write("Pay");
                                            } else {
                                                out.write("No Pay");
                                            }
                                        } else {
                                            out.write("No Action");
                                        }
                                        %></td>
                                    <td><%
                                        if (l.getIsPay() != null) {
                                            out.write(l.getRemark());
                                        } else {
                                            out.write("No Remark");
                                        }

                                        %></td>
                                    <td class=" last"> 
                                        <input type="button" value="Approve" onclick="ApproveLeave(<%=l.getId()%>)"/>
                                        <input type="button" value="Ignore" onclick="IgnoreLeave(<%=l.getId()%>)"/>
                                    </td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <br />
        <br />
        <br />
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
