let nav;
const ADDRESS_CLASS = ".address-toggle";
const PAYMENT_CLASS = ".payment-toggle";

window.addEventListener("DOMContentLoaded", function() {
  document.getElementById("custom-address").addEventListener("click", toggleForm.bind(null, ADDRESS_CLASS), false);
  document.getElementById("back-to-address").addEventListener("click", function(evt) {
    document.getElementById("custom-address").selected = false;
    toggleAddressForm(".address-toggle");
  }, false);

  document.getElementById("custom-payment").addEventListener("click", toggleForm.bind(null, PAYMENT_CLASS), false);
  document.getElementById("back-to-payment").addEventListener("click", function(evt) {
    document.getElementById("custom-payment").selected = false;
    toggleForm(".payment-toggle");
  }, false);
});


function toggleForm(query) {
  document.querySelectorAll(query).forEach(i => i.classList.toggle("hide"))
}