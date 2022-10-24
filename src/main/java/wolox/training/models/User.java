package wolox.training.models;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import wolox.training.exceptions.BookAlreadyOwnedException;

/**
 * Represents a person operating the system.
 */
@Entity
@ApiModel(description = "User operating the system.")
@Table(name="Users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ApiModelProperty(notes = "The user's username, in alphanumeric characters.")
    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @ApiModelProperty(notes = "The user's real name, in plain text.")
    @Column(nullable = false)
    private String name;

    @ApiModelProperty(notes = "The user's birthdate, in a LocalDate object.")
    @Column(nullable = false)
    private LocalDate birthdate;

    @ApiModelProperty(notes = "The user's book collection, a list of all owned books.")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bookList",
            joinColumns = @JoinColumn(name="book_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    private List<Book> books = new ArrayList<>();

    //Getters & setters

    public void setUsername(String username) {
        Preconditions.checkNotNull(username, "Illegal Argument, parameter is null");
        this.username = username;
    }

    public void setName(String name) {
        Preconditions.checkNotNull(name, "Illegal Argument, parameter is null");
        this.name = name;
    }

    public void setBirthdate(LocalDate birthdate) {
        Preconditions.checkNotNull(birthdate, "Illegal Argument, parameter is null");
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    //Methods
    /**
     * This method adds a book object to a user's collection.
     * @param b: Book to be added (Book)
     */
    @ApiOperation(value = "Given a book, add it to the user's collection.")
    public void addBookToCollection(@ApiParam(value="Book to be added", required = true) Book b){
        try {
            this.books.add(b);
        }catch(BookAlreadyOwnedException e){
            System.out.println(e);
        }
    }

    /**
     * This method removes a book from a user's collection.
     * @param b: Book to be removed (Book)
     */
    @ApiOperation(value = "Given a book, delete it from a user's collection")
    public void removeBookFromCollection(@ApiParam(value="Book to be deleted", required = true) Book b){
        this.books.remove(b);
    }
}
