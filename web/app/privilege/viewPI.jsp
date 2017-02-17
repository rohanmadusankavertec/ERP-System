<%-- 
    Document   : tableEdit
    Created on : Mar 21, 2016, 2:48:15 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.PrivilegeItem"%>
<%@page import="com.vertec.hibe.model.Priviledge"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/privilege.js"></script>
<script src="app/js/notAlert.js"></script>


<%if (ca.checkUserAuth("UPDATE_PRIVILEGE_ITEM", group) != null) {%>

<%PrivilegeItem privilegeItem = (PrivilegeItem) request.getAttribute("privilegeItem"); %>
<%List<Priviledge> privList = (List<Priviledge>) request.getAttribute("prevList");%>         



<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Privilege Items Details
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



    <div class="clearfix"></div>

    <div class="row">

        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Privilege Item </h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <!--                        <li class="dropdown">
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

                    <form action="" method="post" class="form-horizontal form-label-left" novalidate>



                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Privilege Item ID <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="number" id="privilegeId" class="form-control col-md-7 col-xs-12" name="privilegeId" value="<%=privilegeItem.getPrivilegeItemId()%>" readonly/>
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Privilege Item Name <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="text" id="privilegeName" class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="privilegeName" required="required" value="<%=privilegeItem.getPrivilegeItemName()%>" />
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Privilege Item CODE <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="text" id="privilegeCode" class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="privilegeCode"  required="required" value="<%=privilegeItem.getPrivilegeItemCode()%>" />
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="Privilege" class="control-label col-md-3 col-sm-3 col-xs-12">Default Status</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input  type="text" id="privilegeStatus" class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="privilegeStatus" vakue="" required="required" value="<%=privilegeItem.getPrivilegeItemDefaultStatus()%>"/>

                            </div>
                        </div>


                        <div class="item form-group">
                            <label for="Privilege" class="control-label col-md-3 col-sm-3 col-xs-12">Select Privilege Group</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="privilege" id="privilege"  required="required" >


                                    <%for (Priviledge p : privList) {%>                                                                                
                                    <option value="<%=p.getPriviledgeId()%>"><%=p.getPriviledgeName()%></option>
                                    <%}%>

                                </select>                            </div>
                        </div>
                        <!--                        <div class="item form-group">
                                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                                        <input type="button" onclick="emailValid();">
                                                    </div>
                                                </div>-->

                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <!--                                <button type="submit" class="btn btn-primary">Cancel</button>-->
                                <button id="send" type="button" class="btn btn-success" onclick="updateSelectedPI();">Submit</button>
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
