<%-- 
    Document   : header
    Created on : Mar 21, 2016, 2:25:33 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.SysUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">


    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Meta, title, CSS, favicons, etc. -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Enterprise Resource Planning System </title>

        <!-- Bootstrap core CSS -->
        <%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="context" value="${pageContext.request.contextPath}" />
        <link href="${context}/resources/css/bootstrap.min.css" rel="stylesheet">
        <link href="${context}/resources/css/bootstrap-dialog.min.css" rel="stylesheet">

        <link href="${context}/resources/fonts/css/font-awesome.min.css" rel="stylesheet">
        <link href="${context}/resources/css/animate.min.css" rel="stylesheet">

        <!-- Custom styling plus plugins -->
        <link href="${context}/resources/css/custom.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="${context}/resources/css/maps/jquery-jvectormap-2.0.3.css" />
        <link href="${context}/resources/css/icheck/flat/green.css" rel="stylesheet" />
        <link href="${context}/resources/css/floatexamples.css" rel="stylesheet" type="text/css" />
        <link href="${context}/resources/css/icheck/flat/green.css" rel="stylesheet">
        <link href="${context}/resources/css/datatables/tools/css/dataTables.tableTools.css" rel="stylesheet">
        <link href="${context}/resources/css/style.css" rel="stylesheet">

        <script src="${context}/resources/js/jquery.min.js"></script>
        <script src="${context}/resources/js/jquery.leanModal.min.js"></script>
        <script src="${context}/resources/js/nprogress.js"></script>
        <script src="${context}/resources/js/bootstrap-dialog.min.js"></script>

        <script type="text/javascript" src="${context}/resources/js/notify/pnotify.core.js"></script>
        <script type="text/javascript" src="${context}/resources/js/notify/pnotify.buttons.js"></script>
        <script type="text/javascript" src="${context}/resources/js/notify/pnotify.nonblock.js"></script>

        <link rel="shortcut icon" href="${context}/resources/images/erp_icon.ico" />
        <!--[if lt IE 9]>
              <script src="../assets/js/ie8-responsive-file-warning.js"></script>
              <![endif]-->

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
                <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
                <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
              <![endif]-->
        <script type="text/javascript">
            setTimeout(function () {
                $('#mydiv').fadeOut('fast');
            }, 1000);
        </script>



    </head>


    <body class="nav-md">

        <div class="container body">


            <div class="main_container">
