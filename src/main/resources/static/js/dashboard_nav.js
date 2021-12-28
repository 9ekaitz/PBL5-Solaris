
window.onload = function() {
  document.getElementById("nav-expand").addEventListener("click", toggleNav, false);

  document.getElementsByTagName("nav")[0].style.width="3.6rem";
}

function toggleNav() {
  let nav = document.getElementsByTagName("nav")[0];
  if (nav.style.width != "15rem") nav.style.width="15rem";
  else nav.style.width="3.6rem"
  document.querySelector(".expand .logo").classList.toggle("rotate");
}