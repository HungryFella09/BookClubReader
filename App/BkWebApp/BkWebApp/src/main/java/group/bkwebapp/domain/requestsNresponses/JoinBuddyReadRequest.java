package group.bkwebapp.domain.requestsNresponses;

import org.springframework.web.bind.annotation.ModelAttribute;

public class JoinBuddyReadRequest {

    private Long user;
    private String key;

    public JoinBuddyReadRequest() {}

    public JoinBuddyReadRequest(Long user, String key) {
        this.user = user;
        this.key = key;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
