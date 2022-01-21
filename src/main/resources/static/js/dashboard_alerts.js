window.addEventListener("DOMContentLoaded", function () {
  document.querySelectorAll(".alert").forEach(function(item){
    setTimeout(removeElement.bind(item), 5000);
  });
  document.querySelectorAll(".alert a").forEach(function(item){
    item.addEventListener("click", removeElement.bind(item.parentElement));
  });
});

function removeElement() {
  this.parentNode.removeChild(this);
}