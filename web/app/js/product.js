/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function updateSelectedPC() {
    var id = document.getElementById('pcId').value;
    var branchName = document.getElementById('pcName').value;
    var address = document.getElementById('description').value;
    var dataArr = [id + "_", branchName + "_", address];
    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = xmlHttp.responseText;
            if (reply === "Success") {
                nom_Success("Successfully Updated");
                setTimeout("location.href = 'Product?action=ViewProductCategories';", 1500);
//                window.location = "Product?action=ViewProductCategories";
            } else {
                sm_warning("Product is Not Updated, Please Try again");
            }

        }
    };
    xmlHttp.open("POST", "Product?action=SaveUpdatedPC&dataArr=" + dataArr, true);
    xmlHttp.send();
}
function updateSelectedProduct() {
    BootstrapDialog.show({
        message: 'Do you want Update this Product ?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function (dialogRef) {
                    dialogRef.close();
                    var productId = document.getElementById('productId').value;
                    var productName = document.getElementById('productName').value;
                    var productCode = document.getElementById('productCode').value;
                    var description = document.getElementById('description').value;
                    var reorderLevel = document.getElementById('reorderLevel').value;
                    var dataArr = [productId + "_", productName + "_", productCode + "_", description + "_", reorderLevel];
                    var xmlHttp = getAjaxObject();
                    xmlHttp.onreadystatechange = function ()
                    {
                        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                        {
                            var reply = xmlHttp.responseText;
                            if (reply === "Success") {
                                nom_Success("Successfully Updated");
                                setTimeout("location.href = 'Product?action=ViewProducts';", 1500);
//                                window.location = "Product?action=ViewProducts";
                            } else {
                                sm_warning("Product is Not Updated, Please Try again");
                            }

                        }
                    };
                    xmlHttp.open("POST", "Product?action=SaveUpdatedProduct&dataArr=" + dataArr, true);
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

function deleteProduct(Pid) {
    BootstrapDialog.show({
        message: 'Do you want Delete this Product ?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function (dialogRef) {
                    dialogRef.close();
                    var dataArr = [Pid + "_", "Just"];
                    var xmlHttp = getAjaxObject();
                    xmlHttp.onreadystatechange = function ()
                    {
                        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                        {
                            var reply = xmlHttp.responseText;
                            if (reply === "Success") {
                                nom_Success("Successfully Updated");
                                setTimeout("location.href = 'Product?action=ViewProducts';", 1500);
//                                window.location = "Product?action=ViewProducts";
                            } else {
                                sm_warning("Product is Not Updated, Please Try again");
                            }

                        }
                    };
                    xmlHttp.open("POST", "Product?action=DeleteProduct&dataArr=" + dataArr, true);
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

function loadProductDetail() {
    $("#pmtable").empty();
    clearPM();
    var productId = document.getElementById('productId').value;

    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = eval('(' + xmlHttp.responseText + ')');
            console.log(reply);
            var arrLn1 = reply.ip.length;

            var pmtable = document.getElementById('pmtable');

            for (var f = 0; arrLn1 > f; f++) {
                var pmId = reply.ip[f].pmId;
                var product = reply.ip[f].pId;
                var purPrice = reply.ip[f].purchasedPrice;
                var sellPrice = reply.ip[f].sellingPrice;
                var row = document.createElement("tr");
                row.id = pmId;
                var col1 = document.createElement("td");
                col1.type = "text";
                col1.value = pmId;
                col1.innerHTML = pmId;
                var col2 = document.createElement("td");
                col2.type = "text";
                col2.value = product;
                col2.innerHTML = product;
                var col3 = document.createElement("td");
                col3.type = "text";
                col3.value = purPrice;
                col3.innerHTML = purPrice;
                var col4 = document.createElement("td");
                col4.type = "text";
                col4.value = sellPrice;
                col4.innerHTML = sellPrice;
                var col5 = document.createElement("td");
                var elem1 = document.createElement("span");
                elem1.id = 'update';
                elem1.name = 'update';
                elem1.type = "button";
                elem1.className = "btn btn-default glyphicon glyphicon-edit";
                var elem2 = document.createElement("span");
                elem2.id = 'delete';
                elem2.name = 'delete';
                elem2.type = "button";
                elem2.className = "btn btn-default glyphicon glyphicon-remove";
                col5.appendChild(elem1);
                col5.appendChild(elem2);

                row.appendChild(col1);
                row.appendChild(col2);
                row.appendChild(col3);
                row.appendChild(col4);
                row.appendChild(col5);
                pmtable.appendChild(row);
            }



        }
    };
    xmlHttp.open("POST", "Product?action=LoadProductMasters&productId=" + productId, true);
    xmlHttp.send();
}

function viewAddPM() {
    document.getElementById('addPm').className = '';
    document.getElementById('prosel').className = 'item form-group';
    document.getElementById('saveP').className = 'btn btn-success';
}
function clearPM() {
    document.getElementById('addPm').className = 'hidden';
    document.getElementById('prosel').className = 'item form-group';
    document.getElementById('updateP').className = 'btn btn-group-crop disabled';

    document.getElementById('saveP').className = 'btn btn-success disabled';
    document.getElementById('purchasedPrice').value = '';
    document.getElementById('sellingPrice').value = '';
}


function savePM() {
    var productId = document.getElementById('productAddId').value;
    var purchasedPrice = document.getElementById('purchasedPrice').value;
    var sellingPrice = document.getElementById('sellingPrice').value;

    var dataArr = [productId + "_", purchasedPrice + "_", sellingPrice];

    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = xmlHttp.responseText;
            if (reply === "Success") {
                nom_Success("Successfully Updated");
                setTimeout("location.href = 'Product?action=ToProductMaster';", 1500);
//                window.location = "Product?action=ToProductMaster";
            } else {
                sm_warning("Product Master is Not Added, Please Try again");
            }

        }
    };
    xmlHttp.open("POST", "Product?action=SaveProductMaster&dataArr=" + dataArr, true);
    xmlHttp.send();
}

$(document).on('click', '#pmtableOr span', function () {
    var tr = $(this).closest('tr');
    var pmId = tr.find('td:first-child').text();
    var product = tr.find('td:nth-child(2)').text();
    var purchasedPrice = tr.find('td:nth-child(3)').text();
    var sellingPrice = tr.find('td:nth-child(4)').text();


    if (this.id === 'update') {
        BootstrapDialog.show({
            message: 'Do you want Update this Product Master?',
            closable: false,
            buttons: [{
                    label: 'Yes',
                    action: function (dialogRef) {
                        dialogRef.close();
                        document.getElementById('pmId').value = pmId;
                        document.getElementById('prosel').className = 'hidden';
                        document.getElementById('purchasedPrice').value = purchasedPrice;
                        document.getElementById('sellingPrice').value = sellingPrice;
                        document.getElementById('addPm').className = '';
                        document.getElementById('updateP').className = 'btn btn-group-crop';
//        $("#pmtable").empty();
                    }
                }, {
                    label: 'No',
                    action: function (dialogRef) {
                        dialogRef.close();
                    }
                }]
        });
    } else if (this.id === 'delete') {

        BootstrapDialog.show({
            message: 'Do you want Remove this Product Master?',
            closable: false,
            buttons: [{
                    label: 'Yes',
                    action: function (dialogRef) {
                        dialogRef.close();

                        $.ajax({
                            type: "POST",
                            url: "Product?action=DeletePM&dataArr=" + pmId,
                            success: function (msg) {
                                // alert(msg);
                                if (msg === "Success") {
                                    nom_Success("Successfully Updated");
                                    setTimeout("location.href = 'Product?action=ToProductMaster';", 1500);
//                                    window.location = "Product?action=ToProductMaster";
                                } else {
                                    sm_warning("Vehicle is Not Updated, Please Try again");
                                }



                            }

                        });

                    }
                }, {
                    label: 'No',
                    action: function (dialogRef) {
                        dialogRef.close();
                    }
                }]
        });



    }

});

function updatePM() {

    BootstrapDialog.show({
        message: 'Do you want Update this Product Master?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function (dialogRef) {
                    dialogRef.close();

                    var pmId = document.getElementById('pmId').value;
//    var product = document.getElementById('productAddId').value;
                    var purchasedPrice = document.getElementById('purchasedPrice').value;
                    var sellingPrice = document.getElementById('sellingPrice').value;
                    var dataArr = [pmId + "_", "product" + "_", purchasedPrice + "_", sellingPrice];
                    $.ajax({
                        type: "POST",
                        url: "Product?action=SaveUpdatedPM&dataArr=" + dataArr,
                        success: function (msg) {
                            // alert(msg);
                            if (msg === "Success") {
                                nom_Success("Successfully Updated");
                                setTimeout("location.href = 'Product?action=ToProductMaster';", 1500);
//                                window.location = "Product?action=ToProductMaster";
                            } else {
                                sm_warning("Vehicle is Not Updated, Please Try again");
                            }



                        }

                    });
                }
            }, {
                label: 'No',
                action: function (dialogRef) {
                    dialogRef.close();
                }
            }]
    });

}