// $.ajax({
//     url: 'http://localhost:8080/api/v1/controllers',
//     method: 'GET',
//     success: function(response) {
//         const parsedData = JSON.parse(response);
//         document.getElementById("div").innerHTML = parsedData;
//         // Process the parsed data here
//     },
//     error: function(xhr, status, error) {
//         // Handle any errors
//     }
// });

fetch('http://localhost:8080/api/v1/controllers')
  .then(response => {
    if (response.ok) {
      return response.json(); // Parse the response data as JSON
    } else {
      throw new Error('API request failed');
    }
  })
  .then(data => {
    // Process the response data here
    console.log(data); // Example: Logging the data to the console
  })
  .catch(error => {
    // Handle any errors here
    console.error(error); // Example: Logging the error to the console
  });

function send_user(){
    var myusername = document.getElementById("input_username").value;
    var mypassword = document.getElementById("input_password").value;
    var myemail = document.getElementById("input_email").value;

    alert(JSON.stringify({
        username: myusername,
        password: mypassword,
        email: myemail
    }));

    fetch("http://localhost:8080/api/v1/controllers", {
    method: "POST",
    body: JSON.stringify({
        username: myusername,
        password: mypassword,
        email: myemail
    }),
    headers: {
        "Content-type": "application/json; charset=UTF-8"
    }
    });
    // .then((response) => response.json())
    // .then((json) => console.log(json));
}