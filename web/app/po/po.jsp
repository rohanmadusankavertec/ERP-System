<%-- 
    Document   : po
    Created on : Aug 22, 2016, 3:57:00 PM
    Author     : vertec-r
--%>

<%@page import="com.vertec.hibe.model.Company"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Session"%>
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.vertec.hibe.model.Supplier"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/po.js"></script>
<script src="app/js/notAlert.js"></script>

<script type="text/javascript">
    // load product to select element 
    function loadProducts() {
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                document.getElementById("getpro").innerHTML = "";
                var MainElement = document.getElementById("getpro");
                var op = document.createElement("option");
                op.innerHTML = "Select Product";
                op.disabled = "true";
                op.selected = "true";
                MainElement.appendChild(op);

                var reply = xmlHttp.responseText;
                if (reply !== "") {
                    var product = reply.split(";;;;;");
                    for (var i = 0; i < product.length; i++) {
                        var pd = product[i].split(":::::");
                        var option = document.createElement("option");
                        option.innerHTML = pd[0] + "_" + pd[1] + "_" + pd[2];
                        MainElement.appendChild(option);
                    }
                }
            }
        };
        var cid = document.getElementById("getcat").value;
        xmlHttp.open("POST", "PRN?action=ProductFromCategory&cid=" + cid, true);
        xmlHttp.send();
    }
</script>


<% Supplier su = (Supplier) request.getAttribute("supId");
    String sAddress = "";
    if (su.getAddress() != null) {
        sAddress = su.getAddress().replace(",", "<br/>");
    }

%>


<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                PO (Purchasing Order)
                <small>
                </small>
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

                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <section class="content invoice">
                        <!-- title row -->
                        <div class="row">
                            <div class="item form-group">
                                <h4> Supplier Details</h4>
                                <input type="hidden" value="<%= su.getSupplierId().toString()%>" id="supplierId"/>
                                <label class="control-label col-md-12 col-sm-12 col-xs-12" for="name"> <%= su.getCompanyName()%></label>
                                <br>
                                <label class="control-label col-md-12 col-sm-12 col-xs-12" for="name"> <%= sAddress%></label>
                                <br>
                                <label class="control-label col-md-12 col-sm-12 col-xs-12" for="name"> <%
                                    if (su.getType() == false) {
                                    %>
                                    (LOCAL Supplier)
                                    <%
                                    } else {
                                    %>
                                    (FOREIGN Supplier)
                                    <%
                                        }
                                    %></label>
                                <br>
                                <label class="control-label col-md-12 col-sm-12 col-xs-12" for="name"> <%= new Date().toString()%></label>

                            </div>
                            <br>
                            <br>
                            <br>
                            <br>
                            <br>
                            <div class="item form-group">
                                <label class="control-label col-md-5 col-sm-3 col-xs-12">Select Product Category</label>
                                <div class="col-md-7 col-sm-6 col-xs-12">

                                    <select class="form-control" name="category" id="getcat"  required="required" onchange="loadProducts()">
                                        <option selected="true" >Select Product Category</option>
                                        <%
                                            HttpSession httpSession = request.getSession();
                                            Company company = (Company) httpSession.getAttribute("company");
                                            SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                            Session ses = sf.openSession();
                                            String sql = "SELECT product_category_id,product_category_name FROM product_category WHERE company_id='" + company.getId() + "'";
                                            SQLQuery querysql = ses.createSQLQuery(sql);
                                            List<Object[]> inListsql = querysql.list();
                                            for (Object[] list : inListsql) {%>

                                        <option value="<%= list[0].toString()%>"><%= list[1].toString()%></option>
                                        <%
                                            }
                                        %>


                                    </select>                            
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="item form-group" style="margin-top: 10px;">
                                <label class="control-label col-md-5 col-sm-3 col-xs-12">Select Product</label>
                                <div class="col-md-7 col-sm-6 col-xs-12">

                                    <select class="form-control" name="product" id="getpro"  required="required" >
                                        <option selected="true" >Select Product</option>
                                        <%                                            SQLQuery query = ses.createSQLQuery("SELECT product_id,product_code,product_name FROM product WHERE company_id='" + company.getId() + "'");
                                            List<Object[]> inList = query.list();
                                            for (Object[] list : inList) {%>
                                        <option><%= list[0].toString()%>_<%= list[1].toString()%>_<%= list[2].toString()%></option>
                                        <%
                                            }
                                        %>
                                    </select>                            
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="item form-group" style="margin-top: 10px;">
                                <label class="control-label col-md-5">Qty</label>
                                <div class="col-md-7 col-sm-6 col-xs-12">
                                    <input id="qty" type="qty" name="qty"  class="form-control col-md-7 col-xs-12" placeholder="Enter Purchasing Quantity" required="required">
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="item form-group" style="margin-top: 10px;">
                                <label class="control-label col-md-5">Unit Price</label>
                                <div class="col-md-7 col-sm-6 col-xs-12">
                                    <input id="price" type="price" name="price"  class="form-control col-md-7 col-xs-12" placeholder="Enter Purchasing Price" required="required">
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-12 col-md-offset-6" style="margin-top: 10px; margin-left: 80%;">
                                <button id="send" onclick="AddToPO()" class="btn btn-success">Add Item</button>
                            </div>






                            <div class="row" style="padding-top: 20px;">
                                <div class="col-xs-12 table">
                                    <table class="table table-striped" id="poItemTable">
                                        <thead>
                                            <tr>
                                                <th>Product ID</th>
                                                <th>Product</th>
                                                <th>Qty</th>
                                                <th>Unit Price</th>
                                                <th>Total</th>
                                                <th>Delete</th>
                                            </tr>
                                        </thead>
                                        <tbody id="poItemBody">


                                        </tbody>
                                    </table>
                                </div>
                            </div>       



                            <div class="row" style="padding-top: 20px;">
                                <label style="float: right;" class="control-label col-md-3 col-sm-3 col-xs-3" for="name"> Total Amount (LKR) <span id="pototal">0000.00</span></label>


                            </div>   





                        </div>

                        <div class="row no-print">
                            <div class="col-xs-12">
                                <button class="btn btn-success pull-right" onclick="submitPO();"><i class="fa fa-credit-card"></i> Submit Purchasing Order</button>
                            </div>
                        </div>
                    </section>

                </div>
            </div>
        </div>
    </div>












</div>




<%@include file="../../template/footer.jsp"%>