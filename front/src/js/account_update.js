document.addEventListener("DOMContentLoaded", function() {
    fillFriendList();
    loadInfo();
});

function fillFriendList() {
    $.ajax({
        url: 'http://bbsn-server.eu:8080/users/friend?username=' + sessionStorage.getItem("username"),
        type: 'GET',
        contentType: "application/json",
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', sessionStorage.getItem("authorization"));},
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        complete: function (result, status) {
            if (status === "success") {
                var list = document.getElementById("friendList");
                var array = JSON.parse(result.responseText);
                array.forEach(function(element) {
                    addFriendToList(element.friend, list);
                });
            } else {
                console.log(sessionStorage.getItem("username"));
                console.log(sessionStorage.getItem("authorization"));
                return false;
            }
        }
    });
}

function addFriendToList(friend, list) {
    var item = document.createElement('li');
    item.className = 'w3-bar';

    var img = document.createElement('img');
    img.className = 'w3-bar-item w3-circle w3-hide-small';
    img.style = 'width:85px';
    img.src = (friend.picturePath != null ? friend.picturePath : "https://www.w3schools.com/howto/img_avatar.png");

    var userDiv = document.createElement('div');
    userDiv.className = 'w3-bar-item';

    var userInfo = document.createElement('span');
    userInfo.className = 'w3-large';
    userInfo.textContent = (friend.firstName != null ? friend.firstName : "") + " " + (friend.lastName != null ? friend.lastName : "");

    var username = document.createElement('span');
    username.textContent = friend.username;

    userDiv.appendChild(userInfo);
    userDiv.appendChild(document.createElement('br'));
    userDiv.appendChild(username);

    item.appendChild(img);
    item.appendChild(userDiv);
    list.appendChild(item);
}

function update() {
    if (document.getElementById("registerPassword").value === ""|| (document.getElementById("registerPassword").value === document.getElementById("registerConfirmPassword").value && document.getElementById("registerPassword").value.length >= 5))
        send_update();
    else {
        alert("Merci de vérifier votre mot de passe");
        return false;
    }
}

function send_update() {
    $.ajax({
        url: 'http://bbsn-server.eu:8080/users',
        type: 'PUT',
        data: JSON.stringify({username: sessionStorage.getItem("username"),
            password: $('#registerPassword').val() ,
            firstName: $('#registerFirstName').val(), lastName: $('#registerLastName').val(),
            picturePath: $('#registerPicture').val(), phoneNumber: $('#registerPhoneNumber').val()}),
        contentType: "application/json",
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', sessionStorage.getItem("authorization"));},
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        complete: function (result, status) {
            if (status === "success") {
                alert ("Informations mises à jour");
            } else {
                var error = "";
                if (result.responseText !== "") {
                    var respond = JSON.parse(result.responseText);
                    for (var err in respond.errors) {
                        error += respond.errors[err].defaultMessage + '\n';
                    }
                } else
                    error = "Un compte avec cet email existe déjà";
                alert(error);
                return false;
            }
        }
    });
}

function loadInfo() {
    $.ajax({
        url: 'http://bbsn-server.eu:8080/users?username=' + sessionStorage.getItem("username"),
        type: 'GET',
        contentType: "application/json",
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', sessionStorage.getItem("authorization"));},
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        complete: function (result, status) {
            if (status === "success") {
                var json = JSON.parse(result.responseText);
                document.getElementById("registerFirstName").value = json.firstName;
                document.getElementById("registerLastName").value = json.lastName;
                document.getElementById("registerPhoneNumber").value = json.phoneNumber;
                document.getElementById("registerPicture").value = json.picturePath;
            } else {
                return false;
            }
        }
    });
}

function addFriend() {
    $.ajax({
        url: 'http://bbsn-server.eu:8080/users/friend/add?friendName=' + $('#friendName').val(),
        type: 'POST',
        contentType: "application/json",
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', sessionStorage.getItem("authorization"));},
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        complete: function (result, status) {
            if (status === "success") {
                fillFriendList();
            } else {
                return false;
            }
        }
    });
}