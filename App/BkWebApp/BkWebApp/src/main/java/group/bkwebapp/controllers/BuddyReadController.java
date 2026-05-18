package group.bkwebapp.controllers;


import group.bkwebapp.domain.BuddyRead;
import group.bkwebapp.domain.User;
import group.bkwebapp.domain.dtos.BuddyReadDto;
import group.bkwebapp.domain.dtos.UserDto;
import group.bkwebapp.domain.requestsNresponses.*;
import group.bkwebapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/buddyreads_of_user")
    public AllBooksOfUsersResponse buddyReadsOfUser(@RequestBody UserDto user) {
        return new AllBooksOfUsersResponse(bookService.returnBuddyReadsOfUser(user));
    }

    @PostMapping("/chapter")
    public Chapter getChapter(@RequestBody ChapterRequest chapterRequest){
        //System.out.println("in getChapter");
        return new Chapter(bookService.returnChapter(chapterRequest.getBookId(), chapterRequest.getChapterId()));
    }

    @PostMapping("/nrChapters")
    public NumberOfChaptersResponse getNumberOfChapters(@RequestBody NumberOfChaptersRequest numberOfChaptersRequest) {
        return new NumberOfChaptersResponse(bookService.getNumberOfChapters(numberOfChaptersRequest.getBookId()));
    }


}
