<%-- 
    Document   : ViewAccount
    Created on : Nov 17, 2016, 5:03:16 PM
    Author     : Java-Dev-Ruchira
--%>

<%@page import="com.vertec.hibe.model.Type"%>
<%@page import="com.vertec.hibe.model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>


<%
    List<Type> typeList =(List<Type>) request.getAttribute("type"); 
    Account ac =(Account) request.getAttribute("acc"); 

%>


<script type="text/javascript">
    
    //Load account data to select elements after 100 miliseconds
    setTimeout("document.getElementById('accountType').value=<%=ac.getSubtypeId().getTypeId().getId()%>;loadToUpdate();",100);
    
    //Load account data to select elements after 100 miliseconds
    function loadToUpdate(){
       var type = document.getElementById("accountType").value;
        
        $("subType").empty();
        $.ajax({
            type: "POST",
            url: "Account?action=loadofSubType&typeId="+type,
            success: function (msg) {
                var reply = eval('('+ msg +')');
                var arrLn1 = reply.subType;
                var sType = document.getElementById("subType");
                var ihtml = "";
                for(var i=0 ;i<arrLn1.length;i++){
                    ihtml +="<option value='"+arrLn1[i].id+"'>"+arrLn1[i].name+"</option>";
                }
                 sType.innerHTML=ihtml;  
                 document.getElementById('subType').value=<%=ac.getSubtypeId().getId()%>;
            }
        });
    }
    
</script>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Update Account
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

                    <form action="Account?action=UpdateAccont" method="post" class="form-horizontal form-label-left" novalidate>

                   
                        <span class="section">Account Info</span>
                        
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Select Account type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="accountType" id="accountType" onchange="loadToUpdate();"  required="required" >
                                    <option selected="true" disabled="true" >Select Account type</option>
                                    <% for (Type t : typeList) {%>
                                    <option value="<%= t.getId() %>"><%= t.getName() %></option>
                                    <%}%>
                                </select>
                            </div>
                        </div>
                        
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Select Sub type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="subType" id="subType"  required="required" >
                                    
                                    
                                </select>
                            </div>
                        </div>        

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name"> Account Name <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="accountName" class="form-control col-md-7 col-xs-12" data-validate-length-range="6" data-validate-words="2" name="accountName" placeholder="Account name..." required="required" type="text" value="<%=ac.getName() %>">
                                <input type="hidden" name="accountId" value="<%=ac.getId() %>"/>
                            </div>
                        </div> 
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Opening Balance</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="balance" type="number" name="balance" data-validate-linked="password" class="form-control col-md-7 col-xs-12" required="required" value="<%=ac.getBalance() %>">
                            </div>
                        </div>
                        <div class="ln_solid"></div>        
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <!--                                <button type="submit" class="btn btn-primary">Cancel</button>-->
                                <button id="send" type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                        
                       


<!--                        <div class="ln_solid"></div>-->
                        
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>



<%@include file="../../template/footer.jsp"%>
