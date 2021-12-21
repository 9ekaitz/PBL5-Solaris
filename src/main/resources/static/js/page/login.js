window.addEventListener("DOMContentLoaded", _ => {
    const passwordInput = document.querySelector(".password input");
    const eyeIconShow = document.getElementById("password-icon-show");
    const eyeIconHide = document.getElementById("password-icon-hide");

    document.getElementById("show-password").addEventListener("click", _ => {
        if (passwordInput.type === "text") {
            passwordInput.type = "password";
            eyeIconShow.classList.add('hide');
            eyeIconHide.classList.remove('hide');
        }
        else {
            passwordInput.type = "text";
            eyeIconHide.classList.add('hide');
            eyeIconShow.classList.remove('hide');
        }
    });
});
