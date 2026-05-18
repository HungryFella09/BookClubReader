package group.bkwebapp.controllers;

import group.bkwebapp.domain.Comment;
import group.bkwebapp.domain.dtos.UserDto;
import group.bkwebapp.domain.requestsNresponses.LogInRequest;
import group.bkwebapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/comments")
public class CommentController {
    private final BookService bookService;

    @Autowired
    public CommentController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/{buddyReadId}/{chapterNumber}")
    public List<Comment> getAllComments(@PathVariable long buddyReadId, @PathVariable int chapterNumber){
        return bookService.getCommentsOfPage(buddyReadId, chapterNumber);
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment){
        return bookService.saveComment(comment);
    }

    @PutMapping
    public Comment updateComment(@RequestBody Comment comment){
        return bookService.updateComment(comment);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable long commentId){
        bookService.deleteComment(commentId);
    }

}
