document.getElementById("loginForm").addEventListener("submit", function(event) {

    // Fetch the login endpoint with the form data
    fetch("/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: document.getElementById("usernameInput").value,
            password: document.getElementById("passwordInput").value
        })
    })
        .then(function(response) {
            if (response.ok) {
                return response.text(); // Successful login
            } else {
                throw new Error("Login failed."); // Unsuccessful login
            }
        })
        .then(function(result) {
            if (result === "succes") {
                window.location.href = "/"; // Redirect to the dashboard on successful login
            } else {
                alert("Invalid username or password."); // Show an alert for unsuccessful login
            }
        })
        .catch(function(error) {
            console.error(error);
            alert("An error occurred during login."); // Show an alert for any other errors
        });
});