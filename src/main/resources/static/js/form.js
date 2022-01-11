window.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".password-toggle").forEach(t => {
        const passwordInput = t.querySelector(".input-field");
        const eyeIconShow = t.querySelector(".password-icon-show");
        const eyeIconHide = t.querySelector(".password-icon-hide");

        t.querySelector(".show-password").addEventListener('click', () => {
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
});