<%-- 
    Document   : tableEdit
    Created on : Mar 21, 2016, 2:48:15 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Priviledge"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/privilege.js"></script>
<script src="app/js/notAlert.js"></script>



<%if (ca.checkUserAuth("ADD_PRIVILEGE", group) != null) {%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Privileges Details
                <small>

                </small>
            </h3>
        </div>

        <div class="title_right">
            <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                <!--                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Search for...">
                                    <span class="input-group-btn">
                                        <button class="btn btn-default" type="button">Go!</button>
                                    </span>
                                </div>-->
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-lg-2 col-xs-2 col-md-2">
            <input type="hidden" name="privilegeId" class="form-control" id="privilegeId" placeholder="ID" required/>
        </div>
        <div class="col-lg-4 col-xs-4 col-md-4">
            <input type="text" name="privilege" class="form-control" id="privilege" placeholder="Enter Privilege Name" required/>
        </div>

    </div>
    <br/>
    <div class="row">
        <div class="col-lg-2 col-xs-2 col-md-2">
        </div>
        <div class="col-lg-3 col-xs-3 col-md-3">
            <input type="button" onclick="savePrivilege();" name="Add" class="btn btn-round btn-success" id="Add" value="Add Privilege"/>
        </div>
        <div class="col-lg-3 col-xs-3 col-md-3">
            <input type="button"  name="Update" onclick="updateForms();" class="btn btn-round btn-warning disabled" id="Update" value="Update Privilege"/>
        </div>
        <div class="col-lg-2 col-xs-2 col-md-2">
            <input type="button" onclick="clearForms();" name="clear" class="btn btn-round btn-default" id="clear" value="Clear"/>
        </div>
        <div class="col-lg-2 col-xs-2 col-md-2">
        </div>
    </div>
    <br/>

    <div class="clearfix"></div>

    <div class="row">
        <%List<Priviledge> privilegeList = (List<Priviledge>) request.getAttribute("prList");%>

        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <!--                    <h2>Registered users <small>up to now</small></h2>-->
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <!--                                                <li class="dropdown">
                                                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                                                            <ul class="dropdown-menu" role="menu">
                                                                                <li><a href="#">Settings 1</a>
                                                                                </li>
                                                                                <li><a href="#">Settings 2</a>
                                                                                </li>
                                                                            </ul>
                                                                        </li>-->
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="table-responsive">
                        <table id="privilegeTable" class="table table-striped responsive-utilities jambo_table">
                            <thead>
                                <tr class="headings">

                                    <th>Privilege ID </th>
                                    <th>Privilege Name </th>

                                    <th class=" no-link last"><span class="nobr">Action</span>
                                    </th>
                                </tr>
                            </thead>

                            <tbody>

                                <% for (Priviledge pr : privilegeList) {%>
                                <tr>

                                    <td class=" "><%=pr.getPriviledgeId()%></td>
                                    <td class=" "><%=pr.getPriviledgeName()%></td>


                                    <%if (ca.checkUserAuth("UPDATE_PRIVILEGE", group) != null) {%><td class=" last"> <span id="1" class="btn btn-default glyphicon glyphicon-edit"></span>  <%}%>
                                    </td>
                                </tr>
                                <%}%>
                            </tbody>

                        </table>
                    </div>
                </div>
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
