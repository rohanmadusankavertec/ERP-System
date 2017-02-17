
<%@page import="com.vertec.hibe.model.Company"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="com.vertec.hibe.model.BranchProductmaster"%>
<%@page import="com.vertec.hibe.model.Branch"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/invoice.js"></script>
<script src="app/js/notAlert.js"></script>

<script type="text/javascript">

    //load product to select element
    function loadProducts() {
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                document.getElementById("productId").innerHTML = "";
                var MainElement = document.getElementById("productId");
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
        var branch = document.getElementById("branchId").value;
        xmlHttp.open("POST", "Invoice?action=ProductFromCategory&cid=" + cid + "&branchId=" + branch, true);
        xmlHttp.send();
    }


    //load branch product master to select element
    function loadBranchPM() {
        $("#bpmId").empty();
        var s1 = document.getElementById('bpmId');
        var t1 = document.createElement("option");
        t1.value = "";
        t1.innerHTML = "Select Price Master";
        s1.appendChild(t1);
        var productId = document.getElementById('productId').value;
        var branchId = document.getElementById('branchId').value;
        var pid = productId.split("_");
        var dataArr = [branchId, pid[0]];
        $.ajax({
            type: "POST",
            url: "Invoice?action=LoadBPMToInvoice&dataArr=" + dataArr,
            success: function (msg) {
                var reply = eval('(' + msg + ')');
                var arrLn1 = reply.jArr1;
                var bpm = document.getElementById('bpmId');
                for (var f = 0; f < arrLn1.length; f++) {
                    var t = document.createElement("option");
                    var val = arrLn1[f].bpmid;
                    t.value = val;
                    t.innerHTML = arrLn1[f].pprice + "_" + arrLn1[f].sprice;
                    bpm.appendChild(t);
                }
            }
        });
    }


</script>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Bin Card
            </h3>
        </div>
    </div>
    <%
        List<Object[]> pList = (List<Object[]>) request.getAttribute("pList");
        Branch branch = (Branch) request.getAttribute("branch");
    %>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Create Bin Card </h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form action="Report?action=ToViewBIN" method="post" target="_blank">
                        <input type="hidden" id="branchId" name="branchId" required="required" class="form-control col-md-7 col-xs-12" value="<%=branch.getBranchId()%>"/>        
                        <div class="item form-group"  style="padding-top: 10px;">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Select Product Category</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="getcat" id="getcat"  required="required" onchange="loadProducts()">
                                    <option selected="true" >Select Product Category</option>
                                    <%
                                        HttpSession httpSession = request.getSession();
                                        Company company = (Company) httpSession.getAttribute("company");
                                        SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                        Session ses = sf.openSession();
                                        String sql = "SELECT product_category_id,product_category_name FROM product_category WHERE company_id='"+company.getId()+"'";
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
                        <div class="form-group invoiceenter" style="padding-top: 10px;">
                            <label for="itemSelect" class="control-label col-lg-3 col-md-3 lbl_name">Select Product : </label>
                            <div class="col-lg-6 col-md-6">
                                <select class="form-control" name="productId" id="productId" onchange="loadBranchPM();">
                                    <option selected="true" value="" disabled="true">Select Product</option>
                                    <%for (Object[] p : pList) {%>
                                    <option value="<%=p[0].toString() + "_" + p[1].toString() + "_" + p[2].toString()%>"><%=p[1].toString() + "_" + p[2].toString()%></option>
                                    <%}%>
                                </select>
                            </div>
                        </div>

                        <div class="clearfix"></div>
                        <div class="form-group" style="padding-top: 10px;" id="pmasterDiv">
                            <label for="itemSelect" class="control-label col-lg-3 col-md-3 lbl_name">Select Price Master : </label>
                            <div class="col-lg-6 col-md-6">
                                <select class="form-control" name="bpmId" id="bpmId">
                                    <option selected="true" value="" disabled="true">Select Price Master</option>
                                </select>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" id="updateP" type="submit"><i class="fa fa-arrow-right"></i>  View Bin Card </button>
                            </div>
                        </div> 
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
