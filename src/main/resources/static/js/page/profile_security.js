window.addEventListener("DOMContentLoaded", _ => {
    const passwordInput1 = document.getElementById("old_password_1");
    const passwordInput2 = document.getElementById("old_password_2");
    const passwordInput3 = document.getElementById("new_password");

    const eyeIconShow1 = document.getElementById("password-icon-show-1");
    const eyeIconHide1 = document.getElementById("password-icon-hide-1");

    const eyeIconShow2 = document.getElementById("password-icon-show-2");
    const eyeIconHide2 = document.getElementById("password-icon-hide-2");
    const eyeIconShow3 = document.getElementById("password-icon-show-3");
    const eyeIconHide3 = document.getElementById("password-icon-hide-3");

    document.getElementById("show-password-1").addEventListener("click", _ => {
        if (passwordInput1.type === "text") {
            passwordInput1.type = "password";
            eyeIconShow1.classList.add('hide');
            eyeIconHide1.classList.remove('hide');
        }
        else {
            passwordInput1.type = "text";
            eyeIconHide1.classList.add('hide');
            eyeIconShow1.classList.remove('hide');
        }
    });

    document.getElementById("show-password-2").addEventListener("click", _ => {
        if (passwordInput2.type === "text") {
            passwordInput2.type = "password";
            eyeIconShow2.classList.add('hide');
            eyeIconHide2.classList.remove('hide');
        }
        else {
            passwordInput2.type = "text";
            eyeIconHide2.classList.add('hide');
            eyeIconShow2.classList.remove('hide');
        }
    });

    document.getElementById("show-password-3").addEventListener("click", _ => {
        if (passwordInput3.type === "text") {
            passwordInput3.type = "password";
            eyeIconShow3.classList.add('hide');
            eyeIconHide3.classList.remove('hide');
        }
        else {
            passwordInput3.type = "text";
            eyeIconHide3.classList.add('hide');
            eyeIconShow3.classList.remove('hide');
        }
    });
});
