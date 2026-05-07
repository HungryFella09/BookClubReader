package group.bkwebapp.controllers;


import group.bkwebapp.domain.BuddyRead;
import group.bkwebapp.domain.User;
import group.bkwebapp.domain.dtos.BuddyReadDto;
import group.bkwebapp.domain.requestsNresponses.BookSaveRequest;
import group.bkwebapp.domain.requestsNresponses.JoinBuddyReadRequest;
import group.bkwebapp.domain.requestsNresponses.LogInRequest;
import group.bkwebapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/buddy_read_controller")
public class BuddyReadController {
    private final BookService bookService;

    @Autowired
    public BuddyReadController(BookService bookService){
        this.bookService = bookService;
    }


    @PostMapping(path = "/send_file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public BuddyReadDto saveBuddyRead(@ModelAttribute BookSaveRequest book) {
        return bookService.addNewBook(book);
    }

    @PostMapping("/join_buddy_read")
    public void joinBuddyRead(@RequestBody JoinBuddyReadRequest joinBuddyReadRequest) {
        bookService.joinBuddyRead(joinBuddyReadRequest);
    }

}
