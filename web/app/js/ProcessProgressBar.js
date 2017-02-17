$(document).ready(function(){
    var options = {
        
        beforeSend:function(){
            $("#progressBox").show();
            $("#progressBar").html('0%');
            $("#progressBar").css("width","0%");
            $("#message").empty();
        },
        uploadProgress:function (event,position,total,percentComplete){
            $("#progressBar").html(percentComplete+'%');
            $("#progressBar").css("width",percentComplete+'%');
            
            if(percentComplete>50){
                $("#message").html("File Upload is in Progress....!");
                $("#message").addClass("alert alert-warning");
            }
        },
        success:function(){
            $("#progressBar").html('100%');
            $("#progressBar").width('100%');
        },
        complete:function(response){
            
            $("#progressBar").removeClass("active");
                $("#message").removeClass("alert-warning");
                $("#message").addClass("alert alert-success");
                $("#message").html("File Upload has been Complete....!");
        },        
        error:function(){
            $("#message").html("Error....!");
            $("#message").removeClass("alert-warning");
                $("#message").addClass("alert alert-danger");
                 $("#message").html("File Uploading Failed....!");
        }
    };
    $("#UploadForm").ajaxForm(options);
});
