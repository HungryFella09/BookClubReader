package group.bkwebapp.domain.requestsNresponses;

public class NumberOfChaptersResponse {

    private int nrOfChapters;

    public NumberOfChaptersResponse(int nrOfChapters) {
        this.nrOfChapters = nrOfChapters;
    }

    public int getNrOfChapters() {
        return nrOfChapters;
    }

    public void setNrOfChapters(int nrOfChapters) {
        this.nrOfChapters = nrOfChapters;
    }
}
