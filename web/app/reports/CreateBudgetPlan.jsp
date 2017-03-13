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

function fieldsVisibility(){
    var acc = document.getElementById("account").value;
    var year = document.getElementById("year").value;
    var fields = document.getElementById("monthsFields");
    if(acc==="" || year===""){
        fields.className="hidden";
    }else{
        fields.className="";
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
                                <input type="number" id="january" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="submit" type="button" class="btn btn-success">Save</button>
                                <button id="submit" type="button" class="btn btn-warning">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">February
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="february" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="submit" type="button" class="btn btn-success">Save</button>
                                <button id="submit" type="button" class="btn btn-warning">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">March 
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="march" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="submit" type="button" class="btn btn-success">Save</button>
                                <button id="submit" type="button" class="btn btn-warning">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">April
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="april" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="submit" type="button" class="btn btn-success">Save</button>
                                <button id="submit" type="button" class="btn btn-warning">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">May
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="may" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="submit" type="button" class="btn btn-success">Save</button>
                                <button id="submit" type="button" class="btn btn-warning">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">June 
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="june" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="submit" type="button" class="btn btn-success">Save</button>
                                <button id="submit" type="button" class="btn btn-warning">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">July
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="july" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="submit" type="button" class="btn btn-success">Save</button>
                                <button id="submit" type="button" class="btn btn-warning">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">August 
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="august" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="submit" type="button" class="btn btn-success">Save</button>
                                <button id="submit" type="button" class="btn btn-warning">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">September
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="september" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="submit" type="button" class="btn btn-success">Save</button>
                                <button id="submit" type="button" class="btn btn-warning">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">October 
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="october" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="submit" type="button" class="btn btn-success">Save</button>
                                <button id="submit" type="button" class="btn btn-warning">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">November 
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="november" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="submit" type="button" class="btn btn-success">Save</button>
                                <button id="submit" type="button" class="btn btn-warning">Update</button>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name" style="text-align: right;">December 
                            </label>
                            <div class="col-md-3 col-sm-3 col-xs-6">
                                <input type="number" id="december" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                            <div class="col-md-5">
                                <button id="submit" type="button" class="btn btn-success">Save</button>
                                <button id="submit" type="button" class="btn btn-warning">Update</button>
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
