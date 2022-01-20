const CSRF_TOKEN = document.querySelector("meta[name='_csrf']").content;
const CSRF_HEADER = document.querySelector("meta[name='_csrf_header']").content;

let cartToggleExt;
let cartList;
let cartTotal;

const loadShop = async () => {
    cartToggleExt = document.getElementById("cart-toggle-ext");
    if (cartToggleExt) {
        document.querySelectorAll(".cart-toggle").forEach(t => t.addEventListener('click', toggleShoppingCart));
        showShoppingCart(await loadShoppingCart());
    }
}

window.addEventListener("DOMContentLoaded", () => {
    cartList = document.querySelector("#shopping-cart .product-list");
    cartTotal = document.querySelector("#cart-total");
    loadShop();
});

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
    console.log(cart);
    cartList.textContent = '';
    cart.products.forEach(product => {
        const container = document.createElement('div');
        container.classList.add("product");
        container.setAttribute('data-product-id', product.id);

        const imgWrap = document.createElement('div');
        imgWrap.classList.add("img-wrap");

        const image = document.createElement('img');
        image.src = product.imagePath;
        imgWrap.appendChild(image);

        container.appendChild(imgWrap);

        const title = document.createElement('h3');
        title.textContent = product.name;
        container.appendChild(title);

        const price = document.createElement('p');
        price.textContent = product.totalPrice + ' €';
        container.appendChild(price);

        const menu = document.createElement('div');
        menu.classList.add("controls");
        container.appendChild(menu);

        cartList.appendChild(container);
    });

    cartTotal.textContent = cart.totalPrice + ' €';

}