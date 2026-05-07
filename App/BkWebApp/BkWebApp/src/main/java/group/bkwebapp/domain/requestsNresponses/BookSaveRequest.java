package group.bkwebapp.domain.requestsNresponses;

import group.bkwebapp.domain.User;
import org.springframework.web.multipart.MultipartFile;

public class BookSaveRequest {
    private MultipartFile file;
    private Long user;
    private String bookName;

    public BookSaveRequest(){}

    public BookSaveRequest(MultipartFile file, Long user, String bookName) {
        this.file = file;
        this.user = user;
        this.bookName = bookName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
