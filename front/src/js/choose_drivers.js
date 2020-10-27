document.addEventListener("DOMContentLoaded", function() {
    updateBetState();
});

var betState;
var driverCounter = 0;

function updateBetState() {
    $.ajax({
        url: 'http://bbsn-server.eu:8080/championship/bet?championshipId=' + sessionStorage.getItem("champId"),
        type: 'GET',
        contentType: "application/json",
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', sessionStorage.getItem("authorization"));},
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        complete: function (result, status) {
            if (status === "success") {
                if (result.responseText != null && result.responseText !== "") {
                    betState = JSON.parse(result.responseText);
                    jQuery.each(betState, function (key, value) {
                        if (value === sessionStorage.getItem("username"))
                            driverCounter++;
                    });
                } else {
                    self.location = "../html/my_team.html";
                }
                fillDriverList();
            } else {
                return false;
            }
        }
    });
}

function fillDriverList() {
    $.ajax({
        url: 'http://bbsn-server.eu:8080/championship/driver',
        type: 'GET',
        contentType: "application/json",
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', sessionStorage.getItem("authorization"));},
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        complete: function (result, status) {
            if (status === "success") {
                var list = document.getElementById("driverList");
                var array = JSON.parse(result.responseText);
                array.forEach(function(element) {
                    addDriversToList(element, list);
                });
            } else {
                return false;
            }
        }
    });
}

function sendBetOnDriver(driverId, price) {
    $.ajax({
        url: 'http://bbsn-server.eu:8080/championship/bet?championshipId=' + sessionStorage.getItem("champId") + '&driverId=' + driverId + '&price=' + price,
        type: 'POST',
        contentType: "application/json",
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', sessionStorage.getItem("authorization"));},
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        complete: function (result, status) {
            if (status === "success") {
                if (result.responseText != null) {
                    var array = JSON.parse(result.responseText);
                    betState = array;
                    fillDriverList();
                } else {
                    self.location = "../html/my_team.html";
                }

            } else {
                return false;
            }
        }
    });
}

function betOnDriver(id) {
    if (driverCounter >= 2)
        return false;
    var id_save = id;
    id = "betField" + id;
    var b = document.getElementById(id).min;
    if (parseInt(document.getElementById(id).value) < parseInt(document.getElementById(id).min))
        return false;
    var bet = document.getElementById(id).value;
    sendBetOnDriver(id_save, bet);
    return true;
}

function addDriversToList(driver, list) {

    var item = document.createElement('li');
    item.className = 'w3-bar';

    var button = document.createElement('span');
    button.type = 'button';
    button.className = 'w3-bar-item w3-button w3-white w3-xlarge w3-right';
    button.textContent = 'Miser';
    button.id = driver.sportRadarId;
    button.addEventListener("click", function(){
        if (betOnDriver(this.id))
            this.textContent = "OK";
        else
            return false;
        }, false);

    var img = document.createElement('img');
    img.className = 'w3-bar-item w3-circle w3-hide-small';
    img.style = 'width:85px';
    img.src = driver.pictureUrl;

    var userDiv = document.createElement('div');
    userDiv.className = 'w3-bar-item';

    var userInfo = document.createElement('span');
    userInfo.className = 'w3-large';
    userInfo.textContent = driver.name;

    var username = document.createElement('span');
    username.textContent = driver.team;

    var betField = document.createElement('input');
    betField.type = 'number';
    betField.min = driver.priceMin;
    betField.placeholder = "Mise (minimum = " + driver.priceMin + ")";
    betField.id = "betField" + driver.sportRadarId;

    userDiv.appendChild(userInfo);
    userDiv.appendChild(document.createElement('br'));
    userDiv.appendChild(username);

    if ((betState == null || betState[driver.sportRadarId] == null || betState[driver.sportRadarId] !== sessionStorage.getItem("username")) && driverCounter < 2) {
        item.appendChild(betField);
        item.appendChild(button);
    }
    item.appendChild(img);
    item.appendChild(userDiv);
    list.appendChild(item);
}