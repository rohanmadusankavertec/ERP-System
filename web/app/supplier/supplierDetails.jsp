<%-- 
    Document   : tableEdit
    Created on : Mar 21, 2016, 2:48:15 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Supplier"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/supplier.js"></script>
<script src="app/js/notAlert.js"></script>


<%if (ca.checkUserAuth("VIEW_CUSTOMERS", group) != null) {%>

<div class="">
    <!--    <div class="page-title">
            <div class="title_left">
                <h3>
                    User Details
                    <small>
                        All Users Personal Details Here 
                    </small>
                </h3>
            </div>
    
            <div class="title_right">
                <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                                    <div class="input-group">
                                        <input type="text" class="form-control" placeholder="Search for...">
                                        <span class="input-group-btn">
                                            <button class="btn btn-default" type="button">Go!</button>
                                        </span>
                                    </div>
                </div>
            </div>
        </div>-->
    <div class="clearfix"></div>
    <%List<Supplier> supplierList = (List<Supplier>) request.getAttribute("suList");%>
    <div class="row">

        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Registered Supplier <small>up to now</small></h2>
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

                                    <th>Supplier Name </th>
                                    <th>Company Name </th>
                                    <th>Address </th>
                                    <th>Fax No </th>
                                    <th>Office No</th>
                                    <th>Mobile No</th>
                                    <th>Email</th>
                                    <th>Type</th>
                                    <th class=" no-link last"><span class="nobr">Action</span>
                                    </th>
                                </tr>
                            </thead>

                            <tbody>

                                <% for (Supplier cu : supplierList) {

                                %>
                                <tr>

                                    <td class=" "><%=cu.getSupplierName()%></td>
                                    <td class=" "><%=cu.getCompanyName()%></td>
                                    <td class=" "><%=cu.getAddress()%></td>
                                    <td class=" "><%=cu.getFax()%></td>
                                    <td class=" "><%=cu.getLand()%></td>
                                    <td class=" "><%=cu.getMobile()%></td>
                                    <td class=" "><%=cu.getEmail()%></td>
                                    <td class=" ">
                                        <%
                                        
                                            if (cu.getType()==false) {
                                        %>
                                        Local

                                        <%
                                        } else {
                                        %>
                                        Foreign

                                        <%
                                            }
                                        %>
                                    </td>
                                    <td class=" last"> 
                                        <%if (ca.checkUserAuth("UPDATE_SUPPLIER", group) != null) {%>
                                        <form name="form1" method="post" action="Supplier?action=UpdateSupplier"><input type="hidden" name="supplierId" value="<%=cu.getSupplierId()%>"/>
                                            <button type="submit" class="glyphicon glyphicon-edit">

                                            </button></form>
                                        <%}%>
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
    $(document).ready(function() {
        $('input.tableflat').iCheck({
            checkboxClass: 'icheckbox_flat-green',
            radioClass: 'iradio_flat-green'
        });
    });

    var asInitVals = new Array();
    $(document).ready(function() {
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
        $("tfoot input").keyup(function() {
            /* Filter on the column based on the index of this element's parent <th> */
            oTable.fnFilter(this.value, $("tfoot th").index($(this).parent()));
        });
        $("tfoot input").each(function(i) {
            asInitVals[i] = this.value;
        });
        $("tfoot input").focus(function() {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("tfoot input").blur(function(i) {
            if (this.value == "") {
                this.className = "search_init";
                this.value = asInitVals[$("tfoot input").index(this)];
            }
        });
    });
</script>




<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
