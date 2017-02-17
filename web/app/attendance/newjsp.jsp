<%-- 
    Document   : AJAX
    Created on : Jan 16, 2015, 7:41:22 AM
    Author     : ROHAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <style type="text/css">
            #progressBox{
                width: 500px;
                height: auto;
            }
            #nametag{
                width: 350px;

            }
            #fileUpload{
                position: relative;
                overflow: hidden;
                margin: 10px;

            }
            #fileUpload input.upload{
                position: absolute;
                cursor: pointer;
                opacity: 0;
                top: 0;
                right: 0;
                margin: 0;
                padding: 0;
                font-size: 20px;
            }
            #message{
                height: 50px;
                width: 350px;
            }
        </style>
        <script src="../js/jquery.js" type="text/javascript"></script>
        <script src="../js/jquery.form.js" type="text/javascript"></script>
        <script src="../js/ProcessProgressBar.js" type="text/javascript"></script>
        <script type="text/javascript">
            //get File name 
            function getFileName() {
                var f1 = document.getElementById("fileName").value;
                var f2 = f1.split("\\");
                document.getElementById("nametag").value = f2[f2.length - 1];
            }

            var xmlhttp;
            //Parse multiple form data. this method use for upload the file
            function Create() {
                try {
                    var xml = new XMLHttpRequest();
                    var args = arguments;
                    var context = this;
                    var multipart = "";

                    xml.open(args[0].method, args[0].url, true);

                    if (args[0].method.search(/post/i) !== -1) {
                        var boundary = Math.random().toString().substr(2);
                        xml.setRequestHeader("content-type",
                                "multipart/form-data; charset=utf-8; boundary=" + boundary);
                        for (var key in args[0].data) {
                            multipart += "--" + boundary
                                    + "\r\nContent-Disposition: form-data; name=" + key
                                    + "\r\nContent-type: application/octet-stream"
                                    + "\r\n\r\n" + args[0].data[key] + "\r\n";
                        }
                        multipart += "--" + boundary + "--\r\n";
                    }
                    xml.onreadystatechange = function () {
                        try {
                            if (xml.readyState == 4) {
                                context.txt = xml.responseText;
                                context.xml = xml.responseXML;
                                args[0].callback();
                            }
                        } catch (e) {
                        }
                    }

                    xml.send(multipart);
                } catch (e) {
                }

            }





        </script>
    </head>
    <body>
    <center>
        <h1>~~File Uploader~~</h1>

        <form method="POST" action="/Uploader" id="UploadForm" enctype="multipart/form-data">

            <div id="progressBox">
                <input id="nametag" placeholder="Choose File" disabled="disabled"/>
                <div id="fileUpload" class='btn btn-danger'>
                    <span>Browse</span>
                    <input name='mmm' onchange="getFileName()" class="upload" type="file" id="fileName"/>
                </div>
                <div class='progress '>
                    <div class='progress-bar progress-bar-success progress-bar-striped active'  id='progressBar'> 
                    </div>
                </div>
            </div>
            <br>
            <div id='message'>

            </div>

            <input class="btn btn-success" type="submit" value="Upload File"/>
        </form>
    </center>
</body>
</html>
