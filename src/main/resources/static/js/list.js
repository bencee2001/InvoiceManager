
function viewInvoice(val) {
    window.location.href = '/list/select/'+val
}

function switchInvoices(){
    if(document.querySelector('#swVal').checked){
        document.getElementById('ownTable').hidden = true
        document.getElementById('clientTable').hidden = false
    }else{
        document.getElementById('ownTable').hidden = false
        document.getElementById('clientTable').hidden = true
    }
}