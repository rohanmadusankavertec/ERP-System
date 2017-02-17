
<%@page import="java.util.Date"%>
<%@page import="com.vertec.hibe.model.BranchProductmaster"%>

<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<%
    List<String[]> bpm =(List<String[]>) request.getAttribute("productList");
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
                    <h2><small></small></h2>

                    
                    
    <h4>DATE :<%=new Date()%></h4>
    <br>
    <br>
    <br>


    
    <center>
        <div class="table-responsive">
                    <table id="example" class="table table-bordered table-hover table-striped">
                        <thead>
                            <tr class="headings">
                                <th>Product Code </th>
                                <th>Product Name </th>
                                <th>Product Description </th>
                                <th>Re Order Level </th>
                                <th>Available QTY</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (String[] b : bpm) {
                            System.out.println("DATA : "+b[0]+" "+b[1]+" "+b[2]+" "+b[3]);
                            %>
                                
                            <tr>
                                <td><%=b[0] %></td>
                                <td><%=b[1]%></td>
                                <td><%=b[2]%></td>
                                <td><%=b[3]%></td>
                                <td><%=b[4]%></td>
                            </tr>
                            <%}%>
                        </tbody>

                    </table>
                </div>
    </center>
    
    <br>
    <br>

                    
                    
                    
                    
                    <div class="clearfix"></div>
                </div>

            </div>
        </div>
    </div>











    <button class="btn btn-default" onclick="window.print();"><i class="fa fa-print"></i> Print</button>

    <div class="clearfix"></div>
    <div class="clearfix"></div>
</div>




<%@include file="../../template/footer.jsp"%>