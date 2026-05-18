var user = JSON.parse(localStorage.getItem('mainUser'));


function get_all_buddy_reads(){

    var user_id = user.id;
    var user_name = user.username;
    var user_email = user.email;

    fetch("http://localhost:8080/api/buddy_read_controller/buddyreads_of_user", {
        method: "POST",
        body: JSON.stringify({
            id: user_id,
            username: user_name,
            email: user_email,
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    })
    .then(response => {
        if (response.ok) return response.json();
        return response.json().then(response => {throw new Error(response.message)})
    })
    .then((buddyReads) => {
                // alert(AAAAAAAAAAAAAAAAAAA);
                // console.log(buddyReads);
                load_buddyreads(buddyReads);
            })
    .catch((message) => {
        
    })
}


get_all_buddy_reads();


function load_buddyreads(buddyReads){
    var list = buddyReads.buddyReadsOfUser

    var div = $("#list_of_buddyreads");

    list.forEach(buddyRead => {
        var bkId = buddyRead.id;
        //alert(bkId);
        var bkName = buddyRead.bookName;
        var bkAdmin = buddyRead.adminName;

        var tog = 
            $("<div></div>")
            .on("click", function(e){on_buddyread(bkId);})
            .append($("<p></p>").html(bkName).addClass("bkTitle"))
            .append($("<p></p>").html("Admin: " + bkAdmin));

        div.append(tog);

        // $(obj).parent().next()
        //         .before($("<li></li>")
        //         .html(tog))
        //         .on("click", function(e){($(this).toggleClass("show"));});

        // $(obj).parent().remove();


    });
}

function on_buddyread(bk){
    //alert("aaaa");
    localStorage.setItem('selectedBook', bk);
    window.location.href = "book_view.html";
}