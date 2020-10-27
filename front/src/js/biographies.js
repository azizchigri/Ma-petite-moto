document.addEventListener("DOMContentLoaded", function() {
    fillDriverList();
});

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

function addDriversToList(driver, list) {

    var item = document.createElement('li');
    item.className = 'w3-bar';
    item.type = 'button';
    item.id = driver.id;
    item.addEventListener("click", function(){
        var a = document.getElementById("Content" + this.id);
        var frame = document.getElementById("inlineFrameExample");
        frame.src = a.urlData;
        frame.hidden = false;
        return true;
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
    userInfo.id = "Content" + driver.id;
    userInfo.urlData = driver.url;

    var username = document.createElement('span');
    username.textContent = driver.team;

    userDiv.appendChild(userInfo);
    userDiv.appendChild(document.createElement('br'));
    userDiv.appendChild(username);

    item.appendChild(img);
    item.appendChild(userDiv);
    list.appendChild(item);
}