var item_details = {};


function AddToMRN() {
    var pro = document.getElementById("getpro").value;
    var qty = document.getElementById("qty").value;

    if (pro === "Select Product") {
        alert("Please select a product");
    } else if (qty === "") {
        alert("Please Enter the Qty");
    }else {
        var product=pro.split("_");
        var item_detail = {};
        var bool = true;
        
        for (var key in item_details)
            {
                
                var itdt = item_details[key];
                for (var key1 in itdt)
                {
                    if (key1.indexOf("productId") !== -1) {
                        if (product[0].indexOf(itdt[key1]) !== -1) {
                            bool = false;
                            sm_warning("This Item is already exist in Material Requisition Note....");
                        }
                    }
                }
            }
        
        if(bool){
        
        
        item_detail["productId"] = product[0];
        item_detail["qty"] = qty;
        item_details[pro] = item_detail;

        var invoiceItemTable = document.getElementById('poItemTable').getElementsByTagName('tbody')[0];
        var row = document.createElement("tr");
        row.id = product[0];
        var col1 = document.createElement("td");
        col1.type = "text";
        col1.value = product[0];
        col1.innerHTML = product[0];
        var col2 = document.createElement("td");
        col2.type = "text";
        col2.value = product[1]+" "+product[2];
        col2.innerHTML = product[1]+" "+product[2];
        var col3 = document.createElement("td");
        col3.type = "text";
        col3.value = qty;
        col3.innerHTML = qty;
        var col7 = document.createElement("td");
        var elem1 = document.createElement("span");
        elem1.id = product[0];
        elem1.name = product[0];
        elem1.type = "button";
        elem1.className = "btn btn-default glyphicon glyphicon-remove text-center";
        col7.appendChild(elem1);
        row.appendChild(col1);
        row.appendChild(col2);
        row.appendChild(col3);
        row.appendChild(col7);
        invoiceItemTable.appendChild(row);
        
        clearFields();
    }
    }
}

function clearFields() {
    document.getElementById("getpro").value="";
    document.getElementById("qty").value="";
}


$(document).on('click', '#poItemTable span', function() {

    var r = confirm("Are you Sure You want to delete this?");
    if (r === true) {
        var tr = $(this).closest('tr');
        
        $(this).closest('tr').remove();

        delete item_details[this.id];

    } else {

    }

});

function submitMRN() {
    var data = {};
    
    data["item_details"] = item_details;
    var jsonDetails = JSON.stringify(data);
    BootstrapDialog.show({
        message: 'Do you want to Submit ?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function(dialogRef) {
                    dialogRef.close();


                    var xmlHttp = getAjaxObject();
                    xmlHttp.onreadystatechange = function()
                    {
                        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                        {
                            var reply = xmlHttp.responseText;

                            if (reply === "Success") {
                                nom_Success("Material Requisition Note Successfully Added");
                                setTimeout("location.href = 'MRN?action=toMRN';", 1500);
                            } else {
                                sm_warning("Material Requisition Note Not Correctly Entered Please Try Again");
                            }
                        }
                    };
                    xmlHttp.open("POST", "MRN?action=SubmitMRN&data=" + jsonDetails, true);
                    xmlHttp.send();


                }
            }, {
                label: 'No',
                action: function(dialogRef) {
                    dialogRef.close();
                }
            }]
    });

}


