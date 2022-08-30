package wolox.training.models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import wolox.training.exceptions.BookAlreadyOwnedException;

/**
 * Represents a person operating on the system.
 */

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

    @OneToMany(mappedBy = "user")
    @Column(nullable = false)
    private List<Optional<Book>> books = Collections.emptyList();

    //Getters & setters
    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<Optional<Book>> getBooks() {
        return (List<Optional<Book>>) Collections.unmodifiableList(books);
    }

    //Constructors
    public User() {
    }

    public User(String username, String name, LocalDate birthdate) {
        this.username = username;
        this.name = name;
        this.birthdate = birthdate;
    }

    //Methods
    /**
     * This method adds a book object to a user's collection.
     * @param b: Book to be added (Book)
     */
    public void addBookToCollection(Optional<Book> b){
        try {
            this.books.add(b);
        }catch(BookAlreadyOwnedException e){}
    }

    /**
     * This method removes a book from a user's collection.
     * @param b: Book to be removed (Book)
     */
    public void removeBookFromCollection(Optional<Book> b){
        this.books.remove(b);
    }
}
