package wolox.training.controllers;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@Api
public class BookController {

    private final static int PAGE_SIZE = 10;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/all")
    public Page<Book> findAll(@RequestParam String id, @RequestParam String genre, @RequestParam String author,
            @RequestParam String image, @RequestParam String title, @RequestParam String subtitle,
            @RequestParam String publicationYear, @RequestParam String pages, @RequestParam String isbn,
            @RequestParam int pageNumber){
        int numberOfPages;
        long idNumber;
        if(pages.equals("null") || pages.isEmpty())
            numberOfPages = 0;
        else
            numberOfPages = Integer.parseInt(pages);
        if(id.equals("null") || id.isEmpty())
            idNumber = 0;
        else
            idNumber = Long.parseLong(id);

        return bookRepository.findAll(idNumber, genre, author, image, title, subtitle,
                publicationYear, numberOfPages, isbn, PageRequest.of(pageNumber, PAGE_SIZE));
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found: "+id));
    }

    @GetMapping("/author/{bookAuthor}")
    public Book findByAuthor(@PathVariable String bookAuthor) {
        return bookRepository.findByAuthor(bookAuthor)
                .orElseThrow(()-> new BookNotFoundException("Book not found!"));
    }

    @GetMapping("/publisher-genre-year")
    public Page<Book> findByPublisherAndGenreAndYear(@RequestParam String publisher, @RequestParam String genre,
            @RequestParam String publicationYear, @RequestParam int pageNumber){
        return bookRepository.findByPublisherOrGenreOrPublicationYear(publisher, genre,
                publicationYear, PageRequest.of(pageNumber, PAGE_SIZE));
    }

    @PutMapping("/id/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable("id") Long id) {

        if (book.getId() != id) {
            throw new BookIdMismatchException("Ids do not match!");
        }
        bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found: "+id));
        return bookRepository.save(book);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
            bookRepository.findById(id)
                    .orElseThrow(()->new BookNotFoundException("Book not found: "+id));
        bookRepository.deleteById(id);
    }
}
