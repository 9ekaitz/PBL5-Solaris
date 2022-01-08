let nav;

window.addEventListener("DOMContentLoaded", function() {
  document.getElementById("nav-expand").addEventListener("click", toggleNav, false);
  document.querySelectorAll(".nav-mobile-item").forEach(element => {
    element.addEventListener("click", changeView, false);  
  });
  nav = document.querySelector("nav .nav-computer");
  nav.style.width="3.6rem";
});

function toggleNav() {
  if (nav.style.width != "16rem") nav.style.width="16rem";
  else nav.style.width="3.6rem"
  document.querySelector(".expand .logo").classList.toggle("rotate");
}

function changeView(evt) {
  if (evt.currentTarget != document.querySelector(".current")) {
    document.querySelector(".current").classList.remove("current");
    document.querySelector("."+evt.currentTarget.id).classList.add("current");
  }
}