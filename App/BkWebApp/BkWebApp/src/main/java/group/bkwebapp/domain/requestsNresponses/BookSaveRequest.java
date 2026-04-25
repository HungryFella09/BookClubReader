package group.bkwebapp.domain.requestsNresponses;

import org.springframework.web.multipart.MultipartFile;

public class BookSaveRequest {
    MultipartFile file;

    public BookSaveRequest(){}

    public BookSaveRequest(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
