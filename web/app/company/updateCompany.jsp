<%-- 
    Document   : viewCustomer
    Created on : Oct 20, 2016, 1:14:40 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.CompanyGroup"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>



<div class="">
   

    <%CompanyGroup cg = (CompanyGroup) request.getAttribute("cg");%>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small></small></h2>
   
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <form action="Company?action=updateCompany" method="post" class="form-horizontal form-label-left" novalidate >

                        </p>
                        <span class="section">Company Update</span>

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name"> Company Group Name <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="Name" class="form-control col-md-7 col-xs-12"  name="Name" required="required" type="text" value="<%=cg.getName()  %>">
                                <input id="cuId" name="cuId" required="required" type="hidden" value="<%=cg.getId() %>"> 
                            </div>
                        </div>
                            
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <!--                                <button type="submit" class="btn btn-primary">Cancel</button>-->
                                <button id="send" type="submit" class="btn btn-success">Update</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
                            
                                <%@include file="../../template/footer.jsp"%>