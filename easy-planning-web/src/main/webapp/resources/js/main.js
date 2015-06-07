$(window).scroll(function(){
    console.log(window.pageYOffset);
    if(window.pageYOffset > 25){
        document.getElementById('navbar').className = "navbar navbar-default navbar-fixed-top menu-nav";
        //$("#navbar").
    }
    else{
        document.getElementById('navbar').className = "navbar navbar-default navbar-static-top menu-nav";
    }
});
function scrolling(){
    console.log(window.pageYOffset);
    /*
    if(window.pageYOffset > 25){
        document.getElementById('navbar').className = "navbar navbar-default navbar-fixed-top menu-nav";
        //$("#navbar").
    }
    else{
        document.getElementById('navbar').className = "navbar navbar-default navbar-static-top menu-nav";
    }
    */
}