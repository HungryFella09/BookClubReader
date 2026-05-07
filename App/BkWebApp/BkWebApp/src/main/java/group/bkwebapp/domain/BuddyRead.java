package group.bkwebapp.domain;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "buddy_reads")
public class BuddyRead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookName;
    private String key;

    @ManyToOne
    @JoinColumn(name = "admin")
    private User admin;

    @ManyToMany
    @JoinTable(
            name = "user_buddy_read",
            joinColumns = @JoinColumn(name = "buddy_read_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members = new HashSet<>();


    public BuddyRead() {}

    public BuddyRead(Long id, String bookName, User admin, String key) {
        this.id = id;
        this.bookName = bookName;
        this.admin = admin;
        this.key = key;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
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

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public void addMember(User user){
        members.add(user);
    }

    @Override
    public String toString() {
        return "BuddyRead{" +
                ", bookName='" + bookName + '\'' +
                ", admin=" + admin +
                '}';
    }
}
