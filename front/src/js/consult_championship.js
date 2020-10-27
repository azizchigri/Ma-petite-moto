document.addEventListener("DOMContentLoaded", function() {
    fillChampionshipList();
});

function fillChampionshipList() {
    $.ajax({
        url: 'http://bbsn-server.eu:8080/championship',
        type: 'GET',
        contentType: "application/json",
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', sessionStorage.getItem("authorization"));},
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        complete: function (result, status) {
            if (status === "success") {
                var list = document.getElementById("championshipList");
                var array = JSON.parse(result.responseText);
                array.forEach(function(element) {
                    addChampionshipToList(element, list);
                });
            } else {
                return false;
            }
        }
    });
}

function selectChampionship(id) {
    var id_save = id;
    id = "Content" + id;
    var b = document.getElementById(id).min;
    if (parseInt(document.getElementById(id).value) < parseInt(document.getElementById(id).min))
        return false;
    var bet = document.getElementById(id).value;
    sendBetOnDriver(id_save, bet);
    return true;
}

function addChampionshipToList(driver, list) {
    if( typeof addChampionshipToList.counter == 'undefined' ) {
        addChampionshipToList.counter = 0;
    }

    var item = document.createElement('li');
    item.className = 'w3-bar';
    item.type = 'button';
    item.id = driver.id;
    item.addEventListener("click", function(){
        sessionStorage.setItem("champId", this.id);
        self.location = "../html/choose_drivers.html";
        return true;
    }, false);

    var userDiv = document.createElement('div');
    userDiv.className = 'w3-bar-item';
    var dateObject = new Date(Date.parse(driver.endDate.toString()));
    userDiv.textContent = "Championnat #" + driver.id + " | Fin le " + dateObject.toLocaleDateString();
    userDiv.id = "Content" + addChampionshipToList.counter.toString();

    item.appendChild(userDiv);
    list.appendChild(item);
    addChampionshipToList.counter++;
}