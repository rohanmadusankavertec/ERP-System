<%-- 
    Document   : GTN
    Created on : Dec 27, 2016, 10:42:16 AM
    Author     : Java-Dev-Ruchira
--%>

<%@page import="com.vertec.hibe.model.BranchProductmaster"%>
<%@page import="com.vertec.hibe.model.Branch"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>


<script>
    
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
    
    var item_details={};
    //Load products to select element
    function LoadProduct(){
        var pcid = document.getElementById("pCategory").value;
        
        $.ajax({
            type: 'POST',
            url: "GTN?action=loadProcut&category="+pcid,
            success: function (msg) {
                var reply = eval('(' +msg+ ')');
                
                var arrLn1 = reply.product;
                
                var ihtml = "";
                
                var product = document.getElementById("product");
                ihtml+="<option selected='true' disabled='true'>Select Product</option>";
                for(var i=0; i<arrLn1.length; i++){
                    ihtml+="<option value='"+arrLn1[i].id+"~"+arrLn1[i].name+"'>"+arrLn1[i].name+" "+"~"+" "+arrLn1[i].code+"</option>";
                }
                product.innerHTML=ihtml;        
            }
        });

    }
    //load selling price to select element
    function LoadSalePrice(){
        var pid = document.getElementById("product").value;
        var pidArr = pid.split("~");
        
        $.ajax({
            type: 'POST',
            url: "GTN?action=loadSalePrice&product="+pidArr[0],
            success: function (msg) {
                var reply = eval('('+msg+')');
                var arrL1 = reply.saleprice;
                var ihtml = "";
                
                ihtml+="<option selected='true' disabled='true'>Select Selling Price..</option>";
                var price = document.getElementById("sprice");
                for(var i =0; i<arrL1.length; i++){
                    ihtml+="<option value='"+arrL1[i].id+"~"+arrL1[i].price+"'>"+arrL1[i].price+"</option>";
                    
                }
                
                price.innerHTML=ihtml;        
            }
            
        });
    }
    
    // show available products qty
    function LoadAvailableQty(){
        var pmid = document.getElementById("sprice").value;
        var pidArr = pmid.split("~");
        
        
        
        $.ajax({
            type: 'POST',
            url: "GTN?action=loadAvialableQty&sprice="+pidArr[0],
            success: function (msg) {
                var reply = eval('('+msg+')');
                var arrL1 = reply.aqty;
                
                
                
                var aqty = document.getElementById("aqty");
                
                
                aqty.innerHTML=arrL1;        
            }
            
        });
        
    }
    
    //validation before add to table
    function checkAddToGrid(){
       
        var item_detail = {};
//        var pcId = document.getElementById("pCategory").value;
        var pId = document.getElementById("product").value;
        var pmid = document.getElementById("sprice").value;
        var quantity = document.getElementById("qty").value;
        var aqty = document.getElementById("aqty").innerHTML;
        var pidArr = pId.split("~");
        var productId = pidArr[0];
        var productName = pidArr[1];
        
        
        var PriceArr = pmid.split("~");
        var sellPrice = PriceArr[1];
        var bpmid = PriceArr[0];
        
        var totalAmount = quantity*sellPrice;
        
        
        
        
        
        if(parseInt(quantity) > parseInt(aqty)){
            sm_warning("Not enough Qty.....");}
//            alert();
//        }if else(productName==="" || sellPrice === "" || quantity ==="" ){
//            sm_warning("Please Fill the fields.....");
//        }
        else{
            addAllToGrid(productId,bpmid,productName,sellPrice,quantity,totalAmount,aqty,item_detail);
            
        }
        document.getElementById("pCategory").value="Select Product Category";
        document.getElementById("product").value="Select Product";
        document.getElementById("sprice").value="Select Selling Price..";
        document.getElementById("qty").value="";
        document.getElementById("aqty").innerHTML="";
        
    }
    
    // Add products to tables
    function addAllToGrid(productId,bpmid,productName, sellPrice, quantity, totalAmount,Aqty,item_detail){
        
        var parr = productId.split("~");
        var pid = parr[0];
        var pname = parr[1];
        
        item_detail["productId"] = pid;
        item_detail["bpmid"] = bpmid;
        item_detail["productName"] = pname;
        item_detail["sellPrice"] = sellPrice;
        item_detail["quantity"] = quantity;
        item_detail["totalAmount"] = totalAmount;
        item_detail["Aqty"] = Aqty;
        
        item_details[pid]=item_detail;
        var invoiceItemTable = document.getElementById('invoiceItemTable').getElementsByTagName('tbody')[0];
        var row = document.createElement("tr");
        row.id = bpmid;
        var col1 = document.createElement("td");
        col1.type = "text";
        col1.value = pname;
        col1.innerHTML = productName;
        var col2 = document.createElement("td");
        col2.type = "text";
        col2.value = sellPrice;
        col2.innerHTML = sellPrice;
        var col3 = document.createElement("td");
        col3.type = "text";
        col3.value = quantity;
        col3.innerHTML = quantity;
        var col4 = document.createElement("td");
        col4.type = "text";
        col4.value = totalAmount;
        col4.innerHTML = totalAmount;
        var col5 = document.createElement("td");
        var elem1 = document.createElement("span");
        elem1.id = pid;
        elem1.name = pid;
        elem1.type = "button";
        elem1.className = "btn btn-default glyphicon glyphicon-remove text-center";
        col5.appendChild(elem1);
        
        row.appendChild(col1);
        row.appendChild(col2);
        row.appendChild(col3);
        row.appendChild(col4);
        row.appendChild(col5);
        invoiceItemTable.appendChild(row);
        
    }
    
    // Save GTN
    function submitGTN(){
        var data = {};

        var fm = document.getElementById('hidden1').value;
        var to = document.getElementById('hidden2').value;

        data["fdate"] = fm;
        data["todate"] = to;
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
                                nom_Success("Successfully Added");
                                setTimeout("location.href = 'GTN?action=loadBranch';", 1500);
//                                window.location = "Invoice?action=ToCreateInvoice";
                            } else {

                                sm_warning("Invoice Not Correctly Entered Please Try Again");

                            }
                        }
                    };
                    xmlHttp.open("POST", "GTN?action=SubmitGTN&data=" + jsonDetails, true);
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
    
    //Delete Item from Table
    $(document).on('click', '#invoiceItemTable span', function() {

    var r = confirm("Are you Sure You want to delete this?");
    if (r === true) {
        var tr = $(this).closest('tr');
       
        $(this).closest('tr').remove();

        delete item_details[this.id];

    } else {

    }

});
</script>

<%
    List<BranchProductmaster> pList =(List<BranchProductmaster>) request.getAttribute("pList");
//    int fbranch = Integer.parseInt(request.getAttribute("fb").toString());
//    int tobranch = Integer.parseInt(request.getAttribute("tb").toString());
    String tobranch = (String)request.getAttribute("tb");
    String fbranch = (String)request.getAttribute("fb");
    System.out.println(fbranch+"dgfgf "+tobranch);
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Create GTN (Good Transfer Note)
            </h3>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Add New GTN<small></small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form action="#" method="post" class="form-horizontal form-label-left" validate>
                        <!--<span class="section"></span>-->
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Product Category</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="pCategory" id="pCategory" required="required" onchange="LoadProduct()">
                                    <option selected="true" disabled="true">Select Product Category</option>
                                    <% for (BranchProductmaster p : pList) {%>
                                    <option value="<%=p.getProductMasterId().getProductId().getProductCategoryId().getProductCategoryId() %>"><%=p.getProductMasterId().getProductId().getProductCategoryId().getProductCategoryName() %></option>
                                    <%}%>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Product</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="product" id="product" required="required" onchange="LoadSalePrice()">
                                    <option selected="true" disabled="true">Select Product</option>
                                    
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Selling Price</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="sprice" id="sprice" required="required" onchange="LoadAvailableQty()">
                                    <option selected="true" disabled="true">Select Selling Price..</option>
                                    
                                </select>
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" id="" for="name">Available QTY: </label>
                                <label style="color: #404;" class="control-label col-md-3 col-sm-3 col-xs-12" id="aqty" for="name"></label>
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">QTY
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="qty" name="qty" class="form-control col-md-7 col-xs-12" placeholder="Enter Qty...." type="number">
                            </div>
                        </div>        
                        <div class="form-group" >
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <button id="send" type="button" onclick="checkAddToGrid()" class="btn btn-success">Add Product</button>
                            </div>
                        </div>
                                

                    </form>
                <div class="ln_solid"></div>
                    
                   <div class="row" style="padding-top: 20px;">
                            <div class="col-xs-12 table">
                                <table class="table table-striped" id="invoiceItemTable">
                                    <thead>
                                        <tr>
                                            <th>Product</th>
                                            <th>Price</th>
                                            <th>Qty#</th>
                                            <th>Total #</th>
                                            <th>Delete</th>
                                            
                                        </tr>
                                    </thead>
                                    <tbody id="invoiceItemBody">


                                    </tbody>
                                </table>
                            </div>
                            <!-- /.col -->
                        </div>

                        
                <input type="hidden" id="hidden2" name="hidden2" value="<%=tobranch%>"/>
                <input type="hidden" id="hidden1" name="hidden1" value="<%=fbranch%>"/>


                    <div class="ln_solid"></div>
                    <div class="form-group" >
                        <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                            <button id="send" type="button" onclick="submitGTN()" class="btn btn-success">Submit</button>
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>

</div>


<%@include file="../../template/footer.jsp"%>
