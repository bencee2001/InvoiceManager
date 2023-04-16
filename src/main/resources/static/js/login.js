function success() {
    if(document.getElementById("username").value!=="" && document.getElementById("password").value.length>3){
        document.getElementById("button").disabled=false
    }else{
        document.getElementById("button").disabled=true
    }
}