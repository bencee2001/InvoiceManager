var clients;
var csrf_name;
var csrf_token;

function ajaxInit(newClients, csrfName, csrfToken) {
    clients = newClients
    csrf_name = csrfName
    csrf_token = csrfToken
}
function viewInvoice(val) {
    window.location.href = '/invoice/select/'+val

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

function reloadTables() {
    ownAjax();
    if (clients !== null)
        clientAjax()
}

function ownAjax(){
    $.ajax({
        url: '/list/refreshOwn',
        type: 'GET',
        data:{
            csrf_name : csrf_token
        },
        success: function (data){
            deleteRows('ownRows')
            for(let i=0;i<data.length;i++){
                createRow(data[i],'own')
            }
        }
    })
}

function clientAjax(){
    $.ajax({
        url: '/list/refreshClient',
        type: 'GET',
        data:{
            csrf_name : csrf_token
        },
        success: function (data){
            deleteRows('clientRows')
            for(let i=0;i<data.length;i++){
                createRow(data[i],'client')
            }
        }
    })
}

function createRow(invoice,prefixName){
    const tr = document.createElement("tr");
    tr.className = prefixName+"Rows";
    const div = document.createElement("div");

    const td1 = document.createElement("td");
    td1.textContent = invoice.isNew ? "New" : "Opened";
    td1.setAttribute("onclick","viewInvoice("+invoice.id+")")
    tr.appendChild(td1)

    const td2 = document.createElement("td");
    td2.textContent = invoice.buyerName;
    td2.setAttribute("onclick","viewInvoice("+invoice.id+")")
    tr.appendChild(td2)

    const td3 = document.createElement("td");
    const iDate = new Date(invoice.issueDate.toString())
    const iTimezoneOffset = iDate.getTimezoneOffset();
    const iAdjustedDate = new Date(iDate.getTime()-iTimezoneOffset*60*1000).toISOString()
    td3.textContent = iAdjustedDate.split('T')[0];
    td3.setAttribute("onclick","viewInvoice("+invoice.id+")")
    tr.appendChild(td3);

    const td4 = document.createElement("td");
    const dDate = new Date(invoice.dueDate.toString())
    const dTimezoneOffset = dDate.getTimezoneOffset();
    const dAdjustedDate = new Date(dDate.getTime()-dTimezoneOffset*60*1000).toISOString()
    td4.textContent = dAdjustedDate.split('T')[0];
    td4.setAttribute("onclick","viewInvoice("+invoice.id+")")
    tr.appendChild(td4);

    const td5 = document.createElement("td");
    td5.textContent = invoice.comment;
    td5.setAttribute("onclick","viewInvoice("+invoice.id+")")
    tr.appendChild(td5);

    const td6 = document.createElement("td");
    td6.textContent = (invoice.price * invoice.itemNumber).toFixed(2);
    td6.setAttribute("onclick","viewInvoice("+invoice.id+")")
    tr.appendChild(td6);

    const td7 = document.createElement("td");
    td7.align="center"
    if(invoice.payment===null){
        const button = document.createElement("button");
        button.className="button"
        button.textContent="Pay"
        button.onclick = () => showPayPanel(invoice.id)
        td7.appendChild(button)
    }else{
        td7.textContent = invoice.payment.type;
        td7.setAttribute("onclick","viewInvoice("+invoice.id+")")
    }
    //td7.textContent = invoice.payment !== null ? 'hello' : 'bello'
    tr.appendChild(td7);

    const td8 = document.createElement("td");
    const form = document.createElement("form");
    form.method="get"
    form.action="/invoice/delete/"+invoice.id;
    const button = document.createElement("button");
    button.type="submit"
    button.className="button"
    button.textContent="X"
    form.appendChild(button);
    td8.appendChild(form);
    tr.appendChild(td8);



    document.getElementById(prefixName+"Body").appendChild(tr)
}

function deleteRows(RowClassName){
    const asd = Array.from(document.getElementsByClassName(RowClassName));
    asd.forEach(a=>{
        a.remove()
    })
}