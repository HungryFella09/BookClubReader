package group.bkwebapp.service;


import group.bkwebapp.domain.User;
import group.bkwebapp.domain.requestsNresponses.BookSaveRequest;
import group.bkwebapp.exception.RepositoryException;
import group.bkwebapp.repository.UserRepository;
import group.bkwebapp.utils.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;


@Service
public class BookService {
    private final UserRepository userRepository;

    @Autowired
    public BookService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User logIn(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            password = PasswordEncryptor.encryptPassword(password);
            if (user.get().getPassword().equals(password)) {
                return user.get();
            }
            throw new RepositoryException("incorrect password");
        }
        throw new RepositoryException("user not found");
    }

    public User getUser(){
        String username = "petra";
        Optional<User> u = userRepository.findByUsername(username);
        if(u.isPresent()){
            return u.get();
        }
        throw new IllegalStateException("Username not found");
        //throw new RepositoryException("user not found");
    }

    public void addNewUser(User user) {
        user.setPassword(PasswordEncryptor.encryptPassword(user.getPassword()));
        userRepository.save(user);
        //System.out.println(user);
    }

    public void addNewBook(BookSaveRequest book) {
        MultipartFile file = book.getFile();
        System.out.println(file.getOriginalFilename());
        try {
            file.transferTo(Path.of("D:\\school\\homework\\ISS\\BookClubReader\\App\\BkWebApp\\BkWebApp\\src\\main\\resources\\books"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
