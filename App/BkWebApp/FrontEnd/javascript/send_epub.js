


function send(){
    alert("cv")
    var input = document.getElementById("epub_file")
    alert(input)

    var data = new FormData()
    data.append('file', input.files[0])
    // data.append('user', 'hubot')

    fetch("http://localhost:8080/api/user_controller/send_file", {
        method: 'POST',
        body: data
    })

}

 	//application/epub+zip