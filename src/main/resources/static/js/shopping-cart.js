const CSRF_TOKEN = document.querySelector("meta[name='_csrf']").content;
const CSRF_HEADER = document.querySelector("meta[name='_csrf_header']").content;

let cartToggleExt;

const loadShop = async () => {
    cartToggleExt = document.getElementById("cart-toggle-ext");
    if (cartToggleExt) {
        document.querySelectorAll(".cart-toggle").forEach(t => t.addEventListener('click', toggleShoppingCart));
        showShoppingCart(await loadShoppingCart());
    }
}

window.addEventListener("DOMContentLoaded", loadShop);

const toggleShoppingCart = () => {
    document.getElementById("shopping-cart").classList.toggle("hide");
    document.getElementById("cart-toggle-ext").classList.toggle("hide");
}

const loadShoppingCart = async () => {
    const res = await fetch("/shop-cart", {
        method: 'GET',
        headers: {
            CSRF_HEADER: CSRF_TOKEN
        }
    });
    return await res.json();
}

const showShoppingCart = cart => {
    console.log("🔥🔥🔥🔥🔥", cart);
    cart.forEach(product => {

    });
}