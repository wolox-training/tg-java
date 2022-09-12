package wolox.training.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.exceptions.UsernameMismatchException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
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

    @GetMapping
    public User findByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException("User not found!"));
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable long id){
        if (user.getId() != id) {
            throw new UsernameMismatchException("Username does not match!");
        }
        userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found!"));
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found!"));
        userRepository.deleteById(id);
    }

    /**
     * This method adds a book object to a user's collection.
     * @param user: User who wants a book to be added (User)
     * @param bookId: ID from the book to be added (long)
     */
    @PostMapping
    void addBookToUsersCollection(@RequestBody User user, @PathVariable long bookId ){
        user.addBookToCollection(bookRepository.findById(bookId)
                .orElseThrow(()->new BookNotFoundException("Book not found!")));
    }

    /**
     * This method deletes a book objects from a user's collection.
     * @param user: User who has a book on their collection (User)
     * @param bookId: ID from the book to be removed (long)
     */
    @DeleteMapping
    void removeBookFromUsersCollection(@RequestBody User user, @PathVariable long bookId){
        user.removeBookFromCollection(bookRepository.findById(bookId)
                .orElseThrow(()-> new BookNotFoundException("Book not found!")));
    }


}
