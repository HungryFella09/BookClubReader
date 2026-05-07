var user = JSON.parse(localStorage.getItem('mainUser'));


function send(){

    var book = document.getElementById("epub_file");
    var user_id = user.id;
    var title = document.getElementById("input_title").value;


    if(book.files.length == 0){
        book.style.borderColor = "red";
        return;
    }
    if(title == ""){
        document.getElementById("input_title").style.borderColor = "red";
        return;
    }


    var data = new FormData()
    data.append('file', book.files[0])
    data.append('user', user_id)
    data.append('bookName', title)

    fetch("http://localhost:8080/api/buddy_read_controller/send_file", {
        method: 'POST',
        body: data
    })
    .then(response => {
        if (response.ok) return response.json();
        return response.json().then(response => {throw new Error(response.message)})
    })
    .then((buddy_read) => {
                console.log(buddy_read);
                document.getElementById("generated_code").innerHTML = "Your BuddyRead was created successfully!<br>Copy this code and send it to others to let them join: " +  buddy_read.key;
            })
    .catch((message) => {
        document.getElementById("error_message").innerHTML=message;
    })

}

//application/epub+zip


function joinWCode(){
    var user_id = user.id;
    var mykey = document.getElementById("input_key").value;

    fetch("http://localhost:8080/api/buddy_read_controller/join_buddy_read", {
        method: "POST",
        body: JSON.stringify({
            user: user_id,
            key: mykey,
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    })
    .then(response => {
        if (response.ok) return document.getElementById("succesful_msg").innerHTML="Succesfully joined Buddy Read!";
        return response.json().then(response => {throw new Error(response.message)})
    })
    .then(() => {})
    .catch((message) => {
        document.getElementById("wrong_id").innerHTML=message;
    })

}