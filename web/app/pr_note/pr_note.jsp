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
<script src="app/js/notAlert.js"></script>

<script type="text/javascript">
    //load products to select element
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
    var item_details = {};

    //add product to table
    function AddToPRN() {
        var pro = document.getElementById("getpro").value;
        var qty = document.getElementById("qty").value;
        if (pro === "") {
            alert("Please select a product");
        } else if (qty === "") {
            alert("Please Enter the Qty");
        } else {
            var product = pro.split("_");
            var item_detail = {};
            var bool = true;
            for (var key in item_details)
            {
                var itdt = item_details[key];
                for (var key1 in itdt)
                {
                    if (key1.indexOf("productId") !== -1) {
                        if (product[0].indexOf(itdt[key1]) !== -1) {
                            bool = false;
                            sm_warning("This Item is already exist in Purchasing order....");
                        }
                    }
                }
            }
            if (bool) {
                item_detail["productId"] = product[0];
                item_detail["qty"] = qty;
                item_details[pro] = item_detail;

                var invoiceItemTable = document.getElementById('poItemTable').getElementsByTagName('tbody')[0];
                var row = document.createElement("tr");
                row.id = pro;
                var col1 = document.createElement("td");
                col1.type = "text";
                col1.value = product[0];
                col1.innerHTML = product[0];
                var col2 = document.createElement("td");
                col2.type = "text";
                col2.value = product[1] + " " + product[2];
                col2.innerHTML = product[1] + " " + product[2];
                var col3 = document.createElement("td");
                col3.type = "text";
                col3.value = qty;
                col3.innerHTML = qty;
                var col7 = document.createElement("td");
                var elem1 = document.createElement("span");
                elem1.id = pro;
                elem1.name = pro;
                elem1.type = "button";
                elem1.className = "btn btn-default glyphicon glyphicon-remove text-center";
                col7.appendChild(elem1);
                row.appendChild(col1);
                row.appendChild(col2);
                row.appendChild(col3);
                row.appendChild(col7);
                invoiceItemTable.appendChild(row);
                clearFields();
            }
        }
    }
    //clear fileds
    function clearFields() {
        document.getElementById("getpro").value = "";
        document.getElementById("qty").value = "";
    }

    //delete item from table
    $(document).on('click', '#poItemTable span', function () {
        var r = confirm("Are you Sure You want to delete this ?");
        if (r === true) {
            var tr = $(this).closest('tr');
            $(this).closest('tr').remove();
            delete item_details[this.id];
        } else {

        }
    });
    // check whether the array is empty
    function isEmpty(obj) {
        for (var i in obj) {
            return false;
        }
        return true;
    }

    //save PRN
    function submitPRN() {
        if (isEmpty(item_details)) {
            alert("Please add products..");
        } else {
            var data = {};
            data["item_details"] = item_details;
            var jsonDetails = JSON.stringify(data);
            BootstrapDialog.show({
                message: 'Do you want to Submit ?',
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
                                    if (reply === "Success") {
                                        nom_Success("PRN Successfully Added");
                                        setTimeout("location.href = 'PRN?action=toPRN';", 1500);
                                    } else {
                                        sm_warning("PRN Not Correctly Entered Please Try Again");
                                    }
                                }
                            };
                            xmlHttp.open("POST", "PRN?action=SubmitPRN&data=" + jsonDetails, true);
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
    }
</script>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                PRN (Purchase Requisition Note)
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
                                        <option selected="true" value="">Select Product Category</option>
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
                                        <option selected="true"  value="">Select Product</option>
                                        <%                                            SQLQuery query = ses.createSQLQuery("SELECT product_id,product_code,product_name FROM product WHERE company_id='"+ company.getId()+"'");
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
                            <div class="col-md-12 col-md-offset-6" style="margin-top: 10px; margin-left: 80%;">
                                <button id="send" onclick="AddToPRN()" class="btn btn-success">Add Item</button>
                            </div>

                            <div class="row" style="padding-top: 20px;">
                                <div class="col-xs-12 table">
                                    <table class="table table-striped" id="poItemTable">
                                        <thead>
                                            <tr>
                                                <th>Product ID</th>
                                                <th>Product</th>
                                                <th>Qty</th>
                                                <th>Delete</th>
                                            </tr>
                                        </thead>
                                        <tbody id="poItemBody">


                                        </tbody>
                                    </table>
                                </div>
                            </div>       
                        </div>
                        <div class="row no-print">
                            <div class="col-xs-12">
                                <button class="btn btn-success pull-right" onclick="submitPRN();"><i class="fa fa-credit-card"></i> Submit Purchase Requisition Note</button>
                            </div>
                        </div>
                    </section>

                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="../../template/footer.jsp"%>