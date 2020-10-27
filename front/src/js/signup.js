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

function facebookLogin() {
    FB.login(function(response) {
        if (response.authResponse) {
            FB.api('/me?locale=en_US&fields=name,email,first_name,last_name', function(response) {
                document.getElementById("registerFirstName").value = response.first_name;
                document.getElementById("registerLastName").value = response.last_name;
                document.getElementById("registerUsername").value = response.email;
                document.getElementById("registerPicture").value = "https://graph.facebook.com/" + response.id  + "/picture?type=normal";
            });
        } else {
            console.log('User cancelled login or did not fully authorize.');
        }
    }, {scope: 'email'});
}

function register() {
    if (document.getElementById("registerPassword").value === document.getElementById("registerConfirmPassword").value && document.getElementById("registerPassword").value.length >= 5)
        send_registration();
    else {
        alert("Merci de vérifier votre mot de passe");
        return false;
    }
}

function send_registration() {
    $.ajax({
        url: 'http://bbsn-server.eu:8080/users/sign-up',
        type: 'POST',
        data: JSON.stringify({username: $('#registerUsername').val(),
            password: $('#registerPassword').val() ,
        firstName: $('#registerFirstName').val(), lastName: $('#registerLastName').val(),
        picturePath: $('#registerPicture').val(), phoneNumber: $('#registerPhoneNumber').val()}),
        contentType: "application/json",
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        complete: function (result, status) {
            if (status === "success") {
                alert ("Inscription réussie");
                self.location = "login.html";
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