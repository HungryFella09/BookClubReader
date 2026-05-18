var user = JSON.parse(localStorage.getItem('mainUser'));
var book = localStorage.getItem('selectedBook');
var currentComments;


function get_book_chapter(chapter){



    fetch("http://localhost:8080/api/buddy_read_controller/chapter", {
        method: "POST",
        body: JSON.stringify({
            chapterId: chapter,
            bookId: book,
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    })
    .then(response => {
        if (response.ok) return response.json();
        return response.json().then(response => {throw new Error(response.message)})
    })
    .then((chap) => {
                //console.log(AAAAAAAAAAAAAAAAAAA);
                // console.log(buddyReads);
                load_chapter(chap);
                get_comments(chapter);
            })
    .catch((message) => {
        
    })
}

function get_comments_of_paragraph(paragraphNumber){
    console.log(paragraphNumber);
    var comm = [];
    currentComments.forEach(elem=>{
        console.log(elem.paragraphNumber);
        if(elem.paragraphNumber == paragraphNumber){
            comm.push(elem);
        }
    });
    return comm;
}

function get_comments(chapterNumber){
    fetch("http://localhost:8080/api/comments/" + book + "/"+chapterNumber, {
        method: "GET",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    })
    .then(response => {
        if (response.ok) return response.json();
        return response.json().then(response => {throw new Error(response.message)})
    })
    .then((comments) => {
                console.log(comments);
                load_comments(comments);
                currentComments = comments;
            })
    .catch((message) => {
        
    })
}



function load_chapter(chapter){
    
    var div = $("#book_background").html("");
    var i=0;

    chapter.chapter.forEach(element => {
        var a = i;
        var tog = 
            $("<p></p>")
            .html(element)
            .attr("id", ""+i)
            .on("click", function(e){on_click_comment(a);})
        div.append(tog);    
        i++;    
    });

}


function load_comments(comments){
    comments.forEach(elem=>{
        $("#"+elem.paragraphNumber).css('background-color', 'pink');
    })
    
}

function on_click_comment(id){
    var comments = get_comments_of_paragraph(id);
    $("#comments").css("display", "block");
    $("#paragraph_id").html(id);

    var div = $("#comments_container").html("");
    for (const element of comments) {
        console.log(element.message);
        var tog = 
            $("<p></p>")
            .html(element.username + ": " + element.message)
            .attr("id", "c"+element.id);
        if(element.userId == user.id){
            tog.on("click", function(e){set_up_update(element);})
        }    
        
        div.append(tog);    
    }

    
}

function on_add_comment(){
    var myparagraphNumber = $("#paragraph_id").html();
    var mymessage = $("#input_comment").val();
    $("#input_comment").val("");
    var myuserId = user.id;
    var chapterNumber = document.getElementById("select_chapter").value;
    var bookId = book;


    fetch("http://localhost:8080/api/comments", {
        method: "POST",
        body: JSON.stringify({
            message: mymessage,
            userId: myuserId,
            buddyReadId: bookId,
            paragraphNumber: myparagraphNumber,
            pageNumber: chapterNumber
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    })
    .then(response => {
        if (response.ok) return response.json();
        return response.json().then(response => {throw new Error(response.message)})
    })
    .then((comment) => {
                var div = $("#comments_container");
                var tog = 
                    $("<p></p>")
                    .html(comment.username + ": " + comment.message)
                    .attr("id", "c"+comment.id)
                
                if(comment.userId == user.id){
                    tog.on("click", function(e){set_up_update(comment);})
                }
                div.append(tog);
            })
    .catch((message) => {
        
    })
}

function set_up_update(comment){
    console.log(comment)
    $("#update_comment").css("display", "block");
    $("#update_comment").on("click", function(e){on_update(comment);})

    $("#delete_comment").css("display", "block");
    $("#delete_comment").on("click", function(e){on_delete(comment.id);})

    $("#input_comment").val(comment.message);
}

function on_update(comment){
    fetch("http://localhost:8080/api/comments", {
        method: "PUT",
        body: JSON.stringify({
            id: comment.id,
            message: $("#input_comment").val(),
            userId: comment.userId,
            buddyReadId: comment.buddyReadId,
            paragraphNumber: comment.paragraphNumber,
            pageNumber: comment.pageNumber,
            username: comment.username
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    })
    .then(response => {
        if (response.ok) return response.json();
        return response.json().then(response => {throw new Error(response.message)})
    })
    .then((comment) => {
                $("#c"+comment.id).html(comment.username + ": " + comment.message);
            })
    .catch((message) => {
        
    })


    $("#update_comment").css("display", "none");
    $("#delete_comment").css("display", "none");
    $("#input_comment").val("");
}


function on_delete(commentId){
    $("#update_comment").css("display", "none");
    $("#delete_comment").css("display", "none");
    $("#input_comment").val("");


    fetch("http://localhost:8080/api/comments/"+commentId, {
        method: "DELETE",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    })
    .then(response => {
        if (response.ok) {$("#c"+commentId).remove();}
        return response.json().then(response => {throw new Error(response.message)})
    })
    .catch((message) => {
        
    })
}


function number_of_chapters(){
    fetch("http://localhost:8080/api/buddy_read_controller/nrChapters", {
        method: "POST",
        body: JSON.stringify({
            bookId: book,
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    })
    .then(response => {
        if (response.ok) return response.json();
        return response.json().then(response => {throw new Error(response.message)})
    })
    .then((chapter) => {
                load_chapter_numbers(chapter.nrOfChapters);
            })
    .catch((message) => {
        
    })
}



function load_chapter_numbers(nr_chapters){

    var select = document.getElementById("select_chapter");

    select.addEventListener("change", ()=>{
        get_book_chapter(select.value);
    });

    for(i=0; i<nr_chapters; i++){
        const newOption = new Option("Chapter "+i, i); 
        select.add(newOption);
    }
}


function back(){
    window.location.href = "main_page.html";
}


number_of_chapters();


get_book_chapter(0);
get_comments(0);