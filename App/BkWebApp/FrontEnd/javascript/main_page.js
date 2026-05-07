


function on_add_buddyRead(){
    window.location.href = "add_or_join_buddy_read.html";
}


var user = JSON.parse(localStorage.getItem('mainUser'));
var username = user.username
document.getElementById("accountIcon").innerHTML = user.username
