function initChecklist(users, roles) {
    users.forEach(user => {
        const checkList = document.getElementById('listOf' + user.userName);
        checkList.getElementsByClassName('anchor')[0].onclick = function (evt) {
            if (checkList.classList.contains('visible'))
                checkList.classList.remove('visible');
            else
                checkList.classList.add('visible');
        }
    });
}
function selectUser(val) {
    window.location.href = '/admin/delete/' + val
}

function checkForOneChecked(userId) {
    for (let i = 0; i < roles.length; i++) {
        if (document.getElementById('check' + userId + roles[i].id).checked) {
            document.getElementById('saveButton' + userId).disabled = false;
            return;
        }
    }
    document.getElementById('saveButton' + userId).disabled = true;
}
