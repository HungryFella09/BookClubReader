package group.bkwebapp.service;


import group.bkwebapp.domain.BuddyRead;
import group.bkwebapp.domain.Comment;
import group.bkwebapp.domain.User;
import group.bkwebapp.domain.dtos.BuddyReadDto;
import group.bkwebapp.domain.dtos.UserDto;
import group.bkwebapp.domain.requestsNresponses.BookSaveRequest;
import group.bkwebapp.domain.requestsNresponses.JoinBuddyReadRequest;
import group.bkwebapp.exception.RepositoryException;
import group.bkwebapp.exception.ServiceException;
import group.bkwebapp.repository.BookRepository;
import group.bkwebapp.repository.CommentRepository;
import group.bkwebapp.repository.UserRepository;
import group.bkwebapp.utils.CodeGenerator;
import group.bkwebapp.utils.PasswordEncryptor;
import nl.siegmann.epublib.domain.*;
import nl.siegmann.epublib.epub.EpubReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialException;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public BookService(UserRepository userRepository, BookRepository bookRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
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

    public List<BuddyReadDto> returnBuddyReadsOfUser(UserDto user) {
        User fullUser = userRepository.findById(user.getId()).get();
        List<BuddyReadDto> dtos = new ArrayList<>();
        for (BuddyRead bk : fullUser.getBuddyReads()) {
            dtos.add(new BuddyReadDto(bk.getId(), bk.getBookName(), bk.getKey(), bk.getAdmin().getUsername()));
        }
        System.out.println(dtos.size());
        return dtos;
    }

    public List<String> returnChapter(long bookId, int chapterId) {
        EpubReader epubReader = new EpubReader();
        String bookPath = "D:\\school\\homework\\ISS\\BookClubReader\\App\\BkWebApp\\BkWebApp\\src\\main\\resources\\books\\" + bookId + ".epub";


        try {

            Book book = epubReader.readEpub(new FileInputStream(bookPath));

//            List<String> titles = book.getMetadata().getTitles();
//            System.out.println("book title:" + (titles.isEmpty() ? "book has no title" : titles.get(0)));
//            List<Author> authors = book.getMetadata().getAuthors();
//            System.out.println("book author:" + (authors.isEmpty() ? "book has no author" : authors.get(0)));

            List<String> mEntireBook = new ArrayList<>();
            Spine spine = new Spine(book.getTableOfContents());

            for (SpineReference bookSection : spine.getSpineReferences()) {
                StringBuilder chapter = new StringBuilder();

                Resource res = bookSection.getResource();
                try {
                    InputStream is = res.getInputStream();
                    BufferedReader r = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = r.readLine()) != null) {
//                        line = Html.fromHtml(line).toString();
//                        Log.i("Read it ", line);
                        chapter.append(line);

                    }
                } catch (IOException e) {
                }
                mEntireBook.add(chapter.toString());
            }

            String html = mEntireBook.get(chapterId);

//            System.out.println("------------------------------------------");
//            System.out.println(html);

            Document doc = Jsoup.parse(html);

            Elements paragraphElements = doc.select("p");

            List<String> extractedTexts = new ArrayList<>();
            for (Element paragraphElement : paragraphElements) {
                String extractedText = paragraphElement.text();
                extractedTexts.add(extractedText);
            }
//            for (String extractedText : extractedTexts) {
//                System.out.println(extractedText);
//            }
            return extractedTexts;

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServiceException("Book Could Not Be Found");
    }

    public int getNumberOfChapters(long bookId) {
        EpubReader epubReader = new EpubReader();
        String bookPath = "D:\\school\\homework\\ISS\\BookClubReader\\App\\BkWebApp\\BkWebApp\\src\\main\\resources\\books\\" + bookId + ".epub";

        try {

            Book book = epubReader.readEpub(new FileInputStream(bookPath));
            //List<String> mEntireBook = new ArrayList<>();
            Spine spine = new Spine(book.getTableOfContents());

            return spine.getSpineReferences().size();

//            for (SpineReference bookSection : spine.getSpineReferences()) {
//                StringBuilder chapter = new StringBuilder();
//
//                Resource res = bookSection.getResource();
//                try {
//                    InputStream is = res.getInputStream();
//                    BufferedReader r = new BufferedReader(new InputStreamReader(is));
//                    String line;
//                    while ((line = r.readLine()) != null) {
//                        chapter.append(line);
//
//                    }
//                } catch (IOException e) {
//                }
//                mEntireBook.add(chapter.toString());
//            }
//
//            return mEntireBook.toArray().length;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServiceException("Book Could Not Be Found");
    }

    public List<Comment> getCommentsOfPage(Long buddyReadId, Integer pageNumber) {
        return commentRepository.findByBuddyReadIdAndPageNumber(buddyReadId, pageNumber);
    }

    public Comment saveComment(Comment comment) {
        User u = userRepository.findById(comment.getUserId()).get();
        comment.setUsername(u.getUsername());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        commentRepository.delete(comment);
    }

    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
