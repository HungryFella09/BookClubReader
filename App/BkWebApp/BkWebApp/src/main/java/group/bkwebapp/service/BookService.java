package group.bkwebapp.service;


import group.bkwebapp.domain.BuddyRead;
import group.bkwebapp.domain.User;
import group.bkwebapp.domain.dtos.BuddyReadDto;
import group.bkwebapp.domain.dtos.UserDto;
import group.bkwebapp.domain.requestsNresponses.BookSaveRequest;
import group.bkwebapp.domain.requestsNresponses.JoinBuddyReadRequest;
import group.bkwebapp.exception.RepositoryException;
import group.bkwebapp.repository.BookRepository;
import group.bkwebapp.repository.UserRepository;
import group.bkwebapp.utils.CodeGenerator;
import group.bkwebapp.utils.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookService(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public UserDto logIn(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            password = PasswordEncryptor.encryptPassword(password);
            if (user.get().getPassword().equals(password)) {
                return new UserDto(user.get().getId(), user.get().getUsername(), user.get().getEmail());
            }
            throw new RepositoryException("incorrect password");
        }
        throw new RepositoryException("user not found");
    }

    public void addNewUser(User user) {
        user.setPassword(PasswordEncryptor.encryptPassword(user.getPassword()));
        userRepository.save(user);
        //System.out.println(user);
    }

    public BuddyReadDto addNewBook(BookSaveRequest book) {
        System.out.println("addNewBook");

        BuddyRead bk = new BuddyRead();
        if (userRepository.findById(book.getUser()).isEmpty()) {
            throw new RepositoryException("user not found");
        }

        User admin = userRepository.findById(book.getUser()).get();
        bk.setAdmin(admin);
        bk.setBookName(book.getBookName());

        boolean ok = false;
        while (!ok) {
            ok = true;
            bk.setKey(CodeGenerator.generateCode(9));
            for (BuddyRead bd : bookRepository.findAll()) {
                if (bd.getKey().equals(bk.getKey())) {
                    ok = false;
                    break;
                }
            }
        }
        bk.addMember(admin);

        bookRepository.save(bk);
        Long id = bk.getId();

        MultipartFile file = book.getFile();

        try {
            file.transferTo(Path.of("D:\\school\\homework\\ISS\\BookClubReader\\App\\BkWebApp\\BkWebApp\\src\\main\\resources\\books\\" + id + ".epub"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new BuddyReadDto(bk.getId(), bk.getBookName(), bk.getKey(), bk.getAdmin().getUsername());
    }

    public void joinBuddyRead(JoinBuddyReadRequest bookSaveRequest) {
        long user_id = bookSaveRequest.getUser();
        String key = bookSaveRequest.getKey();

        User user = userRepository.findById(user_id).get();
        Optional<BuddyRead> buddyRead = bookRepository.findBuddyReadByKey(key);
        if (buddyRead.isPresent()) {
            BuddyRead bk = buddyRead.get();
            for (User u: bk.getMembers()) {
                if (u.getId().equals(user.getId())) {
                    throw new RepositoryException("user already a member");
                }
            }
            bk.addMember(user);
            bookRepository.save(bk);
            return;
        }
        throw new RepositoryException("buddy read not found");
    }

    public List<BuddyRead> findAllBuddyReadsOfUser(long user_id) {
        User user = userRepository.findById(user_id).get();
        return user.getBuddyReads().stream().toList();
    }
}
