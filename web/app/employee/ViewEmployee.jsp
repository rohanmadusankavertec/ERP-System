<%-- 
    Document   : tableEdit
    Created on : Mar 21, 2016, 2:48:15 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Employee"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/notAlert.js"></script>

<script type="text/javascript">
    //Delete employee by id
    function deleteEmployee(employeeId) {
    BootstrapDialog.show({
        message: 'Do you want delete this Employee ?',
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
                                nom_Success("Successfully Deleted");
                                setTimeout("location.href = 'Employee?action=ViewEmployee';", 1500);
                            } else {
                                sm_warning("Employee is Not Deleted, Please Try again");
                            }
                        }
                    };
                    xmlHttp.open("POST", "Employee?action=deleteEmployee&eid=" + employeeId, true);
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
                Employee Details
                <small>
                    All Employee Personal Details Here 
                </small>
            </h3>
        </div>

        <div class="title_right">
            <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
               
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <%List<Employee> employeeList = (List<Employee>) request.getAttribute("employee");%>
    <div class="row">

        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Registered Employee <small>up to now</small></h2>
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
                                <th>DOB </th>
                                <th>Gender</th>
                                <th>Address </th>
                                <th>Department </th>
                                <th>Designation </th>
                                <th class=" no-link last"><span class="nobr">Action</span>
                                </th>
                            </tr>
                        </thead>

                        <tbody>
                            <% for (Employee e : employeeList) {%>
                            <tr>
                                <td><%= e.getFname()+" "+e.getLname()  %></td>
                                <td><%= e.getNic() %></td>
                                <td><%= e.getDob() %></td>
                                <td><%
                                if(e.getGender()){
                                out.write("Male");
                                }else{
                                out.write("Female");
                                }
                                %></td>
                                <td class=" "><%= e.getAddress() %></td>
                                <td class=" "><%= e.getDesignationId().getDepartmentId().getName() %></td>
                                <td class=" "><%= e.getDesignationId().getName() %></td>
                                <td class=" last"> 
                                    <form name="form1" method="post" action="Employee?action=UpdateEmployee"><input type="hidden" name="employeeId" value="<%=e.getId()%>"/>
                                        <button type="submit" class="glyphicon glyphicon-edit">
                                        </button>
                                    </form>
                                    <a href="#" id="deleteUser" onclick="deleteEmployee(<%=e.getId()%>);" class="glyphicon glyphicon-remove"></a>
                                    
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
