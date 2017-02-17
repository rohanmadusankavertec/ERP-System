<%-- 
    Document   : viewCustomer
    Created on : Oct 20, 2016, 1:14:40 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Company"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<%
    Company  c = (Company)request.getAttribute("c");
%>

<div class="">
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small></small></h2>
   
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <form action="Company?action=updateCompanyDetails" method="post" class="form-horizontal form-label-left" novalidate >

                        </p>
                        <span class="section">Company Update</span>

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Company Name <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input value="<%=c.getCompanyName() %>" id="Name" class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="Name" placeholder="Enter Customer Name...." required="required" type="text">
                            </div>
                        </div>

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Address <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input value="<%=c.getAddress() %>" type="text" id="address" name="address" placeholder="Enter Address...." required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Contact Number <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input value="<%=c.getContactNo() %>" type="tel" id="hotline" name="hotline" placeholder="Enter Contact number...." required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        
                        
                        
                        

                        <div class="item form-group">
                            <label for="password" class="control-label col-md-3">Email</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input value="<%=c.getEmail() %>" id="Email" type="email" name="Email"  class="form-control col-md-7 col-xs-12" placeholder="Enter Email...." required="required" onblur="checkEmail();" >
                            </div>
                        </div>
                        <input type="hidden" name="cId" value="<%=c.getId()%>"/>
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