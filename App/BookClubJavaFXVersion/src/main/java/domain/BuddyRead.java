package domain;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "buddy_reads")
public class BuddyRead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "book_name",length = 100,nullable = false)
    private String bookName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")
    private User admin;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "participants",
            joinColumns = { @JoinColumn(name = "id_user") },
            inverseJoinColumns = { @JoinColumn(name = "id_buddy_read") }
    )
    private Set<User> participants;

    public BuddyRead(long id, String bookName, Book book, User admin, Set<User> participants) {
        this.id = id;
        this.bookName = bookName;
        this.book = book;
        this.admin = admin;
        this.participants = participants;
    }

    public BuddyRead() {

    }

    public Set<User> getParticipants() {
        return participants;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }
}
