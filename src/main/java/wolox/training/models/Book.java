package wolox.training.models;

import com.google.common.base.Preconditions;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String genre;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String publicationYear;

    @Column(nullable = false)
    private Integer pages;

    @Column(nullable = false)
    private String isbn;

    @ManyToMany(mappedBy = "books")

    //Getters & setters
    public void setGenre(String genre) {
        Preconditions.checkNotNull(genre, "Illegal Argument, parameter is null");
        this.genre = genre;
    }

    public void setAuthor(String author) {
        Preconditions.checkNotNull(author, "Illegal Argument, parameter is null");
        this.author = author;
    }

    public void setImage(String image) {
        Preconditions.checkNotNull(image, "Illegal Argument, parameter is null");
        this.image = image;
    }

    public void setTitle(String title) {
        Preconditions.checkNotNull(title, "Illegal Argument, parameter is null");
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkNotNull(subtitle, "Illegal Argument, parameter is null");
        this.subtitle = subtitle;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkNotNull(publisher, "Illegal Argument, parameter is null");
        this.publisher = publisher;
    }

    public void setYear(String publicationYear) {
        Preconditions.checkNotNull(publicationYear, "Illegal Argument, parameter is null");
        this.publicationYear = publicationYear;
    }

    public void setPages(Integer pages) {
        Preconditions.checkNotNull(pages, "Illegal Argument, parameter is null");
        this.pages = pages;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkNotNull(isbn, "Illegal Argument, parameter is null");
        this.isbn = isbn;
    }
}
