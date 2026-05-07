package group.bkwebapp.controllers;

import group.bkwebapp.domain.User;
import group.bkwebapp.domain.dtos.UserDto;
import group.bkwebapp.domain.requestsNresponses.BookSaveRequest;
import group.bkwebapp.domain.requestsNresponses.LogInRequest;
import group.bkwebapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/user_controller")
public class UserController {
    private final BookService bookService;

    @Autowired
    public UserController(BookService bookService){
        this.bookService = bookService;
    }


    @PostMapping("/log_in")
    public UserDto logInUser(@RequestBody LogInRequest loginRequest){
        return bookService.logIn(loginRequest.getUsername(),  loginRequest.getPassword());
    }

    @PostMapping("/sign_up")
    public void signUpUser(@RequestBody User user){
        bookService.addNewUser(user);
    }

    @PostMapping(path = "/send_file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void saveEmployee(@ModelAttribute BookSaveRequest book) {
        bookService.addNewBook(book);
    }
}