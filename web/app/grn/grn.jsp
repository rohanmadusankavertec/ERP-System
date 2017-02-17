<%-- 
    Document   : po
    Created on : Aug 22, 2016, 3:57:00 PM
    Author     : vertec-r
--%>

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
<script src="../js/grn.js" type="text/javascript"></script>
<script src="app/js/notAlert.js"></script>


<% Supplier su = (Supplier) request.getAttribute("supId");
    String sAddress = "";
    if (su.getAddress() != null) {
        sAddress = su.getAddress().replace(",", "<br/>");
    }

    String type = "";
    if (request.getAttribute("type") != null) {
        type = request.getAttribute("type").toString();

    }
    String poid = "";
    if (request.getAttribute("po") != null) {
        poid = request.getAttribute("po").toString();
    }
%>

<script type="text/javascript">
    //call loadPO method on load
    LoadPO();
    var item_arr = {};
    
    //create ajax object to communicate with ajax
    function AjaxObject() {
        var xmlHttp;

        if (window.XMLHttpRequest) {
            xmlHttp = new XMLHttpRequest();
        }
        else
        {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        return xmlHttp;

    }
//    delete Row from table
    $(document).on('click', '#grnItemTable span', function() {

        var r = confirm("Are you Sure You want to delete this?");
        if (r === true) {
            var tr = $(this).closest('tr');
            var id = tr.find('td:nth-child(1)').text();
            var tot = tr.find('td:nth-child(6)').text();
            for (var i = arrpid.length - 1; i >= 0; i--) {
                if (arrpid[i] === id) {
                    arrpid.splice(i, 1);
                    arrprice.splice(i, 1);
                    arrqty.splice(i, 1);
                }
            }
            var potot = document.getElementById("grntotal");
            potot.innerHTML = parseFloat(potot.innerHTML) - tot;
            $(this).closest('tr').remove();
        } else {
        }
    });

    // Update qty using id
    function UpdateQty(id) {
        var qty = document.getElementById("qty" + id).value;
        var price = document.getElementById("price" + id).value;
        document.getElementById("itemprice" + id).innerHTML = parseFloat(qty) * parseFloat(price);
        var tot = 0;
        for (var i in arrpid) {
            var id1 = parseInt(id);
            var id2 = parseInt(arrpid[i]);
            if (id1 === id2) {
                arrqty[i] = qty;
                arrprice[i] = price;
            }
            tot += parseFloat(arrprice[i] * arrqty[i]);
        }
        document.getElementById("grntotal").innerHTML = tot;
    }
    var arrpid = [];
    var arrqty = [];
    var arrprice = [];
    var poid;
    
    //load selected po's items
    function LoadPO() {
        var type =<%=type%>;
        poid =<%=poid%>;
        var grntot = 0;
        if (type === 2) {
            var xmlHttp = AjaxObject();
            xmlHttp.onreadystatechange = function()
            {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                {
                    var msg = xmlHttp.responseText;

                    var reply = JSON.parse(msg);
                    var arrLn1 = reply.data;

                    for (var f = 0; f < arrLn1.length; f++) {

                        arrpid.push(arrLn1[f].pid);
                        arrqty.push(arrLn1[f].qty);
                        arrprice.push(arrLn1[f].price);
                        var row = document.createElement("tr");
                        row.id = arrLn1[f].pid;
                        var col1 = document.createElement("td");
                        col1.type = "text";
                        col1.value = arrLn1[f].pid;
                        col1.innerHTML = arrLn1[f].pid;
                        var col2 = document.createElement("td");
                        col2.type = "text";
                        col2.value = arrLn1[f].pcode + " " + arrLn1[f].pname;
                        col2.innerHTML = arrLn1[f].pcode + " " + arrLn1[f].pname;
                        var col3 = document.createElement("td");
                        var textqty = document.createElement("input");
                        textqty.type = "text";
                        textqty.id = "qty" + arrLn1[f].pid;
                        textqty.setAttribute('onkeyup', 'UpdateQty(' + arrLn1[f].pid + ')');
                        textqty.value = arrLn1[f].qty;
                        var col4 = document.createElement("td");
                        var textprice = document.createElement("input");
                        textprice.id = "price" + arrLn1[f].pid;
                        textprice.setAttribute('onkeyup', 'UpdateQty(' + arrLn1[f].pid + ')');
                        textprice.type = "text";
                        textprice.value = arrLn1[f].price;
                        var col5 = document.createElement("td");
                        var textsprice = document.createElement("input");
                        textsprice.type = "text";
                        textsprice.value = "";
                        textsprice.id = "sprice" + arrLn1[f].pid;
                        var col6 = document.createElement("td");
                        col6.type = "text";
                        col6.id = "itemprice" + arrLn1[f].pid;
                        col6.value = parseFloat(arrLn1[f].price) * parseFloat(arrLn1[f].qty);
                        col6.innerHTML = parseFloat(arrLn1[f].price) * parseFloat(arrLn1[f].qty);
                        grntot += parseFloat(arrLn1[f].price) * parseFloat(arrLn1[f].qty);
                        var col7 = document.createElement("td");
                        var elem1 = document.createElement("span");
                        elem1.id = arrLn1[f].pid;
                        elem1.name = arrLn1[f].pid;
                        elem1.type = "button";
                        elem1.className = "btn btn-default glyphicon glyphicon-remove text-center";
                        col7.appendChild(elem1);
                        row.appendChild(col1);
                        row.appendChild(col2);
                        row.appendChild(col3);
                        col3.appendChild(textqty);

                        row.appendChild(col4);
                        col4.appendChild(textprice);
                        row.appendChild(col5);
                        col5.appendChild(textsprice);
                        row.appendChild(col6);
                        row.appendChild(col7);
                        document.getElementById('grnItemBody').appendChild(row);
                    }
                    document.getElementById("grntotal").innerHTML = grntot;
                }
            };
            xmlHttp.open("POST", "GRN?action=LoadPO&poid=" + poid, true);
            xmlHttp.send();
        }
    }
//Check validation and Save GRN
    function submitGRN() {
        var bool = true;
        item_details = {}
        for (var i in arrpid) {
            var proid = arrpid[i]
            var qty = document.getElementById("qty" + arrpid[i]).value;
            var price = document.getElementById("price" + arrpid[i]).value;
            var sprice = document.getElementById("sprice" + arrpid[i]).value;
            if (proid === "") {
                alert("Something Went Wronge....");
                bool = false;
                break;
            } else if (qty === "") {
                alert("Please Check All Qty Fields.....");
                bool = false;
                break;
            } else if (price === "") {
                alert("Please Check All Purchased Price Fields.....");
                bool = false;
                break;
            } else if (sprice === "") {
                alert("Please Check All Selling Price Fields.....");
                bool = false;
                break;
            } else {
                var item_detail = {}
                item_detail["productId"] = proid;
                item_detail["qty"] = qty;
                item_detail["price"] = price;
                item_detail["sprice"] = sprice;
                item_details[arrpid[i]] = item_detail;
            }
        }
        if (bool) {
            var supplierId = document.getElementById('supplierId').value;
            var data = {};
            var grntotal = document.getElementById('grntotal').innerHTML;
            data["supplierId"] = supplierId;
            data["poId"] = poid;
            data["grntotal"] = grntotal;
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
                                        nom_Success("Goods Receved Note Successfully Added");
                                        setTimeout("location.href = 'GRN?action=createGRN'", 1500);
                                    } else {
                                        sm_warning("Goods Receved Note Not Correctly Entered Please Try Again");
                                    }
                                }
                            };
                            xmlHttp.open("POST", "GRN?action=SubmitGRN&data=" + jsonDetails, true);
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
    }
</script>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                GRN (Goods Received Note)
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
                            <div class="hidden">
                                <div class="item form-group">
                                    <label class="control-label col-md-5 col-sm-3 col-xs-12">Select Product</label>
                                    <div class="col-md-7 col-sm-6 col-xs-12">
                                        <select class="form-control" name="product" id="getpro"  required="required" >
                                            <option selected="true" >Select Product</option>
                                            <%
                                                SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                                Session ses = sf.openSession();

                                                SQLQuery query = ses.createSQLQuery("SELECT product_id,product_code,product_name FROM product");

                                                List<Object[]> inList = query.list();

                                                for (Object[] list : inList) {%>
                                            <option><%= list[0].toString()%>_<%= list[1].toString()%>_<%= list[2].toString()%></option>
                                            <%
                                                }

                                            %>


                                        </select>                            
                                    </div>
                                </div>
                                <div class="item form-group">
                                    <label class="control-label col-md-5">Qty</label>
                                    <div class="col-md-7 col-sm-6 col-xs-12">
                                        <input id="qty" type="qty" name="qty"  class="form-control col-md-7 col-xs-12" placeholder="Enter Purchasing Quantity" required="required">
                                    </div>
                                </div>
                                <div class="item form-group">
                                    <label class="control-label col-md-5">Purchased Price</label>
                                    <div class="col-md-7 col-sm-6 col-xs-12">
                                        <input id="price" type="price" name="price"  class="form-control col-md-7 col-xs-12" placeholder="Enter Purchased Price" required="required">
                                    </div>
                                </div>
                                <div class="item form-group">
                                    <label class="control-label col-md-5">Selling Price</label>
                                    <div class="col-md-7 col-sm-6 col-xs-12">
                                        <input id="price" type="price" name="price"  class="form-control col-md-7 col-xs-12" placeholder="Enter Selling Price" required="required">
                                    </div>
                                </div>
                                <div class="col-md-12 col-md-offset-6">
                                    <button id="send" onclick="AddToPO()" class="btn btn-success">Add Item</button>
                                </div>
                            </div>

                            <div class="row" style="padding-top: 20px;">
                                <div class="col-xs-12 table">
                                    <table class="table table-striped" id="grnItemTable">
                                        <thead>
                                            <tr>
                                                <th>Product ID</th>
                                                <th>Product</th>
                                                <th>Qty</th>
                                                <th>Purchased Price</th>
                                                <th>Selling Price</th>
                                                <th>Total</th>
                                            </tr>
                                        </thead>
                                        <tbody id="grnItemBody">


                                        </tbody>
                                    </table>
                                </div>
                            </div>       



                            <div class="row" style="padding-top: 20px;">
                                <label style="float: right;" class="control-label col-md-3 col-sm-3 col-xs-3" for="name"> Total Amount (LKR) <span id="grntotal">0000.00</span></label>


                            </div>   





                        </div>




                        <div class="row no-print">
                            <div class="col-xs-12">
                                <button class="btn btn-success pull-right" onclick="submitGRN();"><i class="fa fa-credit-card"></i> Submit GRN</button>
                            </div>
                        </div>
                    </section>

                </div>
            </div>
        </div>
    </div>












</div>




<%@include file="../../template/footer.jsp"%>