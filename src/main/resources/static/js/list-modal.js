function showPayPanel( invoiceId ) {
    chosenInvoiceID = invoiceId
    modal.style.display = "block";
}

function sendPayRequest(){
    console.log(document.getElementById("typeSelect").value)
    $.ajax({
        url: '/pay/select/'+chosenInvoiceID,
        type: 'GET',
        data:{
            csrf_name : csrf_token,
            type : document.getElementById("typeSelect").value
        },
        success: () => reloadTables()
    })
    modal.style.display = "none";
}

function btnChange(){
    if(document.getElementById("typeSelect").value!=='Select Payment Type'){
        document.getElementById("payBtn").disabled=false
    }else{
        document.getElementById("payBtn").disabled=true
    }
}

function modalWork(span, modal, window) {
    span.onclick = function () {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}