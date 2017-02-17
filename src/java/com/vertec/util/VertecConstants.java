/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.util;

/**
 *
 * @author Vertec
 */
public interface VertecConstants {

    String SUCCESS = "Success";
    String FAILED = "Failed";
    String DISABLED = "Disabled";
    String ERROR = "Error";
    String USER = "user";
    String NOT_ADDED = "not added";
    String NOT_UPDATED = "not updated";
    String EXIST = "exist";
    String NOT_EXIST = "not exist";
    String INVALID_LOGIN = "invalidlogin";
    String SESSION_NULL = "sessionNull";
    String ERROR_MESSAGE = "errorMessage";
    String USER_REGISTERED_SUCCESSFULLY = "userRegisteredSuccessfully";
    String USER_DELETED_SUCCESSFULLY = "userDeletedSuccessfully";
    String USER_UPDATED_SUCCESSFULLY = "userUpatedSuccessfully";
    String ALREADYEXIST = "alreadyExistUser";
    String USER_LOGGED = "userLogged";
    String UPDATED = "UserDeleted";
    String UPDATEDPASSWORD = "UpdatePassword";
    String LOGGED = "<script type=\"text/javascript\">\n"
            + "   var permanotice, tooltip, _alert;\n"
            + "   $(function() {\n"
            + "     new PNotify({\n"
            + "       title: \"User Logged\",\n"
            + "       type: \"success\",\n"
            + "       text: \"User Logged Successfully.\",\n"
            + "       nonblock: {\n"
            + "         nonblock: true\n"
            + "       },\n"
            + "       before_close: function(PNotify) {\n"
            + "         // You can access the notice's options with this. It is read only.\n"
            + "         //PNotify.options.text;\n"
            + "\n"
            + "         // You can change the notice's options after the timer like this:\n"
            + "         PNotify.update({\n"
            + "           title: PNotify.options.title + \" - Enjoy your Stay\",\n"
            + "           before_close: null\n"
            + "         });\n"
            + "         PNotify.queueRemove();\n"
            + "         return false;\n"
            + "       }\n"
            + "     });\n"
            + "\n"
            + "   });\n"
            + " </script>";
}
