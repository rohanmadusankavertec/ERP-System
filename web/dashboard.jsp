<%-- 
    Document   : dashboard
    Created on : Nov 02, 2016, 12:31:21 AM
    Author     : Rohan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="template/header.jsp"%>
<%@include file="template/sidebar.jsp"%>
<script src="app/js/notAlert.js"></script>

<script type="text/javascript">
//    loadData();
//    function loadData() {
//        $.ajax({
//            type: "POST",
//            url: "Report?action=dashboard",
//            success: function(msg) {
//                var reply = eval('(' + msg + ')');
//                var arrLn1 = reply.des;
//                for (var f = 0; f < arrLn1.length; f++) {
//                    document.getElementById("employee").innerHTML=arrLn1[f].liblitity;
//                    document.getElementById("debtors").innerHTML=arrLn1[f].debtors;
//                    document.getElementById("income").innerHTML=arrLn1[f].income;
//                    document.getElementById("Expense").innerHTML=arrLn1[f].expense;
//                    document.getElementById("account").innerHTML=arrLn1[f].account;
//                }
//            }
//        });
//    }



    function loadInventory() {
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                window.location = 'dashboard.jsp';
            }
        };
        xmlHttp.open("POST", "Dashboard?action=loadinventory", true);
        xmlHttp.send();
    }
    function loadpayroll() {
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                window.location = 'dashboard.jsp';
            }
        };
        xmlHttp.open("POST", "Dashboard?action=loadpayroll", true);
        xmlHttp.send();
    }
    function loadAccounting() {
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                window.location = 'dashboard.jsp';
            }
        };
        xmlHttp.open("POST", "Dashboard?action=loadaccounting", true);
        xmlHttp.send();
    }
</script>
<div class="">
    <div class="row top_tiles">
        
        <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12" onclick="loadInventory() ;" style="width: 300px;height: 100px; margin-left: 20px; cursor: pointer; ">
            <div class="tile-stats" style="width: 300px;height: 150px; background-color: #0063DC;">
                <div class="icon"><i class="fa fa-shopping-bag"></i>
                </div>
                <h3 style="color: #FFFFFF;">Inventory <br><small style="color: #FFFFFF;">Management</small></h3>
                <p></p>
            </div>
        </div>

        <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12" onclick="loadpayroll() ;" style="width: 300px;height: 100px; margin-left: 20px; cursor: pointer;">
            <div class="tile-stats" style="width: 300px;height: 150px; background-color: blueviolet;">
                <div class="icon"><i class="fa fa-calendar"></i>
                </div>
                <h3 style="color: #FFFFFF;">Payroll <br><small style="color: #FFFFFF;">Management</small></h3>
                <p></p>
            </div>
        </div>

        <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12" onclick="loadAccounting() ;" style="width: 300px;height: 100px; margin-left: 20px; cursor: pointer;">
            <div class="tile-stats" style="width: 300px;height: 150px; background-color: #066;">
                <div class="icon"><i class="fa fa-dollar"></i>
                </div>
                <h3 style="color: #FFFFFF;">Account <br><small style="color: #FFFFFF;">Management</small></h3>
                <p></p>
            </div>
        </div>
        
    </div>

    


</div>
<div class="clearfix" style="margin-top: 100px;"></div>
<script src="resources/js/echart/echarts-all.js"></script>
<script src="resources/js/echart/green.js"></script>
<script src="app/js/dashboard.js"></script>

<!-- footer content -->

<%@include file="template/footer.jsp"%>
