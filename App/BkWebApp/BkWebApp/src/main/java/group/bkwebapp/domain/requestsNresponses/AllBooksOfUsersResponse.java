package group.bkwebapp.domain.requestsNresponses;

import group.bkwebapp.domain.dtos.BuddyReadDto;

import java.util.ArrayList;
import java.util.List;

public class AllBooksOfUsersResponse {

    private List<BuddyReadDto> buddyReadsOfUser;

    public AllBooksOfUsersResponse() {
        this.buddyReadsOfUser = new ArrayList<>();
    }

    public AllBooksOfUsersResponse(List<BuddyReadDto> buddyReadsOfUser) {
        this.buddyReadsOfUser = buddyReadsOfUser;
    }

    public List<BuddyReadDto> getBuddyReadsOfUser() {
        return buddyReadsOfUser;
    }

    public void setBuddyReadsOfUser(List<BuddyReadDto> buddyReadsOfUser) {
        this.buddyReadsOfUser = buddyReadsOfUser;
    }
}
