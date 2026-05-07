package group.bkwebapp.domain.dtos;

public class BuddyReadDto {

    private Long id;
    private String bookName;
    private String key;
    private String adminName;


    public BuddyReadDto() {}

    public BuddyReadDto(Long id, String bookName, String key, String adminName) {
        this.id = id;
        this.bookName = bookName;
        this.key = key;
        this.adminName = adminName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
