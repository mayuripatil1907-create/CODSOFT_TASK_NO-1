document.getElementById("loginForm").addEventListener("submit", function(event) {

    event.preventDefault();

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let role = document.getElementById("role").value;

    if (username === "admin" && password === "admin123" && role === "ADMIN") {

        document.getElementById("message").style.color = "green";
        document.getElementById("message").innerText = "Login successful!";

        window.location.href = "dashboard.html";

        } else {

        document.getElementById("message").style.color = "red";
        document.getElementById("message").innerText = "Invalid username or password.";

    }

});