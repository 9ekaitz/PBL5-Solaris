let nav;

window.addEventListener("DOMContentLoaded", function () {
  document.getElementById("nav-expand").addEventListener("click", toggleNav, false);
  document.querySelectorAll(".nav-mobile-item").forEach(element => {
    element.addEventListener("click", changeView, false);
  });
  nav = document.querySelector("nav .nav-computer");
  if (window.location.pathname == "/install") {
    let params = new URL(window.location.href);
    switch (params.searchParams.get('view')) {
      case "completed": document.querySelector(".completed-installations").classList.add("current"); break;
      case "pending": document.querySelector(".available-installations").classList.add("current"); break;
      case "banner": 
      default: document.querySelector(".user-banner").classList.add("current"); break;
    }
  }
});

function toggleNav() {
  nav.classList.toggle("expand");
  document.querySelector(".expand .logo").classList.toggle("rotate");
}

function changeView(evt) {
  if (window.location.pathname == "/install") {
    evt.preventDefault();
    let container = document.querySelector("." + evt.currentTarget.id);
    if (!container.classList.contains("current")) {
      document.querySelector(".current").classList.remove("current");
      container.classList.add("current");
    }
  }

}