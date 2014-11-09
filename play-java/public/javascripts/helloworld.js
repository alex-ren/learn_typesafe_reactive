

window.onload = init;

function init() {
    var planet = document.getElementById("greenplanet");
    planet.innerHTML = "Red Alert2."
    
    var button = document.getElementById("addButton");
    button.onclick = handleButtonClick;
    

}

function handleButtonClick() {
    var textInput = document.getElementById("nameInput");
    var name = textInput.value;
    
    obj = {name: name}
    var json_obj = JSON.stringify(obj)
    
    
    var url = "http://localhost:8088/objapi"
    var request = new XMLHttpRequest();
    request.open("POST", url);
    request.setRequestHeader('Content-Type', 'application/json');
    request.onload = function() {
        if (request.status == 200) {
            // alert(request.responseText);
            var result = document.getElementById("result");
            result.innerHTML = request.responseText;
        }
    };

    request.send(json_obj);
}


 

