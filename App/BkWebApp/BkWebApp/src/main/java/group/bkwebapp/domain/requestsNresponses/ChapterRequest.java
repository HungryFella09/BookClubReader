package group.bkwebapp.domain.requestsNresponses;

public class ChapterRequest {

    int chapterId;
    long bookId;

    public ChapterRequest(){}

    public ChapterRequest(int chapterId, long bookId) {
        this.chapterId = chapterId;
        this.bookId = bookId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
}
