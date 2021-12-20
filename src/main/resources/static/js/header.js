window.onload = function() {
    document.getElementById("search-computer-icon").addEventListener("click", showSearch, false);
    document.getElementById("search-mobile-icon").addEventListener("click", showSearch, false);
    document.getElementById("lang-computer-icon").addEventListener("click", showLang, false);
    document.getElementById("lang-mobile-icon").addEventListener("click", showLang, false);
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
    let lang_bar = document.getElementById("header-mobile-lang");
    if(lang_bar.classList.contains("show-lang")){
        lang_bar.classList.remove("show-lang");
    }
    lang_bar = document.getElementById("header-computer-lang");
    if(lang_bar.classList.contains("show-lang")){
        lang_bar.classList.remove("show-lang");
    }
}

function showMobileLang(){
    let lang_bar = document.getElementById("header-mobile-lang");
    if(lang_bar.classList.contains("show-lang")){
        lang_bar.classList.remove("show-lang");
    }else{
        lang_bar.classList.add("show-lang");
    }
}

function showComputerLang(){
    let lang_bar = document.getElementById("header-computer-lang");
    if(lang_bar.classList.contains("show-lang")){
        lang_bar.classList.remove("show-lang");
    }else{
        lang_bar.classList.add("show-lang");
    }
}

function removeSearch(){
    let search_bar = document.getElementById("search-mobile-bar");
    if(search_bar.classList.contains("show-search")){
        search_bar.classList.remove("show-search");
    }
    search_bar = document.getElementById("search-computer-bar");
    if(search_bar.classList.contains("show-search")){
        search_bar.classList.remove("show-search");
        document.getElementsByTagName("body")[0].classList.remove("search-open");
    }
}

function showComputerSearch(){
    let search_bar = document.getElementById("search-computer-bar");
    if(search_bar.classList.contains("show-search")){
        search_bar.classList.remove("show-search");
        document.getElementsByTagName("body")[0].classList.remove("search-open");
    }else{
        search_bar.classList.add("show-search");
        document.getElementById("search-computer-box").focus();
        document.getElementsByTagName("body")[0].classList.add("search-open");
    }
}

function showMobileSearch(){
    let search_bar = document.getElementById("search-mobile-bar");
    if(search_bar.classList.contains("show-search")){
        search_bar.classList.remove("show-search");
    }else{
        search_bar.classList.add("show-search");
        document.getElementById("search-mobile-box").focus();
    }
}