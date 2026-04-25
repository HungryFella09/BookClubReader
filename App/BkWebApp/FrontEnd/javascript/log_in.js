


function log_in(){
    var myusername = document.getElementById("input_username").value;
    var mypassword = document.getElementById("input_password").value;

    fetch("http://localhost:8080/api/user_controller/log_in", {
        method: "POST",
        body: JSON.stringify({
            username: myusername,
            password: mypassword,
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    })
    .then(response => {
        if (response.ok) return response.json();
        return response.json().then(response => {throw new Error(response.message)})
    })
    .then((user) => {
                console.log(user);
                localStorage.setItem('mainUser', JSON.stringify(user));
                window.location.href = "main_page.html";
            })
    .catch((message) => {
        document.getElementById("errorLabel").innerHTML=message;
    })

    // .then((response) => {    
    //     if(!response.ok){
    //         return response.text().then((message) => {alert(message);});
    //     }    
    //     else{
    //         return response.json().then((user) => {
    //             console.log(user);
    //             localStorage.setItem('mainUser', JSON.stringify(user));
    //             window.location.href = "main_page.html";
    //         });
    //     }
    // });
    
}