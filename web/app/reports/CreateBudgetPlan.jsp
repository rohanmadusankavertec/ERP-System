<%-- 
    Document   : ProfitLoss
    Created on : Nov 26, 2016, 9:08:08 AM
    Author     : Java-Dev-Ruchira
--%>

<%@page import="com.vertec.hibe.model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>


<script type="text/javascript">

    function fieldsVisibility() {
        clearFields();
        var acc = document.getElementById("account").value;
        var year = document.getElementById("year").value;
        var fields = document.getElementById("monthsFields");
        if (acc === "" || year === "") {
            fields.className = "hidden";
        } else {

            var xmlHttp = getAjaxObject();
            xmlHttp.onreadystatechange = function ()
            {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                {
                    var reply = eval('(' + xmlHttp.responseText + ')');
                    var arr = reply.bp;
                    for (var i = 1; i < 13; i++) {
                        var bool = true;
                        for (var f = 0; arr.length > f; f++) {
                            if (parseInt(arr[f].month) === parseInt(i)) {

                                document.getElementById("val" + i).value = arr[f].value;
                                document.getElementById("save" + i).className = "hidden";
                                document.getElementById("update" + i).className = "btn btn-warning";
                                bool = false;
                            }
                        }
                        if (bool) {
                            document.getElementById("save" + i).className = "btn btn-success";
                            document.getElementById("update" + i).className = "hidden";
                        }
                    }
                    fields.className = "";
                }
            };
            xmlHttp.open("POST", "Report?action=getBudgetPlan&account=" + acc + "&year=" + year, true);
            xmlHttp.send();




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
    function save(id) {
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                var reply = xmlHttp.responseText;
                alert(reply);
                if (reply === "success") {
                    fieldsVisibility();
                } else {
                    sm_warning("Something went wronge...");
                }
            }
        };
        var acc = document.getElementById("account").value;
        var year = document.getElementById("year").value;
        var value = document.getElementById("val" + id).value;
        xmlHttp.open("POST", "Report?action=SaveBudgetPlan&account=" + acc + "&year=" + year + "&value=" + value + "&month=" + id, true);
        xmlHttp.send();

    }

    function update(id) {
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                var reply = xmlHttp.responseText;
                alert(reply);
                if (reply === "success") {
                    fieldsVisibility();
                } else {
                    sm_warning("Something went wronge...");
                }
            }
        };
        var acc = document.getElementById("account").value;
        var year = document.getElementById("year").value;
        var value = document.getElementById("val" + id).value;
        xmlHttp.open("POST", "Report?action=UpdateBudgetPlan&account=" + acc + "&year=" + year + "&value=" + value + "&month=" + id, true);
        xmlHttp.send();

    }

    function clearFields() {
        for (var i = 1; i < 13; i++) {
            document.getElementById("val" + i).value = "";

        }

    }

</script>










<%
    List<Account> e = (List<Account>) request.getAttribute("account");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Create Budget Plan 
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
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="item form-group">
                        <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Account </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="account" id="account" onchange="fieldsVisibility()" required="required">
                                <option selected="true" value="">Select Account</option>
                                <% for (Account a : e) {%>
                                <option value="<%=a.getId()%>"><%=a.getName()%></option>
                                <%}%>
                            </select>                              
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="item form-group" style="padding-top: 10px; padding-bottom: 50px;">
                        <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Year </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" name="year" id="year" onchange="fieldsVisibility()" required="required">
                                <option selected="true" value="">Select Year</option>
                                <option value="2015">2015</option>
                                <option value="2016">2016</option>
                                <option value="2017">2017</option>
                                <option value="2018">2018</option>
                                <option value="2019">2019</option>
                                <option value="2020">2020</option>
                                <option value="2021">2021</option>
                                <option value="2022">2022</option>
                            </select>                              
                        </div>
                    </div>

                    <div class="ln_solid" ></div>        









                    <div class="hidden" id="monthsFields">

                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">January
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="val1" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="save1" type="button" class="btn btn-success" onclick="save('1')">Save</button>
                                <button id="update1" type="button" class="btn btn-warning" onclick="update('1')">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">February
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="val2" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="save2" type="button" class="btn btn-success" onclick="save('2')">Save</button>
                                <button id="update2" type="button" class="btn btn-warning" onclick="update('2')">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">March 
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="val3" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="save3" type="button" class="btn btn-success" onclick="save('3')">Save</button>
                                <button id="update3" type="button" class="btn btn-warning" onclick="update('3')">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">April
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="val4" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="save4" type="button" class="btn btn-success" onclick="save('4')">Save</button>
                                <button id="update4" type="button" class="btn btn-warning" onclick="update('4')">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">May
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="val5" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="save5" type="button" class="btn btn-success" onclick="save('5')">Save</button>
                                <button id="update5" type="button" class="btn btn-warning" onclick="update('5')">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">June 
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="val6" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="save6" type="button" class="btn btn-success" onclick="save('6')">Save</button>
                                <button id="update6" type="button" class="btn btn-warning" onclick="update('6')">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">July
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="val7" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="save7" type="button" class="btn btn-success" onclick="save('7')">Save</button>
                                <button id="update7" type="button" class="btn btn-warning" onclick="update('7')">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">August 
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="val8" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="save8" type="button" class="btn btn-success" onclick="save('8')">Save</button>
                                <button id="update8" type="button" class="btn btn-warning" onclick="update('8')">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">September
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="val9" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="save9" type="button" class="btn btn-success" onclick="save('9')">Save</button>
                                <button id="update9" type="button" class="btn btn-warning" onclick="update('9')">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">October 
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="val10" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="save10" type="button" class="btn btn-success" onclick="save('10')">Save</button>
                                <button id="update10" type="button" class="btn btn-warning" onclick="update('10')">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">November 
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="val11" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="save11" type="button" class="btn btn-success" onclick="save('11')">Save</button>
                                <button id="update11" type="button" class="btn btn-warning" onclick="update('11')">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">December 
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="val12" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="save12" type="button" class="btn btn-success" onclick="save('12')">Save</button>
                                <button id="update12" type="button" class="btn btn-warning" onclick="update('12')">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>


                    </div>





                </div>
            </div>
        </div>
    </div>
</div>




<%@include file="../../template/footer.jsp"%>
