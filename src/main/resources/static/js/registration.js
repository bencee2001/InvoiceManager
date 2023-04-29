var roles
function initRegistration(newRoles) {
    roles = newRoles.filter(item => item.id !== 3);

    let regForm = document.getElementById("regForm");
    regForm.addEventListener("submit", (e) => {
        e.preventDefault();
        if (document.getElementById("psw1").value === document.getElementById("psw2").value) {
            document.getElementById("regForm").submit();
        } else {
            alert('Passwords are not matching.');
        }
    })
}
function isAnyChecked() {
    for (let i = 0; i < roles.length; i++) {  //delete nem torol rendesen ezert kell a -1
        if (document.getElementById('check' + roles[i].id)?.checked) {
            return true;
        }
    }
    return false;
}

function success() {
    document.getElementById("button").disabled = !(document.getElementById("uname").value !== ""
        && document.getElementById("psw1").value.length > 3
        && document.getElementById("psw2").value.length > 3
        && document.getElementById("fname").value !== ""
        && isAnyChecked());
}