document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Previene la recarga de la página
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Simulación de validación
    if (username === "admin" && password === "1234") {
        // Redirigir a index.html
        window.location.href = "index.html";
    } else {
        alert("Usuario o contraseña incorrectos.");
    }
});