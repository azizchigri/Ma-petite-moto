function getUserInfo() {
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
                document.getElementById("userAvatar").src = (json.picturePath != null ? json.picturePath : "https://www.w3schools.com/howto/img_avatar.png");
                document.getElementById("userAvatar2").src = (json.picturePath != null ? json.picturePath : "https://www.w3schools.com/howto/img_avatar.png");
            } else {
                return false;
            }
        }
    });
}

function goToCreateChampionship() {
    self.location = "../html/create_championship.html";
}

function goToSettings() {
    self.location = "../html/account_update.html"
}

function goToHome() {
    self.location = "../html/home.html"
}

function goToBiographies() {
    self.location = "../html/biographies.html"
}

function goToCurrentChampionships() {
    self.location = "../html/current_championships.html"
}

function goToResults() {
    self.location = "../html/official_results.html"
}