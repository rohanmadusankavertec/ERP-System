
<%@page import="com.vertec.hibe.model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/product.js"></script>
<script src="app/js/notAlert.js"></script>

<%if (ca.checkUserAuth("MANAGE_PRODUCT_MASTER", group) != null) {%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Product Master

            </h3>
        </div>

    </div>
    <div class="clearfix"></div>
    <%
        List<Product> productList = (List<Product>) request.getAttribute("productList");
    %>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Product Master<small>Add,View and Update Product Masters</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="#">Settings 1</a>
                                </li>
                                <li><a href="#">Settings 2</a>
                                </li>
                            </ul>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <section class="content invoice">
                        <!-- title row -->

                        <!-- /.row -->
                        <!-- Start Item row -->
                        <div class="row" style="padding-top: 60px;">
                            <div class="col-lg-10 col-lg-offset-1">
                                <div class="x_panel tile fixed_height_500">
                                    <div class="x_title">
                                        <h2>Product Details</h2>

                                        <ul class="nav navbar-right panel_toolbox">
                                            <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                            </li>

                                            <li><a class="close-link"><i class="fa fa-close"></i></a>
                                            </li>
                                        </ul>
                                        <div class="clearfix"></div>
                                    </div>
                                    <%if (ca.checkUserAuth("UPDATE_PRODUCT_MASTER", group) != null) {%>

                                    <div class="x_content" id="privilegemanage">
                                        <div class="item form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Product <span class="required"></span>
                                            </label>
                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                <select class="form-control" name="productId" id="productId"  required="required" onchange="loadProductDetail();">
                                                    <option selected="true" disabled="true" >Select Product</option>

                                                    <%for (Product pc : productList) {%>
                                                    <option value="<%=pc.getProductId()%>"><%=pc.getProductCode() + " " + pc.getProductName()%></option>
                                                    <%}%>
                                                </select>                              </div>
                                        </div>
                                    </div>
                                    <%}%>
                                </div>

                            </div>




                        </div>
                        <!-- Finish Item row -->
                        <!-- Table row -->
                        <div class="row" style="padding-top: 100px;">
                            <div class="col-xs-12 table">
                                <div class="table-responsive">
                                    <table class="table table-striped" id="pmtableOr">
                                        <thead>
                                            <tr>
                                                <th>PM ID</th>
                                                <th>Product</th>
                                                <th>Purchased Price</th>
                                                <th>Selling Price</th>
                                                <th>action</th>
                                            </tr>
                                        </thead>
                                        <tbody id="pmtable">


                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->

                        <div class="row">
                            <!-- accepted payments column -->
                            <div class="col-xs-6">
                            </div>
                            <!-- /.col -->
                            <div class="col-xs-6">


                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->

                        <!-- this row will not appear when printing -->
                        <%if (ca.checkUserAuth("ADD_PRODUCT_MASTER", group) != null) {%>

                        <div class="row no-print">
                            <div class="col-xs-12">
                                <!--                                <button class="btn btn-default" onclick="window.print();"><i class="fa fa-print"></i> Print</button>-->
                                <button class="btn btn-success pull-right" onclick="viewAddPM();"><i class="fa fa-credit-card"></i> Add New Product Master</button>
                            </div>
                        </div>
                        <%}%>
                        <div class="hidden" id="addPm">
                            <div class="row">
                                <div class="item form-group" id="prosel">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Product <span class="required"></span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <select class="form-control" name="productAddId" id="productAddId"  required="required">
                                            <option selected="true" disabled="true" >Select Product</option>

                                            <%for (Product pc : productList) {%>
                                            <option value="<%=pc.getProductId()%>"><%=pc.getProductCode() + " " + pc.getProductName()%></option>
                                            <%}%>
                                        </select>                              </div>
                                </div><br/><br/>
                                <div class="item form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Purchased Price<span class="required"></span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <input type="hidden" id="pmId" name="pmId" required="required" class="form-control col-md-7 col-xs-12">
                                        <input type="text" id="purchasedPrice" name="purchasedPrice" required="required" class="form-control col-md-7 col-xs-12">
                                    </div>
                                </div><br/><br/>
                                <div class="item form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Selling Price<span class="required"></span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <input type="text" id="sellingPrice" name="sellingPrice" required="required" class="form-control col-md-7 col-xs-12">
                                    </div>
                                </div>

                            </div><br/>
                            <div class="row no-print">
                                <div class="col-xs-12 col-lg-offset-2">
                                    <%if (ca.checkUserAuth("ADD_PRODUCT_MASTER", group) != null) {%>
                                    <button class="btn btn-success disabled" onclick="savePM();" id="saveP"><i class="fa fa-save"></i>  Save </button>
                                    <%}%>
                                    <%if (ca.checkUserAuth("UPDATE_PRODUCT_MASTER", group) != null) {%>
                                    <button class="btn btn-group-crop disabled" onclick="updatePM();" id="updateP"><i class="fa fa-edit"></i>  Update </button>
                                    <%}%>
                                </div>
                                <button class="btn btn-warning  pull-right" onclick="clearPM();"><i class="fa fa-close"></i>  Clear </button>
                            </div>
                        </div>
                    </section>
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
