let mobile_lang_bar;
let computer_lang_bar;

let mobile_search_bar;
let computer_search_bar;

window.addEventListener("DOMContentLoaded", function() {
    mobile_lang_bar = document.getElementById("header-mobile-lang");
    computer_lang_bar = document.getElementById("header-computer-lang");
    mobile_search_bar = document.getElementById("search-mobile-bar");
    computer_search_bar = document.getElementById("search-computer-bar");

    document.getElementById("search-computer-icon").addEventListener("click", showSearch, false);
    document.getElementById("search-mobile-icon").addEventListener("click", showSearch, false);
    document.getElementById("lang-computer-icon").addEventListener("click", showLang, false);
    document.getElementById("lang-mobile-icon").addEventListener("click", showLang, false);

    let langs = document.getElementsByClassName("locales");
    
    for(let i = 0; i < langs.length; i++){
        langs[i].addEventListener("click", changeLanguage, false);
    }
})

function changeLanguage(e){
    let locale = e.target.value;
    let url = window.location.href;
    console.log(url)
    let newUrl = url.replace(/[a-z]{2}-[a-z]{2}/, locale);
    window.location.href = newUrl;
}

function showSearch(){
    removeLang();
    showComputerSearch();
    showMobileSearch();
}

function showLang(){
    removeSearch();
    showComputerLang();
    showMobileLang();
}

function removeLang(){
    if(mobile_lang_bar.classList.contains("show-lang")){
        mobile_lang_bar.classList.remove("show-lang");
    }
    if(computer_lang_bar.classList.contains("show-lang")){
        computer_lang_bar.classList.remove("show-lang");
    }
}

function showMobileLang(){
    mobile_lang_bar.classList.toggle("show-lang");
}

function showComputerLang(){
    computer_lang_bar.classList.toggle("show-lang");
}

function removeSearch(){
    if(mobile_search_bar.classList.contains("show-search")){
        mobile_search_bar.classList.remove("show-search");
    }
    if(computer_search_bar.classList.contains("show-search")){
        computer_search_bar.classList.remove("show-search");
    }
}

function showComputerSearch(){
    computer_search_bar.classList.toggle("show-search");
    computer_search_bar.focus();
}

function showMobileSearch(){
    mobile_search_bar.classList.toggle("show-search");
    mobile_search_bar.focus();
}