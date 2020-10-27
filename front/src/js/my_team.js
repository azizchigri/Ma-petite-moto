document.addEventListener("DOMContentLoaded", function() {
    getChampionshipInfo();
});

var championship;
var driver1;
var driver2;
var score;

function getChampionshipInfo() {
    $.ajax({
        url: 'http://bbsn-server.eu:8080/championship/findOne?championshipId=' + sessionStorage.getItem("champId"),
        type: 'GET',
        contentType: "application/json",
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', sessionStorage.getItem("authorization"));},
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        complete: function (result, status) {
            if (status === "success") {
                championship = JSON.parse(result.responseText);
                var list = document.getElementById("playerList");
                var array = championship.championshipPlayer;
                array.forEach(function(element) {
                    if (element.username === sessionStorage.getItem("username")) {
                        getDrivers(element.firstDriver, element.secondDriver);
                        score = element.score;
                    } else {
                        addPlayersToList(element, list);
                    }
                    setMyTeam();
                });
            } else {
                return false;
            }
        }
    });
}

function addPlayersToList(driver, list) {

    var item = document.createElement('li');
    item.className = 'w3-bar';

    var userDiv = document.createElement('div');
    userDiv.className = 'w3-bar-item';

    var userInfo = document.createElement('span');
    userInfo.className = 'w3-large';
    userInfo.textContent = driver.username;

    var username = document.createElement('span');
    username.textContent = "Score actuel: " + driver.score;

    userDiv.appendChild(userInfo);
    userDiv.appendChild(document.createElement('br'));
    userDiv.appendChild(username);

    item.appendChild(userDiv);
    list.appendChild(item);
}

function getDrivers(driver1str, driver2str) {
    $.ajax({
        async: false,
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
                var array = JSON.parse(result.responseText);
                array.forEach(function(element) {
                    if (element.sportRadarId === driver1str)
                        driver1 = element;
                    else if (element.sportRadarId === driver2str)
                        driver2 = element;
                });
            } else {
                return false;
            }
        }
    });
}

function setMyTeam() {
    document.getElementById("driver1photo").src = driver1.pictureUrl;
    document.getElementById("driver2photo").src = driver2.pictureUrl;
    document.getElementById("driver1name").textContent = driver1.name;
    document.getElementById("driver2name").textContent = driver2.name;
    document.getElementById("score").textContent = "Mon score actuel: " + score;
}