function goToLoginPage() {
    self.location = "/front/src/html/login.html";
}

function goToRegisterPage() {
    self.location = "/front/src/html/signup.html";
}

function returnToWelcome() {
    self.location = "/front/index.html";
}

function returnToRef(reference) {
    self.location = "/front/index.html" + "#" + reference;
}