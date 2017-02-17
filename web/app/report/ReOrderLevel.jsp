<%@page import="com.vertec.hibe.model.Branch"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>


<%
    List<Branch> BList =(List<Branch>) request.getAttribute("blist");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Re Order Level Report
                <small>
                    
                </small>
            </h3>
        </div>
    </div>
    <div class="clearfix"></div>


    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small>Selecting Branch</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>

                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form action="Stock?action=reorderlevelreport" method="post">

                        <div class="clearfix"></div>
                        <div style="padding-top: 10px;" id="branchwise">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Branch <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="branchId" id="branchId" >
                                    <option selected="true" disabled value="">Select Branch</option>
                                    
                                    <option value="All_All">All</option>
                                    <%for(Branch b: BList){%>
                                        <option value="<%=b.getBranchId() %>"><%=b.getBranchName() %></option>
                                    <%}%>
                                    
                                </select>                              
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        
                        

                        <div class="form-group" style="padding-top: 10px;">
                            <div class="col-md-6 col-md-offset-3">
                                <button id="send" onclick="" type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>





    <center>

    </center>
    <div class="clearfix"></div>
    <div class="clearfix"></div>
</div>




<%@include file="../../template/footer.jsp"%>