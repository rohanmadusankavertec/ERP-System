<%-- 
    Document   : JournamEntries
    Created on : Nov 24, 2016, 12:14:51 PM
    Author     : Java-Dev-Ruchira
--%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<script type="text/javascript">
    
//    open journal entries in new tab
    function loadGeneralEntries(){
        var fdate = document.getElementById("fromDay").value;
        var tdate = document.getElementById("toDate").value;
        var win = window.open("app/reports/GeneralJournalEntries.jsp?fromDay="+fdate+"&toDate="+tdate,'_blank');
        win.focus();
    }
    
    function getValue(){
        $('fromDay').val('value');
        $('toDate').val('value');
    }
    
</script>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                 Journal Entries
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

                    <form  action="Report?action=loadJournalEntriesPage" target="_blank" method="post" class="form-horizontal form-label-left" novalidate>

                        </p>
                        <span class="section">Journal Entries Info</span>
                        
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">From Date</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="fromDay" type="date" name="fromDay" data-validate-linked="password" class="form-control col-md-7 col-xs-12" required="required">
                                
                            </div>
                        </div>
                                
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">To Date </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="toDate" type="date" name="toDate" data-validate-linked="password" class="form-control col-md-7 col-xs-12" required="required">
                            </div>
                        </div> 
                        
                        
                        
                        
                        
                        
                        
                        <div class="ln_solid"></div>        
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <!--                                <button type="submit" class="btn btn-primary">Cancel</button>-->
                                <!--<button id="send"  class="btn btn-success">Submit</button>-->
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