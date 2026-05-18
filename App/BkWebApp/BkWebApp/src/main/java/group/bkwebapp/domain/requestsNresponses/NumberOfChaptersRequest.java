package group.bkwebapp.domain.requestsNresponses;

public class NumberOfChaptersRequest {

    private long bookId;

    public NumberOfChaptersRequest() {}

    public NumberOfChaptersRequest(long bookId) {
        this.bookId = bookId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

}
