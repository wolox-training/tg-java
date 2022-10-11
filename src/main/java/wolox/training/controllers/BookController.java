package wolox.training.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.services.OpenLibraryService;

@RestController
@RequestMapping("/api/books")
@Api
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    public OpenLibraryService openLibraryService;

    @PostMapping("/newBook")
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/all")
    public Iterable findAll(){
        return bookRepository.findAll();
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

    @GetMapping("/isbn/{isbn}")
    @ResponseStatus(code=HttpStatus.OK)
    public Book findByIsbn(@PathVariable String isbn) throws JsonProcessingException {
        return bookRepository.findByIsbn(isbn)
                .orElse(create(openLibraryService.bookInfo(isbn)));
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

