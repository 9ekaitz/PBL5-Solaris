const CSRF_TOKEN = document.querySelector("meta[name='_csrf']").content;
const CSRF_HEADER = document.querySelector("meta[name='_csrf_header']").content;

let cartToggleExt;
let cartList;
let cartTotal;

const loadShop = async () => {
    cartToggleExt = document.getElementById("cart-toggle-ext");
    if (cartToggleExt) {
        const quantityLabel = document.querySelector(".single-product .product__quantity");

        const addBtn = document.querySelector(".add-to-cart");
        if (addBtn) addBtn.addEventListener('click', () => {
            addProduct(parseInt(addBtn.getAttribute("data-target")), parseInt(quantityLabel.textContent));
        });
        
        const sumBtn = document.querySelector(".single-product .controls__sum");
        if (sumBtn) sumBtn.addEventListener('click', () => {
            let quantity = parseInt(quantityLabel.textContent) + 1;
            if(quantity < 1) quantity = 1;
            quantityLabel.textContent = quantity;
        });
        
        const subBtn = document.querySelector(".single-product .controls__substract");
        if (subBtn) subBtn.addEventListener('click', () => {
            let quantity = parseInt(quantityLabel.textContent) - 1;
            if(quantity < 1) quantity = 1;
            quantityLabel.textContent = quantity;
        });

        document.querySelectorAll(".cart-toggle, #header-mobile-cart").forEach(t => t.addEventListener('click', e => {
            e.preventDefault();
            toggleShoppingCart();
        }));
        await loadShoppingCart();
    }
}

window.addEventListener("DOMContentLoaded", () => {
    cartList = document.querySelector("#shopping-cart .product-list");
    cartTotal = document.querySelector("#cart-total");
    document.querySelectorAll(".filter-option-title").forEach(e => e.addEventListener('click', () => e.classList.toggle("show")));
    loadShop();
});

const toggleShoppingCart = () => {
    document.querySelectorAll("#shopping-cart, #cart-toggle-ext, body").forEach(e => e.classList.toggle("hide"));
}

const loadShoppingCart = async () => {
    const response = await fetch("/shop-cart", {
        method: 'GET'
    });
    const result = await response.json();
    if (!response.ok) showError();
    else showShoppingCart(result);
}

const showShoppingCart = cart => {
    cartList.textContent = '';
    cart.products.forEach(product => showCartProduct(product));
    cartTotal.textContent = `${formatPrice(cart.totalPrice)} €`;
}

const showCartProduct = product => {
    const container = document.createElement('div');
    container.id = `product-${product.id}`;
    container.classList.add("product");
    container.setAttribute('data-product-id', product.id);

    const imgWrap = document.createElement('div');
    imgWrap.classList.add("img-wrap");
    imgWrap.style.backgroundImage = `url(${product.imagePath})`;
    container.appendChild(imgWrap);

    const title = document.createElement('h3');
    title.textContent = product.name;
    container.appendChild(title);

    const price = document.createElement('p');
    price.classList.add("product__totalCost");
    price.textContent = formatPrice(product.totalPrice) + ' €';
    container.appendChild(price);

    const menu = document.createElement('div');
    menu.classList.add("controls");

    const remove = document.createElement('span');
    remove.classList.add("controls__remove", "btn");
    remove.textContent = 'Eliminar producto';
    remove.addEventListener('click', () => removeProduct(product.id));
    menu.appendChild(remove);

    const quantityWrap = document.createElement('div');
    quantityWrap.classList.add("controls__quantity-wrap");

    const subs = document.createElement('span');
    subs.classList.add("controls__substract", "btn");
    subs.textContent = '-';
    subs.addEventListener('click', () => subsProduct(product.id));
    quantityWrap.appendChild(subs);

    const quantity = document.createElement('span');
    quantity.classList.add("product__quantity");
    quantity.textContent = product.quantity;
    quantityWrap.appendChild(quantity);

    const sum = document.createElement('span');
    sum.classList.add("controls__sum", "btn");
    sum.textContent = '+';
    sum.addEventListener('click', () => sumProduct(product.id));
    quantityWrap.appendChild(sum);

    menu.appendChild(quantityWrap);

    container.appendChild(menu);

    cartList.appendChild(container);
}

const subsProduct = async id => {
    const currentQuantity = parseInt(document.querySelector(`#product-${id} .product__quantity`).textContent);
    const response = await fetch("/shop-cart/update", {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'credentials': 'same-origin',
            [CSRF_HEADER]: CSRF_TOKEN
        },
        body: JSON.stringify({
            productId: id,
            quantity: currentQuantity - 1
        })
    });
    const result = await response.json();
    if (!response.ok) showError();
    else if (currentQuantity - 1 <= 0) removeItem(id, result);
    else updateItem(result, id);
}

const sumProduct = async id => {
    const currentQuantity = parseInt(document.querySelector(`#product-${id} .product__quantity`).textContent);
    const response = await fetch("/shop-cart/update", {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'credentials': 'same-origin',
            [CSRF_HEADER]: CSRF_TOKEN
        },
        body: JSON.stringify({
            productId: id,
            quantity: currentQuantity + 1
        })
    });
    const result = await response.json();
    if (!response.ok) showError();
    else updateItem(result, id);
}

const removeProduct = async id => {
    const response = await fetch("/shop-cart/remove", {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'credentials': 'same-origin',
            [CSRF_HEADER]: CSRF_TOKEN
        },
        body: JSON.stringify({
            productId: id
        })
    });
    const result = await response.json();
    if (!response.ok) showError();
    else removeItem(id, result);
}

const addProduct = async (id, quantity) => {
    const response = await fetch("/shop-cart/add", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'credentials': 'same-origin',
            [CSRF_HEADER]: CSRF_TOKEN
        },
        body: JSON.stringify({
            productId: id,
            quantity: quantity
        })
    });
    const result = await response.json();
    if (!response.ok) showError();
    else updateItem(result, id);
}

const removeItem = (id, cart) => {
    document.querySelector(`#product-${id}`).remove();
    updateCartTotalPrice(cart);
}

const updateItem = (cart, id) => {
    const cp = cart.products.find(e => e.id == id);
    const product = document.querySelector(`#product-${id}`);
    if (product) {
        product.querySelector('.product__quantity').textContent = cp.quantity;
        product.querySelector('.product__totalCost').textContent = `${formatPrice(cp.totalPrice)} €`;
    } else {
        showCartProduct(cp);
    }
    updateCartTotalPrice(cart);
}

const updateCartTotalPrice = cart => {
    document.querySelector('#cart-total').textContent = `${formatPrice(cart.totalPrice)} €`
}

const showError = error => {
    console.error(error);
}

const formatPrice = num => {
    return num.toFixed(2).replace(".", ",");
}
