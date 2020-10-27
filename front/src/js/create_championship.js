document.addEventListener("DOMContentLoaded", function() {
    fillFriendList()
});

var friendsToInvite = [];

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
                return false;
            }
        }
    });
}

function inviteFriendsToChampionship(champId, wallet, friendName) {
    $.ajax({
        async: false,
        url: 'http://bbsn-server.eu:8080/championship/addPlayer?championshipId=' + champId + '&wallet=' + wallet + '&username=' + friendName,
        type: 'POST',
        contentType: "application/json",
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', sessionStorage.getItem("authorization"));},
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        complete: function (result, status) {
            if (status === "success") {
                console.log("successfully add " + friendName + " to championship");
            } else {
                console.log("cannot add " + friendName + " to championship");
                return false;
            }
        }
    });
}

function createChampionship() {
    $.ajax({
        url: 'http://bbsn-server.eu:8080/championship',
        type: 'POST',
        contentType: "application/json",
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', sessionStorage.getItem("authorization"));},
        data: JSON.stringify({wallet: $('#startingWallet').val(),
            endDate: $('#endDate').val()}),
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        complete: function (result, status) {
            if (status === "success") {
                console.log("success");
                var json = JSON.parse(result.responseText);
                var champId = json.id;
                var wallet = json.wallet;
                sessionStorage.setItem("champId", champId);
                for (var i = 0; i < friendsToInvite.length; i++) {
                    inviteFriendsToChampionship(champId, wallet, friendsToInvite[i]);
                }
                friendsToInvite = [];
                self.location = "../html/choose_drivers.html";
            } else {
                console.log("create championship fails");
                return false;
            }
        }
    });
}

function invitePeople(id) {
    id = id.replace("InviteButton", "friendUsername");
    var friendName = document.getElementById(id).textContent;
    friendsToInvite.push(friendName);
}

function addFriendToList(friend, list) {
    if( typeof addFriendToList.counter == 'undefined' ) {
        addFriendToList.counter = 0;
    }

    var item = document.createElement('li');
    item.className = 'w3-bar';

    var button = document.createElement('span');
    button.type = 'button';
    button.className = 'w3-bar-item w3-button w3-white w3-xlarge w3-right';
    button.textContent = 'Inviter';
    button.id = "InviteButton" + addFriendToList.counter.toString();
    button.addEventListener("click", function(){
        invitePeople(this.id);
        this.textContent = "OK";
    }, false);

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
    username.id = "friendUsername" + addFriendToList.counter.toString();
    username.textContent = friend.username;

    userDiv.appendChild(userInfo);
    userDiv.appendChild(document.createElement('br'));
    userDiv.appendChild(username);

    item.appendChild(button);
    item.appendChild(img);
    item.appendChild(userDiv);
    list.appendChild(item);
    addFriendToList.counter++;
}