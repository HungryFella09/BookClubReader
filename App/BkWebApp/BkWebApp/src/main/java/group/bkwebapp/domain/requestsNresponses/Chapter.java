package group.bkwebapp.domain.requestsNresponses;

import java.util.List;

public class Chapter {

    private List<String> chapter;

    public Chapter(){}

    public Chapter(List<String> chapter) {
        this.chapter = chapter;
    }

    public List<String> getChapter() {
        return chapter;
    }
    public void setChapter(List<String> chapter) {
        this.chapter = chapter;
    }

}
