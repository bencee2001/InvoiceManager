var csrf_name;
var csrf_token;

function ajaxInit(csrfName,csrfToken){
    csrf_name=csrfName;
    csrf_token=csrfToken;
}

function refreshNewCount(){
    $.ajax({
        url: '/refreshCount',
        type: 'GET',
        data:{
            csrf_name : csrf_token
        },
        success: function (data){
            document.getElementById('count').textContent=data;
        }
    })
}