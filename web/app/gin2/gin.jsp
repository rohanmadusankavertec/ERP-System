<%-- 
    Document   : po
    Created on : Aug 22, 2016, 3:57:00 PM
    Author     : vertec-r
--%>

<%@page import="com.vertec.hibe.model.Branch"%>
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
    var item_details = {};


    function AddToGIN() {
        var pro = document.getElementById("getpro").value;
        var qty = document.getElementById("qty").value;
        var pprice = document.getElementById("pprice").value;
        var sprice = document.getElementById("sprice").value;
        var pgintot = document.getElementById("pgintotal");
        var sgintot = document.getElementById("sgintotal");
        var product = pro.split("_");
        if (pro === "Select Product") {
            alert("Please select a product");
        } else if (qty === "") {
            alert("Please Enter the Qty");
        } else if (pprice === "") {
            alert("Please Enter the Purchased Price");
        } else if (sprice === "") {
            alert("Please Enter the Selling Price");
        } else if (parseFloat(product[2]) < parseFloat(qty)) {
            alert("Available qty is Greater than entered qty....");
        } else {
            var item_detail = {};


            var bool = true;

            for (var key in item_details)
            {

                var itdt = item_details[key];
                for (var key1 in itdt)
                {
                    if (key1.indexOf("productId") !== -1) {
                        if (product[6].indexOf(itdt[key1]) !== -1) {
                            bool = false;
                            sm_warning("This Item is already exist in Goods Issue Note....");
                        }
                    }
                }
            }

            if (bool) {

                item_detail["productId"] = product[6];
                item_detail["qty"] = qty;
                item_detail["pprice"] = pprice;
                item_detail["sprice"] = sprice;
                var ptot = parseFloat(qty) * parseFloat(pprice);
                var stot = parseFloat(qty) * parseFloat(sprice);
                item_detail["ptotal"] = ptot;
                item_detail["stotal"] = stot;
                item_detail["wid"] = product[5];
                item_details[pro] = item_detail;


                var invoiceItemTable = document.getElementById('poItemTable').getElementsByTagName('tbody')[0];
                var row = document.createElement("tr");
                row.id = product[0];
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
                var col4 = document.createElement("td");
                col4.type = "text";
                col4.value = pprice;
                col4.innerHTML = pprice;
                var col5 = document.createElement("td");
                col5.type = "text";
                col5.value = sprice;
                col5.innerHTML = sprice;
                var col6 = document.createElement("td");
                col6.type = "text";
                col6.value = ptot;
                col6.innerHTML = ptot;
                var col7 = document.createElement("td");
                col7.type = "text";
                col7.value = stot;
                col7.innerHTML = stot;
                var col8 = document.createElement("td");
                var elem1 = document.createElement("span");
                elem1.id = product[0];
                elem1.name = product[0];
                elem1.type = "button";
                elem1.className = "btn btn-default glyphicon glyphicon-remove text-center";
                col8.appendChild(elem1);
                row.appendChild(col1);
                row.appendChild(col2);
                row.appendChild(col3);
                row.appendChild(col4);
                row.appendChild(col5);
                row.appendChild(col6);
                row.appendChild(col7);
                row.appendChild(col8);
                invoiceItemTable.appendChild(row);

                pgintot.innerHTML = parseFloat(pgintot.innerHTML) + ptot;
                sgintot.innerHTML = parseFloat(sgintot.innerHTML) + stot;

                clearFields();
            }
        }
    }
    $(document).on('click', '#poItemTable span', function() {

        var r = confirm("Are you Sure You want to delete this?");
        if (r === true) {
            var tr = $(this).closest('tr');
            var tot1 = tr.find('td:nth-child(6)').text();
            var tot2 = tr.find('td:nth-child(7)').text();

            var ptot = document.getElementById("pgintotal");
            var stot = document.getElementById("sgintotal");
            ptot.innerHTML = parseFloat(ptot.innerHTML) - tot1;
            stot.innerHTML = parseFloat(stot.innerHTML) - tot2;


            $(this).closest('tr').remove();

            delete item_details[this.id];

        }
    });
    function clearFields() {
        document.getElementById("getpro").value = "Select Product";
        document.getElementById("qty").value = "";
        document.getElementById("sprice").value = "";
        document.getElementById("pprice").value = "";
    }
    function FillData() {
        var product = document.getElementById("getpro").value;
        var data = product.split("_");
        document.getElementById("sprice").value = data[4];
        document.getElementById("pprice").value = data[3];
    }



    function submitGIN() {
        var branch = document.getElementById('branchId').value;
        var data = {};
        data["branch"] = branch;
        data["item_details"] = item_details;
        var jsonDetails = JSON.stringify(data);


        BootstrapDialog.show({
            message: 'Do you want to Submit ?',
            closable: false,
            buttons: [{
                    label: 'Yes',
                    action: function(dialogRef) {
                        dialogRef.close();

                        var xmlHttp = getAjaxObject();
                        xmlHttp.onreadystatechange = function()
                        {
                            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                            {
                                var reply = xmlHttp.responseText;

                                if (reply === "Success") {
                                    nom_Success("Goods Issue Note Successfully Added");
                                    setTimeout("location.href = '/sumaga/app/gin/CreateGIN.jsp';", 1500);
                                } else {
                                    sm_warning("Goods Issue Note Not Correctly Entered Please Try Again");
                                }
                            }
                        };
                        xmlHttp.open("POST", "GIN?action=SubmitGIN&data=" + jsonDetails, true);
                        xmlHttp.send();
                    }
                }, {
                    label: 'No',
                    action: function(dialogRef) {
                        dialogRef.close();
                    }
                }]
        });
    }

    function loadProducts() {
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                document.getElementById("getpro").innerHTML = "";
                
                var MainElement = document.getElementById("getpro");
                
                var op = document.createElement("option");
                op.innerHTML ="Select Product";
                op.disabled="true";
                op.selected="true";
                 MainElement.appendChild(op);
                 
                 
                 
                 
                var reply = xmlHttp.responseText;
                if (reply !== "") {
                    
                    var product = reply.split(";;;;;");
                    for (var i = 0; i < product.length; i++) {
                        var pd = product[i].split(":::::");
                        var option = document.createElement("option");
                        option.value = pd[1] + "_" + pd[2] + "_" + pd[3] + "_" + pd[4] + "_" + pd[5] + "_" + pd[6] + "_" + pd[0];
                        option.innerHTML = pd[1] + "_" + pd[2] + "_Available Qty:" + pd[3] + "_P.P.:" + pd[4] + "_S.P.:" + pd[5];
                        MainElement.appendChild(option);
                    }
                }
            }
        };
        var cid = document.getElementById("getcat").value;
        xmlHttp.open("POST", "GIN?action=ProductFromCategory&cid=" + cid, true);
        xmlHttp.send();
    }




</script>





<% Branch branch = (Branch) request.getAttribute("branch");
    String bAddress = "";
    if (branch.getAddress() != null) {
        bAddress = branch.getAddress().replace(",", "<br/>");
    }

%>


<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                GIN (Goods Issue Note)
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
                                <h4> Branch Details</h4>
                                <input type="hidden" value="<%= branch.getBranchId().toString()%>" id="branchId"/>
                                <label class="control-label col-md-12 col-sm-12 col-xs-12" for="name"> <%= branch.getBranchName()%></label>
                                <br>
                                <label class="control-label col-md-12 col-sm-12 col-xs-12" for="name"> <%= bAddress%></label>
                                <br>
                                <br>
                                <label class="control-label col-md-12 col-sm-12 col-xs-12" for="name"> <%= new Date().toString()%></label>

                            </div>


                            <br>
                            <br>
                            <br>
                            <div class="item form-group">
                                <label class="control-label col-md-5 col-sm-3 col-xs-12">Select Product Category</label>
                                <div class="col-md-7 col-sm-6 col-xs-12">

                                    <select class="form-control" name="category" id="getcat"  required="required" onchange="loadProducts()">
                                        <option selected="true" >Select Product Category</option>
                                        <%
                                            SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                            Session ses = sf.openSession();
                                            String sql = "SELECT product_category_id,product_category_name FROM product_category ";

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

                            <div class="item form-group">
                                <label class="control-label col-md-5 col-sm-3 col-xs-12">Select Product</label>
                                <div class="col-md-7 col-sm-6 col-xs-12">

                                    <select class="form-control" name="product" id="getpro"  required="required" onchange="FillData()">
                                        <option selected="true" >Select Product</option>
                                        <%                                            String hql = "SELECT p.product_id,p.product_code,p.product_name,w.qty,pm.purchased_price,pm.selling_price,w.warehouse_stock_id FROM warehouse_stock w INNER JOIN product_master pm ON pm.product_master_id=w.product_master_product_master_id INNER JOIN product p ON pm.product_id=p.product_id INNER JOIN sys_user su ON su.sysuser_id=w.updated_by where w.qty>0 ORDER BY p.product_code ASC";

                                            SQLQuery query = ses.createSQLQuery(hql);

                                            List<Object[]> inList = query.list();

                                            for (Object[] list : inList) {%>

                                        <option value="<%= list[1].toString()%>_<%= list[2].toString()%>_<%= list[3].toString()%>_<%= list[4].toString()%>_<%= list[5].toString()%>_<%= list[6].toString()%>_<%= list[0].toString()%>"><%= list[1].toString()%>_<%= list[2].toString()%>_Available Qty:<%= list[3].toString()%>_P.P.:<%= list[4].toString()%>_S.P.:<%= list[5].toString()%></option>


                                        <%
                                            }

                                        %>


                                    </select>                            
                                </div>
                            </div>
                            <div class="item form-group">
                                <label class="control-label col-md-5">Qty</label>
                                <div class="col-md-7 col-sm-6 col-xs-12">
                                    <input id="qty" type="qty" name="qty"  class="form-control col-md-7 col-xs-12" placeholder="Enter Quantity" required="required">
                                </div>
                            </div>
                            <div class="item form-group">
                                <label class="control-label col-md-5">Purchased Price</label>
                                <div class="col-md-7 col-sm-6 col-xs-12">
                                    <input id="pprice" type="text" name="pprice"  class="form-control col-md-7 col-xs-12" placeholder="Enter Purchasing Price" required="required">
                                </div>
                            </div>
                            <div class="item form-group">
                                <label class="control-label col-md-5">Selling Price</label>
                                <div class="col-md-7 col-sm-6 col-xs-12">
                                    <input id="sprice" type="text" name="sprice"  class="form-control col-md-7 col-xs-12" placeholder="Enter Selling Price" required="required">
                                </div>
                            </div>
                            <div class="col-md-12 col-md-offset-6">
                                <button id="send" onclick="AddToGIN()" class="btn btn-success">Add Item</button>
                            </div>






                            <div class="row" style="padding-top: 20px;">
                                <div class="col-xs-12 table">
                                    <table class="table table-striped" id="poItemTable">
                                        <thead>
                                            <tr>
                                                <th>Product ID</th>
                                                <th>Product</th>
                                                <th>Qty</th>
                                                <th>Purchased Price</th>
                                                <th>Selling Price</th>
                                                <th>Purchasing Total</th>
                                                <th>Selling Total</th>
                                            </tr>
                                        </thead>
                                        <tbody id="poItemBody">


                                        </tbody>
                                    </table>
                                </div>
                            </div>       



                            <div class="row" style="padding-top: 20px;">
                                <label style="float: right;" class="control-label col-md-3 col-sm-3 col-xs-3" for="name"> Total Selling Amount (LKR) <span id="sgintotal">0000.00</span></label>
                                <label style="float: right;" class="control-label col-md-3 col-sm-3 col-xs-3" for="name"> Total Purchasing Amount (LKR) <span id="pgintotal">0000.00</span></label>


                            </div>   





                        </div>




                        <div class="row no-print">
                            <div class="col-xs-12">
                                <button class="btn btn-success pull-right" onclick="submitGIN();"><i class="fa fa-credit-card"></i> Submit GIN</button>
                            </div>
                        </div>
                    </section>

                </div>
            </div>
        </div>
    </div>












</div>




<%@include file="../../template/footer.jsp"%>