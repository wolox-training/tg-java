package wolox.training.models;

import com.google.common.base.Preconditions;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
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

    @Column(nullable = false, name="publication_year")
    private String year;

    @Column(nullable = false)
    private Integer pages;

    @Column(nullable = false)
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<User> userList;

    //Getters & setters
    public long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        try{
            Preconditions.checkNotNull(genre, "Illegal Argument, parameter is null");
            this.genre = genre;
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        try{
            Preconditions.checkNotNull(author, "Illegal Argument, parameter is null");
            this.author = author;
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        try{
            Preconditions.checkNotNull(image, "Illegal Argument, parameter is null");
            this.image = image;
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        try{
            Preconditions.checkNotNull(title, "Illegal Argument, parameter is null");
            this.title = title;
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        try{
            Preconditions.checkNotNull(subtitle, "Illegal Argument, parameter is null");
            this.subtitle = subtitle;
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        try{
            Preconditions.checkNotNull(publisher, "Illegal Argument, parameter is null");
            this.publisher = publisher;
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        try{
            Preconditions.checkNotNull(year, "Illegal Argument, parameter is null");
            this.year = year;
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        try{
            Preconditions.checkNotNull(pages, "Illegal Argument, parameter is null");
            this.pages = pages;
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        try{
            Preconditions.checkNotNull(isbn, "Illegal Argument, parameter is null");
            this.isbn = isbn;
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    //Constructors
    public Book(){

    }
    public Book(long id, String genre, String author, String image, String title, String subtitle, String publisher, String year,
            Integer pages, String isbn) {
        this.id = id;
        this.genre = genre;
        this.author = author;
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
    }
}
