package wolox.training.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import wolox.training.exceptions.BookAlreadyOwnedException;

/**
 * Represents a person operating the system.
 */
@Entity
@ApiModel(description = "User operating the system.")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ApiModelProperty(notes = "The user's username, in alphanumeric characters.")
    @Column(nullable = false)
    private String username;

    @ApiModelProperty(notes = "The user's real name, in plain text.")
    @Column(nullable = false)
    private String name;

    @ApiModelProperty(notes = "The user's birthdate, in a Date object.")
    @Column(nullable = false)
    private LocalDate birthdate;

    @ApiModelProperty(notes = "The user's book collection, a list of all owned books.")
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
    @ApiOperation(value = "Given a book, add it to the user's collection.")
    public void addBookToCollection(@ApiParam(value="Book to be added", required = true) Optional<Book> b){
        try {
            this.books.add(b);
        }catch(BookAlreadyOwnedException e){}
    }

    /**
     * This method removes a book from a user's collection.
     * @param b: Book to be removed (Book)
     */
    @ApiOperation(value = "Given a book, delete it from a user's collection")
    public void removeBookFromCollection(@ApiParam(value="Book to be deleted", required = true) Optional<Book> b){
        this.books.remove(b);
    }
}
