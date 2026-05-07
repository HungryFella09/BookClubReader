


function sign_up(){
    var myusername = document.getElementById("input_username").value;
    var mypassword = document.getElementById("input_password").value;
    var myretypedpassword = document.getElementById("input_retyped_password").value;
    var myemail = document.getElementById("input_email").value;


    if(myusername == ""){
        document.getElementById("input_username").style.borderColor = "red";
        return;
    }
    if(mypassword == ""){
        document.getElementById("input_password").style.borderColor = "red";
        return;
    }
    if(myemail == ""){
        document.getElementById("input_email").style.borderColor = "red";
        return;
    }
    if(myretypedpassword == ""){
        document.getElementById("input_retyped_password").style.borderColor = "red";
        return;
    }

    if(mypassword != myretypedpassword){
        document.getElementById("input_password").style.borderColor= "red";
        document.getElementById("errorLabel").innerHTML="Retype your password";
        return
    }

    fetch("http://localhost:8080/api/user_controller/sign_up", {
    method: "POST",
    body: JSON.stringify({
        username: myusername,
        password: mypassword,
        email: myemail
    }),
    headers: {
        "Content-type": "application/json; charset=UTF-8"
    }
    })
    .then(response => {
        if (response.ok) {window.location.href = "index.html"};
        return response.json().then(response => {throw new Error(response.message)})
    })
    .catch((message) => {
        document.getElementById("errorLabel").innerHTML=message;
    })

}