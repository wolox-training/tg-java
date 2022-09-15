package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
@Api
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public Iterable findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{username}")
    public User findByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException("User not found! username: "+username));
    }

    @GetMapping("/bookList/{username}")
    public List<Book> getBookList(@PathVariable String username){
        return userRepository.findByUsername(username).get().getBooks();
    }

    @PutMapping("/id/{id}")
    public User updateUser(@RequestBody User user, @PathVariable long id) {
        if (user.getId() != id) {
            throw new UserIdMismatchException("User id does not match! ID: "+id);
        }
        userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found! ID: "+id));
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found! ID: "+id));
        userRepository.deleteById(id);
    }

    /**
     * This method adds a book object to a user's collection.
     * @param user: User who wants a book to be added (User)
     * @param book: Book to be added (Book)
     */
    @PutMapping("addBook/{username}")
    @ApiOperation(value = "Given a book, add it to a user's collection", response= User.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message="Book successfully added to collection."),
            @ApiResponse(code=401, message="You are not authorized to view the resource."),
            @ApiResponse(code=403, message="Accessing the resource you were trying to reach is forbidden."),
            @ApiResponse(code=404, message="The resource you were trying to reach is not found.")
    })
    void addBookToUsersCollection(@ApiParam(value="User who wants to add the book", required = true)
    @PathVariable String username, @ApiParam(value="Book to be added", required = true) @RequestParam long bookId ){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException("User not found! username: "+username));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new BookNotFoundException("Book not found! ID: "+bookId));
        user.addBookToCollection(book);
        System.out.println(user.getBooks().size());
    }

    /**
     * This method deletes a book objects from a user's collection.
     * @param username: Username of the user who has a book on their collection (String)
     * @param bookId: ID of the book to be removed (long)
     */
    @PutMapping("/removeBook/{username}")
    void removeBookFromUsersCollection(@PathVariable String username, @RequestParam long bookId){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException("User not found! username: "+username));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new BookNotFoundException("Book not found! ID: "+bookId));
        user.removeBookFromCollection(book);
        System.out.println(user.getBooks().size());
    }
}
