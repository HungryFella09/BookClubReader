package group.bkwebapp.domain;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "buddy_read_id")
    private Long buddyReadId;

    @Column(name = "paragraph_number")
    private Integer paragraphNumber;

    @Column(name = "page_number")
    private Integer pageNumber;

    private String username;

    public Comment() {

    }

    public Comment(Long id, String message, Long userId, Long buddyReadId, Integer paragraphNumber, Integer pageNumber) {
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.buddyReadId = buddyReadId;
        this.paragraphNumber = paragraphNumber;
        this.pageNumber = pageNumber;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBuddyReadId() {
        return buddyReadId;
    }

    public void setBuddyReadId(Long buddyReadId) {
        this.buddyReadId = buddyReadId;
    }

    public Integer getParagraphNumber() {
        return paragraphNumber;
    }

    public void setParagraphNumber(Integer paragraphNumber) {
        this.paragraphNumber = paragraphNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", userId=" + userId +
                ", buddyReadId=" + buddyReadId +
                ", paragraphNumber=" + paragraphNumber +
                '}';
    }
}
