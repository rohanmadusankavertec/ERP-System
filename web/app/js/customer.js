/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function deleteCustomer(customerId) {
    BootstrapDialog.show({
        message: 'Do you want Deactivate this User ?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function (dialogRef) {
                    dialogRef.close();

                    var xmlHttp = getAjaxObject();
                    xmlHttp.onreadystatechange = function ()
                    {
                        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                        {
                            var reply = xmlHttp.responseText;
                            console.log(reply);
                            if (reply === "UserDeleted") {
                                nom_Success("Successfully Deleted");
                                setTimeout("location.href = 'Customer?action=ViewCustomer';", 1500);
//                                window.location = "Customer?action=ViewCustomer";
                            } else {
                                sm_warning("User is Not Deactivated, Please Try again");
                            }

                        }
                    };
                    xmlHttp.open("POST", "Customer?action=RemoveCustomer&customerId=" + customerId, true);
                    xmlHttp.send();




                }
            }, {
                label: 'No',
                action: function (dialogRef) {
                    dialogRef.close();
                }
            }]
    });

}
function updateCustomer(customerId) {

    window.location = "Customer?action=UpdateCustomer&customerId=" + customerId;


}

function checkEmail() {

    var email = document.getElementById('contactPersonEmail').value;
    $.ajax({
        type: "POST",
        url: "../../Customer?action=CheckEmail&email=" + email,
        success: function (msg) {

            if (msg === "alreadyExistUser") {
                sm_warning("This Email Address Already Registered with System. Please Try with another Email Address");
                document.getElementById('contactPersonEmail').value='';
            } else {

            }



        }

    });
}
