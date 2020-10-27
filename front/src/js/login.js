var attempt = 3;

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function validate() {
    $.ajax({
        url: 'http://bbsn-server.eu:8080/login',
        type: 'POST',
        data: JSON.stringify({username: $('#inputUsername').val(), password: $('#inputPassword').val()}),
        contentType: "application/json",
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        complete: function (result, status) {
            if (status === "success") {
                sessionStorage.setItem("username", $('#inputUsername').val());
                sessionStorage.setItem("authorization", result.getResponseHeader("authorization"));
                console.log(result.getAllResponseHeaders());
                if (document.getElementById("stayConnectedCheckBox").checked === true) {
                    setCookie("authorization", result.getResponseHeader("authorization"), 99);
                }
                console.log(result.getAllResponseHeaders());
                self.location = "../html/home.html";
            } else {
                attempt --;
                alert("Il vous reste "+attempt+" tentatives");
                if (attempt === 0) {
                    alert("Vous n'avez plus de tentatives disponibles, merci de réessayer plus tard");
                    document.getElementById("card").hidden = true;
                    return false;
                }
            }
        }
    });
}