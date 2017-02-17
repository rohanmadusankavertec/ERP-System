/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).on("ready", function () {
    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = xmlHttp.responseText;

            

        }
    };
    xmlHttp.open("POST", "Privilege?action=CheckPrivilege", true);
    xmlHttp.send();


});
