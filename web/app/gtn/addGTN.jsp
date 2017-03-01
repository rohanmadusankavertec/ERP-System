<%-- 
    Document   : addGTN
    Created on : Dec 27, 2016, 8:54:32 AM
    Author     : Java-Dev-Ruchira
--%>

<%@page import="com.vertec.hibe.model.Branch"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script type="text/javascript">
    
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
    
    function sentData(){
        
        var fbranch = document.getElementById('fBranch').value;
        var tbranch = document.getElementById('tBranch').value;
        
        if(fbranch === "Select From Branch"){
            sm_warning("Please Select the FROM Branch...");
        }else if(tbranch === "Select To Branch"){
            sm_warning("Please Select the TO Branch...");
        }else if(fbranch === tbranch){
            sm_warning("You have selected SAME Branchs,Please Check...");
        }else{
          window.location = "GTN?action=loadGtnPage&fBranch=" + fbranch + "&tBranch=" + tbranch;
        }
        
    }
    
</script>

<%
    List<Branch> bList =(List<Branch>) request.getAttribute("bList");
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
                    <!--<form action="GTN?action=loadGtnPage" method="post" class="form-horizontal form-label-left" novalidate>-->
                        <!--<span class="section"></span>-->
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">From Branch</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                
                                <select class="form-control" name="fBranch" id="fBranch" required="required">
                                    <option selected="true" disabled="true">Select From Branch</option>
                                    <% for (Branch b : bList) {%>
                                    <option value="<%=b.getBranchId() %>"><%=b.getBranchName() %></option>
                                    <%}%>
                                </select>                            
                            </div>
                        </div>
                                <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;" >
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">To Branch</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="tBranch" id="tBranch" required="required" onchange="">
                                    <option selected="true" disabled="true">Select To Branch</option>
                                    <% for (Branch b : bList) {%>
                                    <option value="<%=b.getBranchId() %>"><%=b.getBranchName() %></option>
                                    <%}%>
                                </select>                            
                            </div>
                        </div> 
                                <div class="clearfix"></div>
                        <div class="ln_solid"></div>
                        <div class="form-group" >
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <!--<input type="submit" value="Next00" class="btn btn-success"/>-->
                                <button id="send" onclick="sentData();" class="btn btn-success">Next</button>
                            </div>
                        </div>        

                    <!--</form>-->

                    
                   




                    
                    
                </div>
            </div>
        </div>
    </div>

</div>


<%@include file="../../template/footer.jsp"%>