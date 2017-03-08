<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.ProductCategory"%>
<%@page import="com.vertec.hibe.model.Tax"%>
<%@page import="com.vertec.hibe.model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/product.js"></script>
<script src="app/js/notAlert.js"></script>

<script type="text/javascript">
    var arr = [];

    function taxFields() {
        
        var tx = document.getElementById("tax").value;
        if (tx === "0") {
            document.getElementById("txfields").className = "hidden";
        } else {
            document.getElementById("txfields").className = "item form-group";
        }
    }
    
    
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
    //Check validation and save Products
    function SaveProduct() {
        
        CheckTaxes();
        console.log(arr);
        var pc = document.getElementById("productCode").value;
        var pn = document.getElementById("productName").value;
        var des = document.getElementById("description").value;
        var rl = document.getElementById("reorderLevel").value;
        var pc = document.getElementById("productCategory").value;
        var t = document.getElementById("tax").value;
        if (pc === "") {
            sm_warning("Please Select Product Code......");
        } else if (pn === "") {
            sm_warning("Please fill Product Name......");
        } else if (des === "") {
            sm_warning("Please fill Description......");
        } else if (rl === "") {
            sm_warning("Please Fill Re-Order Level......");
        } else if (pc === "") {
            sm_warning("Please Select Product Category......");
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
            xmlHttp.open("POST", "Product?action=SaveProduct&productCode=" + pc + "&productName=" + pn + "&description=" + des+ "&reorderLevel=" + rl+ "&productCategory=" + pc+ "&arr=" + arr , true);
            xmlHttp.send();
        }
    }
    
    var arr2=[];
    function CheckTaxes(){
        arr2=[];
        for(var i =0; i< arr.length;i++){
            var id="ch"+arr[i];
            alert(id);
           if(document.getElementById(id).checked){
               arr2.push(arr[i]);
           }
        }
        arr=arr2;
    }
    
    
    
    
</script>


<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Product Management
            </h3>
        </div>
    </div>
    <div class="clearfix"></div>

    <%
        List<ProductCategory> pcList = (List<ProductCategory>) request.getAttribute("pcList");
        List<Tax> taxList = (List<Tax>) request.getAttribute("taxList");
    %>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Add Product<small></small></h2>
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
                    <!--<form action="Product?action=SaveProduct" method="post" class="form-horizontal form-label-left" novalidate>-->
                        <span class="section"></span>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Product Code <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="text" id="productCode" class="form-control col-md-7 col-xs-12" placeholder="Enter Product Code" name="productCode" required="required" value="" />
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="margin-top: 10px;">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Product Name <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="text" id="productName" class="form-control col-md-7 col-xs-12" placeholder="Enter Product Name" data-validate-words="1" name="productName" required="required" value="" />
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="margin-top: 10px;">
                            <label for="Privilege" class="control-label col-md-3 col-sm-3 col-xs-12">Description</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <textarea rows="4" cols="50" class="form-control col-md-7 col-xs-12" placeholder="Enter Description" name="description" id="description"></textarea>                
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="margin-top: 10px;">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Re Order Level <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="number" id="reorderLevel" class="form-control col-md-7 col-xs-12" placeholder="Enter Re-Order Level" data-validate-words="1" name="reorderLevel" required="required" value="" />
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="margin-top: 10px;">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Product Category <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="productCategory" id="productCategory"  required="required" >
                                    <option selected="true" disabled="true">Select Product Category</option>

                                    <%for (ProductCategory pc : pcList) {%>
                                    <option value="<%=pc.getProductCategoryId()%>"><%=pc.getProductCategoryName()%></option>
                                    <%}%>
                                </select>                              </div>
                        </div>
                                <div class="clearfix"></div>
                        <div class="item form-group" style="margin-top: 10px;">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Tax <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="tax" id="tax"  required="required" onchange="taxFields()">
                                    <option value="1">Yes</option>
                                    <option selected="true" value="0">No</option>

                                </select>                              
                            </div>
                        </div>
                                <div class="clearfix"></div>
                        <div class="hidden" id="txfields" style="margin-top: 10px;">
                            <div class="control-label col-md-3 col-sm-3 col-xs-12"></div>
                            <div class="col-md-6 col-sm-6 col-xs-12" >
                                <%for (Tax t : taxList) {%>
                                <script>
                                arr.push(<%=t.getId()%>);
                                </script>
                                <input type="radio" value="ch<%=t.getId()%>"/><%=t.getName()%><br>
                                <%}%>
                            </div>
                        </div>
                            <div class="clearfix"></div>
                        <div class="ln_solid"></div>
                        <div class="form-group" style="margin-top: 10px;">
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <!--                                <button type="submit" class="btn btn-primary">Cancel</button>-->
                                <button id="send" onclick="SaveProduct()" type="button" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                    <!--</form>-->

                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <%
            List<Product> pList = (List<Product>) request.getAttribute("pList");
        %>
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Product Categories<small>up to now</small></h2>
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
                    <div class="table-responsive">
                        <table id="example" class="table table-striped responsive-utilities jambo_table">
                            <thead>
                                <tr class="headings">

                                    <th>Product ID </th>
                                    <th>Product Code </th>
                                    <th>Product Name </th>
                                    <th>Description</th>
                                    <th>Re Order Level</th>
                                    <th>Tax</th>

                                    <th class=" no-link last"><span class="nobr" style="width: 300px;">Action</span></th>

                                </tr>
                            </thead>

                            <tbody>
                                <%for (Product pi : pList) {%>
                                <tr>
                                    <td class=" "><%=pi.getProductId()%></td>
                                    <td class=" "><%=pi.getProductCode()%></td>
                                    <td class=" "><%=pi.getProductName()%></td>
                                    <td class=" "><%=pi.getProductDescription()%></td>
                                    <td class=" "><%=pi.getReOrderLevel()%></td>
                                    <%if (pi.getTaxId() == null) {%>
                                    <td class=" ">No Tax</td>
                                    <%} else {%>
                                    <td class=" "><%=pi.getTaxId().getName()%></td>
                                    <%}%>

                                    <td class=" last">

                                        <form action="Product?action=ViewProduct" method="POST">
                                            <input type="hidden" name="pcId" value="<%=pi.getProductId()%>"/>
                                            <button type="submit" class="glyphicon glyphicon-edit">
                                            </button>
                                        </form>
                                        <a href="#" id="deleteUser" onclick="deleteProduct(<%=pi.getProductId()%>);" class="glyphicon glyphicon-remove"></a>

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