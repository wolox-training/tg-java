package wolox.training.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import wolox.training.models.Book;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDTO {
    public String title;
    public String subtitle = "-";
    public List<PublisherDTO> publishers;
    public String publish_date;
    public int number_of_pages;
    public List<AuthorDTO> authors;

    public CoverDTO cover;
    public Book convertToBook(String isbn){
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(this.title);
        book.setSubtitle(this.subtitle);
        book.setAuthor(this.authors.get(0).name);
        book.setYear(this.publish_date);
        book.setPublisher(this.publishers.get(0).name);
        book.setPages(this.number_of_pages);
        book.setImage(this.cover.medium);
        return book;
    }
}
