let nav;

window.addEventListener("DOMContentLoaded", function() {
  document.getElementById("nav-expand").addEventListener("click", toggleNav, false);
  document.querySelectorAll(".nav-mobile-item").forEach(element => {
    element.addEventListener("click", changeView, false);  
  });
  nav = document.querySelector("nav .nav-computer");
});

function toggleNav() {
  nav.classList.toggle("expand");
  document.querySelector(".expand .logo").classList.toggle("rotate");
}

function changeView(evt) {
  let container = document.querySelector("."+evt.currentTarget.id);
  if (!container.classList.contains("current")) {
    document.querySelector(".current").classList.remove("current");
    container.classList.add("current");
  }
}