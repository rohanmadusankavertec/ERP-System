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
<script src="app/js/notAlert.js"></script>


<%
    String type = "";
    if (request.getAttribute("type") != null) {
        type = request.getAttribute("type").toString();
    }
    String mrn = "";
    if (request.getAttribute("mrn") != null) {
        mrn = request.getAttribute("mrn").toString();
    }
%>

<script type="text/javascript">
    //call to LoadMRN method on load
    LoadMRN();
    var item_arr = {};
    //create ajax object to process
    function AjaxObject() {
        var xmlHttp;

        if (window.XMLHttpRequest) {
            xmlHttp = new XMLHttpRequest();
        } else
        {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        return xmlHttp;

    }
    
    //delete item from table
    $(document).on('click', '#grnItemTable span', function () {
        var r = confirm("Are you Sure You want to delete this?");
        if (r === true) {
            var tr = $(this).closest('tr');
            var id = tr.find('td:nth-child(1)').text();
            var tot = tr.find('td:nth-child(6)').text();
            for (var i = arrpid.length - 1; i >= 0; i--) {
                if (arrpid[i] === id) {
                    arrpid.splice(i, 1);
                    arrprice.splice(i, 1);
                    arrqty.splice(i, 1);v 
                    arrpm.splice(i, 1);
                    UpdateQty(id);
                }
            }
            var potot = document.getElementById("gintotal");
            potot.innerHTML = parseFloat(potot.innerHTML) - tot;
            $(this).closest('tr').remove();
        } else {
        }
    });


    //Update Qty by id
    function UpdateQty(id) {
        var qty = document.getElementById("qty" + id).value;
        var pricear = document.getElementById("pm" + id).value;
        var price=pricear.split("~~");
        document.getElementById("tot" + id).innerHTML = parseFloat(qty) * parseFloat(price[1]);
        var tot = 0;
        for (var i in arrpid) {
            var id1 = parseInt(id);
            var id2 = parseInt(arrpid[i]);
            if (id1 === id2) {
                arrqty[i] = qty;
                arrpm[i] = price[0];
                arrprice[i] = price[1];
                alert("Ids "+id1+" and "+id2+" pm id "+arrpm[i]+" Price "+arrprice[i]);
            }
            tot += parseFloat(arrprice[i] * arrqty[i]);
        }
        document.getElementById("gintotal").innerHTML = tot;
    }
    
    
    var arrpid = [];
    var arrqty = [];
    var arrprice = [];
    var arrpm = [];
    var poid;
    
    //Load MRN to table
    function LoadMRN() {
//        var type =type;
        var type = 2;
        poid =<%=mrn%>;

        var gintot = 0;
        if (type === 2) {
            var xmlHttp = AjaxObject();
            xmlHttp.onreadystatechange = function ()
            {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                {
                    var msg = xmlHttp.responseText;

                    var reply = JSON.parse(msg);
                    var arrLn1 = reply.data;

                    for (var f = 0; f < arrLn1.length; f++) {

                        arrpid.push(arrLn1[f].pid);
                        arrqty.push(arrLn1[f].qty);
                        
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
                        var textpm = document.createElement("select");
                        textpm.type = "text";
                        textqty.setAttribute('onchange', 'UpdateQty(' + arrLn1[f].pid + ')');
                        
                        textpm.id = "pm" + arrLn1[f].pid;
                        textpm.className ="form-control";

                        var pmdata = arrLn1[f].pmarr;

                        var unitprice=0;

                        for (var r = 0; r < pmdata.length; r++) {
                            var selecttag = document.createElement("option");
                            selecttag.type = "text";
                            selecttag.value = pmdata[r].pmid+"~~"+pmdata[r].sp;
                            selecttag.innerHTML = pmdata[r].sp;
                            selecttag.id = pmdata[r].pmid;
                            
                            textpm.appendChild(selecttag);
                            
                            if(unitprice===0){
                                unitprice= pmdata[r].sp;
                                arrpm[f]= pmdata[r].pmid;
                                arrprice[f]= pmdata[r].sp;
                                gintot+=unitprice*arrLn1[f].qty;
                            }
                        }
                        var col5 = document.createElement("td");
                        col5.type = "text";
                        col5.id = "tot" + arrLn1[f].pid;;
                        var itmtot=parseFloat(arrLn1[f].qty)*parseFloat(unitprice);
                        col5.value = itmtot;
                        col5.innerHTML = itmtot;

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
                        col4.appendChild(textpm);
                        row.appendChild(col5);
                        row.appendChild(col7);
                        document.getElementById('grnItemBody').appendChild(row);
                    }
                    
                    document.getElementById('gintotal').innerHTML=gintot;
                    
                }
            };
            xmlHttp.open("POST", "GIN?action=LoadMRN&poid=" + poid, true);
            xmlHttp.send();
        }
    }

//Submit GIN
    function submitGIN() {
        var bool = true;
        item_details = {};
        for (var i in arrpid) {
            var proid = arrpid[i];
            var qty = document.getElementById("qty" + arrpid[i]).value;
            alert(arrpm);
            var sprice =  arrpm;
            if (proid === "") {
                alert("Something Went Wronge....");
                bool = false;
                break;
            } else if (qty === "") {
                alert("Please Check All Qty Fields.....");
                bool = false;
                break;
            }else {
                var item_detail = {};
                item_detail["productId"] = proid;
                item_detail["qty"] = qty;
                item_detail["sprice"] = sprice;
                item_details[arrpid[i]] = item_detail;
            }
        }

        if (bool) {
            var data = {};
            var gintotal = document.getElementById('gintotal').innerHTML;
            data["mrnId"] = poid;
            data["gintotal"] = gintotal;
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
                                        nom_Success("Goods Issue Note Successfully Added");
                                        setTimeout("location.href = 'GIN?action=createGIN'", 1500);
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
                GIN (Goods Issue Note)
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
                                                <th>Price</th>
                                                <th>Total (LKR)</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody id="grnItemBody">
                                        </tbody>
                                    </table>
                                </div>
                            </div>       
                            <div class="row" style="padding-top: 20px;">
                                <label style="float: right;" class="control-label col-md-3 col-sm-3 col-xs-3" for="name"> Total Amount (LKR) <span id="gintotal">0000.00</span></label>
                            </div>   
                        </div>
                        <div class="row no-print">
                            <div class="col-xs-12">
                                <button class="btn btn-success pull-right" onclick="submitGIN();"><i class="fa fa-credit-card"></i> Submit GRN</button>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>












</div>




<%@include file="../../template/footer.jsp"%>