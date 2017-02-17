<%@page import="com.vertec.hibe.model.Product"%>
<%@page import="com.vertec.hibe.model.Branch"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/stock.js"></script>
<script src="app/js/notAlert.js"></script>


<%if (ca.checkUserAuth("MANAGE_VEHICLE_STOCK", group) != null) {%>


<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Stock Management
            </h3>
        </div>


    </div>
    <div class="clearfix"></div>
    <%
        List<Branch> bList = (List<Branch>) request.getAttribute("bList");
    %>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Stock Management<small>Vehicle wise</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>

                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="item form-group">
                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Branch <span class="required"></span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="branchId" id="branchId"  required="required" onchange="loadVehiclePM();">
                                <option selected="true" disabled="true" >Select Branch</option>

                                <%if (ca.checkUserAuth("OTHER_BRANCH", group) != null) {%>
                                    <%for (Branch  b : bList) {%>
                                    <option value="<%=b.getBranchId()%>"><%=b.getBranchName()%></option>
                                    <%}
                                    }else{
                                    %>
                                        <option value="<%= user.getBranchBranchId().getBranchId() %>"><%= user.getBranchBranchId().getBranchName() %></option>
                                     <%   
                                    }
                                    
                                    %>
                            </select>                              
                        </div>
                    </div>
                    <div class="hidden" id="vehiDiv" style="padding-top: 30px;">
                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Vehicle <span class="required"></span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="vehicleId" id="vehicleId"  required="required" onchange="">
                                <option selected="true" disabled="true" >Select Vehicle</option>


                            </select>                              
                        </div>
                    </div>
                    <div class="hidden" id="selPro" style="padding-top: 30px;">
                        <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Product <span class="required"></span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="productId" id="productId"  required="required" onchange="">
                                <option selected="true" disabled="true" >Select Product</option>


                            </select>                              
                        </div>
                    </div>
                    <div class="hidden" id="btnHide" style="padding-top: 30px;">
                        <div class="col-xs-12">
                            <%if (ca.checkUserAuth("UPDATE_VEHICLE_STOCK", group) != null) {%>
                            <button class="btn btn-warning  pull-right" onclick="openUpdateWindow();"><i class="fa fa-edit"></i>  Update Vehicle Stock </button><%}%>
                            <%if (ca.checkUserAuth("ADD_VEHICLE_STOCK", group) != null) {%>
                            <button class="btn btn-success pull-right" onclick="openAddWindow();"><i class="fa fa-save"></i>  Add New Vehicle Stock </button><%}%>
                        </div>

                    </div>
                    <%if (ca.checkUserAuth("ADD_VEHICLE_STOCK", group) != null) {%>

                    <div id="addDiv" class="hidden">

                        <div class="x_panel tile">
                            <div class="x_title">
                                <h2></h2>


                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content" id="branchmanage">
                                <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Price Master<span class="required"></span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <select class="form-control" name="bpmId" id="bpmId"  required="required" onchange="">
                                        <option selected="true" disabled="true" >Select Product Master</option>


                                    </select>                              
                                </div>
                                <br/>
                                <br/>
                                <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Quantity<span class="required"></span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input id="addQuantity" class="form-control col-md-6 col-xs-12"  name="addQuantity"  required="required" type="text">


                                </div>
                                <br/>
                                <br/>
                                <br/>
                                <div class="col-xs-12">
                                    <button class="btn btn-success col-lg-offset-4" onclick="saveVStock();"><i class="fa fa-save"></i> Save</button>
                                </div>
                            </div>
                        </div>



                    </div>
                    <%}%>

                    <%if (ca.checkUserAuth("UPDATE_VEHICLE_STOCK", group) != null) {%>
                    <div id="updateDiv" class="hidden">
                        <div class="row" style="padding-top: 100px;">
                            <div class="col-xs-12 table">
                                <div class="table-responsive">
                                    <table class="table table-striped" id="updateVSTable">
                                        <thead>
                                            <tr>
                                                <th>VS ID</th>
                                                <th>BPM ID</th>
                                                <th>Purchased Price</th>
                                                <th>Selling Price</th>
                                                <th>Branch Available Quantity</th>
                                                <th>Loaded Quantity</th>
                                                <th>action</th>
                                            </tr>
                                        </thead>
                                        <tbody id="updateVSBody">


                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- /.col -->
                        </div>


                    </div>


                    <div class="hidden" id="updateVSPM">
                        <div class="row">
                            <div class="item form-group" id="prosel">
                                <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name"><span class="required"></span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type="hidden" id="vsId" name="vsId" required="required" class="form-control col-md-7 col-xs-12">
                                    <input type="hidden" id="bpmIdNew" name="bpmIdNew" required="required" class="form-control col-md-7 col-xs-12">
                                </div>
                            </div><br/><br/>

                            <div class="item form-group">
                                <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Purchased Price<span class="required"></span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type="text" id="pPrice" name="pPrice" required="required" class="form-control col-md-7 col-xs-12" readonly>
                                </div>
                            </div><br/><br/>
                            <div class="item form-group">
                                <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Selling Price<span class="required"></span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type="text" id="sPrice" name="sPrice" required="required" class="form-control col-md-7 col-xs-12" readonly>
                                </div>
                            </div><br/><br/>
                            <div class="item form-group">
                                <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Available Quantity<span class="required"></span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type="hidden" id="aquantity" name="aquantity" required="required" class="form-control col-md-7 col-xs-12">
                                    <input type="text" id="cquantity" name="cquantity" required="required" class="form-control col-md-7 col-xs-12" readonly>
                                </div>
                            </div><br/><br/>
                            <div class="item form-group">
                                <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Loading Quantity<span class="required"></span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type="text" id="lquantity" name="lquantity" required="required" class="form-control col-md-7 col-xs-12">
                                </div>
                            </div>

                        </div><br/>
                        <div class="row no-print">
                            <div class="col-xs-12 col-lg-offset-2">
                                <button class="btn btn-group-crop" onclick="updateAllVS();" id="updateP"><i class="fa fa-edit"></i>  Update </button>
                            </div>
                            <button class="btn btn-warning  pull-right" onclick="clearVS();"><i class="fa fa-close"></i>  Clear </button>
                        </div>
                    </div>
                    <%}%>




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

